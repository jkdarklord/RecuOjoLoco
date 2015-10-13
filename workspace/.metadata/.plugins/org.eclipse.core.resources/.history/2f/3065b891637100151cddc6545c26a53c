import java.util.TreeSet;
import java.util.Iterator;

public class PostingsList
{
	private TreeSet<Integer> postings;
	
	public PostingsList()
	{
		postings = new TreeSet<Integer>();
	}
	
	public Iterator<Integer> getIterator()
	{
		if( postings.isEmpty() )
			return null;
		else
			return postings.iterator();
	}
	
	public void add(Integer docId)
	{
		postings.add(docId);
	}
}
