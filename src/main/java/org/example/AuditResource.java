package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Path("/audit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuditResource {

    private static final Logger LOG = Logger.getLogger(AuditResource.class);

    @Inject
    EntityManager entityManager;

    @GET
    @Path("/{id}")
    public Response getMessageAudit(@PathParam("id") Long id) {
        try {
            AuditReader auditReader = AuditReaderFactory.get(entityManager);

            List<Number> revisions = auditReader.getRevisions(Message.class, id);
            List<Message> messageHistory = revisions.stream()
                    .map(rev -> auditReader.find(Message.class, id, rev))
                    .collect(Collectors.toList());

            return Response.ok(messageHistory).build();
        } catch (Exception e) {
            LOG.error("Error fetching audit history for message ID: " + id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}").build();
        }
    }
}