package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * La classe "Server" définie deux "public final
 * static" de type String ainsi que plusieurs objets
 * privés. De plus, elle contient un constructeur.
 */
public class Server {

    /**
     * La commande "REGISTER_COMMAND" est associée
     * au String "INSCRIRE" et il ne peut être modifié.
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";

    /**
     * La commande "LOAD_COMMAND" est associée au
     * String "CHARGER" et il ne peut être modifié.
     */
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;


    /**
     * Le constructeur de la classe "Server" prend en paramètre
     * le "port" de type "int" choisit par "ServeurLauncher". Il
     * envoie aussi une exception de type "IOException". De plus,
     * il initialize trois objets dont un qui est une "ArrayList".
     *
     * @param port port numéro 1337
     * @throws IOException lorsque la connection n'a pas réussie
     *                     entre le client et le serveur
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * La méthode "addEventHandler" ajoute un paramètre
     * de type "EventHandler" à la liste "handlers"
     *
     * @param h
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * La méthode "run" contient une boucle permettant
     * de connecter l'utilisateur au serveur pour exécuter
     * sa commande avant de le déconnecter.
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * La méthode "listen" attend que le client émette un message
     * qui est, par la suite, traité si aucune  exception n'a été
     * notifiée. Ensuite, il envoyé vers la méthode "alterHandlers"
     * pour avertir les "EventHandler" des changements occurés.
     *
     * @throws IOException lorsque la connection entre le client
     *                     et le serveur n'a pas réussie
     * @throws ClassNotFoundException lorsque la méthode ne
     *                                trouve pas la classe "Server"
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * La méthode "processCommandLine" permet de séparer une
     * ligne de type "String" en deux parties. La première
     * partie du "String" correspond à la commande  "cmd" alors
     * que la deuxième correspond à la partie argument "args".
     * Le tout est, ensuite, mis dans une paire "Pair<>(cmd, args)"
     *
     * @param line le texte qui définit la commande
     *             ainsi que l'article choisit par le client
     * @return l'inscription du client ainsi que le
     *         cours qu'il a choisit sous forme d'une
     *         pair "Pair<>(cmd, args)"
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * La méthode "disconnect" permet de
     * déconnecter le client tout en arrêtant
     * de générer de nouveaux "flux d'objects"
     *
     * @throws IOException lorsque qu'on n'arrive pas
     *                     à déconnecter le client du serveur
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * La méthode "handleEvents" permet de déterminer
     * laquelle des deux méthodes, "handleRegistration"
     * ou "handleLoadCourses", utiliser selon la requête
     * envoyé par le client.
     *
     * @param cmd l'incrisption au cours choisit par le client
     * @param arg le cours que le client veut choisir
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     * Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     * La méthode filtre les cours par la session spécifiée en argument.
     * Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     * La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     *
     * @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        ArrayList<Course> courseList = new ArrayList<Course>();
        try {
            File fileCourses = new File("src/main/java/server/data/cours.txt");
            try (Scanner scanner = new Scanner(fileCourses)) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    String parse[] = line.split("\t+");
                    if (parse[2].equals(arg)) {
                        courseList.add(new Course(parse[1], parse[0], parse[2]));
                    }
                }
            }
            try {
                objectOutputStream.writeObject(courseList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     * et renvoyer un message de confirmation au client.
     * La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {
            RegistrationForm form = (RegistrationForm) objectInputStream.readObject();

            String responseWorks = "Félicitation! Inscription réussi de " + form.getPrenom() + " " + form.getNom() + " au cours " + form.getCourse().getCode();
            String responseNotWorks = "Vous êtes déjà incrit au cours " + form.getCourse().getCode();
            Boolean found = false;

            System.out.println(responseWorks);

            //File regVerification = new File("..\\..\\..\\src\\main\\java\\server\\data\\inscription.txt");
            File regVerification = new File("./src/main/java/server/data/inscription.txt");
            Scanner scanner = new Scanner(regVerification);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String parse[] = line.split("\t+");
                System.out.print(parse[1]);
                System.out.println(parse[2]);
                if (parse[1].equals(form.getCourse().getCode()) & (parse[2].equals(form.getMatricule()))) {
                    found = true;
                    break;
                }
            }
            scanner.close();

            if (!found) {
                //FileWriter fw = new FileWriter("..\\..\\..\\src\\main\\java\\server\\data\\inscription.txt", true);
                FileWriter fw = new FileWriter("src/main/java/server/data/inscription.txt", true);
                PrintWriter out = new PrintWriter(fw);

                out.println(form.getCourse().getSession() + "\t" + form.getCourse().getCode() + "\t" + form.getMatricule() + "\t\t" + form.getPrenom() + "\t\t" +
                        form.getNom() + "\t" + form.getEmail());

                objectOutputStream.writeObject(responseWorks);
                out.close();
            } else
                objectOutputStream.writeObject(responseNotWorks);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

