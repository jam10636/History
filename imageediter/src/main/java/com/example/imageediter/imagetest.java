package com.example.imageediter;

/**
 * Created by KevinTsou on 2/4/2018.
 */
import java.io.*;
import java.util.*;
public class imagetest {
    public static String print(int height,int width,imageclass[][] px1)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("P3"+"\n"+width+" "+height+"\n"+255+"\n");
        for(int i=0;i<height;i++)
        {
            for(int a=0;a<width;a++)
            {
                sb.append(px1[i][a].red+"\n");
                sb.append(px1[i][a].green+"\n");
                sb.append(px1[i][a].blue+"\n");
            }
        }
        return sb.toString();
    }

    public static void main(String args[]) throws FileNotFoundException
    {
       // File scrfile=new File(args[0]);
        //File destfile=new File(args[1]);
        File scrFile = new File("C:\\Users\\KevinTsou\\AndroidStudioProjects\\image2\\imageediter\\src\\main\\java\\com\\example\\imageediter\\slctemple.ppm");
        File destFile = new File("C:\\Users\\KevinTsou\\AndroidStudioProjects\\image2\\imageediter\\src\\main\\java\\com\\example\\imageediter\\new.ppm");
        String type="motionblur";
        Scanner scanner=new Scanner(scrFile);
        PrintWriter write=new PrintWriter(destFile);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
                        //    ((#[^\\n]*\\n)|(\\s+))+   ((#[^\\n]*\\n)|(\\s+))+  ((#[^\\n]*\\n)|(\\s+)) ((#[^\\n]*\\n)|(\\s+))+
        scanner.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)");
        //(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)
        //((#[^\\n]*\\n)|(\\s+))+     (\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)
        scanner.next();
        int width=scanner.nextInt();
        int height=scanner.nextInt();
        scanner.next();
        imageclass[][] px=new imageclass[height][width];
        for(int i=0;i<height;i++)
        {
            for(int a=0;a<width;a++)
            {
                px[i][a]=new imageclass();
                px[i][a].red=scanner.nextInt();
                px[i][a].green=scanner.nextInt();
                px[i][a].blue=scanner.nextInt();
            }
        }
        if(type.equals("grayscale"))
        {
            for(int i=0;i<height;i++)
            {
                for(int a=0;a<width;a++)
                {
                    px[i][a].grayscale();
                }
            }
        }
        else if(type.equals("invert"))
        {
            for(int i=0;i<height;i++)
            {
                for(int a=0;a<width;a++)
                {
                    px[i][a].invert();
                }
            }
        }
        else if(type.equals("emboss"))
        {
            for(int i=height-1;i>-1;i--)
            {
                for(int a=width-1;a>-1;a--)
                {
                    if(a==0||i==0) {
                        px[i][a].red = 128;
                        px[i][a].green = 128;
                        px[i][a].blue = 128;
                    }
                    else {
                        int reddif = px[i][a].red - px[i - 1][a - 1].red;
                        int greenddif = px[i][a].green - px[i - 1][a - 1].green;
                        int bluedif = px[i][a].blue - px[i - 1][a - 1].blue;
                        int max = Math.max(Math.max(Math.abs(reddif), Math.abs(greenddif)), Math.abs(bluedif));
                        int value = 0;
                        if (Math.abs(reddif) == max) {
                            value = 128 + reddif;
                        } else if (Math.abs(greenddif) == max) {
                            value = 128 + greenddif;
                        } else {
                            value = 128 + bluedif;
                        }
                        if (value > 255) {
                            value = 255;
                        } else if (value < 0) {
                            value = 0;
                        }
                        px[i][a].red=value;
                        px[i][a].blue=value;
                        px[i][a].green=value;
                    }
                }

            }
        }
        else if(type.equals("motionblur"))
        {
            int m=10;
            for(int i=0;i<height;i++)
            {
                for(int a=0;a<width;a++)
                {
                    int redave=0;
                    int greenadv=0;
                    int blueadv=0;
                    if(m+a>width)
                    {
                        for(int x=0;x<width-a;x++)
                        {
                            redave=redave+px[i][x+a].red;
                            greenadv=greenadv+px[i][x+a].green;
                            blueadv=blueadv+px[i][x+a].blue;
                        }
                        redave=redave/(width-a);
                        greenadv=greenadv/(width-a);
                        blueadv=blueadv/(width-a);
                    }
                    else
                    {
                        for(int x=0;x<m;x++)
                        {
                            redave=redave+px[i][x+a].red;
                            greenadv=greenadv+px[i][x+a].green;
                            blueadv=blueadv+px[i][x+a].blue;
                        }
                        redave=redave/m;
                        greenadv=greenadv/m;
                        blueadv=blueadv/m;
                    }
                    px[i][a].red=redave;
                    px[i][a].green=greenadv;
                    px[i][a].blue=blueadv;
                }
            }
        }
        scanner.close();
        write.write(print(height,width,px));
        write.close();
    }
}
