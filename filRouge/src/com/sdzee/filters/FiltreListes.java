package com.sdzee.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Client;
import com.sdzee.beans.Commande;
import com.sdzee.dao.ClientDao;
import com.sdzee.dao.CommandeDao;
import com.sdzee.dao.DAOFactory;

/**
 * Servlet Filter implementation class FiltreListes
 */
@WebFilter( "/*" )
public class FiltreListes implements Filter {
    public static final String SESSION_CLIENTS   = "clients";
    public static final String SESSION_COMMANDES = "commandes";
    public static final String CONF_DAO_FACTORY  = "daofactory";
    private ClientDao          clientDao;
    private CommandeDao        commandeDao;
    FilterConfig               config;

    public void setFilterConfig( FilterConfig config ) {
        this.config = config;
    }

    public FilterConfig getFilterConfig() {
        return config;
    }

    public void init( FilterConfig fConfig ) throws ServletException {
        setFilterConfig( fConfig );
        ServletContext context = getFilterConfig().getServletContext();
        this.clientDao = ( (DAOFactory) context.getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        this.commandeDao = ( (DAOFactory) context.getAttribute( CONF_DAO_FACTORY ) ).getCommandeDao();
    }

    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain )
            throws IOException, ServletException {
        /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        @SuppressWarnings( "unchecked" )
        HashMap<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( SESSION_CLIENTS );
        /*
         * Si aucune map n'existe, alors initialisation d'une nouvelle map
         */
        if ( clients == null ) {
            clients = new HashMap<Long, Client>();
        }

        ArrayList<Client> clientList = clientDao.listerClients();
        for ( Client client : clientList ) {
            clients.put( client.getId(), client );
        }

        /* Et enfin (ré)enregistrement de la map en session */
        session.setAttribute( SESSION_CLIENTS, clients );

        @SuppressWarnings( "unchecked" )
        HashMap<Long, Commande> commandes = (HashMap<Long, Commande>) session.getAttribute( SESSION_COMMANDES );
        /*
         * Si aucune map n'existe, alors initialisation d'une nouvelle map
         */
        if ( commandes == null ) {
            commandes = new HashMap<Long, Commande>();
        }

        ArrayList<Commande> comListe = commandeDao.listerCommandes();
        for ( Commande comm : comListe ) {
            commandes.put( comm.getIdCommande(), comm );
        }

        /* Et enfin (ré)enregistrement de la map en session */
        session.setAttribute( SESSION_COMMANDES, commandes );
        // pass the request along the filter chain
        chain.doFilter( request, response );
    }

    public void destroy() {
        // TODO Auto-generated method stub
    }

}
