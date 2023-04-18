package server.models;

import java.io.Serializable;

/**
 * Cette classe initialise les cinqs variables qui
 * sont demandés dans le formulaire d'inscription
 * de l'Université de Montréal.
 */
public class RegistrationForm implements Serializable {
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private Course course;


    /**
     * Ce constructeur permet d'attribuer des valeurs aux cinq
     * paramètres qu'il possède. Ceux-ci sont de type "String"
     * pour les paramètres "prenom", "nom", "email" et "matricule".
     * Pour le dernier paramètre, il est de type "Course".
     *
     * @param prenom prénom du client
     * @param nom nom du client
     * @param email email du client
     * @param matricule matricule du client
     * @param course cours choisit par le client
     */
    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }

    /**
     * Retourne la valeur de
     * la variable privée "prenom".
     * Celle-ci est de type "String".
     *
     * @return le prénom du client
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Met à jour la valeur de la
     * variable privée "prenom"
     * @param prenom le prénom du client.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Retourne la valeur de
     * la variable privée "nom".
     * Celle-ci est de type "String".
     *
     * @return le nom du client
     */
    public String getNom() {
        return nom;
    }

    /**
     * Met à jour la valeur de la
     * variable privée "nom".
     *
     * @param nom le nom du client
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne la valeur de
     * la variable privée "prenom".
     * Celle-ci est de type "email".
     *
     * @return l'email du client
     */
    public String getEmail() {
        return email;
    }

    /**
     * Met à jour la valeur de la
     * variable privée "email".
     *
     * @param email l'email du client
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne la valeur de
     * la variable privée "prenom".
     * Celle-ci est de type "matricule".
     *
     * @return le matricule du client
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * Met à jour la valeur de la
     * variable privée "matricule".
     *
     * @param matricule le matricule du client
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * Retourne la valeur de
     * la variable privée "course".
     * Celle-ci est de type "Course".
     *
     * @return le cours choisit
     *         par le client
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Met à jour la valeur de la
     * variable privée "course".
     *
     * @param course le cours choisit
     *               par le client
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * La méthode "toString()" retourne les valeurs des
     * variables "prenom", "nom", "email", "matricule" et
     * "course" de la classe "RegistrationForm" dans le
     * format de type "string" lisible pour l'utilisateur.
     */
    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
}
