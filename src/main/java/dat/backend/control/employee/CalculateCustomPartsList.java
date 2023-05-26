package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.RoofFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "calculate-custom-partslist", value = "/calculate-custom-partslist")
public class CalculateCustomPartsList extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String validCheck = request.getParameter("valid");
        String orderIdString = request.getParameter("orderId");
        float orderId = 0;
        float length = 0;
        float width = 0;
        float height = 0;
        if (validCheck != null) {
            orderId = Float.parseFloat(orderIdString);
            length = Float.parseFloat(request.getParameter("carportLength"));
            width = Float.parseFloat(request.getParameter("carportWidth"));
            height = Float.parseFloat(request.getParameter("carportHeight"));
        }

        try {
            Roof roof = RoofFacade.getRoofById(1, connectionPool);

            PartsList partsList = new PartsList((int) height, (int) length, (int) width, roof, connectionPool);
            request.setAttribute("partslist", partsList);
            request.setAttribute("orderId", orderId);
            request.setAttribute("edit", "edit");
            request.setAttribute("valid", "valid");

            // for override
            request.setAttribute("id", orderId);
            request.setAttribute("length", length);
            request.setAttribute("width", width);
            request.setAttribute("height", height);
            request.setAttribute("price", partsList.getTotalPrice());

            //
            request.getRequestDispatcher("WEB-INF/calculatedCustompartlistFRAME.jsp").forward(request, response);
        } catch (DatabaseException | NotFoundException | IllegalArgumentException e) {
            request.setAttribute("msg", e.getMessage());
            request.getRequestDispatcher("WEB-INF/calculatedCustompartlistFRAME.jsp").forward(request, response);
        }
    }
}