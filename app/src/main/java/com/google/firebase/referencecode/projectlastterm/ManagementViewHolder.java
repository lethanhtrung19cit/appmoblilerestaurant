package com.google.firebase.referencecode.projectlastterm;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ManagementViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView name, price, amount, status, buttonOption;

    private itemClickListener itemClickListener;
    public ManagementViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=itemView.findViewById(R.id.imageMa);
        name= itemView.findViewById(R.id.nameMa);
        price=itemView.findViewById(R.id.priceMa);
        amount=itemView.findViewById(R.id.amountMa);
        status=itemView.findViewById(R.id.statusMa);
        buttonOption=itemView.findViewById(R.id.optionMenu);
    }




}
