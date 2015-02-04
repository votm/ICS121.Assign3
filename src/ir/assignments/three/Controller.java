package ir.assignments.three;

import java.util.Collection;
import java.util.List;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;
import ir.assignments.two.b.WordFrequencyCounter;
public class Controller {
	public static void main(String[] args) throws Exception {
		// Create a timestamp for start of crawl
		long timestamp = System.currentTimeMillis();
		
		// Start the crawl and save the returned collection of URLs
		Collection<String> urls = Crawler.crawl("http://www.ics.uci.edu/");
		
		System.out.println("\nReached end of crawl!");
		
		System.out.println("\nQUESTION 1: TIME TO CRAWL");
		
		System.out.println("\nTime to crawl the entire domain: " + (System.currentTimeMillis() - timestamp)/1000 + " seconds");
		
		System.out.println("\nQUESTION 2: URL COUNT");
		  
		System.out.println("\nNumber of unique pages found in domain: " + urls.size());
		  
		System.out.println("\nQUESTION 3: SUBDOMAINS\n");
		  
		List<Frequency> frequencies = WordFrequencyCounter.computeWordFrequencies(Crawler.getSubdomainList());
		
		Utilities.printFrequencies(frequencies);
		
		System.out.println("\nQUESTION 4: LONGEST PAGE");
		
		Crawler.printLongestPageInfo();
		
		System.out.println("\nQUESTION 5: MOST COMMON WORDS\n");
		
		Crawler.printMostCommonWords();
	}
}
