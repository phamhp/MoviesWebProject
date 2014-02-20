<!DOCTYPE html>
<html class="no-js" lang="en">
<head>
	<meta content="charset=utf-8">
	<title>Home</title>
	<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;">

  <!-- Demo CSS -->
	<link rel="stylesheet" href="css/demo.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="flexslider.css" type="text/css" media="screen" />

	<!-- Modernizr -->
  <script src="js/modernizr.js"></script>

</head>
<body class="loading">

  <div id="container" class="cf">
    <header role="navigation">
      <a class="logo" href="http://www.woothemes.com" title="WooThemes">
        <img src="images/logo.png" alt="WooThemes" />
	  </a>
	  <%
		  if(session == null){
			response.sendRedirect("login.html");
		}else{ 
			// This scriptlet declares and initializes "date"
			String email = (String) session.getAttribute("email");
		  
		   

		    out.println( "<h1> Welcome back " + String.valueOf( email ) + "</h1>");
		   
		}
	  %>
     
      <h2>The best responsive slider. Period.</h2>
	
	
		<form action="searchMovie" method="get">
		<INPUT class="button green:hover" TYPE="TEXT" NAME="searchTxt">
		<INPUT TYPE="SUBMIT" class="button green" VALUE="Search">
		</form>
      <h3 class="nav-header">Other Examples</h3>
      <nav>
        <ul>
        <li><a class="button green:hover" href="http://localhost:8080/ProjectCS122B/advanceSearch.html">Advance search</a></li>
          <li><a class="button green:hover" href="cart?Viewcart=1">View cart</a></li>
          <li><a class="button green:hover" href="logout">Logout</a></li>
         <li><a class="button green:hover" href="https://github.com/woothemes/FlexSlider/zipball/master">About us</a></li>
          
        </ul>
      </nav>
    </header>
    <div id="main" role="main">
      <section class="slider">
        <div class="flexslider">
          <ul class="slides">
            <li>
              <iframe id="player_1"  width="640" height="360" src="http://www.youtube.com/embed/5NV6Rdv1a3I" frameborder="0"  webkitAllowFullScreen mozallowfullscreen allowfullscreen></iframe>
  	    		</li>
  	    		<li>
  	    	   <iframe width="640" height="360" src="http://www.youtube.com/embed/e73J71RZRn8" frameborder="0" webkitAllowFullScreen mozallowfullscreen allowfullscreen></iframe>
  	    		</li>
  	    		<li>
  	    	     <iframe width="640" height="360" src="http://www.youtube.com/embed/NLWsK1ZFunA" frameborder="0" webkitAllowFullScreen mozallowfullscreen allowfullscreen></iframe>
  	    		</li>
  	    		<li>
  	    	    <iframe width="640" height="360" src="http://www.youtube.com/embed/nbp3Ra3Yp74" frameborder="0" webkitAllowFullScreen mozallowfullscreen allowfullscreen></iframe>
  	    		</li>
          </ul>
        </div>
      </section>
      <aside>
        <div class="cf">
          <h3>Browse</h3>
          <ul class="toggle cf">
            <li class="js"><a href="#view-js">By Genre</a></li>
            <li class="html"><a href="#view-html">By Title</a></li>
          </ul>
        </div>
        <div id="view-js" class="code">
          <div  ALIGN="CENTER">
<b><a href='SelectTitle?genre=Action&title=&sort=&sortby=&number=&numOfTimes=0'>Action- </a></b>
<b><a href='SelectTitle?genre=Advanture&title=&sort=&sortby=&number=&numOfTimes=0'>Advanture-   </a></b>
<b><a href='SelectTitle?genre=Animation&title=&sort=&sortby=&number=&numOfTimes=0'>Animation-    </a></b>
<b><a href='SelectTitle?genre=Biography&title=&sort=&sortby=&number=&numOfTimes=0'>Biography-    </a></b>
<b><a href='SelectTitle?genre=Comedy&title=&sort=&sortby=&number=&numOfTimes=0'>Comedy-    </a></b>
<b><a href='SelectTitle?genre=Crime&title=&sort=&sortby=&number=&numOfTimes=0'>Crime-    </a></b>
<b><a href='SelectTitle?genre=Documentary&title=&sort=&sortby=&number=&numOfTimes=0'>Documentary-   </a></b></br>
<b><a href='SelectTitle?genre=Drama&title=&sort=&sortby=&number=&numOfTimes=0'>Drama- </a></b>
<b><a href='SelectTitle?genre="Family&title=&sort=&sortby=&number=&numOfTimes=0'>Family- </a></b>
<b><a href='SelectTitle?genre=Fantasy&title=&sort=&sortby=&number=&numOfTimes=0'>Fantasy </a></b>
<b><a href='SelectTitle?genre=Film-Noir&title=&sort=&sortby=&number=&numOfTimes=0'>Film-Noir- </a></b>

