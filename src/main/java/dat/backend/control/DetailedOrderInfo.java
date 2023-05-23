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

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "detailed-order-info", value = "/detailed-order-info")
public class DetailedOrderInfo extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int orderId = Integer.parseInt(request.getParameter("orderId"));

        if (orderId == 0) {
            orderId = (int) request.getAttribute("orderId");
        }

        

        String code = request.getParameter("fromJsp");
        String from = request.getHeader("referer").split("/")[request.getHeader("referer").split("/").length - 1];
        try {
            CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(orderId, connectionPool);
            request.setAttribute("carportOrder", carportOrder);
            request.setAttribute("load", "true");
            if (code.equalsIgnoreCase("customer")) {
                request.getRequestDispatcher("WEB-INF/seeCustomerOrders.jsp").forward(request, response);
            } else {
                if (from.equalsIgnoreCase("see-employee-orders")) {
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