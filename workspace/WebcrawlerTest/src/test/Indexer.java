package test;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import test.PostingsList.PostingsListElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Indexer implements Serializable
{
	public HashMap<String, Integer> docList;
	public PostingsList pList;
	public Dictionary wDict;
	private int nextID;
	
	public Indexer()
	{
		docList = new HashMap<String, Integer>();
		pList = new PostingsList();
		wDict = new Dictionary();
		nextID = 0;
	}
	/*
	public void load()
	{
		File docListFile = new File("docs.ser");
		if(docListFile.exists())
		{
			try
			{
				FileInputStream file = new FileInputStream("docs.ser");
				ObjectInputStream input = new ObjectInputStream(file);
				docList = (HashMap<String, Integer>) input.readObject();
				input.close();
			}catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			docList = new HashMap<String, Integer>();
		}
		
		File dictionaryFile = new File("dict.ser");
		if(dictionaryFile.exists())
		{
			try
			{
				FileInputStream file = new FileInputStream("dict.ser");
				ObjectInputStream input = new ObjectInputStream(file);
				wDict = (Dictionary) input.readObject();
				input.close();
			}catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			wDict = new Dictionary();
		}
	}
	
	public void save()
	{
		try
		{  
			FileOutputStream file = new FileOutputStream("docs.ser");
			ObjectOutputStream output = new ObjectOutputStream(file);   
			output.writeObject(docList);
			output.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		try
		{  
			FileOutputStream file = new FileOutputStream("dict.ser");
			ObjectOutputStream output = new ObjectOutputStream(file);   
			output.writeObject(wDict);
			output.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}*/
	
	public void parseDocument(String path) throws FileNotFoundException
	{
		if(!docList.containsKey(path))
		{
			File f = new File(path);
			Scanner sc = new Scanner(f);
			sc.useDelimiter("[^a-zA-Z0-9\\'\\-]");
			
			int actualID = nextID;
			docList.put(path, actualID);
			nextID++;
			
			String word;
			HashMap<String, PostingsListElement> localDict = new HashMap<String, PostingsListElement>();
			
			while(sc.hasNext())
			{
				word = sc.next().toLowerCase();
				if(!word.isEmpty())
				{
					int id = wDict.addOcurrence(word);
					
					if(!localDict.containsKey(word))
					{
						PostingsListElement p = pList.append(actualID, id, 1);
						localDict.put(word, p);
					}
					else
					{
						localDict.get(word).df += 1;
					}
				}
				
				
				System.out.println(word);
			}
			sc.close();
		}
		else
			System.out.println(path);
	}
	
	public int fileID(String path)
	{
		int res = 0;
		if(docList.containsKey(path))
			res = docList.get(path);
		return res;
	}
}
