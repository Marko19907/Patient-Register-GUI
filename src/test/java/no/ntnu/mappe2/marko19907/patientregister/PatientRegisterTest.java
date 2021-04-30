package no.ntnu.mappe2.marko19907.patientregister;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientRegisterTest
{
    @Test
    @DisplayName("Test adding a patient to the register")
    public void testAddingPatient()
    {
        PatientRegister register = new PatientRegister();
        Patient patient = new Patient("Name1", "LastName1", "123");

        register.addPatient(patient);

        assertEquals(1, register.getNumberOfPatients());
        assertTrue(register.getPatientList().stream().anyMatch(patient1 -> patient1.equals(patient)));
    }

    @Test
    @DisplayName("Test adding null to the register")
    public void testAddingNullPatient()
    {
        PatientRegister register = new PatientRegister();

        register.addPatient(null);

        assertEquals(0, register.getNumberOfPatients());
    }

    @Test
    @DisplayName("Test adding a List of patients to the register")
    public void testAddingPatientList()
    {
        // Arrange
        PatientRegister register = new PatientRegister();
        Patient patient1 = new Patient("Name1", "LastName1", "123");
        Patient patient2 = new Patient("Name2", "LastName2", "321");
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient1);
        patientList.add(patient2);

        // Act
        register.addPatients(patientList);

        // Assert
        assertEquals(2, register.getNumberOfPatients());
        assertTrue(register.getPatientList().stream().anyMatch(patient -> patient.equals(patient1)));
        assertTrue(register.getPatientList().stream().anyMatch(patient -> patient.equals(patient2)));
    }

    @Test
    @DisplayName("Test adding a null List of patients to the register")
    public void testAddingNullPatientList()
    {
        PatientRegister register = new PatientRegister();

        register.addPatients(null);

        assertEquals(0, register.getNumberOfPatients());
    }

    @Test
    @DisplayName("Test removing a patient from the register")
    public void testRemovingPatient()
    {
        PatientRegister register = new PatientRegister();
        Patient patient = new Patient("Name1", "LastName1", "123");
        register.addPatient(patient);

        boolean removed = register.removePatient(patient);

        assertEquals(0, register.getNumberOfPatients());
        assertTrue(removed);
    }

    @Test
    @DisplayName("Test removing null from the register")
    public void testRemovingNull()
    {
        PatientRegister register = new PatientRegister();
        Patient patient = new Patient("Name1", "LastName1", "123");
        register.addPatient(patient);

        boolean removed = register.removePatient(null);

        assertEquals(1, register.getNumberOfPatients());
        assertFalse(removed);
    }
}