package com.huanzong.property.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.huanzong.property.R;
import com.huanzong.property.database.ImgBean;
import com.youth.banner.loader.ImageLoader;

/**
 * banner图片加载机制
 * Created by hudongwx on 18-4-16.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_image_load)	//加载成功之前占位图
                .error(R.mipmap.icon_image_error);
//                .centerCrop();
//        if(path!=null&&path instanceof BannerBean){
//            Glide.with(context)
////                .load(url)
//                    .load("http://app.hzmtkj.com/upload/"+((BannerBean)path).getImage())
//                    .apply(options)
//                    .into(imageView);
//        }else
        if (path!=null&&path instanceof ImgBean){
            Glide.with(context)
                    .load("http://app.hzmtkj.com/"+((ImgBean)path).getThumb())
                    .apply(options)
                    .into(imageView);
        }else{
            Glide.with(context)
//                .load(url)
                    .load(path)
                    .apply(options)
                    .into(imageView);
        }

    }
}
