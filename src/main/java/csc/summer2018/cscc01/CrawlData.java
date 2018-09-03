package csc.summer2018.cscc01;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CrawlData {
	
	public HashMap <String, Integer> FetchTableData = new HashMap<String, Integer>();
	public HashMap <String, String[]> VisitTableData = new HashMap<String, String[]>();
	public List<String> URLSTableData = new ArrayList<String>();
	Set<String> uniqueURLs = new HashSet<String>();
	Set<String> URLsWithin = new HashSet<String>();
	Set<String> URLsOutside = new HashSet<String>();
	public String fileName = "";
	int fetchesAttempted = 0;
	int fetchesSucceeded = 0;
	int fetchesFailed = 0;
	int fetchesAborted = 0;
	HashMap <Integer, Integer> StatusCodesCounter = new HashMap<Integer,Integer>();
	HashMap <String, Integer> FileSizes = new HashMap <String, Integer>();
	HashMap <String, Integer> ContentTypes = new HashMap <String, Integer>();
	
	public CrawlData() {
		FileSizes.put("<1KB", 0);
		FileSizes.put("1KB~<10KB", 0);
		FileSizes.put("10KB~<100KB", 0);
		FileSizes.put("100KB~<1MB", 0);
		FileSizes.put(">=1MB", 0);
	}
	
	public HashMap<String, Integer> getFetchTableData() {
		return FetchTableData;
	}
	
	public HashMap<String, String[]> getVisitTableData() {
		return VisitTableData;
	}
	
	public List<String> getURLSTableData() {
		return URLSTableData;
	}
	
	public Set<String> getUniqueURLs() {
		return uniqueURLs;
	}
	
	public Set<String> getURLsWithin() {
		return URLsWithin;
	}
	
	public Set<String> getURLsOutside() {
		return URLsOutside;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public int getFetchesAttempted() {
		return fetchesAttempted;
	}
	
	public int getFetchesSucceeded() {
		return fetchesSucceeded;
	}
	
	public int getFetchesFailed() {
		return fetchesFailed;
	}
	
	public int getFetchesAborted() {
		return fetchesAborted;
	}
	
	public HashMap<Integer, Integer> getStatusCodesCounter() {
		return StatusCodesCounter;
	}
	
	public HashMap<String, Integer> getFileSizes() {
		return FileSizes;
	}
	
	public HashMap<String, Integer> getContentTypes() {
		return ContentTypes;
	}

	public void mergeFilesSizesOrContentTypes(CrawlData stored, boolean isFileSizes) {
		HashMap<String, Integer> map = null;
		HashMap<String, Integer> otherMap = null;
		if (isFileSizes) {
			map = FileSizes;
			otherMap = stored.getFileSizes();
		} else {
			map = ContentTypes;
			otherMap = stored.getContentTypes();
		}
		Iterator it = otherMap.entrySet().iterator();
        while (it.hasNext()) {
    		Map.Entry <String, Integer> pair = (Map.Entry)it.next();
    		if (map.containsKey(pair.getKey())) {
    			map.put(pair.getKey(), map.get(pair.getKey()) + pair.getValue());
    		} else {
    			map.put(pair.getKey(), pair.getValue());
    		}
        }
	}

	public void mergeStatusCodeCounter(CrawlData stored) {
		Iterator it = stored.getStatusCodesCounter().entrySet().iterator();
        while (it.hasNext()) {
    		Map.Entry <Integer, Integer> pair = (Map.Entry)it.next();
    		if (StatusCodesCounter.containsKey(pair.getKey())) {
    			StatusCodesCounter.put(pair.getKey(), StatusCodesCounter.get(pair.getKey()) + pair.getValue());
    		} else {
    			StatusCodesCounter.put(pair.getKey(), pair.getValue());
    		}
        }
	}
}
