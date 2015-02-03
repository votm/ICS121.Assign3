package ir.assignments.three;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class Crawler extends WebCrawler {
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	private ArrayList<String> traps = new ArrayList<String>();
	private ArrayList<String> subdomains = new ArrayList<String>();
	
	/**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            return !FILTERS.matcher(href).matches() && href.contains("ics.uci.edu/") && !isTrap(href);
    }

    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {          
            String url = page.getWebURL().getURL();
            System.out.println("URL: " + url);
            
            boolean alreadyVisited = false;
            for (String subdomain : subdomains) {
            	if (url.startsWith(subdomain)) {
            		alreadyVisited = true;
            	}
            }
            if (!alreadyVisited) {
            	System.out.println(alreadyVisited);
            	System.out.println(getSubdomain(url));
            	subdomains.add(getSubdomain(url));
            }

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
	
	/**
	 * This method is for testing purposes only. It does not need to be used
	 * to answer any of the questions in the assignment. However, it must
	 * function as specified so that your crawler can be verified programatically.
	 * 
	 * This methods performs a crawl starting at the specified seed URL. Returns a
	 * collection containing all URLs visited during the crawl.
	 */
	public static Collection<String> crawl(String seedURL) {
		// TODO implement me
		return null;
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
		return url.split(".ics.uci.edu")[0];
	}
}
