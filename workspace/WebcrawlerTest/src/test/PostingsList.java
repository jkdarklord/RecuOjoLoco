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
	
	public PostingsList()
	{
		postings = new Vector<PostingsListElement>();
	}
	
	public PostingsListElement append(int docID, int termID, int df)
	{
		PostingsListElement p = new PostingsListElement();
		p.docID = docID;
		p.termID = termID;
		p.df = df;
		postings.add(p);
		return p;
	}
	
	public String toString()
	{
		return postings.toString();
	}
	
	public void save()
	{
		try
		{  
			FileOutputStream file = new FileOutputStream("0.ser");
			ObjectOutputStream output = new ObjectOutputStream(file);   
			output.writeObject(postings);
			output.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void load()
	{
		try
		{
			FileInputStream file = new FileInputStream("0.ser");
			ObjectInputStream input = new ObjectInputStream(file);
			postings = (Vector<PostingsListElement>) input.readObject();
			input.close();
		}catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
