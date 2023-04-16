package client.MVC;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.models.Course;
import server.models.RegistrationForm;


/**
 * La classe "Vue" permet de créer l'interface
 * graphique d'un site pour s'inscription à des cours
 * de l'Université de Montréal
 */
public class Vue extends Application {

    /**
     * La méthode "main" permet de
     * lancer l'exécution du programme
     * @param args rêquete du client en format
     *             String stocké dans un tableau
     */
    public static void main(String[] args) {
        Vue.launch(args);
    }

    @Override
    /**
     * La méthode "start" permet d'initialiser et
     * de modifier les éléments qui se trouve dans
     * le "Stage" ainsi que la "Scene" de l'interface.
     * C'est-à-dire dans la fenêtre du programme.
     */
    public void start(Stage primaryStage) throws Exception {


        /**
         * Outils de JavaFX pour initialiser la
         * fenêtre où sera contenu le programme
         */
        Pane root = new Pane();

        /**
         * Initialisation de la scène avec ses dimensions
         */
        Scene scene = new Scene(root, 800, 550);


        /**
         * Change de couleur la scène de l'interface
         */
        root.setStyle("-fx-background-color: #EFECDF");


        /**
         * Initialisation du titre "Liste des cours"
         * pour la partie gauche de l'interface et
         * modification de l'allure du titre.
         */
        Text titre1 = new Text("Liste des cours");
        titre1.setFont(Font.font("Purisa", 20));
        titre1.setLayoutX(135);
        titre1.setLayoutY(35);
        root.getChildren().add(titre1);


        /**
         * Initialisation du tableau interactif présentant
         * la liste de cours selon la session sélectionnée.
         * De plus, ajout de précision sur l'allure du tableau
         * ainsi que sur sa position dans la scène.
         */
        TableView<Course> screen = new TableView<Course>();
        screen.setEditable(true);

        TableColumn code = new TableColumn<Course, String>("Code");
        code.setCellValueFactory(new PropertyValueFactory<Course,String>("code"));

        TableColumn name = new TableColumn<Course, String>("Cours");
        name.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));

        screen.getColumns().add(code);
        screen.getColumns().add(name);

        screen.setLayoutX(20);
        screen.setLayoutY(50);
        screen.setPrefHeight(425);
        screen.setPrefWidth(360);

        screen.setColumnResizePolicy(screen.CONSTRAINED_RESIZE_POLICY);
        root.getChildren().add(screen);


        /**
         * Initialisation des deux séparateurs
         * (vertical et horizontal) entre les
         * deux partie du programme. Précision
         * sur leurs positions dans la fenêtre.
         */
        Line sepV = new Line();
        sepV.setStartX(400);
        sepV.setEndX(400);
        sepV.setStartY(0);
        sepV.setEndY(550);
        sepV.setStrokeWidth(3);
        sepV.setStroke(Color.WHITE);
        root.getChildren().add(sepV);

        Line sepH = new Line();
        sepH.setStartX(0);
        sepH.setEndX(400);
        sepH.setStartY(490);
        sepH.setEndY(490);
        sepH.setStrokeWidth(3);
        sepH.setStroke(Color.WHITE);
        root.getChildren().add(sepH);


        /**
         * Initialisation du bouton interactif "charger"
         * ainsi que de la liste déroulante mettant en
         * avant les choix de sessions possible. Précision
         * sur l'allure des boutons.
         */
        Button charger = new Button("charger");
        charger.setLayoutX(275);
        charger.setLayoutY(508);
        root.getChildren().add(charger);

        ObservableList<String> items = FXCollections.observableArrayList("Hiver", "Ete", "Automne");
        ChoiceBox<String> session = new ChoiceBox<>(items);

        session.setLayoutX(50);
        session.setLayoutY(508);
        session.setPrefWidth(100);
        root.getChildren().add(session);

        /**
         * Initialisation du titre "Formulaire d'inscription"
         * de la partie droite de l'interface ainsi que son allure.
         */
        Text titre2 = new Text("Formulaire d'inscription");
        titre2.setFont(Font.font("Purisa", 20));
        titre2.setLayoutX(500);
        titre2.setLayoutY(35);
        root.getChildren().add(titre2);


        /**
         * Initialisation du bouton d'envoi du
         * formulaire d'inscription en apportant
         * des précisions sur l'allure de celui-ci.
         */
        Button envoyer = new Button("envoyer");
        envoyer.setLayoutX(600);
        envoyer.setLayoutY(225);
        envoyer.setPrefWidth(100);
        root.getChildren().add(envoyer);




        /**
         * Initialisation de la zone de remplissage du formulaire
         * d'inscription (zone de textes pour le prénom, nom de
         * famille, email et matricule de l'étudiant). Partie
         * droite de l'interface graphique.
         */
        ArrayList<String> liste = new ArrayList<String>(4);
        liste.add("Prénom");
        liste.add("Nom");
        liste.add("Email");
        liste.add("Matricule");

        int addition = 75;
        int index = 0;

        TextField userName = new TextField();
        userName.setLayoutX(575);
        userName.setLayoutY(75);
        root.getChildren().add(userName);

        TextField userLastName = new TextField();
        userLastName.setLayoutX(575);
        userLastName.setLayoutY(110);
        root.getChildren().add(userLastName);

        TextField userEmail = new TextField();
        userEmail.setLayoutX(575);
        userEmail.setLayoutY(145);
        root.getChildren().add(userEmail);

        TextField userID = new TextField();
        userID.setLayoutX(575);
        userID.setLayoutY(180);
        root.getChildren().add(userID);


        for (int i = 0; i <=3; i++) {
            String element = liste.get(index);
            Text newTitle = new Text(element);
            newTitle.setLayoutX(475);
            newTitle.setLayoutY(addition+15);

            addition = addition + 35;
            index = index + 1;

            root.getChildren().add(newTitle);
        }

        /**
         * Cette partie de code permet d'appeler le bouton
         * "envoyer" en assignant une action. Celle-ci appelle
         * le méthode "RegistrationRequest" qui inscrit l'élève
         * à un cours de l'Université de Montréal.
         */
        envoyer.setOnAction(actionEvent -> {
            String finalUsernNameString = userName.getText();
            ;
            String finalUserLastNameString = userLastName.getText();
            String finalUserEmailString = userEmail.getText();
            String finalUserIDString = userID.getText();
            Course object = screen.getSelectionModel().getSelectedItem();

            if (validation(screen, userName, userLastName, userEmail, userID)) {
                try {
                    Controleur.RegistrationRequest(finalUsernNameString, finalUserLastNameString, finalUserEmailString, finalUserIDString, object);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        /**
         * Mise à jour des modifications apportées à la scène
         * ainsi que son apparaition à l'écran du client
         */
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.setScene(scene);
        primaryStage.show();

        final ArrayList<Course>[] courseArrayList = new ArrayList[]{new ArrayList<>()};
        charger.setOnAction((actionEvent) -> {
            String yourSession = session.getValue();
            try {
                courseArrayList[0] = Controleur.CourseRequest(yourSession);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            screen.getItems().clear();
            for (int i=0; i < courseArrayList[0].size() ; i++) {
                screen.getItems().add(courseArrayList[0].get(i));
            }

        });
    }

    public static Boolean validation(TableView table, TextField name, TextField lastName, TextField email, TextField userID) {
        String message = "";

        Course object = (Course) table.getSelectionModel().getSelectedItem();
        try {
            if (object == null) {
                message = "Veuillez sélectionner un cours.";
                throw new Exception();
            }
            if (name.getText().isEmpty() || lastName.getText().isEmpty() || email.getText().isEmpty() || userID.getText().isEmpty()){
                message = "Veuillez remplir chacune des cases du formulaire d'inscription.";
                throw new Exception();
            }
            String regex = "[0-9]+";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(userID.getText());
            boolean matches = m.matches();

            if (userID.getText().length() != 6 || matches == false) {
                message ="Le matricule doit contenir 6 chiffres.";
                throw new Exception();
            }
            String rightEmail = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
            Pattern pattern = Pattern.compile(rightEmail);
            Matcher matcher = pattern.matcher(email.getText());
            boolean gotMatched = matcher.matches();
            if (gotMatched == false) {
                message = "Veuillez saisir un mail valide.";
                throw new Exception();
            }


        } catch (Exception e) {
            System.out.println(message);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(message);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get().equals(ButtonType.OK)){
                alert.close();
            }
        }
        finally {
            if(message.equals(""))
                return true;

            else
                return false;
        }

    }

}