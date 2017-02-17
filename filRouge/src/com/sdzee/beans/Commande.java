package com.sdzee.beans;

import java.io.Serializable;

public class Commande implements Serializable {
    private static final long serialVersionUID = 1L;
    /* Propriétés du bean */
    private Long              idCommande;
    private Client            client;
    private String            date;
    private Double            montant;
    private String            modePaiement;
    private String            statutPaiement;
    private String            modeLivraison;
    private String            statutLivraison;

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande( Long idCommande ) {
        this.idCommande = idCommande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient( Client client ) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant( Double montant ) {
        this.montant = montant;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement( String modePaiement ) {
        this.modePaiement = modePaiement;
    }

    public String getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement( String statutPaiement ) {
        this.statutPaiement = statutPaiement;
    }

    public String getModeLivraison() {
        return modeLivraison;
    }

    public void setModeLivraison( String modeLivraison ) {
        this.modeLivraison = modeLivraison;
    }

    public String getStatutLivraison() {
        return statutLivraison;
    }

    public void setStatutLivraison( String statutLivraison ) {
        this.statutLivraison = statutLivraison;
    }
}