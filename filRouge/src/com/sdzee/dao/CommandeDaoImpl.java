package com.sdzee.dao;

import static com.sdzee.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.sdzee.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sdzee.beans.Client;
import com.sdzee.beans.Commande;

public class CommandeDaoImpl implements CommandeDao {
    private DAOFactory          daoFactory;
    private static final String SQL_INSERT = "INSERT INTO commande  (date, montant, modePaiement, statutPaiement, modeLivraison, statutLivraison, idclient) VALUES (?, ?, ?, ?, ?, ?, ? )";
    private static final String SQL_SUPP   = "DELETE FROM commande WHERE idcommande = ?";
    private static final String SQL_LISTE  = "SELECT * FROM commande ORDER BY date";

    /*
     * Implémentation de la méthode creer() définie dans l'interface CommandeDao
     */
    @Override
    public void creerCommande( Commande commande ) throws DAOException {
        java.sql.Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    commande.getDate(), commande.getMontant(), commande.getModePaiement(), commande.getStatutPaiement(),
                    commande.getModeLivraison(), commande.getStatutLivraison(), commande.getClient().getId() );
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de la commande, aucune ligne ajoutée dans la table." );
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                /*
                 * Puis initialisation de la propriété id du bean Commande avec
                 * sa valeur
                 */
                commande.setIdCommande( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException(
                        "Échec de la création de la commande en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    @Override
    public void supprimerCommande( Long idcommande ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        if ( idcommande > 0 && idcommande != null ) {
            try {
                connexion = (Connection) daoFactory.getConnection();
                preparedStatement = (PreparedStatement) initialisationRequetePreparee( connexion, SQL_SUPP, false,
                        idcommande );
                int statut = preparedStatement.executeUpdate();
                if ( statut != 1 )
                    throw new DAOException( "Échec de la suppression" );
            } catch ( SQLException e ) {
                throw new DAOException( e );
            } finally {
                fermeturesSilencieuses( preparedStatement, connexion );
            }
        } else
            throw new DAOException( "La commande n'a pas fourni d'ID" );
    }

    public CommandeDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Commande> listerCommandes() throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Commande> commandes = new ArrayList<>();

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = (Connection) daoFactory.getConnection();
            preparedStatement = (PreparedStatement) initialisationRequetePreparee( connexion, SQL_LISTE, false );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while ( resultSet.next() ) {
                Commande commande = map( resultSet );
                commandes.add( commande );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return commandes;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des clients (un ResultSet) et
     * un bean Client.
     */
    private static Commande map( ResultSet resultSet ) throws SQLException {
        Commande commande = new Commande();
        try {
            commande.setIdCommande( resultSet.getLong( "idcommande" ) );
        } catch ( Exception e ) {
            System.out.println( "Erreur id line 123" );
        }
        Long idCli = ( resultSet.getLong( "idclient" ) );
        Client client = new Client();
        client.setId( idCli );
        commande.setClient( client );
        commande.setDate( resultSet.getString( "date" ) );
        commande.setModeLivraison( resultSet.getString( "modeLivraison" ) );
        commande.setModePaiement( resultSet.getString( "modePaiement" ) );
        commande.setMontant( resultSet.getDouble( "montant" ) );
        commande.setStatutLivraison( resultSet.getString( "statutLivraison" ) );
        commande.setStatutPaiement( resultSet.getString( "statutPaiement" ) );

        return commande;
    }
}
