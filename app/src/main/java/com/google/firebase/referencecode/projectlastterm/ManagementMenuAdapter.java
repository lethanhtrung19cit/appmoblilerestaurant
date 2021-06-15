package com.google.firebase.referencecode.projectlastterm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagementMenuAdapter extends RecyclerView.Adapter<ManagementViewHolder> {
    ArrayList<listMenuModel> list;
    Context context;
     AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText txtprice, txtamount;
    TextView textPrice, textAmount;
    RecyclerView recyclerView;
    public ManagementMenuAdapter(){}
    public ManagementMenuAdapter(ArrayList<listMenuModel> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public ManagementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_managemenu, parent, false);
        recyclerView=view.findViewById(R.layout.activity_main_management);
        return new ManagementViewHolder(view);
    }



    public void onBindViewHolder(@NonNull ManagementViewHolder holder, int position) {
        listMenuModel current = list.get(position);
        holder.name.setText(current.getName());
        holder.price.setText(String.valueOf(current.getPrice()));
        holder.amount.setText(String.valueOf(current.getAmount()));
        holder.status.setText(current.getStatus());
        Picasso.get().load(current.getUrl()).into(holder.imageView);
        holder.buttonOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.buttonOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.update:
                                dialogBuilder=new AlertDialog.Builder(view.getContext());
                                final View contactPopupView = LayoutInflater.from(context).inflate(R.layout.edit_food, null);
                                dialogBuilder.setView(contactPopupView);
                                dialog=dialogBuilder.create();
                                dialog.show();
                                textPrice=contactPopupView.findViewById(R.id.textPrice);
                                textAmount=contactPopupView.findViewById(R.id.textAmount);

                                txtamount=contactPopupView.findViewById(R.id.amountEd);
                                txtamount.setText(String.valueOf(current.getAmount()));
                                txtprice=contactPopupView.findViewById(R.id.priceEd);
                                txtprice.setText(String.valueOf(current.getPrice()));
                                Button btnSave = contactPopupView.findViewById(R.id.Save);
                                btnSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String urlOrder="http://192.168.1.6/KTCK/editFood.php";
                                        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlOrder, new Response.Listener<String>() {
                                            @Override

                                            public void onResponse(String response) {
                                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_LONG).show();



                                                listMenuModel listMenuModel=new listMenuModel(current.getName(), current.getUrl(), Double.parseDouble(String.valueOf(txtprice.getText())), Integer.parseInt(String.valueOf(txtamount.getText())), current.getStatus());
                                                list.set(position, listMenuModel);
                                                ListMenuAdapter listMenuAdapter=new ListMenuAdapter(list, v.getContext(), "");

                                                listMenuAdapter.notifyItemChanged(position);
                                                notifyItemRangeChanged(position, list.size());

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
                                                params.put("price", String.valueOf(txtprice.getText()));
                                                params.put("amount", String.valueOf(txtamount.getText()));
                                                params.put("name", String.valueOf(current.getName()));
                                                //params.put("nameFood", )
                                                return params;
                                            }
                                        };

                                        RequestQueue requestQueue= Volley.newRequestQueue(context);
                                        requestQueue.add(stringRequest);
                                        dialog.dismiss();
                                    }
                                });
                                break;
                            case R.id.delete:
                                int actualPosition = holder.getAdapterPosition();
                                list.remove(actualPosition);
                                notifyItemRemoved(actualPosition);
                                notifyItemRangeChanged(actualPosition, list.size());
                                break;

                        }
                        return false;
                    }



                });
                //displaying the popup
                popup.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
