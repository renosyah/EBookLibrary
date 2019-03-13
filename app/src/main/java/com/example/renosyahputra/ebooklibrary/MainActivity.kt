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



class MainActivity : AppCompatActivity(),
    View.OnClickListener,
    PdfViewerInit.OnPdfViewerListener,
    PdfBrowserInit.OnPdfBrowserListener{


    lateinit var context: Context
    lateinit var pdfViewerInit : PdfViewerInit


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

                pdfViewerInit.closePdf()

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

        var pdfViewerInit = PdfViewerInit.newInstance()

        pdfViewerInit = PdfViewerInit.newInstance()
            .setContext(context)
            .setPdfFile(pdfBrowserData.file)
            .setPdfFileName(pdfBrowserData.filename)
            .setOnPdfViewerListener(object : PdfViewerInit.OnPdfViewerListener{
                override fun onRenderPdf(bmp : Bitmap) {

                    // halaman pdf akan di render menjadi bitmap
                    // letakkan kode anda disini untuk
                    // mengelola/menampilkan gambar

                }

                override fun onPageIndex(page : Int) {

                    // page yg sedang dipilih

                }

                override fun onException(e: java.lang.Exception) {

                    // exception

                }

            }).openPdf()


            pdfViewerInit.previousPage()


            pdfViewerInit.nextPage()


            pdfViewerInit.closePdf()
    }

    override fun onBrowsingFinish() {
        Toast.makeText(context,"Finish browse pdf!",Toast.LENGTH_SHORT).show()
    }


    override fun onRenderPdf(bitmap: Bitmap) {
        pdfImage.setImageBitmap(bitmap)
    }

    override fun onPageIndex(page: Int) {
        Toast.makeText(context,"In Page : ${page}",Toast.LENGTH_SHORT).show()
    }

    override fun onException(e: Exception) {
        Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
    }
}
