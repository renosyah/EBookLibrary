package com.example.renosyahputra.ebooklibrary

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.renosyahputra.pdfbrowserlibrary.PdfBrowserInit
import com.example.renosyahputra.pdfviewerlibrary.pdfViewerInit.PdfViewerInit
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
    View.OnClickListener,
    PdfViewerInit.OnPdfViewerListener,
    PdfBrowserInit.OnPdfBrowserListener{



    lateinit var context: Context
    var pdfViewerInit = PdfViewerInit.newInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidget()
    }

    private fun initWidget(){
        context = this@MainActivity

        previewPage.setOnClickListener(this)
        nextPage.setOnClickListener(this)
        pdfImage.setOnClickListener(this)

        PdfBrowserInit.newInstance()
            .setContext(context)
            .setOnPdfBrowserListener(this)
            .browse()

    }



    override fun onClick(v: View?) {
        when (v){
            pdfImage -> {

                PdfBrowserInit.newInstance()
                    .setContext(context)
                    .setOnPdfBrowserListener(this)
                    .browse()

            }
            previewPage ->{
                pdfViewerInit.previousPage()
            }
            nextPage -> {
                pdfViewerInit.nextPage()
            }
        }
    }

    override fun onChoosePdf(pdfBrowserData: PdfBrowserInit.PdfBrowserData) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(pdfBrowserData.file), "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }


        pdfViewerInit.closePdf()

        pdfViewerInit = PdfViewerInit.newInstance()
            .setContext(context)
            .setPdfFile(pdfBrowserData.file)
            .setPdfFileName(pdfBrowserData.filename)
            .setOnPdfViewerListener(this)
            .openPdf()

    }

    override fun onBrowsingFinish() {
        Toast.makeText(context,"Finish browse pdf!",Toast.LENGTH_SHORT).show()
    }

    override fun onRenderPdf(page: Int, bitmap: Bitmap) {
        pdfImage.setImageBitmap(bitmap)
        Toast.makeText(context,"In Page : ${page}",Toast.LENGTH_SHORT).show()
    }

    override fun onException(e: Exception) {
        Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
    }
}
