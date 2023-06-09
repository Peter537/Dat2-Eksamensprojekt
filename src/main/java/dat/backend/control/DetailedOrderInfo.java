package dat.backend.control;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.PartsList;
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
 * This servlet loads in a specific order from the database using an ID forwarded by the jsp
 * and shows a more detailed view showing all information regarding said order.
 * Depending on what the attribute "fromJsp" is set to, the servlet will forward the user to the appropriate page.
 * This is due to the fact that this servlet is used by both the customer and employee site.
 */
@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "detailed-order-info", value = "/detailed-order-info")
public class DetailedOrderInfo extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = (int) Float.parseFloat(request.getParameter("orderId"));
        String message = request.getParameter("cancel");
        String fromJsp = request.getParameter("fromJsp");
        if (fromJsp == null) { //This variable is occasionally null, so we need to check for it
            String home = request.getSession().getAttribute("myhome").toString();
            request.getRequestDispatcher(home).forward(request, response);
        }

        request.setAttribute("cancel", message);
        try {
            CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(orderId, connectionPool);
            request.setAttribute("carportOrder", carportOrder);
            request.setAttribute("load", "true");
            if (fromJsp.equalsIgnoreCase("customer")) {
                request.getRequestDispatcher("WEB-INF/seeCustomerOrders.jsp").forward(request, response);
            } else {
                if (fromJsp.equalsIgnoreCase("see-employee-orders")) {
                    request.setAttribute("from", "see-employee-orders");
                } else {
                    request.setAttribute("from", "see-all-orders");
                }
                request.getSession().setAttribute("partslist", new PartsList(carportOrder, connectionPool));
                request.getRequestDispatcher("WEB-INF/seeAllOrders.jsp").forward(request, response);
            }
        } catch (DatabaseException | NotFoundException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }
    }
}