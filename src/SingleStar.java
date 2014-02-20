/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.Date;
import java.text.*;
import java.util.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class SingleStar extends HttpServlet
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

        response.setContentType("text/html");    // Response mime type

      
        out.println("<HTML><HEAD><TITLE>Browse By Title</TITLE>"+  "<script type='text/javascript'></script>" +"</HEAD>");
        out.println("<BODY BGCOLOR=' #42a2ce'><H1>Browse By Title</H1>");


        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
        	   //Class.forName("org.gjt.mm.mysql.Driver");
        	dbcon = source.getConnection();
            // Declare our statement
             statement = dbcon.createStatement();
             // Declare our statement
              statement = dbcon.createStatement();
              Statement statement2 = dbcon.createStatement();
              
              
          String fname = request.getParameter("fname");
          String lname = request.getParameter("lname");
          String query = "select * from stars where first_name = '"+fname+"' and last_name = '"+lname+"'";
          String query2 = "select title from movies inner join stars_in_movies on movies.id = " +
          		"stars_in_movies.movie_id inner join stars on stars_in_movies.star_id = stars.id" +
          		" where stars.first_name = '"+fname+"' and stars.last_name = '"+lname+"'";
          
       // Perform the query
          rs = statement.executeQuery(query);
          ResultSet rs2 = statement2.executeQuery(query2);
          
          out.println("<div ALIGN='CENTER'> <a href='javascript:history.back()'>" + "Previous " + "</a>|<a href='http://localhost:8080/ProjectCS122B/Home.jsp'> Home </a>|<a href='logout'> Logout </a>| <a href='http://localhost:8080/ProjectCS122B/Checkout.jsp'> Checkout </a></div>");
          out.println("<div ALIGN='CENTER'>");
          out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Star ID</th>");
          out.println("<th>First Name</th>");
          out.println("<th>Last Name</th>");
          out.println("<th>Date Of Birth</th>");
          out.println("<th>Photo</th>");
          out.println("<th>Movies</th>");
          out.println("</tr>");

          // Iterate through each row of rs
          
          String movies = "";
          while (rs2.next())
          {
        	  movies = "<b><a href='SingleMovie?title=" + rs2.getString("title") +
        			  "'>"+rs2.getString("title")+"</a></b></br>" + movies;
          }
          while (rs.next())
          {
              String starID = rs.getString("id");
              String firstName = rs.getString("first_name");
              String lastName = rs.getString("last_name");
              Date dob = rs.getDate("dob");
              String photo = rs.getString("photo");
              out.println("<tr>" + 
                          "<td>" + starID + "</td>" +
                          "<td>" + firstName + "</td>" +
                          "<td>" + lastName + "</td>" +
                          "<td>" + dob + "</td>" +
                          "<td><img src='"+photo+ "' height='50' width='50'></td>" +
                          "<td>" + movies + "</td>" +
                          "</tr>");
          }
          
          
          out.println("</TABLE>");
          out.println("</div>");
          rs.close();
          rs2.close();
          
          statement.close();
          statement2.close();
          
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
