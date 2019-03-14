package com.example.renosyahputra.pdfbrowserlibrary;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;
import com.example.renosyahputra.pdfbrowserlibrary.fragment_dialog.FragmentDialogPdfBrowser;

import java.io.File;
import java.io.Serializable;

public class PdfBrowserInit {

    private static PdfBrowserInit _instance;
    private Context context;
    private Boolean enableThumbnail = false;
    private PdfBrowserInit.OnPdfBrowserListener listener;

    public static PdfBrowserInit newInstance() {
        _instance = new PdfBrowserInit();
        return _instance;
    }

    public PdfBrowserInit setEnableThumbnail(Boolean enableThumbnail) {
        _instance.enableThumbnail = enableThumbnail;
        return _instance;
    }

    public PdfBrowserInit setContext(Context context) {
        _instance.context = context;
        return _instance;
    }

    public PdfBrowserInit setOnPdfBrowserListener(OnPdfBrowserListener listener) {
        _instance.listener = listener;
        return _instance;
    }

    private PdfBrowserInit() {
        super();
    }

    public void browse(){

        if (checkVariableIsNull()){
            return;
        }

        FragmentDialogPdfBrowser fragmentDialogPdfBrowser = new  FragmentDialogPdfBrowser();
        fragmentDialogPdfBrowser.setListener(listener);
        fragmentDialogPdfBrowser.setEnableThumbnail(enableThumbnail);

        try {

            Activity activity = (Activity) this.context;
            fragmentDialogPdfBrowser.show(activity.getFragmentManager(), "dialog");

        }catch (ClassCastException e){
            e.printStackTrace();
        }

    }

    private Boolean checkVariableIsNull(){
        return (context == null || listener == null);
    }

    public interface OnPdfBrowserListener {
        void onChoosePdf(@NonNull PdfBrowserData pdfBrowserData);
        void onBrowsingFinish();
    }

    public static class PdfBrowserData implements Serializable {
        public File file;
        public String filename;
        public String filePath;

        public PdfBrowserData() {
            super();
        }

        public PdfBrowserData(File file, String filename) {
            this.file = file;
            this.filename = filename;
        }

        public PdfBrowserData(String filePath,String filename) {
            this.filename = filename;
            this.filePath = filePath;
        }

        public PdfBrowserData(File file, String filename, String filePath) {
            this.file = file;
            this.filename = filename;
            this.filePath = filePath;
        }

        public String GetFileExtension(){
            return  MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(this.file).toString());
        }
        public String GetFiletTypeExtension(){
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(Uri.fromFile(this.file).toString());
        }
        public static final String FormatPDF = "pdf";
    }
}
