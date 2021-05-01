package no.ntnu.mappe2.marko19907.patientregister;

/**
 * Captures that a given object already exist in the register
 *
 * @author Marko
 * @version 01-05-2021
 */
public class DuplicateKeyException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * Default no-arg constructor without a message
     */
    public DuplicateKeyException()
    {
        super();
    }

    /**
     * Constructor with a message
     * @param message The message to include with the exception
     */
    public DuplicateKeyException(String message)
    {
        super(message);
    }
}