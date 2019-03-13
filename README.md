# EBookLibrary


## PDF file viewer
library ini berguna untuk melihat file pdf dalam bentuk gambar
berikut tampilannya :


![GitHub Logo](/image/pdf_viewer.jpg)




## PDF file Browser
library ini berguna untuk browsing file pdf dan mengambil file pdf dari gallery
berikut tampilannya : 


![GitHub Logo](/image/pdf_browser.jpg)





cara pakai : 

* instalaltion : 

tambahkan ke build.gradle :
```

	allprojects {
    		repositories {
        		maven { url "https://dl.bintray.com/renosyah/maven" }
        		...
       			...
    		}
	}

```


tambahkan ke app.gradle :
```

	dependencies {
    		...	
    		implementation 'com.github.renosyah:PDF-Viewer-Library:1.0.0'
		implementation 'com.github.renosyah:PDF-Browser-Library:1.0.0'
	}

```

* browsing file pdf dari galery : 

```

        PdfBrowserInit.newInstance()
            .setContext(this)
            .setOnPdfBrowserListener(object : PdfBrowserInit.OnPdfBrowserListener{
                override fun onChoosePdf(pdf : PdfBrowserInit.PdfBrowserData) {
                    
                    // pada saat file pdf dipilih
                    // letakkan kode anda untuk mengelola file pdf
                    
                }

                override fun onBrowsingFinish() {
                    
                    // pada saat browsing file pdf selesai
                    
                }

            }).browse()


```


* menggunakan pdf viewer modul : 

```

	// jadikan variable ini sebagai variable global
	// agar bisa diakses di activity
	var pdfViewerInit = PdfViewerInit.newInstance()
        
	// pastikan untuk menginisialisasi ulang
	// untuk jaga-jaga
        pdfViewerInit = PdfViewerInit.newInstance()
            .setContext(this)
            .setPdfFile(File("YOUR_PATH_TO_PDF"))
            .setPdfFileName("YOUR_PDF_FILENAME")
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


	    // fungsi yg dipanggil jika ingin ke halaman selanjutnya	
            pdfViewerInit.previousPage()

	    // fungsi yg dipanggil jika ingin ke halaman sebelumnya
            pdfViewerInit.nextPage()

	    // fungsi yg dipanggil pada saat ingin menutup pdf
	    // seperti pada saat activity selesai atau
	    // fragment dihancurkan
            pdfViewerInit.closePdf()


```


# [example link](https://github.com/renosyah/EBookLibrary/blob/master/app/src/main/java/com/example/renosyahputra/ebooklibrary/MainActivity.kt)










