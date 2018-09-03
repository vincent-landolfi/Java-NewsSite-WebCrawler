package csc.summer2018.cscc01;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import junit.framework.TestCase;

public class CrawlerTest extends TestCase{
	public void testShouldVisitGoodURLChicago() {
    	Crawler crawler = new Crawler();
    	Page mockPage = mock(Page.class);
    	App.setCurrentSeed("https://www.chicagotribune.com");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	WebURL mockUrl = mock(WebURL.class);
    	when(mockUrl.getURL()).thenReturn("https://www.chicagotribune.com/news.html");
    	assertTrue(crawler.shouldVisit(mockPage, mockUrl));
    	App.currentSeed = null;
    }
    
    public void testShouldVisitGoodURLNewsdayPNG() {
    	Crawler crawler = new Crawler();
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	App.setCurrentSeed("https://www.newsday.com");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.newsday.com/test.png");
    	assertTrue(crawler.shouldVisit(mockPage, mockUrl));
    	App.currentSeed = null;
    }
    
    public void testShouldVisitGoodURLCspanPDF() {
    	Crawler crawler = new Crawler();
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	App.setCurrentSeed("https://www.c-span.org/");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.c-span.org/testing.pdf");
    	assertTrue(crawler.shouldVisit(mockPage, mockUrl));
    	App.currentSeed = null;
    }
    
    public void testShouldVisitGoodURLFoxJPG() {
    	Crawler crawler = new Crawler();
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	App.setCurrentSeed("https://www.foxnews.com/");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.foxnews.com/testing.jpg");
    	assertTrue(crawler.shouldVisit(mockPage, mockUrl));
    	App.currentSeed = null;
    }
    
    public void testShouldVisitGoodURLNBCJPEG() {
    	Crawler crawler = new Crawler();
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	App.setCurrentSeed("https://www.nbcnews.com/");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.nbcnews.com/testing.jpeg");
    	assertTrue(crawler.shouldVisit(mockPage, mockUrl));
    	App.currentSeed = null;
    }
    
    public void testShouldVisitGoodURLNBCDOC() {
    	Crawler crawler = new Crawler();
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	App.setCurrentSeed("https://www.nbcnews.com/");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.nbcnews.com/testing.doc");
    	assertTrue(crawler.shouldVisit(mockPage, mockUrl));
    	App.currentSeed = null;
    }
    
    public void testShouldVisitBadURL() {
    	Crawler crawler = new Crawler();
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	App.setCurrentSeed("https://www.randomsite.com");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.randomsite.com/media.mp4");
    	assertTrue(!crawler.shouldVisit(mockPage, mockUrl));
    	App.currentSeed = null;
    }
    
    public void testShouldVisitGoodURLBadExtension() {
    	Crawler crawler = new Crawler();
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	App.setCurrentSeed("https://www.newsday.com/");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.newsday.com/media.mp4");
    	assertTrue(!crawler.shouldVisit(mockPage, mockUrl));
    	App.currentSeed = null;
    }
    
    public void testShouldVisitBadURLGoodExtension() {
    	Crawler crawler = new Crawler();
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	App.setCurrentSeed("https://www.chicagotribune.com/");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.randomsite.com/media.png");
    	assertTrue(!crawler.shouldVisit(mockPage, mockUrl));
    	App.currentSeed = null;
    }
    
    public void testShouldVisitSlashEndingGoodContentType() {
    	Crawler crawler = new Crawler();
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	App.setCurrentSeed("https://www.nbcnews.com/");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.nbcnews.com/nightly-news/");
    	assertTrue(crawler.shouldVisit(mockPage, mockUrl));
    	App.currentSeed = null;
    }
    /*
    public void testShouldVisitSlashEndingBadContentType() {
    	Crawler crawler = new Crawler();
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.nbcnews.com/nightly-news/");
    	assertTrue(crawler.shouldVisit(mockPage, mockUrl));
    }
    */
    public void testShouldVisitStoresURLDataWithin() {
    	Crawler crawler = new Crawler();
    	
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	App.setCurrentSeed("https://www.nbcnews.com/");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.nbcnews.com/");
    	crawler.shouldVisit(mockPage, mockUrl);
    	boolean uniqueAdded = crawler.getData().getUniqueURLs().contains("https://www.nbcnews.com/");
    	boolean tableDataAdded = false;
    	if (uniqueAdded) {
    		tableDataAdded = crawler.getData().getURLSTableData().contains("https://www.nbcnews.com/,OK");
    	}
    	boolean withinAdded = crawler.getData().getURLsWithin().contains("https://www.nbcnews.com/");
    	assertTrue(uniqueAdded && tableDataAdded && withinAdded);
    	App.currentSeed = null;
    }
    
