package evil;

import java.io.*;
import java.util.*;

import javax.sound.midi.SysexMessage;

/**
 * Created by KevinTsou on 1/25/2018.
 */

public class EvilHangmanGame implements IEvilHangmanGame {

    public Set<String> guesword=new TreeSet<>();
    public Set<String>dic=new TreeSet<>();
    public Map<String,Set<String>>pat=new TreeMap<>();
    public Set<String>cur=new TreeSet<>();
    public StringBuilder curp=new StringBuilder();
    private int wordin=0;
    public int guess2=0;
    char curgue;
    public StringBuilder startone=new StringBuilder();
    public String curpat="";
    public void startGame(File dictionary, int wordLength)
    {

        int wordlen=wordLength;
        Scanner scanner=null;
        wordin=wordLength;
        int guesses=0;
        StringBuilder sb=new StringBuilder();
        try {
           scanner=new Scanner(dictionary);

        }
        catch(FileNotFoundException x)
        {
            System.out.print(x);
        }
        while(scanner.hasNext())
        {
            String it=scanner.next().toLowerCase();
            if(it.length()==wordlen)
            {
                dic.add(it);
            }
        }
        for(int i=0;i<wordLength;i++)
        {
            startone.append('-');
        }

    }
    public void guessword(char guess){
        pat.clear();
        for(String w:dic)
        {
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<w.length();i++)
            {
                if(w.charAt(i)==guess)
                {
                    sb.append(w.charAt(i));
                }
                else
                {
                    sb.append("-");
                }
            }
            createpattern(sb,w);
        }
    }
    public void readin(int g)
    {
        guess2=g;
    }
    public void createpattern(StringBuilder sb,String in)
    {
        if(pat.get(sb.toString())!=null)
        {
            pat.get(sb.toString()).add(in);
        }
        else
        {
            Set<String>non=new TreeSet<>();
            non.add(in);
            pat.put(sb.toString(),non);
        }
    }
    public Set<String> makeGuess(char guess) throws IEvilHangmanGame.GuessAlreadyMadeException
    {

           String gues=Character.toString(guess);
           gues=gues.toLowerCase();
           if(guesword.contains(gues))
           {
                throw new IEvilHangmanGame.GuessAlreadyMadeException();
           }
           else
           {
               guesword.add(gues);
               guessword(guess);
               comp(guess);
               curgue=guess;
           }
        return dic;
    }
    public void comp(char guess) {
        comsize();
        if (pat.size() != 1)
        {
            notappear(guess);
            if(pat.size()!=1)
            {
                comletter(guess);
                if(pat.size()!=1)
                {
                    comright(guess);
                }
            }
        }
        for(Set<String> w:pat.values())
        {

            dic=new TreeSet<>(w);

        }
    }
    public void notappear(char word)
    {
        Map<String,Set<String>>two=new TreeMap<String,Set<String>>(pat);
        for(String w:pat.keySet())
        {
            if(w.contains(Character.toString(word)))
            {
                two.remove(w);
            }
        }
        if(two.size()==1)
        {
            pat= new TreeMap<>(two);
        }
    }
     public void comsize()
     {
         int count=0;
         int biggest=0;
         Map<String,Set<String>>two=new TreeMap<String,Set<String>>(pat);
         for(Set<String> w:pat.values())
         {
             if(w.size()>biggest)
                {
                    biggest=w.size();
                }
         }
         for(String w:pat.keySet())
         {
             if(pat.get(w).size()!=biggest)
             {
                 two.remove(w);
             }
         }
         pat=new TreeMap<>(two);
     }
     public void comletter(char word) {
         Map<String,Set<String>>two=new TreeMap<String,Set<String>>(pat);
         int count = 50;
         int small = 0;
         for (String w : pat.keySet()) {
             for (int i = 0; i < w.length(); i++) {
                 if (w.charAt(i) != '-') {
                     small++;
                 }
             }
             if (small < count) {
                 count = small;
             }
         }
         small=0;
         for (String w : pat.keySet()) {
             for (int i = 0; i < w.length(); i++) {
                 if (w.charAt(i) != '-') {
                     small++;
                 }
             }
             if (small != count) {
                 two.remove(w);
             }
             small=0;
         }
         pat=new TreeMap<String,Set<String>>(two);
     }
     public void comright(char word)
     {
         Map<String,Set<String>>two=new TreeMap<String,Set<String>>(pat);
         int cou=wordin;
         int lastindx=-2;
         while(two.size()!=1)
         {
             for(String w:pat.keySet())
             {
                 if(w.lastIndexOf(Character.toString(word),cou)>lastindx)
                 {
                     lastindx=w.lastIndexOf(Character.toString(word),cou);
                 }
             }
             for(String w:pat.keySet())
             {
                 if(w.lastIndexOf(Character.toString(word),cou)!=lastindx)
                 {
                     two.remove(w);
                 }
             }
             pat=new TreeMap<String,Set<String>>(two);
             cou=lastindx-1;
             lastindx=-2;
         }
     }
     public String printout()
     {
        StringBuilder sb=new StringBuilder();
         sb.append("Guess: ");
         sb.append(guess2);
         sb.append('\n');
         sb.append("Guessed word: ");
         for(String w:guesword)
         {
             sb.append(w);
             sb.append(" ");
         }
         sb.append('\n');
         sb.append("word: ");
         //Optional<String> x=cur.stream().findFirst();
         print();
         boolean here=false;
        /*for(int i=0;i<curpat.length();i++)
        {
            for(String x:guesword)
            {
                if(curpat.charAt(i)==x.charAt(0))
                {
                    sb.append(x.charAt(0));
                    here=true;
                }
            }
            if(here==false)
            {
                sb.append('-');
            }
            here=false;
        }*/
        sb.append(curp);
       if(pat.keySet().toArray()[0].toString().equals(startone.toString()))
        {
            sb.append("\nSorry, there are no "+curgue+"" + "'s\n");
        }
        else
        {
            String h=pat.keySet().toArray()[0].toString();
            int count=0;
            for(char x:h.toCharArray())
            {
                if(x==curgue)
                {
                    count++;
                }
            }
            String ma=null;
            if(count>1)
            {
                ma=" are ";
            }
            else
            {
                ma=" is ";
            }
            sb.append("\nYes, there"+ma+count+" "+curgue+'\n');
        }

         return sb.toString();

     }
     public int re()
     {
         return guess2;
     }
    public void check()
    {
        if(pat.get(startone.toString())!=null)
        {
            guess2--;
        }
    }
    public void print()
    {
        boolean here=false;
        curp=new StringBuilder();
        for(String w:dic)
        {
            curpat=new String(w);
            break;
        }
        for(int i=0;i<curpat.length();i++)
        {
            for(String x:guesword)
            {
                if(curpat.charAt(i)==x.charAt(0))
                {
                    curp.append(x.charAt(0));
                    here=true;
                }
            }
            if(here==false)
            {
                curp.append('-');
            }
            here=false;
        }
    }
    public boolean win()
    {
        if(curp.toString().contains("-"))
        {
            return false;
        }
        return true;
    }
    public StringBuilder first()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("Guess: "+guess2+"\nUsed letters: \nword: "+startone.toString()+"\n");
        return sb;
    }
}
