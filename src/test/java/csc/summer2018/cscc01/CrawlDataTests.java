package csc.summer2018.cscc01;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import junit.framework.TestCase;

public class CrawlDataTests extends TestCase{
	
	public void testMergeFileSizes() {
    	CrawlData data = new CrawlData();
    	CrawlData data2 = mock(CrawlData.class);
    	HashMap<String, Integer> map1 = new HashMap<String, Integer>();
    	HashMap<String, Integer> map2 = new HashMap<String, Integer>();
    	map1.put(">=1MB", 505);
    	map1.put("<1KB", 45);
    	map2.put(">=1MB", 333);
    	map2.put("1KB~<10KB", 34);
    	map2.put("10KB~<100KB", 22);
    	data.FileSizes = map1;
    	when(data2.getFileSizes()).thenReturn(map2);
    	data.mergeFilesSizesOrContentTypes(data2, true);
    	boolean check1 = map1.get(">=1MB") == 838;
    	boolean check2 = map1.get("<1KB") == 45;
    	boolean check3 = map1.get("1KB~<10KB") == 34;
    	boolean check4 = map1.get("10KB~<100KB") == 22;
    	assertTrue(check1 && check2 && check3 && check4);
    }
	
	public void testMergeContentTypes() {
    	CrawlData data = new CrawlData();
    	CrawlData data2 = mock(CrawlData.class);
    	HashMap<String, Integer> map1 = new HashMap<String, Integer>();
    	HashMap<String, Integer> map2 = new HashMap<String, Integer>();
    	map1.put("text/html", 435);
    	map1.put("image/png", 22);
    	map2.put("text/html", 232);
    	map2.put("image/jpg", 34);
    	map2.put("image/png", 453);
    	data.ContentTypes = map1;
    	when(data2.getContentTypes()).thenReturn(map2);
    	data.mergeFilesSizesOrContentTypes(data2, false);
    	boolean check1 = map1.get("text/html") == 667;
    	boolean check2 = map1.get("image/png") == 475;
    	boolean check3 = map1.get("image/jpg") == 34;
    	assertTrue(check1 && check2 && check3);
    }
	
	public void testMergeStatusCodesCounter() {
    	CrawlData data = new CrawlData();
    	CrawlData data2 = mock(CrawlData.class);
    	HashMap<Integer, Integer> map1 = new HashMap<Integer, Integer>();
    	HashMap<Integer, Integer> map2 = new HashMap<Integer, Integer>();
    	map1.put(200, 435);
    	map1.put(301, 22);
    	map2.put(404, 232);
    	map2.put(201, 34);
    	map2.put(200, 453);
    	data.StatusCodesCounter = map1;
    	when(data2.getStatusCodesCounter()).thenReturn(map2);
    	data.mergeStatusCodeCounter(data2);
    	boolean check1 = map1.get(200) == 888;
    	boolean check2 = map1.get(301) == 22;
    	boolean check3 = map1.get(404) == 232;
    	boolean check4 = map1.get(201) == 34;
    	assertTrue(check1 && check2 && check3 && check4);
    }
}
