package com.gabrielaigner.boundary;

import io.quarkus.scheduler.Scheduled;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

/**
 * Server Sent Event Example Resource
 *
 * @author Gabriel Aigner
 */
@Path("sse")
@ApplicationScoped
@Produces(MediaType.SERVER_SENT_EVENTS)
public class BroadcastResource {

    private SseBroadcaster broadcaster;
    private Sse sse;

    /**
     * registerClient - checks if the broadcaster is null and
     * registers the Client on the SseBroadcaster.
     */
    @GET
    public void registerClient(@Context Sse sse, @Context SseEventSink eventSink) {
        this.sse = sse;
        if (broadcaster == null) {
            this.broadcaster = sse.newBroadcaster();
        }
        System.out.println("Registered");
        this.broadcaster.register(eventSink);
    }

    /**
     * notifyClient - gets called every 5s and
     * broadcasts a message to all subscribed clients.
     */
    @Scheduled(every="5s")
    public void notifyClient() {
        if (broadcaster != null){
            this.broadcaster.broadcast(this.sse.newEvent("Hello 5BHIF!"));
        }
    }
}
