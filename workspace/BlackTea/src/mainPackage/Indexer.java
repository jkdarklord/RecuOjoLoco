package mainPackage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

import mainPackage.Dictionary.DictionaryElement;
import mainPackage.PostingsList.PostingsListElement;

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
import java.util.Map;
import javax.swing.JOptionPane;
import static mainPackage.MyCrawler.directory;

public class Indexer implements Serializable
{
        // Listas de documentos analizados
	public HashMap<String, Integer> docList;            // Mapeo path -> id de documentos
	public HashMap<Integer, String> invertedDocList;    // Mapeo id -> path de documentos
        private int nextID;                                 // Siguiente id de documento disponible
        
	public PostingsList pList;                          // Lista de postings
	public Dictionary wDict;                            // Diccionario de palabras
        public WeightVectors[] weights;                     // Arreglo de vectores de peso, uno para cada documento
	
	public Indexer()
	{
            // Constructor, crea la lista de documentos, la lista inversa, la lista de postings y el diccionario
		docList = new HashMap<String, Integer>();
		invertedDocList = new HashMap<Integer, String>();
		pList = new PostingsList();
		wDict = new Dictionary();
		nextID = 0;
	}
	
        /*
         * Parsea individualmente un documenta, agregando sus ocurrencias en la lista de postings
         * Tambien mantiene la cantidad de apariciones y guarda estas como tf para cada elemento
         */
	public void parseDocument(String path) throws FileNotFoundException
	{
                //Si el documento no ha sido parseado con anterioridad
		if(!docList.containsKey(path))
		{
                    // Si no es el seed index lo procesa
                    if(!path.contains("seedIndex.txt")){
                        File f = new File(path);
			Scanner sc = new Scanner(f);
			sc.useDelimiter("[^a-zA-Z0-9\\'\\-]");
			
                        // Agrega el documento a la lista de documentos
			int actualID = nextID;
			docList.put(path, actualID);
			invertedDocList.put(actualID, path);
			nextID++;
			
                        // Crea un diccionario local para el documento y la lista de postings respectivos
			String word;
			HashMap<String, PostingsListElement> localDict = new HashMap<String, PostingsListElement>();
                        HashMap<String, Long> posList = new HashMap<String, Long>();
			
                        int i=0;
                        // Para cada palabra del documento
			while(sc.hasNext())
			{
                                // Pone la palabra en minuscula y si no esta vacia la procesa
                                word = sc.next().toLowerCase();
				if(!word.isEmpty())
				{
                                        // Incrementa el contador de palabras del documento y lo agrega al diccionario global
                                        i++;
					int id = wDict.addOcurrence(word);
					// Si no esta contenido en el diccionario local, lo agrega
					if(!localDict.containsKey(word))
					{
                                                // QUE HACE ESTO?
                                                posList.put(word, pList.actualList * pList.maxListSize + pList.postings.size());
						PostingsListElement p = pList.append(actualID, id, 1);
						localDict.put(word, p);
					}
					else
					{
                                                // Sino incrementa su contador
						localDict.get(word).tf += 1;
					}
				}
			}
                        // Cierra el scanner e imprime el documento procesado
			sc.close();
                        System.out.println(path);
                        
                        // Actualizar postinglist con valores finales de tf
                        // Itera sobre el diccionario local, actualizando en la posicion donde se guarda
                        // el elemento en la postingsList el tf
                            for(String key : localDict.keySet())
                            {
                                    if(localDict.get(key) != null)
                                    {
                                        pList.elementAt(posList.get(key)).tf = localDict.get(key).tf;
                                    }
                            }
                        }
		}
	}
	
        /*
         * Retorna el id de un archivo a partir de su path absoluto
         */
	public int fileID(String path)
	{
		int res = 0;
		if(docList.containsKey(path))
			res = docList.get(path);
		return res;
	}
	
