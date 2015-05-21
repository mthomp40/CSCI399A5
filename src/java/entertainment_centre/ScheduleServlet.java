package entertainment_centre;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class ScheduleServlet extends HttpServlet {

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
            out.println("<h1 align=\"center\">Create new show record</h1>");
            out.println("<form method=\"POST\" action=\"\">");
            out.println("<fieldset>");
            out.println("<legend>Add a show</legend>");
            out.println("Show identifier <br>&nbsp&nbsp&nbsp&nbsp");
            out.println("<input type=\"text\" id=\"identifier\" name=\"identifier\" value size=\"32\" required=\"1\">");
            out.println("<br>Title <br>&nbsp&nbsp&nbsp&nbsp");
            out.println("<input type=\"text\" id=\"title\" name=\"title\" value size=\"50\" required=\"1\">");
            out.println("<br>Type <br>&nbsp&nbsp&nbsp&nbsp");
            out.println("<select name=\"type\" id=\"type\">");
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
            out.println("Venue <br>&nbsp&nbsp&nbsp&nbsp");
            out.println("<select name=\"venue\" id=\"venue\">");
            out.println("<option value=\"Opera\" label=\"Opera\">Opera</option>");
            out.println("<option value=\"Concert\" label=\"Concert\">Concert</option>");
            out.println("<option value=\"Playhouse\" label=\"Playhouse\">Playhouse</option>");
            out.println("<option value=\"Studio\" label=\"Studio\">Studio</option>");
            out.println("</select><br>");
            out.println("From date <br>&nbsp&nbsp&nbsp&nbsp");
            out.println("<input type=\"date\" name=\"fromdate\" id=\"fromdate\" value=\"\"><br>");
            out.println("To date <br>&nbsp&nbsp&nbsp&nbsp");
            out.println("<input type=\"date\" name=\"todate\" id=\"todate\" value=\"\"><br>");
            out.println("Company <br>&nbsp&nbsp&nbsp&nbsp");
            out.println("<input type=\"text\" id=\"company\" name=\"company\" value size=\"50\" required=\"1\">");
            out.println("<br>Description <br>&nbsp&nbsp&nbsp&nbsp");
            out.println("<textarea id=\"description\" name=\"description\" cols=\"80\" rows=\"4\" required=\"1\"></textarea>");
            out.println("<br>Performances <br>&nbsp&nbsp&nbsp&nbsp");
            out.println("<textarea id=\"performances\" name=\"performances\" cols=\"80\" rows=\"4\" readonly=\"readonly\"></textarea><br><br>&nbsp&nbsp&nbsp&nbsp");
            out.println("<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Add event\">");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<br><fieldset>");
            out.println("<legend>Enter performance details</legend>");
            out.println("Show time <br>");
            out.println("<select name=\"showtime\" id=\"showtime\">");
            out.println("<option value=\"matinee\">matinee</option>");
            out.println("<option value=\"evening\">evening</option>");
            out.println("</select><br>");
            out.println("Date <br>");
            out.println("<input type=\"date\" name=\"performancedate\" id=\"performancedate\"><br><br>");
            out.println("<input type=\"button\" id=\"addperformance\" value=\"Add performance\" onclick=\"addPerformance()\">");
            out.println("</fieldset>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            response.sendRedirect("Errors.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String identifier = request.getParameter("identifier");
            String title = request.getParameter("title");
            String venue = request.getParameter("venue");
            String genre = request.getParameter("type");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date startdate = formatter.parse(request.getParameter("fromdate"));
            java.sql.Date startseason = new java.sql.Date(startdate.getTime());
            java.util.Date enddate = formatter.parse(request.getParameter("todate"));
            java.sql.Date endseason = new java.sql.Date(enddate.getTime());
            String company = request.getParameter("company");
            String shortdescription = request.getParameter("description");
            Context initialContext = new InitialContext();
            DataSource ds = (DataSource) initialContext.lookup("jdbc/myDatasource");
            Connection dbcon = ds.getConnection();
            PreparedStatement pstmnt = dbcon.prepareStatement("insert into ecpresentations(mykey, venue, startseason, endseason, genre, title, company, shortdescription) values(?,?,?,?,?,?,?,?);");
            pstmnt.setString(1, identifier);
            pstmnt.setString(2, venue);
            pstmnt.setDate(3, startseason);
            pstmnt.setDate(4, endseason);
            pstmnt.setString(5, genre);
            pstmnt.setString(6, title);
            pstmnt.setString(7, company);
            pstmnt.setString(8, shortdescription);
            pstmnt.execute();
            String performances = request.getParameter("performances");
            if (performances != null) {
                String[] shows = performances.split(";");
                for (int i = 0; i < shows.length; i++) {
                    String[] parts = shows[i].split(", ");
                    java.util.Date swdte = formatter.parse(parts[0]);
                    java.sql.Date showdate = new java.sql.Date(swdte.getTime());
                    pstmnt = dbcon.prepareStatement("insert into ecperformances(showid, showtime, showdate) values(?,?,?);");
                    pstmnt.setString(1, identifier);
                    pstmnt.setString(2, parts[1]);
                    pstmnt.setDate(3, showdate);
                    pstmnt.execute();
                }
            }
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
            out.println("<h2>New record added</h2>");
            out.println("<ul>");
            out.println("<li><a href=\"\">Schedule another event</a></li>");
            out.println("<li><a href=\"AddInfoServlet\">Add information to event record</a></li>");
            out.println("</ul>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (NamingException | SQLException | ParseException ex) {
            response.sendRedirect("Errors.html");
        }
    }
}
