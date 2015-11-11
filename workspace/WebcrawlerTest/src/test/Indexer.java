package test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

import test.Dictionary.DictionaryElement;
import test.PostingsList.PostingsListElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import static java.lang.System.gc;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JOptionPane;
import static test.MyCrawler.directory;

public class Indexer implements Serializable
{
	public HashMap<String, Integer> docList;
	public HashMap<Integer, String> invertedDocList;
	public PostingsList pList;
	public Dictionary wDict;
	private int nextID;
        private WeightVectors[] weights; 
	
	public Indexer()
	{
		docList = new HashMap<String, Integer>();
		invertedDocList = new HashMap<Integer, String>();
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
			invertedDocList.put(actualID, path);
			nextID++;
			
			String word;
			HashMap<String, PostingsListElement> localDict = new HashMap<String, PostingsListElement>();
                        HashMap<Integer, Long> posList = new HashMap<Integer, Long>();
			
                        int i=0;
			while(sc.hasNext())
			{
                            i++;
				word = sc.next().toLowerCase();
				if(!word.isEmpty())
				{
					int id = wDict.addOcurrence(word);
					
					if(!localDict.containsKey(word))
					{
                                                posList.put(id, pList.actualList * pList.maxListSize + pList.postings.size());
						PostingsListElement p = pList.append(actualID, id, 1);
						localDict.put(word, p);
					}
					else
					{
						localDict.get(word).tf += 1;
					}
				}
				//System.out.println(word);
			}
			sc.close();
                        System.out.println(path);
                        
                        // Actualizar postinglist con valores finales
                        Iterator it = localDict.entrySet().iterator();
                        if(!path.contains("seedIndex.txt")){
                            while (it.hasNext())
                            {
                                PostingsListElement actual = (PostingsListElement)((HashMap.Entry)it.next()).getValue();
                                pList.elementAt(docList.get(actual.termID)).tf = actual.tf; //ESTA LINEA NO FUNCIONA
                                //it.remove(); // avoids a ConcurrentModificationException
                            }
                        }
                        
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
	
	public String pathFromDocID(int docID)
	{
		String res = null;
		if(invertedDocList.containsKey(docID))
			res = invertedDocList.get(docID);
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
    	int totalBlocks = pList.nextList;
    	/*
        for(int i = 0; i < totalBlocks; ++i)
        {
        	pList.load(i);
        	PostingsListElement[] temp = sort( (PostingsListElement[])pList.postings.toArray() );
        	pList.postings = new ArrayList<PostingsListElement>();
        	for(int j = 0; j < temp.length; j++)
            {
        		pList.postings.add(temp[j]);
            }
        }
        pList.save();*/
        
    	if(totalBlocks > 1)
    	{
    		while(totalBlocks > 1)
            {
                pList.maxListSize *= 2;
                for(int i = 0; i < totalBlocks; i += 2)
                {
                    pList.load(i);
                    PostingsListElement[] l1 = new PostingsListElement[pList.postings.size()];
                    pList.postings.toArray(l1);
                    pList.load(i + 1);
                    PostingsListElement[] l2 = new PostingsListElement[pList.postings.size()];
                    pList.postings.toArray(l2);
                    l1 = Arrays.copyOf(l1, l1.length + l2.length);
                    System.arraycopy(l2, 0, l1, l1.length, l2.length);
                    pList.postings = null;
                    pList.actualList = i / 2;
                    if(l2 != null)
                    {
                    	
                    	Arrays.sort(l1);
                    }
                    
                    // Meter a PostingsList ArrayList y guardar
                    pList.postings = new ArrayList<PostingsListElement>();
                	for(int j = 0; j < l1.length; j++)
                    {
                		pList.postings.add(l1[j]);
                    }
                    pList.save();
                }
            }
            pList.nextList = 1;
    	}
    	else
    	{
    		pList.load(0);
    		PostingsListElement[] l1 = new PostingsListElement[pList.postings.size()];
    		pList.postings.toArray(l1);
    		Arrays.sort(l1);
    		pList.postings = new ArrayList<PostingsListElement>();
        	for(int j = 0; j < l1.length; j++)
            {
        		pList.postings.add(l1[j]);
            }
    	}
        
        // Rellenar el indice con postings
        updateIndex();
    }
    
    public void updateIndex()
    {
    	HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();
    	
    	int previous = -1;
    	for(int i = 0; i < pList.postings.size(); ++i)
    	{
    		if( pList.postings.get(i).termID != previous )
    		{
    			temp.put(pList.postings.get(i).termID, i);
    		}
    		previous = pList.postings.get(i).termID;
    	}
    	
    	Collection<DictionaryElement> x = wDict.map.values();
    	Iterator itr = x.iterator();
    	while(itr.hasNext())
    	{
    		DictionaryElement actual = (DictionaryElement) itr.next();
    		actual.postings = temp.get(actual.id);
    	}
    }
    
    public PostingsListElement[] getPostings(String term)
    {
    	if(!wDict.map.containsKey(term))
    		return null;
    	int termID = wDict.map.get(term).postings;
    	PostingsListElement[] l1 = new PostingsListElement[pList.postings.size()];
    	return pList.listFrom(termID);
    }
    
    public int getTermDF(String term){
        if(!wDict.map.containsKey(term))
    		return -1;
    	return wDict.map.get(term).df;
    }
    
    public int getTermTF(String term, int docID){
        PostingsListElement[] postings = getPostings(term);
        int i=0;
        while((i<postings.length)&&(postings[i].docID!=docID)){
            i++;
        }
        if(i<postings.length){
            return postings[i].tf;
        }
        else{
            return -1;
        }
        
    }
    

    public void calculateWeights(){
        weights=new WeightVectors[invertedDocList.size()];
        
        Iterator it = wDict.map.entrySet().iterator();
        for(int i=0;i<weights.length;i++){
            weights[i]=new WeightVectors(wDict.map.size());
            for(int j=0;j<wDict.map.size();j++){
                String actualTerm = (String)((HashMap.Entry)it.next()).getKey();
                double df = getTermDF(actualTerm);
                double tf = getTermTF(actualTerm,i);
                weights[i].termWeight[j]= ((1 + Math.log10(tf))*(Math.log10(invertedDocList.size()/df)));
            }
        }
    }
    
    public void writeWeights(){
        try{
        File file = new File(directory, "pesos.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        
        for(int i=0;i<weights.length;i++){
            String line="";
            for(int j=0;j<wDict.map.size();j++){
                line += weights[i].termWeight[j];
            }
            pw.write("Documento " + i + ": " + line+"\n");
        }
        pw.write("\r\n");
        pw.close();
        }
        catch(Exception e){
            System.err.println(e);
        }
        
    }
}
