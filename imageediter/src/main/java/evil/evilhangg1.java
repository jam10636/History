package evil;

import java.io.*;
import java.util.*;

/**
 * Created by KevinTsou on 2/4/2018.
 */

public class evilhangg1 implements IEvilHangmanGame {
    public Map<String,Set<String>>pat=new TreeMap<>();
    public Set<String>dic=new TreeSet<>();
    int guessleft=0;
    Set<String>guessword=new TreeSet<>();
    int wordlen=0;
    StringBuilder curpat=new StringBuilder();
    String curp;
    StringBuilder startone=new StringBuilder();
    char curgue;
    public void startGame(File dictionary, int wordLength)
    {
        Scanner scanner=null;
        wordlen=wordLength;
        try
        {
         scanner=new Scanner(dictionary);
        }
        catch (FileNotFoundException e)
        {
            System.out.print(e);
        }
        while(scanner.hasNext())
        {
            String h=scanner.next().toLowerCase();
            if(h.length()==wordLength)
            {
                dic.add(h);
            }
        }
        for(int i=0;i<wordLength;i++)
        {
            startone.append('-');
        }
    }
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException
    {
        String g=Character.toString(guess).toLowerCase();
        if(guessword.contains(g))
        {
            throw new IEvilHangmanGame.GuessAlreadyMadeException();
        }
        else
        {
            curgue=guess;
            readinguess(guess);
            comp(guess);
            guessword.add(g);

        }
        return dic;
    }
    public void readinguess(char in)
    {
        pat.clear();
        for(String w:dic)
        {
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<w.length();i++)
            {
                if(w.charAt(i)==in)
                {
                    sb.append(in);
                }
                else
                {
                    sb.append('-');
                }
            }
            createpattern(w,sb);
        }
    }
    public void createpattern(String in,StringBuilder sb)
    {
        if(pat.get(sb.toString())!=null){
            pat.get(sb.toString()).add(in);
        }
        else
        {
            Set<String>non=new TreeSet<>();
            non.add(in);
            pat.put(sb.toString(),non);
        }
    }
    public void comp(char in)
    {
        compsize();
        if(pat.size()!=1)
        {
            notappear(in);
            if(pat.size()!=1)
            {
                completter(in);
                if(pat.size()!=1)
                {
                    comright(in);
                }
            }
        }
        for(Set<String>w:pat.values())
        {
            dic=new TreeSet<>(w);
        }
    }
    public void compsize()
    {
        Map<String,Set<String>>two=new TreeMap<>(pat);
        int max=0;
        for(Set<String>w:pat.values())
        {
            if(w.size()>max)
            {
                max=w.size();
            }
        }
        for(String w:pat.keySet())
        {
            if(pat.get(w).size()!=max)
            {
                two.remove(w);
            }
        }
        pat=new TreeMap<>(two);
    }
    public void notappear(char in)
    {
        Map<String,Set<String>>two=new TreeMap<>(pat);
        for(String w:pat.keySet())
        {
            if(w.contains(Character.toString(in)))
            {
                two.remove(w);
            }
        }
        if(two.size()==1)
        {
            pat=new TreeMap<>(two);
        }
    }
    public void completter(char in)
    {
        int count=50;
        int small=0;
        Map<String,Set<String>>two=new TreeMap<>(pat);
        for(String w:pat.keySet())
        {
            for(int i=0;i<w.length();i++)
            {
                if(w.charAt(i)!='-')
                {
                    small++;
                }
            }
            if(small<count)
            {
                count=small;
            }
            small=0;
        }
        for(String w:pat.keySet())
        {
            for(int i=0;i<w.length();i++)
            {
                if(w.charAt(i)!='-')
                {
                    small++;
                }
            }
            if(small!=count)
            {
                two.remove(w);
            }
            small=0;
        }
        pat=new TreeMap<>(two);
    }
    public void comright(char in)
    {
        int lastindex=-2;
        int cou=wordlen;
        Map<String,Set<String>>two=new TreeMap<>(pat);
        while(pat.size()!=1) {
            for (String w : pat.keySet()) {
                if (w.lastIndexOf(Character.toString(in), cou) > lastindex) {
                    lastindex = w.lastIndexOf(Character.toString(in), cou);
                }
            }
            for (String w : pat.keySet()) {
                if (w.lastIndexOf(Character.toString(in), cou) != lastindex) {
                    two.remove(w);
                }
            }
            cou=lastindex-1;
            lastindex=-2;
            pat=new TreeMap<>(two);
        }

    }
    public void check()
    {
        if(pat.get(startone.toString())!=null)
        {
         guessleft--;
        }
    }
    public int re()
    {
        return guessleft;
    }
    public void print() {
        curpat=new StringBuilder();
        boolean here=false;
        for (String w : dic) {
            curp = new String(w);
            break;
        }
        for (int i = 0; i < curp.length(); i++)
        {
            for(String w:guessword)
            {
                if(curp.charAt(i)==w.charAt(0))
                {
                    curpat.append(w.charAt(0));
                    here=true;
                }
            }
            if(here==false)
            {
                curpat.append('-');
            }
            here=false;
        }
    }
    public boolean win()
    {
        if(curpat.toString().contains("-"))
        {
            return false;
        }
        return true;
    }
    public String printout()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("You have "+guessleft+" left\n");
        sb.append("Used letters: ");
        for(String w:guessword)
        {
            sb.append(w+" ");
        }
        sb.append('\n');
        print();
        sb.append("word: "+curpat+"\n");
        if(pat.get(startone.toString())!=null)
        {
            sb.append("Sorry, there are no"+ curgue+'s'+"\n");
        }
        else
        {
            sb.append("Yes, there is "+curgue+"\n");
        }
        return sb.toString();
    }
    public void readin(int x)
    {
     guessleft=x;
    }

}
