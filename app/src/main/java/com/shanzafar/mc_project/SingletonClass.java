package com.shanzafar.mc_project;


import android.util.Log;

import com.shanzafar.mc_project.ModelClasses.ItemChooseForBought;

import java.util.ArrayList;

public class SingletonClass {

    String TAG = SingletonClass.class.getSimpleName();
    private static SingletonClass singletonClass;
    private static ArrayList<ItemChooseForBought> itemChooseForBoughtArrayList;


    public  ArrayList<ItemChooseForBought> getItemChooseForBoughtArrayList() {
        return itemChooseForBoughtArrayList;
    }

    public static SingletonClass getSingleton()
    {
        if(singletonClass == null) {
            singletonClass = new SingletonClass();
            itemChooseForBoughtArrayList = new ArrayList<>();

        }
        return singletonClass;
    }


    public void addBoughtItem(ItemChooseForBought itemChooseForBought)
    {
        Log.d(TAG, "addBoughtItem: ");
        itemChooseForBoughtArrayList.add(itemChooseForBought);
    }






}
