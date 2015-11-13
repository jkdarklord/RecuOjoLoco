    package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import test.PostingsList.PostingsListElement;;

public class IndexerControler
{
	private static String indexerPath = Paths.get("").toAbsolutePath().toString() + "\\idx.ser";
	private static String workDirectory = Paths.get("").toAbsolutePath().toString() + "\\CrawlerFiles\\";
	
	public static Indexer idxr;
	
	public static void initialize()
	{
		idxr = loadIndexer();
	}
	
	public static void run()
	{
            File workingFolder = new File(workDirectory);
            //System.out.println(Paths.get("").toAbsolutePath().toString() + "\\CrawlerFiles\\");
            
            FilenameFilter textFilter = new FilenameFilter()
            {
                    public boolean accept(File dir, String name)
                    {
                            String lowercaseName = name.toLowerCase();
                            if (dir.isDirectory() || lowercaseName.endsWith(".txt"))
                            {
                                return true;
                            } else
                            {
                                return false;
                            }
                    }
            };
            
            ArrayList<File> fileList = new ArrayList<File>();
            File temp[] = workingFolder.listFiles(textFilter);
            for(int i = 0; i < temp.length; i++)
            {
                fileList.add(temp[i]);
            }
            
            
            //System.out.println("Dir: " + Arrays.toString(workingFolder.listFiles(textFilter)));
            try
            {
                for( int i = 0; i < fileList.size(); ++i )
                {
                    File fi = fileList.get(i);
                    if(fi.isDirectory())
                    {
                        temp = fi.listFiles(textFilter);
                        for(int j = 0; j < temp.length; j++)
                        {
                            fileList.add(temp[j]);
                        }
                        System.out.println("Dir: " + fi.getPath());
                    }
                    else
                    {
                        idxr.parseDocument(fi.getPath());
                        //System.out.println("Txt: " + fi.getPath());
                    }
                }
                
            JOptionPane.showMessageDialog(null, "Index creado: OUT");
            } catch (FileNotFoundException e)
            {
                    e.printStackTrace();
                    return;
            }
            
            
            /*JOptionPane.showMessageDialog(null, idxr.wDict.toString());
            JOptionPane.showMessageDialog(null, idxr.pList.toString());
            idxr.pList.save();
            JOptionPane.showMessageDialog(null, "It is time");
            idxr.pList.load();
            JOptionPane.showMessageDialog(null, idxr.pList.toString());*/
            idxr.BSBIMerge();
            //JOptionPane.showMessageDialog(null, idxr.pList.toString());
            saveIndexer(idxr);
            idxr.pList.save();
            
            idxr.calculateWeights();
            idxr.writeWeights();
	}
	
	private static Indexer loadIndexer()
	{
		Indexer idxr = new Indexer();
		
		File docListFile = new File(indexerPath);
		if(docListFile.exists())
		{
			//System.out.println("Heh");
			try
			{
				FileInputStream file = new FileInputStream(indexerPath);
				ObjectInputStream input = new ObjectInputStream(file);
				idxr = (Indexer) input.readObject();
				input.close();
				idxr.pList.postings = new ArrayList<PostingsListElement>();
				idxr.pList.actualList = -1;
			}catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			idxr = new Indexer();
		}
		
		return idxr;
	}
	
	private static void saveIndexer(Indexer idxr)
	{
		try
		{  
			FileOutputStream file = new FileOutputStream(indexerPath);
			ObjectOutputStream output = new ObjectOutputStream(file);   
			output.writeObject(idxr);
			output.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
        
        public static void deleteSerFiles(){
            File folder = new File(Paths.get("").toAbsolutePath().toString());
            File fList[] = folder.listFiles();
            for (int i = 0; i < fList.length; i++) {
                File pes = fList[i];
                if (pes.getName().endsWith(".ser")) {
                    pes.delete();
                }
            }
        }
        
        public static PostingsListElement[] searchQueryOnIndex(String query){
            String [] tokens = query.split("[^a-zA-Z0-9\\'\\-]");
            //Lo que en realidad deberia decir, es algo como
            PostingsListElement[] results = idxr.getPostings(tokens[0]);
            for(int i=1;i<tokens.length;i++){
                //Lo que en realidad deberia decir, es algo como
                results = Indexer.intersectArrays(results, idxr.getPostings(tokens[i]));
            }
            return results;
        }
        
        public PostingsListElement[] searchQueryOnIndex2(String query){
            String [] tokens = query.split("[^a-zA-Z0-9\\'\\-]");
            //Lo que en realidad deberia decir, es algo como
            PostingsListElement[] results = idxr.getPostings(tokens[0]);
            for(int i=1;i<tokens.length;i++){
                //Lo que en realidad deberia decir, es algo como
                results = Indexer.intersectArrays(results, idxr.getPostings(tokens[i]));
            }
            results = idxr.sortByCosine(tokens, results);
            return results;
        }
        
	
}
