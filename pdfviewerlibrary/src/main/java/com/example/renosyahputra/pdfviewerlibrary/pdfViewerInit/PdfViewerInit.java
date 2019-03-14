package com.example.renosyahputra.pdfviewerlibrary.pdfViewerInit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PdfViewerInit {

    private static PdfViewerInit _instance;

    private Context context;
    private File pdfFile;
    private String pdfFileName;
    private OnPdfViewerListener listener;
    private OnGetPdfPageSize onGetPdfPageSize;

    private ParcelFileDescriptor parcelFileDescriptor;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;

    private boolean prePage,nextPage = false;

    private PdfViewerInit() {
        super();
    }

    public static PdfViewerInit newInstance() {
        _instance = new PdfViewerInit();
        return  _instance;
    }

    public PdfViewerInit setContext(Context context) {
        _instance.context = context;
        return  _instance;
    }

    public PdfViewerInit setPdfFile(File pdfFile) {
        _instance.pdfFile = pdfFile;
        return  _instance;
    }

    public PdfViewerInit setPdfFileName(String pdfFileName) {
        _instance.pdfFileName = pdfFileName;
        return  _instance;
    }

    public PdfViewerInit setOnPdfViewerListener(OnPdfViewerListener listener) {
        _instance.listener = listener;
        return  _instance;
    }

    public PdfViewerInit setOnGetPdfPageSize(OnGetPdfPageSize listener) {
        _instance.onGetPdfPageSize = listener;
        return  _instance;
    }

    public PdfViewerInit previousPage(){
        if (_instance != null && prePage && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            showPage(currentPage.getIndex() - 1);
        }
        return _instance;
    }

    public PdfViewerInit setPage(int page){
        if (_instance != null && nextPage && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            showPage(page);
        }
        return _instance;
    }

    public PdfViewerInit nextPage(){
        if (_instance != null && nextPage && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            showPage(currentPage.getIndex() + 1);
        }
        return _instance;
    }

    public PdfViewerInit openPdf(){
        try {
            openRender();
            showPage(0);
        }catch (Exception e){
            if (listener != null){
                listener.onException(e);
            }
        }
        return _instance;
    }

    public void closePdf(){
        try {
            closeRenderer();
        }catch (Exception e){
            if (listener != null){
                listener.onException(e);
            }
        }
    }

    public ArrayList<Bitmap> getAllRenderedPdf(){

        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (currentPage != null){
                currentPage.close();
            }

            PdfRenderer.Page currentPagePdf = null;

            for (int i = 0; i < pdfRenderer.getPageCount(); i++) {

                if (currentPagePdf != null){
                    currentPagePdf.close();
                }

                currentPagePdf = pdfRenderer.openPage(i);
                Bitmap bitmap = Bitmap.createBitmap(currentPagePdf.getWidth(), currentPagePdf.getHeight(),
                        Bitmap.Config.ARGB_8888);
                currentPagePdf.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                bitmaps.add(bitmap);


            }
        }

        return bitmaps;
    }


    private void openRender() throws IOException {

        if (!pdfFile.exists() && pdfFileName != null){
            InputStream asset = context.getAssets().open(pdfFileName);
            FileOutputStream outputStream = new FileOutputStream(pdfFile);
            final byte[] buffer = new byte[1024];
            int size;
            while ((size = asset.read(buffer)) != -1) {
                outputStream.write(buffer, 0, size);
            }
            asset.close();
            outputStream.close();
        }

        parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);

        if (parcelFileDescriptor != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                pdfRenderer = new PdfRenderer(parcelFileDescriptor);
            }
        }
    }

    private void showPage(int index) throws NullPointerException {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (onGetPdfPageSize != null){
                onGetPdfPageSize.pdfPageSize(pdfRenderer.getPageCount());
            }

            if (pdfRenderer.getPageCount() <= index) {
                return;
            }

            if (currentPage != null) {
                currentPage.close();
            }

            currentPage = pdfRenderer.openPage(index);

            Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(),
                    Bitmap.Config.ARGB_8888);

            currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            if (listener != null){
                listener.onRenderPdf(currentPage.getIndex(),bitmap);
            }

            updateUi();
        }
    }

    private void closeRenderer() throws IOException {

        if (pdfRenderer != null && parcelFileDescriptor != null
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (currentPage != null) {
                    currentPage.close();
            }
            pdfRenderer.close();
            parcelFileDescriptor.close();
        }
    }


    private void updateUi() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int index = currentPage.getIndex();
            int pageCount = pdfRenderer.getPageCount();
            prePage = (0 != index);
            nextPage = (index + 1 < pageCount);
        }
    }

    public interface OnPdfViewerListener {
        void onRenderPdf(@NonNull int page,@NonNull Bitmap bitmap);
        void onException(@NonNull Exception e);
    }

    public interface OnGetPdfPageSize {
        void pdfPageSize(@NonNull int pageSize);
    }
}
