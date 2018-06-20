package spell;
import java.util.*;
import java.io.*;
/**
 * Created by KevinTsou on 1/20/2018.
 */

public class SpellCorrector implements ISpellCorrector{
    private Trie root=new Trie();
    private Trie compare=new Trie();
    public void deletion(String input,Set<String> in)
    {
        StringBuilder sb=new StringBuilder(input);
        if(input.length()>1)
        {
            for (int i = 0; i < input.length(); i++) {
                sb.deleteCharAt(i);
                in.add(sb.toString());
                sb=new StringBuilder(input);
            }
        }
    }
    public void insertion(String input,Set<String>in)
    {
        StringBuilder sb=new StringBuilder(input);
        for(int i=0;i<input.length()+1;i++)
        {
            for(int a=0;a<26;a++)
            {
                sb.insert(i,(char) ('a' + a));
                in.add(sb.toString());
                sb=new StringBuilder(input);
            }
            //sb=new StringBuilder(input);
        }
    }
    public void alteration(String input,Set<String>in)
    {
        StringBuilder sb=new StringBuilder(input);
        for(int i=0;i<input.length();i++)
        {
            for(int a=0;a<26;a++)
            {
                if((char) ('a' + a)!=input.charAt(i)) {
                    sb.setCharAt(i, (char) ('a' + a));
                    in.add(sb.toString());
                }
            }
            sb=new StringBuilder(input);
        }
    }
    public void transpos(String input,Set<String>in)
    {
        StringBuilder sb=new StringBuilder(input);
        for(int i=0;i<input.length()-1;i++)
        {
            StringBuilder sb1=new StringBuilder(sb);
            char x=sb.charAt(i);
            char y=sb.charAt(i+1);
            sb1.setCharAt(i,y);
            sb1.setCharAt(i+1,x);
            in.add(sb1.toString());

        }
    }
    public String suggest(Set<String>in)
    {
        int maxval=0;
        String sug=null;
        for(String w: in)
        {
            if(root.find(w)!=null)
            {
                if(root.find(w).getValue()>maxval)
                {
                    maxval=root.find(w).getValue();
                    sug=w;
                }

            }
        }
        return sug;
    }
    public String suggest2(Set<String>in)
    {
        Set<String>sec= new TreeSet<String>();
        for(String w:in)
        {
            deletion(w,sec);
            insertion(w,sec);
            transpos(w,sec);
            alteration(w,sec);
        }
        return suggest(sec);
    }
    public void useDictionary(String dictionaryFileName) throws IOException
    {

        File srcFile=new File("C:\\Users\\KevinTsou\\AndroidStudioProjects\\image2\\imageediter\\src\\main\\java\\spell\\words.txt");
        //File srcFile=new File(dictionaryFileName);
        Scanner scanner = new Scanner(srcFile);
        String input;
        while(scanner.hasNext())
        {
            input=scanner.next().toLowerCase();
            root.add(input);
        }
        srcFile=new File("C:\\Users\\KevinTsou\\AndroidStudioProjects\\image2\\imageediter\\src\\main\\java\\spell\\words2.txt");
        scanner=new Scanner(srcFile);
        while(scanner.hasNext())
        {
            input=scanner.next().toLowerCase();
            compare.add(input);
        }
        System.out.print(root.toString());
        System.out.print(compare.find("car").getValue());
    }
    public String suggestSimilarWord(String inputWord)
    {
        Set<String> sugg= new TreeSet<String>();
        inputWord=inputWord.toLowerCase();
        String suggest=null;
        if(inputWord=="")
        {
            return null;
        }
        else if(root.find(inputWord)==null)
        {
            deletion(inputWord,sugg);
            insertion(inputWord,sugg);
            alteration(inputWord,sugg);
            transpos(inputWord,sugg);
            suggest=suggest(sugg);
            if(suggest==null)
            {
               suggest=suggest2(sugg);
            }
            else
            {
                return suggest;
            }
        }
        else
        {
            return inputWord;
        }
    return suggest ;
    }
}
