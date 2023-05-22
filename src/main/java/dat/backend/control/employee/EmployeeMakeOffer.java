package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "employee-make-offer", value = "/employee-make-offer")
public class EmployeeMakeOffer extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        float priceOffer = Float.parseFloat(request.getParameter("priceOffered"));
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            CarportOrder carport = CarportOrderFacade.getCarportOrderById(orderId, connectionPool);

            CarportOrderFacade.makeOffer(carport, priceOffer, connectionPool);

            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("DetailedOrderInfo").forward(request, response);
        } catch (DatabaseException | NotFoundException | ValidationException e) {
            e.printStackTrace();
        }


    }
}