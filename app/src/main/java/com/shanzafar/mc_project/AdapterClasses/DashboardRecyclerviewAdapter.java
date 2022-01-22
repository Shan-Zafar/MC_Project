package com.shanzafar.mc_project.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shanzafar.mc_project.ItemActivity;
import com.shanzafar.mc_project.ModelClasses.MainItemsModel;
import com.shanzafar.mc_project.R;

import java.util.ArrayList;

public class DashboardRecyclerviewAdapter extends RecyclerView.Adapter<DashboardRecyclerviewAdapter.MyViewHolder> {

    Context context;
    ArrayList<MainItemsModel> itemsModelArrayList ;
    String TAG = DashboardRecyclerviewAdapter.class.getSimpleName();
    public static String EXTRAA_ITEM_TYPE = "com.shanzafar.mc_project.AdapterClasses.DashboardRecyclerviewAdapter.extra.itemtype";


    public DashboardRecyclerviewAdapter(Context context) {
        this.context = context;
        itemsModelArrayList = new ArrayList<>();
    }

    public void setItemsModelArrayList(ArrayList<MainItemsModel> itemsModelArrayList) {
        this.itemsModelArrayList = itemsModelArrayList;
        notifyDataSetChanged();
        Log.d(TAG, "setItemsModelArrayList: "+itemsModelArrayList.toString());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.dashboard_recyclerview_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        MainItemsModel mainItemsModel = itemsModelArrayList.get(i);

        myViewHolder.itemName.setText(mainItemsModel.getMain_item_name());
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.ic_sale);
        Glide.with(context).applyDefaultRequestOptions(requestOptions).load(mainItemsModel.getMain_item_path()).into(myViewHolder.itemImage);
        Log.d(TAG, "onBindViewHolder: "+i);


    }

    @Override
    public int getItemCount() {
        return itemsModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView itemName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.dashboard_recyclerview_item_imageview);
            itemName = itemView.findViewById(R.id.dashboard_recyclerview_item_name_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String selectedItemName =  itemsModelArrayList.get(getAdapterPosition()).getMain_item_name();

                    Log.d(TAG, "onClick: "+selectedItemName);
                    context.startActivity(new Intent(context,ItemActivity.class).putExtra(EXTRAA_ITEM_TYPE,selectedItemName));






                }
            });

        }
    }



}
