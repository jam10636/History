package evil;

import java.io.*;
import java.util.*;

/**
 * Created by KevinTsou on 2/4/2018.
 */

public class evilhangg2 {
    public static void main(String args[]) {
        Scanner reader=new Scanner(System.in);
        File srcfile=new File(args[0]);
        int lenght=Integer.valueOf(args[1]);
        int guess=Integer.valueOf(args[2]);
        evilhangg1 play = new evilhangg1();
        play.startGame(srcfile,lenght);
        play.readin(guess);
        while(play.re()>0)
        {
            System.out.print("Please enter a word: ");
            String read=reader.next();
            while(read.length()>1)
            {
                System.out.print("Please reenter a word: ");
                read=reader.next();
            }
            while(!Character.isAlphabetic(read.charAt(0)))
            {
                System.out.print("Please reenter a word: ");
                read=reader.next();
                while(read.length()>1)
                {
                    System.out.print("Please reenter a word: ");
                    read=reader.next();
                }
            }
            try
            {
                play.makeGuess(read.charAt(0));
                play.check();
            }
            catch(IEvilHangmanGame.GuessAlreadyMadeException e)
            {
                System.out.print(e);
            }
            play.print();
            if(play.win())
            {
                System.out.print("You win!The word is"+play.curp);
                break;
            }
            else if(play.re()>0)
            {
                System.out.print(play.printout());
            }
        }
        if(!play.win())
        {
            System.out.print(play.dic.size());
            System.out.print("You lost the word is "+play.curp);
        }
    }
}
