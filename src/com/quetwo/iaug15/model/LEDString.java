package com.quetwo.iaug15.model;

import com.github.mraa4j.MraaException;
import com.github.mraa4j.UART;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Nicholas Kwiatkowski
 * Date: 6/4/2015
 * Time: 3:43 PM
 */
public class LEDString
{
    private int LEDsSize;
    private UART Arduino;
    private BufferedOutputStream ArduionoBuffer;

    public LEDString(int numOfLEDS, int _uartNum) throws MraaException, IOException, InterruptedException
    {
        LEDsSize = numOfLEDS;
        Arduino = new UART(_uartNum);

        try
        {
            ArduionoBuffer = new BufferedOutputStream(new FileOutputStream(Arduino.getDevPath()));
        }
        catch (IOException e)
        {
            System.err.println("Unable to open connection to Arduino...");
        }

        replaceALL(new LED()); // initialize the lights to be off.
        updateLEDS();
    }

    public void setLED(LED newLED, int index) throws IOException, InterruptedException
    {
        String command = index + "," + newLED.Red + "," + newLED.Green + "," + newLED.Blue + '\n';
        ArduionoBuffer.write(command.getBytes());
        ArduionoBuffer.flush();
        Thread.sleep(100);
    }

    public void replaceALL(LED newLED) throws IOException, InterruptedException
    {
        String command = "-1," + newLED.Red + "," + newLED.Green + "," + newLED.Blue + '\n';
        ArduionoBuffer.write(command.getBytes());
        ArduionoBuffer.flush();
        Thread.sleep(100);
    }

    public void updateLEDS() throws IOException, InterruptedException
    {
        String command = "-2,0,0,0" + '\n';
        ArduionoBuffer.write(command.getBytes());
        ArduionoBuffer.flush();
        Thread.sleep(100);
    }


}
