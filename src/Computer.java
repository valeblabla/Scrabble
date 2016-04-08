import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Computer {
	private String name;
	private int score;
	private ArrayList<Tile> letters;
	private int countTurn;
	private ArrayList<revertCPUTiles> revertOriginal;
	private int difficulty;
	
	//Costruttore
	public Computer(ArrayList<Tile> extractedLetters){
		this.score=0;
		this.name="CPU";
		this.letters=extractedLetters;
		//Cambia l'aspetto delle tiles per tenerle nascoste all'utente
		for(int i=0; i<letters.size(); i++ ){
			this.letters.get(i).setPath("img/tiles/tilesFlat/big/cpu.png");
			this.letters.get(i).setEnabled(false);
		}
		countTurn=0;
		revertOriginal=new ArrayList<revertCPUTiles>();
		this.difficulty=2;
	}


	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public ArrayList<Tile> getLetters() {
		return letters;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<revertCPUTiles> getRevertOriginal() {
		return revertOriginal;
	}

	public int getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}


	public void setLetters(ArrayList<Tile> letters) {
		this.letters = letters;
		//Cambia l'aspetto delle tiles per tenerle nascoste all'utente
		for(int i=0; i<7; i++ ){
			this.letters.get(i).setPath("img/tiles/tilesFlat/big/cpu.png");
			this.letters.get(i).setEnabled(false);
		}
	}
	
	public void setLetter(Tile newTile){
		//Cambia l'aspetto della tile per tenerla nascoste all'utente
		newTile.setPath("img/tiles/tilesFlat/big/cpu.png");
		newTile.setEnabled(false);
		this.letters.add(newTile);
				
	}
	
	public boolean checkWordCPU (String word){
		String reverseWord="";
		boolean found=false;
		    for (int k = word.length() - 1; k >= 0; k--){
		    	reverseWord = reverseWord + word.charAt(k);
		    }      						 
		    File dictionary=new File("it.txt");
		    try {
		    	LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(dictionary)));
		    	String lineRead = null;									 
		    	while((lineRead = reader.readLine()) != null) {
		    		if((lineRead.compareToIgnoreCase(word)==0)||(lineRead.compareToIgnoreCase(reverseWord)==0)){
		    			//PAROLA TROVATA
		    			found=true;
		    			break;
		    		}
		    	}
		    	reader.close();
		    } catch (FileNotFoundException e1) {
		    	// TODO Auto-generated catch block
		    	e1.printStackTrace();
		    } catch (IOException e1) {
		    	// TODO Auto-generated catch block
		    	e1.printStackTrace();
		    }
		  return found;
	}
	

	private boolean checkHorizontal(Board board, int startRow, int startCol, String currentWord, TileBoard currentBoardLetter){
		boolean flag=true;
		//Controllo se la parola può essere inserita nella board
		  if( (startCol<0) || (startCol+currentWord.length() - 1 > 14) ) 
			  flag=false;
		  if(flag && board.getCell(startRow, startCol).isOccupied())
			  flag=false;
		  //Controllo che se la parola fosse inserita non ai lati della board
		  //Non si "accodi" a parole già presenti, creandone quindi una senza significato
		  if(flag && startCol!=0 && startCol!=14 && startCol+currentWord.length()<15){
			  if(board.getCell(startRow, startCol-1).isOccupied() || board.getCell(startRow, startCol+currentWord.length() ).isOccupied()  )
				  flag=false;
			  
		  }
		  else {
			  if(flag && startCol==0)
				  if(board.getCell(startRow, startCol+currentWord.length()).isOccupied())
					  flag=false;
				  
			  if(flag && startCol==14)
				  if(board.getCell(startRow, startCol-1).isOccupied())
					  flag=false;
			  
			  if(flag && startCol+currentWord.length() >= 15)
				  if(board.getCell(startRow, startCol-1).isOccupied())
					  flag=false;
		  }

		  if(flag) {
			  for(int k=startCol; k<startCol+currentWord.length(); k++){
				  //System.out.println("Parola "+currentWord+" k "+k+" Col "+startCol+" "+board.getCell(k, startCol).isOccupied());
				  if(k!=currentBoardLetter.getY1()-1)
				  if(board.getCell(startRow, k).isOccupied() ){
					  //Errore:parola non ci sta nella riga
					  flag=false;
					  break;	
				  }
			  }
		  }	
		  return flag;
	}
	
	private boolean checkVertical(Board board, int startRow, int startCol, String currentWord, TileBoard currentBoardLetter){
		boolean flag=true;
		if( (startRow<0) || (startRow+currentWord.length()-1 > 14) )
			   flag=false;
		if(flag && board.getCell(startRow, startCol).isOccupied())
			flag=false;
		   
		if(flag && startRow!=0 && startRow!=14 && startRow+currentWord.length()<15){
			if(board.getCell(startRow-1, startCol).isOccupied() || board.getCell(startRow+currentWord.length(), startCol).isOccupied())
				flag=false;  
		 }
		   else {
			   if(flag && startRow==0)
				   if(board.getCell(startRow+currentWord.length(), startCol).isOccupied())
					   flag=false;
			   
			   if(flag && startRow+currentWord.length() == 15)
				   if(board.getCell(startRow-1, startCol).isOccupied())
					   flag=false;
			   
			   if(flag && startRow==14)
				   if(board.getCell(startRow-1, startCol).isOccupied())
					   flag=false;  
			}
		   
			if(flag){
				for(int k=startRow; k<startRow+currentWord.length(); k++){
					//System.out.println("Parola "+currentWord+" k "+k+" Col "+startCol+" "+board.getCell(k, startCol).isOccupied());
					if(k!=currentBoardLetter.getX1()-1)
					if(board.getCell(k, startCol).isOccupied() ){ //&& board.getCell(k, startCol).getLetter()!=currentBoardLetter.getLetter()){
						//Errore:parola non ci sta nella colonna
						flag=false;
						break;
					}
				}
			}
		return flag;
	}

	//Restituisce le parole del dizionario che possono essere usate per creare una parola a partire dalle lettere restituite da board.getAvailableLetters
	public ArrayList<PlaceWord> getDictWords(Board board, Box box) throws FileNotFoundException {
		
		Dictionary dict = new Dictionary();
		ArrayList<String> words = dict.getWords();	
		ArrayList<PlaceWord> wordsToPlace = new ArrayList<PlaceWord>();
		ArrayList<Tile> currentRack = new ArrayList<Tile>();
		ArrayList<TileBoard> lettersOnBoard = board.getAvailableLetters();
		TileBoard currentBoardLetter = null;
		boolean continuer = false, breaker = false;
		char missingLetter = '!';
		int startRow=0;
		int startCol=0;
		boolean reverseDir=false;
		
		for(int x = 0; x<words.size(); x++){
		   if(words.get(x).length()>8)		
		      continue;
		   
		   String currentWord=words.get(x);
		   currentWord=currentWord.toUpperCase();
		   continuer = false; 
		   breaker = false;
		   
		   //Controllo se è primo turno: se lo è non ho availableLetters e lettersOnBoard è vuoto
		   //per cui devo costruire una parola considerando SOLO le lettere del computer
		   if(lettersOnBoard.isEmpty()){
				currentRack.clear();
				currentRack.addAll(this.letters);
				int count=0;	
				for(int check = 0; check<currentWord.length(); check++){
					for(int y = 0; y<currentRack.size(); y++) {
					     if(currentRack.get(y).getLetter()==currentWord.charAt(check)) { 
					    	 count++; 
					    	 currentRack.remove(y); 
					    	 break; 
					     }
					  }
				   }
				if(count==currentWord.length() ){
					//Parola perfetta, computer ha tutte le lettere che la compongono
					wordsToPlace.add(new PlaceWord(currentWord, startRow, startCol,0,dict.getScore(currentWord),0, 0,0,false));
				 }
			}
		   
		   //NON è il primo turno
		   else { 
		   for(int i = 0; i<currentWord.length(); i++) {
		       for(int j = 0; j<lettersOnBoard.size(); j++){
		    	  currentBoardLetter=lettersOnBoard.get(j); 
				  if(currentWord.charAt(i)==currentBoardLetter.getLetter()){
					  
					  //Direzione Orizzontale
					  if(currentBoardLetter.getDirection()==0){
						  startRow = currentBoardLetter.getX1()-1;
						  startCol = currentBoardLetter.getY1() - 1 - i;
						  
						  boolean flag=checkHorizontal(board, startRow, startCol,currentWord, currentBoardLetter);
						  if(!flag) continue;
								  
						  breaker = true;
						  continuer=false;
						  currentBoardLetter=lettersOnBoard.get(j);
						  break;
					  }
					  
					  //Direzione verticale
					  else {
						   startRow = currentBoardLetter.getX1() - i -1;
						   startCol = currentBoardLetter.getY1() - 1;
						   boolean flag=checkVertical(board, startRow, startCol,currentWord, currentBoardLetter);
						   if(!flag) continue;
					
						   breaker = true;
						   continuer=false;
						   currentBoardLetter=lettersOnBoard.get(j);
						   break;
					  }
				  }
			   }
			   
			   if(breaker)
			      break;
		   }
		   
		   if(continuer)
			  continue; 
		   
		   currentRack.clear();
		   currentRack.addAll(this.letters);
		   
		   //Controllo di poter costruire la parola con le lettere del computer
		   int count=0;	
		   boolean flag=false;
		   for(int check = 0; check<currentWord.length(); check++){
			   for(int y = 0; y<currentRack.size(); y++) {
			     if(currentRack.get(y).getLetter()==currentWord.charAt(check)) { 
			    	 if(currentWord.charAt(check)==currentBoardLetter.getLetter())
			    		 	flag=true;
			    	 count++; 
			    	 currentRack.remove(y); 
			    	 break; 
			     }
			     else {
			    	 missingLetter=currentWord.charAt(check);
			     }
			  }
		   }
		   
		   if(count==currentWord.length() && flag){
			   //Parola perfetta, computer ha tutte le lettere che la compongono
			   wordsToPlace.add(new PlaceWord(currentWord, startRow, startCol,currentBoardLetter.getDirection(), dict.getScore(currentWord),0, currentBoardLetter.getX1()-1, currentBoardLetter.getY1()-1, reverseDir));
		   }
		   
		   if(count==currentWord.length()-1 && missingLetter==currentBoardLetter.getLetter() && !flag){
			   //Il computer ha le lettere per formare la parola ad eccezione di quella sulla board 
			   wordsToPlace.add(new PlaceWord(currentWord, startRow, startCol,currentBoardLetter.getDirection(),dict.getScore(currentWord),0, currentBoardLetter.getX1()-1, currentBoardLetter.getY1()-1, reverseDir));
		   }
		   
		 }
		   
		} //end main for loop
	
		if(wordsToPlace.size()==0){   
			throwAwayEnemyRack(box); 
			return wordsToPlace; 
		}
		else {
			if(this.difficulty==2)
				Collections.sort(wordsToPlace, new PlaceWord.ScoreComparator());
			else
				if(this.difficulty==0)
					Collections.sort(wordsToPlace, new PlaceWord.ScoreComparatorInverse());
			if(this.difficulty==1)
				Collections.sort(wordsToPlace, new PlaceWord.ScoreComparatorAlpha());
		    return wordsToPlace;	   
		}
	
	}
	
	
	public void throwAwayEnemyRack(Box box){
		boolean findA=false;
		boolean findE=false;
		boolean findI=false;
		boolean findO=false;
		
		for(int i=0; i<this.letters.size();i++){
			if(this.letters.get(i).getLetter()=='A' && findA) this.letters.get(i).setSelected(true);
			else
				if(this.letters.get(i).getLetter()=='A') findA=true;
				
			if(this.letters.get(i).getLetter()=='E' && findE) this.letters.get(i).setSelected(true);
			else
				if(this.letters.get(i).getLetter()=='E') findE=true;
			
			if(this.letters.get(i).getLetter()=='I' && findI) this.letters.get(i).setSelected(true);
			else
				if(this.letters.get(i).getLetter()=='I') findI=true;
			
			if(this.letters.get(i).getLetter()=='O' && findO) this.letters.get(i).setSelected(true);
			else
				if(this.letters.get(i).getLetter()=='O') findO=true;
			
			if(this.letters.get(i).getLetter()!='A' && this.letters.get(i).getLetter()!='E' && this.letters.get(i).getLetter()!='I' && this.letters.get(i).getLetter()!='O')
				this.letters.get(i).setSelected(true);
		}
		for(int i=0; i<letters.size(); i++ ){
			if(this.letters.get(i).isSelected()){
				this.letters.get(i).setEnabled(true);
				String letter="";
		  		letter+=this.letters.get(i).getLetter();
		  		File letters=new File("initializingLetters.txt");
			    try {
			    	LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(letters)));
			    	String lineRead = null;									 
			    	while((lineRead = reader.readLine()) != null) {
			    		if((lineRead.compareToIgnoreCase(letter)==0)){
			    			reader.readLine();
			    			this.letters.get(i).setPath(reader.readLine());
			    			break;
			    		}
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
		}
		this.setLetters(box.changeLetters(this.letters));
	}
	
	
	
	public boolean placeEnemyTile(int num, int row, int col, int dir, Board board, int missRow, int missCol){
		  boolean right=true;
		  if(row<0||row>14||col<0||col>14)
		    return false;
		
		  if(!board.getCell(row, col).isOccupied()) {
			  	if(!this.letters.get(num).isJolly()){  
			  		String originalPath="";
			  		board.getCell(row, col).setLetter(this.letters.get(num).getLetter());
			  		board.getCell(row, col).setValue(this.letters.get(num).getValue());
			  		board.getCell(row, col).setOccupied(true);
			  		String letter="";
			  		letter+=this.letters.get(num).getLetter();
			  		File letters=new File("coloredLetters.txt");
				    try {
				    	LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(letters)));
				    	String lineRead = null;									 
				    	while((lineRead = reader.readLine()) != null) {
				    		if((lineRead.compareToIgnoreCase(letter)==0)){
				    			originalPath=reader.readLine();
				    			board.getCell(row, col).setPath(reader.readLine());
				    			
				    			break;
				    		}
				    	}
				    	reader.close();
				    } catch (FileNotFoundException e1) {
				    	// TODO Auto-generated catch block
				    	e1.printStackTrace();
				    } catch (IOException e1) {
				    	// TODO Auto-generated catch block
				    	e1.printStackTrace();
				    }
			  		this.letters.remove(num);
			  		revertOriginal.add(new revertCPUTiles(row, col, originalPath));
			  		return right;
			  	}
			  	else {
			   //JOLLY
			  		return right;
			  	}	
		  }
		  else {
			  if(row!=missRow && missCol!=col ){
				  right=false;
				  return right;
				  
			  }
			  else 
				  return right;
		  
		  } 
		  
		}
	
	

	public PlaceWord enemyTurn(Box box, Board board, int tryCount, JFrame scrabble, ScrabbleManager scrabbleM)  throws FileNotFoundException {
		ArrayList<PlaceWord> wordsToUse = getDictWords(board, box);
		PlaceWord currentPlaceWord;
		String currentWord;
		int dir;
		int startRow;
		int startCol;
		boolean right=true;
		boolean firstRound=false;
		boolean check=false;
		Board savedBoard=board;
		ArrayList<Tile> currentRack=new ArrayList<Tile>();
		int scoreTemp=0;
		int missingRow;
		int missingCol;
		int countDW=0;
		int countTW=0;
		if(revertOriginal!=null)
			revertOriginal.clear();
		  
		System.out.println("WordsToUse");
		for(int i=0; i<wordsToUse.size();i++){
			System.out.println(wordsToUse.get(i).getWord() + wordsToUse.get(i).getScore() + wordsToUse.get(i).getDir() + " missingRow "+wordsToUse.get(i).getMissingRow()+" missCol "+wordsToUse.get(i).getMissingCol());
			
		}
		
		if(wordsToUse.isEmpty()){
			//PASSO IL TURNO
			countTurn++;
			if(countTurn==3){
				 JPanel messagePane = new JPanel(new BorderLayout());
	       		 JPanel results=new JPanel(new GridLayout(2,2,20,0));
	       		 results.add(scrabbleM.cpuNameValue);
	       		 results.add(scrabbleM.cpuScoreValue);
	       		 results.add(scrabbleM.playerNameValue);
	       		 results.add(scrabbleM.playerScoreValue);
	       		 messagePane.add(scrabbleM.lostBtn, BorderLayout.NORTH);
	       		 messagePane.add(results, BorderLayout.CENTER);
	       		 LookAndFeel previousLF = UIManager.getLookAndFeel();
	    	   		try {
	    				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
	    					| UnsupportedLookAndFeelException e1) {
	    				// TODO Auto-generated catch block
	    				e1.printStackTrace();
	    			}
	    	   		JOptionPane.showMessageDialog(scrabble, messagePane, "Risultato finale", JOptionPane.PLAIN_MESSAGE);
	    	   		try {
	    				UIManager.setLookAndFeel(previousLF);
	    			} catch (UnsupportedLookAndFeelException e1) {
	    				// TODO Auto-generated catch block
	    				e1.printStackTrace();
	    			} 
       		 	scrabbleM.newGame();
    		 	scrabbleM.whoStarts();
			}
			throwAwayEnemyRack(box);
			System.out.println("WordsToUse size: "+wordsToUse.size());
			currentPlaceWord = new PlaceWord("",0,0,0,0,0,0,0,false);
			currentPlaceWord.setWord("");
			return currentPlaceWord;
		}
		
		else {
		   if (tryCount>=wordsToUse.size()){
			   //PASSO IL TURNO
			   countTurn++;
			   if(countTurn==3){
	       		 	String result="Partita vinta dal giocatore";
	       		 	JOptionPane.showMessageDialog(scrabble, result, "Risultato finale", JOptionPane.INFORMATION_MESSAGE);
	       		 	scrabbleM.newGame();
	       		 	scrabbleM.whoStarts();
			   }
			   throwAwayEnemyRack(box);
			   System.out.println("WordsToUse size: "+wordsToUse.size());
			   currentPlaceWord = new PlaceWord("",0,0,0,0,0,0,0,false);
			   currentPlaceWord.setWord("");
			   return currentPlaceWord;
		   }
		   
		   currentRack.clear();
		   currentRack.addAll(this.letters);
		   currentPlaceWord=wordsToUse.get(tryCount);
		   currentWord=currentPlaceWord.getWord();
		   currentPlaceWord.setMultiScore(0);
		   dir=currentPlaceWord.getDir();
		   startRow=currentPlaceWord.getStartRow();
		   startCol=currentPlaceWord.getStartCol();
		   missingRow=currentPlaceWord.getMissingRow();
		   missingCol=currentPlaceWord.getMissingCol();
			
		   //Controllo se è il primo turno
		   if(board.isEmpty()){
					//Prima parola da inserire: deve essere sulla stella e di almeno due lettere
					if(currentWord.length()<2){
						//PASSO IL TURNO
						countTurn++;
						throwAwayEnemyRack(box);
						currentPlaceWord = new PlaceWord("",0,0,0,0,0,0,0,false);
						currentPlaceWord.setWord("");
						return currentPlaceWord;
					}
					else {
						dir=0;
						startRow=7;
						startCol=6;
						firstRound=true;
					}	
		   }
		   
		   ArrayList<Character> currentWordChar = new ArrayList<Character>();
		   for(int i = 0; i<currentWord.length(); i++)  
			      currentWordChar.add(currentWord.charAt(i));
		   
		   int num = 0, counter = 0;		//num indice della lettera da posizionare all'interno del rack
		   while(currentWordChar.size()>0){
			   for(int i = 0; i<this.letters.size(); i++) {
				   if(this.letters.get(i).getLetter()==currentWordChar.get(0)){
					   num = i;
					   break;
				   }
			   }
			    
			   if(dir==0) { 
				   right=placeEnemyTile(num, startRow, startCol + counter, dir, board, missingRow, missingCol); 
				   if(right && (startCol + counter) >=0 && startCol + counter <15 && startRow>=0 && startRow<15){
					   scoreTemp+=board.getCell(startRow, startCol+counter).getMultiScore()*box.getScoreLetter(currentWordChar.get(0), board);
					   if( (startRow ==0 && startCol+counter==3) || (startRow==0 && startCol+counter==11) || (startRow ==2 && startCol+counter==6) || (startRow ==2 && startCol+counter==8) || (startRow ==3 && startCol+counter==0) || (startRow ==3 && startCol+counter==14) || (startRow==6 && startCol+counter==12) || (startRow==6 && startCol+counter==2) || (startRow==8 && startCol+counter==2)
								|| (startRow ==8 && startCol+counter==12) || (startRow ==11 && startCol+counter==0) || (startRow ==11 && startCol+counter==14) || (startRow==12 && startCol+counter==6) || (startRow ==12 && startCol+counter==8) || (startRow ==14 && startCol+counter==3) || (startRow==14 && startCol+counter==11)){
										countDW++;;
								}
							   
							   if( (startRow ==0 && startCol+counter==7) || (startRow==1 && startCol+counter==5) || (startRow==1 && startCol+counter==9) || (startRow ==5 && startCol+counter==1) || (startRow==5 && startCol+counter==13) || (startRow==7 && startCol+counter==0) || (startRow==7 && startCol+counter==14) || (startRow ==9 && startCol+counter==1) || (startRow==9 && startCol+counter==13) 
							   || (startRow ==13 && startCol+counter==5) || (startRow==13 && startCol+counter==9) || (startRow ==14 && startCol+counter==7) )
									countTW++;
					   counter++;
				   }
				   else 
					   right=false;
			   }
				
			   if(dir==1){
				   right=placeEnemyTile(num, startRow + counter, startCol, dir, board,  missingRow, missingCol); 
				   if((startRow + counter)>=0 && (startRow + counter) <15 && startCol>=0 && startCol<15){
					   scoreTemp+=board.getCell(startRow + counter, startCol).getMultiScore()*box.getScoreLetter(currentWordChar.get(0), board);
					   if( (startRow + counter==0 && startCol==3) || (startRow + counter==0 && startCol==11) || (startRow + counter==2 && startCol==6) || (startRow + counter==2 && startCol==8) || (startRow + counter==3 && startCol==0) || (startRow + counter==3 && startCol==14) || (startRow + counter==6 && startCol==12) || (startRow + counter==6 && startCol==2) || (startRow + counter==8 && startCol==2)
						|| (startRow + counter==8 && startCol==12) || (startRow + counter==11 && startCol==0) || (startRow + counter==11 && startCol==14) || (startRow + counter==12 && startCol==6) || (startRow + counter==12 && startCol==8) || (startRow + counter==14 && startCol==3) || (startRow + counter==14 && startCol==11)){
								countDW++;;
						}
					   
					   if( (startRow + counter==0 && startCol==7) || (startRow + counter==1 && startCol==5) || (startRow + counter==1 && startCol==9) || (startRow + counter==5 && startCol==1) || (startRow + counter==5 && startCol==13) || (startRow + counter==7 && startCol==0) || (startRow + counter==7 && startCol==14) || (startRow + counter==9 && startCol==1) || (startRow + counter==9 && startCol==13) 
					   || (startRow + counter==13 && startCol==5) || (startRow + counter==13 && startCol==9) || (startRow + counter==14 && startCol==7) )
							countTW++;
					   
					   counter++;
				   }
				   else
					   right=false;	 
			   }
				   
			   currentWordChar.remove(0);
			   currentPlaceWord.setRevertOriginal(revertOriginal);
			      
			   if(!right){
				   for(int i=0; i<this.revertOriginal.size();i++){
					   board.getCell(this.revertOriginal.get(i).getRow(),this.revertOriginal.get(i).getCol()).setPath(this.revertOriginal.get(i).getOriginalPath());
					   board.removeTile(this.revertOriginal.get(i).getRow(), this.revertOriginal.get(i).getCol());
				   }
				   board=savedBoard;
				   check=true;
				   this.letters.clear();
				   this.letters.addAll(currentRack);
				   PlaceWord nextWord=enemyTurn(box, board,tryCount+1,scrabble,scrabbleM);
				   currentPlaceWord.setWord(nextWord.getWord());
				   currentPlaceWord.setMultiScore(nextWord.getMultiScore());
				   break;
			   }
			  
		   }
		   
			 if(countDW>0){
				 for(int z=0; z<countDW; z++){
					 scoreTemp*=2;
					 System.out.println("possibleTotal" + scoreTemp);
				 }
			 }
			 
			 if(countTW>0){
				 for(int z=0; z<countTW; z++){
					 scoreTemp*=3;
					 System.out.println("possibleTotal" + scoreTemp);
				 }
			 }
			 
			//parola di sole due lettere--> prossima parola
			 boolean flag1=false;
			   if(currentWord.length()<3){
				   flag1=true;
				   for(int i=0; i<this.revertOriginal.size();i++){
						   board.getCell(this.revertOriginal.get(i).getRow(),this.revertOriginal.get(i).getCol()).setPath(this.revertOriginal.get(i).getOriginalPath());
						   board.removeTile(this.revertOriginal.get(i).getRow(), this.revertOriginal.get(i).getCol());
					   }
				   check=true;
					   board=savedBoard;
					   this.letters.clear();
					   this.letters.addAll(currentRack);
					   PlaceWord nextWord=enemyTurn(box, board,tryCount+1,scrabble,scrabbleM);
					   currentPlaceWord.setWord(nextWord.getWord());
					   currentPlaceWord.setMultiScore(nextWord.getMultiScore());
					   
			   }
		    
		   if(currentPlaceWord.getWord().length()-1!=7-this.letters.size() && !firstRound && !flag1){
			   for(int i=0; i<this.revertOriginal.size();i++){
				   board.getCell(this.revertOriginal.get(i).getRow(),this.revertOriginal.get(i).getCol()).setPath(this.revertOriginal.get(i).getOriginalPath());
				   board.removeTile(this.revertOriginal.get(i).getRow(), this.revertOriginal.get(i).getCol());
			   }
			   check=true;
			   board=savedBoard;
			   this.letters.clear();
			   this.letters.addAll(currentRack);
			   PlaceWord nextWord=enemyTurn(box, board,tryCount+1,scrabble, scrabbleM);
			   currentPlaceWord.setWord(nextWord.getWord());
			   currentPlaceWord.setMultiScore(nextWord.getMultiScore());
			   //this.revertOriginal=nextWord.getRevertOriginal();
			   
		   }
		   
			
		   countTurn=0;
		   currentPlaceWord.setMultiScore(scoreTemp);
		   if(!check)
			   this.score+=currentPlaceWord.getMultiScore();
		   return currentPlaceWord;		
		}
	}
	
	
	
	
}

