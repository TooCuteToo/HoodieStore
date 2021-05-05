package com.example.shopping.Utils;

import android.content.Context;
import android.graphics.Color;

import com.example.shopping.Adapter.ImageSliderAdapter;
import com.example.shopping.Model.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class ImageSlider {
    public static void loadImageSlider(SliderView view, Context context) {
        SliderView sliderView = view;
        ImageSliderAdapter adapter = new ImageSliderAdapter(context);
        adapter.addItem(new SliderItem("file:///android_asset/Image/slider_1.jpg", "lala"));
        adapter.addItem(new SliderItem("file:///android_asset/Image/slider_2.jpg", "lalala"));
        adapter.addItem(new SliderItem("file:///android_asset/Image/slider_3.jpg", "lalala"));
        adapter.addItem(new SliderItem("file:///android_asset/Image/slider_4.jpg", "lalala"));
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(2); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }
}
