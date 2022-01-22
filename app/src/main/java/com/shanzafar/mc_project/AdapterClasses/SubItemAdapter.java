package com.shanzafar.mc_project.AdapterClasses;


import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shanzafar.mc_project.ModelClasses.ItemChooseForBought;
import com.shanzafar.mc_project.ModelClasses.SubItem;
import com.shanzafar.mc_project.R;
import com.shanzafar.mc_project.SingletonClass;

import java.util.ArrayList;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.MyViewHolder> {

    Context context;
    ArrayList<SubItem> subItemArrayList;
    String TAG = SubItemAdapter.class.getSimpleName();


    public SubItemAdapter(Context context) {
        this.context = context;
        subItemArrayList = new ArrayList<>();
    }

    public void setSubItemArrayList(ArrayList<SubItem> subItemArrayList) {
        this.subItemArrayList = subItemArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.sub_item_recyclerview_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        SubItem subItem = subItemArrayList.get(i);

        myViewHolder.name.setText(subItem.getName());
        myViewHolder.price.setText(subItem.getPrice());


        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.ic_sale);
        Glide.with(context).applyDefaultRequestOptions(requestOptions).load(subItem.getImage()).into(myViewHolder.itemImage);



    }

    @Override
    public int getItemCount() {
        return subItemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,price;
        ImageView itemImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sub_item_name);
            price = itemView.findViewById(R.id.sub_item_price);
            itemImage = itemView.findViewById(R.id.sub_item_img);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogueSetup();
                    Log.d(TAG, "onClick: ");
                }
            });
        }

        private void dialogueSetup() {

            final Dialog quantity_dialogue = new Dialog(context);
            quantity_dialogue.setContentView(R.layout.quantity_item_layout);
            quantity_dialogue.setCancelable(false);
            quantity_dialogue.show();

            //-----------------------------------Item whose quantity is to determine----------------//
            final SubItem subItem = subItemArrayList.get(getAdapterPosition());


            //--------------------------------Dialogue Content Initlization------------------------//

            Button cancel = quantity_dialogue.findViewById(R.id.quantity_item_cancel);
            Button Okay = quantity_dialogue.findViewById(R.id.quantity_item_okay);
            ImageView addImage = quantity_dialogue.findViewById(R.id.quantity_plus_img);
            ImageView minusImage = quantity_dialogue.findViewById(R.id.quantity_minus_img);
            final TextView quantityShowerTxt = quantity_dialogue.findViewById(R.id.quantity_show_txt);
            TextView quantityMainTxt = quantity_dialogue.findViewById(R.id.quantity_main_textview);
            ImageView quantityMainImageView = quantity_dialogue.findViewById(R.id.quantity_layout_img);


            quantityMainTxt.setText(subItem.getName());


            RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.ic_sale);
            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(subItem.getImage()).into(quantityMainImageView);














            //------------------------------------Dialogue content Click listeners------------------//
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    quantity_dialogue.dismiss();


                }
            });

            Okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String quantitySelected = quantityShowerTxt.getText().toString();
                    if(quantitySelected.equalsIgnoreCase("0")) {
                        quantity_dialogue.dismiss();
                        return;
                    }


                    ItemChooseForBought itemChooseForBought = new ItemChooseForBought();

                    itemChooseForBought.setName(subItem.getName());
                    itemChooseForBought.setImage(subItem.getImage());
                    itemChooseForBought.setPrice(subItem.getPrice());

                    Log.d(TAG, "onClick: "+quantitySelected);
                    itemChooseForBought.setPrice(
                            String.valueOf(Integer.parseInt(subItem.getPrice())*Integer.parseInt(quantitySelected))
                    );


                    SingletonClass.getSingleton().addBoughtItem(itemChooseForBought);
                    quantity_dialogue.dismiss();


                }
            });


            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String previousQuantity = quantityShowerTxt.getText().toString();
                    Log.d(TAG, "onClick: "+previousQuantity);
                    quantityShowerTxt.setText(String.valueOf(Integer.parseInt(previousQuantity)+1));


                }
            });

            minusImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String previousQuantity = quantityShowerTxt.getText().toString();
                    if(previousQuantity.equalsIgnoreCase("0"))
                        return;
                    quantityShowerTxt.setText(String.valueOf(Integer.parseInt(previousQuantity)-1));
                    Log.d(TAG, "onClick: "+quantityShowerTxt);


                }
            });










        }
    }
}

