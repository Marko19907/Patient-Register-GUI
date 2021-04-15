package no.ntnu.mappe2.marko19907.patientregister;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class Controller represents the main controller for the application.
 * It is responsible for handling the events from the GUI.
 *
 * @author Marko
 * @version 15-04-2021
 */
public class Controller
{
    private final PatientRegister patientRegister;

    /**
     * Instantiates the controller.
     */
    public Controller()
    {
        this.patientRegister = new PatientRegister();
    }

    /**
     * Application exit dialog. A confirmation dialog that is displayed before exiting.
     */
    public void doQuit(Event event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm close");
        alert.setHeaderText("Exit this application?");
        alert.setContentText("Are you sure you want to exit the application?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                //TODO: Save the app's state to disk before exiting?
                Platform.exit();
            }
            else {
                event.consume();
            }
        }
    }
}
