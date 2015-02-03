// Leon Thai (#55997869)

package ir.assignments.two.c;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * Count the total number of 2-grams and their frequencies in a text file.
 */
public final class TwoGramFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private TwoGramFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique 2-gram in the original list. The frequency of each 2-grams
	 * is equal to the number of times that two-gram occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied 2-grams sorted
	 * alphabetically. 
	 * 
	 * 
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["you", "think", "you", "know", "how", "you", "think"]
	 * 
	 * The output list of 2-gram frequencies should be 
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of two gram frequencies, ordered by decreasing frequency.
	 */
	private static List<Frequency> computeTwoGramFrequencies(ArrayList<String> words) {
		
		List<Frequency> result = new ArrayList<Frequency>();
		
		List<String> temp = new ArrayList<String>();
		
		for(int i = 0; i < words.size()-1; i++)
		{
			String item = words.get(i) + " " + words.get(i+1);
			temp.add(item);
		}
		
		Hashtable ht = new Hashtable();
		
		for(int i = 0; i < temp.size(); i++)
		{
			if(ht.containsKey(temp.get(i)) == true)
			{
				int count = (Integer)ht.get(temp.get(i));
				ht.put(temp.get(i), count + 1);
			}
			else
			{
				ht.put(temp.get(i), 1);
			}
		}
		
		Enumeration k = ht.keys();
		Enumeration e = ht.elements();

		
		while(k.hasMoreElements()){
			String strKey = (String)k.nextElement();
			int strElem = (Integer)e.nextElement();

			Frequency f = new Frequency(strKey, strElem);
			result.add(f);
		}
		
		return result;
	}
	
	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) throws IOException{
		File file = new File("ty.txt");
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeTwoGramFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
