package com.example.renosyahputra.pdfviewerlibrary.pdfViewerInit;

import java.io.File;
import java.io.Serializable;

public class PdfViewerDataObj implements Serializable {
    public File file;
    public String filename;

    public PdfViewerDataObj() {
        super();
    }

    public PdfViewerDataObj(File file, String filename) {
        this.file = file;
        this.filename = filename;
    }
}
