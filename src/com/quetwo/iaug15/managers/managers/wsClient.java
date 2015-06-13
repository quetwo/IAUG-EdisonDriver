package com.quetwo.iaug15.managers.managers;

/**
 * Created with IntelliJ IDEA.
 * User: Nicholas Kwiatkowski
 * Date: 6/5/2015
 * Time: 8:52 PM
 */

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class wsClient
{
    boolean DEBUG_MODE;

    private Session userSession = null;
    private MessageHandler messageHandler;
    private URI endpoint = null;

    public wsClient(final URI endpoint, boolean DEBUG_MODE)
    {
        this.DEBUG_MODE = DEBUG_MODE;
        this.endpoint = endpoint;
        try
        {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpoint);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(final Session userSession)
    {
        if (DEBUG_MODE) System.out.println("[SERVER] CONNECT");
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(final Session userSession, final CloseReason reason)
    {
        if (DEBUG_MODE) System.out.println("[SERVER] DISCONNECT");
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(final String message)
    {
        if (DEBUG_MODE) System.out.println("[SERVER] -> " + message);
        if (messageHandler != null) {
            messageHandler.handleMessage(message);
        }
    }

    public void closeConnection()
    {
        try {
            userSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected()
    {
        return (this.userSession != null);
    }

    public void reconnect() throws Exception
    {
        if (!isConnected())
        {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, this.endpoint);
        }
    }

    public void addMessageHandler(final MessageHandler msgHandler)
    {
        messageHandler = msgHandler;
    }

    public void sendMessage(final String message)
    {
        if (DEBUG_MODE) System.out.println("[SERVER] <- " + message);
        userSession.getAsyncRemote().sendText(message);
    }

    public static interface MessageHandler
    {
        public void handleMessage(String message);
    }

}
