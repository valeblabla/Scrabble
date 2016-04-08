
public class revertCPUTiles {
	private int row;
	private int col;
	private String originalPath;
	private Tile originalTile;
	
	public revertCPUTiles(int row, int col, String path){
		this.row=row;
		this.col=col;
		this.originalPath=path;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public String getOriginalPath() {
		return originalPath;
	}

	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}

	public Tile getOriginalTile() {
		return originalTile;
	}

	public void setOriginalTile(Tile originalTile) {
		this.originalTile = originalTile;
	}


	
}
