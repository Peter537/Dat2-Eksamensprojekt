package dat.backend.control;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.LumberFacade;
import dat.backend.model.persistence.item.LumberTypeFacade;
import dat.backend.model.persistence.item.RoofFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "create-catalog-item", value = "/create-catalog-item")
public class CreateCatalogItem extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String catalogItemType = request.getParameter("catalogItemType");
            switch (catalogItemType) {
                case "roof":
                    float squareMeterPrice = Float.parseFloat(request.getParameter("squareMeterPrice"));
                    String roofType = request.getParameter("roofType");
                    RoofFacade.createRoof(squareMeterPrice, roofType, connectionPool);
                    break;
                case "pole":
                case "rafter":
                    String lumberType = request.getParameter("lumberType");
                    float poleLength = Float.parseFloat(request.getParameter("poleLength"));
                    float poleWidth = Float.parseFloat(request.getParameter("poleWidth"));
                    float poleThickness = Float.parseFloat(request.getParameter("poleThickness"));
                    float poleMeterPrice = Float.parseFloat(request.getParameter("poleMeterPrice"));
                    int amount = Integer.parseInt(request.getParameter("amount"));
                    LumberType lumberTypeObj = LumberTypeFacade.createLumberType(poleThickness, poleWidth, poleMeterPrice, lumberType, connectionPool);
                    LumberFacade.createLumber(poleLength, lumberTypeObj.getId(), amount, connectionPool);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type: " + catalogItemType);
            }
            request.getRequestDispatcher("see-catalog").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }
    }
}