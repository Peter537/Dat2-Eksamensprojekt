package dat.backend.control;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Person;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "DetailedOrderInfo", value = "/DetailedOrderInfo")
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
        String code = request.getParameter("fromJsp");

        try {
            CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(orderId, connectionPool);
            request.setAttribute("carportOrder", carportOrder);
            request.setAttribute("load", "true");
            if (code.equalsIgnoreCase("customer")) {
                request.getRequestDispatcher("WEB-INF/customerOrders.jsp").forward(request, response);
            }else {
                request.getRequestDispatcher("WEB-INF/seeAllOrders.jsp").forward(request, response);
            }


        } catch (DatabaseException | NotFoundException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }
    }
}
