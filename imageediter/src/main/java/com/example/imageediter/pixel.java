package com.example.imageediter;

/**
 * Created by jam10 on 1/12/2018.
 */

public class pixel {
    public int red;
    public int green;
    public int blue;

    public void grayscale() {
        int average = (red + green + blue) / 3;
        red = average;
        green = average;
        blue = average;
    }

    public void invert()
    {
        red=255-red;
        green=255-green;
        blue=255-blue;
    }

}

