package com.quetwo.iaug15.managers.managers;

import com.google.common.eventbus.Subscribe;
import com.quetwo.iaug15.events.events.wsIncomingEvent;
import com.quetwo.iaug15.model.Votes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nicholas Kwiatkowski
 * Date: 4/26/2015
 * Time: 6:32 PM
 */
public class ActionManager
{
    private boolean DEBUG_MODE;
    private List<Votes> wsQueue = new ArrayList<>();

    public ActionManager(boolean DEBUG_MODE)
    {
        this.DEBUG_MODE = DEBUG_MODE;
    }

    @Subscribe
    public void handleIncomingAction(wsIncomingEvent event)
    {

        switch (event.controlMethod)
        {

            case "VOTE":
            {
                // parameter 0 = high vote
                // parameter 1 = low vote
                // parameter 2 = median

                String[] incomingVote = event.parameter.split(",");
                Votes myVote = new Votes();
                myVote.lowVote = Integer.parseInt(incomingVote[0]);
                myVote.highVote = Integer.parseInt(incomingVote[1]);
                myVote.medianVote = Integer.parseInt(incomingVote[2]);
                wsQueue.add(myVote);

                break;
            }

            default:
            {
                System.out.println("Unhandled Event Code : " + event.controlMethod);
            }
        }

    }

    public int getWsQueueSize()
    {
        return wsQueue.size();
    }

    public List<Votes> getWsQueue()
    {
        List<Votes> myReturn = new ArrayList<>(wsQueue.size());
        for(Votes item : wsQueue)
        {
            myReturn.add(item);
        }
        wsQueue.clear();
        return myReturn;
    }
}
