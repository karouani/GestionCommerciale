package com.sdzee.dao;

import java.util.ArrayList;

import com.sdzee.beans.Commande;

public interface CommandeDao {
    void creerCommande( Commande commande ) throws DAOException;

    void supprimerCommande( Long idcommande ) throws DAOException;

    ArrayList<Commande> listerCommandes() throws DAOException;

}
