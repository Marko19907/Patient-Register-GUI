package no.ntnu.mappe2.marko19907.patientregister;

/**
 * Class Patient represents a single patient in the hospital.
 * It is responsible for storing the details of a single patient, their name, last name,
 * the social security number and the name of the patient's general practitioner.
 *
 * @author Marko
 * @version 14-04-2021
 */
public class Patient
{
    private String firstName;
    private String lastName;
    private String socialSecurityNumber;
    private String generalPractitioner;

    /**
     * Constructor for Patient objects
     */
    public Patient(String firstName, String lastName, String socialSecurityNumber, String generalPractitioner)
    {
        if (firstName == null || lastName == null || socialSecurityNumber == null || generalPractitioner == null) {
            throw new IllegalArgumentException(
                    "firstName, lastName, socialSecurityNumber or generalPractitioner can not be null!");
        }
        if (firstName.isBlank() || lastName.isBlank() || socialSecurityNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "firstName, lastName or socialSecurityNumber can not be blank!");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.generalPractitioner = generalPractitioner;
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

    @Override
    public String toString()
    {
        return this.getFullName() + ", with ID: " + this.getSocialSecurityNumber()
                + " is a patient of " + this.getGeneralPractitioner();
    }
}