    public void testShouldVisitStoresURLDataOutside() {
    	Crawler crawler = new Crawler();
    	
    	Page mockPage = mock(Page.class);
    	WebURL mockUrl = mock(WebURL.class);
    	App.setCurrentSeed("https://www.nbcnews.com/");
    	when(mockPage.getContentType()).thenReturn("text/html");
    	when(mockUrl.getURL()).thenReturn("https://www.randomsite.com/");
    	crawler.shouldVisit(mockPage, mockUrl);
    	boolean uniqueAdded = crawler.getData().getUniqueURLs().contains("https://www.randomsite.com/");
    	boolean tableDataAdded = false;
    	if (uniqueAdded) {
    		tableDataAdded = crawler.getData().getURLSTableData().contains("https://www.randomsite.com/,N_OK");
    	}
    	boolean outsideAdded = crawler.getData().getURLsOutside().contains("https://www.randomsite.com/");
    	crawler.shouldVisit(mockPage, mockUrl);
    	assertTrue(uniqueAdded && tableDataAdded && outsideAdded);
    	App.currentSeed = null;
    }
    
    public void testValidChicago() {
    	Crawler crawler = new Crawler();
    	App.setCurrentSeed("https://www.chicagotribune.com/");
    	assertTrue(crawler.startsValid("https://www.chicagotribune.com/test/test.png"));
    	App.currentSeed = null;
    }
    
    public void testValidNewsday() {
    	Crawler crawler = new Crawler();
    	App.setCurrentSeed("https://www.newsday.com/");
    	assertTrue(crawler.startsValid("https://www.newsday.com/test/test.jpg"));
    	App.currentSeed = null;
    }
    
    public void testValidNBC() {
    	Crawler crawler = new Crawler();
    	App.setCurrentSeed("https://www.nbcnews.com/");
    	assertTrue(crawler.startsValid("https://www.nbcnews.com/test/test.pdf"));
    	App.currentSeed = null;
    }
    
    public void testValidFOX() {
    	Crawler crawler = new Crawler();
    	App.setCurrentSeed("https://www.foxnews.com/");
    	assertTrue(crawler.startsValid("https://www.foxnews.com/hhh/gggg/ddd.doc"));
    	App.currentSeed = null;
    }
    
    public void testValidCSPAN() {
    	Crawler crawler = new Crawler();
    	App.setCurrentSeed("https://www.c-span.org/");
    	assertTrue(crawler.startsValid("https://www.c-span.org/test/ttttt.png"));
    	App.currentSeed = null;
    }
    
    public void testInvalid() {
    	Crawler crawler = new Crawler();
    	App.currentSeed = "https://www.foxnews.com";
    	assertTrue(!crawler.startsValid("https://www.randomsite.com/"));
    	App.currentSeed = null;
    }
    
    public void testVisitPageHtmlParseData() {
    	Crawler crawler = new Crawler();
    	Page page = mock(Page.class);
    	when(page.getContentType()).thenReturn("text/html");
    	when(page.getContentData()).thenReturn(new byte[1000001]);
    	WebURL url = mock(WebURL.class);
    	when(url.getURL()).thenReturn("https://www.newsday.com");
    	when(page.getWebURL()).thenReturn(url);
    	HtmlParseData parseData = mock(HtmlParseData.class);
    	Set<WebURL> outgoing = mock(Set.class);
    	when(outgoing.size()).thenReturn(5);
    	when(parseData.getOutgoingUrls()).thenReturn(outgoing);
    	when(page.getParseData()).thenReturn(parseData);
    	String[] equalData = {"1000001","5","text/html"};
    	crawler.visit(page);
    	boolean contentTypeAdded = false;
    	boolean fileSizeAdded = false;
    	boolean visitDataAdded = false;
    	if (crawler.getData().getContentTypes().containsKey("text/html")) {
    		contentTypeAdded = crawler.getData().getContentTypes().get("text/html") == 1;
    	}
    	if (crawler.getData().getFileSizes().containsKey(">=1MB")) {
    		fileSizeAdded = (crawler.getData().getFileSizes().get(">=1MB") == 1);
    	}
    	if (crawler.getData().getVisitTableData().containsKey("https://www.newsday.com")) {
    		String[] data = crawler.getData().getVisitTableData().get("https://www.newsday.com");
    		visitDataAdded = data[0].equals(equalData[0]);
    		visitDataAdded = visitDataAdded && data[1].equals(equalData[1]);
    		visitDataAdded = visitDataAdded && data[2].equals(equalData[2]);
    	}

    	assertTrue(contentTypeAdded && fileSizeAdded && visitDataAdded);
    }
    
