# pdf-generator

This project helps you to create pdf files from html content. It also clears any html errors and does not corrupt for non-english alphabet characters.

This project uses jtidy and core-renderer as dependency libraries. core-renderer uses old itext dependency 2.0.8 


# Usage

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