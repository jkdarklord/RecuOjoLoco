package mainPackage;

import java.util.ArrayList;

public class DocumentList
{
	private ArrayList<String> array;
	public DocumentList()
	{
		array = new ArrayList<String>();
	}

	public int add(String path)
	{
		array.add(path);
		return array.size() - 1;
	}
}
