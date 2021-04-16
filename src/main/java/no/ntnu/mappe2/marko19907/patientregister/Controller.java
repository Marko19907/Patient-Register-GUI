package no.ntnu.mappe2.marko19907.patientregister;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class Controller represents the main controller for the application.
 * It is responsible for handling the events from the GUI.
 *
 * @author Marko
 * @version 16-04-2021
 */
public class Controller
{
    private final PatientRegister patientRegister;
    private final ObservableList<Patient> patientObservableList;

    /**
     * Instantiates the controller.
     */
    public Controller()
    {
        this.patientRegister = new PatientRegister();
        this.patientObservableList = FXCollections.observableArrayList(this.patientRegister.getPatientList());

        this.fillWithDemoPatients();
    }

    /**
     * Fills the register with demo data
     */
    private void fillWithDemoPatients()
    {
        this.patientRegister.addPatient(new Patient
                ("Name1", "LastName1", "123", ""));
        this.patientRegister.addPatient(new Patient
                ("Name2", "LastName2", "321", "SpongeBob"));

        this.updateObservableList();
    }

    /**
     * Updates the observable list of patients with fresh values from the register
     */
    private void updateObservableList()
    {
        this.patientObservableList.setAll(this.patientRegister.getPatientList());
    }

    /**
     * Returns an ObservableList of patients to show
     * @return An ObservableList of patients to show
     */
    public ObservableList<Patient> getPatientObservableList()
    {
        return this.patientObservableList;
    }

    // -----------------------------------------------------------
    //    DIALOGS
    // -----------------------------------------------------------

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

    /**
     * Shows the About Dialog
     */
    public void doShowAboutDialog()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Patient Register App");
        alert.setContentText("An application created by" + "\n"
                + "Marko19907" + "\n"
                + "2021" + "\n\n"
                + "Running on Java " + System.getProperty("java.version")
                + " with JavaFX version " + System.getProperty("javafx.version"));
        alert.showAndWait();
    }
}
