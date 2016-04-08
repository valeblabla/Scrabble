import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Random;

public class Box {
	private ArrayList<Tile> lettersLeft;		//Lettere nel sacchetto
	private int numLetters;						//numero di lettere nel sacchetto
	
	public Box(){
		lettersLeft=new ArrayList<Tile>() ;	
		
		for(int i=0; i<14; i++)
			lettersLeft.add( new Tile('A',1, "img/tiles/tilesFlat/a.png",false,"img/tiles/tilesFlat/colored/a.png","img/tiles/tilesFlat/a.png","img/tiles/tilesFlat/big/a.png","img/tiles/tilesFlat/playerHover/a.png") );
		for(int i=0; i<3; i++)
			lettersLeft.add( new Tile('B',5, "img/tiles/tilesFlat/b.png",false,"img/tiles/tilesFlat/colored/b.png","img/tiles/tilesFlat/b.png","img/tiles/tilesFlat/big/b.png","img/tiles/tilesFlat/playerHover/b.png") );
		for(int i=0; i<6; i++)
			lettersLeft.add( new Tile('C',2, "img/tiles/tilesFlat/c.png",false,"img/tiles/tilesFlat/colored/c.png","img/tiles/tilesFlat/c.png","img/tiles/tilesFlat/big/c.png","img/tiles/tilesFlat/playerHover/c.png") );
		for(int i=0; i<3; i++)
			lettersLeft.add( new Tile('D',5, "img/tiles/tilesFlat/d.png",false,"img/tiles/tilesFlat/colored/d.png","img/tiles/tilesFlat/d.png","img/tiles/tilesFlat/big/d.png","img/tiles/tilesFlat/playerHover/d.png") );
		for(int i=0; i<11; i++)
			lettersLeft.add( new Tile('E',1, "img/tiles/tilesFlat/e.png",false,"img/tiles/tilesFlat/colored/e.png","img/tiles/tilesFlat/e.png","img/tiles/tilesFlat/big/e.png","img/tiles/tilesFlat/playerHover/e.png") );
		for(int i=0; i<3; i++)
			lettersLeft.add( new Tile('F',5, "img/tiles/tilesFlat/f.png",false,"img/tiles/tilesFlat/colored/f.png","img/tiles/tilesFlat/f.png","img/tiles/tilesFlat/big/f.png","img/tiles/tilesFlat/playerHover/f.png") );
		for(int i=0; i<2; i++)
			lettersLeft.add( new Tile('G',8, "img/tiles/tilesFlat/g.png",false,"img/tiles/tilesFlat/colored/g.png","img/tiles/tilesFlat/g.png","img/tiles/tilesFlat/big/g.png","img/tiles/tilesFlat/playerHover/g.png") );
		for(int i=0; i<2; i++)
			lettersLeft.add( new Tile('H',8, "img/tiles/tilesFlat/h.png",false,"img/tiles/tilesFlat/colored/h.png","img/tiles/tilesFlat/h.png","img/tiles/tilesFlat/big/h.png","img/tiles/tilesFlat/playerHover/h.png") );
		for(int i=0; i<12; i++)
			lettersLeft.add( new Tile('I',1, "img/tiles/tilesFlat/i.png",false,"img/tiles/tilesFlat/colored/i.png","img/tiles/tilesFlat/i.png","img/tiles/tilesFlat/big/i.png","img/tiles/tilesFlat/playerHover/i.png") );
		for(int i=0; i<5; i++)
			lettersLeft.add( new Tile('L',3, "img/tiles/tilesFlat/l.png",false,"img/tiles/tilesFlat/colored/l.png","img/tiles/tilesFlat/l.png","img/tiles/tilesFlat/big/l.png","img/tiles/tilesFlat/playerHover/l.png") );
		for(int i=0; i<5; i++)
			lettersLeft.add( new Tile('M',3, "img/tiles/tilesFlat/m.png",false,"img/tiles/tilesFlat/colored/m.png","img/tiles/tilesFlat/m.png","img/tiles/tilesFlat/big/m.png","img/tiles/tilesFlat/playerHover/m.png") );
		for(int i=0; i<5; i++)
			lettersLeft.add( new Tile('N',3, "img/tiles/tilesFlat/n.png",false,"img/tiles/tilesFlat/colored/n.png","img/tiles/tilesFlat/n.png","img/tiles/tilesFlat/big/n.png","img/tiles/tilesFlat/playerHover/n.png") );
		for(int i=0; i<15; i++)
			lettersLeft.add( new Tile('O',1, "img/tiles/tilesFlat/o.png",false,"img/tiles/tilesFlat/colored/o.png","img/tiles/tilesFlat/o.png","img/tiles/tilesFlat/big/o.png","img/tiles/tilesFlat/playerHover/o.png") );
		for(int i=0; i<3; i++)
			lettersLeft.add( new Tile('P',5, "img/tiles/tilesFlat/p.png",false,"img/tiles/tilesFlat/colored/p.png","img/tiles/tilesFlat/p.png","img/tiles/tilesFlat/big/p.png","img/tiles/tilesFlat/playerHover/p.png") );
		lettersLeft.add( new Tile('Q',10, "img/tiles/tilesFlat/q.png",false,"img/tiles/tilesFlat/colored/q.png","img/tiles/tilesFlat/q.png","img/tiles/tilesFlat/big/q.png","img/tiles/tilesFlat/playerHover/q.png") );
		for(int i=0; i<6; i++)
			lettersLeft.add( new Tile('R',2, "img/tiles/tilesFlat/r.png",false,"img/tiles/tilesFlat/colored/r.png","img/tiles/tilesFlat/r.png","img/tiles/tilesFlat/big/r.png","img/tiles/tilesFlat/playerHover/r.png") );
		for(int i=0; i<6; i++)
			lettersLeft.add( new Tile('S',2, "img/tiles/tilesFlat/s.png",false,"img/tiles/tilesFlat/colored/s.png","img/tiles/tilesFlat/s.png","img/tiles/tilesFlat/big/s.png","img/tiles/tilesFlat/playerHover/s.png") );
		for(int i=0; i<6; i++)
			lettersLeft.add( new Tile('T',2, "img/tiles/tilesFlat/t.png",false,"img/tiles/tilesFlat/colored/t.png","img/tiles/tilesFlat/t.png","img/tiles/tilesFlat/big/t.png","img/tiles/tilesFlat/playerHover/t.png") );
		for(int i=0; i<5; i++)
			lettersLeft.add( new Tile('U',3, "img/tiles/tilesFlat/u.png",false,"img/tiles/tilesFlat/colored/u.png","img/tiles/tilesFlat/u.png","img/tiles/tilesFlat/big/u.png","img/tiles/tilesFlat/playerHover/u.png") );
		for(int i=0; i<3; i++)
			lettersLeft.add( new Tile('V',5, "img/tiles/tilesFlat/v.png",false,"img/tiles/tilesFlat/colored/v.png","img/tiles/tilesFlat/v.png","img/tiles/tilesFlat/big/v.png","img/tiles/tilesFlat/playerHover/v.png") );
		for(int i=0; i<2; i++)
			lettersLeft.add( new Tile('Z',8, "img/tiles/tilesFlat/z.png",false,"img/tiles/tilesFlat/colored/z.png","img/tiles/tilesFlat/z.png","img/tiles/tilesFlat/big/z.png","img/tiles/tilesFlat/playerHover/z.png") );
		for(int i=0; i<2; i++)
			lettersLeft.add( new Tile(' ',0, "img/tiles/tilesFlat/jolly.png",true,"","img/tiles/tilesFlat/colored/jolly.png","img/tiles/tilesFlat/big/jolly.png","img/tiles/tilesFlat/playerHover/jolly.png") );
		
		numLetters=lettersLeft.size();
	}
	
