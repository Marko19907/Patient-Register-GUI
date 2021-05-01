package no.ntnu.mappe2.marko19907.patientregister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The class PatientRegisterPlain is an implantation of a PatientRegister and
 * represents a register that can hold Patients in memory.
 * It is responsible for adding, removing and returning a list of the Patients
 *
 * @author Marko
 * @version 01-05-2021
 */
public class PatientRegisterPlain implements PatientRegister
{
    private final HashMap<String, Patient> patients;

    /**
     * Constructor for PatientRegisterPlain objects
     */
    public PatientRegisterPlain()
    {
        this.patients = new HashMap<>();
    }

    @Override
    public void addPatient(Patient patient)
    {
        if (patient != null) {
            if (this.patients.containsKey(patient.getSocialSecurityNumber())) {
                throw new DuplicateKeyException("A patient with that social security number already exists");
            }
            this.patients.put(patient.getSocialSecurityNumber(), patient);
        }
    }

    @Override
    public void addPatients(List<Patient> patients)
    {
        if (patients != null) {
            patients.forEach(this::addPatient);
        }
    }

    @Override
    public List<Patient> getPatientList()
    {
        return new ArrayList<>(this.patients.values());
    }

    @Override
    public boolean removePatient(Patient patient)
    {
        // Guard condition
        if (patient == null) {
            return false;
        }

        return this.patients.remove(patient.getSocialSecurityNumber(), patient);
    }

    @Override
    public void updatePatient(Patient newPatient, Patient oldPatient) throws DuplicateKeyException
    {
        if (newPatient != null && oldPatient != null) {
            if (this.patients.containsKey(oldPatient.getSocialSecurityNumber())) {
                Patient foundPatient = this.patients.get(newPatient.getSocialSecurityNumber());
                if (foundPatient != null
                        && !foundPatient.equals(oldPatient)
                        && foundPatient.getSocialSecurityNumber().equals(newPatient.getSocialSecurityNumber())) {
                    throw new DuplicateKeyException("A patient with that social security number already exists");
                }

                this.removePatient(oldPatient);
                this.addPatient(newPatient);
            }
        }
    }

    @Override
    public int getNumberOfPatients()
    {
        return this.patients.size();
    }

    @Override
    public void close()
    {
        // Nothing to do here
    }
}
