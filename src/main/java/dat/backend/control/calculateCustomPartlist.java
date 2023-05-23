package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.Roof;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.RoofFacade;
import dat.backend.model.persistence.order.CarportOrderFacade;
import dat.backend.model.services.PartsListCalculator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "calculateCustomPartlist", value = "/calculateCustomPartlist")
public class calculateCustomPartlist extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String validCheck = request.getParameter("valid");

        int orderId = 0;
        int length = 0;
        int width = 0;
        int height = 0;

        if (validCheck != null) {
            orderId = Integer.parseInt(request.getParameter("orderId"));
            if (orderId == 0) {
                orderId = 0;
            }
            length = Integer.parseInt(request.getParameter("carportLength"));
            width = Integer.parseInt(request.getParameter("carportWidth"));
            height = Integer.parseInt(request.getParameter("carportHeight"));
        }

        if (orderId != 0) {
            try {
                Roof roof = RoofFacade.getRoofById(1, connectionPool);

                PartsList partsList = new PartsList(height, length, width, roof, connectionPool);
                request.setAttribute("partlist", partsList);
                request.getRequestDispatcher("WEB-INF/calculatedCustompartlistFRAME.jsp").forward(request, response);

            } catch (DatabaseException | NotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String validCheck = request.getParameter("valid");

        float orderId = 0;
        float length = 0;
        float width = 0;
        float height = 0;

        if (validCheck != null) {
            orderId = Integer.parseInt(request.getParameter("orderId"));
            if (orderId == 0) {
                orderId = 0;
            }
            length = Float.parseFloat(request.getParameter("carportLength"));
            width = Float.parseFloat(request.getParameter("carportWidth"));
            height = Float.parseFloat(request.getParameter("carportHeight"));
        }

        if (orderId != 0) {
            try {
                Roof roof = RoofFacade.getRoofById(1, connectionPool);

                PartsList partsList = new PartsList((int)height, (int)length, (int)width, roof, connectionPool);
                request.setAttribute("partslist", partsList);
                request.getRequestDispatcher("WEB-INF/calculatedCustompartlistFRAME.jsp").forward(request, response);

            } catch (DatabaseException | NotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
