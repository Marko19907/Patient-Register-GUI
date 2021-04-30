package no.ntnu.mappe2.marko19907.patientregister;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PatientTest
{
    @Test
    @DisplayName("Testing the constructor")
    public void testConstructor()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");


        assertEquals("Name1", patient.getFirstName());
        assertEquals("LastName1", patient.getLastName());
        assertEquals("123", patient.getSocialSecurityNumber());

        assertTrue(patient.getGeneralPractitioner().isBlank());
        assertTrue(patient.getDiagnosis().isBlank());
    }

    @Test
    @DisplayName("Testing the constructor with null values")
    public void testConstructorWithNull()
    {
        Patient patient = null;
        boolean exceptionThrown = false;

        try {
            patient = new Patient(null, null, null);
            fail("An IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    @Test
    @DisplayName("Testing the constructor with blank Strings")
    public void testConstructorWithBlankStrings()
    {
        Patient patient = null;
        boolean exceptionThrown = false;

        try {
            patient = new Patient("", "", "");
            fail("An IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    @Test
    @DisplayName("Test setting a new social security number")
    public void testSettingSocialSecurityNumber()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");
        String newSocialSecurityNumber = "321";

        patient.setSocialSecurityNumber(newSocialSecurityNumber);

        assertEquals(newSocialSecurityNumber, patient.getSocialSecurityNumber());
    }

    @Test
    @DisplayName("Test setting the social security number to null")
    public void testSettingSocialSecurityNumberToNull()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setSocialSecurityNumber(null);

        assertNotNull(patient.getSocialSecurityNumber());
        assertEquals("123", patient.getSocialSecurityNumber());
    }

    @Test
    @DisplayName("Test setting the social security number to a blank String")
    public void testSettingSocialSecurityNumberToBlankString()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setSocialSecurityNumber("");

        assertEquals("123", patient.getSocialSecurityNumber());
    }

    @Test
    @DisplayName("Test setting the first name")
    public void testSettingTheFirstName()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setFirstName("NewName");

        assertEquals("NewName", patient.getFirstName());
    }

    @Test
    @DisplayName("Test setting the first name to null")
    public void testSettingTheFirstNameToNull()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setFirstName(null);

        assertEquals("Name1", patient.getFirstName());
    }

    @Test
    @DisplayName("Test setting the first name to a blank String")
    public void testSettingTheFirstNameToBlankString()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setFirstName("");

        assertEquals("Name1", patient.getFirstName());
    }

    @Test
    @DisplayName("Test setting the last name")
    public void testSettingLastName()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setLastName("NewLastName");

        assertEquals("NewLastName", patient.getLastName());
    }

    @Test
    @DisplayName("Test setting the last name to null")
    public void testSettingLastNameToNull()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setLastName(null);

        assertEquals("LastName1", patient.getLastName());
    }

    @Test
    @DisplayName("Test setting the last name to null")
    public void testSettingLastNameToBlankString()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setLastName("");

        assertEquals("LastName1", patient.getLastName());
    }

    @Test
    @DisplayName("Test setting the diagnosis")
    public void testSettingDiagnosis()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setDiagnosis("Diag1");

        assertEquals("Diag1", patient.getDiagnosis());
    }

    @Test
    @DisplayName("Test setting the diagnosis to null")
    public void testSettingDiagnosisToNull()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setDiagnosis(null);

        assertTrue(patient.getDiagnosis().isBlank());
    }

    @Test
    @DisplayName("Test resetting the diagnosis to a blank String")
    public void testResettingTheDiagnosis()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");
        patient.setDiagnosis("Diag1");

        patient.setDiagnosis("");

        assertTrue(patient.getDiagnosis().isBlank());
    }

    @Test
    @DisplayName("Test setting the general practitioner")
    public void testSettingTheGeneralPractitioner()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setGeneralPractitioner("Doc1");

        assertEquals("Doc1", patient.getGeneralPractitioner());
    }

    @Test
    @DisplayName("Test setting the general practitioner to null")
    public void testSettingTheGeneralPractitionerToNull()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");

        patient.setGeneralPractitioner(null);

        assertTrue(patient.getGeneralPractitioner().isBlank());
    }

    @Test
    @DisplayName("Test setting the general practitioner to null")
    public void testResettingTheGeneralPractitioner()
    {
        Patient patient = new Patient("Name1", "LastName1", "123");
        patient.setGeneralPractitioner("Doc1");

        patient.setGeneralPractitioner("");

        assertTrue(patient.getGeneralPractitioner().isBlank());
    }

    @Test
    @DisplayName("Test the PatientBuilder with no additional arguments")
    public void testPatientBuilder()
    {
        // Arrange
        Patient patient = new Patient
                .PatientBuilder("Name1", "LastName1", "123")

                // Act
                .build();

        // Assert
        assertEquals("Name1", patient.getFirstName());
        assertEquals("LastName1", patient.getLastName());
        assertEquals("123", patient.getSocialSecurityNumber());
        assertTrue(patient.getDiagnosis().isBlank());
        assertTrue(patient.getGeneralPractitioner().isBlank());
    }

    @Test
    @DisplayName("Test the PatientBuilder with null values")
    public void testPatientBuilderWithNull()
    {
        // Better way to test exceptions, not AAA tho?
        assertThrows(IllegalArgumentException.class, () ->  new Patient
                .PatientBuilder(null, null, null)
                .build());
    }

    @Test
    @DisplayName("Test the PatientBuilder with blank Strings")
    public void testPatientBuilderWithBlankStrings()
    {
        // Arrange
        Patient patient = null;
        boolean exceptionThrown = false;

        try {
            patient = new Patient
                    .PatientBuilder("", "LastName1", "")

                    // Act
                    .build();
            fail("An IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }

        // Assert
        assertTrue(exceptionThrown);
    }

    @Test
    @DisplayName("Test the PatientBuilder with a diagnosis argument")
    public void testPatientBuilderWithDiagnosis()
    {
        Patient patient = new Patient
                .PatientBuilder("Name1", "LastName1", "123")

                .withDiagnosis("Diag1")
                .build();

        assertEquals("Diag1", patient.getDiagnosis());
    }

    @Test
    @DisplayName("Test the PatientBuilder with a general practitioner argument")
    public void testPatientBuilderWithGeneralPractitioner()
    {
        Patient patient = new Patient
                .PatientBuilder("Name1", "LastName1", "123")

                .withGeneralPractitioner("Doc1")
                .build();

        assertEquals("Doc1", patient.getGeneralPractitioner());
    }

    @Test
    @DisplayName("Test the PatientBuilder with null arguments")
    public void testPatientBuilderWithNullArguments()
    {
        Patient patient = new Patient
                .PatientBuilder("Name1", "LastName1", "123")

                .withDiagnosis(null)
                .withGeneralPractitioner(null)
                .build();

        assertTrue(patient.getDiagnosis().isBlank());
        assertTrue(patient.getGeneralPractitioner().isBlank());
    }
}