<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Liste des clients existants</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
     <body>
        <c:import url="/inc/menu.jsp" />
        <div id="corps">
        <br>
        <c:if test="${ !empty erreur }">
        <p class="erreur">
        <c:out value="${ erreur }"></c:out>
        </p>
        <br>
        </c:if>
        
        <c:choose>
            <%-- Si aucun client n'existe en session, affichage d'un message par défaut. --%>
            <c:when test="${ empty sessionScope.clients }">
                <p class="erreur">Aucun client enregistré.</p>
            </c:when>            
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Adresse</th>
                    <th>Téléphone</th>
                    <th>Email</th>
                    <th>Image</th>
                    <th class="action">Action</th>                    
                </tr>
                <%-- Parcours de la Map des clients en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ sessionScope.clients }" var="mapClients" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage des propriétés du bean Client, qui est stocké en tant que valeur de l'entrée courante de la map --%>
                    <td><c:out value="${ mapClients.value.nom }"/></td>
                    <td><c:out value="${ mapClients.value.prenom }"/></td>
                    <td><c:out value="${ mapClients.value.adresse }"/></td>
                    <td><c:out value="${ mapClients.value.telephone }"/></td>
                    <td><c:out value="${ mapClients.value.email }"/></td>
                    <td>
                        <%-- On ne construit et affiche un lien vers l'image que si elle existe. --%>
                        <c:if test="${ !empty mapClients.value.image }">
                            <c:set var="image"><c:out value="${ mapClients.value.image }"/></c:set>
                            <a href="<c:url value="/images/${ image }"/>">Voir</a>
                        </c:if>
                    </td>
                    <%-- Lien vers la servlet de suppression, avec passage du nom du client - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param/>. --%>
                    <td class="action">
                        <a href="<c:url value="/suppressionClient"><c:param name="idclient" value="${ mapClients.key }" /></c:url>">
                            <img src="<c:url value="/inc/supprimer.png"/>" alt="Supprimer" />
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
        </div>
    </body>
</html>