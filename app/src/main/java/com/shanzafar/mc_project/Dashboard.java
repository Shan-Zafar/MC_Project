package com.shanzafar.mc_project;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jawad.departmentalstore.AdapterClasses.CustomViewPagerAdapter;
import com.jawad.departmentalstore.AdapterClasses.DashboardRecyclerviewAdapter;
import com.jawad.departmentalstore.ModelClasses.MainItemsModel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    static {

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    ViewPager viewPager;
    CustomViewPagerAdapter customViewPagerAdapter;
    FloatingActionButton floatingActionButton;
    FirebaseDatabase firebaseDatabase;
    String TAG = Dashboard.class.getSimpleName();
    RecyclerView dashboardRecyclerView ;
    DashboardRecyclerviewAdapter dashboardRecyclerviewAdapter;


    public static final String EXTRAA_HOTITEMS = "hotitems";
    public static final String EXTRAA_ITEMS = "main";
    private Toolbar toolbar;

    Timer timer;
    int page = 1;



    // this is an inner class...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        //---------------------------------------Set up toolbar/actionbar---------------------------//

        setUpToolbar();

        //-----------------------------------------Set up Floating button --------------------------//
        setUpFloatingButton(

        );

        //--------------------------------------Intilize Firebase-----------------------------------//
        firebaseDatabase = FirebaseDatabase.getInstance();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //-------------------------------------------View pager to show hot items-------------------//
        setUpViewPager();


        //-------------------------------------------Get data for Hot items -------------------------//
        getDataForHotItems();



        //--------------------------------------------Set up recyclerview---------------------------//
        setUpRecyclerView();

        //------------------------------------------- Get data from server to shoe in recyclerview--//
        getDataForRecyclerView();


    }

    private void getDataForRecyclerView() {

        firebaseDatabase.getReference().child(EXTRAA_ITEMS).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d(TAG, "onDataChange: "+dataSnapshot.toString());


                        ArrayList<MainItemsModel> mainItemsModels = new ArrayList<>();

                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                            Log.d(TAG, "onDataChange: "+dataSnapshot1.toString());
                            MainItemsModel mainItemsModel = new MainItemsModel();
                            mainItemsModel.setMain_item_name(dataSnapshot1.getKey());
                            mainItemsModel.setMain_item_path(String.valueOf(dataSnapshot1.child("image").getValue()));
                            mainItemsModels.add(mainItemsModel);

                        }

                        dashboardRecyclerviewAdapter.setItemsModelArrayList(mainItemsModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );



    }

    private void setUpRecyclerView() {

        dashboardRecyclerView = findViewById(R.id.dashboard_recyclerview_item);
        dashboardRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        dashboardRecyclerviewAdapter = new DashboardRecyclerviewAdapter(this);
        dashboardRecyclerView.setAdapter(dashboardRecyclerviewAdapter);


    }

    private void setUpToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    private void getDataForHotItems() {


        firebaseDatabase.getReference().child(EXTRAA_HOTITEMS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d(TAG, "onDataChange: "+dataSnapshot.toString());

                        //-----------------Initlize ArrayList for CustompagerAdapter------------------------//
                        ArrayList<String> imagesPath = new ArrayList<>();


                        Log.d(TAG, "onDataChange: "+dataSnapshot.getChildrenCount());
                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                        {
                            Log.d(TAG, "onDataChange: "+dataSnapshot1.getValue());
                            imagesPath.add(String.valueOf(dataSnapshot1.getValue()));

                        }


                        customViewPagerAdapter.setImagesPathArray(imagesPath);
                        pageSwitcher(2);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });










    }

    private void setUpFloatingButton() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void setUpViewPager() {

        //-------------------------------------------Initlize view pager ---------------------------//
        viewPager = findViewById(R.id.viewPager);
        customViewPagerAdapter = new CustomViewPagerAdapter(this);
        viewPager.setAdapter(customViewPagerAdapter);

        // ---------------------------------------------------------------------------



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {

            startActivity(new Intent(this,Shoppingcart.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_shopping) {
            // Handle the camera action
            startActivity(new Intent(this,Shoppingcart.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {

                    page++;
                    int pos = page%viewPager.getChildCount();
                    viewPager.setCurrentItem(pos);
                    // Toast.makeText(Dashboard.this, ""+pos, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
