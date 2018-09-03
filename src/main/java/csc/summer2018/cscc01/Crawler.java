package csc.summer2018.cscc01;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler{
	
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|rm|smil|wmv|swf|wma|zip|rar|gz|xml|json|jsonp))$");
	CrawlData data = new CrawlData();
	
	public boolean shouldVisit(Page referringPage, WebURL url) {
		// lowercase the url
		String givenURL = url.getURL().toLowerCase();
		// replace commas with , as per handout
        givenURL = givenURL.replaceAll(",", "_");
        // check if url doesnt match the filter
        boolean ret = !FILTERS.matcher(url.getURL().toLowerCase()).matches();
        // add url to set of unique urls
        data.uniqueURLs.add(givenURL);
        // get content type of the referring page
        String type = referringPage.getContentType();
        // slice off the charset if it is text/html
        if (type != null && type.contains("text/html")) {
        	type = "text/html";
        }
        /*
        // type exists
        if (type != null) {
	        // if we already have the type in our content types hashmap
	        if (data.getContentTypes().containsKey(type)) {
	        	// add one to the value
	        	data.ContentTypes.put(type, data.ContentTypes.get(type) + 1);
	        // if that type isnt in the hashmap
	        } else {
	        	// create a new entry with 1 as the value
	        	data.ContentTypes.put(type, 1);
	        }
        }
        */
        // if the given url starts valid
        if (startsValid(givenURL)) {
        	data.URLSTableData.add(givenURL + ",OK");
        	// add it to the URL table with the OK value
        	//data.URLSTableData.put(givenURL, "OK");
        	// add it to urls within the site
        	data.URLsWithin.add(givenURL);
        	// if url ends with /
        	if (givenURL.endsWith("/")) {
        		URL url2;
        		String urlType = "";
				try {
					// new url
					url2 = new URL(givenURL);
					// open connection to it, get the content type
					urlType = url2.openConnection().getContentType();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// if the content type is not pdf, image, or doc
        		if (urlType != null && !urlType.contains("png") && !urlType.contains("html") && !urlType.contains("jpg") && !urlType.contains("jpeg") &&
                		!urlType.contains("gif") && !urlType.contains("pdf") && !urlType.contains("msword")) {
        			// we dont want to visit
                	return false;
                }
        	}
        	// return if it passed through the filter
        	return ret;
        // didnt start valid
        } else {
        	// add it to urls outside of site
        	data.URLsOutside.add(givenURL);
        	// add it to urls table n_ok value
        	data.URLSTableData.add(givenURL + ",N_OK");
        	// dont visit
        	return false;
        }
	}
	
	/*
	 * Returns true if the given URL starts with a valid prefix
	 * @param url The url of a site page
	 */
	public boolean startsValid(String url) {
		// failsafe if no currentseed
		if (App.currentSeed == null) {
			// starts proper
			return url.startsWith("https://www.newsday.com") || url.startsWith("https://www.chicagotribune.com") || url.startsWith("http://www.chicagotribune.com") ||
					url.startsWith("https://www.foxnews.com") || url.startsWith("https://www.nbcnews.com") || url.startsWith("https://www.c-span.org") || url.startsWith("http://www.newsday.com") ||
					url.startsWith("http://www.foxnews.com") || url.startsWith("http://www.nbcnews.com") || url.startsWith("http://www.c-span.org");
		} else {
			// starts with one of the urls that we accept
			return url.startsWith(App.currentSeed) || url.startsWith(App.currentSeed.replace("https", "http"));
		}
	}
	
	/**
     * Classes that extends WebCrawler can overwrite this function to process
     * the content of the fetched and parsed page.
     *
     * @param page the page object that is just fetched and parsed.
     */
    @Override
	public void visit(Page page) {
    	// get the url
    	String url = page.getWebURL().getURL();
    	// replace , with _ as per handout
        url = url.replaceAll(",", "_");
        // get content type
        String type = page.getContentType();
        // slice of charset if html
        if (type.contains("text/html")) {
        	type = "text/html";
        }
        // if we already have that content type in our hashmap
        if (data.ContentTypes.containsKey(type)) {
        	// increase val by one
        	data.ContentTypes.put(type, data.ContentTypes.get(type) + 1);
        // not in the hashmap
        } else {
        	// create a new key val pair with val of 1
        	data.ContentTypes.put(type, 1);
        }
        // get file size
        int fileSize = page.getContentData().length;
        // < 1KB
        if (fileSize < 1000) {
        	// add 1 to <1k counter
        	data.FileSizes.put("<1KB", data.FileSizes.get("<1KB") + 1);
        // bw 1kb and 10kb
        } else if (fileSize >= 1000 && fileSize < 10000) {
        	// add it to respective counter
        	data.FileSizes.put("1KB~<10KB", data.FileSizes.get("1KB~<10KB") + 1);
        // bw 10 and 100 kb
        } else if (fileSize >= 10000 && fileSize < 100000) {
        	// add it to respective counter
        	data.FileSizes.put("10KB~<100KB", data.FileSizes.get("10KB~<100KB") + 1);
        // bw 100kb and 1mb
        } else if (fileSize >= 100000 && fileSize < 1000000) {
        	// add it to respective counter
        	data.FileSizes.put("100KB~<1MB", data.FileSizes.get("100KB~<1MB") + 1);
        // over 1mb
        } else if (fileSize >= 1000000) {
        	// add it to respective counter
        	data.FileSizes.put(">=1MB", data.FileSizes.get(">=1MB") + 1);
        }
        // number of outlinks is 0
        int numOutLinks = 0;
        // html parsed data
        if (page.getParseData() instanceof HtmlParseData) {
        	// get the parsed data
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            // set number of outgoing urls
            numOutLinks = htmlParseData.getOutgoingUrls().size();
        }
        // store all the data from the visit in array
        String[] visitData = {String.valueOf(fileSize), String.valueOf(numOutLinks), type};
        // add array to visit table data
        data.VisitTableData.put(url,visitData);
	}
	
    /**
     * This function is called once the header of a page is fetched. It can be
     * overwritten by sub-classes to perform custom logic for different status
     * codes. For example, 404 pages can be logged, etc.
     *
     * @param webUrl
     * @param statusCode
     * @param statusDescription
     */
	public void handlePageStatusCode(WebURL webUrl,int statusCode,String statusDescription) {
		// increase number of fetches attempted
    	data.fetchesAttempted++;
    	// get url
    	String url = webUrl.getURL();
    	// replace , with _ as per handout
    	url = url.replaceAll(",", "_");
    	// put the fetch data in the fetch table data
    	data.FetchTableData.put(url, statusCode);
    	// code bw 200 and 300
    	if (statusCode >= 200 && statusCode < 300) {
    		// increased succeeded counter
    		data.fetchesSucceeded++;
    	// code between 400 and 600
    	} else if (statusCode >= 400 && statusCode < 600) {
    		// increase failed counter
    		data.fetchesFailed++;
    	// between 300 and 400
    	} else if (statusCode >=300 && statusCode < 400) {
    		// increase aborted counter
    		data.fetchesAborted++;
    	}
    	// if there is already an entry for that status code
    	if (data.StatusCodesCounter.containsKey(statusCode)) {
    		// increase counter by 1
    		data.StatusCodesCounter.put(statusCode, data.StatusCodesCounter.get(statusCode) + 1);
    	// no entry yet
    	} else {
    		// new entry
    		data.StatusCodesCounter.put(statusCode, 1);
    	}
	}
	
	/*
	 * Returns crawldata for this instance of crawler, used for testing.
	 */
	public CrawlData getData() {
		return data;
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.uci.ics.crawler4j.crawler.WebCrawler#getMyLocalData()
	 * Returns the local data for this instance of a crawler.
	 * This will return the crawler data.
	 */
	public Object getMyLocalData() {
    	return data;
    }
}
