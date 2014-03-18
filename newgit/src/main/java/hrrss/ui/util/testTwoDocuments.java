package hrrss.ui.util;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;


//we pass the tests into the DocumentSimilarity, using the getCosineSimilarity to 
//compare the cosines of the two passed documents

public class testTwoDocuments{
	
	private static Vector<String> testList = new Vector<String>();
	private static Charset CHARSET = StandardCharsets.UTF_8;
	
	public static void main(String[] args) throws IOException, ParseException {
		
		double startTime =System.currentTimeMillis();
		
		String filename ="C:/test/testDoc.doc";
		
		// put in your job description
		String jobDescription ="C:/test/test1.txt";
		
		//put in a directory of resumes (cv's)
		// make sure your directory ends with "/"
		String directory = "C:/tests/";
		
		String jobDescr = readFile(jobDescription, CHARSET);;
		String fileContents = "";
		
		
		//String testDocConvertor = getDocToString(filename);
		
		//strings into a treemap to sort them. 
		//then get the highest value
		ArrayList<String>fileContentList = new ArrayList<String>();
		fileContentList.add(jobDescr);
		for(String item: readDirectory(directory)){
			fileContents = readFile(item, CHARSET);
			fileContentList.add(fileContents);
		}
		
		Boolean runInMemory=false;
		
		Map<Double, String> treeMap = new TreeMap<Double, String> (Collections.reverseOrder());
		treeMap.putAll(new CopyOfDocumentSimilarity().mainComponent(fileContentList, runInMemory));
		double endTime1 = System.currentTimeMillis();
		
		int topNumber =4;
		int count =0;
		System.out.println("Search for best matching documents");
		for(Map.Entry entry:treeMap.entrySet()){
			if(count<=topNumber){
				double i=(Double)(entry.getKey());
				
				System.out.println("Key: " + i + " Value= " + entry.getValue());
				//System.out.println("Key: " + entry.getKey());
				count++;
				
			}
		}
		
		double endTime = System.currentTimeMillis();
		
		double totalTime=(endTime-startTime)/(1000.00);
		System.out.println("total time was: "+totalTime);
		
	}
	
	
	
	static ArrayList<String> readDirectory(String directory){
		ArrayList<String> stringList = new ArrayList<String>();
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	stringList.add(directory + listOfFiles[i].getName());
		        //System.out.println("File " + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        //System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
		return stringList;
	}
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return encoding.decode(ByteBuffer.wrap(encoded)).toString();
			}
	static String getFileExtension(String fileName){
		
		String extension = "";
		int i = fileName.lastIndexOf(".");
		if(i>0){
			extension=fileName.substring(i+1);
		}
		return extension;
	}

	static String getDocToString (String fileName) throws IOException {
		File docFile=new File(fileName);  
		FileInputStream finStream=new FileInputStream(docFile.getAbsolutePath()); 
		HWPFDocument doc=new HWPFDocument(finStream);
		WordExtractor wordExtract=new WordExtractor(doc); 
		String [] dataArray =wordExtract.getParagraphText();
		String docContents = "";
		
		for(int i=0;i<dataArray.length;i++){
			//System.out.println(dataArray[i]);
			docContents = docContents + dataArray[i];
		}
		
		finStream.close();
		return docContents;

	}
	
}