<!DOCTYPE html>
<html>
	<head>
        <link type="text/css" rel="stylesheet" href="style.css" />
    </head>
   <body>
        <c:import url="/inc/menu.jsp" />
        <div>
            <form method="post" action="<c:url value="/creationClient"/>" enctype="multipart/form-data" accept-charset="UTF-8">
                <fieldset>
                    <legend>Informations client</legend>
                    <c:import url="/inc/inc_client_form.jsp" />
                </fieldset>  
                <p class="info">${ form.resultat }</p>                
                <input type="reset" value="Remettre à zéro" />
                <input type="submit" value="Valider"  /> <br />
            </form>
        </div>
    </body>
</html>