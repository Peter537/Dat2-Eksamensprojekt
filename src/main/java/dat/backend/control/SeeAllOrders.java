package dat.backend.control;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "see-all-orders", value = "/see-all-orders")
public class SeeAllOrders extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<CarportOrder> carportOrders = CarportOrderFacade.getAllCarportOrders(connectionPool);
            request.setAttribute("carportOrders", carportOrders);
            request.getRequestDispatcher("WEB-INF/seeAllOrders.jsp").forward(request, response);
        } catch (DatabaseException | NotFoundException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }
    }
}