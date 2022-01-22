package com.shanzafar.mc_project.AdapterClasses;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shanzafar.mc_project.ModelClasses.ItemChooseForBought;
import com.shanzafar.mc_project.R;

import java.util.ArrayList;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {


    Context context;
    ArrayList<ItemChooseForBought> shoppingCartAdapterArrayList;


    public ShoppingCartAdapter(Context context) {
        this.context = context;
        shoppingCartAdapterArrayList = new ArrayList<>();
    }


    public void setShoppingCartAdapterArrayList(ArrayList<ItemChooseForBought> shoppingCartAdapterArrayList) {
        this.shoppingCartAdapterArrayList = shoppingCartAdapterArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shopping_cart_recyclerview_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        ItemChooseForBought itemChooseForBought = shoppingCartAdapterArrayList.get(i);

        myViewHolder.shoppingPrice.setText(itemChooseForBought.getPrice());
        myViewHolder.shoppingMainText.setText(itemChooseForBought.getName());

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.banner);
        Glide.with(context).applyDefaultRequestOptions(requestOptions).load(itemChooseForBought.getImage()).into(myViewHolder.mainShoppingImage);

        myViewHolder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCartAdapterArrayList.remove(i);
                notifyDataSetChanged();
            }
        });





    }

    @Override
    public int getItemCount() {
        return shoppingCartAdapterArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mainShoppingImage,deleteImageView;
        TextView shoppingMainText,shoppingPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            mainShoppingImage = itemView.findViewById(R.id.shopping_cart_main_img);
            deleteImageView = itemView.findViewById(R.id.shopping_cart_delete_img);
            shoppingMainText  = itemView.findViewById(R.id.shopping_cart_main_txt);
            shoppingPrice = itemView.findViewById(R.id.shopping_cart_price);
        }
    }



}
