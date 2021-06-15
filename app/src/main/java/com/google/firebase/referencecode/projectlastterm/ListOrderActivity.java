package com.google.firebase.referencecode.projectlastterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;

public class ListOrderActivity extends AppCompatActivity {
    TextView txtnameTable, txtstatus;
    public AlertDialog.Builder dialogBuilder;
    public AlertDialog dialog;
    private RecyclerView recyclerView, listOrder, listMenu;
    Button btnCallOrder, btnPay, btnClose;
    //fix problem here
    ArrayList<listMenuModel> arrayList;
    ArrayList<ListOrderModel> arrayListOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);


        Intent intent=getIntent();
        String nameTable = intent.getStringExtra("nameTable");


        Button btntable = findViewById(R.id.listtable);
        btntable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ListOrderActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        txtnameTable=findViewById(R.id.table);
        txtnameTable.setText(nameTable);
        txtstatus=findViewById(R.id.statusOrder);
         btnCallOrder=findViewById(R.id.callOrder);
         btnPay=findViewById(R.id.pay);

         btnCallOrder.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 createDialog();


             }
         });

        final Bitmap[] bmp = {null};
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        android.os.Handler handler=new Handler(Looper.myLooper());
        final String[] stat = new String[1];
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String result = null;

                String url1 = "http://192.168.1.6/KTCK/StatusTable.php?nameTable="+nameTable;

                StringRequest stringRequest=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        //Toast.makeText(ListOrderActivity.this, response.trim(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this, stringRequest.toString(), Toast.LENGTH_LONG).show();
                        //stat[0] =response.trim();
                        
                        txtstatus.setText(response.trim());



                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ListOrderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("nameTable", nameTable);

                        return params;
                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(ListOrderActivity.this);
                requestQueue.add(stringRequest);

