package com.canpekdemir;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.charset.Charset;

public class PdfGenerator {

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String ARIAL_REGULAR_PATH = "font/arial-unicode-regular.ttf";
    public static final String ARIAL_BOLD_PATH = "font/arial-unicode-bold.ttf";
    public static final String ARIAL_ITALIC_PATH = "font/arial-unicode-italic.ttf";

    public static void generate(String contentBody, String pdfOutputPath) throws IOException, DocumentException {
        String htmlContent = appendHeaderAndFooterTagsToHtmlContent(contentBody);
        ByteArrayOutputStream outputStream = cleanHtmlTags(htmlContent);
        createPdf(outputStream,pdfOutputPath);
    }

    private static String appendHeaderAndFooterTagsToHtmlContent(String body) {
        String htmlContent = "<html>" +
                "<head>" +
                "<title></title>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "    <style>" +
                "        body { font-family: 'Arial Unicode MS';}\n" +
                "    </style>" +
                "</head>" +
                "<body> %s </body></html>";

        return String.format(htmlContent, body);
    }

    private static ByteArrayOutputStream cleanHtmlTags(String htmlContent) {
        InputStream inputStream = new ByteArrayInputStream(htmlContent.getBytes());

        Tidy tidy = new Tidy();
        tidy.setShowWarnings(false);
        tidy.setXmlTags(false);
        tidy.setInputEncoding(DEFAULT_CHARSET);
        tidy.setOutputEncoding(DEFAULT_CHARSET);
        tidy.setXHTML(true);
        tidy.setMakeClean(true);
        Document xmlDoc = tidy.parseDOM(inputStream, null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.pprint(xmlDoc, outputStream);
        return outputStream;
    }

    private static void createPdf(ByteArrayOutputStream outputStream, String pdfOutputPath) throws DocumentException, IOException {
        ITextRenderer renderer = new ITextRenderer();

        renderer.getFontResolver().addFont(ARIAL_REGULAR_PATH,
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.getFontResolver().addFont(ARIAL_BOLD_PATH,
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.getFontResolver().addFont(ARIAL_ITALIC_PATH,
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        renderer.setDocumentFromString(new String(outputStream.toByteArray(), Charset.forName(DEFAULT_CHARSET)));
        renderer.layout();

        OutputStream pdfOutputStream = new FileOutputStream(pdfOutputPath);
        renderer.createPDF(pdfOutputStream);

        pdfOutputStream.close();
    }

}
