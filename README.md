# EBookLibrary


## PDF file Browser
library ini berguna untuk browsing file pdf dan mengambil file pdf dari gallery
berikut tampilannya : 


![GitHub Logo](/image/pdf_browser.jpg)





## PDF file viewer
library ini berguna untuk melihat file pdf dalam bentuk gambar
berikut tampilannya :


![GitHub Logo](/image/pdf_viewer.jpg)





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

#

