package com.google.firebase.referencecode.projectlastterm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;

import static com.google.firebase.referencecode.projectlastterm.R.id.amount;
import static com.google.firebase.referencecode.projectlastterm.R.id.parent;


public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuViewHolder> {

    ArrayList<listMenuModel> list;
    ArrayList<ListOrderModel> listOrderModelArrayList;

    Context context;
    Button btnAddAmount, btnSubAmount;
    TextView amount;
     String nameTable;

    RecyclerView recyclerView, recyclerViewOrder;
    public ListMenuAdapter(){}
    public ListMenuAdapter(ArrayList<listMenuModel> list, Context context, String nameTable)
    {

        this.list=list;

        this.context=context;
        this.nameTable=nameTable;
    }
    public ListMenuAdapter(ArrayList<ListOrderModel> list, Context context)
    {

        listOrderModelArrayList=list;
        this.context=context;

    }

    @NonNull
    @Override
    public ListMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_listmenu, parent, false);


        btnAddAmount=view.findViewById(R.id.btnAddFood);
        btnSubAmount=view.findViewById(R.id.btnSubFood);
        return new ListMenuViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ListMenuViewHolder holder, int position) {
        listMenuModel current = list.get(position);

        holder.name.setText(current.getName());


        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        String str1 = en.format(current.getPrice());
        holder.price.setText(str1);
        Picasso.get().load(current.getUrl()).into(holder.imageView);

       // btnAddAmount.setId(position);
        //System.out.println(btnAddAmount.getId());
        //amount.setId(position);
       // System.out.println(amount.getId());

        btnAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.amount.setText(String.valueOf(Integer.parseInt(String.valueOf(holder.amount.getText()))+1));
            }
        });
        btnSubAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(String.valueOf(holder.amount.getText()))>1)
                holder.amount.setText(String.valueOf(Integer.parseInt(String.valueOf(holder.amount.getText()))-1));
            }
        });
       // System.out.println(txtNameTable.getText());
        View contactPopupView = LayoutInflater.from(context).inflate(R.layout.activity_list_order, null);

        listOrderModelArrayList=new ArrayList<ListOrderModel>();
        recyclerViewOrder= contactPopupView.findViewById(R.id.listOrder);
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));

        holder.setItemClickListener(new itemClickListener() {
            @Override
            public void onclick(View view, int position, boolean isLongClick) {
                if (isLongClick) {


                    String urlOrder="http://192.168.1.6/KTCK/nameFood.php?nameTable="+nameTable+"&nameFood="+current.getName();
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, urlOrder, new Response.Listener<String>() {
                        @Override

                        public void onResponse(String response) {
                            //Toast.makeText(context, "Đã thêm món vào bàn", Toast.LENGTH_LONG).show();



                            final Bitmap[] bmp = {null};
                            ExecutorService executorService= Executors.newSingleThreadExecutor();
                            Handler handler=new Handler(Looper.myLooper());
                            ProgressDialog mDialog = new ProgressDialog(context);

                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    String result;
//                                    try {
//                                        URL url = new URL("http://192.168.1.6/KTCK/listOrder.php?nameTable=" + nameTable);
//                                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                                        BufferedReader r = new BufferedReader(new InputStreamReader(in));
//                                        StringBuffer sb = new StringBuffer();
//                                        String line;
//                                        while ((line = r.readLine()) != null)
//                                            sb.append(line);
//
//                                        result = sb.toString();
//                                        JSONArray parent = new JSONArray(result);
//                                        int i = 0;
//                                        while (i <= parent.length()) {
//                                            JSONObject child = parent.getJSONObject(i);
//
//                                            String imgurl = child.getString("img");
//                                            String nameFood = child.getString("nameFood");
//                                            double price = child.getDouble("price");
//                                            int amount = child.getInt("amount");
//                                            double sumPrice = child.getDouble("sumPrice");
//
//                                            listOrderModelArrayList.add(new ListOrderModel(imgurl, nameFood, price, amount, sumPrice));
//
//                                            i++;
//                                        }
//                                    } catch (MalformedURLException e) {
//                                        e.printStackTrace();
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//
//                                    handler.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//
//                                            mDialog.dismiss();
                                            if (response.trim().equals(""))
                                            {
                                                String urlOrder="http://192.168.1.6/KTCK/insertOrder.php";
                                                StringRequest stringRequest=new StringRequest(Request.Method.POST, urlOrder, new Response.Listener<String>() {
                                                    @Override

                                                    public void onResponse(String response) {
                                                        Toast.makeText(context, "Đã thêm món vào bàn", Toast.LENGTH_LONG).show();

//
                                                    }
                                                },
                                                        new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                ) {
                                                    @Override
                                                    protected Map<String, String> getParams() {

                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("nameTable", nameTable);
                                                        params.put("nameFood", current.getName());
                                                        params.put("amount", String.valueOf(holder.amount.getText()));
                                                        params.put("sumPrice",String.valueOf(Double.parseDouble(String.valueOf(current.getPrice()))*Integer.parseInt(String.valueOf(holder.amount.getText()))));

                                                        //params.put(1, )
                                                        return params;
                                                    }
                                                };
                                                RequestQueue requestQueue= Volley.newRequestQueue(context);
                                                requestQueue.add(stringRequest);
//                                                System.out.println(String.valueOf(holder.amount.getText())+" + "+response.trim());
//                                                ListOrderModel listOrderModel=new ListOrderModel(current.getUrl(), current.getName(), current.getPrice(),  Integer.parseInt(String.valueOf(holder.amount.getText())) , Double.parseDouble(String.valueOf(Double.parseDouble(String.valueOf(current.getPrice()))*Integer.parseInt(String.valueOf(holder.amount.getText())))));
//
//                                                listOrderModelArrayList.add(listOrderModelArrayList.size(), listOrderModel);
//                                                ListOrderAdapter listOrderAdapter=new ListOrderAdapter(listOrderModelArrayList, contactPopupView.getContext());
//                                                listOrderAdapter.notifyItemInserted(listOrderModelArrayList.size()+1);
//                                                listOrderAdapter.notifyDataSetChanged();
                                            }
                                            else
                                            {
                                                String urlOrder="http://192.168.1.6/KTCK/updateAmount.php";
                                                StringRequest stringRequest=new StringRequest(Request.Method.POST, urlOrder, new Response.Listener<String>() {
                                                    @Override

                                                    public void onResponse(String response1) {
                                                        Toast.makeText(context, "Đã thêm món vào bàn", Toast.LENGTH_LONG).show();

//
                                                    }
                                                },
                                                        new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                ) {
                                                    @Override
                                                    protected Map<String, String> getParams() {

                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("nameTable", nameTable);
                                                        params.put("nameFood", current.getName());
                                                        String amount1=String.valueOf(Integer.parseInt(String.valueOf(holder.amount.getText()))+ Integer.parseInt(response.trim()));
                                                        params.put("amount", amount1);
                                                        params.put("amount1", String.valueOf(holder.amount.getText()));
                                                        params.put("sumPrice",String.valueOf(Double.parseDouble(String.valueOf(current.getPrice()))*Integer.parseInt(amount1)));

                                                        //params.put(1, )
                                                        return params;
                                                    }
                                                };
                                                RequestQueue requestQueue= Volley.newRequestQueue(context);
                                                requestQueue.add(stringRequest);


//                                                if (listOrderModelArrayList.isEmpty()) System.out.println("empty");
//                                                else System.out.println("no empty");
//                                                System.out.println(listOrderModelArrayList.size());
//
//                                                String amount1=String.valueOf(Integer.parseInt(String.valueOf(holder.amount.getText()))+ Integer.parseInt(response.trim()));
                                                //ListOrderModel listOrderModel=new ListOrderModel(current.getUrl(), current.getName(), current.getPrice(), Integer.parseInt(amount1), Double.parseDouble(String.valueOf(Double.parseDouble(String.valueOf(current.getPrice()))*Integer.parseInt(amount1)))) ;

                                                //View viewOrder=infalInflater.inflate(R.layout.activity_list_order, view12.findViewById(R.id.listOrder), false);
//                                                listOrderModelArrayList.set(position, listOrderModel);
//                                                ListOrderAdapter listOrderAdapter1=new ListOrderAdapter(listOrderModelArrayList, contactPopupView.getContext());
////
//                                                listOrderAdapter1.notifyItemChanged(position);
//                                                notifyItemRangeChanged(position, listOrderModelArrayList.size());
//                                                recyclerViewOrder.setAdapter(listOrderAdapter1);
                                            }
//                                        }
//                                    });


                                }
                            });
//
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("nameTable", nameTable);
                            params.put("nameFood", current.getName());


                            //params.put(1, )
                            return params;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);

                    //Toast.makeText(context, "Position:"+current.getName(), Toast.LENGTH_LONG).show();



                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
