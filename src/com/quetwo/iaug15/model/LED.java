package com.quetwo.iaug15.model;

/**
 * Created with IntelliJ IDEA.
 * User: Nicholas Kwiatkowski
 * Date: 6/5/2015
 * Time: 9:18 AM
 */
public class LED
{
    public int Red = 0;
    public int Green = 0;
    public int Blue = 0;

    public LED()
    {
        Red = 0;
        Green = 0;
        Blue = 0;
    }

    public LED(int _red, int _green, int _blue)
    {
        Red = _red;
        Green = _green;
        Blue = _blue;
    }

    public String toString()
    {
        return "R=" + Red+ " G=" + Green + " B=" + Blue;
    }
}
