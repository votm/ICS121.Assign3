//Rex Rockwell #29198266
//Leon Thai #55997869
//Tuan Vo #60819735

package ir.assignments.three;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.b.WordFrequencyCounter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler extends WebCrawler {
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	// List of known trap subdomains
	private String[] traps = {"http://archive.ics.uci.edu", "http://calendar.ics.uci.edu", "http://wics.ics.uci.edu",
			"http://drzaius.ics.uci.edu"};
	
	// The list of all urls visited
	public static ArrayList<String> urls = new ArrayList<String>();
	
	// The list of all subdomains visited
	// (duplicates allowed, as they will be passed into a frequency list-generating function later)
	public static ArrayList<String> subdomains = new ArrayList<String>();
	
	// Information on the longest page visited so far
	public static int longestPageWordCount = 0;
	public static String longestPageUrl = "";
	
	// A list of all words from all pages
	// (duplicates allowed, as they will be passed into a frequency list-generating function later)
	public static ArrayList<String> allWords = new ArrayList<String>();
	
	/**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            return !FILTERS.matcher(href).matches() && href.contains(".ics.uci.edu") && !isTrap(href);
    }

    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
    		System.out.println("\nGot new page");
            String url = page.getWebURL().getURL();
            
            // Add the url to our list of urls visited
            urls.add(url);
            System.out.println("URL: " + url);
            
            // Add the subdomain to our list of subdomains
            subdomains.add(getSubdomain(url));
        	
        	// Information about the page
            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    String text = htmlParseData.getText();
                    String html = htmlParseData.getHtml();
                    List<WebURL> links = htmlParseData.getOutgoingUrls();
                    
                    // Turn text into a list of words
                    List<String> words = tokenizeString(text,false); 
                    
                    // Add words to our list of all words
                    allWords.addAll(words);
                    
                    // Get the number of words on this page
                    int wordCount = words.size(); 
                    
                    // If this page has more words than our current longest page, update our longest page info
                    if (wordCount > longestPageWordCount) {
                    	longestPageWordCount = wordCount;
                    	longestPageUrl = url;
                    }
                    
                    System.out.println("Text length: " + text.length());
                    System.out.println("Html length: " + html.length());
                    System.out.println("Number of outgoing links: " + links.size());
            }
    }
    
    /*
	 * This method is a helper method that we used
	 * in our visit method to check if a link leads to a trap
	 */
    public boolean isTrap(String href) {
		for (String trap : traps) {
			if (href.startsWith(trap)) {
				return true;
			}
		}
		return false;
	}
    
    /*
	 * This method is a helper method that we used
	 * in the visit method to parse out the subdomain
	 */
    public String getSubdomain(String url) {
		return url.split(".ics.uci.edu")[0].split("http://")[1];
	}
	
    /*
	 * This method is a helper methods used in 
	 * the visit methods to add subdomain to a list of subdomains
	 */
	public static ArrayList<String> getSubdomainList () {
		return subdomains;
	}
	
	/*
	 * This method takes a string, set it against our filters
	 * and then add them to our tokenizedWords list
	 */
	public static ArrayList<String> tokenizeString (String str, boolean excludeStopWords) {
		// The array to return
		ArrayList<String> tokenizedWords = new ArrayList<String>();
		
		// Use the Pattern class to denote we want to search for words
		Pattern p = Pattern.compile("[\\w]+");
		
		// Search str for words using our Pattern p
		Matcher m = p.matcher(str);
		
		// Iterate through m to find individual words
		while ( m.find() ) {
			// Add the current word to our tokenizedWords
			String word = str.substring(m.start(), m.end()).toLowerCase();
			
			if (!excludeStopWords || !isStopWord(word)) {
				tokenizedWords.add( word );
			}
		}
		
		return tokenizedWords;
	}
	
	/*
	 * This method is a helper method that check
	 * if a word is a stopword
	 */
	public static boolean isStopWord (String word) {
		if (Arrays.asList(stopwords).contains(word)) {
			return true;
		}
		return false;
	}
	
	/*
	 * This method compare an array of words we collected with the list of stopwords
	 * if a word in allWords is in stopwords, it is removed
	 */
	public static ArrayList<String> removeStopWords(ArrayList<String> allWords) {
		for (int i = 0; i < allWords.size(); i++) {
			if (isStopWord(allWords.get(i))) {
				allWords.remove(i);
				while (isStopWord(allWords.get(i))) {
					allWords.remove(i);
				}
			}
		}
		return allWords;
	}
	
	/*
	 * This method addresses the most common word that we encountered during our crawl
	 * This satisfy question 5 of our assignment
	 */
	public static void printMostCommonWords () {
		List<Frequency> wordFrequencies = WordFrequencyCounter.computeWordFrequencies(removeStopWords(allWords));
		for (int i = 0; i < 500; i++) {
			System.out.println(wordFrequencies.get(i).toString());
		}
	}
	
	/*
	 * This method addresses the longest page through word count
	 * This satisfy question 4 of our assignment
	 */
	public static void printLongestPageInfo () {
		System.out.println("\nLongest Page Word Count: " + longestPageWordCount + "\nLongest Page URL: " + longestPageUrl);
	}
	
	/**
	 * This method is for testing purposes only. It does not need to be used
	 * to answer any of the questions in the assignment. However, it must
	 * function as specified so that your crawler can be verified programatically.
	 * 
	 * This methods performs a crawl starting at the specified seed URL. Returns a
	 * collection containing all URLs visited during the crawl.
	 * @throws Exception 
	 */
	public static Collection<String> crawl(String seedURL) throws Exception {
		String crawlStorageFolder = "/data/crawl/root";
		int numberOfCrawlers = 7;
		
		CrawlConfig config = new CrawlConfig();
		
		config.setCrawlStorageFolder(crawlStorageFolder);
		
		config.setUserAgentString("UCI Inf141-CS121 crawler 29198266 60819735 55997869");
		config.setPolitenessDelay(600);
		
		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		
		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		controller.addSeed(seedURL);
		
		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(Crawler.class, numberOfCrawlers);
		
		return urls;
	}
		/*
		 * This methods contains stopwords that we want our crawler to ignore
		 */
	private static String[] stopwords = {"a","about","above","after","again","against","all","am","an","and","any","are",
			"aren't","as","at","be","because","been","before","being","below","between","both","but","by","can't",
			"cannot","could","couldn't","did","didn't","do","does","doesn't","doing","don't","down","during","each",
			"few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll",
			"he's","her","here","here's","hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm",
			"i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my",
			"myself","no","nor","not","of","off","on","once","only","or","other","ought","our","ours","ourselves","out",
			"over","own","same","shan't","she","she'd","she'll","she's","should","shouldn't","so","some","such","than",
			"that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd",
			"they'll","they're","they've","this","those","through","to","too","under","until","up","very","was",
			"wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where",
			"where's","which","while","who","who's","whom","why","why's","with","won't","would","wouldn't","you",
			"you'd","you'll","you're","you've","your","yours","yourself","yourselves"};
}
