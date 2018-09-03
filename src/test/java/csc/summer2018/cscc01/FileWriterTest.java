package csc.summer2018.cscc01;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import junit.framework.TestCase;

public class FileWriterTest extends TestCase{
	
	public void testWriteFetch() throws IOException {
    	CrawlData data = mock(CrawlData.class);
    	HashMap<String,Integer> mockFetch = new HashMap<String,Integer>();
    	mockFetch.put("http://www.newsday.com/", 200);
    	mockFetch.put("http://www.newsday.com/testing.png", 404);
    	mockFetch.put("http://www.newsday.com/coolpdfs/test.pdf", 500);
    	mockFetch.put("http://www.newsday.com/images/ttt.jpg", 303);
    	mockFetch.put("http://www.newsday.com/test/testing/tester/test.doc", 201);
    	when(data.getFetchTableData()).thenReturn(mockFetch);
    	when(data.getFileName()).thenReturn("Test");
    	FileWriter writer = new FileWriter();
    	writer.writeCSV(data, "fetch",true);
    	File file = new File("fetchTest.csv");
    	Scanner scanner = new Scanner(file);
    	String csvText = "";
    	while (scanner.hasNextLine()) {
    		csvText += scanner.nextLine();
    	}
    	boolean entry1 = csvText.contains("URL,Status Code");
    	boolean entry2 = csvText.contains("http://www.newsday.com/testing.png,404");
    	boolean entry3 = csvText.contains("http://www.newsday.com/coolpdfs/test.pdf,500");
    	boolean entry4 = csvText.contains("http://www.newsday.com/images/ttt.jpg,303");
    	boolean entry5 = csvText.contains("http://www.newsday.com/,200");
    	boolean entry6 = csvText.contains("http://www.newsday.com/test/testing/tester/test.doc,201");
    	file.delete();
    	assertTrue(entry1 && entry2 && entry3 && entry4 && entry5 && entry6);
    }
	
	public void testWriteVisit() throws IOException {
		CrawlData data = mock(CrawlData.class);
    	HashMap<String,String[]> mockVisit = new HashMap<String,String[]>();
    	mockVisit.put("http://www.newsday.com/", new String[] {"111","2","text/html"});
    	mockVisit.put("http://www.newsday.com/testing.png", new String[] {"54","55","image/png"});
    	mockVisit.put("http://www.newsday.com/coolpdfs/test.pdf", new String[] {"9999","42","application/pdf"});
    	mockVisit.put("http://www.newsday.com/images/ttt.jpg", new String[] {"94454999","13","image/jpg"});
    	mockVisit.put("http://www.newsday.com/test/testing/tester/test.doc", new String[] {"949","2","applcation/msword"});
    	when(data.getVisitTableData()).thenReturn(mockVisit);
    	when(data.getFileName()).thenReturn("Test");
    	FileWriter writer = new FileWriter();
    	writer.writeCSV(data, "visit", true);
    	File file = new File("visitTest.csv");
    	Scanner scanner = new Scanner(file);
    	String csvText = "";
    	while (scanner.hasNextLine()) {
    		csvText += scanner.nextLine();
    	}
    	boolean entry1 = csvText.contains("URL,File Size (Bytes),# of outlinks,ContentType");
    	boolean entry2 = csvText.contains("http://www.newsday.com/testing.png,54,55,image/png");
    	boolean entry3 = csvText.contains("http://www.newsday.com/coolpdfs/test.pdf,9999,42,application/pdf");
    	boolean entry4 = csvText.contains("http://www.newsday.com/images/ttt.jpg,94454999,13,image/jpg");
    	boolean entry5 = csvText.contains("http://www.newsday.com/,111,2,text/html");
    	boolean entry6 = csvText.contains("http://www.newsday.com/test/testing/tester/test.doc,949,2,applcation/msword");
    	file.delete();
    	assertTrue(entry1 && entry2 && entry3 && entry4 && entry5 && entry6);
    }
	
	public void testWriteURLS() throws IOException {
		CrawlData data = mock(CrawlData.class);
    	List<String> mockUrls = new ArrayList<String>();
    	mockUrls.add("http://www.newsday.com/,OK");
    	mockUrls.add("http://www.fewsfay.com/testing.png,N_OK");
    	mockUrls.add("http://www.nlalala.com/coolpdfs/test.pdf,N_OK");
    	mockUrls.add("http://www.newsday.com/images/ttt.jpg,OK");
    	mockUrls.add("http://www.randomsite.com/test/testing/tester/test.doc,N_OK");
    	when(data.getURLSTableData()).thenReturn(mockUrls);
    	when(data.getFileName()).thenReturn("Test");
    	FileWriter writer = new FileWriter();
    	writer.writeCSV(data, "urls", true);
    	File file = new File("urlsTest.csv");
    	Scanner scanner = new Scanner(file);
    	String csvText = "";
    	while (scanner.hasNextLine()) {
    		csvText += scanner.nextLine();
    	}
    	boolean entry1 = csvText.contains("URL,OK Status");
    	boolean entry2 = csvText.contains("http://www.fewsfay.com/testing.png,N_OK");
    	boolean entry3 = csvText.contains("http://www.newsday.com/,OK");
    	boolean entry4 = csvText.contains("http://www.nlalala.com/coolpdfs/test.pdf,N_OK");
    	boolean entry5 = csvText.contains("http://www.newsday.com/images/ttt.jpg,OK");
    	boolean entry6 = csvText.contains("http://www.randomsite.com/test/testing/tester/test.doc,N_OK");
    	file.delete();
    	assertTrue(entry1 && entry2 && entry3 && entry4 && entry5 && entry6);
    }
	
