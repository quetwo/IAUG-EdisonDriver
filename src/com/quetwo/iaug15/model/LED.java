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
