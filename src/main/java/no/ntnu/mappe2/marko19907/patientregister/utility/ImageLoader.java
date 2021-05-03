package no.ntnu.mappe2.marko19907.patientregister.utility;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class ImageLoader represents an image loader
 * It is responsible for loading and returning images from the disk
 *
 * @author Marko
 * @version 25-04-2021
 */
public class ImageLoader
{
    private static volatile ImageLoader instance;
    private final Logger logger;

    /**
     * ImageLoader constructor
     * Set to private to help ensure only a single instance
     */
    private ImageLoader()
    {
        this.logger = Logger.getLogger(this.getClass().getSimpleName());
    }

    /**
     * Returns an instance of an ImageLoader
     * @return An instance of an ImageLoader
     */
    public static ImageLoader getInstance()
    {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                instance = new ImageLoader();
            }
        }
        return instance;
    }

    /**
     * Returns an ImageView with the given image
     * @param imageName The image name corresponding to the image to return, can not be blank or null
     * @return ImageView with the given image name,
     * or null if the image can not be found or loaded,
     * or null if the given String is blank or null.
     */
    public ImageView getImage(String imageName)
    {
        // Guard condition
        if (imageName == null || imageName.isBlank()) {
            return null;
        }

        ImageView toReturn = null;
        String imageLocation = imageName + ".png";

        try {
            Image image = new Image(imageLocation);
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            toReturn = imageView;
        }
        catch (IllegalArgumentException e) {
            String message = "Image " + imageLocation + " could not be loaded!";
            this.logger.log(Level.INFO, message);
        }

        return toReturn;
    }
}