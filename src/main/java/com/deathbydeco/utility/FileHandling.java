package com.deathbydeco.utility;

import com.deathbydeco.MainClass;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by rex on 2017/03/23.
 * <p>
 * This class provides methods to handle file inside this program.
 */
public class FileHandling {
    /**
     * Get a file in the resource folder.
     *
     * @param relativePath the path to the wanted file.
     *
     * @return the wanted file
     *
     * @throws URISyntaxException   if the path is wrong.
     * @throws NullPointerException if relativePath is null
     * @require relativePath != null
     * @ensure the wanted file
     */
    public static File getFile(String relativePath) throws URISyntaxException {
        if (relativePath == null) {
            throw new NullPointerException("Argument cannot be null.");
        }

        // Retrieve the file
        URL url = MainClass.class.getResource(relativePath);
        return new File(url.toURI());
    }

    /**
     * Check if the file exists.
     *
     * @param relativePath the path to the checked file.
     *
     * @return whether the file exists.
     */
    public static boolean isExisting(String relativePath) {
        if (relativePath == null) {
            throw new NullPointerException("Argument cannot be null.");
        }
        // Retrieve the file and check if it exists.
        try {
            return getFile(relativePath).exists();
        } catch (URISyntaxException e) {
            return false;
        }
    }

    /**
     * Create new file with given path and name.
     *
     * @param relativePath path to the file
     * @param fileName     name of the file
     *
     * @return whether the file has been successfully created.
     *
     * @throws IOException          if file cannot be created
     * @throws NullPointerException if either argument is null
     * @require relativePath != null && fileName != null
     * @ensure whether the file has been successfully created
     */
    public static boolean createFile(String relativePath, String fileName) throws IOException {
        if (relativePath == null || fileName == null) {
            throw new NullPointerException("Argument cannot be null.");
        }
        if (isExisting(relativePath)) {
            throw new IOException("File already exists");
        }
        // Create new file
        try {
            File fileToCreate = getFile(relativePath + File.separator + fileName);
            return fileToCreate.createNewFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }
}
