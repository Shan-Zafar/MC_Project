package com.shanzafar.mc_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shanzafar.mc_project.AdapterClasses.DashboardRecyclerviewAdapter;
import com.shanzafar.mc_project.AdapterClasses.SubItemAdapter;
import com.shanzafar.mc_project.ModelClasses.SubItem;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {


    String TAG = ItemActivity.class.getSimpleName();
    RecyclerView recyclerView;
    SubItemAdapter subItemAdapter;
    FirebaseDatabase firebaseDatabase;
    public static String EXTRAA_SUBITEM_ITEMS = "items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);



        //------------------------------Set  up recylerview to show data----------------------------//
        setUpRecyclerView();


        //--------------------------------Get data from the server to show in the recyclerview------//
        getDataFromServer();


    }

    private void getDataFromServer() {


        Log.d(TAG, "getDataFromServer: "+getIntent().getStringExtra(DashboardRecyclerviewAdapter.EXTRAA_ITEM_TYPE));
        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference().child(Dashboard.EXTRAA_ITEMS)
                .child(getIntent().getStringExtra(DashboardRecyclerviewAdapter.EXTRAA_ITEM_TYPE))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d(TAG, "onDataChange: "+dataSnapshot.toString());
                        ArrayList<SubItem> subItemArrayList = new ArrayList<>();


                        for(DataSnapshot dataSnapshot1 : dataSnapshot.child(EXTRAA_SUBITEM_ITEMS).getChildren())
                        {
                            Log.d(TAG, "onDataChange: loop"+dataSnapshot1.toString());

                            SubItem subItem = new SubItem();
                            subItem.setName(dataSnapshot1.getKey());
                            subItem.setImage(String.valueOf(dataSnapshot1.child("image").getValue()));
                            subItem.setPrice(String.valueOf(dataSnapshot1.child("price").getValue()));

                            subItemArrayList.add(subItem);
                        }

                        subItemAdapter.setSubItemArrayList(subItemArrayList);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




    }

    private void setUpRecyclerView() {

        subItemAdapter = new SubItemAdapter(this);


        recyclerView = findViewById(R.id.sub_recycelerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));



        recyclerView.setAdapter(subItemAdapter);







    }









}
