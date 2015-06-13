package com.quetwo.iaug15.events.events;

/**
 * Created with IntelliJ IDEA.
 * User: Nicholas Kwiatkowski
 * Date: 4/26/2015
 * Time: 6:26 PM
 */
public class wsIncomingEvent extends basicEvent
{
    public String controlMethod;
    public String parameter;

    public wsIncomingEvent(String name, String controlMethod, String parameter)
    {
        super(name);
        this.controlMethod = controlMethod;
        this.parameter = parameter;
    }
}
