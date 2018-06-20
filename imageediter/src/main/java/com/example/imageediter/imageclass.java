package com.example.imageediter;

/**
 * Created by KevinTsou on 2/4/2018.
 */
public class imageclass {
    public int red;
    public int green;
    public int blue;
    public void grayscale() {
        int ave=(red+green+blue)/3;
        red=ave;
        green=ave;
        blue=ave;
    }

    public void invert()
    {
        red=255-red;
        blue=255-blue;
        green=255-green;
    }
}
