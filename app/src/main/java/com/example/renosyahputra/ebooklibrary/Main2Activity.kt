package com.example.renosyahputra.ebooklibrary

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.renosyahputra.pdfbrowserlibrary.PdfBrowserInit
import com.example.renosyahputra.pdfviewerlibrary.PdfViewer
import kotlinx.android.synthetic.main.activity_main.*

class Main2Activity : AppCompatActivity(),
    View.OnClickListener,
    PdfBrowserInit.OnPdfBrowserListener,
    PdfViewer.OnPdfVewerListener {

    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initWidget()
    }

    private fun initWidget(){
        context = this@Main2Activity
        pdfImage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v){
            pdfImage -> {

                PdfBrowserInit.newInstance()
                    .setContext(context)
                    .setEnableThumbnail(true)
                    .setOnPdfBrowserListener(this)
                    .browse()

            }
        }
    }

    override fun onChoosePdf(pdfBrowserData: PdfBrowserInit.PdfBrowserData) {

        PdfViewer.newInstance()
            .setContext(context)
            .setPdfFile(pdfBrowserData.file)
            .setOnPdfVewerListener(this)
            .viewPDF()
    }

    override fun onBrowsingFinish() {
        Toast.makeText(context,"Finish browse pdf!", Toast.LENGTH_SHORT).show()
    }
    override fun onFinishView() {
        Toast.makeText(context,"Finish view pdf!", Toast.LENGTH_SHORT).show()
    }
}
