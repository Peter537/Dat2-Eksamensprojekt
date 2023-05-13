package dat.backend.control;

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

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "PartList", value = "/PartList")
public class CreateOrder extends HttpServlet {

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

            //load in info
            Customer customer = (Customer) request.getSession().getAttribute("user");

            String street = request.getParameter("customerAddress");
            int tempZip = Integer.parseInt(request.getParameter("customerZip"));

            Zip zip = ZipFacade.getZipByZipCode(tempZip, connectionPool);


           if (street == null || street.isEmpty()) {
               street = request.getParameter("customerAddressOther");

               if(street == null || street.isEmpty()) {
                   request.setAttribute("errormessage", "Please enter an address");
                   request.getRequestDispatcher("WEB-INF/carportFormula.jsp").forward(request, response);
                   return;
               }

            }
            Address address = new Address(street, zip);
           Roof roof = RoofFacade.getRoofById(1, connectionPool);
           Optional<ToolRoom> toolRoom = Optional.empty();
           Optional<String> remarks = Optional.ofNullable(request.getParameter("remarks"));


            //infoend




            String successMessage = "good job";

            CarportOrderFacade.createCarportOrder(customer, address, width, length, height, roof, toolRoom, remarks, connectionPool);

            request.setAttribute("partsListSuccess", successMessage);
            request.getRequestDispatcher("WEB-INF/carportFormula.jsp").forward(request, response);

        } catch (DatabaseException | IllegalArgumentException | NotFoundException | ValidationException e) {
            e.printStackTrace();
        }


    }
}
