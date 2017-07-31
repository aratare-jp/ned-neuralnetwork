package com.deathbydeco.server.realtime;

import com.deathbydeco.utility.Randomiser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rex on 2017/03/31.
 * <p>
 * This class is used to send data back to the client side on real time.
 */
@ServerEndpoint("/ws")
public class RealTimeWebSocket {
    /**
     * Logger for the class.
     */
    private static Logger LOGGER = LoggerFactory.getLogger(RealTimeWebSocket.class);

    /**
     * Hash map for sessions with timers.
     */
    private Map<Session, Timer> sessionTimerMap = new HashMap<>();

    /**
     * Hash map for sessions with its client endpoints.
     */
    private Map<Session, RemoteEndpoint.Async> sessionAsyncMap = new HashMap<>();

    /**
     * Called when a connection is found.
     *
     * @param session the session that is just connected
     */
    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("New session found: " + session.getId());
        LOGGER.info("Sending data to session " + session.getId());
        sessionAsyncMap.put(session, session.getAsyncRemote());
        // Continuously sending data to the client in form of JSON.
        Timer newTimer = new Timer(session.getId());
        newTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Send JSON
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("newData", Randomiser.randomise());
                sessionAsyncMap.get(session).sendText(jsonObject.toString());
            }
        }, (long) 0, (long) 100);
        sessionTimerMap.put(session, newTimer);
    }

    /**
     * Called when the server receives a message from the client.
     *
     * @param session the session that just sent the message
     * @param message the message itself
     */
    @OnMessage
    public void onMessage(Session session, String message) {

    }

    /**
     * Called when the connection is on error.
     *
     * @param throwable the actual error
     */
    @OnError
    public void onError(Throwable throwable) {
        LOGGER.info("Session on error: " + throwable.getMessage());
    }

    /**
     * Called when the connection closed.
     *
     * @param session the session that is just closed
     */
    @OnClose
    public void onClose(Session session) {
        if (sessionTimerMap.containsKey(session)) {
            LOGGER.info(String.format("Session %s closed", session));
            LOGGER.info("Stopping sending data for session" + session);
            sessionTimerMap.get(session).cancel();
            sessionTimerMap.remove(session);
            sessionAsyncMap.remove(session);
        }
    }
}
