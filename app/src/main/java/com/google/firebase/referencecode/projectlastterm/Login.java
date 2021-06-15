package com.google.firebase.referencecode.projectlastterm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText txtUserName, txtPassWord;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUserName=findViewById(R.id.txtUserName);
        txtPassWord=findViewById(R.id.txtPassWord);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url1 = "http://192.168.1.6/KTCK/login.php?userName=" + txtUserName.getText();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        String a = response.trim();
                        if (a.equals(String.valueOf(txtPassWord.getText()))) {
                            Toast.makeText(Login.this, a, Toast.LENGTH_LONG).show();
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Login.this, MainActivityManagement.class);
                            startActivity(intent);
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userName", String.valueOf(txtUserName.getText()));

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                requestQueue.add(stringRequest);
            }
        });
    }
}