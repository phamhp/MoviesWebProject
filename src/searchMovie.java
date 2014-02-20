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


public class searchMovie extends HttpServlet
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
    		response.sendRedirect("http://localhost:8080/CS122B/Login.html");
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
                  
              String searchTxt = request.getParameter("searchTxt");
             
              String query =   "select distinct title, year, director,movies.id from movies inner " +
              		"join stars_in_movies on movies.id = stars_in_movies.movie_id inner " +
              		"join stars on stars.id = stars_in_movies.star_id where title " +
              		"like '" + searchTxt + "%';";
            
       // Perform the query
      //    out.println("<form action='Pagination' method = 'get'>");
      //    out.println("Number of movies per page: <input type='number'>");
      //    out.println("<input type='submit'></form>");
          
          
           rs = statement.executeQuery(query);
          
           out.println("<HTML><HEAD><TITLE>Welcome </TITLE>"+  "<script type='text/javascript'></script>" +"</HEAD>");
           out.println("<BODY BGCOLOR=' #42a2ce'><H1>MovieDB</H1>");
           out.println("<div ALIGN='CENTER'> <a href='javascript:history.back()'>" + "Previous " + "</a>|<a href='http://localhost:8080/ProjectCS122B/Home.jsp'> Home </a>|<a href='logout'> Logout </a>| <a href='http://localhost:8080/ProjectCS122B/Checkout.jsp'> Checkout </a></div>");
           out.println("<div ALIGN='CENTER'>"); 
           out.println("<TABLE border>");
       /*   out.println("<tr>");
          out.println("<th><b><a href='Sort?param1=title&param2=asc'>Title</a></b></br></th>");
          out.println("<th><b><a href='Sort?param1=director&param2=asc'>Director</a></b></br></th>");
          out.println("<th><b><a href='Sort?param1=year&param2=asc&param3='>Year</a></b></br></th>"); 
          out.println("</tr>");*/
         
          String movieTitle = "";
          String movieDirector="";
          String movieYear="";
          String id = "";
          // Iterate through each row of rs
          while (rs.next())
          {
               movieTitle = rs.getString("title");
               movieDirector = rs.getString("director");
               movieYear = rs.getString("year");
               id = rs.getString("id");
             
          }
          
         
          ArrayList<String>genres = new ArrayList<String>();
          String query2 = "select * from (select G.name,  movie_id, title, G.id as genre_id from genres as G join genres_in_movies on G.id = genre_id join movies as m "
          		+ "where m.id  = genres_in_movies.movie_id) as T where T.movie_id ='" + id + "';";
          
          
          rs = statement.executeQuery(query2);
          while (rs.next())
          {
               String genres_name = rs.getString("name");
               genres.add(genres_name);
          }
          
          String query3 = "select * from (select banner_url, movies.id, title, first_name, last_name,star_id from movies join stars_in_movies as M on M.movie_id = movies.id join stars where stars.id = M.star_id) as T where T.title = '"+ movieTitle+"';";
          
          rs = statement.executeQuery(query3);
         
          Map<String,String> mMap = new HashMap<String,String>(); 
          String url="";
          while (rs.next())
          {
               String first_name = rs.getString("first_name");
               String last_name = rs.getString("last_name");
               String fullName = first_name +' ' +last_name;
               String starId = rs.getString("star_id");
              url = rs.getString("banner_url");
               mMap.put(starId,fullName);
               
          }
          out.println("<tr><img src='"+url+ "'></tr>");
          out.println("<tr><td> <div align='left'>Title:</div></td>" +
                  "<td><a href='getMovie?movieId="+id+"'>" +movieTitle + "</a></td></tr>");
          out.println("<tr><td> <div align='left'>Movie id:</div></td>" +
                  "<td>" +id + "</td></tr>");
          out.println("<tr><td> <div align='left'>Year:</div></td>" +
                  "<td>" +movieYear + "</td></tr>");
          out.println("<tr><td> <div align='left'>Director:</div></td>" +
                  "<td>" +movieDirector + "</td></tr>" + 
                  "<tr><td> <div align='left'>Genre:</div></td><td>");   
          
          
          
         for(int i=0;i<genres.size(); ++i){
        	  out.println( genres.get(i) + ",");   
        	    
          }
         out.println( "</td></tr>");
         out.println("<tr><td> <div align='left'>Stars:</div></td>" +
                 "<td>"); 
         Iterator iter = mMap.entrySet().iterator();
      /*   for(int i=0;i<name.size(); ++i){
        	 String example = "<a href='getStar?starId='" + name.get(i)+  "'>" + name.get(i) + "</a>,";
        	  out.println( "<a href='getStar?starId=" + name.get(i)+  "'>" + name.get(i) + "</a>,");   
        	 System.out.println("example" + example);
          } */
         while (iter.hasNext()) {
				Map.Entry mEntry = (Map.Entry) iter.next();
				out.println( "<a href='getStar?starId=" + mEntry.getKey()+  "'>" + mEntry.getValue() + "</a>,");   
			}
         
     	out.println("</td></tr>");
     	out.println("<tr><td><a href='cart?movieid="+id+"&qty=1'><img src='http://gateway.hopto.org:9000/fabflix/images/buttonOff.png'></a></td><td></td></tr>");
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
