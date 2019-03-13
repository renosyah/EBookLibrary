package com.example.renosyahputra.ebooklibrary

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.renosyahputra.pdfbrowserlibrary.PdfBrowserInit
import com.example.renosyahputra.pdfviewerlibrary.PdfViewerInit
import kotlinx.android.synthetic.main.activity_main.*

class Main2Activity : AppCompatActivity(),
    View.OnClickListener,
    PdfViewerInit.OnPdfViewerListener,
    PdfBrowserInit.OnPdfBrowserListener {

    lateinit var context: Context
    lateinit var pdfViewerInit : PdfViewerInit

    var listBitmap = ArrayList<Bitmap>()
    var index = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initWidget()
    }

    private fun initWidget(){
        context = this@Main2Activity

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
                try {
                    index--
                    pdfImage.setImageBitmap(listBitmap.get(index))
                }catch (e  :IndexOutOfBoundsException){

                }

            }
            nextPage -> {

                try {
                    index++
                    pdfImage.setImageBitmap(listBitmap.get(index))
                }catch (e  :IndexOutOfBoundsException){

                }

            }
        }
    }

    override fun onChoosePdf(pdfBrowserData: PdfBrowserInit.PdfBrowserData) {
        pdfViewerInit = PdfViewerInit.newInstance()
            .setContext(context)
            .setPdfFile(pdfBrowserData.file)
            .setPdfFileName(pdfBrowserData.filename)
            .setOnPdfViewerListener(this)
            .openPdf()

        listBitmap = pdfViewerInit.allRenderedPdf

    }

    override fun onBrowsingFinish() {
        Toast.makeText(context,"Finish browse pdf!", Toast.LENGTH_SHORT).show()
    }


    override fun onRenderPdf(bitmap: Bitmap) {
        pdfImage.setImageBitmap(bitmap)
    }

    override fun onPageIndex(page: Int) {
        Toast.makeText(context,"In Page : ${page}", Toast.LENGTH_SHORT).show()
    }

    override fun onException(e: Exception) {
        Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
    }
}
