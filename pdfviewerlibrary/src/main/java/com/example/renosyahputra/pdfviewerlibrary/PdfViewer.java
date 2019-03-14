package com.example.renosyahputra.pdfviewerlibrary;

import android.app.Activity;
import android.content.Context;
import com.example.renosyahputra.pdfviewerlibrary.fragment_dialog.FragmentDialogPdfViewer;
import com.example.renosyahputra.pdfviewerlibrary.pdfViewerInit.PdfViewerDataObj;

import java.io.File;

public class PdfViewer {

    private static PdfViewer _instance;
    private Context context;
    private File pdfFile;
    private PdfViewer.OnPdfVewerListener listener;

    public static PdfViewer newInstance() {
        _instance = new PdfViewer();
        return _instance;
    }

    public PdfViewer setContext(Context context) {
        _instance.context = context;
        return _instance;
    }

    public PdfViewer setPdfFile(File pdfFile) {
        _instance.pdfFile = pdfFile;
        return _instance;
    }

    public PdfViewer setOnPdfVewerListener(OnPdfVewerListener listener) {
        _instance.listener = listener;
        return _instance;
    }

    private PdfViewer() {
        super();
    }

    public void viewPDF(){

        if (checkVariableIsNull()){
            return;
        }

        FragmentDialogPdfViewer fragmentDialogPdfViewer = new FragmentDialogPdfViewer();
        fragmentDialogPdfViewer.setPdfViewerDataObj(new PdfViewerDataObj(pdfFile,pdfFile.getName()));
        fragmentDialogPdfViewer.setListener(listener);

        try {

            Activity activity = (Activity) this.context;
            fragmentDialogPdfViewer.show(activity.getFragmentManager(), "dialog");

        }catch (ClassCastException e){
            e.printStackTrace();
        }

    }
    private Boolean checkVariableIsNull(){
        return (context == null || listener == null);
    }

    public interface OnPdfVewerListener {
        void onFinishView();
    }
}
