<head>
	<meta charset="utf-8" />
</head>
	<body>
        <c:import url="/inc/menu.jsp" />
        <div id="corps">
            <p class="info">${ form.resultat }</p>
            <p>Nom : <c:out value="${ client.nom }"/></p>
            <p>Pr�nom : <c:out value="${ client.prenom }"/></p>
            <p>Adresse : <c:out value="${ client.adresse }"/></p>
            <p>Num�ro de t�l�phone : <c:out value="${ client.telephone }"/></p>
            <p>Email : <c:out value="${ client.email }"/></p>
            <p>Image : <c:out value="${ client.image }"/></p>
        </div>
    </body>
</html>