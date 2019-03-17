package com.example.renosyahputra.pdfbrowserlibrary.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.ParcelFileDescriptor;
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

import java.io.File;
import java.io.IOException;
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

    boolean enableTubnail = true;
    public void SetEnableTubnail(boolean enableTubnail){
        this.enableTubnail = enableTubnail;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (enableTubnail && getPdfTubnail(item.file) != null) {
                holder.image.setImageBitmap(getPdfTubnail(item.file));
            }
        }



        return row;
    }

    private class DataList {
        public ImageView image;
        public TextView name;
    }

    private Bitmap getPdfTubnail(File file)  {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            try {
                ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(file,
                        ParcelFileDescriptor.MODE_READ_ONLY);

                if (parcelFileDescriptor != null) {
                    PdfRenderer pdfRenderer = new PdfRenderer(parcelFileDescriptor);

                    PdfRenderer.Page currentPagePdf = pdfRenderer.openPage(0);
                    bitmap = Bitmap.createBitmap(currentPagePdf.getWidth(), currentPagePdf.getHeight(),
                            Bitmap.Config.ARGB_8888);

                    currentPagePdf.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

                    currentPagePdf.close();
                    pdfRenderer.close();
                    parcelFileDescriptor.close();

                }

            }catch (IOException ignored){

            }
        }

        return bitmap;
    }
}
