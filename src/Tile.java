import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Tile extends JButton {
	private char letter;
	private int value;
	private String path;
	private String pathColored;
	private String originalPath;
	private String bigPath;
	private String playerSelected;
	private boolean selected;
	private boolean jolly;
	
	public Tile(char letter, int value, String path, boolean jolly, String pathColored, String originalPath, String bigPath, String playerSelected){
		super();
		this.letter=letter;
		this.value=value;
		this.path=path;
		this.pathColored=pathColored;
		this.originalPath=originalPath;
		this.bigPath=bigPath;
		this.playerSelected=playerSelected;
		this.selected=false;
		this.jolly=jolly;
		this.setIcon(new ImageIcon(path));
		this.setMargin(new Insets(0,0,0,0));
		this.setBorder(BorderFactory.createEmptyBorder());
		
	}
	
	public String getPlayerSelected() {
		return playerSelected;
	}

	public void setPlayerSelected(String playerSelected) {
		this.playerSelected = playerSelected;
	}
	
	public String getBigPath() {
		return bigPath;
	}

	public void setBigPath(String bigPath) {
		this.bigPath = bigPath;
	}

	public String getOriginalPath() {
		return originalPath;
	}

	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}

	public boolean isJolly() {
		return jolly;
	}

	public void setJolly(boolean jolly) {
		this.jolly = jolly;
	}

	public String getPath() {
		return path;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public char getLetter() {
		return letter;
	}


	public int getValue() {
		return value;
	}

	public void setPath(String path) {
		this.path = path;
		this.setIcon(new ImageIcon(path));
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getPathColored() {
		return pathColored;
	}

	public void setPathColored(String pathColored) {
		this.pathColored = pathColored;
	}

	
	
}
