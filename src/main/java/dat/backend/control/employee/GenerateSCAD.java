package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.entities.PartsList;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.print3d.Model3D;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "GenerateSCAD", value = "/GenerateSCAD")
public class GenerateSCAD extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PartsList partsList = (PartsList) request.getSession().getAttribute("partslist");
        try {
            Model3D model3D = new Model3D(partsList);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        //Prepare package
        String[] paths = {"/OpenSCAD/View1.scad", "/OpenSCAD/View2.scad", "/OpenSCAD/View3.scad"};
        ServletOutputStream servletOutputStream = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(servletOutputStream);

        //Set headers
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"package.zip\"");

        //Iterate through files
        for (String filePath : paths) {
            File view = new File(filePath);
            try (FileInputStream fileInputStream = new FileInputStream(view)) {
                //Create zip entry
                ZipEntry zipEntry = new ZipEntry(view.getName());
                zipOutputStream.putNextEntry(zipEntry);

                //Write file to output stream
                byte[] bufferData = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(bufferData)) != -1) {
                    zipOutputStream.write(bufferData, 0, bytesRead);
                }

                //Close entry
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //Close streams
        zipOutputStream.close();
        servletOutputStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}