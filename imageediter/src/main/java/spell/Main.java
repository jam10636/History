package spell;

import java.io.IOException;
import java.util.*;

public class Main {
	

	public static void main(String[] args) throws IOException {
		
		/*String dictionaryFileName = args[0];
		String inputWord = args[1];
		ISpellCorrector te=new spelltest2();
		te.useDictionary(dictionaryFileName);
		//corrector.useDictionary(dictionaryFileName);
		String suggestion = te.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}
		System.out.println("Suggestion is: " + suggestion);*/
		Set<String>dic=new TreeSet<>();
		String k=null;
		dic.add("Yes");
		for(String w:dic)
		{
			k=w;
		}
		dic.clear();
		System.out.print(k);

	}

}
