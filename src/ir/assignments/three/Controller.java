package ir.assignments.three;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;
import ir.assignments.two.b.WordFrequencyCounter;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
	public static void main(String[] args) throws Exception {
//        String crawlStorageFolder = "/data/crawl/root";
//        int numberOfCrawlers = 7;
//
//        CrawlConfig config = new CrawlConfig();
//        
//        config.setCrawlStorageFolder(crawlStorageFolder);
//        
//        config.setUserAgentString("UCI Inf141-CS121 crawler 29198266 60819735 55997869");
//        config.setMaxDepthOfCrawling(1);
//        config.setPolitenessDelay(600);
//
//        /*
//         * Instantiate the controller for this crawl.
//         */
//        PageFetcher pageFetcher = new PageFetcher(config);
//        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
//        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
//        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
//        
//        /*
//         * For each crawl, you need to add some seed urls. These are the first
//         * URLs that are fetched and then the crawler starts following links
//         * which are found in these pages
//         */
//        controller.addSeed("http://ics.uci.edu/");
//        
//        
//        
//        /*
//         * Start the crawl. This is a blocking operation, meaning that your code
//         * will reach the line after this only when crawling is finished.
//         */
//        controller.start(Crawler.class, numberOfCrawlers);
//        
//        //write info to Subdomains.txt
//        //write info to CommonWords.txt
//        System.out.println("\nReached end of crawl!");
//        
//        System.out.println("\nQUESTION 2: URLS");
//        
//        System.out.println("\nNumber of Unique Pages: " + Crawler.crawl("hi").size());
//        
//        System.out.println("\nQUESTION 3: SUBDOMAINS\n");
//        
//        List<Frequency> frequencies = WordFrequencyCounter.computeWordFrequencies(Crawler.getSubdomainList());
//        
//        Utilities.printFrequencies(frequencies);
		
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
		
		System.out.println("\nQUESTION 4: LONGEST PAGE\n");
	}
}
