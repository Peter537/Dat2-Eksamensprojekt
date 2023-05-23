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
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "detailed-search-see-all-orders", value = "/detailed-search-see-all-orders")
public class DetailedSearchSeeAllOrders extends HttpServlet {

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
        try {
            List<CarportOrder> carportOrders = CarportOrderFacade.getAllCarportOrders(connectionPool);
            AtomicBoolean isEmptySearchFields = new AtomicBoolean(false);
            List<CarportOrder> orders = carportOrders.stream()
                    .filter(carportOrder -> {
                        if (request.getParameter("searchId") != null && !request.getParameter("searchId").isEmpty()) {
                            int id = Integer.parseInt(request.getParameter("searchId"));
                            if (carportOrder.getId() == id) {
                                return true;
                            }
                        }

                        if (request.getParameter("searchCustomerEmail") != null && !request.getParameter("searchCustomerEmail").isEmpty()) {
                            return carportOrder.getCustomer().getEmail().equals(request.getParameter("searchCustomerEmail"));
                        }

                        isEmptySearchFields.set(true);
                        return false;
                    })
                    .collect(Collectors.toList());

            if (orders.isEmpty() && isEmptySearchFields.get()) {
                orders.addAll(carportOrders);
            }

            request.setAttribute("carportOrders", orders);
            request.getRequestDispatcher("WEB-INF/seeAllOrders.jsp").forward(request, response);
        } catch (DatabaseException | NotFoundException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }
    }
}