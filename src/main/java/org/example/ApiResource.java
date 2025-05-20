package org.example;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApiResource {

    private static final Logger LOG = LoggerFactory.getLogger(ApiResource.class);

    // === Create a Message ===
    @POST
    @Transactional
    public Response saveMessage(Message message) {
        try {
            message.persist();  // Save object to the database
            LOG.info("Saved message: {}", message.message);
            return Response.ok("{\"status\":\"success\"}").build();
        } catch (Exception e) {
            LOG.error("Error saving message", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}").build();
        }
    }

    // === Retrieve All Messages ===
    @GET
    public Response getMessages() {
        try {
            List<Message> messages = Message.listAll();  // Retrieve all messages
            return Response.ok(messages).build();
        } catch (Exception e) {
            LOG.error("Error retrieving messages", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}").build();
        }
    }

    // === Update a Message ===
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateMessage(@PathParam("id") Long id, Message updatedMessage) {
        try {
            Message existingMessage = Message.findById(id);
            if (existingMessage == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"status\":\"error\", \"message\":\"Message not found\"}").build();
            }

            existingMessage.message = updatedMessage.message;
            existingMessage.persist();
            LOG.info("Updated message with ID: " + id);
            return Response.ok("{\"status\":\"success\"}").build();
        } catch (Exception e) {
            LOG.error("Error updating message", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}").build();
        }
    }

    // === Delete a Message ===
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteMessage(@PathParam("id") Long id) {
        try {
            Message message = Message.findById(id);
            if (message == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"status\":\"error\", \"message\":\"Message not found\"}").build();
            }

            message.delete();
            LOG.info("Deleted message with ID: " + id);
            return Response.ok("{\"status\":\"success\"}").build();
        } catch (Exception e) {
            LOG.error("Error deleting message", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}").build();
        }
    }
}