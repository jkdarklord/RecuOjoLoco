package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JOptionPane;

import test.PostingsList.PostingsListElement;;

public class IndexerControler
{
	private static String indexerPath = "idx.ser";
	private static String workDirectory = "./CrawlerFiles/";
	
	
	public static void run()
	{
		Indexer idxr = loadIndexer();
		File workingFolder = new File(workDirectory);
		
		FilenameFilter textFilter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(".txt"))
				{
					return true;
				} else
				{
					return false;
				}
			}
		};
		File fileList[] = workingFolder.listFiles(textFilter);
		
		try
		{
			for( File fi : fileList )
			{
				idxr.parseDocument(fi.getPath());
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
			System.out.println("Heh");
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
