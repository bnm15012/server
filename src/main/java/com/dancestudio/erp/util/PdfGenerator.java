package com.dancestudio.erp.util;

import com.lowagie.text.DocumentException;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfGenerator {

    public ByteArrayOutputStream generatePDF(String mergedHtml, PDRectangle pageSize) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(mergedHtml);
        renderer.layout();

        renderer.createPDF(outputStream);

        return outputStream;
    }
}
