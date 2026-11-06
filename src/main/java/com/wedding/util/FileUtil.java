package com.wedding.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * FileUtil class for handling file operations
 * Provides methods to read and write data to text files with "|" delimiter
 */
public class FileUtil {
    private static final String DATA_DIR = "data";

    static {
        // Create data directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get file path for a data file
     */
    public static String getFilePath(String filename) {
        return DATA_DIR + File.separator + filename;
    }

    /**
     * Read all lines from a file
     */
    public static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        String filepath = getFilePath(filename);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, return empty list
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Write lines to a file
     */
    public static boolean writeFile(String filename, List<String> lines) {
        String filepath = getFilePath(filename);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Append a line to a file
     */
    public static boolean appendToFile(String filename, String line) {
        String filepath = getFilePath(filename);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true))) {
            writer.write(line);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if file exists
     */
    public static boolean fileExists(String filename) {
        return Files.exists(Paths.get(getFilePath(filename)));
    }

    /**
     * Clear file content
     */
    public static boolean clearFile(String filename) {
        String filepath = getFilePath(filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            // Empty file
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Generate unique ID based on current timestamp
     */
    public static String generateId(String prefix) {
        return prefix + "_" + System.currentTimeMillis();
    }
}
