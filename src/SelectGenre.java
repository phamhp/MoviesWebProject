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

public class SelectGenre extends HttpServlet
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

       
        out.println("<HTML><HEAD><TITLE>Browse By Genre</TITLE>"+  "<script type='text/javascript'></script>" +"</HEAD>");
        out.println("<BODY BGCOLOR=' #42a2ce'><H1>Browse By Genre</H1>");


        try
           {
        	 dbcon = source.getConnection();
             // Declare our statement
              statement = dbcon.createStatement();
              
          String genre = request.getParameter("genre");
          String query = "Select distinct * from movies inner join genres_in_movies on movies.id = " +
          		"genres_in_movies.movie_id inner join genres on genres.id = genres_in_movies.genre_id " +
          		"where genres.name = '" + genre + "'";

       // Perform the query
          rs = statement.executeQuery(query);
          HttpSession session = request.getSession(false);
          session.setAttribute("genre", genre);
          
          out.println("<form action = 'Pagination' method = 'get'>");
          out.println("Number of movies per page: <input type='text' name = 'number'>");
          out.println("<input type = 'submit'></form>");
          out.println("<div ALIGN='CENTER'> <a href='javascript:history.back()'>" + "Back " + "</a>|<a href='http://localhost:8080/ProjectCS122B/Home.jsp'> Home </a>|<a href='logout'> Logout </a>| <a href='http://localhost:8080/ProjectCS122B/Checkout.jsp'> Checkout </a></div>");
          out.println("<div ALIGN='CENTER'>");
          out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th><b><a href='SortGenre?param1=id&param2=asc&param3=" + genre + "'>Photo</a></b></br></th>");
          out.println("<th><b><a href='SortGenre?param1=id&param2=asc&param3=" + genre + "'>Movie ID</a></b></br></th>");
          out.println("<th><b><a href='SortGenre?param1=title&param2=asc&param3=" + genre + "'>Title</a></b></br></th>");
          out.println("<th><b><a href='SortGenre?param1=director&param2=asc&param3=" + genre + "'>Director</a></b></br></th>");
          out.println("<th><b><a href='SortGenre?param1=year&param2=asc&param3=" + genre + "'>Year</a></b></br></th>");
          out.println("</tr>");

          // Iterate through each row of rs
          while (rs.next())
          {
              String movieID = rs.getString("movies.id");
              String movieTitle = rs.getString("title");
              String movieDirector = rs.getString("director");
              int year = rs.getInt("year");
              String photo = rs.getString("banner_url");
              out.println("<tr>" +
            		  		"<td><img src='"+photo+ "' height='50' width='50'></td>" +
                          "<td>" + movieID + "</td>" +
                          "<td>" + movieTitle + "</br><a href='cart?movieid="+movieID+"&qty=1'><img src='http://gateway.hopto.org:9000/fabflix/images/buttonOff.png'></a></td>" +
                          "<td>" + movieDirector + "</td>" +
                          "<td>" + year + "</td>" +
                          "</tr>");
          }
          
          out.println("</TABLE>");
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
