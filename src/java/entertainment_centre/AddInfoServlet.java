package entertainment_centre;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class AddInfoServlet extends HttpServlet {

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
            out.println("<h1 align=\"center\">Add details to Show</h1>");
            out.println("<form method=\"POST\" action=\"\">");
            out.println("<fieldset>");
            out.println("<legend>Show</legend>");
            out.println("Show identifier &nbsp&nbsp<input type=\"text\" id=\"identifier\" name=\"identifier\" value size=\"32\" required=\"1\">");
            out.println("</fieldset>");
            out.println("<br><fieldset>");
            out.println("<legend>Supplementary Data</legend>");
            out.println("<table id=\"droppertable\" border=\"1\">");
            out.println("<tr><th>Picture</th><th>Comment</th></tr>");
            out.println("</table><br>");
            out.println("<input type=\"button\" value=\"Add data\" onclick=\"addData()\"><br>");
            out.println("</fieldset><br>");
            out.println("<fieldset>");
            out.println("<legend>Action</legend>");
            out.println("<input type=\"submit\" name=\"submit\" value=\"Add details\"><br>");
            out.println("</fieldset><br>");
            out.println("</form>");
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
            Enumeration<String> params = request.getParameterNames();
            Context initialContext = new InitialContext();
            DataSource ds = (DataSource) initialContext.lookup("jdbc/myDatasource");
            Connection dbcon = ds.getConnection();
            while (params.hasMoreElements()) {
                String param = (String) params.nextElement();
                if (param.startsWith("img")) {
                    int num = Integer.parseInt(param.substring(3));
                    String details = request.getParameter("info" + num);
                    String picy = request.getParameter("img" + num);
                    PreparedStatement pstmnt = dbcon.prepareStatement("insert into ecinfo(showid, details, picy) values(?,?,?);");
                    pstmnt.setString(1, identifier);
                    pstmnt.setString(2, details);
                    pstmnt.setString(3, picy);
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
            if (request.getUserPrincipal() != null) {
                out.println("<div id=\"loggedin\">");
                out.println("<h2>Logged in</h2>");
                out.println("<p>Currently logged in as " + request.getUserPrincipal().getName() + ".&nbsp<a href=\"LogoutServlet\">Logout</a></p></div>");
            }
            out.println("<div id=\"content\">");
            out.println("<h1 align=\"center\">Add details to Show</h1>");
            out.println("<p>Records added to " + identifier + "</p>");
            out.println("<form method=\"POST\" action=\"\">");
            out.println("<fieldset>");
            out.println("<legend>Show</legend>");
            out.println("Show identifier &nbsp&nbsp<input type=\"text\" id=\"identifier\" name=\"identifier\" value size=\"32\" required=\"1\">");
            out.println("</fieldset>");
            out.println("<br><fieldset>");
            out.println("<legend>Supplementary Data</legend>");
            out.println("<table id=\"droppertable\" border=\"1\">");
            out.println("<tr><th>Picture</th><th>Comment</th></tr>");
            out.println("</table><br>");
            out.println("<input type=\"button\" value=\"Add data\" onclick=\"addData()\"><br>");
            out.println("</fieldset><br>");
            out.println("<fieldset>");
            out.println("<legend>Action</legend>");
            out.println("<input type=\"submit\" name=\"submit\" value=\"Add details\"><br>");
            out.println("</fieldset><br>");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (NamingException | SQLException ex) {
            response.sendRedirect("Errors.html");
        }
    }
}
