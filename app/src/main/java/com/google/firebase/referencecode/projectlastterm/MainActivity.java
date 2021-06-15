package com.google.firebase.referencecode.projectlastterm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

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

public class MainActivity extends AppCompatActivity{
    String status="trốn";
    TextView txtStatus;

    Button bt;
    Button[] tableList = new Button[9];
    public void listTable()
    {
        tableList[0] = (Button)findViewById(R.id.table1);
        tableList[1] = (Button)findViewById(R.id.table2);
        tableList[2] = (Button)findViewById(R.id.table3);
        tableList[3] = (Button)findViewById(R.id.table4);
        tableList[4] = (Button)findViewById(R.id.table5);
        tableList[5] = (Button)findViewById(R.id.table6);
        tableList[6] = (Button)findViewById(R.id.table7);
        tableList[7] = (Button)findViewById(R.id.table8);
        tableList[8] = (Button)findViewById(R.id.table9);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listTable();
        txtStatus=findViewById(R.id.txtstatus);
        bt=findViewById(R.id.button);
        bt.setBackgroundColor(Color.RED);



        for (int i=0; i < 9; i++) // Loop through your entire list to access all your 9 Buttons
        {
//
            Status();
            int finalI = i;
            tableList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //change boolean value

                    Intent intent=new Intent(MainActivity.this, ListOrderActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("nameTable", tableList[finalI].getText());
                    startActivity(intent);
                }
            });


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.option_menu_header, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent=new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    public void Status()
    {
        for (int i=0; i < 9; i++) // Loop through your entire list to access all your 9 Buttons
        {
            String url1 = "http://192.168.1.6/KTCK/StatusTable.php?nameTable=" + tableList[i].getText();

            int finalI = i;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                @Override

                public void onResponse(String response) {
                    status=response.trim();
                    if (status.equals("Đang dùng")) {
                        tableList[finalI].setBackgroundColor(Color.RED);
                    }
                    //Toast.makeText(MainActivity.this, response.trim(), Toast.LENGTH_LONG).show();

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nameTable", String.valueOf(tableList[finalI].getText()));

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }
    }

}