package spell;

/**
 * Created by KevinTsou on 2/4/2018.
 */
import java.io.*;
import java.util.*;
public class spelltest2  implements ISpellCorrector{
    spelltest root=new spelltest();
    public void deletion(Set<String>in,String word)
    {
        StringBuilder sb=new StringBuilder(word);
        if(word.length()>1) {
            for (int i = 0; i < word.length(); i++) {
                sb.deleteCharAt(i);
                in.add(sb.toString());
                sb=new StringBuilder(word);
            }
        }
    }
    public void insert(Set<String>in,String word)
    {
        StringBuilder sb=new StringBuilder(word);
        for(int i=0;i<word.length();i++)
        {
            for(int a=0;a<26;a++)
            {
                sb.insert(i,(char)(a+'a'));
                in.add(sb.toString());
                sb=new StringBuilder(word);
            }
        }
    }
    public void alteration(Set<String>in, String word)
    {
        StringBuilder sb=new StringBuilder(word);
        for(int i=0;i<word.length();i++)
        {
            for(int a=0;a<26;a++)
            {
                if(word.charAt(i)!=(char)(a+'a'))
                {
                    sb.setCharAt(i,(char)(a+'a'));
                    in.add(sb.toString());
                }
            }
            sb=new StringBuilder(word);
        }
    }
    public void transpose(Set<String>in,String word)
    {
        StringBuilder sb=new StringBuilder(word);
        {
            for(int i=0;i<word.length()-1;i++)
            {
                char x=sb.charAt(i);
                char y=sb.charAt(i+1);
                sb.setCharAt(i,y);
                sb.setCharAt(i+1,x);
                in.add(sb.toString());
                sb=new StringBuilder(word);
            }
        }
    }
    public String suggest1(Set<String>in)
    {
        int max=0;
        String h=null;
        for(String w:in)
        {
            if(root.find(w)!=null)
            {
                if(root.find(w).getValue()>max)
                {
                    h=new String(w);
                    max=root.find(w).getValue();
                }
            }
        }
        return h;
    }
    public String suggest2(Set<String>in)
    {
        Set<String>sec=new TreeSet<>(in);
        for(String w:in)
        {
            deletion(sec,w);
            insert(sec,w);
            alteration(sec,w);
            transpose(sec,w);
        }
        return suggest1(sec);
    }
    public void useDictionary(String dictionaryFileName) throws IOException
    {
        File srcfile=new File(dictionaryFileName);
        Scanner scanner=new Scanner(srcfile);
        while(scanner.hasNext())
        {
            root.add(scanner.next().toLowerCase());
        }
    }

    /**
     * Suggest a word from the dictionary that most closely matches
     * <code>inputWord</code>
     * @param inputWord
     * @return The suggestion or null if there is no similar word in the dictionary
     */
    public String suggestSimilarWord(String inputWord){
        String suggestion=null;
        Set<String>h=new TreeSet<>();
        inputWord=inputWord.toLowerCase();
        if(inputWord=="")
        {
            return  null;
        }
        if(root.find(inputWord)!=null)
        {
            return inputWord;
        }
        else if(root.find(inputWord)==null)
        {
            deletion(h,inputWord);
            insert(h,inputWord);
            transpose(h,inputWord);
            alteration(h,inputWord);
            suggestion=suggest1(h);
            if(suggestion==null)
            {
                suggestion=suggest2(h);
            }

        }
        return suggestion;
    }
}