//

                handler.post(new Runnable() {
                    @Override
                    public void run() {



//                        Toast.makeText(ListOrderActivity.this, arrayList.toString(), Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
        listOrder();
        sumPrice();
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListOrderActivity.this);
                builder.setTitle("Thanh toán "+nameTable);
                builder.setMessage("Bạn có muốn thanh toán bàn "+nameTable+" với tổng tiền "+sumPayment+" không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            payment();
                            finish();
                            startActivity(getIntent());
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }
    public void listOrder()
    {
        listOrder=findViewById(R.id.listOrder);
        listOrder.setHasFixedSize(true);
        listOrder.setLayoutManager(new LinearLayoutManager(this));


        arrayListOrder=new ArrayList<ListOrderModel>();

        final Bitmap[] bmp = {null};
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.myLooper());
        ProgressDialog mDialog = new ProgressDialog(ListOrderActivity.this);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String result;
                try {
                    URL url = new URL("http://192.168.1.6/KTCK/listOrder.php?nameTable="+txtnameTable.getText());
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader r=new BufferedReader(new InputStreamReader(in));
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line=r.readLine())!=null)
                        sb.append(line);

                    result=sb.toString();
                    JSONArray parent = new JSONArray(result);
                    int i=0;
                    while (i<=parent.length())
                    {
                        JSONObject child = parent.getJSONObject(i);

                        String imgurl=child.getString("img");
                        String nameFood = child.getString("nameFood");
                        double price=child.getDouble("price");
                        int amount = child.getInt("amount");
                        double sumPrice=child.getDouble("sumPrice");

                        arrayListOrder.add(new ListOrderModel(imgurl, nameFood, price, amount, sumPrice));

                        i++;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        mDialog.dismiss();
                        //listMenuModel = new Gson().fromJson(result, RssRoot.class);
                        //FeedAdapter adapter = new FeedAdapter(rssRoot, getBaseContext());
                        ListOrderAdapter listOrderAdapter= new ListOrderAdapter(arrayListOrder, getApplicationContext());
                        listOrder.setAdapter(listOrderAdapter);

                        new ListMenuAdapter(arrayListOrder, getApplicationContext());
//                        listMenu.setAdapter(listMenuAdapter);
                    }
                });
            }
        });
    }
    String sumPayment;
    public void sumPrice()
    {
        String urlOrder="http://192.168.1.6/KTCK/sumMoney.php?nameTable="+txtnameTable.getText();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlOrder, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {


                //Toast.makeText(ListOrderActivity.this, response.trim(), Toast.LENGTH_LONG).show();
                sumPayment=response.trim();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         Toast.makeText(ListOrderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("nameTable", String.valueOf(txtnameTable.getText()));
                //params.put("nameFood", )
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(ListOrderActivity.this);
        requestQueue.add(stringRequest);
    }
    public void payment()
    {
        String urlPayMent="http://192.168.1.6/KTCK/payment.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlPayMent, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {


                Toast.makeText(ListOrderActivity.this, "Thanh toán thành công", Toast.LENGTH_LONG).show();
              //  sumPayment=response.trim();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListOrderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("nameTable", String.valueOf(txtnameTable.getText()));
                //params.put("nameFood", )
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(ListOrderActivity.this);
        requestQueue.add(stringRequest);
    }
    public void createDialog()
    {
        dialogBuilder=new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.activity_listmenu, null);
       // DialogFragment dialogFragment=new DialogFragment();
     //   ProgressDialog mDialog = new ProgressDialog(ListOrderActivity.this);
//        Bundle bundle = new Bundle();
//        bundle.putString("text", String.valueOf(txtnameTable.getText()));
//
//
//        dialogFragment.setArguments(bundle);
//
//
//        dialogFragment.show(contactPopupView.get);

        recyclerView=contactPopupView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<listMenuModel>();

        final Bitmap[] bmp = {null};
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.myLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String result;
                try {
                    String urlOrder="http://192.168.1.6/KTCK/insertOrder.php";
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, urlOrder, new Response.Listener<String>() {
                        @Override

                        public void onResponse(String response) {
                            //Toast.makeText(ListOrderActivity.this, response.trim(), Toast.LENGTH_LONG).show();
                            //Toast.makeText(MainActivity.this, stringRequest.toString(), Toast.LENGTH_LONG).show();

                            //Toast.makeText(ListOrderActivity.this, response.trim(), Toast.LENGTH_LONG).show();

                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                   // Toast.makeText(ListOrderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("nameTable", String.valueOf(txtnameTable.getText()));
                            //params.put("nameFood", )
                            return params;
                        }
                    };

                    RequestQueue requestQueue= Volley.newRequestQueue(ListOrderActivity.this);
                    requestQueue.add(stringRequest);
                    String url1 = "http://192.168.1.6/KTCK/json.php";

                    URL url = new URL(url1);
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader r=new BufferedReader(new InputStreamReader(in));
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line=r.readLine())!=null)
                        sb.append(line);

                    result=sb.toString();
                    JSONArray parent = new JSONArray(result);
                    int i=0;
                    while (i<=parent.length())
                    {
                        JSONObject child = parent.getJSONObject(i);
                        String name = child.getString("name");
                        String img = child.getString("img");
                        double price = child.getDouble("price");

                        arrayList.add(new listMenuModel(name, img, price));
                        i++;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                handler.post(new Runnable() {
                    @Override
                    public void run() {


                        //listMenuModel = new Gson().fromJson(result, RssRoot.class);
                        //FeedAdapter adapter = new FeedAdapter(rssRoot, getBaseContext());
                        ListMenuAdapter listMenuAdapter= new ListMenuAdapter(arrayList, getApplicationContext(), String.valueOf(txtnameTable.getText()));
                        recyclerView.setAdapter(listMenuAdapter);

                    }
                });
            }
        });

        dialogBuilder.setView(contactPopupView);
        dialog=dialogBuilder.create();
        dialog.show();
        btnClose=contactPopupView.findViewById(R.id.close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {


                closeDialog();
            }


        });


    }
    public void closeDialog()
    {
        dialog.dismiss();
       finish();
       startActivity(getIntent());
    }
}