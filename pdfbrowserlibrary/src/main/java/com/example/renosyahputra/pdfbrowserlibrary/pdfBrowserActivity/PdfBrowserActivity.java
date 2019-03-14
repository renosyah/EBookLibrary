package com.example.renosyahputra.pdfbrowserlibrary.pdfBrowserActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.example.renosyahputra.pdfbrowserlibrary.PdfBrowserInit;
import com.example.renosyahputra.pdfbrowserlibrary.R;
import com.example.renosyahputra.pdfbrowserlibrary.adapter.CustomAdapterFilePdf;

import java.io.File;
import java.util.ArrayList;

public class PdfBrowserActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private Context context;

    private GridView gridViewPdfFiles;
    private SwipeRefreshLayout swipeRefreshLayoutgridViewPdfFiles;
    private TextView textViewEmpty;

    private ArrayList<PdfBrowserInit.PdfBrowserData> datafilesPdf = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_browser);
        initWidget();
    }

    private void initWidget(){
        context = this;

        gridViewPdfFiles = findViewById(R.id.ActivityGridViewPdfFile);
        gridViewPdfFiles.setOnItemClickListener(this);

        swipeRefreshLayoutgridViewPdfFiles = findViewById(R.id.refresActivityGridViewPdfFile);
        swipeRefreshLayoutgridViewPdfFiles.setOnRefreshListener(this);

        textViewEmpty = findViewById(R.id.DataKosongTextView);
        textViewEmpty.setText(getString(R.string.text_empty));

        if (requestPermission()){
            return;
        }

        for (PdfBrowserInit.PdfBrowserData data : GetAllPdfFile(context)){
            if (data.GetFileExtension().equals(PdfBrowserInit.PdfBrowserData.FormatPDF)){
                datafilesPdf.add(data);
            }
        }

        setAdapter();
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

        if (requestCode == 200) {
            Intent i = getIntent();
            i.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            startActivity(i);
            finish();
        }


    }

    private ArrayList<PdfBrowserInit.PdfBrowserData> GetAllPdfFile(Context context) {
        ArrayList<PdfBrowserInit.PdfBrowserData> datas = new ArrayList<>();

        String[] columns = {"*"};

        String orderBy = MediaStore.Files.FileColumns.DATE_ADDED+" DESC";
        String select =  MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_NONE;

        Cursor phones = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), columns, select, null, orderBy);
        assert phones != null;
        while (phones.moveToNext()) {
            Uri path = Uri.parse(phones.getString(phones.getColumnIndex(MediaStore.Files.FileColumns.DATA)));
            datas.add(new PdfBrowserInit.PdfBrowserData(new File(path.getPath()),new File(path.getPath()).getName(),path.getPath()));
        }
        phones.close();


        return datas;
    }

    private void setAdapter(){

        CustomAdapterFilePdf adapterFilePdf = new CustomAdapterFilePdf(context,R.layout.custom_adapter_pdf_file,datafilesPdf);
        gridViewPdfFiles.setAdapter(adapterFilePdf);

        checkIsEmpty(datafilesPdf);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("data",new PdfBrowserInit.PdfBrowserData(
                datafilesPdf.get(position).filePath,
                datafilesPdf.get(position).filename)
        );
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayoutgridViewPdfFiles.setRefreshing(!swipeRefreshLayoutgridViewPdfFiles.isRefreshing());

    }

    private void checkIsEmpty(ArrayList<PdfBrowserInit.PdfBrowserData> data){
        if (!(data.size() > 0)){

            swipeRefreshLayoutgridViewPdfFiles.setVisibility(View.GONE);
            textViewEmpty.setVisibility(View.VISIBLE);

        }else {

            swipeRefreshLayoutgridViewPdfFiles.setVisibility(View.VISIBLE);
            textViewEmpty.setVisibility(View.GONE);

        }
    }
}
