package com.irayasoft.pakkruti.util;
import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.irayasoft.pakkruti.R;
public class Util {
    //loading image using glide librray
    public static void loadImage(ImageView imageView, String url, CircularProgressDrawable progressDrawable ){
        RequestOptions requestOptions=new RequestOptions()
                .placeholder(progressDrawable)
                .error(R.drawable.dogimg);
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .into(imageView);
    }
    //for loading progresdrawable
    public static CircularProgressDrawable getProgressDrawable(Context context){
        CircularProgressDrawable circularProgressDrawable=new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(20f);
        circularProgressDrawable.start();
        return circularProgressDrawable;

    }
    //methode to set image from layout
    @BindingAdapter("android:imageUrl")
    public static void loadImage(ImageView imageView,String url){
        loadImage(imageView,url,getProgressDrawable(imageView.getContext()));
    }
}
