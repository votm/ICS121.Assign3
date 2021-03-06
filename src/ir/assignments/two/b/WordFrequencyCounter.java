// Leon Thai (#55997869)

package ir.assignments.two.b;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Enumeration;


/**
 * Counts the total number of words and their frequencies in a text file.
 */
public final class WordFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private WordFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique word in the original list. The frequency of each word
	 * is equal to the number of times that word occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied words sorted
	 * alphabetically.
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["this", "sentence", "repeats", "the", "word", "sentence"]
	 * 
	 * The output list of frequencies should be 
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of word frequencies, ordered by decreasing frequency.
	 */
	public static List<Frequency> computeWordFrequencies(List<String> words) {
		
		//To store String key and Int value
		Hashtable temp = new Hashtable();
		
		for(int i=0; i < words.size(); i++)
		{
			if(temp.containsKey(words.get(i)) == true)
			{
				int count = (Integer)temp.get(words.get(i));
				temp.put(words.get(i), count + 1);
			}
			else
			{
				temp.put(words.get(i), 1);
			}
		}
		
		List<Frequency> result = new ArrayList<Frequency>();
		
		//Interate through hash table values
		Enumeration k = temp.keys();
		Enumeration e = temp.elements();

		
		while(k.hasMoreElements()){
			String strKey = (String)k.nextElement();
			int strElem = (Integer)e.nextElement();

			Frequency f = new Frequency(strKey, strElem);
			result.add(f);
		}
		
		Collections.sort(result, 
	            new Comparator<Frequency>() {
	                @Override
	                public int compare(Frequency e1, Frequency e2) {
	                	
	                	//Compare the frequency value first
	                	int c1 = Integer.compare(e2.getFrequency(), e1.getFrequency());
	                	if(c1 == 0){
	                		//if the frequency value is equal, compare the word value
		                	return e1.getText().compareTo(e2.getText());
	                	}

	                	return c1;
	                }
	            }
	    );
		
		return result;
	}
	
	/**
	 * Runs the word frequency counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) throws IOException {
		File file = new File("ha.txt");
		List<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeWordFrequencies(words);
		Utilities.printFrequencies(frequencies);
		
	}
}
