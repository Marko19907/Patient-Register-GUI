package no.ntnu.mappe2.marko19907.patientregister;

import java.util.ArrayList;
import java.util.List;

/**
 * The class PatientRegister represents a register that can hold Patients
 * It is responsible for adding, removing and returning a list of the Patients
 *
 * @author Marko
 * @version 22-04-2021
 */
public class PatientRegister
{
    private final ArrayList<Patient> patients;

    /**
     * Constructor for PatientRegister objects
     */
    public PatientRegister()
    {
        this.patients = new ArrayList<>();
    }

    /**
     * Adds a given patient to the register
     * @param patient The patient to add, can not be null
     */
    public void addPatient(Patient patient)
    {
        if (patient != null) {
            this.patients.add(patient);
        }
    }

    /**
     * Adds a given List of patients to the register
     * @param patients The patient List to add, can not be null or empty
     */
    public void addPatients(List<Patient> patients)
    {
        if (patients != null) {
            patients.forEach(this::addPatient);
        }
    }

    /**
     * Returns a List of patients in the register
     * @return A List of patients in the register
     */
    public List<Patient> getPatientList()
    {
        return this.patients;
    }

    /**
     * Removes a given patient from the register
     * @param patientToRemove The patient to remove, can not be null
     * @return True if the given patient was removed, false when given null and false otherwise
     */
    public boolean removePatient(Patient patientToRemove)
    {
        return this.patients.removeIf(patient -> patient.equals(patientToRemove));
    }

    /**
     * Returns the number of patients in the register
     * @return The number of patients in the register as an int
     */
    public int getNumberOfPatients()
    {
        return this.patients.size();
    }
}
