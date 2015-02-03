// Leon Thai (#55997869)
package ir.assignments.two.a;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A collection of utility methods for text processing.
 */
public class Utilities {
	/**
	 * Reads the input text file and splits it into alphanumeric tokens.
	 * Returns an ArrayList of these tokens, ordered according to their
	 * occurrence in the original text file.
	 * 
	 * Non-alphanumeric characters delineate tokens, and are discarded.
	 *
	 * Words are also normalized to lower case. 
	 * 
	 * Example:
	 * 
	 * Given this input string
	 * "An input string, this is! (or is it?)"
	 * 
	 * The output list of strings should be
	 * ["an", "input", "string", "this", "is", "or", "is", "it"]
	 * 
	 * @param input The file to read in and tokenize.
	 * @return The list of tokens (words) from the input file, ordered by occurrence.
	 * @throws IOException 
	 */
	public static ArrayList<String> tokenizeFile(File input) throws IOException {
		//Reading and storing strings from text file
		FileReader file = new FileReader(input);
		BufferedReader reader = new BufferedReader(file);
		
		String text = "";
		String line = reader.readLine();
		while(line != null)
		{
			text += line;
			line = reader.readLine();
		}
		
		file.close();
		reader.close();
		
		//Keep char only
		List<String> strs = Arrays.asList(text.split("[^a-zA-Z']+"));
		
		ArrayList<String> result = new ArrayList<String>(strs);
				

		return result;
	}
	
	/**
	 * Takes a list of {@link Frequency}s and prints it to standard out. It also
	 * prints out the total number of items, and the total number of unique items.
	 * 
	 * Example one:
	 * 
	 * Given the input list of word frequencies
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total item count: 6
	 * Unique item count: 5
	 * 
	 * sentence	2
	 * the		1
	 * this		1
	 * repeats	1
	 * word		1
	 * 
	 * 
	 * Example two:
	 * 
	 * Given the input list of 2-gram frequencies
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total 2-gram count: 6
	 * Unique 2-gram count: 5
	 * 
	 * you think	2
	 * how you		1
	 * know how		1
	 * think you	1
	 * you know		1
	 * 
	 * @param frequencies A list of frequencies.
	 */
	public static void printFrequencies(List<Frequency> frequencies) {
		
		int total = 0;
		
		//Check if it is 1-gram word
		if(frequencies.get(0).getText().split(" ").length < 2)
		{
			for(int i=0; i<frequencies.size(); i++)
			{
				total += frequencies.get(i).getFrequency();
			}
			
			System.out.println("Total item count: " + total);
			System.out.println("Unique item count: " + frequencies.size() + "\n");
			
			
			//Sort item in a list of Frequency object in decreasing values and alphabetically
			Collections.sort(frequencies, 
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
			
			
			//Printing results
			for(int i=0; i<frequencies.size(); i++)
			{
				String w = frequencies.get(i).getText();
				int f = frequencies.get(i).getFrequency();
				System.out.println(w + " " + f);
			}
		}
		else //Check if 2-gram or more words
		{
			for(int i=0; i<frequencies.size(); i++)
			{
				total += frequencies.get(i).getFrequency();
			}
			
			System.out.println("Total 2-gram count: " + total);
			System.out.println("Unique 2-gram count: " + frequencies.size() + "\n");
			
			//Sort item in a list of Frequency object in decreasing values and alphabetically
			Collections.sort(frequencies, 
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
			
			for(int i=0; i<frequencies.size(); i++)
			{
				System.out.println(frequencies.get(i).getText() + " " + frequencies.get(i).getFrequency());
			}
		}
		
		
	}
	
	
}
