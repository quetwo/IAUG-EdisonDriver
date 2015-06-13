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
