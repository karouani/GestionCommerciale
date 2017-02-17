package com.sdzee.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Client;
import com.sdzee.beans.Commande;
import com.sdzee.dao.ClientDao;
import com.sdzee.dao.DAOFactory;

public class ListeCommandes extends HttpServlet {
    private static final long  serialVersionUID  = 1L;
    public static final String VUE               = "/WEB-INF/listeCommandes.jsp";
    public static final String CONF_DAO_FACTORY  = "daofactory";
    public static final String SESSION_COMMANDES = "commandes";
    private ClientDao          clientDao;

    public void init() throws ServletException {
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        HttpSession session = request.getSession();
        @SuppressWarnings( "unchecked" )
        HashMap<Long, Commande> commandes = (HashMap<Long, Commande>) session.getAttribute( SESSION_COMMANDES );

        for ( Map.Entry<Long, Commande> commande : commandes.entrySet() ) {
            Client client = commande.getValue().getClient();
            client = clientDao.trouverID( client.getId() );
            commande.getValue().setClient( client );
        }
        session.setAttribute( SESSION_COMMANDES, commandes );
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}
