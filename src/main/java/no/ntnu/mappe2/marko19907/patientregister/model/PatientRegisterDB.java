package no.ntnu.mappe2.marko19907.patientregister.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import no.ntnu.mappe2.marko19907.patientregister.exception.DuplicateKeyException;

import java.util.List;

/**
 * The class PatientRegisterDB is an implantation of a PatientRegister
 * that can hold Patients in persistent storage
 *
 * @author Marko
 * @version 01-05-2021
 */
public class PatientRegisterDB implements PatientRegister
{
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    /**
     * Constructor for PatientRegisterDB objects
     */
    public PatientRegisterDB()
    {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("st-olavs-register");
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @Override
    public void addPatient(Patient patient)
    {
        if (patient != null) {
            Patient foundPatient = this.entityManager.find(Patient.class, patient.getSocialSecurityNumber());

            if (foundPatient != null) {
                throw new DuplicateKeyException("A patient with that social security number already exists");
            }

            this.entityManager.getTransaction().begin();
            this.entityManager.persist(patient);
            this.entityManager.getTransaction().commit();
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
        String jpql = "SELECT c FROM Patient c";
        return this.entityManager.createQuery(jpql, Patient.class).getResultList();
    }

    @Override
    public boolean removePatient(Patient patient)
    {
        boolean success = false;
        if (patient != null) {
            this.entityManager.getTransaction().begin();
            this.entityManager.remove(patient);
            this.entityManager.getTransaction().commit();
            success = true;
        }
        return success;
    }

    @Override
    public void updatePatient(Patient newPatient, Patient oldPatient) throws DuplicateKeyException
    {
        if (newPatient != null && oldPatient != null) {
            if (this.entityManager.contains(oldPatient)) {
                Patient foundPatient = this.entityManager.find(Patient.class, newPatient.getSocialSecurityNumber());
                if (foundPatient != null && !foundPatient.equals(oldPatient)) {
                    throw new DuplicateKeyException("A patient with that social security number already exists");
                }

                if (!this.entityManager.contains(newPatient)) {
                    this.removePatient(oldPatient);
                    this.addPatient(newPatient);
                }
            }
        }
    }

    @Override
    public int getNumberOfPatients()
    {
        return this.entityManager.getProperties().size();
    }

    @Override
    public void close()
    {
        this.entityManager.close();
        this.entityManagerFactory.close();
    }
}
