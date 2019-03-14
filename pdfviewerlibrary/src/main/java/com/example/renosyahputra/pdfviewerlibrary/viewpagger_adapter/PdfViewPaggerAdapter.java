package com.example.renosyahputra.pdfviewerlibrary.viewpagger_adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import com.example.renosyahputra.pdfviewerlibrary.R;
import com.example.renosyahputra.pdfviewerlibrary.photoview.PhotoView;

import java.util.ArrayList;

public class PdfViewPaggerAdapter extends PagerAdapter {
    private Context context;
    private int layout;
    private ArrayList<Bitmap> images;


    public PdfViewPaggerAdapter(Context context,int layout,ArrayList<Bitmap> images) {
        this.context = context;
        this.layout = layout;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view,@NonNull Object object) {
        return view == object;
    }

    private PhotoView imageView;

    public void setImageViewBitmap(Bitmap bitmap) {
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {

        View view = ((Activity) context)
                .getLayoutInflater()
                .inflate(layout,null);

        imageView = (PhotoView) view.findViewById(R.id.image_pdf_viewer);
        imageView.setImageBitmap(images.get(position));

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }


}
