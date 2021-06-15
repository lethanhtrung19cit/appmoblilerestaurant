package com.google.firebase.referencecode.projectlastterm;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageMenuFragment extends Fragment {
    RecyclerView listAllMenu;
    ArrayList<listMenuModel> listMenuModelArrayList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;
    public ManageMenuFragment(Context context) {
        this.context=context;
        // Required empty public constructor
    }
    public ManageMenuFragment() {

        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageMenuFragment newInstance(String param1, String param2) {
        ManageMenuFragment fragment = new ManageMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view=inflater.inflate(R.layout.fragment_manage_menu, container, false);
        final FragmentActivity c= getActivity();
        Button addNewFood=view.findViewById(R.id.addFood);
        addNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(view.getContext());
                final View contactPopupView = LayoutInflater.from(view.getContext()).inflate(R.layout.add_food, null);
                dialogBuilder.setView(contactPopupView);
                AlertDialog dialog=dialogBuilder.create();
                dialog.show();
                EditText addName, addPrice, addUrlImage, addAmount, addStatus;
                addName=contactPopupView.findViewById(R.id.nameFoodAdd);
                addPrice=contactPopupView.findViewById(R.id.priceAdd);
                addUrlImage=contactPopupView.findViewById(R.id.urlImage);
                addAmount=contactPopupView.findViewById(R.id.amountAdd);
                addStatus=contactPopupView.findViewById(R.id.statusAdd);
                Button btnAdd = contactPopupView.findViewById(R.id.btnAddFoodAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String urlOrder="http://192.168.1.6/KTCK/addFood.php";
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlOrder, new Response.Listener<String>() {
                            @Override

                            public void onResponse(String response) {
                                Toast.makeText(view.getContext(), "Thêm thành công", Toast.LENGTH_LONG).show();




                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(view.getContext(), error.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams() {

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("name", String.valueOf(addName.getText()));
                                params.put("price", String.valueOf(addPrice.getText()));
                                params.put("img", String.valueOf(addUrlImage.getText()));
                                params.put("amount", String.valueOf(addAmount.getText()));
                                params.put("status", String.valueOf(addStatus.getText()));
                                //params.put("nameFood", )
                                return params;
                            }
                        };

                        RequestQueue requestQueue= Volley.newRequestQueue(view.getContext());
                        requestQueue.add(stringRequest);
                        listMenuModel listMenuModel=new listMenuModel(String.valueOf(addName.getText()),String.valueOf(addUrlImage.getText()), Double.parseDouble(String.valueOf(addPrice.getText())),  Integer.parseInt(String.valueOf(addAmount.getText())), String.valueOf(addStatus.getText()));

                        listMenuModelArrayList.add(listMenuModelArrayList.size(), listMenuModel);
                        ListMenuAdapter listMenuAdapter=new ListMenuAdapter(listMenuModelArrayList, view.getContext(), "");
                        listMenuAdapter.notifyItemInserted(listMenuModelArrayList.size()+1);
                        listMenuAdapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
            }
        });

        listAllMenu= view.findViewById(R.id.listAllMenu);
        listAllMenu.setHasFixedSize(true);
        listAllMenu.setLayoutManager(new LinearLayoutManager(c));
        listMenuModelArrayList=new ArrayList<listMenuModel>();


        ExecutorService executorService= Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.myLooper());
        ProgressDialog mDialog = new ProgressDialog(container.getContext());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String result;
                try {
                    URL url = new URL("http://192.168.1.6/KTCK/listMenu.php");
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
                        int amount=child.getInt("amount");
                        String status=child.getString("status");
                        listMenuModelArrayList.add(new listMenuModel(name, img, price, amount, status));
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
                        ManagementMenuAdapter listMenuAdapter= new ManagementMenuAdapter(listMenuModelArrayList, c);
                        listAllMenu.setAdapter(listMenuAdapter);

                    }
                });
            }
        });
        return view;
    }
}