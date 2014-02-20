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

public class SingleMovie extends HttpServlet
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
        	 dbcon = source.getConnection();
             // Declare our statement
              statement = dbcon.createStatement();
              // Declare our statement
            
              Statement statement2 = dbcon.createStatement();
              Statement statement3 = dbcon.createStatement();
              
          String title = request.getParameter("title");
          title = title.replace("'", "''");
          String query = "select id, title, year, director, trailer_url,banner_url from movies where title = '"+title+"'";
          String query2 = "select genres.name from movies inner join genres_in_movies on movies.id = " +
          		"genres_in_movies.movie_id inner join genres on genres_in_movies.genre_id = genres.id" +
          		" where movies.title = '"+title+"'";
          String query3 = "select stars.first_name, stars.last_name from movies inner join stars_in_movies on " +
          		"movies.id = stars_in_movies.movie_id inner join stars on stars_in_movies.star_id = " +
          		"stars.id where movies.title = '"+title+"'";
       // Perform the query
          rs = statement.executeQuery(query);
          ResultSet rs2 = statement2.executeQuery(query2);
          ResultSet rs3 = statement3.executeQuery(query3);
          out.println("<div ALIGN='CENTER'> <a href='javascript:history.back()'>" + "Previous " + "</a>|<a href='http://localhost:8080/ProjectCS122B/Home.jsp'> Home </a>|<a href='logout'> Logout </a>| <a href='http://localhost:8080/ProjectCS122B/Checkout.jsp'> Checkout </a></div>");
          out.println("<div ALIGN='CENTER'>");
          out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Photo</th>");
          out.println("<th>Movie ID</th>");
          out.println("<th>Title</th>");
          out.println("<th>Director></th>");
          out.println("<th>Year</th>");
          out.println("<th>Genres</th>");
          out.println("<th>Stars</th>");
          out.println("<th>Trailer></th>");
          out.println("</tr>");

          // Iterate through each row of rs
          String stars = "";
          while (rs3.next())
          {
        	  String firstName = rs3.getString("stars.first_name");
        	  String lastName = rs3.getString("stars.last_name");
        	  stars = "<b><a href='SingleStar?fname=" + firstName +
        			  "&lname="+lastName+"'>"+firstName + " " + lastName +"</a></b></br>" + stars;
          }
          String genres = "";
          while (rs2.next())
          {
        	  genres = "<b><a href='SelectTitle?genre=" + rs2.getString("genres.name") +
        			  "&number=&numOfTimes=0&title=&sort=&sortby='>"+rs2.getString("genres.name")+"" +
        			  		"</a></b></br>" + genres;
          }
          while (rs.next())
          {
              String movieID = rs.getString("id");
              String movieTitle = rs.getString("title");
              String movieDirector = rs.getString("director");
              int year = rs.getInt("year");
              String trailer = rs.getString("trailer_url");
              String photo = rs.getString("banner_url");
              out.println("<tr>" + 
            		  	  "<td><img src='"+photo+ "' height='50' width='50'></td>" + 
                          "<td>" + movieID + "</td>" +
                          "<td><b><a href='SingleMovie?title=" + movieTitle + "'>"+movieTitle+"</a></b></br><a href='cart?movieid="+movieID+"&qty=1'><img src='http://gateway.hopto.org:9000/fabflix/images/buttonOff.png'></a></td>" +
                          "<td>" + movieDirector + "</td>" +
                          "<td>" + year + "</td>" +
                          "<td>" + genres + "</td>" +
                          "<td>" + stars + "</td>" +
                          "<td><a href='"+ trailer+"'>" + trailer + "</a></td>" +
                          "</tr>");
          }
          
          
          out.println("</TABLE>");
     
          out.println("</div>");

          rs.close();
          rs2.close();
          rs3.close();
          statement.close();
          statement2.close();
          statement3.close();
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