    public void testVisitPageFull() {
    	Crawler crawler = new Crawler();
    	Page page = mock(Page.class);
    	when(page.getContentType()).thenReturn("text/html");
    	when(page.getContentData()).thenReturn(new byte[1000001]);
    	WebURL url = mock(WebURL.class);
    	when(url.getURL()).thenReturn("https://www.newsday.com");
    	when(page.getWebURL()).thenReturn(url);
    	Page page2 = mock(Page.class);
    	when(page2.getContentType()).thenReturn("application/pdf");
    	when(page2.getContentData()).thenReturn(new byte[111]);
    	WebURL url2 = mock(WebURL.class);
    	when(url2.getURL()).thenReturn("https://www.foxnews.com");
    	when(page2.getWebURL()).thenReturn(url2);
    	Page page3 = mock(Page.class);
    	when(page3.getContentType()).thenReturn("application/pdf");
    	when(page3.getContentData()).thenReturn(new byte[1111]);
    	WebURL url3 = mock(WebURL.class);
    	when(url3.getURL()).thenReturn("https://www.nbcnews.com");
    	when(page3.getWebURL()).thenReturn(url3);
    	HtmlParseData parseData = mock(HtmlParseData.class);
    	Set<WebURL> outgoing = mock(Set.class);
    	when(outgoing.size()).thenReturn(5);
    	when(parseData.getOutgoingUrls()).thenReturn(outgoing);
    	when(page.getParseData()).thenReturn(parseData);
    	when(page2.getParseData()).thenReturn(null);
    	String[] equalData = {"1000001","5","text/html"};
    	String[] equalData2 = {"111","0","application/pdf"};
    	crawler.visit(page);
    	crawler.visit(page2);
    	crawler.visit(page3);
    	boolean contentTypeAdded = false;
    	boolean contentTypeAdded2 = false;
    	boolean fileSizeAdded = false;
    	boolean fileSizeAdded2 = false;
    	boolean fileSizeAdded3 = false;
    	boolean visitDataAdded = false;
    	boolean visitDataAdded2 = false;
    	if (crawler.getData().getContentTypes().containsKey("text/html")) {
    		contentTypeAdded = crawler.getData().getContentTypes().get("text/html") == 1;
    	}
    	if (crawler.getData().getContentTypes().containsKey("application/pdf")) {
    		contentTypeAdded2 = crawler.getData().getContentTypes().get("application/pdf") == 2;
    	}
    	if (crawler.getData().getFileSizes().containsKey(">=1MB")) {
    		fileSizeAdded = (crawler.getData().getFileSizes().get(">=1MB") == 1);
    	}
    	if (crawler.getData().getFileSizes().containsKey("<1KB")) {
    		fileSizeAdded2 = (crawler.getData().getFileSizes().get("<1KB") == 1);
    	}
    	if (crawler.getData().getFileSizes().containsKey("1KB~<10KB")) {
    		fileSizeAdded3 = (crawler.getData().getFileSizes().get("1KB~<10KB") == 1);
    	}
    	if (crawler.getData().getVisitTableData().containsKey("https://www.newsday.com")) {
    		String[] data = crawler.getData().getVisitTableData().get("https://www.newsday.com");
    		visitDataAdded = data[0].equals(equalData[0]);
    		visitDataAdded = visitDataAdded && data[1].equals(equalData[1]);
    		visitDataAdded = visitDataAdded && data[2].equals(equalData[2]);
    	}
    	if (crawler.getData().getVisitTableData().containsKey("https://www.foxnews.com")) {
    		String[] data = crawler.getData().getVisitTableData().get("https://www.foxnews.com");
    		visitDataAdded2 = data[0].equals(equalData2[0]);
    		visitDataAdded2 = visitDataAdded2 && data[1].equals(equalData2[1]);
    		visitDataAdded2 = visitDataAdded2 && data[2].equals(equalData2[2]);
    	}
    	assertTrue(contentTypeAdded && contentTypeAdded2 && fileSizeAdded && fileSizeAdded2 && fileSizeAdded3 
    			&& visitDataAdded && visitDataAdded2);
    }
    
