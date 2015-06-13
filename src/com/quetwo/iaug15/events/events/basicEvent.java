package com.quetwo.iaug15.events.events;

/**
 * Created with IntelliJ IDEA.
 * User: Nicholas Kwiatkowski
 * Date: 4/26/2015
 * Time: 6:24 PM
 */
public abstract class basicEvent
{
    String eventName;

    public basicEvent(String name)
    {
        this.eventName = name;
    }
}
