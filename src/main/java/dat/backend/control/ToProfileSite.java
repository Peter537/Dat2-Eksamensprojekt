package dat.backend.control;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;
import dat.backend.model.persistence.order.OrderStatusFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "ToProfileSite", value = "/ToProfileSite")
public class ToProfileSite extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Customer customer = (Customer) request.getSession().getAttribute("user");


        try {




            List<CarportOrder> orderList = CarportOrderFacade.getCarportOrdersByCustomer(customer, connectionPool);

            if (orderList.size() == 0) {
                request.setAttribute("noOrder", "Ingen odrer fundet");
                request.getRequestDispatcher("/WEB-INF/profileSite.jsp").forward(request, response);
                return;
            }

            CarportOrder order = orderList.get(orderList.size()-1);
            request.setAttribute("order", order);
        } catch (DatabaseException | ValidationException | NotFoundException e) {
            e.printStackTrace();
        }


        request.getRequestDispatcher("/WEB-INF/profileSite.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/profileSite.jsp").forward(request, response);
    }
}