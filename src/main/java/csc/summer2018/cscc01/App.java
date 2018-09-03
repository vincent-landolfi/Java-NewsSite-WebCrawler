package csc.summer2018.cscc01;

import java.awt.List;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Hello world!
 *
 */
public class App 
{
	public static String currentSeed = null;
    public static void main( String[] args ) throws Exception
    {
    	String crawlStorageFolder = "Crawl";
        int numberOfCrawlers = 7;
        Options options = new Options();

        Option seed = new Option("seed", "seed", true, "seeds for crawling");
        seed.setRequired(true);
        options.addOption(seed);

        Option depth = new Option("depth", "depth", true, "depth of search");
        depth.setRequired(true);
        options.addOption(depth);
        
        Option pages = new Option("pages", "pages", true, "max pages to search");
        pages.setRequired(true);
        options.addOption(pages);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        String[] enteredSeed = cmd.getOptionValue("seed").split(",");
        int enteredDepth = Integer.parseInt(cmd.getOptionValue("depth"));
        int enteredPages = Integer.parseInt(cmd.getOptionValue("pages"));
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(enteredDepth);
        config.setMaxPagesToFetch(enteredPages);
        config.setIncludeBinaryContentInCrawling(Boolean.TRUE);
        config.setPolitenessDelay(150);
        /*
         * Instantiate the controller for this crawl.
         */
        for (String arg: enteredSeed) {
	        PageFetcher pageFetcher = new PageFetcher(config);
	        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
	        robotstxtConfig.setEnabled(false);
	        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
	        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
	
	        /*
	         * For each crawl, you need to add some seed urls. These are the first
	         * URLs that are fetched and then the crawler starts following links
	         * which are found in these pages
	         */
	        if (!arg.startsWith("https://www.newsday.com") && !arg.startsWith("https://www.chicagotribune.com") && !arg.startsWith("http://www.chicagotribune.com") &&
				!arg.startsWith("https://www.foxnews.com") && !arg.startsWith("https://www.nbcnews.com") && !arg.startsWith("https://www.c-span.org") && !arg.startsWith("http://www.newsday.com") &&
				!arg.startsWith("http://www.foxnews.com") && !arg.startsWith("http://www.nbcnews.com") && !arg.startsWith("http://www.c-span.org")) {
	        	System.out.println("Invalid URL - Not one of the 5 News sites: " + arg);
	        } else {
		        currentSeed = arg;
		        controller.addSeed(arg);
		        /*
		         * Start the crawl. This is a blocking operation, meaning that your code
		         * will reach the line after this only when crawling is finished.
		         */
		        controller.start(Crawler.class, numberOfCrawlers);
		        CrawlData masterCrawlData = new CrawlData();
		        java.util.List<Object> crawlerData = controller.getCrawlersLocalData();
		        for (Object data: crawlerData) {
		        	CrawlData storedData = (CrawlData) data;
		        	masterCrawlData.FetchTableData.putAll(storedData.FetchTableData);
		        	masterCrawlData.URLSTableData.addAll(storedData.URLSTableData);
		        	masterCrawlData.VisitTableData.putAll(storedData.VisitTableData);
		        	masterCrawlData.fetchesAttempted += storedData.fetchesAttempted;
		        	masterCrawlData.fetchesFailed += storedData.fetchesFailed;
		        	masterCrawlData.fetchesSucceeded += storedData.fetchesSucceeded;
		        	masterCrawlData.fetchesAborted += storedData.fetchesAborted;
		        	masterCrawlData.uniqueURLs.addAll(storedData.uniqueURLs);
		        	masterCrawlData.URLsOutside.addAll(storedData.URLsOutside);
		        	masterCrawlData.URLsWithin.addAll(storedData.URLsWithin);
		        	masterCrawlData.mergeFilesSizesOrContentTypes(storedData, true);
		        	masterCrawlData.mergeFilesSizesOrContentTypes(storedData, false);
		        	masterCrawlData.mergeStatusCodeCounter(storedData);
		        }
		        FileWriter writer = new FileWriter();
		        masterCrawlData.fileName = arg;
		        writer.writeCSV(masterCrawlData, "fetch", false);
		        writer.writeCSV(masterCrawlData, "visit", false);
		        writer.writeCSV(masterCrawlData, "urls", false);
		        writer.writeStatsFile(masterCrawlData, false);
	        }
        }
    }
    
    public static void setCurrentSeed(String s) {
    	currentSeed = s;
    }
}