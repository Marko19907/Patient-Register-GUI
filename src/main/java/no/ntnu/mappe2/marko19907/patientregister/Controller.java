package no.ntnu.mappe2.marko19907.patientregister;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CancellationException;

/**
 * Class Controller represents the main controller for the application.
 * It is responsible for handling the events from the GUI.
 *
 * @author Marko
 * @version 01-05-2021
 */
public class Controller
{
    private final PatientRegister patientRegister;
    private final CSVHandler csvHandler;
    private final NodeFactory nodeFactory;
    private final ObservableList<Patient> patientObservableList;
    private Patient currentlySelectedPatient;

    /**
     * Instantiates the controller.
     */
    public Controller()
    {
        //this.patientRegister = new PatientRegisterPlain();
        this.patientRegister = new PatientRegisterDB();
        this.csvHandler = new CSVHandler();
        this.nodeFactory = new NodeFactory();

        this.patientObservableList = FXCollections.observableArrayList(this.patientRegister.getPatientList());
        this.currentlySelectedPatient = null;

        this.fillWithDemoPatients();
    }

    /**
     * Fills the register with demo data
     */
    private void fillWithDemoPatients()
    {
        try {
            this.patientRegister.addPatient(new Patient("Name1", "LastName1", "123"));

            this.patientRegister.addPatient(new Patient
                    .PatientBuilder("Name2", "LastName2", "321")
                    .withGeneralPractitioner("SpongeBob")
                    .build());

            this.patientRegister.addPatient(new Patient
                    .PatientBuilder("Name3", "LastName3", "987")
                    .withGeneralPractitioner("Doc3")
                    .withDiagnosis("Diag3")
                    .build());
        }
        catch (DuplicateKeyException ignored) {
        }

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

    /**
     * Shows the New Patient Dialog
     */
    public void doAddNewPatientDialog()
    {
        this.doShowPatientDetailsDialog(1);
    }

    /**
     * Shows the Edit Patient Dialog
     */
    public void doEditPatientDialog()
    {
        if (this.currentlySelectedPatient == null) {
            this.showPleaseSelectItemDialog();
        }
        else {
            this.doShowPatientDetailsDialog(2);
        }
    }

    /**
     * Adds the contents of the selected CSV file to the register
     * @return True if the import was successful, false otherwise
     * @throws IOException If an IO error is encountered
     * @throws CancellationException If the user cancels the open action
     * @throws IllegalArgumentException If the CSV header of the chosen file is invalid
     * @throws DuplicateKeyException If the CSV file contains a duplicate entry
     */
    public boolean doImportCSVFile() throws IOException, CancellationException,
            IllegalArgumentException, DuplicateKeyException
    {
        boolean success = false;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) {
            throw new CancellationException("The file chooser was dismissed");
        }

        List<Patient> importedPatients = this.csvHandler.readPatientList(selectedFile);
        if (importedPatients != null) {
            if (!importedPatients.isEmpty()) {
                try {
                    this.patientRegister.addPatients(importedPatients);
                }
                catch (DuplicateKeyException e) {
                    throw new DuplicateKeyException("A patient with that social security number already exists");
                }

                this.updateObservableList();
            }
            success = true;
        }
        return success;
    }

