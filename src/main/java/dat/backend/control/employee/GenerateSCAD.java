package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.entities.PartsList;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.print3d.Model3D;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}