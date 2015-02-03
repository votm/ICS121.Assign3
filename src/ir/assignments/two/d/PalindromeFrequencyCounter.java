// Leon Thai (#55997869)

package ir.assignments.two.d;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class PalindromeFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private PalindromeFrequencyCounter() {}
	
	private static boolean checkPalindrome(String str){
		return str.equals(new StringBuilder(str).reverse().toString());
	}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique palindrome found in the original list. The frequency of each palindrome
	 * is equal to the number of times that palindrome occurs in the original list.
	 * 
	 * Palindromes can span sequential words in the input list.
	 * 
	 * The returned list is ordered by decreasing size, with tied palindromes sorted
	 * by frequency and further tied palindromes sorted alphabetically. 
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["do", "geese", "see", "god", "abba", "bat", "tab"]
	 * 
	 * The output list of palindromes should be 
	 * ["do geese see god:1", "bat tab:1", "abba:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of palindrome frequencies, ordered by decreasing frequency.
	 */
	private static List<Frequency> computePalindromeFrequencies(ArrayList<String> words) {
		
		List<Frequency> result = new ArrayList<Frequency>();
		
		Hashtable temp = new Hashtable();
		
		for(int i = 0; i < words.size(); i++)
		{
			String curr = words.get(i);
			
			String curr_with_space = curr + " ";
			
			//Check the first item of iteration
			if(checkPalindrome(curr)==true && curr.length()>1)
			{
				if(temp.containsKey(curr) == true)
				{
					int count = (Integer)temp.get(curr);
					temp.put(curr, count + 1);
				}
				else
				{
					temp.put(curr, 1);
				}
			}
			
			
			
			
			int j = i+1;
			
			while(j < words.size() )
			{
				//Keep the words seperate with space
				curr_with_space = curr_with_space + words.get(j) + " ";
				//Connect the words without space to check palindrome
				curr = curr + words.get(j);

				
				
				if(checkPalindrome(curr)==true)
				{
					if(temp.containsKey(curr_with_space) == true)
					{
						int count = (Integer)temp.get(curr_with_space);
						temp.put(curr_with_space, count + 1);
					}
					else
					{
						temp.put(curr_with_space, 1);
					}
				}
				
				
				j++;
				
			}
			
			
		}
		
		Enumeration k = temp.keys();
		Enumeration e = temp.elements();

		
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
		File file = new File("pa.txt");
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computePalindromeFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
