package entertainment_centre;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">");
            out.println("<title>Entertainment Centre</title>");
            out.println("<script type=\"text/javascript\" src=\"js/a5.js\"></script>");
            out.println("<link href=\"css/mystyles.css\" media=\"screen\" rel=\"stylesheet\" type=\"text/css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div id=\"headdiv\">");
            out.println("<a href=\"index.html\"><img src=\"siteimages/logo.png\" alt=\"logo\"></a>");
            out.println("<div id=\"ident\">");
            out.println("<p>Entertainment Centre</p>");
            out.println("</div>");
            out.println("</div>");
            out.println("<div id=\"content\">");
            if (request.getUserPrincipal() != null) {
                out.println("<h2>" + request.getUserPrincipal().getName() + " has been logged out</h2>");
            } else {
                out.println("<h2>There is no currently logged in user!</h2>");
            }
            out.println("<p>Click <a href=\"index.html\">here</a> to return to the home page</p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            response.sendRedirect("Errors.html");
        }
        request.getSession().invalidate();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
