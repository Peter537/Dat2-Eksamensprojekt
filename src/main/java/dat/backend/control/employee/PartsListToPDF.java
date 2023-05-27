package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "partslist-to-pdf", value = "/partslist-to-pdf")
public class PartsListToPDF extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=table.pdf");

        try (OutputStream outputStream = response.getOutputStream()) {
            String html = "<table class=\"table table-striped table-bordered table-hover\">\n" +
                    "    <thead>\n" +
                    "    <tr><th>Dimensioner</th>\n" +
                    "    <th>Længde</th>\n" +
                    "    <th>Antal</th>\n" +
                    "    <th>Enhed</th>\n" +
                    "    <th>Beskrivelse</th>\n" +
                    "    </tr></thead>\n" +
                    "    <tbody>\n" +
                    "    <tr>\n" +
                    "        <td>45.0x125.0 mm.\n" +
                    "            spærtre\n" +
                    "        </td>\n" +
                    "        <td>480</td>\n" +
                    "        <td>6</td>\n" +
                    "        <td>stk.</td>\n" +
                    "        <td>Dette er en spær</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>97.0x97.0 mm.\n" +
                    "            stolpe\n" +
                    "        </td>\n" +
                    "        <td>480</td>\n" +
                    "        <td>4</td>\n" +
                    "        <td>stk.</td>\n" +
                    "        <td>Dette er en stolpe</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>45.0x125.0 mm.\n" +
                    "            rem\n" +
                    "        </td>\n" +
                    "        <td>480</td>\n" +
                    "        <td>2</td>\n" +
                    "        <td>stk.</td>\n" +
                    "        <td>Dette er en rem</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>Plastik tag</td>\n" +
                    "        <td>300</td>\n" +
                    "        <td>6</td>\n" +
                    "        <td>m2</td>\n" +
                    "        <td>Dette er taget</td>\n" +
                    "    </tr>\n" +
                    "    </tbody>\n" +
                    "</table>"; //TODO: Change so it has the actual values

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}