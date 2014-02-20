/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;

import java.sql.*;



import javax.naming.InitialContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class Errors extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
        
    {
    	

      response.setContentType("text/html");    // Response mime type
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        out.println("<HTML><HEAD><TITLE>List of Errors</TITLE></HEAD>");
        out.println("<H1>List of Errors</H1>");
      
        Connection dbcon = null;
        Statement statement = null;
        ResultSet rs =null;
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
        	//Class.forName("com.mysql.jdbc.Driver").newInstance();
        	InitialContext contxt = new InitialContext();
    		DataSource source = (DataSource)contxt.lookup("java:comp/env/jdbc/moviedb");
           dbcon = source.getConnection();
              // Declare our statement
          statement = dbcon.createStatement();
        
			String query = "Select id, title from movies where id not in (select movie_id from " +
					"stars_in_movies)";
			rs = statement.executeQuery(query);
			
			out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Movie ID</th>");
          out.println("<th>Title</th>");
          while (rs.next())
          {
          	int movieID = rs.getInt("id");
          	String title = rs.getString("title");
          	out.println("<tr>" + 
          		"<td>" + movieID + "</td>" +
          		"<td>" + title + "</td>" +
          		"</tr>");
          }
          out.println("Movies Without Stars");
          query = "Select id, first_name, last_name from stars where id not in (select star_id " +
          		"from stars_in_movies)";
			rs = statement.executeQuery(query);
			
			out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Star ID</th>");
          out.println("<th>Star First Name</th>");
          out.println("<th>Star Last Name</th>");
          while (rs.next())
          {
          	int starID = rs.getInt("id");
          	String firstName = rs.getString("first_name");
          	String lastName = rs.getString("last_name");
          	out.println("<tr>" + 
          		"<td>" + starID + "</td>" +
          		"<td>" + firstName + "</td>" +
          		"<td>" + lastName + "</td>" +
          		"</tr>");
          }
          out.println("Stars Without Movies");
          query = "Select * from genres where id not in (select genre_id from genres_in_movies)";
			rs = statement.executeQuery(query);
			
			out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Genre ID</th>");
          out.println("<th>Genre Name</th>");
          while (rs.next())
          {
          	int genreID = rs.getInt("id");
          	String genreName = rs.getString("name");
          	out.println("<tr>" + 
          		"<td>" + genreID + "</td>" +
          		"<td>" + genreName + "</td>" +
          		"</tr>");
          }
          out.println("Genres Without Movies");
          query = "Select id, title from movies where id not in (select movie_id from " +
          		"genres_in_movies)";
			rs = statement.executeQuery(query);
			
			out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Movie ID</th>");
          out.println("<th>Title</th>");
          while (rs.next())
          {
          	int movieID = rs.getInt("id");
          	String title = rs.getString("title");
          	out.println("<tr>" + 
          		"<td>" + movieID + "</td>" +
          		"<td>" + title + "</td>" +
          		"</tr>");
          }
          out.println("Movies Without Genres");
          query = "Select creditcards.id from creditcards inner join customers on creditcards.id = " +
          		"customers.id where expiration < utc_date()";
			rs = statement.executeQuery(query);
			
			out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Credit Card ID</th>");
          while (rs.next())
          {
          	int cardID = rs.getInt("creditcards.id");
          	out.println("<tr>" + 
          		"<td>" + cardID + "</td>" +
          		"</tr>");
          }
          out.println("Expired Credit Cards");
          query = "Select id, movies.title, movies.year from movies inner join (select year, " +
          		"title from movies group by year, title having count(*) > 1) dup on " +
          		"movies.year = dup.year and movies.title = dup.title order by title asc";
			rs = statement.executeQuery(query);
			
			out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Movie ID</th>");
          out.println("<th>Title</th>");
          out.println("<th>Year</th>");
          while (rs.next())
          {
          	int movieID = rs.getInt("id");
          	String title = rs.getString("movies.title");
          	String year = rs.getString("movies.year");
          	out.println("<tr>" + 
          		"<td>" + movieID + "</td>" +
          		"<td>" + title + "</td>" +
          		"<td>" + year + "</td>" +
          		"</tr>");
          }
          out.println("Duplicate Movies");
          query = "Select id, stars.first_name, stars.last_name, stars.dob from stars inner " +
          		"join (select first_name, last_name, dob from stars group by first_name, " +
          		"last_name, dob having count(*) > 1) dup on stars.first_name = " +
          		"dup.first_name and stars.last_name = dup.last_name and stars.dob = dup.dob " +
          		"order by first_name asc";
			rs = statement.executeQuery(query);
			
			out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Star ID</th>");
          out.println("<th>Star First Name</th>");
          out.println("<th>Star Last Name</th>");
          out.println("<th>Date of Birth</th>");
          while (rs.next())
          {
          	int starID = rs.getInt("id");
          	String firstName = rs.getString("stars.first_name");
          	String lastName = rs.getString("stars.last_name");
          	Date dob = rs.getDate("stars.dob"); 
          	out.println("<tr>" + 
          		"<td>" + starID + "</td>" +
          		"<td>" + firstName + "</td>" +
          		"<td>" + lastName + "</td>" +
          		"<td>" + dob + "</td>" +
          		"</tr>");
          }
          out.println("Duplicate Stars");
          query = "Select id, genres.name from genres inner join (select name from genres " +
          		"group by name having count(*) > 1) dup on genres.name = dup.name order by " +
          		"name asc";
			rs = statement.executeQuery(query);
			
			out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Genre ID</th>");
          out.println("<th>Genre</th>");
          while (rs.next())
          {
          	int genreID = rs.getInt("id");
          	String genre = rs.getString("genres.name"); 
          	out.println("<tr>" + 
          		"<td>" + genreID + "</td>" +
          		"<td>" + genre + "</td>" +
          		"</tr>");
          }
          out.println("Duplicate Genres");
          query = "Select id, first_name, last_name, dob from stars where dob > utc_date() " +
          		"or dob < '1900-01-01'";
			rs = statement.executeQuery(query);
			
			out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Star ID</th>");
          out.println("<th>Star First Name</th>");
          out.println("<th>Star Last Name</th>");
          out.println("<th>Date of Birth</th>");
          while (rs.next())
          {
          	int starID = rs.getInt("id");
          	String firstName = rs.getString("first_name"); 
          	String lastName = rs.getString("last_name"); 
          	Date dob = rs.getDate("dob");
          	out.println("<tr>" + 
          		"<td>" + starID + "</td>" +
          		"<td>" + firstName + "</td>" +
          		"<td>" + lastName + "</td>" +
          		"<td>" + dob + "</td>" +
          		"</tr>");
          }
          out.println("Duplicate Genres");
          query = "Select id, email from customers where email not like '%@%'";
			rs = statement.executeQuery(query);
			
			out.println("<TABLE border>");
          out.println("<tr>");
          out.println("<th>Customer ID</th>");
          out.println("<th>Email</th>");
          while (rs.next())
          {
          	int customerID = rs.getInt("id");
          	String email = rs.getString("email"); 
          	out.println("<tr>" + 
          		"<td>" + customerID + "</td>" +
          		"<td>" + email + "</td>" +
          		"</tr>");
          }
          out.println("Invalid Emails");
              rs.close();
              statement.close();
              dbcon.close();
              statement = null;
              dbcon = null;
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
                            "<P>SQL error in doPost: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }finally {
                try { if(null!=rs)rs.close();} 
                catch (SQLException e) 
                {e.printStackTrace();}
                try { if(null!=statement)statement.close();}
                catch (SQLException e) 
                {e.printStackTrace();}
                try { if(null!=dbcon)dbcon.close();} 
                catch (SQLException e) 
                {e.printStackTrace();}
            }
        
         out.close();
       
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
        {
    	doGet(request, response);
        }
}
