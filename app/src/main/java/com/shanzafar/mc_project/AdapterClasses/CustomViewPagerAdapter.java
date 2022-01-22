package com.shanzafar.mc_project.AdapterClasses;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shanzafar.mc_project.R;

import java.util.ArrayList;

public class CustomViewPagerAdapter extends PagerAdapter {


    ArrayList<String> imagesPathArray;
    Context context;

    public CustomViewPagerAdapter(Context context) {
        this.context = context;
        imagesPathArray = new ArrayList<>();
    }

    public void setImagesPathArray(ArrayList<String> imagesPathArray) {
        this.imagesPathArray = imagesPathArray;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imagesPathArray.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (ConstraintLayout)o;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.view_pager_hot_items,container,false);

        setUpImages(view,position);


        container.addView(view);



        return view;

    }

    private void setUpImages(View view,int position) {


        ImageView hot_items_images = view.findViewById(R.id.view_pager_item_img);
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.banner);
        Glide.with(context).applyDefaultRequestOptions(requestOptions).load(imagesPathArray.get(position)).into(hot_items_images);

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}

