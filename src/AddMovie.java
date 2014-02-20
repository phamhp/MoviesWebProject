/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;

import java.sql.*;



import javax.naming.InitialContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class AddMovie extends HttpServlet
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

        out.println("<HTML><HEAD><TITLE>Add Movie</TITLE></HEAD>");
        out.println("<BODY><H1>Add Movie</H1>");
      
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
        
	      String title = request.getParameter("title");
	      String director = request.getParameter("director");
	      String temp = request.getParameter("year");
	      int year = Integer.parseInt(temp);
	      String banner = request.getParameter("banner");
	      String trailer = request.getParameter("trailer");
	      String fname = request.getParameter("fname");
	      String lname = request.getParameter("lname");
	      String genre = request.getParameter("genre");
	      
	      
	      String query = "Select * from movies where title = '"+title+"'";
	      rs = statement.executeQuery(query);
			if (rs.first())
			{
				out.println("This movie already exists. No changes to the " +
						"database will be made.");
			}
			else
			{
				String instruction = "Insert into movies(title, director, year, banner_url, " +
						"trailer_url) values('"+title+"', '"+director+"', "+year+", '"+banner+"', " +
								"'"+trailer+"')";
				statement.executeUpdate(instruction);
				query = "Select * from stars where first_name = '"+fname+"' and last_name = '"+lname+"'";
				rs = statement.executeQuery(query);
				if (rs.first())
				{
					out.println("This star already exists, will update.");
				}
				else
				{
					instruction = "Insert into stars(first_name, last_name) Values('"+fname+"', " +
							"'"+lname+"')";
					statement.executeUpdate(instruction);
				}
				query = "Select * from genres where name = '"+genre+"'";
				rs = statement.executeQuery(query);
				if (rs.first())
				{
					out.println("This genre already exists, will update.");
				}
				else
				{
					instruction = "Insert into genres(name) values('"+genre+"')";
					statement.executeUpdate(instruction);
				}
				query = "Select id from movies where title = '"+title+"'";
				rs = statement.executeQuery(query);
				rs.next();
				int movieID = rs.getInt("id");
				query = "Select id from stars where first_name = '"+fname+"' and last_name = " +
						"'"+lname+"'";
				rs = statement.executeQuery(query);
				rs.next();
				int starID = rs.getInt("id");
				query = "Select id from genres where name = '"+genre+"'";
				rs = statement.executeQuery(query);
				rs.next();
				int genreID = rs.getInt("id");
				instruction = "Call add_movie("+movieID+", '"+title+"', "+year+", '"+director+"', " +
						"'"+banner+"', '"+trailer+"', "+starID+", "+genreID+")";
				statement.executeUpdate(instruction);
			}
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
