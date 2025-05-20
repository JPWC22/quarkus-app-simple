package org.example;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApiController {

    private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);

    @Inject
    ApiService apiService;

    // === Create a Message ===
    @POST
    @Transactional
    public Response saveMessage(Message message) {
        LOG.info("Received request to save a new message");
        try {
            apiService.saveMessage(message);
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
        LOG.info("Received request to fetch all messages");
        try {
            return Response.ok(apiService.getMessages()).build();
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
        LOG.info("Received request to update message with ID: {}", id);
        try {
            apiService.updateMessage(id, updatedMessage);
            return Response.ok("{\"status\":\"success\"}").build();
        } catch (NotFoundException e) {
            LOG.warn("Message with ID: {} not found", id);
            return Response.status(Response.Status.NOT_FOUND).entity("{\"status\":\"error\", \"message\":\"Message not found\"}").build();
        } catch (Exception e) {
            LOG.error("Error updating message with ID: {}", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}").build();
        }
    }

    // === Delete a Message ===
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteMessage(@PathParam("id") Long id) {
        LOG.info("Received request to delete message with ID: {}", id);
        try {
            apiService.deleteMessage(id);
            return Response.ok("{\"status\":\"success\"}").build();
        } catch (NotFoundException e) {
            LOG.warn("Message with ID: {} not found", id);
            return Response.status(Response.Status.NOT_FOUND).entity("{\"status\":\"error\", \"message\":\"Message not found\"}").build();
        } catch (Exception e) {
            LOG.error("Error deleting message with ID: {}", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}").build();
        }
    }
}