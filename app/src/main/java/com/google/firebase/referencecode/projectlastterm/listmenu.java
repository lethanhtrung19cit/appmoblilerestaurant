package com.google.firebase.referencecode.projectlastterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class listmenu extends AppCompatActivity {
    private RecyclerView recyclerView;

    //fix problem here
    ArrayList<listMenuModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmenu);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<listMenuModel>();

        final Bitmap[] bmp = {null};
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.myLooper());
        ProgressDialog mDialog = new ProgressDialog(listmenu.this);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String result;
                try {
                    URL url = new URL("http://192.168.1.6/KTCK/json.php");
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

                        mDialog.dismiss();
                        //listMenuModel = new Gson().fromJson(result, RssRoot.class);
                        //FeedAdapter adapter = new FeedAdapter(rssRoot, getBaseContext());
                        ListMenuAdapter listMenuAdapter= new ListMenuAdapter(arrayList, getApplicationContext(), "");
                        recyclerView.setAdapter(listMenuAdapter);

                    }
                });
            }
        });
    }

}