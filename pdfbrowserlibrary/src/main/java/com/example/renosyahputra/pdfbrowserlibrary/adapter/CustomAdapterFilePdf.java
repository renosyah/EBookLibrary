package com.example.renosyahputra.pdfbrowserlibrary.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.renosyahputra.pdfbrowserlibrary.PdfBrowserInit;
import com.example.renosyahputra.pdfbrowserlibrary.R;

import java.util.List;

public class CustomAdapterFilePdf extends ArrayAdapter<PdfBrowserInit.PdfBrowserData> {

    private Context context;
    private int resource;
    private List<PdfBrowserInit.PdfBrowserData> objects = null;

    public CustomAdapterFilePdf(@NonNull Context context, int resource, @NonNull List<PdfBrowserInit.PdfBrowserData> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    int color = 0;
    public void SetColor(int color){
        this.color = color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        DataList holder = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)this.context).getLayoutInflater();
            row = inflater.inflate(resource,parent,false);
            holder = new DataList();

            holder.image = row.findViewById(R.id.FileImageAdapter);
            holder.name = row.findViewById(R.id.FileNameAdapter);

            row.setTag(holder);
        }else{
            holder = (DataList) row.getTag();
        }
        PdfBrowserInit.PdfBrowserData item = getItem(position);

        holder.name.setText(item.filename);

        holder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.pdf_logo, null));
        if(color != 0) {
            holder.image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }


        return row;
    }

    private class DataList {
        public ImageView image;
        public TextView name;
    }
}
