package test;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.http.Header;

public class MyCrawler extends WebCrawler {

    //private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
    //                + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|html|swf|wma|zip|rar|gz))$");
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(html))$");

    private static int counter=0;
    public static String visited="";
    
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            counter++;
            return !FILTERS.matcher(href).matches() && href.startsWith("http://metroid.wikia.com/wiki/") && counter<100;
    }

    
    @Override
    public void visit(Page page) {
            String url = page.getWebURL().getURL();
            visited+=page.getWebURL()+"\n";
            try{
            	String line = new String(page.getContentData(),"UTF-8");
            	line = line.replaceAll("<.*?>","");
            	line = line.replaceAll("(?m)^\\s*$[\n\r]{1,}", "");
    			PrintWriter pw = new PrintWriter(new FileWriter(url.replaceAll("http://metroid.wikia.com/wiki/", "")+".txt"));
    			pw.write(line);
    			pw.write("\r\n");
    			pw.close();
    		
    		}
    		catch(IOException x){
    		    System.err.println(x);
    		}           
    }
}