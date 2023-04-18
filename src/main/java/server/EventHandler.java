package server;

/**
 * L'interface fonctionnelle "EventHandler"
 * permet de gérer convenablement les évènements
 * que la méthode "handle" reçoit en paramètres.
 */
@FunctionalInterface
public interface EventHandler {
    void handle(String cmd, String arg);
}
