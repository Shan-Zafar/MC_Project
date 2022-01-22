package com.shanzafar.mc_project;

import android.content.DialogInterface;
import android.content.Intent;
import  com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.shanzafar.mc_project.AdapterClasses.ShoppingCartAdapter;
import com.shanzafar.mc_project.ModelClasses.ItemChooseForBought;

import java.util.ArrayList;

public class Shoppingcart extends AppCompatActivity {


    String TAG = Shoppingcart.class.getSimpleName();
    RecyclerView recyclerView;
    ShoppingCartAdapter shoppingCartAdapter ;
    FloatingActionButton bill;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);



        bill = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ItemChooseForBought> s =  SingletonClass.getSingleton().getItemChooseForBoughtArrayList();
                if(s.size() !=0) {
                    for (int i = 0; i < s.size(); i++) {
                        Log.d("tryForm", s.get(i).getQuantity() + "   " + s.get(i).getPrice());
                        int itemTotal = Integer.parseInt(s.get(i).getPrice());
                        total += itemTotal;
                    }
                    billDialogue(total);
                }
            }
        });


        setUpFilter();

    }

    private void setUpFilter() {
        EditText filter_ed = findViewById(R.id.shopping_cart_search_bar);
        filter_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d(TAG, "onTextChanged: "+s);
                if(SingletonClass.getSingleton().getItemChooseForBoughtArrayList().size()<=0)
                    return;


                ArrayList<ItemChooseForBought>  temp = new ArrayList<>();


                for(ItemChooseForBought itemChooseForBought : SingletonClass.getSingleton().getItemChooseForBoughtArrayList() )
                {
                    Log.d(TAG, "onTextChanged: ");
                    if(itemChooseForBought.getName().toLowerCase().contains(String.valueOf(s).toLowerCase())) {
                        temp.add(itemChooseForBought);
                        Log.d(TAG, "onTextChanged: ");
                    }

                }
                Log.d(TAG, "onTextChanged: outside loop");

                if(shoppingCartAdapter==null)
                    return;
                Log.d(TAG, "onTextChanged: not null");

                shoppingCartAdapter.setShoppingCartAdapterArrayList(temp);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.shopping_cart_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        shoppingCartAdapter = new ShoppingCartAdapter(this);
        shoppingCartAdapter.setShoppingCartAdapterArrayList(SingletonClass.getSingleton().getItemChooseForBoughtArrayList());
        recyclerView.setAdapter(shoppingCartAdapter);


    }

    public void billDialogue(int total)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Shoppingcart.this);
        builder1.setMessage("Your will pay "+total);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Shoppingcart.this,Dashboard.class));
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
