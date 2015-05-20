package entertainment_centre;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class ShowshowServlet extends HttpServlet {

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
            Connection dbcon = null;
            try {
                Context initialContext = new InitialContext();
                DataSource ds = (DataSource) initialContext.lookup("jdbc/myDatasource");
                dbcon = ds.getConnection();
                PreparedStatement pstmnt = dbcon.prepareStatement("select * from ecpresentations where mykey=?");
                pstmnt.setString(1, request.getParameter("id"));
                ResultSet rs = pstmnt.executeQuery();
                if (!rs.next()) {
                    return;
                }
                out.println("<h1>" + rs.getString("title") + "</h1>");
                out.println("<h2>" + rs.getString("company") + "</h2>");
                out.println("<p>On at " + rs.getString("venue") + "</p>");
                out.println("<p>from " + rs.getDate("startseason").toString() + " to " + rs.getDate("endseason").toString() + "</p>");
                out.println("<br><p>" + rs.getString("shortdescription") + "</p>");
                out.println("<h3>Scheduled Performances</h3>");
                rs.close();
                pstmnt = dbcon.prepareStatement("select * from ecperformances where showid=?");
                pstmnt.setString(1, request.getParameter("id"));
                rs = pstmnt.executeQuery();
                out.println("<ul>");
                while (rs.next()) {
                    String showdatestr = rs.getDate("showdate").toString();
                    String showtimestr = rs.getString("showtime");
                    out.println("<li><p>" + showdatestr + " : " + showtimestr + "</p></li>");
                }
                out.println("</ul");
                rs.close();
                pstmnt = dbcon.prepareStatement("select * from ecinfo where showid=?");
                pstmnt.setString(1, request.getParameter("id"));
                rs = pstmnt.executeQuery();
                out.println("<table>");
                while (rs.next()) {
                    String details = rs.getString("details");
                    String picy = rs.getString("picy");
                    out.println("<tr><td><img width=\"200\" src=\"" + picy + "\"></td><td>" + details + "</td></tr>");
                }
                out.println("</ul");

            } catch (NamingException | SQLException ex) {
                out.println("<p>Out: " + ex + "</p>");
            } finally {
                try {
                    dbcon.close();
                } catch (Exception e) {

                }
            }
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
