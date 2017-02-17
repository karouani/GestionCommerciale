	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Bienvenue</title>
        <link type="text/css" rel="stylesheet" href="style.css" />
    </head>
    <style>
    body, p, legend, label, input {
    font: normal 8pt verdana, helvetica, sans-serif;
}

fieldset {
    padding: 10px;
    border: 1px #0568CD solid;
}

legend {
    font-weight: bold;
    color: #0568CD;
}


form label {
    float: left;
    width: 200px;
    margin: 3px 0px 0px 0px;
}

form input {
    margin: 3px 3px 0px 0px;
    border: 1px #999 solid;
}

form input.sansLabel {
    margin-left: 200px;
}

form .requis {
    color: #c00;
}

.erreur {
    color: #900;
}

.succes {
    color: #090;
}

/* Tableaux -------------------------------------------------------------------------------------*/
table{
	border-collapse: collapse;
}
tr.pair{
	background-color: #efefef;
}
tr.impair{
	background-color: #fff;	
}
th{
	color: #0568CD;
	border: 1px solid #0568CD;
	padding: 5px;
}
th.action{
	border: 1px solid #900;
	color: #900;
}
td{
	border: 1px solid #ddd;
	padding: 5px;
}
td.action{
	text-align: center;
}
    </style>
<div id="menu">
    <p><a href="<c:url value="/creationClient"/>">Créer un nouveau client</a></p>
    <p><a href="<c:url value="/creationCommande"/>">Créer une nouvelle commande</a></p>
    <p><a href="<c:url value="/listeClients"/>">Voir les clients existants</a></p>
    <p><a href="<c:url value="/listeCommandes"/>">Voir les commandes existantes</a></p>
</div>