	public void testWriteStatsFile() throws IOException {
		CrawlData data = mock(CrawlData.class);
		when(data.getFileName()).thenReturn("TestStats");
		when(data.getFetchesAttempted()).thenReturn(22);
		when(data.getFetchesSucceeded()).thenReturn(33);
		when(data.getFetchesAborted()).thenReturn(44);
		when(data.getFetchesFailed()).thenReturn(11);
		Set<String> uniqueUrlsMock = mock(Set.class);
		Set<String> withinUrlsMock = mock(Set.class);
		Set<String> outsideUrlsMock = mock(Set.class);
		List<String> urlsTable = mock(List.class);
		when(uniqueUrlsMock.size()).thenReturn(450);
		when(withinUrlsMock.size()).thenReturn(333);
		when(outsideUrlsMock.size()).thenReturn(222);
		when(urlsTable.size()).thenReturn(123);
		when(data.getUniqueURLs()).thenReturn(uniqueUrlsMock);
		when(data.getURLsWithin()).thenReturn(withinUrlsMock);
		when(data.getURLsOutside()).thenReturn(outsideUrlsMock);
		when(data.getURLSTableData()).thenReturn(urlsTable);
		HashMap<Integer,Integer> statusCodes = new HashMap<Integer,Integer>();
		statusCodes.put(200, 44);
		statusCodes.put(404, 66);
		statusCodes.put(500, 2);
		when(data.getStatusCodesCounter()).thenReturn(statusCodes);
		HashMap<String,Integer> fileSizes = mock(HashMap.class);
		when(fileSizes.get("<1KB")).thenReturn(44);
		when(fileSizes.get("1KB~<10KB")).thenReturn(55);
		when(fileSizes.get("10KB~<100KB")).thenReturn(66);
		when(fileSizes.get("100KB~<1MB")).thenReturn(77);
		when(fileSizes.get(">=1MB")).thenReturn(88);
		when(data.getFileSizes()).thenReturn(fileSizes);
		HashMap<String,Integer> contentTypes = new HashMap<String,Integer>();
		contentTypes.put("text/html", 1967);
		contentTypes.put("application/pdf", 3);
		contentTypes.put("image/png", 67);
		when(data.getContentTypes()).thenReturn(contentTypes);
		FileWriter writer = new FileWriter();
		writer.writeStatsFile(data,true);
		File file = new File("ReportTest.txt");
		file.createNewFile();
		String stats = "";
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine()) {
			stats += scanner.nextLine();
		}
		boolean entry1 = stats.contains("News Site Crawled: null");
		boolean entry2 = stats.contains("# fetches attempted: 22");
		boolean entry3 = stats.contains("# fetches succeeded: 33");
		boolean entry4 = stats.contains("# fetches aborted: 44");
		boolean entry5 = stats.contains("# fetches failed: 11");
		boolean entry6 = stats.contains("Total URLs extracted: 123");
		boolean entry7 = stats.contains("# unique URLs extracted: 450");
		boolean entry8 = stats.contains("# unique URLs within News Site: 333");
		boolean entry9 = stats.contains("# unique URLs outside News Site: 222");
		boolean entry16 = stats.contains("200: 44");
		boolean entry10 = stats.contains("404: 66");
		boolean entry11 = stats.contains("500: 2");
		boolean entry12 = stats.contains("<1KB: 44");
		boolean entry13 = stats.contains("1KB ~ <10KB: 55");
		boolean entry14 = stats.contains("10KB ~ <100KB: 66");
		boolean entry15 = stats.contains("100KB ~ <1MB: 77");
		boolean entry17 = stats.contains(">=1MB: 88");
		boolean entry18 = stats.contains("text/html: 1967");
		boolean entry19 = stats.contains("application/pdf: 3");
		boolean entry20 = stats.contains("image/png: 67");
		file.delete();
		assertTrue(entry1 && entry2 && entry3 && entry4 && entry5 && entry6 &&
				entry7 && entry8 && entry9 && entry10 && entry11 && entry12 &&
				entry13 && entry14 && entry15 && entry16 && entry17 && entry18 &&
				entry19 && entry20);
    }
}