<b><a href='SelectTitle?genre=History&title=&sort=&sortby=&number=&numOfTimes=0'>History- </a></b>
<b><a href='SelectTitle?genre=Horror&title=&sort=&sortby=&number=&numOfTimes=0'>Horror- </a></b>
<b><a href='SelectTitle?genre=Music&title=&sort=&sortby=&number=&numOfTimes=0'>Music- </a></b></br>
<b><a href='SelectTitle?genre=Musial&title=&sort=&sortby=&number=&numOfTimes=0'>Musial- </a></b>
<b><a href='SelectTitle?genre=Mystery&title=&sort=&sortby=&number=&numOfTimes=0'>Mystery- </a></b>
<b><a href='SelectTitle?genre=Romance&title=&sort=&sortby=&number=&numOfTimes=0'>Romance- </a></b>
<b><a href='SelectTitle?genre=Sci-fi&title=&sort=&sortby=&number=&numOfTimes=0'>Sci-Fi- </a></b>
<b><a href='SelectTitle?genre=Kid&title=&sort=&sortby=&number=&numOfTimes=0'>Kid- </a></b>
<b><a href='SelectTitle?genre=Thriller&title=&sort=&sortby=&number=&numOfTimes=0'>Thriller- </a></b>
<b><a href='SelectTitle?genre=War&title=&sort=&sortby=&number=&numOfTimes=0'>War- </a></b>
<b><a href='SelectTitle?genre=Westerns&title=&sort=&sortby=&number=&numOfTimes=0'>Western- </a></b>
</div>
        </div>
        <div id="view-html" class="code">
         <div  ALIGN="CENTER">
<b><a href='BrowseByTitle?letter=A'>A-</a></b>
<b><a href='BrowseByTitle?letter=B'>B-</a></b>
<b><a href='BrowseByTitle?letter=C'>C-</a></b>
<b><a href='BrowseByTitle?letter=D'>D-</a></b>
<b><a href='BrowseByTitle?letter=E'>E-</a></b>
<b><a href='BrowseByTitle?letter=F'>F-</a></b>
<b><a href='BrowseByTitle?letter=G'>G-</a></b>
<b><a href='BrowseByTitle?letter=H'>H-</a></b>
<b><a href='BrowseByTitle?letter=I'>I-</a></b></br>
<b><a href='BrowseByTitle?letter=J'>J-</a></b>
<b><a href='BrowseByTitle?letter=K'>K-</a></b>
<b><a href='BrowseByTitle?letter=L'>L-</a></b>
<b><a href='BrowseByTitle?letter=M'>M-</a></b>
<b><a href='BrowseByTitle?letter=N'>N-</a></b>
<b><a href='BrowseByTitle?letter=O'>O-</a></b>
<b><a href='BrowseByTitle?letter=P'>P-</a></b>
<b><a href='BrowseByTitle?letter=Q'>Q-</a></b>
<b><a href='BrowseByTitle?letter=R'>R-</a></b>
<b><a href='BrowseByTitle?letter=S'>S-</a></b></br>
<b><a href='BrowseByTitle?letter=T'>T-</a></b>
<b><a href='BrowseByTitle?letter=U'>U-</a></b>
<b><a href='BrowseByTitle?letter=V'>V-</a></b>
<b><a href='BrowseByTitle?letter=W'>W-</a></b>
<b><a href='BrowseByTitle?letter=X'>X-</a></b>
<b><a href='BrowseByTitle?letter=Y'>Y-</a></b>
<b><a href='BrowseByTitle?letter=Z'>Z-</a></b>
<b><a href='BrowseByTitle?letter=0'>0-</a></b>
<b><a href='BrowseByTitle?letter=1'>1-</a></b>
<b><a href='BrowseByTitle?letter=2'>2-</a></b>
<b><a href='BrowseByTitle?letter=3'>3-</a></b>
<b><a href='BrowseByTitle?letter=4'>4-</a></b>
<b><a href='BrowseByTitle?letter=5'>5-</a></b>
<b><a href='BrowseByTitle?letter=6'>6-</a></b>
<b><a href='BrowseByTitle?letter=7'>7-</a></b>
<b><a href='BrowseByTitle?letter=8'>8-</a></b>
<b><a href='BrowseByTitle?letter=9'>9-</a></b></br>

</div>
        </div>
      </aside>
    </div>

  </div>

  <!-- jQuery -->
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
  <script>window.jQuery || document.write('<script src="js/libs/jquery-1.7.min.js">\x3C/script>')</script>

  <!-- FlexSlider -->
  <script defer src="jquery.flexslider.js"></script>

  <script type="text/javascript">
    $(function(){
      SyntaxHighlighter.all();
    });
    $(window).load(function(){

      // Vimeo API nonsense
      var player = document.getElementById('player_1');
      $f(player).addEvent('ready', ready);

      function addEvent(element, eventName, callback) {
        (element.addEventListener) ? element.addEventListener(eventName, callback, false) : element.attachEvent(eventName, callback, false);
      }

      function ready(player_id) {
        var froogaloop = $f(player_id);

        froogaloop.addEvent('play', function(data) {
          $('.flexslider').flexslider("pause");
        });

        froogaloop.addEvent('pause', function(data) {
          $('.flexslider').flexslider("play");
        });
      }


      // Call fitVid before FlexSlider initializes, so the proper initial height can be retrieved.
      $(".flexslider")
        .fitVids()
        .flexslider({
          animation: "slide",
          useCSS: false,
          animationLoop: false,
          smoothHeight: true,
          start: function(slider){
            $('body').removeClass('loading');
          },
          before: function(slider){
            $f(player).api('pause');
          }
      });
    });
  </script>

   <!-- Syntax Highlighter -->
	<script type="text/javascript" src="js/shCore.js"></script>
	<script type="text/javascript" src="js/shBrushXml.js"></script>
	<script type="text/javascript" src="js/shBrushJScript.js"></script>

  <!-- Optional FlexSlider Additions -->
    <script src="js/froogaloop.js"></script>
	<script src="js/jquery.fitvid.js"></script>
	<script src="js/demo.js"></script>

</body>
</html>