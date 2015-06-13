package com.quetwo.iaug15.managers.managers;

import com.google.common.eventbus.EventBus;
import com.quetwo.iaug15.events.events.wsIncomingEvent;


/**
 * Created with IntelliJ IDEA.
 * User: Nicholas Kwiatkowski
 * Date: 4/26/2015
 * Time: 3:39 PM
 */
public class wsMessageReceiver implements wsClient.MessageHandler
{
    private EventBus eventBus;

    public wsMessageReceiver(EventBus eventBus)
    {
        this.eventBus = eventBus;
    }

    public void handleMessage(String message)
    {
        // Messages are in the format of "X00 PARAMETER"
        if (message.regionMatches(4," ",0,1))
        {
            eventBus.post(new wsIncomingEvent("wsIncomingMessage",message.substring(0,4),message.substring(5)));
        }
        else
        {
            System.out.println("[SERVER] Unknown Packet : " + message);
        }
    }
}