    /**
     * Writes the contents of the patient register to a CSV file
     * @throws IOException If an IO error is encountered
     * @throws CancellationException If the user cancels the export action
     */
    public void doExportToCSV() throws IOException, CancellationException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save to CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile == null) {
            throw new CancellationException("The file chooser was dismissed");
        }

        this.csvHandler.writePatientList(selectedFile, this.patientRegister.getPatientList());
    }

    /**
     * Removes the currently selected patient selection
     */
    private void clearSelection()
    {
        this.currentlySelectedPatient = null;
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

    /**
     * Displays a warning dialog to warn the user that the entered social security number is a duplicate
     */
    private void showDuplicateSocialSecurityNumberDialog()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information");
        alert.setHeaderText("Duplicate Social Security Number");
        alert.setContentText("The social security number entered is a duplicate" + "\n"
                + "The patient was not added or modified . . .");
        alert.showAndWait();
    }

    /**
     * Shows the Patient details dialog
     * @param mode If mode is 1, the method will create a new Patient and add it to the list
     *             If mode is 2, the method will edit the currently selected patient
     * @throws IllegalArgumentException if the given mode is invalid
     */
    private void doShowPatientDetailsDialog(int mode)
    {
        if ((mode != 1) && (mode != 2)) {
            throw new IllegalArgumentException("Unsupported PatientDetailsDialog mode! " +
                    "Valid modes are 1 and 2 . . .");
        }

        Dialog<Patient> patientDialog = new Dialog<>();
        patientDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = (GridPane) this.nodeFactory.createGridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 120, 10, 10));

        TextField patientName = (TextField) this.nodeFactory.createTextField();
        patientName.setPromptText("First name");

        TextField patientLastName = (TextField) this.nodeFactory.createTextField();
        patientLastName.setPromptText("Last name");

        TextField socialSecurityNumber = (TextField) this.nodeFactory.createTextField();
        socialSecurityNumber.setPromptText("Social security number");

        TextField generalPractitioner = (TextField) this.nodeFactory.createTextField();
        generalPractitioner.setPromptText("General Practitioner");

        TextField diagnosis = (TextField) this.nodeFactory.createTextField();
        diagnosis.setPromptText("Diagnosis");

        switch (mode) {
            case 1:
                patientDialog.setTitle("Add Patient");
                break;
            case 2:
                if (this.currentlySelectedPatient != null) {
                    patientDialog.setTitle("Edit Patient");

                    patientName.setText(this.currentlySelectedPatient.getFirstName());
                    patientLastName.setText(this.currentlySelectedPatient.getLastName());
                    socialSecurityNumber.setText(this.currentlySelectedPatient.getSocialSecurityNumber());

                    generalPractitioner.setText(this.currentlySelectedPatient.getGeneralPractitioner());
                    diagnosis.setText(this.currentlySelectedPatient.getDiagnosis());
                }
                break;
            default:
                // This should never occur due to the guard condition further up,
                // adding anyway; defensive programming
                throw new IllegalArgumentException("Unexpected mode: " + mode);
        }

        // Disable the OK Button if one of the TextFields is empty
        BooleanBinding blankTextField = patientName.textProperty().isEmpty()
                .or(patientLastName.textProperty().isEmpty())
                .or(socialSecurityNumber.textProperty().isEmpty());
        patientDialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(blankTextField);

        grid.add(new Label("First name*:"), 0, 0);
        grid.add(patientName, 1, 0);
        grid.add(new Label("Last name*:"), 0, 1);
        grid.add(patientLastName, 1, 1);
        grid.add(new Label("Social security number*:"), 0, 2);
        grid.add(socialSecurityNumber, 1, 2);
        grid.add(new Label("General Practitioner:"), 0, 3);
        grid.add(generalPractitioner, 1, 3);
        grid.add(new Label("Diagnosis:"), 0, 4);
        grid.add(diagnosis, 1, 4);

        patientDialog.getDialogPane().setContent(grid);
        patientDialog.setResultConverter(button -> {
            Patient result = null;
            if (button == ButtonType.OK) {
                result = new Patient.PatientBuilder(
                        patientName.getText(), patientLastName.getText(), socialSecurityNumber.getText())
                        .withGeneralPractitioner(generalPractitioner.getText())
                        .withDiagnosis(diagnosis.getText())
                        .build();
            }
            return result;
        });

        Optional<Patient> result = patientDialog.showAndWait();
        if (result.isPresent()) {
            switch (mode) {
                case 1:
                    Patient newPatient = result.get();
                    try {
                        this.patientRegister.addPatient(newPatient);
                    }
                    catch (DuplicateKeyException e) {
                        this.showDuplicateSocialSecurityNumberDialog();
                    }
                    break;
                case 2:
                    if (this.currentlySelectedPatient != null) {
                        Patient modifiedPatient = new Patient.PatientBuilder(
                                patientName.getText(),
                                patientLastName.getText(),
                                socialSecurityNumber.getText())
                                .withGeneralPractitioner(generalPractitioner.getText())
                                .withDiagnosis(diagnosis.getText())
                                .build();
                        try {
                            this.patientRegister.updatePatient(modifiedPatient, this.currentlySelectedPatient);
                        }
                        catch (DuplicateKeyException e) {
                            this.showDuplicateSocialSecurityNumberDialog();
                        }

                        this.clearSelection();
                    }
                    break;
                default:
                    // This should never occur due to the guard condition further up,
                    // adding anyway; defensive programming
                    throw new IllegalArgumentException("Unexpected mode: " + mode);
            }

            this.updateObservableList();
        }
    }
}
