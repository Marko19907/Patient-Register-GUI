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
    private Patient currentlySelectedPatient;

    /**
     * Instantiates the controller.
     */
    public Controller()
    {
        this.patientRegister = new PatientRegister();
        this.patientObservableList = FXCollections.observableArrayList(this.patientRegister.getPatientList());
        this.currentlySelectedPatient = null;

        this.fillWithDemoPatients();
    }

    /**
     * Fills the register with demo data
     */
    private void fillWithDemoPatients()
    {
        this.patientRegister.addPatient(new Patient("Name1", "LastName1", "123"));
        Patient patient2 = new Patient("Name2", "LastName2", "321");
        patient2.setGeneralPractitioner("SpongeBob");
        this.patientRegister.addPatient(patient2);

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

    /**
     * Deletes the currentlySelectedPatient Patient from the register if one is selected,
     * shows a showPleaseSelectItemDialog() dialog otherwise
     */
    public void doDeletePatient()
    {
        if (this.currentlySelectedPatient == null) {
            this.showPleaseSelectItemDialog();
        }
        else {
            boolean deleteConfirmed = this.showDeleteConfirmationDialog();
            if (deleteConfirmed) {
                this.patientRegister.removePatient(this.currentlySelectedPatient);
                this.currentlySelectedPatient = null;

                this.updateObservableList();
            }
        }
    }

    /**
     * Sets the currently selected patient in the table
     * @param patient The patient to set as the currently selected patient, can not be null
     */
    public void setCurrentlySelectedPatient(Patient patient)
    {
        if (patient != null) {
            this.currentlySelectedPatient = patient;
        }
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

    /**
     * Displays a delete confirmation dialog
     * @return True if the user confirms the delete, false otherwise
     */
    private boolean showDeleteConfirmationDialog()
    {
        boolean deleteConfirmed = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete confirmation");
        alert.setContentText("Are you sure you want to delete this patient?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            deleteConfirmed = (result.get() == ButtonType.OK);
        }
        return deleteConfirmed;
    }

    /**
     * Displays a warning dialog to warn the user to select an element from the list first
     */
    private void showPleaseSelectItemDialog()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information");
        alert.setHeaderText("No items selected");
        alert.setContentText("No item is selected in the table. " + "\n"
                + "Please select an item from the table first.");
        alert.showAndWait();
    }
}
