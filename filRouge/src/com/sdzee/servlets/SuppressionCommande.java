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
import com.sdzee.dao.CommandeDao;
import com.sdzee.dao.DAOFactory;

@WebServlet( "/suppressionCommande" )
public class SuppressionCommande extends HttpServlet {
    private static final long  serialVersionUID  = 1L;
    public static final String PARAM_ID_COMM     = "idcommande";
    public static final String SESSION_COMMANDES = "commandes";
    public static final String VUE               = "/listeCommandes";
    public static final String CONF_DAO_FACTORY  = "daofactory";
    private CommandeDao        commandeDao;

    public void init() throws ServletException {
        this.commandeDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCommandeDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Récupération du paramètre */
        Long idcommande = getParametreLong( request, PARAM_ID_COMM );

        /* Récupération de la Map des clients enregistrés en session */
        HttpSession session = request.getSession();
        @SuppressWarnings( "unchecked" )
        Map<Long, Client> commandes = (HashMap<Long, Client>) session.getAttribute( SESSION_COMMANDES );

        if ( idcommande != null && commandes != null ) {
            try {
                commandeDao.supprimerCommande( idcommande );
                /* Alors suppression de la commande de la Map */
                commandes.remove( idcommande );
                /*
                 * Et remplacement de l'ancienne Map en session par la nouvelle
                 */
                session.setAttribute( SESSION_COMMANDES, commandes );
                /* Redirection vers la fiche récapitulative */
                response.sendRedirect( request.getContextPath() + VUE );
            } catch ( Exception e ) {
                request.setAttribute( "erreur", "Impossible de supprimer cette commande : " + e.getMessage() );
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
