package com.game.spaceinvadors.core.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Centralized logging for all game events, states, errors, decorators, etc.
 * Writes logs to /logs/logs_game.txt (auto-created).
 */
public class GameLogger {

    private static volatile GameLogger instance;

    private FileWriter writer;
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String LOG_FOLDER = "logs";
    private static final String LOG_FILE = "logs/logs_game.txt";

    private boolean enabled = true; // set false if you want to disable file logging

    // -----------------------
    //     PRIVATE CONSTRUCTOR
    // -----------------------
    private GameLogger() {
        try {
            // Create logs/ directory if missing
            File folder = new File(LOG_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            writer = new FileWriter(LOG_FILE, true); // append mode
            write("SYSTEM", "Logger initialized.");

        } catch (IOException e) {
            System.err.println("[LOGGER ERROR] Could not initialize logger.");
            e.printStackTrace();
            enabled = false;
        }
    }

    // -----------------------
    //     GET INSTANCE
    // -----------------------
    public static GameLogger getInstance() {
        if (instance == null) {
            synchronized (GameLogger.class) {
                if (instance == null) {
                    instance = new GameLogger();
                }
            }
        }
        return instance;
    }

    // -----------------------
    //     CORE WRITE FUNCTION
    // -----------------------
    private synchronized void write(String type, String message) {

        if (!enabled || writer == null) return;

        try {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.write(
                    "[" + timestamp + "] [" + type + "] " + message + System.lineSeparator()
            );
            writer.flush();

        } catch (IOException e) {
            System.err.println("[LOGGER ERROR] Failed to write log.");
            e.printStackTrace();
        }
    }

    // -----------------------
    //     PUBLIC LOG HELPERS
    // -----------------------
    public void logState(String from, String to) {
        write("STATE", from + " -> " + to);
    }

    public void logEvent(String message) {
        write("EVENT", message);
    }

    public void logError(String message) {
        write("ERROR", message);
    }

    public void logDecorator(String message) {
        write("DECORATOR", message);
    }

    // -----------------------
    //     CLEANUP
    // -----------------------
    public void close() {
        if (writer != null) {
            try {
                write("SYSTEM", "Logger shutting down.");
                writer.close();
            } catch (IOException ignored) {}
        }
    }
}
