package org.example.business;

import java.util.Date;
import java.net.http.HttpRequest;

public class NoteDeFrais {
    private String[] etats = new String[10];
    private String auteur;
    private String creationDate;
    private int montant;
    private String libelle;
    private Date dateEmission;
    private Date dateRemboursement;
    private String fournisseur;
    private String remarque;
    private String[] justificatifs = new String[10];

    public NoteDeFrais(String auteur, String creationDate, int montant, String libelle, Date dateEmission, Date dateRemboursement, String fournisseur, String remarque) {
        this.auteur = auteur;
        this.creationDate = creationDate;
        this.montant = montant;
        this.libelle = libelle;
        this.dateEmission = dateEmission;
        this.dateRemboursement = dateRemboursement;
        this.fournisseur = fournisseur;
        this.remarque = remarque;
    }

    public NoteDeFrais() {
    }
}
