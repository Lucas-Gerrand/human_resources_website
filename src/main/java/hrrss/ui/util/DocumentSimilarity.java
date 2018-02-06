package hrrss.ui.util;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.math3.linear.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.apache.lucene.store.FSDirectory;

public class DocumentSimilarity {
	
    private final Set<String> terms = new HashSet<String>();
    private final ArrayList<RealVector> vectorList = new ArrayList<RealVector>();
    //make a place to store the vectors
    private final File DIRECTORY_LOCATION= new File("/home/roman/test");
    private final String contentField = "CONTENT";
    
    @SuppressWarnings("deprecation")
    private Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_CURRENT);

    // Set the type of directory
    public Directory getDirectory(Boolean runInMemory) throws IOException{
    	if(runInMemory){
    		Directory directory=new RAMDirectory();
    		return directory;
    	}
    	else{
    		Directory directory = FSDirectory.open(DIRECTORY_LOCATION);	
    		return directory;
    	}
    	
    }
    public HashMap<Double, String> mainComponent(ArrayList<String>docs, boolean runInMemory) throws IOException, ParseException {
    	
    	
    	
    	
    	// Step 1: Create the index
    	//Directory directory = FSDirectory.open(DIRECTORY_LOCATION);	
    	
    	Directory directory= createIndex(docs, runInMemory);
    	//Directory directory= getDirectory(runInMemory);
    	//directory = createIndex(docs, runInMemory);
    	IndexReader reader = DirectoryReader.open(directory);
    	
    	// Step 2: Get the term frequencies. Store these in a map
        ArrayList<Map> docTerms=new ArrayList<Map>();
        for(int i=0;i<docs.size();i++){
        		docTerms.add(getTermFrequencies(reader, i));
        }
        
        // Step 3: Put the documents into vectors
        
        for(Map s:docTerms){
        	vectorList.add(toRealVector(s));
        }
        
        // Step 4: Get the cosine similarity
        // This is defined as the dot product of v1 and v2, then normalize the denominator v1 and then v2
        // Or simply (v1.dotProduct(v2)) / (v1.getNorm() * v2.getNorm());
    	
        HashMap<Double, String>docCosine = new HashMap<Double, String>();
        double cosineResult =0;
        
        for(int j=0;j<vectorList.size();j++){
        	cosineResult=(vectorList.get(0).dotProduct(vectorList.get(j))) / 
        			(vectorList.get(0).getNorm() * vectorList.get(j).getNorm());
        	docCosine.put(cosineResult, docs.get(j));
        }
        analyzer.close();
        
        String searchString = "engineer";
        //System.out.println("Search for anything containing: "+ searchString);
        
        
        ArrayList<String>tempList = new ArrayList<String>();
        tempList.addAll(search(searchString));
        for(int i =0;i<tempList.size();i++){
        	System.out.println(i+1 + ":-: " + tempList.get(i));
        }
        // Step 5: Close the reader and return the map of documents and their respective cosine similarity score
        
        directory.close();
        reader.close();
        
        return docCosine;
                
    }
    
    @SuppressWarnings("deprecation")
    public Directory createIndex(ArrayList<String>docs, boolean runInMemory) throws IOException {
    	//  Can also create a directory on the machine. Directory index = FSDirectory.open(new File("C:/JobDescriptionIndex"));
        // Various types of analyzers available. Simple Analyzer is the most efficent
    	// Configure the writer and add the documents
    	
    	Directory directory= getDirectory(runInMemory);
    	
    	//FSDirectory directory = FSDirectory.open(DIRECTORY_LOCATION);
    	IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_CURRENT,
                analyzer);
        IndexWriter writer = new IndexWriter(directory, iwc);
        //for testing purposes delete everything in the index. 
        //once in development mode this needs to update (ADD FILES)
        
        writer.deleteAll();
        for(int i =0;i<docs.size();i++){
        	addDocument(writer, docs.get(i),Integer.toString(i));
        }
        
        //Term term = new Term("FIELDPATH", "0");
        //System.out.println("term field is: " + term.field() + " term text is: " + term.text());
        //writer.deleteDocuments(term);
        writer.commit();
        writer.close();
        
        return directory;
    }

    public ArrayList<String> search(String userSearch) throws IOException, ParseException{
    	
    	Directory directory = FSDirectory.open(DIRECTORY_LOCATION);	
    	int numberOfHits = 5;
    	Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_CURRENT);
    	//FSDirectory directory = FSDirectory.open(DIRECTORY_LOCATION);
    	IndexReader reader = DirectoryReader.open(directory);
    	IndexSearcher searcher = new IndexSearcher(reader);
    	Query q = new QueryParser(Version.LUCENE_CURRENT, contentField, analyzer).parse(userSearch);
    	TopScoreDocCollector collector = TopScoreDocCollector.create(numberOfHits, true);
    	searcher.search(q, collector);
    	ScoreDoc[] hits=collector.topDocs().scoreDocs;
    	ArrayList<String>results = new ArrayList<String>();
		for(int j=0;j<hits.length;j++){
			int docId=hits[j].doc;
			Document d=searcher.doc(docId);
			//System.out.println((j+1) + " " + d.get(contentField));
			results.add(d.get(contentField));
		}
		return results;
    }
    public void deleteIndex(boolean runInMemory) throws IOException{
    	Directory directory = getDirectory(runInMemory);
    	//FSDirectory directory = FSDirectory.open(DIRECTORY_LOCATION);
    	IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_CURRENT,
                analyzer);
        IndexWriter writer = new IndexWriter(directory, iwc);
        writer.deleteAll();
        writer.close();
    }
    
    public static final FieldType TYPE_STORED = new FieldType();
    //It is cruicial that the field type store term vectors is set to true. This enables the 
    static {
        TYPE_STORED.setIndexed(true);
        TYPE_STORED.setTokenized(true);
        TYPE_STORED.setStored(true);
        TYPE_STORED.setStoreTermVectors(true);
        TYPE_STORED.setStoreTermVectorPositions(true);
        TYPE_STORED.freeze();
    }

    public void addDocument(IndexWriter writer, String content, String path) throws IOException {
        Document doc = new Document();
        Field field1 = new Field("FIELDPATH", path, TYPE_STORED);
        Field field = new Field(contentField, content, TYPE_STORED);
        doc.add(field1);
        doc.add(field);
        writer.addDocument(doc);
    }


    public Map<String, Integer> getTermFrequencies(IndexReader reader, int docId)
            throws IOException {
    	//search through the document with docID in the content fields.
    	//note that in Lucene 4.0 we need to convert to string
        Terms vector = reader.getTermVector(docId, "CONTENT");
        TermsEnum termsEnum = null;
        termsEnum = vector.iterator(termsEnum);
        Map<String, Integer> frequencies = new HashMap<String, Integer>();
        BytesRef text = null;
        while ((text = termsEnum.next()) != null) {
            String term = text.utf8ToString();
            int freq = (int) termsEnum.totalTermFreq();
            frequencies.put(term, freq);
            terms.add(term);
        }
        return frequencies;
    }
    public RealVector toRealVector(Map<String, Integer> map) {
    	//getL1norm returns the linear norm factor of this vector's values (ie, the sum of its values
        RealVector docVector = new ArrayRealVector(terms.size());
        int i = 0;
        for (String term : terms) {
            int value = map.containsKey(term) ? map.get(term) : 0;
            docVector.setEntry(i++, value);
        }
        return (RealVector) docVector.mapDivide(docVector.getL1Norm());
    }

	
}