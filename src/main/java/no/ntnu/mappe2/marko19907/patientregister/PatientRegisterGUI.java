package no.ntnu.mappe2.marko19907.patientregister;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * Class PatientRegisterGUI represents the main window in the application.
 *
 * @author Marko
 * @version 14-04-2021
 */
public class PatientRegisterGUI extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        BorderPane root = new BorderPane();
        root.setTop(this.setupTopMenu());
        root.setCenter(this.setupCenterTable());

        primaryStage.setTitle("Patient Register");
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(200);

        Scene scene = new Scene(root, 600, 400, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();

        root.requestFocus();
    }

    /**
     * Returns up the table that contains the patients
     * @return An already set-up table that contains the patients
     */
    private TableView<Patient> setupCenterTable() {
        TableView<Patient> patientTableView = new TableView<>();
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
                System.out.println("Table clicked!");
            }
        });

        patientTableView.getColumns().addAll(Arrays.asList(nameColumn, lastNameColumn, socialSecurityNumberColumn,
                generalPractitionerColumn, diagnosisColumn));
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
        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(this.setupFileMenu(), this.setupEditMenu());

        return menuBar;
    }

    /**
     * Returns an already set-up file Menu
     * @return An already set-up file Menu
     */
    private Menu setupFileMenu()
    {
        Menu fileMenu = new Menu("File");

        MenuItem importFromCVS = new MenuItem("Import from .CVS");
        importFromCVS.setOnAction(event -> System.out.println("Import from .CVS menu item clicked!"));

        MenuItem exportToCVS = new MenuItem("Export to .CVS");
        exportToCVS.setOnAction(event -> System.out.println("Export to .CVS menu item clicked!"));

        SeparatorMenuItem separator = new SeparatorMenuItem();

        MenuItem exitButton = new MenuItem("Exit");
        exitButton.setOnAction(event -> System.out.println("Exit menu item clicked!"));

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
        addNewPatient.setOnAction(event -> System.out.println("Add new Patient clicked!"));

        MenuItem editSelectedPatient = new MenuItem("Edit Selected Patient");
        editSelectedPatient.setAccelerator(new KeyCodeCombination(KeyCode.E));
        editSelectedPatient.setOnAction(event -> System.out.println("Edit Selected Patient clicked!"));

        MenuItem removeSelectedPatient = new MenuItem("Remove Selected Patient");
        removeSelectedPatient.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        removeSelectedPatient.setOnAction(event -> System.out.println("Remove Selected Patient clicked!"));

        editMenu.getItems().addAll(addNewPatient, editSelectedPatient, removeSelectedPatient);
        return editMenu;
    }
}
