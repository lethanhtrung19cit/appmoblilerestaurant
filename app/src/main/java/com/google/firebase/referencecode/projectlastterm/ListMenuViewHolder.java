package com.google.firebase.referencecode.projectlastterm;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ListMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    ImageView imageView;
    TextView name, price, amount;

    private itemClickListener itemClickListener;
    public ListMenuViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        imageView=itemView.findViewById(R.id.image);
        name= itemView.findViewById(R.id.name);
        price=itemView.findViewById(R.id.price);
        amount=itemView.findViewById(R.id.amount);
    }

    public void setItemClickListener(itemClickListener itemClickListener)
    {

        this.itemClickListener=itemClickListener;
    }

    @Override
    public void onClick(View v)
    {
        itemClickListener.onclick(v, getAdapterPosition(), true);

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
