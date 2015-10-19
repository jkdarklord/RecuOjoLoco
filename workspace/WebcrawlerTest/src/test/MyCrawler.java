package test;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyCrawler extends WebCrawler {

    //private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
    //                + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|html|swf|wma|zip|rar|gz))$");
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(html))$");

    public static int counter=0;
    public static int documentsCreated=0;
    public static String visited="";
    public static String urlDomain="";
    public static boolean limit= false;
    public static int maxCount=0;
    public static File directory;
  
    
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            counter++;
            if(limit){
                return !FILTERS.matcher(href).matches() && href.startsWith(urlDomain) && counter<maxCount;
            }
            else{
                return !FILTERS.matcher(href).matches() && href.startsWith(urlDomain);
            }
    }

    @Override
    public void visit(Page page) {
            String url = page.getWebURL().getURL();
            visited+=page.getWebURL()+"\n";
            try{
            	String line = new String(page.getContentData(),"UTF-8");
            	Document doc = Jsoup.parse(line);
            	Elements paragraphs = doc.select("p");
                String [] urlTokens = url.split("/");
                String title = urlTokens[urlTokens.length-1] + ".txt";
                title = title.replaceAll("[\\/\\\\:\\*\\?\\\"<>\\|]","-");
                File file = new File(directory, title);
                //File file = new File(directory, documentsCreated + ".txt");
                FileWriter fw = new FileWriter(file);
            	PrintWriter pw = new PrintWriter(fw);
            	  for(Element p : paragraphs){
            		  pw.write(p.text()+"\n");
                  }
	    	pw.write("\r\n");
	    	pw.close();
                documentsCreated++;
            }
            catch(IOException x){
                System.err.println(x);
            }           
    }
}