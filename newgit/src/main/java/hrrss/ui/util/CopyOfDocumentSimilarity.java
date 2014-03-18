package hrrss.ui.util;

import hrrss.ui.applicant.cv.document.Download;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyOfDocumentSimilarity {
	
    private final Set<String> terms = new HashSet<String>();
    private final ArrayList<RealVector> vectorList = new ArrayList<RealVector>();
    //make a place to store the vectors
    private final File DIRECTORY_LOCATION= new File("/home/roman/test");
    private final String contentField = "CONTENT";
    private boolean runInRam=false;
    
    final Logger logger = LoggerFactory.getLogger(CopyOfDocumentSimilarity.class);
    
    @SuppressWarnings("deprecation")
    private Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_CURRENT);

    // Set the type of directory
    public Directory getDirectory(Boolean runInMemory) throws IOException{
    	runInRam = runInMemory;
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
    	
    	Directory firstdirectory= getDirectory(runInMemory);
    	
    	//Directory directory= getDirectory(runInMemory);
    	//directory = createIndex(docs, runInMemory);
    	Directory directory = createIndex(firstdirectory, docs, runInMemory);
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
        
        
        // Step 5: Close the reader and return the map of documents and their respective cosine similarity score
        
        
        firstdirectory.close();
    	
        directory.close();
        reader.close();
        
        return docCosine;
                
    }
    
    @SuppressWarnings("deprecation")
    public Directory createIndex(Directory directory, ArrayList<String>docs, boolean runInMemory) throws IOException {
    	//  Can also create a directory on the machine. Directory index = FSDirectory.open(new File("C:/JobDescriptionIndex"));
        // Various types of analyzers available. Simple Analyzer is the most efficent
    	// Configure the writer and add the documents
    	
    	
    	
    	//FSDirectory directory = FSDirectory.open(DIRECTORY_LOCATION);
    	IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_CURRENT,
                analyzer);
        IndexWriter writer = new IndexWriter(directory, iwc);
        //for testing purposes delete everything in the index. 
        //once in development mode this needs to update (ADD FILES)
        
        writer.deleteAll();
        logger.info("the number of docs: " + docs.size());
        Pattern title = Pattern.compile("\\[Title:(.*?)\\]", Pattern.DOTALL);
    	Pattern content = Pattern.compile("\\[Content:(.*?)\\]", Pattern.DOTALL);
    	Pattern qualifications = Pattern.compile("\\[Qualifications:(.*?)\\]", Pattern.DOTALL);
    	for(int i=0;i<docs.size();i++){
    		Matcher m = title.matcher(docs.get(i));
    		Matcher n = content.matcher(docs.get(i));
    		Matcher o = qualifications.matcher(docs.get(i));
    		
    		if(m.find() && n.find() && o.find()){
    			logger.info("there is a match! Add to index...");
    			addDocument(writer,m.group(1), n.group(1), o.group(1));
    		}
    		else{
    			logger.info("there was no match! ");
    		}
    	}
        
        
        //Term term = new Term("FIELDPATH", "0");
        //System.out.println("term field is: " + term.field() + " term text is: " + term.text());
        //writer.deleteDocuments(term);
    	logger.info("num docs in index: " + writer.numDocs());
        
        writer.commit();
        writer.close();
        
        return directory;
    }

    public ArrayList<String> search(String userSearch) throws IOException, ParseException{
    	
    	Directory directory = getDirectory(runInRam);
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

    public void addDocument(IndexWriter writer, String title, String content, String qualifications) throws IOException {
        Document doc = new Document();
        Field field = new Field("title", title, TYPE_STORED);
        Field field1 = new Field("content", content, TYPE_STORED);
        Field field2 = new Field("qualifications", qualifications, TYPE_STORED);
        field1.setBoost((float) 1);
        field2.setBoost((float) 1);
        field.setBoost((float) 3);
        
        doc.add(field);
        doc.add(field1);
        doc.add(field2);
        writer.addDocument(doc);
      
    }


    public Map<String, Integer> getTermFrequencies(IndexReader reader, int docId)
            throws IOException {
    	//search through the document with docID in the content fields.
    	//note that in Lucene 4.0 we need to convert to string
        Terms titleVector = reader.getTermVector(docId, "title");
        Terms contentVector = reader.getTermVector(docId, "content");
        Terms qualificationsVector = reader.getTermVector(docId, "qualifications");
        
        TermsEnum termsEnum = null;
        TermsEnum termsEnumTitle = null;
        TermsEnum termsEnumQualifications = null;
        
        termsEnum = contentVector.iterator(termsEnum);
        termsEnumTitle = titleVector.iterator(termsEnumTitle);
        termsEnumQualifications = qualificationsVector.iterator(termsEnumQualifications);
        
        
        Map<String, Integer> frequencies = new HashMap<String, Integer>();
        BytesRef text = null;
        //content field
        while (((text = termsEnum.next()) != null)){
            String term = text.utf8ToString();
            int freq = (int) termsEnum.totalTermFreq();
            
            frequencies.put(term, freq);
            
            terms.add(term);
        }
        
      //title field
        text = null;
        int changeFreq = 5;
        while (((text = termsEnumTitle.next()) != null)){
            String term = text.utf8ToString();
            int freq = (int) termsEnumTitle.totalTermFreq();
            freq = freq*changeFreq; 
            int newFreq = 0;
            if(frequencies.containsKey(term)){
            	for(Map.Entry<String, Integer>entry:frequencies.entrySet()){
            		newFreq = entry.getValue()+freq;
            		entry.setValue(newFreq);
            	}
            }
            else{
            frequencies.put(term, freq);
            }
            
            terms.add(term);
        }
      //qualifications field
        text = null;
        while (((text = termsEnumQualifications.next()) != null)){
            String term = text.utf8ToString();
            int freq = (int) termsEnumQualifications.totalTermFreq();
            //freq = freq*30;
            int newFreq = 0;
            if(frequencies.containsKey(term)){
            	for(Map.Entry<String, Integer>entry:frequencies.entrySet()){
            		newFreq = entry.getValue()+freq;
            		entry.setValue(newFreq);
            	}
            }
            else{
            frequencies.put(term, freq);
            }
            terms.add(term);
        }
       
        /*for(Map.Entry<String, Integer>entry:frequencies.entrySet()){
        	System.out.println("The term is: " + entry.getKey()+ " " + entry.getValue());
        }   
        */
        return frequencies;
    }
    public RealVector toRealVector(Map<String, Integer> map) {
    	//getL1norm returns the linear norm factor of this vector's values (ie, the sum of its values
        
    	Collection<String>noDuplicates = new HashSet<String>(terms);
    	
    	RealVector docVector = new ArrayRealVector(noDuplicates.size());
        int i = 0;
        for (String term : noDuplicates) {
            int value = map.containsKey(term) ? map.get(term) : 0;
            docVector.setEntry(i++, value);
        }
        return (RealVector) docVector.mapDivide(docVector.getL1Norm());
    }

	
}