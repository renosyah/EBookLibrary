package com.example.renosyahputra.pdfviewerlibrary.activity_pdf_viewer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.example.renosyahputra.pdfviewerlibrary.R;
import com.example.renosyahputra.pdfviewerlibrary.customViewPagger.CustomViewPagger;
import com.example.renosyahputra.pdfviewerlibrary.pdfViewerInit.PdfViewerDataObj;
import com.example.renosyahputra.pdfviewerlibrary.pdfViewerInit.PdfViewerInit;
import com.example.renosyahputra.pdfviewerlibrary.viewpagger_adapter.PdfViewPaggerAdapter;

import java.util.ArrayList;

public class ActivityPdfViewer extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        PdfViewerInit.OnPdfViewerListener,
        PdfViewerInit.OnGetPdfPageSize {

    private Context context;
    private Intent intent;

    private CustomViewPagger viewPaggerPdf;
    private PdfViewerInit pdfViewerInit = PdfViewerInit.newInstance();


    private PdfViewerDataObj pdfViewerDataObj = new PdfViewerDataObj();
    private ArrayList<Bitmap> bitmapsPdf = new ArrayList<>();

    PdfViewPaggerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        initWidget();
    }

    private void initWidget(){

        context = this;
        intent = getIntent();

        pdfViewerDataObj = (PdfViewerDataObj) intent.getSerializableExtra("data");

        viewPaggerPdf = findViewById(R.id.hackyViewPagerPdf);
        viewPaggerPdf.addOnPageChangeListener(this);

        adapter = new PdfViewPaggerAdapter(
                context,R.layout.custom_viewpagger_pdf_viewer,bitmapsPdf);

        if (requestPermission()){
            return;
        }

        pdfViewerInit = PdfViewerInit.newInstance()
                .setContext(context)
                .setPdfFile(pdfViewerDataObj.file)
                .setPdfFileName(pdfViewerDataObj.filename)
                .setOnPdfViewerListener(this)
                .setOnGetPdfPageSize(this)
                .openPdf();


        setAdapter();

    }

    private void setAdapter(){
        viewPaggerPdf.setAdapter(adapter);

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        pdfViewerInit.setPage(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    private Boolean requestPermission(){
        Boolean isDenied = false;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
            }
            isDenied = true;

        }

        return isDenied;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Intent i = getIntent();
        i.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(i);
        finish();


    }

    @Override
    public void pdfPageSize(@NonNull int pageSize) {
        bitmapsPdf.clear();
        for (int i=0;i<pageSize;i++){
            bitmapsPdf.add(emptyBitmap());
        }
    }

    @Override
    public void onRenderPdf(@NonNull int page,@NonNull Bitmap bitmap) {
        bitmapsPdf.set(page,bitmap);
        adapter.setImageViewBitmap(bitmap);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onException(@NonNull Exception e) {
        e.printStackTrace();
    }


    private Bitmap emptyBitmap(){
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(100, 100, conf);
        return bmp;
    }

}
