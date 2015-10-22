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
import static java.lang.System.gc;
import java.util.ArrayList;

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
				//System.out.println(word);
			}
			sc.close();
                        System.out.println(path);
		}
		//else
			//System.out.println(path);
	}
	
	public int fileID(String path)
	{
		int res = 0;
		if(docList.containsKey(path))
			res = docList.get(path);
		return res;
	}
        
    public static PostingsListElement[] sort(PostingsListElement[] inputArr) {
        return quickSort(0, inputArr.length - 1, inputArr);
    }
 
    private static PostingsListElement[] quickSort(int lowerIndex, int higherIndex, PostingsListElement[] array) {
         
        int i = lowerIndex;
        int j = higherIndex;
        PostingsListElement pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
        while (i <= j) {
            while (array[i].docID < pivot.docID) {
                i++;
            }
            while (array[j].docID > pivot.docID) {
                j--;
            }
            if (i <= j) {
                exchangeElements(i, j, array);
                i++;
                j--;
            }
        }
        if (lowerIndex < j)
            quickSort(lowerIndex, j,array);
        if (i < higherIndex)
            quickSort(i, higherIndex,array);
        return array;
    }
 
    private static void exchangeElements(int i, int j, PostingsListElement[] array) {
        PostingsListElement temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    public static PostingsListElement[] intersectArrays(PostingsListElement[] a, PostingsListElement[] b){
        a=Indexer.sort(a);
        b=Indexer.sort(b);
        PostingsListElement[] c = new PostingsListElement[Math.min(a.length, b.length)]; 
        int ai = 0, bi = 0, ci = 0;
        while (ai < a.length && bi < b.length) {
            if (a[ai].docID < b[bi].docID) {
                ai++;
            } else if (a[ai].docID > b[bi].docID) {
                bi++;
            } else {
                if (ci == 0 || a[ai].docID != c[ci - 1].docID) {
                    c[ci++] = a[ai];
                }
                ai++; bi++;
            }
        }
        return trimArray(c,ci); 
    }
    
    public static PostingsListElement[] trimArray(PostingsListElement[] array, int newLength){
        PostingsListElement[] arr = new PostingsListElement[newLength];
        for(int i=0;i<newLength;i++){
            arr[i] = array[i];
        }
        return arr;
    }
    
    public static PostingsListElement[] findMatches (ArrayList<PostingsListElement[]> list){
        if(list.size()==0){
            return null;
        }
        else{
            if(list.size()==1){
                return list.get(0);
            }
            else{
                PostingsListElement[] intersection = list.get(0);
                for(int i=1;i<list.size();i++){
                    intersection = intersectArrays(intersection,list.get(i));
                }
                return intersection;
            }
        }
    }
    
    public void BSBIMerge()
    {
        // Ordenar cada archivo
        int totalDocs = pList.nextList;
        while(totalDocs > 1)
        {
            pList.maxListSize *= 2;
            for(int i = 0; i < totalDocs; i += 2)
            {
                pList.load(i);
                PostingsListElement[] l1 = (PostingsListElement[]) pList.postings.toArray();
                pList.load(i + 1);
                PostingsListElement[] l2 = (PostingsListElement[]) pList.postings.toArray();
                pList.postings = null;
                pList.actualList = i / 2;
                if(l2 != null)
                {
                    l1 = intersectArrays(l1, l2); // Sort
                }
                // Meter a PostingsList Vector
                pList.save();
            }
        }
        pList.nextList = 1;
        
        // Rellenar el indice con postings
    }
}
