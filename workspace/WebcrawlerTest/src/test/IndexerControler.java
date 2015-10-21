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
import java.util.Vector;

import javax.swing.JOptionPane;

import test.PostingsList.PostingsListElement;;

public class IndexerControler
{
	private static String indexerPath = Paths.get("").toAbsolutePath().toString() + "\\idx.ser";
	private static String workDirectory = Paths.get("").toAbsolutePath().toString() + "\\CrawlerFiles\\";
	
	public static void run()
	{
            Indexer idxr = loadIndexer();
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
            Vector<File> fileList = new Vector();
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
                        //System.out.println("Dir: " + fi.getPath());
                    }
                    else
                    {
                        idxr.parseDocument(fi.getPath());
                        //System.out.println("Txt: " + fi.getPath());
                    }
                }
            } catch (FileNotFoundException e)
            {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Yer fuk up");
                    return;
            }

            /*JOptionPane.showMessageDialog(null, idxr.wDict.toString());
            JOptionPane.showMessageDialog(null, idxr.docList.toString());
            JOptionPane.showMessageDialog(null, idxr.pList.toString());
            idxr.pList.save();
            JOptionPane.showMessageDialog(null, "It is time");
            idxr.pList.load();
            JOptionPane.showMessageDialog(null, idxr.pList.toString());*/
            saveIndexer(idxr);
            idxr.pList.save();
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
				idxr.pList.postings = new Vector<PostingsListElement>();
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
	
}
