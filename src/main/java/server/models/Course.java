package server.models;

import java.io.Serializable;

/**
 * Cette classe initialise les trois variables
 * privées présentent dans la classe "Course"
 * qui sont "name", "code" et "session".
 *
 */
public class Course implements Serializable {

    private String name;
    private String code;
    private String session;

    /**
     * Ce constructeur permet d'attribuer des valeurs aux trois
     * paramètres qu'il possède. Ceux-ci sont de type "String"
     * et se nomment respectivement "name", "code" et "session".
     *
     * @param name le nom du cours
     * @param code le code du cours
     * @param session la session durant
     *                laquelle se donne
     *                le cours
     */
    public Course(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }

    /**
     * Cette méthode permet de chercher
     * et de retourner la valeur de la
     * variable privée "name".
     *
     * @return le nom du cours
     */
    public String getName() {
        return name;
    }

    /**
     * Cette méthode permet de mettre
     * à jour la valeur privée "name".
     *
     * @param name le nom du cours
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Cette méthode permet de chercher
     * et de retourner la valeur de la
     * variable privée "code".
     *
     * @return le code du cours
     */
    public String getCode() {
        return code;
    }

    /**
     * Cette méthode permet de mettre
     * à jour la valeur privée "code".
     *
     * @param code le code du cours
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Cette méthode permet de chercher
     * et de retourner la valeur de la
     * variable privée "session".
     *
     * @return la session durant
     *         laquelle se donne
     *         le cours
     */
    public String getSession() {
        return session;
    }

    /**
     *  Cette méthode permet de mettre
     * à jour la valeur privée "session".
     *
     * @param session la session durant
     *                laquelle se donne
     *                le cours
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * La méthode "toString()" retourne les valeurs
     * des variables "name", "code" et "session, de la
     * classe "Course", dans le format de type "string"
     * lisible pour l'utilisateur. C'est-à-dire, le nom
     * du cours choisit et son code ainsi que la session
     * durant laquelle il se donne.
     *
     */
    @Override
    public String toString() {
        return "Course{" +
                "name=" + name +
                ", code=" + code +
                ", session=" + session +
                '}';
    }
}
