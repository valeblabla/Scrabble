import java.awt.Insets;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class Player {
	private String name;
	private int score;
	private ArrayList<Tile> letters;
	private ArrayList<revertCPUTiles> revertOriginal;
	
	
	//Costruttore
	public Player(ArrayList<Tile> extractedLetters){
		this.score=0;
		this.revertOriginal=new ArrayList<revertCPUTiles>();
		try {
			this.name=System.getProperty("user.name");
		} catch(SecurityException e){
			System.out.println(e);
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		this.letters=extractedLetters;
		this.changeIcon();
	}
	
	//Rimuove lettere con il campo selected true
	//Restituisce numero di lettere rimosse
	public int removeLetters(){
		int numLetters=letters.size();
		for(int i=0; i<letters.size(); i++){
			if (letters.get(i).isSelected())
				letters.remove(i);
		}
		int missingLetters= numLetters-letters.size();
		return missingLetters;
		
	}
	
	public void changeIcon(){
		for(int i=0; i<this.letters.size(); i++){
   	 		this.letters.get(i).setIcon(new ImageIcon(this.letters.get(i).getBigPath()));
   	 		this.letters.get(i).setMargin(new Insets(0,0,0,0));
   	 		this.letters.get(i).setBorder(BorderFactory.createEmptyBorder());
		}
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
					wordsToPlace.add(new PlaceWord(currentWord, startRow, startCol,0,0,dict.getScore(currentWord), 0,0,false));
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
		    
		   //Controllo di poter costruire la parola con le lettere del player
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
			//Cambia lettere; 
			return wordsToPlace; 
		}
		else {
			Collections.sort(wordsToPlace, new PlaceWord.ScoreComparator());
		    return wordsToPlace;	   
		}
	
	}
	
	public boolean placeTile(int num, int row, int col, int dir, Board board, int missRow, int missCol){
		  boolean right=true;
		  if(row<0||row>14||col<0||col>14)
		    return false;
		
		  if(!board.getCell(row, col).isOccupied()) {
			  	if(!this.letters.get(num).isJolly()){  
			  		board.getCell(row, col).setLetter(this.letters.get(num).getLetter());
			  		board.getCell(row, col).setValue(this.letters.get(num).getValue());
			  		board.getCell(row, col).setOccupied(true);
			  		board.getCell(row, col).setPath(this.letters.get(num).getPathColored());
			  		board.getCell(row, col).setOriginalPath(this.letters.get(num).getOriginalPath());
			  		board.getCell(row, col).setPathColored(this.letters.get(num).getPathColored());
			  		board.getCell(row, col).setBigPath(this.letters.get(num).getBigPath());
			  		revertOriginal.add(new revertCPUTiles(row, col, this.letters.get(num).getOriginalPath()));
			  		revertOriginal.get(revertOriginal.size()-1).setOriginalTile(this.letters.get(num));
			  		System.out.println(revertOriginal.get(revertOriginal.size()-1).getOriginalTile().getLetter());
			  		this.letters.remove(num);
			  		
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
	
	public PlaceWord hint(Box box, Board board, int tryCount) throws FileNotFoundException{
		ArrayList<PlaceWord> wordsToUse = getDictWords(board, box);
		PlaceWord currentPlaceWord = null;
		String currentWord;
		int dir;
		int startRow;
		int startCol;
		boolean right=true;
		boolean firstRound=false;
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
			//CONSIGLIO DI CAMBIARE LETTERE
			System.out.println("WordsToUse size: "+wordsToUse.size());
			currentPlaceWord = new PlaceWord("",0,0,0,0,0,0,0,false);
			currentPlaceWord.setWord("");
			return currentPlaceWord;
		}
		
		else {
		   if (tryCount>=wordsToUse.size()){
			   //CONSIGLIO CAMBIA LETTERE
			   System.out.println("WordsToUse size: "+wordsToUse.size());
			   currentPlaceWord = new PlaceWord("",0,0,0,0,0,0,0,false);
			   currentPlaceWord.setWord("");
			   return currentPlaceWord;
		   }
		   
		   currentRack.clear();
		   currentRack.addAll(this.letters);
		   currentPlaceWord=wordsToUse.get(tryCount);
		   currentPlaceWord.setMultiScore(0);
		   currentWord=currentPlaceWord.getWord();
		   dir=currentPlaceWord.getDir();
		   startRow=currentPlaceWord.getStartRow();
		   startCol=currentPlaceWord.getStartCol();
		   missingRow=currentPlaceWord.getMissingRow();
		   missingCol=currentPlaceWord.getMissingCol();
		   
		   //Controllo se è il primo turno
		   if(board.isEmpty()){
					//Prima parola da inserire: deve essere sulla stella e di almeno due lettere
					if(currentWord.length()<2){
						//CONSIGLIO DI CAMBIARE
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
				   right=placeTile(num, startRow, startCol + counter, dir, board, missingRow, missingCol); 
				   if(right && (startCol + counter) >=0 && startCol + counter <15 && startRow<15 && startRow>=0){
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
				   right=placeTile(num, startRow + counter, startCol, dir, board,  missingRow, missingCol); 
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
				   
				   this.letters.clear();
				   this.letters.addAll(currentRack);
				   PlaceWord nextWord=hint(box, board,tryCount+1);
				   currentPlaceWord.setWord(nextWord.getWord());
				   currentPlaceWord.setMultiScore(nextWord.getMultiScore());
				   this.revertOriginal=nextWord.getRevertOriginal();
				   break;
			   }
			  
		   }
		   
			 if(countDW>0){
				 for(int z=0; z<countDW; z++){
					 scoreTemp*=2;
				 }
			 }
			 
			 if(countTW>0){
				 for(int z=0; z<countTW; z++){
					 scoreTemp*=3;
				 }
			 }
		    
			 
			//parola di sole due lettere--> prossima parola
			 boolean flag=false;
			   if(currentWord.length()<3){
				   flag=true;
				   for(int i=0; i<this.revertOriginal.size();i++){
						   board.getCell(this.revertOriginal.get(i).getRow(),this.revertOriginal.get(i).getCol()).setPath(this.revertOriginal.get(i).getOriginalPath());
						   board.removeTile(this.revertOriginal.get(i).getRow(), this.revertOriginal.get(i).getCol());
					   }
					   board=savedBoard;
					   this.letters.clear();
					   this.letters.addAll(currentRack);
					   PlaceWord nextWord=hint(box, board,tryCount+1);
					   currentPlaceWord.setWord(nextWord.getWord());
					   currentPlaceWord.setMultiScore(nextWord.getMultiScore());
					   this.revertOriginal=nextWord.getRevertOriginal();
			   }
			   
			 
		   if(currentPlaceWord.getWord().length()-1!=7-this.letters.size() && !firstRound && !flag){
			   for(int i=0; i<this.revertOriginal.size();i++){
				   board.getCell(this.revertOriginal.get(i).getRow(),this.revertOriginal.get(i).getCol()).setPath(this.revertOriginal.get(i).getOriginalPath());
				   board.removeTile(this.revertOriginal.get(i).getRow(), this.revertOriginal.get(i).getCol());
			   }
			   board=savedBoard;
			   this.letters.clear();
			   this.letters.addAll(currentRack);
			   PlaceWord nextWord=hint(box, board,tryCount+1);
			   currentPlaceWord.setWord(nextWord.getWord());
			   currentPlaceWord.setMultiScore(nextWord.getMultiScore());
			   this.revertOriginal=nextWord.getRevertOriginal();
		   }
		}
		 currentPlaceWord.setMultiScore(scoreTemp);
		 return currentPlaceWord;
		   
		   	   
			   
	}
	
	

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<Tile> getLetters() {
		return letters;
	}

	public void setLetters(ArrayList<Tile> letters) {
		this.letters = letters;
	}
	
	public void setLetter(Tile newTile){
		this.letters.add(newTile);
	}

	public ArrayList<revertCPUTiles> getRevertOriginal() {
		return revertOriginal;
	}
}
