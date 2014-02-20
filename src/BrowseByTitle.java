/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class BrowseByTitle extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
    	if (request.getSession(false) == null)
    	{
    		response.sendRedirect("http://localhost:8080/ProjectCS122B/Login.html");
    	}
       

        response.setContentType("text/html");    // Response mime type

        InitialContext contxt = null;
		try {
			contxt = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataSource source = null;
		try {
			source = (DataSource)contxt.lookup("java:comp/env/jdbc/moviedb");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
      
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

       
        Connection dbcon = null;
        Statement statement = null;
        ResultSet rs =null;


        out.println("<HTML><HEAD><TITLE>Browse By Title</TITLE>"+  "<script type='text/javascript'></script>" +"</HEAD>");
        out.println("<BODY  BGCOLOR=' #42a2ce'><H1>Browse By Title</H1>");


        try
           {
        	   dbcon = source.getConnection();
               // Declare our statement
                statement = dbcon.createStatement();
              String character = request.getParameter("letter");

          String query = "SELECT * from movies where title like '"+character+"%' order by title asc";

       // Perform the query
         rs = statement.executeQuery(query);
         out.println("<div ALIGN='CENTER'> <a href='javascript:history.back()'>" + "Back " + "</a>|<a href='http://localhost:8080/ProjectCS122B/Home.jsp'> Home </a>|<a href='logout'> Logout </a>| <a href='http://localhost:8080/ProjectCS122B/Checkout.jsp'> Checkout </a></div>");
         out.println("<div ALIGN='CENTER'>");
         out.println("<TABLE border>");
         out.println("<tr>");
         out.println("<th>Photo</th>");
         out.println("<th>Movie id</th>");
         out.println("<th>Title</th>");
         out.println("<th>Director</th>");
         out.println("<th>Year</th>");
         out.println("<th>Trailer</th>");
         out.println("</tr>");
          
          // Iterate through each row of rs
          while (rs.next())
          {
              String title = rs.getString("title");
              title = title.replace("'", "&#39");
              int movieID = rs.getInt("id");
              String movieDirector = rs.getString("director");
              String year = rs.getString("year");
              String trailer = rs.getString("trailer_url");
              String photo = rs.getString("banner_url");
              
              out.println("<tr>" + 
            		  "<td><img src='"+photo+ "' height='50' width='50'></td>" + 
                      "<td>" + movieID + "</td>" +
                      "<td><b><a href='SingleMovie?title="+title+"&genre=&sort=&sortby=&number=&numOfTimes=0'>"+title+"</a></b></br><a href='cart?movieid="+movieID+"&qty=1'><img src='http://gateway.hopto.org:9000/fabflix/images/buttonOff.png'></a></td>" +
                      "<td>" + movieDirector + "</td>" +
                      "<td>" + year + "</td>" +
                      "<td><a href ='"+trailer +"'>"+trailer +"</a></td>" +
                      "</tr>");
       
          }
          out.println("</table>");
          out.println("</div>");
          rs.close();
          statement.close();
          dbcon.close();
        }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
         out.close();
    }

}