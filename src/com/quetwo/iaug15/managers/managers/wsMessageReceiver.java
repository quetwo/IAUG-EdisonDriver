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
