package test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Dictionary implements Serializable
{
	public HashMap<String, DictionaryElement> map;
	
	public class DictionaryElement implements Serializable
	{
		public int df;
		public int id;
		public int postings;
		
		public DictionaryElement(int id)
		{
			this.id = id;
			this.df = 0;
			this.postings = 0;
		}
		
		public String toString()
		{
			return "ID: " + id + ", DOC FREQ:" + df + ", POST:" + postings;
		}
	}
	
	public int nextID;
	
	public Dictionary()
	{
		nextID = 1;
		map = new HashMap<String, DictionaryElement>();
	}
	
	private int addTerm(String term)
	{
		int id;
		
		if(!map.containsKey(term))
		{
			id = nextID;
			map.put(term, new DictionaryElement(id));
			nextID++;
		}
		else
			id = map.get(term).id;
		return id;
	}
	
	public int addOcurrence(String term)
	{
		int id = addTerm(term);
		++map.get(term).df;
		return id;
	}
	
	public String toString()
	{
		return map.toString();
	}
}
