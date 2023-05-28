package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.Roof;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "partslist-to-pdf", value = "/partslist-to-pdf")
public class PartsListToPDF extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set content type to application/pdf
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=stykliste.pdf");

        // Get the parts-list from the request
        PartsList partsList = (PartsList) request.getSession().getAttribute("partslist");

        // Write the pdf to the response
        response.getOutputStream().write(generatePDFBytes(partsList));
    }

    public static byte[] generatePDFBytes(PartsList partsList) {
        // The byte array to return
        byte[] pdfBytes = null;
        // Separate the parts-list into individual lumber objects to simplify the code
        Lumber rafter = partsList.getRafter();
        Lumber pole = partsList.getPole();
        Lumber plate = partsList.getPlate();
        Roof roof = partsList.getRoof();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            String html = "<body>" +
                    "<table class=\"table table-striped table-bordered table-hover\">\n" +
                    "    <thead>\n" +
                    "       <tr>" +
                    "           <th> Dimensioner </th>\n" +
                    "           <th>Længde</th>\n" +
                    "           <th>Antal</th>\n" +
                    "           <th>Enhed</th>\n" +
                    "           <th>Beskrivelse</th>\n" +
                    "       </tr>" +
                    "   </thead>\n" +
                    "   <tbody>\n" +
                    "       <tr>\n" +
                    "           <td> " + rafter.getLumberType().getThickness() + "x" + rafter.getLumberType().getWidth() + "mm.\n" +
                    "            spærtre\n" +
                    "           </td>\n" +
                    "           <td>" + rafter.getLength() + "</td>\n" +
                    "           <td>" + partsList.getNumberOfRafters() + "</td>\n" +
                    "           <td>stk.</td>\n" +
                    "           <td>" + (rafter.getDescription().isPresent() ? rafter.getDescription().get() : "Dette er en spær") + "</td>\n" +
                    "       </tr>\n" +
                    "       <tr>\n" +
                    "           <td>" + pole.getLumberType().getThickness() + "x" + pole.getLumberType().getWidth() + "mm.\n" +
                    "            stolpe\n" +
                    "           </td>\n" +
                    "           <td>" + pole.getLength() + "</td>\n" +
                    "           <td>" + partsList.getNumberOfPoles() + "</td>\n" +
                    "           <td>stk.</td>\n" +
                    "           <td>" + (pole.getDescription().isPresent() ? pole.getDescription().get() : "Dette er en stolpe") + "</td>\n" +
                    "       </tr>\n" +
                    "       <tr>\n" +
                    "           <td>" + plate.getLumberType().getThickness() + "x" + plate.getLumberType().getWidth() + "mm.\n" +
                    "            rem\n" +
                    "           </td>\n" +
                    "           <td>" + plate.getLength() + "</td>\n" +
                    "           <td>" + partsList.getNumberOfPlates() + "</td>\n" +
                    "           <td>stk.</td>\n" +
                    "           <td>" + (plate.getDescription().isPresent() ? plate.getDescription().get() : "Dette er en rem") + "</td>\n" +
                    "       </tr>\n" +
                    "       <tr>\n" +
                    "           <td>" + roof.getDisplayName() + "</td>\n" +
                    "           <td>" + partsList.getLength() + "</td>\n" +
                    "           <td>" + partsList.getRoofArea() + "</td>\n" +
                    "           <td>m2</td>\n" +
                    "           <td>Dette er taget</td>\n" +
                    "       </tr>\n" +
                    "   </tbody>\n" +
                    "   </table>" +
                    "<table class=\"table table-striped table-bordered table-hover\">\n" +
                    "    <thead>\n" +
                    "       <tr>" +
                    "           <th>Pris på stykliste</th>" +
                    "       </tr>\n" +
                    "    </thead>\n" +
                    "    <tbody>\n" +
                    "       <tr>\n" +
                    "           <td>" + partsList.getFormattedPrice() + "</td>\n" +
                    "       </tr>\n" +
                    "    </tbody>\n" +
                    "</table>" +
                    "</body>";

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);

            // Get the pdf bytes from the output stream
            pdfBytes = outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfBytes;
    }
}