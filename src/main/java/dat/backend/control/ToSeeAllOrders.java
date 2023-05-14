package dat.backend.control;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "ToSeeAllOrders", value = "/ToSeeAllOrders")
public class ToSeeAllOrders extends HttpServlet {


    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            CarportOrder order = CarportOrderFacade.getCarportOrderById(1, connectionPool);

            request.setAttribute("order", order);

        } catch (DatabaseException | NotFoundException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("WEB-INF/seeAllOrders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