        /*
         * Retorna el path absoluto de un archivo a partir de su id
         */
	public String pathFromDocID(int docID)
	{
		String res = null;
		if(invertedDocList.containsKey(docID))
			res = invertedDocList.get(docID);
		return res;
	}
    
    /*
     * Quicksort de listas de Postings, sorteando por ID
     */
    public static PostingsListElement[] sort(PostingsListElement[] inputArr) {
        return quickSort(0, inputArr.length - 1, inputArr);
    }
 
    /*
     * Metodo utilizado para el quicksort
     */
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
    
    /*
     * Quicksort para sortear resultados por coseno
     */
    public PostingsListElement[] sortByCosine(String [] tokens, PostingsListElement[] inputArr) {
        return quickSortCosine(0, inputArr.length - 1, inputArr,tokens);
    }
 
    /*
     * Metodo utilizado para el quicksort
     */
    private  PostingsListElement[] quickSortCosine(int lowerIndex, int higherIndex, PostingsListElement[] array,String [] tokens) {
        WeightVectors queryVector = queryTFIDF(tokens);
        int i = lowerIndex;
        int j = higherIndex;
        PostingsListElement pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
        while (i <= j) {
            
            while (weights[array[i].docID].getCosineSimilarity(queryVector) < weights[pivot.docID].getCosineSimilarity(queryVector)) {
                i++;
            }
            while (weights[array[j].docID].getCosineSimilarity(queryVector) > weights[pivot.docID].getCosineSimilarity(queryVector)) {
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
    
    
    /*
     * Metodo utilizado para el quicksort
     */
    private static void exchangeElements(int i, int j, PostingsListElement[] array) {
        PostingsListElement temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /*
     * Metodo utilizado para el quicksort
     */
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
    
    /*
     * Metodo utilizado para el quicksort
     */
    public static PostingsListElement[] trimArray(PostingsListElement[] array, int newLength){
        PostingsListElement[] arr = new PostingsListElement[newLength];
        for(int i=0;i<newLength;i++){
            arr[i] = array[i];
        }
        return arr;
    }
    
    /*
     * Metodo utilizado para el quicksort
     */
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
    
    /*
     * Ejecuta el ultimo paso del algoritmo BSBI, realizando un merge sobre todos los bloques
     */
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
            // Mientras se tenga mas de un bloque se debe hacer el merge
            while(totalBlocks > 1)
            {
                pList.maxListSize *= 2;
                for(int i = 0; i < totalBlocks; i += 2)
                {
                    // Cargar los dos siguientes bloques
                    pList.load(i);
                    PostingsListElement[] l1 = new PostingsListElement[pList.postings.size()];
                    pList.postings.toArray(l1);
                    pList.load(i + 1);
                    PostingsListElement[] l2 = new PostingsListElement[pList.postings.size()];
                    pList.postings.toArray(l2);
                    
                    // Escribir el l1 la concatenacion de l1 + l2
                    l1 = Arrays.copyOf(l1, l1.length + l2.length);
                    System.arraycopy(l2, 0, l1, l1.length, l2.length);
                    pList.postings = null;
                    
                    // Poner en la posicion i / 2 el nuevo bloque
                    pList.actualList = i / 2;
                    if(l2 != null)
                    {
                    	// Hacer sort de los dos bloques
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
            
            // Solo queda una lista
            pList.nextList = 1;
    	}
    	else
    	{
                // Aunque sea un bloque, es necesario ordenarlo de todas formas
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
    
    /*
     * Actualizar el diccionario, agregando para cada termino la posicion de la postingsList donde este empieza
     */
    public void updateIndex()
    {
    	HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();
    	
    	int previous = -1;
    	for(int i = 0; i < pList.postings.size(); ++i)
    	{
                // Cuando el id del elemento cambie, significa que es un nuevo termino
                // Esta es su posicion inicial en la lista de postings
    		if( pList.postings.get(i).termID != previous )
    		{
    			temp.put(pList.postings.get(i).termID, i);
    		}
    		previous = pList.postings.get(i).termID;
    	}
    	
        // Actualizar el diccionario con las localizaciones
    	Collection<DictionaryElement> x = wDict.map.values();
    	Iterator itr = x.iterator();
    	while(itr.hasNext())
    	{
    		DictionaryElement actual = (DictionaryElement) itr.next();
    		actual.postings = temp.get(actual.id);
    	}
    }
    
    /*
     * Retorna la lista de postings de un termino
     */
    public PostingsListElement[] getPostings(String term)
    {
    	if(!wDict.map.containsKey(term))
    		return null;
    	int postingsLoc = wDict.map.get(term).postings;
    	PostingsListElement[] l1 = new PostingsListElement[pList.postings.size()];
    	return pList.listFrom(postingsLoc);
       
    }
    
    /*
     * Retorna el DF de un termino
     */
    public int getTermDF(String term){
        if(!wDict.map.containsKey(term))
    		return 0;
    	return wDict.map.get(term).df;
    }
    
    /*
     * Retorna el TF de un termino en un documento
     */
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
            return 0;
        }
        
    }
    
    /*
     * Retorna el TF de un termino en una lista de terminos
     */
    public int manuallyGetTermTF(String term, String [] queryTokens){
        int count = 0;
        for(int i=0;i<queryTokens.length;i++){
            if(queryTokens.equals(term)){
                count++;
            }
        }
        return count;
    }
    
    /*
     * Calcula todos los vectores de pesos
     */
    public void calculateWeights(){
        weights=new WeightVectors[invertedDocList.size()];
        
        for(int i=0;i<weights.length;i++){
            weights[i] = calculateTFIDFWeightVector(i);
        }
    }
    
    /*
     * Retorna el vector de pesos de un documento
     */
    public WeightVectors calculateTFIDFWeightVector(int docID){
        //JOptionPane.showMessageDialog(null,"Voy a procesar el archivo " + pathFromDocID(docID));
        WeightVectors TFIDF =new WeightVectors(wDict.map.size());
        Iterator it = wDict.map.entrySet().iterator();
    
            for(int j=0;j<wDict.map.size()-1;j++){
                Map.Entry pair = (Map.Entry)it.next();
                String actualTerm = (String )pair.getKey();
                double df = getTermDF(actualTerm);
                double tf = getTermTF(actualTerm,docID);
                
                // Calcular el peso TF-IDF del termino j
                if(tf>0){
                    TFIDF.termWeight[j]= ((1 + Math.log10(tf))*(Math.log10(invertedDocList.size()/df)));
                }
                else{
                    TFIDF.termWeight[j]= 0;
                }
                
            }
            TFIDF.normalizeVector();
            return TFIDF;
    }
    
    /*
     * Retorna el vector de pesos tf-idf de un vector de terminos
     */
    public WeightVectors queryTFIDF (String [] queryTokens){
        WeightVectors TFIDF =new WeightVectors(wDict.map.size());
        Iterator it = wDict.map.entrySet().iterator();
        for(int j=0;j<wDict.map.size()-1;j++){
                Map.Entry pair = (Map.Entry)it.next();
                String actualTerm = (String)pair.getKey();
                double df = getTermDF(actualTerm);
                double tf = manuallyGetTermTF(actualTerm,queryTokens);
                if(tf>0){
                    TFIDF.termWeight[j]= ((1 + Math.log10(tf))*(Math.log10(invertedDocList.size()/df)));
                }
                else{
                    TFIDF.termWeight[j]= 0;
                }
        }
        TFIDF.normalizeVector();
        return TFIDF;
    }
    
    /*
     * Escribe los pesos de los vectores en un archivo
     * Utilizar para la depuracion de los vectores
     */
    public void writeWeights(){
        try{
        File file = new File(directory, "pesos.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        
        for(int i=0;i<weights.length;i++){
            String line="";
            for(int j=0;j<wDict.map.size();j++){
                line += weights[i].termWeight[j] + " ";
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
