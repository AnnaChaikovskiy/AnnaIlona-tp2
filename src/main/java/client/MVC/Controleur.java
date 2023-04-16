package client.MVC;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * La classe "Controleur" permet de faire le lien entre l'interface graphique
 * et les données utile pour l'inscription de l'utilisateur à ses cours.
 */
public class Controleur {
    /**
     * Permet d'assigner à "REGISTER_COMMAND" la valeur "INSCRIRE" de
     * manière finale. Celle-ci ne pourra être changée ultérieurement.
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";
    /**
     * Permet d'assigner à "LOAD_COMMAND" la valeur "CHARGER" de
     * manière finale. Celle-ci ne pourra être changée ultérieurement.
     */
    public final static String LOAD_COMMAND = "CHARGER";

    /**
     * La méthode "CourseRequest" permet de retourner une liste des cours
     * pour la session sélectionnée par l'utilisateur du programme.
     *
     * @param session La session sélectionnée par l'utilisateur du programme
     * @return Une liste contenant les cours ainsi que leurs informations de la session choisie
     * @throws IOException
     * @throws ClassNotFoundException Est prise en compte si aucune classe n'est trouvée
     */
    public static ArrayList<Course> CourseRequest(String session) throws IOException, ClassNotFoundException {

        Socket server = new Socket("localhost", 1337);
        ArrayList<Course> courseList = new ArrayList<Course>();
        String p = (LOAD_COMMAND + " " + session);


        ObjectOutputStream objectOutputStream = new ObjectOutputStream(server.getOutputStream());
        objectOutputStream.writeObject(p);

        ObjectInputStream objectInputStream = new ObjectInputStream(server.getInputStream());
        Object object = objectInputStream.readObject();

        courseList = (ArrayList<Course>) object;
        for (int i = 0; i < courseList.size(); i++) {
            String line = String.valueOf(i+1) + ".   " + courseList.get(i).getCode() + "     " + courseList.get(i).getName();
            System.out.println(line);
        }
        objectOutputStream.close();
        objectInputStream.close();
        server.close();

        return courseList;
    }

    /**
     * La méthode "RegistrationRequest" permet de s'inscrire
     * au cours choisit par l'utilisateur du programme.
     *
     * @param name Prénom de l'élève qui remplie le formulaire d'inscription
     * @param familyName Nom de famille de l'élève qui remplie le formulaire d'inscription
     * @param email Email de l'élève qui remplie le formulaire d'inscription
     * @param studentNumber Numéro de matricule de l'élève qui remplie le formulaire d'inscription
     * @param course Cours choisit par l'élève qui remplie le formulaire d'inscription
     * @throws IOException
     * @throws ClassNotFoundException Est prise en compte si aucune classe n'est trouvée
     */
    public static void RegistrationRequest(String name, String familyName, String email, String studentNumber, Course course) throws IOException, ClassNotFoundException {
        String p = REGISTER_COMMAND;

        Socket server = new Socket("localhost", 1337);

        ObjectOutputStream  objectOutputStream = new ObjectOutputStream(server.getOutputStream());
        objectOutputStream.writeObject(p);

        RegistrationForm InscriptionForm = new RegistrationForm(name, familyName, email, studentNumber, course);
        objectOutputStream.writeObject(InscriptionForm);

        ObjectInputStream  objectInputStream = new ObjectInputStream(server.getInputStream());
        Object object = objectInputStream.readObject();

        System.out.println((String)object);

        objectOutputStream.close();
        objectInputStream.close();
        server.close();
    }

}