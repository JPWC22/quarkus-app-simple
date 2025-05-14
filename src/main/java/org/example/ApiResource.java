package org.example;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/save")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApiResource {

    private static final Logger LOG = Logger.getLogger(ApiResource.class);

    @POST
    @Transactional
    public Response saveMessage(Message message) {
        try {
            message.persist();  // Save object to the database
            LOG.info("Saved message: " + message.message);
            return Response.ok("{\"status\":\"success\"}").build();
        } catch (Exception e) {
            LOG.error("Error saving message", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}").build();
        }
    }
}