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

/**
 * This servlet's purpose is to generate a zip file containing the 3D models of the parts in the partslist.
 */
@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "GenerateSCAD", value = "/GenerateSCAD")
public class GenerateSCAD extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PartsList partsList = (PartsList) request.getSession().getAttribute("partslist");
        try {
            Model3D model3D = new Model3D(partsList); //This just needs to be called to generate the files
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        //Prepare package
        String[] paths = {"/OpenSCAD/View1.scad", "/OpenSCAD/View2.scad", "/OpenSCAD/View3.scad"}; //These paths are hardcoded as we know what they are called and where they are stored
        ServletOutputStream servletOutputStream = response.getOutputStream(); //Get output stream from response. This is where we will write the zip file to
        ZipOutputStream zipOutputStream = new ZipOutputStream(servletOutputStream); //Create zip output stream. This is where we will write the files to

        //Set headers
        response.setContentType("application/zip"); //We set the content type to zip as that is the expected file type
        response.setHeader("Content-Disposition", "attachment; filename=\"package.zip\""); //Set filename

        //Iterate through files
        for (String filePath : paths) {

            File view = new File(filePath); //Create a temporary file object from the path

            try (FileInputStream fileInputStream = new FileInputStream(view)) {
                //Create zip entry
                ZipEntry zipEntry = new ZipEntry(view.getName());
                zipOutputStream.putNextEntry(zipEntry); //Add entry to zip output stream

                //Write file to output stream
                byte[] bufferData = new byte[4096]; //4096 is an arbitrary buffer. Can be increased if necessary
                int bytesRead; //Number of bytes read from file
                while ((bytesRead = fileInputStream.read(bufferData)) != -1) {
                    zipOutputStream.write(bufferData, 0, bytesRead); //Write to zip output stream from buffer
                }

                //Close entry. We don't want to close the stream, as we are writing multiple files
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //Close streams. It is important to close the streams, as otherwise the file will not be written correctly and they will corrupt
        zipOutputStream.close();
        servletOutputStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}