    public void testVisitPageNotHtmlParseData() {
    	Crawler crawler = new Crawler();
    	Page page = mock(Page.class);
    	when(page.getContentType()).thenReturn("text/html");
    	when(page.getContentData()).thenReturn(new byte[1000001]);
    	WebURL url = mock(WebURL.class);
    	when(url.getURL()).thenReturn("https://www.newsday.com");
    	when(page.getWebURL()).thenReturn(url);
    	HtmlParseData parseData = null;
    	when(page.getParseData()).thenReturn(parseData);
    	String[] equalData = {"1000001","0","text/html"};
    	crawler.visit(page);
    	boolean contentTypeAdded = false;
    	boolean fileSizeAdded = false;
    	boolean visitDataAdded = false;
    	if (crawler.getData().getContentTypes().containsKey("text/html")) {
    		contentTypeAdded = crawler.getData().getContentTypes().get("text/html") == 1;
    	}
    	if (crawler.getData().getFileSizes().containsKey(">=1MB")) {
    		fileSizeAdded = crawler.getData().getFileSizes().get(">=1MB") == 1;
    	}
    	if (crawler.getData().getVisitTableData().containsKey("https://www.newsday.com")) {
    		String[] data = crawler.getData().getVisitTableData().get("https://www.newsday.com");
    		visitDataAdded = data[0].equals(equalData[0]);
    		visitDataAdded = visitDataAdded && data[1].equals(equalData[1]);
    		visitDataAdded = visitDataAdded && data[2].equals(equalData[2]);
    	}
    	assertTrue(contentTypeAdded && fileSizeAdded && visitDataAdded);
    }
    
    public void testHandlePageStatusCode() {
    	Crawler crawler = new Crawler();
    	WebURL url = mock(WebURL.class);
    	when(url.getURL()).thenReturn("https://www.newsday.com/");
    	crawler.handlePageStatusCode(url, 200, "good");
    	crawler.handlePageStatusCode(url, 303, "abort");
    	crawler.handlePageStatusCode(url, 404, "failed");
    	crawler.handlePageStatusCode(url, 403, "failed");
    	crawler.handlePageStatusCode(url, 402, "failed");
    	crawler.handlePageStatusCode(url, 200, "good");
    	boolean succeedCounter = false;
    	boolean fourOtwoCounter = false;
    	boolean fourOThreeCounter = false;
    	boolean fourOFourCounter = false;
    	boolean threeOThreeCounter = false;
    	boolean succeedCount = crawler.getData().getFetchesSucceeded() == 2;
    	boolean abortCount = crawler.getData().getFetchesAborted() == 1;
    	boolean failCount = crawler.getData().getFetchesFailed() == 3;
    	if (crawler.getData().getStatusCodesCounter().containsKey(200)) {
    		succeedCounter = crawler.getData().getStatusCodesCounter().get(200) == 2;
    	}
    	if (crawler.getData().getStatusCodesCounter().containsKey(402)) {
    		fourOtwoCounter = crawler.getData().getStatusCodesCounter().get(402) == 1;
    	}
    	if (crawler.getData().getStatusCodesCounter().containsKey(403)) {
    		fourOThreeCounter = crawler.getData().getStatusCodesCounter().get(403) == 1;
    	}
    	if (crawler.getData().getStatusCodesCounter().containsKey(404)) {
    		fourOFourCounter = crawler.getData().getStatusCodesCounter().get(404) == 1;
    	}
    	if (crawler.getData().getStatusCodesCounter().containsKey(303)) {
    		threeOThreeCounter = crawler.getData().getStatusCodesCounter().get(303) == 1;
    	}
    	assertTrue(succeedCount && abortCount && failCount && succeedCounter && 
    			fourOtwoCounter && fourOThreeCounter && threeOThreeCounter && fourOFourCounter);
    }
}
