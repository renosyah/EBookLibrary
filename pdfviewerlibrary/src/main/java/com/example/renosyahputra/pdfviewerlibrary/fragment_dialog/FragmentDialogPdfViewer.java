package com.example.renosyahputra.pdfviewerlibrary.fragment_dialog;

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
import com.example.renosyahputra.pdfviewerlibrary.PdfViewer;
import com.example.renosyahputra.pdfviewerlibrary.R;
import com.example.renosyahputra.pdfviewerlibrary.activity_pdf_viewer.ActivityPdfViewer;
import com.example.renosyahputra.pdfviewerlibrary.pdfViewerInit.PdfViewerDataObj;

public class FragmentDialogPdfViewer extends DialogFragment {

        private Context context;
        private PdfViewerDataObj pdfViewerDataObj;
        private PdfViewer.OnPdfVewerListener listener;
        private int RequestCode = 236;

        public void setListener(PdfViewer.OnPdfVewerListener listener) {
            this.listener = listener;
        }

        public void setPdfViewerDataObj(PdfViewerDataObj pdfViewerDataObj) {
            this.pdfViewerDataObj = pdfViewerDataObj;
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

            Intent i = new Intent(context, ActivityPdfViewer.class);
            i.putExtra("data",pdfViewerDataObj);
            startActivityForResult(i, RequestCode);

            super.onViewCreated(view, savedInstanceState);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            getDialog().dismiss();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            listener.onFinishView();
        }

}
