package dat.backend.control.employee;

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
import java.util.Objects;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "save-catalog-item", value = "/save-catalog-item")
public class SaveCatalogItem extends HttpServlet {

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
            String returnpage = "see-catalog";

            switch (catalogItemType) {
                case "roof":
                    float squareMeterPrice = Float.parseFloat(request.getParameter("squareMeterPrice"));
                    String roofType = request.getParameter("roofType");
                    if (id != null) {
                        RoofFacade.updateRoof(Integer.parseInt(id), squareMeterPrice, roofType, connectionPool);
                    } else {
                        RoofFacade.createRoof(squareMeterPrice, roofType, connectionPool);
                    }
                    returnpage = "ToRoofFrame";
                    break;
                case "pole":
                case "rafter":
                    String lumberType = request.getParameter("lumberType");
                    int poleLength = Integer.parseInt(request.getParameter("poleLength"));
                    float poleWidth = Float.parseFloat(request.getParameter("poleWidth"));
                    float poleThickness = Float.parseFloat(request.getParameter("poleThickness"));
                    float poleMeterPrice = Float.parseFloat(request.getParameter("poleMeterPrice"));
                    int amount = Integer.parseInt(request.getParameter("amount"));
                    LumberType lumberTypeObj = LumberTypeFacade.createLumberType(poleThickness, poleWidth, poleMeterPrice, lumberType, connectionPool);
                    if (id != null) {
                        LumberFacade.updateLumber(Integer.parseInt(id), poleLength, lumberTypeObj.getId(), amount, connectionPool);
                    } else {
                        LumberFacade.createLumber(poleLength, lumberTypeObj.getId(), amount, connectionPool);
                    }
                    if (Objects.equals(lumberType, "POLE")) {
                        returnpage = "ToPoleFrame";
                    } else {
                        returnpage = "ToRafterFrame";
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type: " + catalogItemType);
            }
            request.getRequestDispatcher(returnpage).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }
    }
}