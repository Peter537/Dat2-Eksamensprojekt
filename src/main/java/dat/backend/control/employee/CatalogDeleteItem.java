package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.LumberFacade;
import dat.backend.model.persistence.item.RoofFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "delete-catalog-item", value = "/delete-catalog-item")
public class CatalogDeleteItem extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String catalogItemType = request.getParameter("catalogItemType");
            String id = request.getParameter("id");
            String returnPage;
            switch (catalogItemType) {
                case "roof":
                    RoofFacade.deleteRoof(Integer.parseInt(id), connectionPool);
                    returnPage = "ToRoofFrame";
                    break;
                case "pole":
                    LumberFacade.deleteLumber(Integer.parseInt(id), connectionPool);
                    returnPage = "ToPoleFrame";
                    break;
                case "rafter":
                    LumberFacade.deleteLumber(Integer.parseInt(id), connectionPool);
                    returnPage = "ToRafterFrame";
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type: " + catalogItemType);
            }

            request.getRequestDispatcher(returnPage).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }
    }
}