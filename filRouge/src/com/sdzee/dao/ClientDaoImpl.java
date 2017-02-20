package com.sdzee.dao;

import static com.sdzee.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.sdzee.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sdzee.beans.Client;

public class ClientDaoImpl implements ClientDao {
    private DAOFactory          daoFactory;
    private static final String SQL_SELECT_PAR_EMAIL = "SELECT * FROM Client WHERE email = ?";
    private static final String SQL_SELECT_PAR_ID    = "SELECT * FROM Client WHERE idclient = ?";
    private static final String SQL_INSERT           = "INSERT INTO Client  (prenom, nom, adresse, email, telephone, image) VALUES (?, ?, ?, ?, ?, ? )";
    private static final String SQL_SUPP             = "DELETE FROM Client WHERE idclient = ?";
    private static final String SQL_LISTE            = "SELECT * FROM Client ORDER BY nom";

    /*
     * Implémentation de la méthode trouver() définie dans l'interface ClientDao
     */
    @Override
    public Client trouverClient( String email ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Client client = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_EMAIL,
                    false, email );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            if ( resultSet.next() ) {
                client = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return client;
    }

    /*
     * Implémentation de la méthode creer() définie dans l'interface ClientDao
     */
    @Override
    public void creerClient( Client client ) throws IllegalArgumentException, DAOException {
        java.sql.Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    client.getPrenom(), client.getNom(), client.getAdresse(), client.getEmail(), client.getTelephone(),
                    client.getImage() );
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création du client, aucune ligne ajoutée dans la table." );
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                /*
                 * Puis initialisation de la propriété id du bean Client avec sa
                 * valeur
                 */
                client.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException(
                        "Échec de la création de l'client en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    @Override
    public void supprimerClient( Long idclient ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        if ( idclient > 0 && idclient != null ) {
            try {
                connexion = (Connection) daoFactory.getConnection();
                preparedStatement = (PreparedStatement) initialisationRequetePreparee( connexion, SQL_SUPP, false,
                        idclient );
                int statut = preparedStatement.executeUpdate();
                if ( statut != 1 )
                    throw new DAOException( "Échec de la suppression" );
            } catch ( SQLException e ) {
                throw new DAOException( e );
            } finally {
                fermeturesSilencieuses( preparedStatement, connexion );
            }
        } else
            throw new DAOException( "Le client n'a pas fourni d'ID" );
    }

    @Override
    public ArrayList<Client> listerClients() throws DAOException {
        java.sql.Connection connexion = null;
        java.sql.PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Client> clients = new ArrayList<>();

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_LISTE, false );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while ( resultSet.next() ) {
                Client client = map( resultSet );
                clients.add( client );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return clients;
    }

    public ClientDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des clients (un ResultSet) et
     * un bean Client.
     */
    private static Client map( ResultSet resultSet ) throws SQLException {
        Client client = new Client();
        try {
            client.setId( resultSet.getLong( "idclient" ) );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        client.setEmail( resultSet.getString( "email" ) );
        client.setNom( resultSet.getString( "nom" ) );
        client.setAdresse( resultSet.getString( "adresse" ) );
        client.setImage( resultSet.getString( "image" ) );
        client.setPrenom( resultSet.getString( "prenom" ) );
        client.setTelephone( resultSet.getString( "telephone" ) );
        return client;
    }

    @Override
    public Client trouverID( Long id ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Client client = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = (Connection) daoFactory.getConnection();
            preparedStatement = (PreparedStatement) initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID,
                    false, id );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            if ( resultSet.next() ) {
                client = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return client;
    }
}
