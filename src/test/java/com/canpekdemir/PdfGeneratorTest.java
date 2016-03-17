package com.canpekdemir;

import com.lowagie.text.DocumentException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class PdfGeneratorTest {

    @Test
    public void should_generate_pdf() throws IOException, DocumentException {
        String outputPath = "output.pdf";

        String contentBody = "<h2 style=\"color:red;\">Red style header</h2>\n" +
                "<p><b>This is a paragraph</b></p>\n" +
                "<table style=\"width:100%\">\n" +
                "  <tr>\n" +
                "    <td>Can</td>\n" +
                "    <td>Smith</td> \n" +
                "    <td>50</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Eve</td>\n" +
                "    <td>Çağrı</td> \n" +
                "    <td>94</td>\n" +
                "  </tr>\n" +
                "</table>";

        PdfGenerator.generate(contentBody,outputPath);

        File outputFile = new File(outputPath);
        assertThat(outputFile).isFile().exists();

        outputFile.delete();
    }
}