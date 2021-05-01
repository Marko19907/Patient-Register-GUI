module no.ntnu.mappe2.marko19907.patientregister {
    requires javafx.controls;
    requires commons.csv;
    requires java.sql;
    requires jakarta.persistence;

    // Open the package to "anyone" to enable access by reflection for the Jupiter test engine
    opens no.ntnu.mappe2.marko19907.patientregister;

    exports no.ntnu.mappe2.marko19907.patientregister;
}