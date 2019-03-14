package com.example.renosyahputra.pdfbrowserlibrary.fragment_dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.example.renosyahputra.pdfbrowserlibrary.PdfBrowserInit;
import com.example.renosyahputra.pdfbrowserlibrary.R;
import com.example.renosyahputra.pdfbrowserlibrary.pdfBrowserActivity.PdfBrowserActivity;

import java.io.File;

public class FragmentDialogPdfBrowser extends DialogFragment {

    private Context context;
    private PdfBrowserInit.OnPdfBrowserListener listener;
    private int RequestCode = 238;
    private Boolean enableThumbnail = false;

    public void setEnableThumbnail(Boolean enableThumbnail) {
        this.enableThumbnail = enableThumbnail;
    }

    public void setListener(PdfBrowserInit.OnPdfBrowserListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.empty_layout, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = getActivity();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent i = new Intent(context, PdfBrowserActivity.class);
        i.putExtra("enableThumbnail",enableThumbnail);
        startActivityForResult(i, RequestCode);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode && Activity.RESULT_OK == resultCode && listener != null && data != null) {
            PdfBrowserInit.PdfBrowserData pdfBrowserData = (PdfBrowserInit.PdfBrowserData) data.getSerializableExtra("data");
            pdfBrowserData.file = new File(pdfBrowserData.filePath);
            listener.onChoosePdf(pdfBrowserData);
        }

        getDialog().dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        listener.onBrowsingFinish();
    }
}