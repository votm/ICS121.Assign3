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
import ir.assignments.two.a.Utilities;
import ir.assignments.two.b.WordFrequencyCounter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class Crawler extends WebCrawler {
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	private String[] traps = {"http://archives.ics.uci.edu", "string2"};
	
	public static ArrayList<String> urls = new ArrayList<String>();
	public static ArrayList<String> subdomains = new ArrayList<String>();
	
	/**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            return !FILTERS.matcher(href).matches() && href.contains("www.ics.uci.edu/") && !isTrap(href);
    }

    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
    		System.out.println("\nGot new page");
            String url = page.getWebURL().getURL();
            urls.add(url);
            System.out.println("URL: " + url);
            
            // Have we already visited this subdomain?
            boolean subdomainVisited = false;
            // Iterate through the list of subdomains
            for (String subdomain : subdomains) {
            	// If our url's subdomain is in the list,
            	if (url.split("http://")[1].startsWith(subdomain)) {
            		// We've already visited url's subdomain
            		subdomainVisited = true;
            	}
            }
            // If we haven't visited url's subdomain
            if (!subdomainVisited) {
            	// Add url's subdomain to our list
            	subdomains.add(getSubdomain(url));
            }
            
            // Debugging
            System.out.print("Subdomains visited so far: ");
            for(String subdomain : subdomains) {
        		System.out.print(subdomain + " ");
        	}
        	System.out.println("");
        	
        	// Information about the page
            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    String text = htmlParseData.getText();
                    String html = htmlParseData.getHtml();
                    List<WebURL> links = htmlParseData.getOutgoingUrls();

                    System.out.println("Text length: " + text.length());
                    System.out.println("Html length: " + html.length());
                    System.out.println("Number of outgoing links: " + links.size());
            }
    }
	
    public boolean isTrap(String href) {
		for (String trap : traps) {
			if (href.startsWith(trap)) {
				return true;
			}
		}
		return false;
	}
    
    public String getSubdomain(String url) {
		return url.split(".ics.uci.edu")[0].split("http://")[1];
	}
	
	public static ArrayList<String> getSubdomainList () {
		return subdomains;
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
		config.setMaxDepthOfCrawling(1);
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
}
