package com.google.firebase.referencecode.projectlastterm;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListOrderViewHolder extends RecyclerView.ViewHolder {
    ImageView imageFood;
    TextView nameFood, amount, price, sumPrice;


    public ListOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        imageFood=itemView.findViewById(R.id.imageFood);
        nameFood= itemView.findViewById(R.id.nameFood);
        amount=itemView.findViewById(R.id.amount);
        sumPrice=itemView.findViewById(R.id.sumPrice);
        price=itemView.findViewById(R.id.price);
    }



}
