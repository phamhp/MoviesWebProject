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


public class getStar extends HttpServlet
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
		
        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

       
        Connection dbcon = null;
        Statement statement = null;
        ResultSet rs =null;
        
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
            //  Class.forName("com.mysql.jdbc.Driver").newInstance();

               dbcon = source.getConnection();
              // Declare our statement
              statement = dbcon.createStatement();
           
              String starId = request.getParameter("starId");
             
              String query =   "select distinct first_name,last_name,title, `year`,movies.id as movie_id, stars.id as star_id,dob,photo from movies inner " +
              		"join stars_in_movies on movies.id = stars_in_movies.movie_id inner " +
              		"join stars on stars.id = stars_in_movies.star_id where star_id='" + starId + "';";
            
       // Perform the query
      //    out.println("<form action='Pagination' method = 'get'>");
      //    out.println("Number of movies per page: <input type='number'>");
      //    out.println("<input type='submit'></form>");
          
          
           rs = statement.executeQuery(query);
          
           out.println("<HTML><HEAD><TITLE>Welcome </TITLE>"+  "<script type='text/javascript'></script>" +"</HEAD>");
           out.println("<BODY BGCOLOR=' #42a2ce'><H1>Star's record</H1>");
           out.println("<div ALIGN='CENTER'> <a href='javascript:history.back()'>" + "Previous " + "</a>|<a href='http://localhost:8080/ProjectCS122B/Home.jsp'> Home </a>|<a href='logout'> Logout </a>| <a href='http://localhost:8080/ProjectCS122B/Checkout.jsp'> Checkout </a></div>");
           out.println("<div ALIGN='CENTER'>");
           out.println("<TABLE border>");
       /*   out.println("<tr>");
          out.println("<th><b><a href='Sort?param1=title&param2=asc'>Title</a></b></br></th>");
          out.println("<th><b><a href='Sort?param1=director&param2=asc'>Director</a></b></br></th>");
          out.println("<th><b><a href='Sort?param1=year&param2=asc&param3='>Year</a></b></br></th>"); 
          out.println("</tr>");*/
          String firstName = "";
          String lastName="";
          String id="";
          String inMovie = "";
          String fullName="";
          String dob = "";
          String movie_id;
          String photo="";
          ArrayList<String>movies = new ArrayList<String>();
          Map<String,String> mMap = new HashMap<String,String>(); 
          // Iterate through each row of rs
          while (rs.next())
          {
               firstName = rs.getString("first_name");
               lastName = rs.getString("last_name");
               fullName = firstName +" " +lastName;
               dob = rs.getString("dob");
               id = rs.getString("star_id");
               inMovie = rs.getString("title");
               movie_id = rs.getString("movie_id");
               photo= rs.getString("photo");
              
               if(!mMap.containsKey(movie_id)){
            	   mMap.put(movie_id, inMovie);
               
               }
          }
          
        
          out.println("<tr><img src='"+photo+ "'></tr>");
          out.println("<tr><td> <div align='left'>Star Name: </div></td>" +
                  "<td>" +fullName + "</td></tr>");
          out.println("<tr><td> <div align='left'>Star id: </div></td>" +
                  "<td>" +id + "</td></tr>");
          out.println("<tr><td> <div align='left'>Date of Birth: </div></td>" +
                  "<td>" +dob + "</td></tr>");
          out.println("<tr><td> <div align='left'>Starred in:</div></td>" +
                  "<td>");   
          
          Iterator iter = mMap.entrySet().iterator();
          while (iter.hasNext()) {
				Map.Entry mEntry = (Map.Entry) iter.next();
				out.println( "<a href='getMovie?movieId=" + mEntry.getKey()+  "'>" + mEntry.getValue() + "</a>,");   
			}
       
        
     	out.println("</td></tr>");
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
