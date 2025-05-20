package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class ApiService {

    private static final Logger LOG = LoggerFactory.getLogger(ApiService.class);

    public void saveMessage(Message message) {
        LOG.info("Saving message: {}", message.message);
        try {
            message.persist();  // Save object to the database
            LOG.info("Successfully saved message: {}", message.message);
        } catch (Exception e) {
            LOG.error("Error while saving message: {}", message.message, e);
            throw new RuntimeException("Failed to save message", e);
        }
    }

    public List<Message> getMessages() {
        LOG.info("Fetching all messages");
        try {
            List<Message> messages = Message.listAll();  // Retrieve all messages
            LOG.info("Retrieved {} messages", messages.size());
            return messages;
        } catch (Exception e) {
            LOG.error("Error while fetching messages", e);
            throw new RuntimeException("Failed to fetch messages", e);
        }
    }

    public void updateMessage(Long id, Message updatedMessage) {
        LOG.info("Updating message with ID: {}", id);
        Message existingMessage = Message.findById(id);
        if (existingMessage == null) {
            LOG.warn("Message with ID: {} not found", id);
            throw new NotFoundException("Message not found");
        }

        // Update the message
        existingMessage.message = updatedMessage.message;
        try {
            existingMessage.persist();  // Save updated message
            LOG.info("Successfully updated message with ID: {}", id);
        } catch (Exception e) {
            LOG.error("Error while updating message with ID: {}", id, e);
            throw new RuntimeException("Failed to update message", e);
        }
    }

    public void deleteMessage(Long id) {
        LOG.info("Deleting message with ID: {}", id);
        Message message = Message.findById(id);
        if (message == null) {
            LOG.warn("Message with ID: {} not found", id);
            throw new NotFoundException("Message not found");
        }

        try {
            message.delete();  // Delete the message
            LOG.info("Successfully deleted message with ID: {}", id);
        } catch (Exception e) {
            LOG.error("Error while deleting message with ID: {}", id, e);
            throw new RuntimeException("Failed to delete message", e);
        }
    }
}