package no.ntnu.mappe2.marko19907.patientregister;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class CSVHandler is responsible for reading and writing, to and from CSV files
 *
 * @author Marko
 * @version 26-04-2021
 */
public class CSVHandler
{
    private static final char DELIMITER = ';';
    private static final String[] HEADERS = {
            "firstName",
            "lastName",
            "generalPractitioner",
            "socialSecurityNumber",
            "diagnosis"
    };

    /**
     * Constructor for CSVHandler objects
     */
    public CSVHandler()
    {
    }

    /**
     * Returns the patient List from a given CSV file
     * @param fileToRead The file to read from, not null
     * @throws IllegalArgumentException If the CSV header of the given file is invalid
     * @throws IOException If an IO error is encountered
     */
    public List<Patient> readPatientList(File fileToRead) throws IOException, IllegalArgumentException
    {
        List<Patient> patientList = new ArrayList<>();

        if (fileToRead != null) {
            try {
                Reader reader = Files.newBufferedReader(Paths.get(fileToRead.getAbsolutePath()));
                Iterable<CSVRecord> records = this.getCSVFormat().withFirstRecordAsHeader().parse(reader);

                for (CSVRecord csvRecord : records) {
                    String firstName = csvRecord.get(HEADERS[0]);
                    String lastName = csvRecord.get(HEADERS[1]);
                    String generalPractitioner = csvRecord.get(HEADERS[2]);
                    String socialSecurityNumber = csvRecord.get(HEADERS[3]);
                    String diagnosis = "";
                    if (csvRecord.isMapped(HEADERS[4])) {
                        diagnosis = csvRecord.get(HEADERS[4]);
                    }

                    patientList.add(new Patient.PatientBuilder(firstName, lastName, socialSecurityNumber)
                            .withGeneralPractitioner(generalPractitioner)
                            .withDiagnosis(diagnosis)
                            .build());
                }
            }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("The CSV header is in an unknown format, "
                        + "expected [" + HEADERS[0] + "; " + HEADERS[1] + "; "
                        + HEADERS[2] + "; " + HEADERS[3] + "; " + HEADERS[4]+ "]");
            }
        }

        return patientList;
    }

    /**
     * Writes the given List to the given CSV file
     * @param fileToWrite The file to write to, not null
     * @param patientList The List to write to the file, can not be null or empty
     * @throws IOException If an IO error is encountered
     */
    public void writePatientList(File fileToWrite, List<Patient> patientList) throws IOException
    {
        if (fileToWrite != null && patientList != null && !patientList.isEmpty()) {
            try (BufferedWriter fileWriter = Files.newBufferedWriter(Paths.get(fileToWrite.getAbsolutePath()))) {
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, this.getCSVFormat());

                for (Patient patient : patientList) {
                    csvPrinter.printRecord(
                            patient.getFirstName(),
                            patient.getLastName(),
                            patient.getGeneralPractitioner(),
                            patient.getSocialSecurityNumber(),
                            patient.getDiagnosis()
                    );
                }

                csvPrinter.flush();
                csvPrinter.close();
            }
        }
    }

    /**
     * Returns the CSVFormat for the CSV files
     * @return The CSVFormat for the CSV files
     */
    private CSVFormat getCSVFormat()
    {
        return CSVFormat
                .DEFAULT
                .withHeader(
                        HEADERS[0],
                        HEADERS[1],
                        HEADERS[2],
                        HEADERS[3],
                        HEADERS[4]
                )
                .withIgnoreHeaderCase()
                .withDelimiter(DELIMITER);
    }
}
