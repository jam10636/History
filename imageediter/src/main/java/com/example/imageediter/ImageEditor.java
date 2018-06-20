package com.example.imageediter;
import java.io.*;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;

import java.util.*;
//import java.util.Scanner;


// Copy one text file to another
// (this is not the best way to copy a text file, but it shows how to
// do simple file i/o without dealing with exceptions)

public class ImageEditor {
    static String tostring(pixel[][] x,int height,int width)
    {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("P3");
        stringBuilder.append("\n");
        stringBuilder.append(Integer.toString(width));
        stringBuilder.append(' ');
        stringBuilder.append(Integer.toString(height));
        stringBuilder.append("\n");
        stringBuilder.append("255");
        stringBuilder.append("\n");
        int o=0;
        for(int i=0;i<height;i++)
        {
            for(int a=0;a<width;a++)
            {
                stringBuilder.append(Integer.toString(x[i][a].red));
                stringBuilder.append("\n");
                stringBuilder.append(Integer.toString(x[i][a].green));
                stringBuilder.append("\n");
                stringBuilder.append(Integer.toString(x[i][a].blue));
                stringBuilder.append("\n");
            }
            o++;
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {

        // args[0] is the source file
        // args[1] is the destination file
        // args[2] is edit12
       // String inputarg=args[0];
        //String output=args[1];
        //String type=args[2];
        String type="emboss";
        File srcFile = new File("C:\\Users\\KevinTsou\\AndroidStudioProjects\\image2\\imageediter\\src\\main\\java\\com\\example\\imageediter\\slctemple.ppm");
        File destFile = new File("C:\\Users\\KevinTsou\\AndroidStudioProjects\\image2\\imageediter\\src\\main\\java\\com\\example\\imageediter\\new.ppm");
        //File srcFile=new File(inputarg);
        //File destFile=new File(output);
            Scanner scanner = new Scanner(srcFile);
            PrintWriter writer = new PrintWriter(destFile);
            scanner.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)");
            //"((#[^\\n]*\\n)|(\\s+))+"
        //(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)
        //((#[^\\n]*\\n)|(\\s+))+
            scanner.next();
            int width=scanner.nextInt();
            int height=scanner.nextInt();
            pixel[][] imagedit= new pixel[height][width];
            scanner.next();
           for(int i=0;i<height;i++) {
               for (int a = 0; a < width; a++) {
                   imagedit[i][a] = new pixel();
                   imagedit[i][a].red = scanner.nextInt();
                   imagedit[i][a].green = scanner.nextInt();
                   imagedit[i][a].blue = scanner.nextInt();
               }
           }
        System.out.print(imagedit[450][999].red);

        if(type.equals("invert")) {
                for (int i = 0; i < height; i++) {
                    for (int a = 0; a < width; a++) {
                        imagedit[i][a].invert();
                    }
                }
            }
            else if(type.equals("grayscale")) {
             for(int i=0;i<height;i++) {
                for (int a = 0; a < width; a++) {
                imagedit[i][a].grayscale();
                }
             }
           }
           else if(type.equals("emboss"))
            {
                for(int i=height-1;i>-1;i--)
                {
                    for(int a=width-1;a>-1;a--)
                    {
                        int rediff=0;
                        int greendiff=0;
                        int bluediff=0;
                        int value=0;
                        if(i==0||a==0)
                        {
                            imagedit[i][a].red=128;
                            imagedit[i][a].green=128;
                            imagedit[i][a].blue=128;
                        }
                        else
                        {
                            int red=imagedit[i][a].red-imagedit[i-1][a-1].red;
                            int green=imagedit[i][a].green-imagedit[i-1][a-1].green;
                            int blue=imagedit[i][a].blue-imagedit[i-1][a-1].blue;
                            int maxnum=Math.max(Math.max(Math.abs(red),Math.abs(green)),Math.abs(blue));
                            if(maxnum==Math.abs(red))
                            {
                                value=128+red;
                            }
                            else if(maxnum==Math.abs(green))
                            {
                                value=128+green;
                            }
                            else
                            {
                                value=128+blue;
                            }
                            if(value<0)
                            {
                                value=0;
                            }
                            else if(value>255)
                            {
                                value=255;
                            }
                            imagedit[i][a].red=value;
                            imagedit[i][a].green=value;
                            imagedit[i][a].blue=value;
                        }
                    }
                }
            }
            else if (type.equals("motionblur")) {
                int input = Integer.parseInt(args[4]);
                for (int i = 0; i < height; i++) {
                    for (int a = 0; a < width; a++) {
                        int redave = 0;
                        int greenadv = 0;
                        int blueadv = 0;
                        if (a + input > width) {
                            for (int x = 0; x < width - a; x++) {
                                redave += imagedit[i][a + x].red;
                                greenadv += imagedit[i][a + x].green;
                                blueadv += imagedit[i][a + x].blue;
                            }
                            redave = redave / (width - a);
                            greenadv = greenadv / (width - a);
                            blueadv = blueadv / (width - a);
                        } else {

                            for (int x = 0; x < input; x++) {
                                redave += imagedit[i][a + x].red;
                                greenadv += imagedit[i][a + x].green;
                                blueadv += imagedit[i][a + x].blue;
                            }
                            redave = redave / input;
                            greenadv = greenadv / input;
                            blueadv = blueadv / input;
                        }
                        imagedit[i][a].red = redave;
                        imagedit[i][a].green = greenadv;
                        imagedit[i][a].blue = blueadv;
                    }
                }
            }

            writer.write(tostring(imagedit,height,width));
            scanner.close();
            writer.close();
        }

    }

