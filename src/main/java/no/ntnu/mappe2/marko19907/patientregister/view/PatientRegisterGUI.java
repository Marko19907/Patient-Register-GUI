package no.ntnu.mappe2.marko19907.patientregister.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import no.ntnu.mappe2.marko19907.patientregister.controller.Controller;
import no.ntnu.mappe2.marko19907.patientregister.exception.DuplicateKeyException;
import no.ntnu.mappe2.marko19907.patientregister.model.Patient;
import no.ntnu.mappe2.marko19907.patientregister.utility.ImageLoader;
import no.ntnu.mappe2.marko19907.patientregister.utility.NodeFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CancellationException;

/**
 * Class PatientRegisterGUI represents the main window in the application.
 *
 * @author Marko
 * @version 03-05-2021
 */
public class PatientRegisterGUI extends Application
{
    private final Controller controller;
    private final NodeFactory nodeFactory;

    private final Label statusLabel;

    /**
     * PatientRegisterGUI constructor
     */
    public PatientRegisterGUI()
    {
        this.controller = new Controller();
        this.nodeFactory = new NodeFactory();

        this.statusLabel = (Label) this.nodeFactory.createLabel();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        BorderPane root = (BorderPane) this.nodeFactory.createBorderPane();
        root.setTop(this.setupTopMenu());
        root.setCenter(this.setupCenter());
        root.setBottom(this.setupBottomLabel());

        primaryStage.setTitle("Patient Register");
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(200);

        primaryStage.setOnCloseRequest(this.controller::doQuit);

        Scene scene = new Scene(root, 600, 400, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();

        this.setStatusMessage("OK");
        root.requestFocus();
    }

    /**
     * Returns up the table that contains the patients
     * @return An already set-up table that contains the patients
     */
    private TableView<Patient> setupCenterTable()
    {
        TableView<Patient> patientTableView = (TableView<Patient>) this.nodeFactory.createTableView();
        patientTableView.setPlaceholder(new Label("No patients to display"));
        patientTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        // Create the columns
        TableColumn<Patient, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Patient, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Patient, String> socialSecurityNumberColumn = new TableColumn<>("Social Security Number");
        socialSecurityNumberColumn.setCellValueFactory(new PropertyValueFactory<>("socialSecurityNumber"));

        TableColumn<Patient, String> generalPractitionerColumn = new TableColumn<>("General Practitioner");
        generalPractitionerColumn.setCellValueFactory(new PropertyValueFactory<>("generalPractitioner"));

        TableColumn<Patient, String> diagnosisColumn = new TableColumn<>("Diagnosis");
        diagnosisColumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));


