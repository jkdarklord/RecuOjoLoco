    package mainPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import mainPackage.PostingsList.PostingsListElement;;

public class IndexerControler
{
	private static String indexerPath = Paths.get("").toAbsolutePath().toString() + "\\idx.ser";
	private static String workDirectory = Paths.get("").toAbsolutePath().toString() + "\\CrawlerFiles\\";
	
	public static Indexer idxr;
	
	public static void initialize()
	{
		idxr = loadIndexer();
	}
	
	public static void run()
	{
            // Carga el directorio de trabajo
            File workingFolder = new File(workDirectory);
            // Variables utilizadas por los algoritmos
            File fi;
            int i,j;
            
            // Filtro utilizado al elegir documentos a procesar: solo .txt o directorios
            FilenameFilter textFilter = new FilenameFilter()
            {
                    public boolean accept(File dir, String name)
                    {
                            String lowercaseName = name.toLowerCase();
                            if (dir.isDirectory() || lowercaseName.endsWith(".txt"))
                            {
                                return true;
                            } else
                            {
                                return false;
                            }
                    }
            };
            
            // Agrega todas las direcciones contenidas en el directorio de trabajo que cumplen con el filtro a la lista
            ArrayList<File> fileList = new ArrayList<File>();
            File temp[] = workingFolder.listFiles(textFilter);
            for(i = 0; i < temp.length; i++)
            {
                fileList.add(temp[i]);
            }
            
            try
            {
                // Para cada documento de la lista
                for(i = 0; i < fileList.size(); i++)
                {
                    fi = fileList.get(i);
                    if(fi.isDirectory())
                    {
                        // Si es directorio agrega su contenido que cumplen con el filtro al final de la lista
                        temp = fi.listFiles(textFilter);
                        for(j = 0; j < temp.length; j++)
                        {
                            fileList.add(temp[j]);
                        }
                        System.out.println("Dir: " + fi.getPath());
                    }
                    else
                    {
                        // Si es un .txt entonces lo indexa
                        idxr.parseDocument(fi.getPath());
                    }
                }
            } catch (FileNotFoundException e)
            {
                    e.printStackTrace();
                    return;
            }
            
            // Realiza el merge, calcula los pesos, los escribe y guarda
            idxr.BSBIMerge();
            idxr.calculateWeights();
            idxr.writeWeights();
            idxr.pList.save();
            saveIndexer(idxr);
	}
	
	private static Indexer loadIndexer()
	{
                // Crea un nuevo indexer y abre el archivo default idx.ser
		Indexer idxr = new Indexer();
		File docListFile = new File(indexerPath);
                // Si existe, carga el objeto indexer desde ahi, sino crea un nuevo
		if(docListFile.exists())
		{
			try
			{
				FileInputStream file = new FileInputStream(indexerPath);
				ObjectInputStream input = new ObjectInputStream(file);
				idxr = (Indexer) input.readObject();
				input.close();
				idxr.pList.postings = new ArrayList<PostingsListElement>();
				idxr.pList.actualList = -1;
			}catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			idxr = new Indexer();
		}
		// Retorna el indexer generado
		return idxr;
	}
	
	private static void saveIndexer(Indexer idxr)
	{
                // Guarda el indexer obtenido en la direccion default: idx.ser
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
        
        public static void deleteSerFiles(){
            // Borra los documentos con terminacion .ser utilizados por el programa
            File folder = new File(Paths.get("").toAbsolutePath().toString());
            File fList[] = folder.listFiles();
            for (int i = 0; i < fList.length; i++) {
                File pes = fList[i];
                if (pes.getName().endsWith(".ser")) {
                    pes.delete();
                }
            }
        }
        
        public static PostingsListElement[] searchQueryOnIndex(String query){
            String [] tokens = query.split("[^a-zA-Z0-9\\'\\-]");
            //Lo que en realidad deberia decir, es algo como
            PostingsListElement[] results = idxr.getPostings(tokens[0]);
            for(int i=1;i<tokens.length;i++){
                //Lo que en realidad deberia decir, es algo como
                results = Indexer.intersectArrays(results, idxr.getPostings(tokens[i]));
            }
            return results;
        }
        
        public static PostingsListElement[] searchQueryOnIndex2(String query){
            String [] tokens = query.split("[^a-zA-Z0-9\\'\\-]");
            //Lo que en realidad deberia decir, es algo como
            PostingsListElement[] results = idxr.getPostings(tokens[0]);
            for(int i=1;i<tokens.length;i++){
                //Lo que en realidad deberia decir, es algo como
                results = Indexer.intersectArrays(results, idxr.getPostings(tokens[i]));
            }
            if(results!=null){
                results = idxr.sortByCosine(tokens, results);
            }
            return results;
        }
        
	
}
