package dk.easv.ticketbar2.gui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class BaseEventController {
    protected int eventID;

    // Setter for event ID
    public void setEventId(int eventId) {
        this.eventID = eventId;
    }

    // Getter for event ID
    public int getEventId() {
        return this.eventID;
    }

    /**
     * Generic method to open a new window and pass the event ID
     * @param fxmlPath Path to the FXML file
     * @param title Window title
     * @param controller The controller instance to initialize
     */
    protected void openWindowWithEventId(String fxmlPath, String title, WindowController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Pass the event ID to the new controller
            controller.setEventId(this.eventID);

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Consider adding proper error handling here
        }
    }

    /**
     * Alternative more flexible window opening method
     * @param fxmlPath Path to the FXML file
     * @param title Window title
     * @param initializer Lambda for controller initialization
     */
    protected void openWindow(String fxmlPath, String title, WindowInitializer initializer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            if (initializer != null) {
                initializer.initialize(loader.getController());
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Functional interface for window initialization
    @FunctionalInterface
    public interface WindowInitializer {
        void initialize(Object controller);
    }

    // Base interface for controllers that need event ID
    public interface WindowController {
        void setEventId(int eventId);
    }
}