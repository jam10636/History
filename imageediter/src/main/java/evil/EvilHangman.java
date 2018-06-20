package evil;

/**
 * Created by KevinTsou on 1/28/2018.
 */


import java.io.*;
import java.util.*;
/**
 * Created by KevinTsou on 1/25/2018.
 */

public class EvilHangman {

    public static void main(String[] args ) {
       /* String filename=args[0];
        int lenght=Integer.valueOf(args[1]);
        int guess=Integer.valueOf(args[2]);
        File srcfile=new File(filename);*/
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        // System.out.println("Enter a Length: ");
        //int n = reader.nextInt();
        //System.out.println("Enter a guess: ");
        //int x = reader.nextInt();
        File srcfile = new File("C:\\Users\\KevinTsou\\AndroidStudioProjects\\image2\\imageediter\\src\\main\\java\\evil\\dictionary.txt");
        int lenght=5;
        int guess=10;
        EvilHangmanGame play = new EvilHangmanGame();
        play.startGame(srcfile,lenght);
        play.readin(guess);
        System.out.print(play.first());
        while (play.re() != 0) {
            System.out.print("Type a letter: ");
            String read = reader.next();
            while (read.length() > 1) {
                System.out.print("ReType a letter: ");
                read = reader.next();
            }
            char he=read.charAt(0);
            while(!Character.isAlphabetic(he))
            {
                System.out.print("ReType a letter: ");
                read = reader.next();
                while (read.length() > 1) {
                    System.out.print("ReType a letter: ");
                    read = reader.next();
                }
                he=read.charAt(0);
            }

            try {
                play.makeGuess(he);
                play.check();
            } catch (IEvilHangmanGame.GuessAlreadyMadeException a) {
                System.out.print("You already used that\n");
            }
            play.print();
            if(play.win()==true)
            {
                System.out.print("You Win! "+play.curpat);
                break;
            }
            if(play.re()>0) {
                System.out.print(play.printout());
            }
        }
        if(play.re()==0&&play.win()==false)
        {
            System.out.print(play.printout());
            System.out.print("You loser!"+'\n'+"The word was: "+play.curpat);
            System.out.print(play.dic.size());

        }

    }
}

