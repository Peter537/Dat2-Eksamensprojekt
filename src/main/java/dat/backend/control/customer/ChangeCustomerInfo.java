package dat.backend.control.customer;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.user.CustomerFacade;
import dat.backend.model.persistence.user.ZipFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This servlet's purpose is to validate and change the customer's information and then show them back to the userpage with the changes made.
 * It will show popups to anything successful and also popups for unsuccessful changes.
 */
@MultipartConfig
@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "change-customer-info", value = "/change-customer-info")
public class ChangeCustomerInfo extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) { }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("user");
        this.saveAddresses(customer, request);
        this.changeName(customer, request);
        this.changePassword(customer, request);
        this.changePersonPhoneNumber(customer, request);
        this.changeProfilePicture(customer, request);
        request.getRequestDispatcher("WEB-INF/customerSite.jsp").forward(request, response);
    }

    private void saveAddresses(Customer customer, HttpServletRequest request) {
        // for loop 1-3
        for (int i = 1; i <= 3; i++) {
            String street = request.getParameter("street" + i);
            String zipCodeStr = request.getParameter("zipCode" + i);
            if (zipCodeStr != null && !zipCodeStr.isEmpty()) {
                int zipCode = Integer.parseInt(zipCodeStr);
                if (zipCode != 0 && !street.isEmpty() || street != null) {
                    try {
                        Zip zip = ZipFacade.getZipByZipCode(zipCode, connectionPool);
                        CustomerFacade.updateAddress(customer, i, street, zip, connectionPool);
                        request.setAttribute("addressSuccess", "adresse-ændring succesfuldt");
                    } catch (DatabaseException | NotFoundException e) {
                        request.setAttribute("errormessage", "zip kunne ikke findes");
                    }
                }
            }
        }
    }

    public void changeName(Customer customer, HttpServletRequest request) {
        String name = request.getParameter("name");
        if (name != null && !name.isEmpty()) {
            try {
                CustomerFacade.updateName(customer, name, connectionPool);
                request.setAttribute("nameSuccess", "navn-ændring succesfuldt");
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
                request.setAttribute("passwordSuccess", "password-ændring succesfuldt");
            } catch (DatabaseException | ValidationException e) {
                request.setAttribute("errormessage", "password kunne ikke opdateres, prøv igen.");
            }
        }
    }

    public void changePersonPhoneNumber(Customer customer, HttpServletRequest request) {
        String oldCustomerPhoneNumber = customer.getPersonalPhoneNumber().orElse("");
        String newPhoneNumber = request.getParameter("newPhoneNumber");
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty() && !oldCustomerPhoneNumber.equals(newPhoneNumber)) {
            try {
                CustomerFacade.updatePhoneNumber(customer, newPhoneNumber, connectionPool);
                request.setAttribute("phoneSuccess", "telefonnummer-ændring succesfuldt");
            } catch (DatabaseException | ValidationException e) {
                request.setAttribute("errormessage", "Telefonnummer kunne ikke opdateres. Telefonnummeret skal være 8 cifre.");
            }
        }
    }

    public void changeProfilePicture(Customer customer, HttpServletRequest request) throws ServletException, IOException {
        //Get the file uploaded by the user
        Part filePart = request.getPart("imageFile");
        if (filePart.getSize() == 0) {
            return;
        }

        FileInputStream fileContent = (FileInputStream) filePart.getInputStream();
        //Update the profile picture
        try {
            CustomerFacade.updateProfilePicture(customer, fileContent, connectionPool);
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", "Profilbilledet kunne ikke opdateres.");
        }
    }
}