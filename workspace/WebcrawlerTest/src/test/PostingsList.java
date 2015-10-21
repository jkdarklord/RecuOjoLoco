package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public class PostingsList implements Serializable
{
	private int nextList;
	private long maxListSize;
	
	public class PostingsListElement implements Serializable
	{
		public int docID;
		public int termID;
		public int df;
		
		public String toString()
		{
			return docID + " " + termID + " " + df;
		}
	}
	
	public transient Vector<PostingsListElement> postings;
	public transient int actualList;
	
	public PostingsList()
	{
		postings = new Vector<PostingsListElement>();
		nextList = 1;
		actualList = 0;
		
		// We will estimate 1 third of memory for a postings list block
		// So we may be able to hold two at the same time
		//long elementLength = ObjectSizeFetcher.getObjectSize(new PostingsListElement());
		long elementLength = 20;
                long mem = Runtime.getRuntime().maxMemory();
		maxListSize = (mem / 3) / elementLength;
                System.out.println(maxListSize + "");
	}
	
	public PostingsListElement append(int docID, int termID, int df)
	{
		if(actualList == -1)
		{
			load(nextList - 1);
		}
		checkSize();
		
		PostingsListElement p = new PostingsListElement();
		p.docID = docID;
		p.termID = termID;
		p.df = df;
		postings.add(p);
		
		checkSize();
		
		return p;
	}
	
	public void checkSize()
	{
            //System.out.println("Actual: " + postings.size() + ", Max: " + maxListSize);
            if(postings.size() >= maxListSize)
            {
                    save();

                    if(actualList + 1 >= nextList)
                    {
                            actualList = nextList;
                            nextList++;
                            postings = new Vector<PostingsListElement>();
                    }
                    else
                    {
                            actualList = -1;
                            postings = null;
                    }
            }
	}
	
	public String toString()
	{
		return postings.toString();
	}
	
	public void save()
	{
		try
		{  
			FileOutputStream file = new FileOutputStream(actualList + ".ser");
			ObjectOutputStream output = new ObjectOutputStream(file);   
			output.writeObject(postings);
			output.close();
			
			//postings = new Vector<PostingsListElement>();
			//actualList = nextList;
			//nextList++;
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void load(int list)
	{
            save();
            if( list < nextList )
            {
                    try
                    {
                            FileInputStream file = new FileInputStream(list + ".ser");
                            ObjectInputStream input = new ObjectInputStream(file);
                            postings = (Vector<PostingsListElement>) input.readObject();
                            input.close();
                            actualList = list;
                    }catch (IOException | ClassNotFoundException e)
                    {
                            e.printStackTrace();
                            actualList = -1;
                            postings = null;
                    }
            }
	}
        
        public PostingsListElement elementAt(long index)
        {
            int block = (int)(index / maxListSize);
            int offset = (int)(index % maxListSize);
            
            if(actualList != block)
            {
                load(block);
            }
            
            if(actualList == -1)
            {
                return null;
            }
            return postings.get(offset);
        }
        
        public Vector<PostingsListElement> listFrom(long index)
        {
            Vector<PostingsListElement> result = new Vector<PostingsListElement>();
            PostingsListElement first = elementAt(index);
            
            if(first != null)
            {
                int i = 1;
                PostingsListElement next = elementAt(index + 1);
                while((next != null) && (first.termID == next.termID))
                {
                    result.add(next);
                    ++i;
                    next = elementAt(index + i);
                }
            }
            
            return result;
        }
}
