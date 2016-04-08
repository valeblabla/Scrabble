import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class Dictionary {
	private ArrayList<String> words = new ArrayList<String>();
	
	public Dictionary(){
		File dictionary=new File("it.txt");
	    try {
	    	LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(dictionary)));
	    	String lineRead = null;									 
	    	while((lineRead = reader.readLine()) != null) {
	    		words.add(lineRead);
	    	}
	    	reader.close();
	    } catch (FileNotFoundException e1) {
	    	// TODO Auto-generated catch block
	    	e1.printStackTrace();
	    } catch (IOException e1) {
	    	// TODO Auto-generated catch block
	    	e1.printStackTrace();
	    }

	}
	
	public int getScore(String word){
		int score=0;
	    File letters;
	    		
	    	for(int i=0;i<word.length();i++){
	    		String currentLetter=""+word.charAt(i);
	    		letters=new File("initializingLetters.txt");
	    	    try {
	    	    	LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(letters)));
	    	    	String lineRead = null;	
	    	    	while((lineRead = reader.readLine()) != null) {
	    		
	    			if((lineRead.compareToIgnoreCase(currentLetter)==0)){
	    				score+=Integer.parseInt(reader.readLine());
	    				break;
	    			}
	    		}
	    	    reader.close();
	    	    }
	    	
	    	    catch (FileNotFoundException e1) {
	    	    // TODO Auto-generated catch block
	    	    e1.printStackTrace();
	    	    } catch (IOException e1) {
	    	    // TODO Auto-generated catch block
	    	    e1.printStackTrace();
	    	    }
	    	}
		return score;
	}

	public ArrayList<String> getWords() {
		return words;
	}
}
