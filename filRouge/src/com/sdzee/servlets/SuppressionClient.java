package com.sdzee.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Client;
import com.sdzee.dao.ClientDao;
import com.sdzee.dao.DAOFactory;

@WebServlet( "/supressionClient" )
public class SuppressionClient extends HttpServlet {
    private static final long  serialVersionUID = 1L;
    public static final String PARAM_ID_CLIENT  = "idclient";
    public static final String SESSION_CLIENTS  = "clients";

    public static final String VUE              = "/listeClients";
    public static final String CONF_DAO_FACTORY = "daofactory";
    private ClientDao          clientDao;

    public void init() throws ServletException {
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Récupération du paramètre */
        Long idclient = getParametreLong( request, PARAM_ID_CLIENT );

        /* Récupération de la Map des clients enregistrés en session */
        HttpSession session = request.getSession();
        @SuppressWarnings( "unchecked" )
        Map<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( SESSION_CLIENTS );

        if ( idclient != null && clients != null ) {
            try {
                clientDao.supprimerClient( idclient );
                /* Alors suppression du client de la Map */
                clients.remove( idclient );
                session.setAttribute( SESSION_CLIENTS, clients );
                /* Redirection vers la fiche récapitulative */
                response.sendRedirect( request.getContextPath() + VUE );
            } catch ( Exception e ) {
                request.setAttribute( "erreur", "Impossible de supprimer ce client : il est lié à une commande" );
                this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
            }
        }
    }

    private static Long getParametreLong( HttpServletRequest request, String nomChamp ) {
        Long valeur = Long.parseLong( request.getParameter( nomChamp ) );
        if ( !( valeur > 0 ) ) {
            return null;
        } else {
            return valeur;
        }
    }
}
