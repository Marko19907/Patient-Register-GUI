package no.ntnu.mappe2.marko19907.patientregister;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

        patientTableView.getColumns().addAll(nameColumn, lastNameColumn, socialSecurityNumberColumn,
                generalPractitionerColumn, diagnosisColumn);
        //Set a default sort column
        patientTableView.getSortOrder().add(nameColumn);

        return patientTableView;
    }
}
