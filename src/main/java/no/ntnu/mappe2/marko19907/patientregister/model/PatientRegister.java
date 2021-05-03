package no.ntnu.mappe2.marko19907.patientregister.model;

import no.ntnu.mappe2.marko19907.patientregister.exception.DuplicateKeyException;

import java.util.List;

/**
 * PatientRegister represents a DAO interface for create, read, update,
 * and delete (CRUD) operations of persistent storage
 *
 * @author Marko
 * @version 01-05-2021
 */
public interface PatientRegister
{
    /**
     * Adds a given patient to the register
     * @param patient The patient to add, can not be null
     * @throws DuplicateKeyException If a patient with the same social security number exists
     */
    void addPatient(Patient patient) throws DuplicateKeyException;

    /**
     * Adds a given List of patients to the register
     * @param patients The patient List to add, can not be null or empty
     * @throws DuplicateKeyException If a patient with the same social security number exists
     */
    void addPatients(List<Patient> patients);

    /**
     * Returns a List of patients in the register
     * @return A List of patients in the register
     */
    List<Patient> getPatientList();

    /**
     * Removes a given patient from the register
     * @param patientToRemove The patient to remove, can not be null
     * @return True if the given patient was removed, false when given null and false otherwise
     */
    boolean removePatient(Patient patientToRemove);

    /**
     * Updates the given patient in the register
     * @param newPatient The patient that replaces the oldPatient
     * @param oldPatient The patient that is to be replaced with the newPatient
     * @throws DuplicateKeyException If a patient with the same social security number exists
     */
    void updatePatient(Patient newPatient, Patient oldPatient) throws DuplicateKeyException;

    /**
     * Returns the number of patients in the register
     * @return The number of patients in the register as an int
     */
    int getNumberOfPatients();

    /**
     * Closes the connection to the database
     */
    void close();
}
