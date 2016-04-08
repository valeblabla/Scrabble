import java.util.ArrayList;

public class Board {
	private final int ROWS=15;
	private final int COLUMNS=15;
	private TileBoard[][] board;
	
	public Board(){
		board=new TileBoard[ROWS][COLUMNS];
			
		for(int i=0; i<ROWS; i++){
			for(int j=0; j<COLUMNS; j++){
				board[i][j]=new TileBoard(' ',0,"img/tiles/tilesFlat/emptyBoard.png","",i+1,j+1,1,"","","");
				if(i==j){
					board[i][j]=new TileBoard(' ',0,"img/tiles/tilesFlat/boardDL.png","",i+1,j+1,2,"","","");
				}
				if(i+j==(ROWS-1)){
					board[i][j]=new TileBoard(' ',0,"img/tiles/tilesFlat/boardTL.png","",i+1,j+1,3,"","","");
				}
				if((i==j)&&(i+j==(ROWS-1))){
					board[i][j]=new TileBoard(' ',0,"img/tiles/tilesFlat/boardStar.png","",i+1,j+1,1,"","","");
				}
				if( (i==0 && j==3) || (i==0 && j==11) || (i==2 && j==6) || (i==2 && j==8) || (i==3 && j==0) || (i==3 && j==14) || (i==6 && j==12) || (i==6 && j==2) || (i==8 && j==2)
						|| (i==8 && j==12) || (i==11 && j==0) || (i==11 && j==14) || (i==12 && j==6) || (i==12 && j==8) || (i==14 && j==3) || (i==14 && j==11)){
						board[i][j]=new TileBoard(' ',0,"img/tiles/tilesFlat/boardDW.png","",i+1,j+1,1,"","","");
				}
				if( (i==3 && j==7) || (i==7 && j==3) || (i==7 && j==11) || (i==11 && j==7))
					board[i][j]=new TileBoard(' ',0,"img/tiles/tilesFlat/boardDL.png","",i+1,j+1,2,"","","");
				if( (i==0 && j==7) || (i==1 && j==5) || (i==1 && j==9) || (i==5 && j==1) || (i==5 && j==13) || (i==7 && j==0) || (i==7 && j==14) || (i==9 && j==1) || (i==9 && j==13) 
						|| (i==13 && j==5) || (i==13 && j==9) || (i==14 && j==7) )
					board[i][j]=new TileBoard(' ',0,"img/tiles/tilesFlat/boardTW.png","",i+1,j+1,1,"","","");
			}
		}
		
		
	}

	public int getROWS() {
		return ROWS;
	}

	public int getCOLUMNS() {
		return COLUMNS;
	}

	public TileBoard[][] getBoard() {
		return board;
	}

	public TileBoard getCell(int x,int y){
		return board[x][y];
	}
	
	//Restituisce il numero di caselle occupate sulla board
	public int occupiedPositions(){
		int count=0;
		for(int i=0; i<ROWS; i++){
			for(int j=0; j<COLUMNS; j++){
				if(this.board[i][j].isOccupied()) count++; 
			}
		}
		return count;
	}
	
	public boolean isEmpty(){
		if(this.occupiedPositions()==0) 
			return true;
		else
			return false;
	}
	
	public void removeTile(int j, int k){
		board[j][k].setOccupied(false);
		if(board[j][k].getMultiScore()==1)
			board[j][k].setPath("img/tiles/tilesFlat/emptyBoard.png");
		if(board[j][k].getMultiScore()==2)
			board[j][k].setPath("img/tiles/tilesFlat/boardDL.png");
		if(board[j][k].getMultiScore()==3)
			board[j][k].setPath("img/tiles/tilesFlat/boardTL.png");
		if( (j==k) && (k+j==(this.COLUMNS-1) ) )
			board[j][k].setPath("img/tiles/tilesFlat/boardStar.png");
		if( (j==0 && j==k) || (j==0 && k==11) || (j==2 && k==6) || (j==2 && k==8) || (j==3 && k==0) || (j==3 && k==14) || (j==6 && k==12) || (j==6 && k==2) || (j==8 && k==2)
				|| (j==8 && k==12) || (j==11 && k==0) || (j==11 && k==14) || (j==12 && k==6) || (j==12 && k==8) || (j==14 && k==3) || (j==14 && k==11)){
				board[j][k].setPath("img/tiles/tilesFlat/boardDW.png");
		}
		if( (j==0 && k==7) || (j==1 && k==5) || (j==1 && k==9) || (j==5 && k==1) || (j==5 && k==13) || (j==7 && k==0) || (j==7 && k==14) || (j==9 && k==1) || (j==9 && k==13) 
				|| (j==13 && k==5) || (j==13 && k==9) || (j==14 && k==7) )
			board[j][k].setPath("img/tiles/tilesFlat/boardTW.png");
	}
	
	
	//Restituisce ArrayList di TileBoard occupate che possono essere usate per creare una parola che si
	//interseca con una già presente sulla board (Utilizzato da Computer)
	//Una TileBoard è definita available quando ha la preedente e la successiva libera (in orizzontale o verticale)
	public ArrayList<TileBoard> getAvailableLetters(){
		ArrayList<TileBoard> availableLetters = new ArrayList<TileBoard>();
	
		for(int x = 0; x<ROWS; x++) {
			for(int y = 0; y<COLUMNS; y++){
				if(x>0&&x<14&&y>0&&y<14){
					if(board[x][y].isOccupied()){
			
					  if( (board[x][y+1].isOccupied() ) && (board[x+1][y].isOccupied()) && (board[x][y-1].isOccupied()) && (board[x-1][y].isOccupied()) )
						  continue;
				  
					  if( !board[x][y+1].isOccupied()&& !board[x][y-1].isOccupied()){
						  board[x][y].setDirection(0);
						  availableLetters.add(board[x][y]);
						  
					  }
					  else 
						  if(!board[x+1][y].isOccupied() && !board[x-1][y].isOccupied()){
							  board[x][y].setDirection(1);
							  availableLetters.add(board[x][y]);
						  }
					} 
				}
		  
			}
		  
		}   
	   return availableLetters; 
	}
	
}
