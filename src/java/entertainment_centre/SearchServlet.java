package entertainment_centre;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class SearchServlet extends HttpServlet {

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
            if (request.getUserPrincipal() != null) {
                out.println("<div id=\"loggedin\">");
                out.println("<h2>Logged in</h2>");
                out.println("<p>Currently logged in as " + request.getUserPrincipal().getName() + ".&nbsp<a href=\"LogoutServlet\">Logout</a></p></div>");
            }
            out.println("<div id=\"content\">");
            out.println("<form method=\"POST\" onsubmit=\"return doCheckDates()\">");
            out.println("<fieldset>");
            out.println("<legend>Search for shows</legend>");
            out.println("Type &nbsp&nbsp");
            out.println("<select name=\"type\" id=\"type\">");
            out.println("<option value=\"All\" label=\"All\">All</option>");
            out.println("<option value=\"Drama\" label=\"Drama\">Drama</option>");
            out.println("<option value=\"Film\" label=\"Film\">Film</option>");
            out.println("<option value=\"Opera\" label=\"Opera\">Opera</option>");
            out.println("<option value=\"Jazz\" label=\"Jazz\">Jazz</option>");
            out.println("<option value=\"World Music\" label=\"World Music\">World Music</option>");
            out.println("<option value=\"Ballet\" label=\"Ballet\">Ballet</option>");
            out.println("<option value=\"Recital\" label=\"Recital\">Recital</option>");
            out.println("<option value=\"Concert\" label=\"Concert\">Concert</option>");
            out.println("<option value=\"Choral\" label=\"Choral\">Choral</option>");
            out.println("<option value=\"Contemporary Dance\" label=\"Contemporary Dance\">Contemporary Dance</option>");
            out.println("<option value=\"Comedy\" label=\"Comedy\">Comedy</option>");
            out.println("<option value=\"Children\" label=\"Children\">Children</option>");
            out.println("</select><br>");
            out.println("Venue &nbsp&nbsp");
            out.println("<select name=\"venue\" id=\"venue\">");
            out.println("<option value=\"Any\" label=\"Any\">Any</option>");
            out.println("<option value=\"Opera\" label=\"Opera\">Opera</option>");
            out.println("<option value=\"Concert\" label=\"Concert\">Concert</option>");
            out.println("<option value=\"Playhouse\" label=\"Playhouse\">Playhouse</option>");
            out.println("<option value=\"Studio\" label=\"Studio\">Studio</option>");
            out.println("</select><br>");
            out.println("From date &nbsp&nbsp");
            out.println("<input type=\"date\" name=\"fromdate\" id=\"fromdate\" value=\"\"><br>");
            out.println("To date &nbsp&nbsp");
            out.println("<input type=\"date\" name=\"todate\" id=\"todate\" value=\"\"><br><br>");
            out.println("<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Search\">");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
            out.println("<h1 align=\"center\">Shows on at the Entertainment Centre</h1>");
            try {
                Context initialContext = new InitialContext();
                DataSource ds = (DataSource) initialContext.lookup("jdbc/myDatasource");
                Connection dbcon = ds.getConnection();
                Statement stmnt = dbcon.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from ecpresentations");
                out.println("<ul>");
                while (rs.next()) {
                    out.println("<li>");
                    String id = rs.getString("mykey");
                    String title = rs.getString("title");
                    out.println("<a href=\"ShowshowServlet?id=" + id + "\">" + title + "</a>");
                    out.println("</li>");
                }
                rs.close();
                out.println("</ul>");
            } catch (NamingException | SQLException ex) {
                
            }
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
