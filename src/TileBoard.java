public class TileBoard extends Tile {
	private int x1;
	private int y1;
	private int multiScore;
	private boolean occupied;
	private boolean blocked;
	private int direction;
	
	public TileBoard(char letter, int value, String path, String pathColored, int x, int y, int multiScore, String originalPath, String bigPath, String playerSelected){
		super(letter, value, path, false, pathColored, originalPath, bigPath, playerSelected);
		this.x1=x;
		this.y1=y;
		this.multiScore=multiScore;
		this.occupied=false;
		this.blocked=false;
		this.direction=0;
		//this.setBackground(null);
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public void setMultiScore(int multiScore) {
		this.multiScore = multiScore;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getMultiScore() {
		return multiScore;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
    
}
