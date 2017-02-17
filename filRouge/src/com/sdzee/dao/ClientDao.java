package com.sdzee.dao;

import java.util.ArrayList;

import com.sdzee.beans.Client;

public interface ClientDao {

    void creerClient( Client client ) throws DAOException;

    Client trouverClient( String email ) throws DAOException;

    Client trouverID( Long id ) throws DAOException;

    void supprimerClient( Long idclient ) throws DAOException;

    ArrayList<Client> listerClients() throws DAOException;
}
