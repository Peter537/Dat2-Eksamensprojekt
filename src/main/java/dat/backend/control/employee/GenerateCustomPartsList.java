package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet's purpose is to redirect the employee to the generateCustomPartsList.jsp page if forwarded via "GET" and
 * to forward a certain order to the generateCustomPartsList.jsp page if forwarded via "POST".
 * The reason it forwards an order is so it can load those values into the partlist generator which makes it easy to edit the values
 * and update the specific order.
 */

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "generate-custom-partslist", value = "/generate-custom-partslist")
public class GenerateCustomPartsList extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/generateCustomPartsList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String edit = request.getParameter("edit");
            CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(Integer.parseInt(request.getParameter("orderId")), connectionPool);
            request.setAttribute("carportOrder", carportOrder);
            request.setAttribute("edit", edit);
            request.getRequestDispatcher("WEB-INF/generateCustomPartsList.jsp").forward(request, response);
        } catch (DatabaseException | NotFoundException e) {
            e.printStackTrace();
        }
    }
}