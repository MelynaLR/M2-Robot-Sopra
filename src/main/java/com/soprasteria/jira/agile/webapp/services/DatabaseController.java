package com.soprasteria.jira.agile.webapp.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseController {
    // Les informations de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/jiraAPI";
    private static final String UTILISATEUR = "admin";
    private static final String MOT_DE_PASSE = "KOxNGMYzDuBKmYsNpdxP";

    // Méthode pour établir une connexion à la base de données
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
    }

    // Exemple d'utilisation
    public static void main(String[] args) {
        try {
            Connection connexion = getConnection();
            if (connexion != null) {
                System.out.println("Connexion réussie !");
                // Faire d'autres opérations avec la base de données ici
                // ...
                // N'oubliez pas de fermer la connexion lorsque vous avez fini
                connexion.close();
            } else {
                System.err.println("La connexion a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //autres classes pour manipuler la bdd
}
