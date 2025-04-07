package dk.easv.ticketbar2.gui.controllers;

import dk.easv.ticketbar2.be.Events;
import dk.easv.ticketbar2.be.Tickets;
import dk.easv.ticketbar2.bll.EventsManager;
import dk.easv.ticketbar2.bll.TicketsManager;
import dk.easv.ticketbar2.dal.exceptions.EventsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class PrintTicketsController  extends  BaseEventController{
    private final TicketsManager ticketService = new TicketsManager();
    private int eventId;

    @FXML
    private TextField txtTicketType;
    @FXML private TextField txtCustomerName;
    @FXML private TextField txtCustomerEmail;
    @FXML private Spinner<Integer> spnQuantity;
    @FXML private TextArea txtDescription;
    @FXML private Button btnPrintTickets;

    private final EventsManager eventsManager = new EventsManager();
    private int eventID;
    public int getEventID() {
        return eventID;
    }

    // Initialize spinner
    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spnQuantity.setValueFactory(valueFactory);
    }

    // This method will now be called AFTER setEventID is called
    private void loadEventDetails() {
        if (eventID != 0) {
            try {
                Events event = eventsManager.getEventById(eventID);
                System.out.println("Event loaded: " + event.getEventName());
                // Optionally, set event details in UI fields
            } catch (EventsException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Method to load ticket details by ID
    public void loadTicket(int ticketId) {
        Tickets ticket = ticketService.getTickets(ticketId);

        if (ticket != null) {
            txtTicketType.setText(ticket.getTicketType());
            txtCustomerName.setText(ticket.getCustomerName());
            txtCustomerEmail.setText(ticket.getCustomerEmail());
            txtDescription.setText(ticket.getDescription());
        } else {
            showAlert("Ticket Not Found", "The requested ticket could not be found.");
        }
    }

    // Show alert message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
