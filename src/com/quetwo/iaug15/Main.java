/*
 *
 *   Copyright 2015 Michigan State University Board of Trustees
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package com.quetwo.iaug15;

import com.github.mraa4j.Mraa4j;
import com.google.common.eventbus.EventBus;
import com.quetwo.iaug15.managers.managers.ActionManager;
import com.quetwo.iaug15.managers.managers.wsClient;
import com.quetwo.iaug15.managers.managers.wsMessageReceiver;
import com.quetwo.iaug15.model.LED;
import com.quetwo.iaug15.model.LEDString;
import com.quetwo.iaug15.model.Votes;

import java.net.URI;
import java.util.List;

public class Main {

    public static final String SERVER_LOCATION = "vote.suroot.com:8080";
    public static final boolean DEBUG_MODE = true;

    public static EventBus eventBus;
    public static ActionManager actionManager;
    public static Votes lastVote = new Votes();

    public static void main(String[] args) throws Exception
    {
        eventBus = new EventBus();

        System.out.println("---------------------------------------------");
        System.out.println("- IAUG Lights Demo for EDP Application       ");
        System.out.println("---------------------------------------------\n");

        actionManager = new ActionManager(DEBUG_MODE);
        eventBus.register(actionManager);

        LED medianVoteMarker = new LED(0,255,0);  // Make the median marker bright green
        LED highLowMarkers = new LED(5,0,5);      // Make the low and high markers light blue

        // init MRAA4J library to talk directly to pins
        Mraa4j mraa = Mraa4j.getInstance();
        System.out.println("MRAA Version = " + mraa.getPlatformVersion());
        System.out.println("MRAA Platform = " + mraa.getPlatformType());

        LEDString stringOfLEDS = new LEDString(20,0);

        if (DEBUG_MODE) System.out.println("Connecting to WebSocket Server " + SERVER_LOCATION + "...");
        final wsClient wsEndpoint = new wsClient(new URI("ws://" + SERVER_LOCATION + "/wsEndpoint/5178849999"), DEBUG_MODE);
        wsEndpoint.addMessageHandler(new wsMessageReceiver(eventBus));

        stringOfLEDS.setLED(new LED(255,0,0),19);
        stringOfLEDS.updateLEDS();

        while(true)
        {
            if (actionManager.getWsQueueSize() > 0)
            {
                List<Votes> queuedMessages = actionManager.getWsQueue();
                for (int i = 0; i < queuedMessages.size(); i++)
                {
                    stringOfLEDS.replaceALL(new LED());             // turn everything dark.
                    stringOfLEDS.setLED(highLowMarkers,queuedMessages.get(i).highVote);
                    stringOfLEDS.setLED(highLowMarkers,queuedMessages.get(i).lowVote);
                    stringOfLEDS.setLED(medianVoteMarker,queuedMessages.get(i).medianVote);

                    stringOfLEDS.updateLEDS();

                    lastVote = queuedMessages.get(i);
                }
            }
            if (!wsEndpoint.isConnected())
            {
                // reconnect to ws
                wsEndpoint.reconnect();
            }
        }

    }


}
