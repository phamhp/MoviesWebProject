<HTML>
<BODY>
Hello!  The time is now
<%
    // This scriptlet generates HTML output
    
   if(session == null){
	response.sendRedirect("login.html");
}else{ 
	// This scriptlet declares and initializes "date"
	String email = (String) session.getAttribute("email");
    System.out.println( "Evaluating date now" );
    java.util.Date date = new java.util.Date();

    out.println( String.valueOf( date ));
    out.println( String.valueOf( email ));

}
%>
</BODY>
</HTML>