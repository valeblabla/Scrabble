import java.util.ArrayList;
import java.util.Comparator;

public class PlaceWord {
	private String word;
	private int score;
	private int multiScore;
	private int startRow;
	private int startCol;
	private int dir;
	private int missingRow;
	private int missingCol;
	private boolean reverseDir;
	private ArrayList<revertCPUTiles> revertOriginal;
	
	public PlaceWord(String word, int startRow, int startCol, int dir, int score, int multiScore, int missingRow, int missingCol, boolean reverseDir){
		this.word=word;
		this.startCol=startCol;
		this.startRow=startRow;
		this.score=score;
		this.multiScore=multiScore;
		this.dir=dir;
		this.missingRow=missingRow;
		this.missingCol=missingCol;
		this.reverseDir=reverseDir;
	}
	
	static class ScoreComparator implements Comparator<PlaceWord> {

		@Override
		public int compare(PlaceWord a, PlaceWord b) {
			return a.getScore() > b.getScore() ? -1 : a.getScore() == b.getScore() ? 0 : 1;
		}
		
	}
	
	static class ScoreComparatorInverse implements Comparator<PlaceWord> {

		@Override
		public int compare(PlaceWord a, PlaceWord b) {
			return a.getScore() < b.getScore() ? -1 : a.getScore() == b.getScore() ? 0 : 1;
		}
		
	}
	
	static class ScoreComparatorAlpha implements Comparator<PlaceWord> {

		@Override
		public int compare(PlaceWord a, PlaceWord b) {
			return a.getWord().charAt(0) > b.getWord().charAt(0) ? -1 : a.getWord().charAt(0) == b.getWord().charAt(0) ? 0 : 1;
		}
		
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
	
	public int getMissingRow() {
		return missingRow;
	}

	public void setMissingRow(int missingRow) {
		this.missingRow = missingRow;
	}

	public int getMissingCol() {
		return missingCol;
	}

	public void setMissingCol(int missingCol) {
		this.missingCol = missingCol;
	}

	public boolean isReverseDir() {
		return reverseDir;
	}

	public void setReverseDir(boolean reverseDir) {
		this.reverseDir = reverseDir;
	}

	public int getMultiScore() {
		return multiScore;
	}

	public void setMultiScore(int multiScore) {
		this.multiScore = multiScore;
	}

	public ArrayList<revertCPUTiles> getRevertOriginal() {
		return revertOriginal;
	}

	public void setRevertOriginal(ArrayList<revertCPUTiles> revertOriginal) {
		this.revertOriginal = revertOriginal;
	}

	

}
