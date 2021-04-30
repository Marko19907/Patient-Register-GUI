package no.ntnu.mappe2.marko19907.patientregister;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CSVHandlerTest
{
    private static final File testFileToWrite1 = new File("target/testFile.csv");
    private static final File testFileToWrite2 = new File("target/testFile2.csv");

    private static final Patient patient1 = new Patient.
            PatientBuilder("TestName", "TestLastName", "123")
            .withGeneralPractitioner("Doc1")
            .withDiagnosis("Diag1")
            .build();

    private static final Patient patient2 = new Patient.
            PatientBuilder("TestName2", "TestLastName2", "321")
            .withGeneralPractitioner("Doc2")
            .withDiagnosis("Diag2")
            .build();


    @BeforeEach
    @DisplayName("Writing the files to perform the testing on")
    public void setup()
    {
        CSVHandler csvHandler = new CSVHandler();
        ArrayList<Patient> patientList = new ArrayList<>();
        patientList.add(patient1);
        patientList.add(patient2);

        try {
            csvHandler.writePatientList(testFileToWrite1, patientList);
        }
        catch (IOException e) {
            fail();
        }
    }

    @AfterEach
    @DisplayName("Cleaning up the files that were written")
    public void cleanup()
    {
        assertTrue(CSVHandlerTest.testFileToWrite1.delete());
        if (testFileToWrite2.exists()) {
            assertTrue(CSVHandlerTest.testFileToWrite2.delete());
        }
    }

    @Test
    @DisplayName("Test writing a new file and then verifying the data from it")
    public void testWrite()
    {
        // Arrange
        CSVHandler csvHandler = new CSVHandler();
        List<Patient> patientListToWrite = new ArrayList<>();
        Patient patientToWrite = new Patient
                .PatientBuilder("Name3", "LastName3", "789")
                .withDiagnosis("None")
                .build();
        patientListToWrite.add(patientToWrite);
        try {
            csvHandler.writePatientList(testFileToWrite2, patientListToWrite);
        }
        catch (IOException e) {
            fail();
        }
        List<Patient> patientListRead = null;


        // Act
        try {
            patientListRead = csvHandler.readPatientList(testFileToWrite2);
        }
        catch (IOException e) {
            fail();
        }


        // Assert
        assertNotNull(patientListRead);
        assertEquals(1, patientListRead.size());
        final Patient patientFromDisk = patientListRead.get(0);
        assertEquals(patientFromDisk, patientToWrite);
    }

    @Test
    @DisplayName("Testing reading an already written file")
    public void testRead()
    {
        CSVHandler csvHandler = new CSVHandler();
        List<Patient> patientList = null;

        try {
            patientList = csvHandler.readPatientList(testFileToWrite1);
        }
        catch (IOException e) {
            fail();
        }

        assertNotNull(patientList);
        assertEquals(2, patientList.size());
        assertTrue(patientList.stream().anyMatch(patient -> patient.equals(patient1)));
        assertTrue(patientList.stream().anyMatch(patient -> patient.equals(patient2)));
    }

    @Test
    @DisplayName("Test overwriting an already existing file")
    public void testWriteToExistingFile()
    {
        // Arrange
        CSVHandler csvHandler = new CSVHandler();
        List<Patient> patientListToWrite = new ArrayList<>();
        List<Patient> patientListToRead = null;
        Patient patientToWrite = new Patient
                .PatientBuilder("Name3", "LastName3", "789")
                .withDiagnosis("None")
                .build();
        patientListToWrite.add(patientToWrite);
        try {
            csvHandler.writePatientList(testFileToWrite1, patientListToWrite);
        }
        catch (IOException e) {
            fail();
        }


        // Act
        try {
            patientListToRead = csvHandler.readPatientList(testFileToWrite1);
        }
        catch (IOException e) {
            fail();
        }


        // Assert
        assertNotNull(patientListToRead);
        assertEquals(1, patientListToRead.size());
    }

    @Test
    @DisplayName("Test reading an invalid file")
    public void testReadingNullFile()
    {
        CSVHandler csvHandler = new CSVHandler();
        List<Patient> patientList = null;

        try {
            patientList = csvHandler.readPatientList(null);
        }
        catch (IOException e) {
            fail();
        }

        assertNotNull(patientList);
        assertTrue(patientList.isEmpty());
    }

    @Test
    @DisplayName("Test reading a non-existent file")
    public void testReadingNonExistentFile()
    {
        CSVHandler csvHandler = new CSVHandler();
        boolean exceptionThrown = false;

        try {
            csvHandler.readPatientList(new File("target/fileThatDoesNotExist.csv"));
        }
        catch (IOException e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }
}
