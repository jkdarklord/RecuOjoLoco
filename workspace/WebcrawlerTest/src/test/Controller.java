package test;

import org.apache.log4j.BasicConfigurator;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class Controller {
	Path currentRelativePath;
	File seedIndex;
	
	public Controller(){
		currentRelativePath = Paths.get("");
		createSeedList();
	}

    public void startCrawling(String seed, int crawlerCount, boolean limitCrawling, int documentMax, String directoryName) throws Exception {
            String crawlStorageFolder = "/data/";
            int numberOfCrawlers = crawlerCount;
            BasicConfigurator.configure();

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);

            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

            controller.addSeed(seed);
            
            setParameters(seed,crawlerCount,limitCrawling,documentMax,directoryName);
            
            controller.start(MyCrawler.class, numberOfCrawlers);
            
            addSeedToIndex(seed,MyCrawler.documentsCreated);
    }
    
    
    public void setParameters(String seed, int crawlerCount, boolean limitCrawling, int documentMax, String directoryName){
        MyCrawler.counter = 0;
        MyCrawler.urlDomain = seed;
        MyCrawler.directory = new File(seed);
        MyCrawler.limit=limitCrawling;
        MyCrawler.maxCount=documentMax;
        MyCrawler.directory = new File(currentRelativePath.toAbsolutePath().toString()+"\\CrawlerFiles\\"+directoryName);
        MyCrawler.directory.mkdirs();
    }
    
    public void createSeedList(){
    	try{
	    	File directory = new File(currentRelativePath.toAbsolutePath().toString()+"\\CrawlerFiles");
	    	directory.mkdir();
	    	File seedIndex = new File(directory,"seedIndex.txt");
	    	FileWriter fw = new FileWriter(seedIndex);
	    	PrintWriter pw = new PrintWriter(fw);
	    	pw.write("\r\n");
	    	pw.close();
    	}
    	catch(IOException x){
            System.err.println(x);
        }   
    }
    
    public void addSeedToIndex(String newSeed,int documentsCreated){
    	try
    	{
            String indexDirectory = currentRelativePath.toAbsolutePath().toString()+"\\CrawlerFiles\\seedIndex.txt";
    	    FileWriter fw = new FileWriter(indexDirectory,true);
    	    fw.write(newSeed+"$"+documentsCreated+"\n");
    	    fw.close();
    	}
    	catch(IOException ioe)
    	{
    	    System.err.println("IOException: " + ioe.getMessage());
    	}
    }
}