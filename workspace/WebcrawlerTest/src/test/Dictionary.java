import java.util.HashMap;

public class Dictionary
{
	private HashMap<String, DictionaryElement> map;
	
	private class DictionaryElement
	{
		public int count;
		public PostingsList postings;
		
		public DictionaryElement()
		{
			count = 0;
			postings = new PostingsList();
		}
	}
	
	public Dictionary()
	{
		map = new HashMap<String, DictionaryElement>();
	}
	
	public void addTerm(String term)
	{
		if(!map.containsKey(term))
		{
			map.put(term, new DictionaryElement());
		}
	}
	
	public void addOcurrence(String term, int docId)
	{
		addTerm(term);
		++map.get(term).count;
		map.get(term).postings.add(docId);
	}
}
