package dat.backend.control.customer;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.Roof;
import dat.backend.model.entities.item.ToolRoom;
import dat.backend.model.entities.user.Address;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.RoofFacade;
import dat.backend.model.persistence.order.CarportOrderFacade;
import dat.backend.model.persistence.user.ZipFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "PartList", value = "/PartList")
public class CreateOrder extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("carportMinHeight") == null || request.getParameter("carportWidth") == null || request.getParameter("carportLength") == null) {
            request.setAttribute("errormessage", "Please enter all dimensions");
            request.getRequestDispatcher("WEB-INF/carportFormula.jsp").forward(request, response);
            return;
        }

        int height = Integer.parseInt(request.getParameter("carportMinHeight"));
        int width = Integer.parseInt(request.getParameter("carportWidth"));
        int length = Integer.parseInt(request.getParameter("carportLength"));
        try {
            PartsList partsList = new PartsList(height, length, width, connectionPool);
            request.setAttribute("partsList", partsList);

            // load in info
            Customer customer = (Customer) request.getSession().getAttribute("user");
            int streetId = Integer.parseInt(request.getParameter("customerAddress"));
            Address address;
            if (streetId == 0) {
                int zipCode = Integer.parseInt(request.getParameter("customerZip"));
                Zip zip = ZipFacade.getZipByZipCode(zipCode, connectionPool);
                address = new Address(request.getParameter("customerAddressOther"), zip);
                if (address.getStreet() == null || address.getStreet().isEmpty()) {
                    request.setAttribute("errormessage", "Please enter an address");
                    request.getRequestDispatcher("WEB-INF/carportFormula.jsp").forward(request, response);
                    return;
                }
            } else {
                if (!customer.getAddress(streetId).isPresent()) {
                    request.setAttribute("errormessage", "Please enter an address");
                    request.getRequestDispatcher("WEB-INF/carportFormula.jsp").forward(request, response);
                    return;
                }

                address = customer.getAddress(streetId).get();
            }

            Roof roof = RoofFacade.getRoofById(1, connectionPool);
            Optional<ToolRoom> toolRoom = Optional.empty();
            Optional<String> remarks = Optional.ofNullable(request.getParameter("remarks"));

            // info end
            String successMessage = "good job";
            CarportOrderFacade.create(customer, address, width, length, height, roof, toolRoom, remarks, connectionPool);
            request.setAttribute("partsListSuccess", successMessage);
            request.getRequestDispatcher("WEB-INF/carportFormula.jsp").forward(request, response);
        } catch (DatabaseException | IllegalArgumentException | NotFoundException | ValidationException e) {
            e.printStackTrace();
            Logger.getLogger("CreateOrder").warning(e.getMessage());
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/carportFormula.jsp").forward(request, response);
        }
    }
}