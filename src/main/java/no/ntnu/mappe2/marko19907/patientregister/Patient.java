package no.ntnu.mappe2.marko19907.patientregister;

import java.util.Objects;

/**
 * Class Patient represents a single patient in the hospital.
 * It is responsible for storing the details of a single patient, their name, last name,
 * the social security number and the name of the patient's general practitioner.
 *
 * @author Marko
 * @version 27-04-2021
 */
public class Patient
{
    private String firstName;
    private String lastName;
    private String socialSecurityNumber;
    private String generalPractitioner;
    private String diagnosis;

    /**
     * Constructor for Patient objects
     */
    public Patient(String firstName, String lastName, String socialSecurityNumber)
    {
        if (firstName == null || lastName == null || socialSecurityNumber == null) {
            throw new IllegalArgumentException(
                    "firstName, lastName or socialSecurityNumber can not be null!");
        }
        if (firstName.isBlank() || lastName.isBlank() || socialSecurityNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "firstName, lastName or socialSecurityNumber can not be blank!");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;

        this.generalPractitioner = "";
        this.diagnosis = "";
    }

    /**
     * Returns the social security number of the patient
     * @return Returns the social security number of the patient as a String
     */
    public String getSocialSecurityNumber()
    {
        return this.socialSecurityNumber;
    }

    /**
     * Sets the patient's social security number
     * @param newSocialSecurityNumber The new social security number, can't be null or blank
     */
    public void setSocialSecurityNumber(String newSocialSecurityNumber)
    {
        if (newSocialSecurityNumber != null) {
            if (!newSocialSecurityNumber.isBlank()) {
                this.socialSecurityNumber = newSocialSecurityNumber;
            }
        }
    }

    /**
     * Sets the patient's first name
     * @param newFirstName The new first name, can't be null or blank
     */
    public void setFirstName(String newFirstName)
    {
        if (newFirstName != null) {
            if (!newFirstName.isBlank()) {
                this.firstName = newFirstName;
            }
        }
    }

    /**
     * Sets the patient's last name
     * @param newLastName The new last name, can't be null or blank
     */
    public void setLastName(String newLastName)
    {
        if (newLastName != null) {
            if (!newLastName.isBlank()) {
                this.lastName = newLastName;
            }
        }
    }

    /**
     * Returns the first name of the patient
     * @return The first name of the patient as a String
     */
    public String getFirstName()
    {
        return this.firstName;
    }

    /**
     * Returns the last name of the patient
     * @return The last name of the patient as a String
     */
    public String getLastName()
    {
        return this.lastName;
    }

    /**
     * Returns the full name of the patient
     * @return The full name of the patient as a single String
     */
    public String getFullName()
    {
        return this.getFirstName() + " " + this.getLastName();
    }

    /**
     * Returns the full name of the patient's general practitioner
     * @return The full name of the patient's general practitioner as a single String
     */
    public String getGeneralPractitioner()
    {
        return this.generalPractitioner;
    }

    /**
     * Sets the full name of the patient's general practitioner
     * @param generalPractitioner The full name of the patient's general practitioner as a single String,
     *                            can not be null
     */
    public void setGeneralPractitioner(String generalPractitioner)
    {
        if (generalPractitioner != null) {
            this.generalPractitioner = generalPractitioner;
        }
    }

    /**
     * Return the patient's diagnosis
     * @return The patient's diagnosis as a String
     */
    public String getDiagnosis()
    {
        return this.diagnosis;
    }

    /**
     * Sets the patient's diagnosis
     * @param diagnosis The patient's diagnosis to set, can not be null
     */
    public void setDiagnosis(String diagnosis)
    {
        if (diagnosis != null) {
            this.diagnosis = diagnosis;
        }
    }

    @Override
    public String toString()
    {
        return this.getFullName() + ", with ID: " + this.getSocialSecurityNumber()
                + " is a patient of " + this.getGeneralPractitioner();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        final Patient patient = (Patient) o;
        return this.getFirstName().equals(patient.getFirstName())
                && this.getLastName().equals(patient.getLastName())
                && this.getSocialSecurityNumber().equals(patient.getSocialSecurityNumber())
                && Objects.equals(this.getGeneralPractitioner(), patient.getGeneralPractitioner())
                && Objects.equals(this.getDiagnosis(), patient.getDiagnosis());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.getFirstName(), this.getLastName(),
                this.getSocialSecurityNumber(), this.getGeneralPractitioner(), this.getDiagnosis());
    }

    /**
     * The PatientBuilder class is responsible for building an instance
     * of a Patient class according to the Builder design pattern.
     */
    public static final class PatientBuilder
    {
        private final String firstName;
        private final String lastName;
        private final String socialSecurityNumber;
        private String generalPractitioner;
        private String diagnosis;

        /**
         * Instantiates the PatientBuilder with the required arguments.
         */
        public PatientBuilder(String firstName, String lastName, String socialSecurityNumber)
        {
            this.firstName = firstName;
            this.lastName = lastName;
            this.socialSecurityNumber = socialSecurityNumber;

            this.generalPractitioner = "";
            this.diagnosis = "";
        }

        /**
         * Sets the patient's general practitioner and returns the PatientBuilder, intermediate operation
         * @param generalPractitioner The patient's general practitioner name to set
         * @return The PatientBuilder with the given general practitioner
         */
        public PatientBuilder withGeneralPractitioner(String generalPractitioner)
        {
            if (generalPractitioner != null) {
                this.generalPractitioner = generalPractitioner;
            }
            return this;
        }

        /**
         * Sets the patient's diagnosis and returns the PatientBuilder, intermediate operation
         * @param diagnosis The patient's diagnosis to set
         * @return The PatientBuilder with the given diagnosis
         */
        public PatientBuilder withDiagnosis(String diagnosis)
        {
            if (diagnosis != null) {
                this.diagnosis = diagnosis;
            }
            return this;
        }

        /**
         * Returns an instance of a Patient according to the provided arguments, terminal operation
         * @return An instance of a Patient with the provided arguments
         */
        public Patient build()
        {
            Patient patient = new Patient(this.firstName, this.lastName, this.socialSecurityNumber);
            patient.generalPractitioner = this.generalPractitioner;
            patient.diagnosis = this.diagnosis;
            return patient;
        }
    }
}