	public Tile randomExtraction(){
		int index=new Random().nextInt(this.numLetters);
		Tile returnTile=lettersLeft.get(index);
		lettersLeft.remove(index);
		numLetters--;
		return returnTile;
	}
	
	public ArrayList<Tile> changeLetters(ArrayList<Tile> oldLetters){
		ArrayList<Tile> support=new ArrayList<Tile>();
		for(int i = 0; i < oldLetters.size(); i++){
            if((oldLetters.get(i)).isSelected()){
            	lettersLeft.add(oldLetters.get(i));
                numLetters++;              
            }
            else support.add(oldLetters.get(i));
        }       
        for(int i=support.size(); i<7; i++){
        	support.add(randomExtraction());
        }
        return support;
    }
	
	public int getScoreLetter(char letterToFind, Board board){
		String letter="";
		int score=0;
  		letter+=letterToFind;
  		File letters=new File("initializingLetters.txt");
	    try {
	    	LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(letters)));
	    	String lineRead = null;									 
	    	while((lineRead = reader.readLine()) != null) {
	    		if((lineRead.compareToIgnoreCase(letter)==0)){
	    			score=Integer.parseInt(reader.readLine());
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
	    return score;
	}
	
	//Restituisce il numero di occorenze di 'letter' nella box
	public int getNumOfALetter(char letter){
		int count=0;
		for (int i=0; i<this.numLetters; i++)
			if(this.lettersLeft.get(i).getLetter()==letter)
				count++;
		return count;
	}
	
	public int getNumJolly(){
		int count=0;
		for(int i=0; i<this.numLetters; i++)
			if(this.lettersLeft.get(i).isJolly())
				count++;
		return count;
	}
	
	//Restituisce il path grande della tile associata alla lettera 'letter'
	public String getSinglePath (char letter){
		String path="";
		for(int i=0; i< this.numLetters; i++){
			if(letter==this.getLettersLeft().get(i).getLetter()){
				path=this.getLettersLeft().get(i).getOriginalPath();	
				break;
			}
		}
		return path;
	}
	
	public void addTile (Tile tile){
		this.lettersLeft.add(tile);
		this.numLetters++;
	}

	public ArrayList<Tile> getLettersLeft() {
		return lettersLeft;
	}

	public int getNumLetters() {
		return numLetters;
	}

	public void setLettersLeft(ArrayList<Tile> lettersLeft) {
		this.lettersLeft = lettersLeft;
	}

	public void setNumLetters(int numLetters) {
		this.numLetters = numLetters;
	}
	

}
