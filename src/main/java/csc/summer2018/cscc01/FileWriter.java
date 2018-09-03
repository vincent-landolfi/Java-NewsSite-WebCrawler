package csc.summer2018.cscc01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FileWriter {
	public void writeCSV(CrawlData data,String type,boolean testMode) throws FileNotFoundException {
		String[] columns;
		// hashmap stored data
		HashMap map = null;
		// list stored data
		List<String> strList = null;
		// if we want the fetch csv
		if (type.equals("fetch")) {
			// columns are url and status code
			columns = new String[]{"URL", "Status Code"};
			// the map is the fetch table data map
			map = data.getFetchTableData();
		// if we want the visit csv
		} else if (type.equals("visit")) {
			// columns are url file size outlinks and content type
			columns = new String[]{"URL","File Size (Bytes)", "# of outlinks", "ContentType"};
			// map is visit table data
			map = data.getVisitTableData();
		// if we want the urls csv
		} else {
			// columns are url and ok status
			columns = new String[]{"URL", "OK Status"};
			// map is urls data map
			strList = data.getURLSTableData();
		}
		// build a string to write to file
		StringBuilder sb = new StringBuilder();
		//
		String filesName = type + ".csv";
		if(testMode) {
			filesName = type + "Test.csv";
		}
		// new file
		File file = new File(filesName);
		int j = 0;
		while (file.exists()) {
			file = new File(type + j + ".csv");
			j++;
		}
		// writer to write to file
		PrintWriter pw = new PrintWriter(file);
		// headers
		for (int i = 0; i < columns.length; i++) {
			// ad the column
			sb.append(columns[i]);
			// if not last column
			if (i != columns.length - 1) {
				// comma between cols
				sb.append(",");
			// last col
			} else {
				// new line
				sb.append("\n");
			}
		}
		if (!type.equals("urls")) {
			// actual data, set from the map
			Iterator it = map.entrySet().iterator();
			// while we have map data
	        while (it.hasNext()) {
	        	// get current data
	    		Map.Entry pair = (Map.Entry)it.next();
	    		// if its not the visit csv
	    		if (!type.equals("visit")) {
	    			// just key value pairs
		    		sb.append(pair.getKey());
		    		sb.append(',');
		    		sb.append(pair.getValue());
		    		sb.append('\n');
		    	// it is visit csv
	    		} else {
	    			// get the key and the three elms from array
	    			sb.append(pair.getKey());
		    		sb.append(',');
		    		sb.append(((String[])pair.getValue())[0]);
		    		sb.append(',');
		    		sb.append(((String[])pair.getValue())[1]);
		    		sb.append(',');
		    		sb.append(((String[])pair.getValue())[2]);
		    		sb.append('\n');
	    		}
	        }
		} else {
			for (String str:strList) {
				sb.append(str + "\n");
			}
		}
        // write it to the file
        pw.write(sb.toString());
        pw.close();
	}
	
	public void writeStatsFile(CrawlData data,boolean testMode) throws FileNotFoundException {
		// build the string for the file
		StringBuilder sb = new StringBuilder();
		// file name
		String fileName = "CrawlReport.txt";
		if (testMode) {
			fileName = "ReportTest.txt";
		}
		// new file
		File file = new File(fileName);
		int i = 0;
		// check if file exisits
		while (file.exists()) {
			file = new File("CrawlReport" + i + ".txt");
			i++;
		}
		// writer to file
		PrintWriter pw = new PrintWriter(file);
		// just laying this out how it is in the handout, pulling the relevant
		// data from the crawl data
		sb.append("Name: Vincent Landolfi\n");
		sb.append("UtorID: 1002350733\n");
		sb.append("News Site Crawled: " + data.fileName + "\n");
		sb.append("Fetch Statistics\n");
		sb.append("================\n");
		sb.append("# fetches attempted: " + data.getFetchesAttempted() + "\n");
		sb.append("# fetches succeeded: " + data.getFetchesSucceeded() + "\n");
		sb.append("# fetches aborted: " + data.getFetchesAborted() + "\n");
		sb.append("# fetches failed: " + data.getFetchesFailed() + "\n\n\n");
		sb.append("Outgoing URLs:\n");
		sb.append("================\n");
		sb.append("Total URLs extracted: " + data.getURLSTableData().size() + "\n");
		sb.append("# unique URLs extracted: " + data.getUniqueURLs().size() + "\n");
		sb.append("# unique URLs within News Site: " + data.getURLsWithin().size() + "\n");
		sb.append("# unique URLs outside News Site: " + data.getURLsOutside().size() + "\n\n\n");
		sb.append("Status Codes:\n");
		sb.append("================\n");
		// for status codes, results can vary so we iterate over them
		Iterator it = data.getStatusCodesCounter().entrySet().iterator();
		while (it.hasNext() ) {
			// get the key value pair
			Map.Entry <Integer, Integer> pair = (Map.Entry)it.next();
			// make a row out of them
			sb.append(pair.getKey() + ": " + pair.getValue() + "\n");
		}
		// continuing to follow handout format
		sb.append("\n\n");
		sb.append("File Sizes: \n");
		sb.append("================\n");
		sb.append("<1KB: " + data.getFileSizes().get("<1KB") + "\n");
		sb.append("1KB ~ <10KB: " + data.getFileSizes().get("1KB~<10KB") + "\n");
		sb.append("10KB ~ <100KB: " + data.getFileSizes().get("10KB~<100KB") + "\n");
		sb.append("100KB ~ <1MB: " + data.getFileSizes().get("100KB~<1MB") + "\n");
		sb.append(">=1MB: " + data.getFileSizes().get(">=1MB") + "\n\n\n");
		sb.append("Content Types:\n");
		sb.append("================\n");
		// iterating over content types because that can also vary
		it = data.getContentTypes().entrySet().iterator();
		while (it.hasNext() ) {
			Map.Entry <String, Integer> pair = (Map.Entry)it.next();
			if (pair.getKey().contains("png") || pair.getKey().contains("html") || pair.getKey().contains("jpg") || pair.getKey().contains("jpeg") ||
            		pair.getKey().contains("gif") || pair.getKey().contains("pdf") || pair.getKey().contains("msword")) {
				// make row out of key val pair
				sb.append(pair.getKey() + ": " + pair.getValue() + "\n");
			}
		}
		// write file
		pw.write(sb.toString());
		// close
		pw.close();
	}
}