        // set on left click action
        patientTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                this.controller.setCurrentlySelectedPatient(
                        patientTableView.getSelectionModel().getSelectedItem());
            }
        });

        patientTableView.getColumns().addAll(Arrays.asList(nameColumn, lastNameColumn, socialSecurityNumberColumn,
                generalPractitionerColumn, diagnosisColumn));
        patientTableView.setItems(this.controller.getPatientObservableList());
        //Set a default sort column
        patientTableView.getSortOrder().add(nameColumn);

        return patientTableView;
    }

    /**
     * Returns an already top MenuBar
     * @return An already top MenuBar
     */
    private MenuBar setupTopMenu()
    {
        MenuBar menuBar = (MenuBar) this.nodeFactory.createMenuBar();

        menuBar.getMenus().addAll(this.setupFileMenu(), this.setupEditMenu(), this.setupHelpMenu());

        return menuBar;
    }

    /**
     * Sets up the VBox that contains the center tool box and the table
     * @return An already set-up VBox that contains the center tool box and the table
     */
    private VBox setupCenter()
    {
        VBox centerVBox = (VBox) this.nodeFactory.createVBox();
        centerVBox.getChildren().addAll(this.setupTopToolBox(), this.setupCenterTable());
        VBox.setVgrow(centerVBox.getChildren().get(1), Priority.ALWAYS);
        return centerVBox;
    }

    /**
     * Sets up the top ToolBar
     * @return An already set-up ToolBar
     */
    private ToolBar setupTopToolBox()
    {
        ToolBar toolBar = (ToolBar) this.nodeFactory.createToolBar();
        toolBar.setPadding(new Insets(5, 5, 5, 5));
        toolBar.setStyle("-fx-background-color: #e7e7e7");

        Button addPatientButton = (Button) this.nodeFactory.createButton();
        this.setupTopToolBarButton(addPatientButton);
        addPatientButton.setTooltip(new Tooltip("Add Patient"));
        addPatientButton.setOnAction(event -> this.controller.doAddNewPatientDialog());
        ImageView addPersonIcon = ImageLoader.getInstance().getImage("add-person");
        if (addPersonIcon != null) {
            addPersonIcon.setFitHeight(33.5);
            addPatientButton.setGraphic(addPersonIcon);
        }

        Button editPatientButton = (Button) this.nodeFactory.createButton();
        this.setupTopToolBarButton(editPatientButton);
        editPatientButton.setTooltip(new Tooltip("Edit Patient"));
        editPatientButton.setOnAction(event -> this.controller.doEditPatientDialog());
        ImageView editPersonIcon = ImageLoader.getInstance().getImage("edit-person");
        if (editPersonIcon != null) {
            editPersonIcon.setFitHeight(34.95);
            editPatientButton.setGraphic(editPersonIcon);
        }

        Button deletePatientButton = (Button) this.nodeFactory.createButton();
        this.setupTopToolBarButton(deletePatientButton);
        deletePatientButton.setTooltip(new Tooltip("Delete Selected Patient"));
        deletePatientButton.setOnAction(event -> this.controller.doDeletePatient());
        ImageView deletePersonIcon = ImageLoader.getInstance().getImage("delete-person");
        if (deletePersonIcon != null) {
            deletePersonIcon.setFitWidth(31.15);
            deletePatientButton.setGraphic(deletePersonIcon);
        }

        toolBar.getItems().addAll(addPatientButton, deletePatientButton, editPatientButton);
        return toolBar;
    }

    /**
     * Sets up the height, width and the alignment of a given button
     * @param button The button to set up the size and the alignment of, not null
     */
    private void setupTopToolBarButton(Button button)
    {
        if (button != null) {
            int height = 40;
            int width = 40;

            button.setMinHeight(height);
            button.setMinWidth(width);
            button.setMaxHeight(height);
            button.setMaxWidth(width);
            button.setPrefHeight(height);
            button.setPrefWidth(width);
            button.setAlignment(Pos.CENTER);
        }
    }

    /**
     * Sets up the HBox that contains the bottom status Label
     * @return An already set-up HBox that contains the bottom status Label
     */
    private HBox setupBottomLabel()
    {
        HBox statusBox = (HBox) this.nodeFactory.createHBox();
        statusBox.setStyle("-fx-background-color: #b6b6b6");
        statusBox.getChildren().add(this.statusLabel);
        return statusBox;
    }

    /**
     * Sets the given status message to the bottom status label
     * @param message The message to set, can not be null
     */
    private void setStatusMessage(String message)
    {
        if (message != null) {
            this.statusLabel.setText("Status: " + message);
        }
    }

    /**
     * Returns an already set-up file Menu
     * @return An already set-up file Menu
     */
    private Menu setupFileMenu()
    {
        Menu fileMenu = new Menu("File");

        MenuItem importFromCVS = new MenuItem("Import from .CVS");
        importFromCVS.setOnAction(event -> {
            this.setStatusMessage("Import started");

            try {
                boolean success = this.controller.doImportCSVFile();
                if (success) {
                    this.setStatusMessage("Import successful");
                }
            }
            catch (CancellationException e) {
                this.setStatusMessage("Import cancelled");
            }
            catch (IllegalArgumentException e) {
                this.setStatusMessage("The CSV file header is invalid, import failed . . .");
            }
            catch (DuplicateKeyException e) {
                this.setStatusMessage("Duplicate entries detected, import cancelled . . .");
            }
            catch (IOException e) {
                this.setStatusMessage("Import failed");
            }
        });

        MenuItem exportToCVS = new MenuItem("Export to .CVS");
        exportToCVS.setOnAction(event -> {
            this.setStatusMessage("Export started");

            try {
                this.controller.doExportToCSV();
                this.setStatusMessage("Export successful");
            }
            catch (CancellationException e) {
                this.setStatusMessage("Export cancelled");
            }
            catch (IOException e) {
                this.setStatusMessage("Export failed");
            }
        });

        SeparatorMenuItem separator = new SeparatorMenuItem();

        MenuItem exitButton = new MenuItem("Exit");
        exitButton.setOnAction(this.controller::doQuit);

        fileMenu.getItems().addAll(importFromCVS, exportToCVS, separator, exitButton);
        return fileMenu;
    }

    /**
     * Returns an already set-up edit Menu
     * @return An already set-up edit Menu
     */
    private Menu setupEditMenu()
    {
        Menu editMenu = new Menu("Edit");

        MenuItem addNewPatient = new MenuItem("Add new Patient");
        addNewPatient.setAccelerator(new KeyCodeCombination(KeyCode.A));
        addNewPatient.setOnAction(event -> this.controller.doAddNewPatientDialog());

        MenuItem editSelectedPatient = new MenuItem("Edit Selected Patient");
        editSelectedPatient.setAccelerator(new KeyCodeCombination(KeyCode.E));
        editSelectedPatient.setOnAction(event -> this.controller.doEditPatientDialog());

        MenuItem removeSelectedPatient = new MenuItem("Remove Selected Patient");
        removeSelectedPatient.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        removeSelectedPatient.setOnAction(event -> this.controller.doDeletePatient());

        editMenu.getItems().addAll(addNewPatient, editSelectedPatient, removeSelectedPatient);
        return editMenu;
    }

    /**
     * Returns an already set-up help Menu
     * @return An already set-up help Menu
     */
    private Menu setupHelpMenu()
    {
        Menu helpMenu = new Menu("Help");

        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(event -> this.controller.doShowAboutDialog());

        helpMenu.getItems().add(aboutMenuItem);
        return helpMenu;
    }
}
