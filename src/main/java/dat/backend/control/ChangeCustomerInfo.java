package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.user.Address;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.user.CustomerFacade;
import dat.backend.model.persistence.user.ZipFacade;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@WebServlet(name = "change-customer-info", value = "/change-customer-info")
public class ChangeCustomerInfo extends HttpServlet {

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

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        Optional<String> phone = Optional.ofNullable(request.getParameter("phone"));
        String city = request.getParameter("city");


        saveAddresses(customer, request);
        changeName(customer, request);
        changePassword(customer, request);
        changePersonPhoneNumber(customer, request);

        request.getRequestDispatcher("WEB-INF/profileSite.jsp").forward(request, response);


    }

    private void saveAddresses(Customer customer, HttpServletRequest request) {

        // for loop 1-3
        int zipcode = 0;
        for (int i = 1; i <= 3; i++) {
            String street = request.getParameter("street" + i);

            String zipTest = request.getParameter("zipCode" + i);

            if (zipTest != null && !zipTest.isEmpty()) {
                zipcode = Integer.parseInt(zipTest);

            Zip zip = null;
            if (zipcode != 0 && !street.isEmpty() || street != null) {
                try {
                    zip = ZipFacade.getZipByZipCode(zipcode, connectionPool);

                    CustomerFacade.updateAddress(customer, i, street, zip, connectionPool);

                } catch (DatabaseException | NotFoundException e) {
                    request.setAttribute("errormessage", "zip kunne ikke findes");
                }
            }
        }

        }

    }

    public void changeName(Customer customer, HttpServletRequest request) {

        String name = request.getParameter("name");

        if(name != null && !name.isEmpty()) {
            try {
                CustomerFacade.updateName(customer, name, connectionPool);
            } catch (DatabaseException | ValidationException e) {
                request.setAttribute("errormessage", "navnet kunne til dette");
            }
        }
    }

    public void changePassword(Customer customer, HttpServletRequest request) {

        String password = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (password != null && !password.isEmpty() && password.equals(confirmPassword)) {
            try {
                CustomerFacade.updatePassword(customer, password, connectionPool);
            } catch (DatabaseException | ValidationException e) {
                request.setAttribute("errormessage", "password kunne ikke opdateres");
            }
        }
    }

    public void changePersonPhoneNumber(Customer customer, HttpServletRequest request) {

        String phone = request.getParameter("newPhoneNumber");

        if (phone != null && !phone.isEmpty()) {
            try {
                CustomerFacade.updatePhoneNumber(customer, phone, connectionPool);
            } catch (DatabaseException | ValidationException e) {
                request.setAttribute("errormessage", "telefonnummer kunne ikke opdateres");
            }
        }
    }


}
