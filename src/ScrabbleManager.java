import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalScrollBarUI;



public class ScrabbleManager extends JFrame {
	private static Box box;										//Sacchetto contenente le 120 lettere iniziali
	private static Player player;								//Giocatore 
	private static Computer cpu;								
	private Board board;										
	private final int NUM_LETTERS=7;
	public static JButton changeLetters;
	private static JPanel southCenter;
	private JPanel southPanel;
	private JPanel changePanel;
	private JPanel southEast;
	private JPanel southWest;
	private static JPanel northPanel;
	private JPanel northEast;
	private JPanel northCenter;
	private JPanel northWest;
	private ArrayList<JCheckBox> option;
	private ArrayList<TileBoard> tilesPlaced;
	private JPanel centerMain;
	private JPanel center;
	private File dictionary;
	private JButton confirm;
	private JButton cancel;
	private JButton cancelHint;
	private JButton confirmHint;
	private JButton changeHint;
	private ArrayList<TileBoard> newWord;
	private int possibleTotal;
	private String possibleWord;
	private boolean firstTurn=false;
	private boolean error;
	private String textError;
	private JFrame scrabble;
	private int countTurn;
	private Timer timer;
	private Timer timerScore;
	private Timer timerCPU;
	private JLabel cpuName;
	JLabel cpuNameValue;
	private JLabel cpuScore;
	JLabel cpuScoreValue;
	private JLabel playerName;
	JLabel playerNameValue;
	private JLabel playerScore;
	JLabel playerScoreValue;
	private int scoreHint;
	private PlaceWord placedWord;
	private boolean saveStatus;
	private String nameFile;
	private static Color green;
	private static Color grey;
	private static Color orange;
	private static Color darkBlue;
	private static Color lightBlue;
	private static Color pink;
	private static Color darkGrey;
	private static Color white;
	private static Font museo;
	private static Font museo2;
	private static Font museo3;
	private static Font proximaNova;
	private static Font proximaNova2;
	private JButton salva;
	private JButton carica;
	private JButton newGame;
	private JButton guide;
	private JButton statistics;
	private JButton hint;
	private int countHint;
	private boolean addHint;
	private JButton vocabulary;
	private JButton boxButton;
	private JPanel boxPanel;
	private JButton confirmBox;
	private JPanel lettersTable;
	private JPanel vocabularyPanel;
	private JPanel mainCorePanel;
	private JPanel corePanel;
	private JPanel playersInfoPanel;
	private JPanel infoContainer;
	private JPanel vs;
	private JPanel buttonsPanel;
	private JPanel supportPanel;
	private JPanel supportPanelDict;
	private JLabel text;
	final JTextField textField01;
	private JLabel known;
	private JPanel searchWord;
	private JPanel browse;
	private JTextArea dictContent;
	private JPanel lettersPanel;
	private ScrabbleManager scrabbleM;
	private JPanel letters;
	private JPanel guidePanel;
	private JPanel guideImg;
	private JButton guideImgBtn;
	private JButton guideImgBtn2;
	private JButton guideImgBtn3;
	private JButton guideImgBtn4;
	private JButton guideImgBtn5;
	private JButton avantiBtn1;
	private JButton avantiBtn2;
	private JButton avantiBtn3;
	private JButton avantiBtn4;
	private JButton indietro1;
	private JButton indietro2;
	private JButton indietro3;
	private JButton indietro4;
	private JPanel btnsPanel;
	private JButton BackGameBtnGuide;
	private JPanel statsPanel;
	private JButton win;
	JButton lostBtn;
	private JButton jollyBtn;
	private JButton error1Btn;
	private JButton error2Btn;
	private JButton error3Btn;
	private JButton error4Btn;
	private JButton error5Btn;
	private JButton noHintPlayer;
	private JPanel centerStat;
	
	//classi per gestire i TIMER
	//"scolora" le lettere messe dalla cpu sulla board
	class lettersTimerTask extends TimerTask {
		public void run() {
			 for(int i=0; i<cpu.getRevertOriginal().size();i++){
				   board.getCell(cpu.getRevertOriginal().get(i).getRow(), cpu.getRevertOriginal().get(i).getCol()).setPath(cpu.getRevertOriginal().get(i).getOriginalPath());
			   }
			timer.cancel(); // Terminate the timer thread
		}
	}
	
	//"scolora" punteggio
	class scoreTimerTask extends TimerTask {
		public void run() {
			cpuScoreValue.setForeground(green);
			playerScoreValue.setForeground(green);
			timerScore.cancel(); // Terminate the timer thread
		}
	}
	
	//Cede turno a CPU
	class CPUroundTask extends TimerTask {
		public void run() {
			computerRound(board);
			timerCPU.cancel(); // Terminate the timer thread
		}
	}
	
	//Classe per personalizzare la scrollbar
	static class MyScrollbarUI extends MetalScrollBarUI {

        private Image imageThumb, imageTrack;
        private JButton b = new JButton() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Dimension getPreferredSize() {
                return new Dimension(0, 0);
            }

        };

        MyScrollbarUI() {
        	try {
				imageThumb = ImageIO.read(new File("img/thumb.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
				imageTrack = ImageIO.read(new File("img/track.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
            g.setColor(Color.blue);
            ((Graphics2D) g).drawImage(imageThumb,
                r.x, r.y, r.width, r.height, null);
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
            ((Graphics2D) g).drawImage(imageTrack,
                r.x, r.y, r.width, r.height, null);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return b;
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return b;
        }
    }

	
	//Costruttore  
	public ScrabbleManager (){
		super("Scarabeo");
		box=new Box();				//Istanziazione delle lettere nel sacchetto
		board=new Board();			//Istanziazione della tabella di gioco
		scrabble=this;
		countTurn=0;
		countHint=2;				//Numero hint iniziali 
		
		//Estrazione delle 7 lettere iniziali da passare al giocatore
		//E istanziazione del giocatore
		ArrayList<Tile> extractedLetters=new ArrayList<Tile> ();
		ArrayList<Tile> extractedLettersCpu=new ArrayList<Tile> ();
		for(int i=0; i<NUM_LETTERS; i++)
			extractedLetters.add(box.randomExtraction());
		player=new Player(extractedLetters);
		
		//Estrazione delle 7 lettere iniziali per il computer
		//E istanziazione del computer
		for(int i=0; i<NUM_LETTERS; i++)
			extractedLettersCpu.add(box.randomExtraction());
		cpu=new Computer(extractedLettersCpu);
		
		//CREAZIONE FONT
		//Museo
		museo = null;
        try {
            //create the font to use. Specify the size!
            museo = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Museo300-Regular.otf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Museo300-Regular.otf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e)
        {
            e.printStackTrace();
        }
        
        museo2 = null;
        try {
            //create the font to use. Specify the size!
            museo2 = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Museo300-Regular.otf")).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Museo300-Regular.otf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e)
        {
            e.printStackTrace();
        }
        
        museo3 = null;
        try {
            //create the font to use. Specify the size!
            museo3 = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Museo300-Regular.otf")).deriveFont(60f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Museo300-Regular.otf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e)
        {
            e.printStackTrace();
        }
        
        try {
             Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Signika-Semibold.otf")).deriveFont(30f);
             GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
             ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Signika-Semibold.otf")));
        } catch (IOException e) {
             e.printStackTrace();
        }
        catch(FontFormatException e){
             e.printStackTrace();
        }
        
        //ProximaNova
      	proximaNova = null;
        try {
             proximaNova = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Proxima Nova Alt Light.otf")).deriveFont(14f);
             GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
             ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Proxima Nova Alt Light.otf")));
        } catch (IOException e) {
             e.printStackTrace();
        }
        catch(FontFormatException e){
             e.printStackTrace();
        }
        
        //ProximaNova2
      	proximaNova2 = null;
        try {
             proximaNova2 = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Proxima Nova Alt Light.otf")).deriveFont(20f);
             GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
             ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Proxima Nova Alt Light.otf")));
        } catch (IOException e) {
             e.printStackTrace();
        }
        catch(FontFormatException e){
             e.printStackTrace();
        }
        
        //COLORI
        green = new Color (31, 187, 166);
        grey = new Color (158, 167, 179);
        darkBlue = new Color (50, 58, 69);
        lightBlue = new Color (237, 237, 239);
        orange = new Color (242, 121, 53);
        white= new Color (252, 252, 253);
        pink = new Color (255, 103, 102);
        darkGrey = new Color (93, 93, 93);
        
		JPanel mainPanel=new JPanel(new BorderLayout(0,50));
		add(mainPanel);
		//Pannelli per BARRA IN ALTO
		JPanel cover = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		cover.setBackground(white);
		//BOTTONE LOGO
		BufferedImage buttonLogo = null;
		try {
			buttonLogo = ImageIO.read(new File("img/logo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final JButton logo=new JButton(new ImageIcon(buttonLogo));
		logo.setBorder(BorderFactory.createEmptyBorder());
		logo.setContentAreaFilled(false);
		
		logo.addMouseListener(new MouseAdapter()
		{
			      public void mouseEntered(MouseEvent evt){
			           ImageIcon img = new ImageIcon("img/logoHover.png");
			           logo.setIcon(img);
			      }
			      public void mouseExited(MouseEvent evt) {
			    	  ImageIcon img = new ImageIcon("img/logo.png");
			          logo.setIcon(img);
			      }
			      public void mousePressed(MouseEvent evt){
			           ImageIcon img = new ImageIcon("img/logo.png");
			           logo.setIcon(img);
			      }
			      public void mouseReleased(MouseEvent evt){
			          	ImageIcon img = new ImageIcon("img/logo.png");
			            logo.setIcon(img);
			      } 
		});
		gc.gridx=0;
		gc.gridy=0;
		gc.weightx=1;
		gc.anchor=GridBagConstraints.NORTHWEST;
		gc.insets=new Insets(2,0,0,2);
		cover.add(logo, gc);
		
		//Menu
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(1,5));
		menuPanel.setBackground(white);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 0.5;
		gc.gridx = 2;
		gc.gridy = 0;
		cover.add(menuPanel, gc);
		//BOTTONI MENU
		//Nuova partita
		BufferedImage nuovaPartitaIcon = null;
		try {
			nuovaPartitaIcon = ImageIO.read(new File("img/titles/newGame.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newGame=new JButton(new ImageIcon(nuovaPartitaIcon));
		newGame.setBorder(BorderFactory.createEmptyBorder());
		newGame.setContentAreaFilled(false);
		newGame.setActionCommand("nuova partita");
		newGame.addActionListener(new ButtonClickListener());
		newGame.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/titles/newGameHover.png");
	            	newGame.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/titles/newGame.png");
	            	newGame.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/titles/newGameHover.png");
	            	newGame.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/titles/newGameHover.png");
	            	newGame.setIcon(img);
	            } 
	        });
		//Salva partita
		BufferedImage salvaPartitaIcon = null;
		try {
			salvaPartitaIcon = ImageIO.read(new File("img/titles/salvaPartita.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		salva=new JButton(new ImageIcon(salvaPartitaIcon));
		salva.setActionCommand("salva");
		salva.addActionListener(new ButtonClickListener());
		salva.setBorder(BorderFactory.createEmptyBorder());
		salva.setContentAreaFilled(false);
		salva.addMouseListener(new MouseAdapter()
		{
            public void mouseEntered(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/salvaPartitaHover.png");
            	salva.setIcon(img);
            }
            public void mouseExited(MouseEvent evt) {
            	ImageIcon img = new ImageIcon("img/titles/salvaPartita.png");
            	salva.setIcon(img);
            }
            public void mousePressed(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/salvaPartitaHover.png");
            	salva.setIcon(img);
            }
            public void mouseReleased(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/salvaPartitaHover.png");
            	salva.setIcon(img);
            } 
        });
		
		//Carica partita
		BufferedImage caricaPartitaIcon = null;
		try {
			caricaPartitaIcon = ImageIO.read(new File("img/titles/carica.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		carica=new JButton(new ImageIcon(caricaPartitaIcon));
		carica.setBorder(BorderFactory.createEmptyBorder());
		carica.setContentAreaFilled(false);
		carica.setActionCommand("carica");
		carica.addActionListener(new ButtonClickListener());
		carica.addMouseListener(new MouseAdapter()
		{
            public void mouseEntered(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/caricaHover.png");
            	carica.setIcon(img);
            }
            public void mouseExited(MouseEvent evt) {
            	ImageIcon img = new ImageIcon("img/titles/carica.png");
            	carica.setIcon(img);
            }
            public void mousePressed(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/caricaHover.png");
            	carica.setIcon(img);
            }
            public void mouseReleased(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/caricaHover.png");
            	carica.setIcon(img);
            } 
        });
		
		//Bottone statistiche
		BufferedImage statisticheIcon = null;
		try {
			statisticheIcon = ImageIO.read(new File("img/titles/statistiche.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		statistics = new JButton(new ImageIcon(statisticheIcon));
		statistics.setBorder(BorderFactory.createEmptyBorder());
		statistics.setContentAreaFilled(false);
		statistics.setActionCommand("statistiche");
		statistics.addActionListener(new ButtonClickListener());
		statistics.setMargin(new Insets(0,0,0,0));
		statistics.addMouseListener(new MouseAdapter()
		{
            public void mouseEntered(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/statisticheHover.png");
            	statistics.setIcon(img);
            }
            public void mouseExited(MouseEvent evt) {
            	ImageIcon img = new ImageIcon("img/titles/statistiche.png");
            	statistics.setIcon(img);
            }
            public void mousePressed(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/statisticheHover.png");
            	statistics.setBorder(BorderFactory.createEmptyBorder());
            	statistics.setIcon(img);
            }
            public void mouseReleased(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/statisticheHover.png");
            	statistics.setBorder(BorderFactory.createEmptyBorder());
            	statistics.setIcon(img);
            } 
        });
		
		//Bottone guida
		BufferedImage guidaIcon = null;
		try {
			guidaIcon = ImageIO.read(new File("img/titles/guida.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		guide = new JButton(new ImageIcon(guidaIcon));
		guide.setBorder(BorderFactory.createEmptyBorder());
		guide.setContentAreaFilled(false);
		guide.setActionCommand("guida");
		guide.addActionListener(new ButtonClickListener());
		guide.addMouseListener(new MouseAdapter()
		{
            public void mouseEntered(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/guidaHover.png");
            	guide.setIcon(img);
            }
            public void mouseExited(MouseEvent evt) {
            	ImageIcon img = new ImageIcon("img/titles/guida.png");
            	guide.setIcon(img);
            }
            public void mousePressed(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/guida.png");
            	guide.setBorder(BorderFactory.createEmptyBorder());
            	guide.setIcon(img);
            }
            public void mouseReleased(MouseEvent evt){
            	ImageIcon img = new ImageIcon("img/titles/guida.png");
            	guide.setIcon(img);
            } 
        });
		
		menuPanel.add(newGame);
		menuPanel.add(salva);
		menuPanel.add(carica);
		menuPanel.add(statistics);
		menuPanel.add(guide);
		
		
		//Pannelli per la PARTE CENTRALE!!
		mainCorePanel = new JPanel();
		mainCorePanel.setOpaque(false);
		corePanel = new JPanel();
		corePanel.setLayout(new GridLayout(1,2));
		mainCorePanel.add(corePanel);
		
		//Pannelli per la parte CENTER
		//BOARD
		centerMain=new JPanel();
		centerMain.setBackground(lightBlue);
		centerMain.setPreferredSize(new Dimension(600,700));
		center=new JPanel();
		center.setOpaque(false);
		center.setLayout(new GridLayout(board.getCOLUMNS(), board.getROWS()));
		center.setBorder(BorderFactory.createLineBorder(Color.white,10, true));
		//centerMain.add(centerWest);
		centerMain.add(center);
		
		for(int i=0; i<board.getROWS(); i++){
			for(int j=0; j<board.getCOLUMNS(); j++){
				center.add(board.getBoard()[i][j]);
				board.getBoard()[i][j].addActionListener(new ButtonClickListener());
				board.getBoard()[i][j].setActionCommand("tileBoard");
			}	
		}
		
		center.repaint();
		center.setVisible(true);
		corePanel.add(centerMain);
		
		//Pannelli relativi ai GIOCATORI
		playersInfoPanel = new JPanel();
		infoContainer = new JPanel();
		infoContainer.setOpaque(false);
		infoContainer.setPreferredSize(new Dimension(900,900));
		playersInfoPanel.setLayout(new BorderLayout(0,20));
		playersInfoPanel.setOpaque(false);
		infoContainer.add(playersInfoPanel);
		corePanel.add(infoContainer);
		
		//Pannelli per la CPU
		northPanel=new JPanel();
		northPanel.setBackground(lightBlue);
		northPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		northEast=new JPanel();
		northCenter=new JPanel();
		northCenter.setPreferredSize(new Dimension(550,100));
		northWest=new JPanel(new GridLayout(4,1));
		northWest.setPreferredSize(new Dimension(100, 105));
        cpuName=new JLabel("Nome: ");
        cpuName.setFont(proximaNova);
        cpuName.setForeground(grey);
        cpuNameValue=new JLabel(cpu.getName());
        cpuNameValue.setFont(museo);
        cpuNameValue.setForeground(green);
        northWest.add(cpuName);
        northWest.add(cpuNameValue);
        cpuScore=new JLabel("Punteggio: ");
        cpuScore.setFont(proximaNova);
        cpuScore.setForeground(grey);
        cpuScoreValue= new JLabel(""+cpu.getScore());
        cpuScoreValue.setFont(museo);
        cpuScoreValue.setForeground(green);
        northWest.add(cpuScore);
        northWest.add(cpuScoreValue);
		for(int i=0; i<NUM_LETTERS; i++)
			northCenter.add(cpu.getLetters().get(i) );
		
		BufferedImage empty = null;
		try {
			empty = ImageIO.read(new File("img/buttons/empty.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JButton emptyBtn=new JButton(new ImageIcon(empty));
		emptyBtn.setBorder(BorderFactory.createEmptyBorder());
		emptyBtn.setContentAreaFilled(false);
		northEast.add(emptyBtn);
		northPanel.add(northWest);
		northPanel.add(northCenter);
		northPanel.add(northEast);
		northEast.setOpaque(false);
		northWest.setOpaque(false);
		northCenter.setOpaque(false);
		
		//Pannello stacco
		vs = new JPanel();
		vs.setPreferredSize(new Dimension(200,180));
		BufferedImage vsIcon = null;
		try {
			vsIcon = ImageIO.read(new File("img/vs.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JButton vsButton=new JButton(new ImageIcon(vsIcon));
		vsButton.setBorder(BorderFactory.createEmptyBorder());
		vsButton.setContentAreaFilled(false);
		vs.add(vsButton);
		
		
		//Pannelli player
		southPanel=new JPanel();
		southPanel.setBackground(lightBlue);
		changePanel= new JPanel(new BorderLayout());
		changePanel.setVisible(false);
		southCenter=new JPanel();
		southCenter.setPreferredSize(new Dimension(550, 100));
		southEast=new JPanel(new GridLayout(2,1));
		southWest=new JPanel(new GridLayout(4,1));
		southWest.setPreferredSize(new Dimension(100, 105));
		for(int i=0; i<NUM_LETTERS; i++){
			southCenter.add(player.getLetters().get(i) );
			player.getLetters().get(i).addActionListener(new ButtonClickListener() );
			player.getLetters().get(i).setActionCommand("tile");
		}
		playerName=new JLabel("Nome: ");
		playerName.setForeground(grey);
		playerName.setFont(proximaNova);
		playerNameValue=new JLabel("" + player.getName());
		playerNameValue.setForeground(green);
		playerNameValue.setFont(museo);
		southWest.add(playerName);
		southWest.add(playerNameValue);
		playerScore=new JLabel("Punteggio: ");
		playerScore.setForeground(grey);
		playerScore.setFont(proximaNova);
		playerScoreValue=new JLabel("" + player.getScore());
		playerScoreValue.setForeground(green);
		playerScoreValue.setFont(museo);
		southWest.add(playerName);
		southWest.add(playerNameValue);
		southWest.add(playerScore);
		southWest.add(playerScoreValue);
		
		//BOTTONE CAMBIA LETTERE
		BufferedImage buttonIconChangeL = null;
		try {
			buttonIconChangeL = ImageIO.read(new File("img/buttons/cambia.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		changeLetters=new JButton(new ImageIcon(buttonIconChangeL));
		changeLetters.setBorder(BorderFactory.createEmptyBorder());
		changeLetters.setContentAreaFilled(false);
		changeLetters.setActionCommand("Cambia lettere");
		//listener per il bottone
		changeLetters.addActionListener(new ButtonClickListener());
		changeLetters.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/cambiaHover.png");
	            	changeLetters.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/cambia.png");
	            	changeLetters.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/cambiaHover.png");
	            	changeLetters.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/cambia.png");
	            	changeLetters.setIcon(img);
	            } 
	        });
		southEast.add(changeLetters);
		
		//BOTTONE ANNULLA
		BufferedImage buttonIconCancel = null;
		try {
			buttonIconCancel = ImageIO.read(new File("img/buttons/annulla.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cancel=new JButton(new ImageIcon(buttonIconCancel));
		cancel.setBorder(BorderFactory.createEmptyBorder());
		cancel.setContentAreaFilled(false);
		cancel.setActionCommand("cancel");
		cancel.setVisible(false);
		cancel.addActionListener(new ButtonClickListener() );
		cancel.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/annullaHover.png");
	            	cancel.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/annulla.png");
	            	cancel.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/annullaHover.png");
	            	cancel.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/annulla.png");
	            	cancel.setIcon(img);
	            } 
	        });
		southEast.add(cancel);	
		
		//BOTTONE CONFERMA
		BufferedImage buttonIcon = null;
		try {
			buttonIcon = ImageIO.read(new File("img/buttons/conferma.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		confirm=new JButton(new ImageIcon(buttonIcon));
		confirm.setBorder(BorderFactory.createEmptyBorder());
		confirm.setContentAreaFilled(false);
		confirm.setActionCommand("conferma");
		confirm.addActionListener(new ButtonClickListener());
		confirm.setVisible(false);
		confirm.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/confermaHover.png");
	            	confirm.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/conferma.png");
	            	confirm.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/confermaHover.png");
	            	confirm.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/conferma.png");
	            	confirm.setIcon(img);
	            } 
	        });
		//southEast.add(confirm);		
		
		southPanel.add(southWest);
		southPanel.add(southCenter);
		southPanel.add(southEast);	
		southWest.setOpaque(false);
		southCenter.setOpaque(false);
		southEast.setOpaque(false);
		
		//Pannelli per bottoni circolari
		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setOpaque(false);
		buttonsContainer.setPreferredSize(new Dimension(800,200));
		buttonsPanel = new JPanel();
		buttonsContainer.add(buttonsPanel);
		buttonsPanel.setLayout(new GridLayout(1,5,10,10));
		
		buttonsPanel.setOpaque(false);
		//BOTTONI CIRCOLARI
		//BOX
		BufferedImage buttonBoxIcon = null;
		try {
			buttonBoxIcon = ImageIO.read(new File("img/buttons/box"+box.getNumLetters()+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boxButton=new JButton(new ImageIcon(buttonBoxIcon));
		boxButton.setBorder(BorderFactory.createEmptyBorder());
		boxButton.setContentAreaFilled(false);
		boxButton.setActionCommand("box button");
		boxButton.addActionListener(new ButtonClickListener());
		boxButton.setVisible(true);
		boxButton.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/box"+box.getNumLetters()+"hover.png");
	            	boxButton.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/box"+box.getNumLetters()+".png");
	            	boxButton.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/box"+box.getNumLetters()+"hover.png");
	            	boxButton.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/box"+box.getNumLetters()+"hover.png");
	            	boxButton.setIcon(img);
	            } 
	        });
		
		buttonsPanel.add(boxButton);
		
		//VOCABULARY
		BufferedImage buttonDictIcon = null;
		try {
			buttonDictIcon = ImageIO.read(new File("img/buttons/book.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vocabulary=new JButton(new ImageIcon(buttonDictIcon));
		vocabulary.setBorder(BorderFactory.createEmptyBorder());
		vocabulary.setPreferredSize(new Dimension(90,115));
		vocabulary.setContentAreaFilled(false);
		vocabulary.setActionCommand("vocabulary");
		vocabulary.addActionListener(new ButtonClickListener());
		vocabulary.setVisible(true);
		vocabulary.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/bookhover.png");
	            	vocabulary.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/book.png");
	            	vocabulary.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/bookhover.png");
	            	vocabulary.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/bookhover.png");
	            	vocabulary.setIcon(img);
	            } 
	        });
		
		buttonsPanel.add(vocabulary);
		
		//HINT
		BufferedImage buttonHintIcon = null;
		try {
			buttonHintIcon = ImageIO.read(new File("img/buttons/hint" + countHint + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hint=new JButton(new ImageIcon(buttonHintIcon));
		hint.setBorder(BorderFactory.createEmptyBorder());
		hint.setContentAreaFilled(false);
		hint.setActionCommand("hint");
		hint.addActionListener(new ButtonClickListener());
		hint.setVisible(true);
		hint.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/hint" + countHint + "hover.png");
	            	hint.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/hint" + countHint + ".png");
	            	hint.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/hint" + countHint + "hover.png");
	            	hint.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/hint" + countHint + "hover.png");
	            	hint.setIcon(img);
	            } 
	        });
		
		buttonsPanel.add(hint);
		
		//Aggiunta dei pannelli a playersInfoPanel
		JPanel southContainer = new JPanel(new GridLayout(2,1));
		southContainer.add(southPanel, BorderLayout.NORTH);
		southContainer.add(buttonsContainer, BorderLayout.SOUTH);
		//northPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		playersInfoPanel.add(northPanel, BorderLayout.NORTH);
		playersInfoPanel.add(vs, BorderLayout.CENTER);
		playersInfoPanel.add(southContainer, BorderLayout.SOUTH);
		
		//Pannello supporto box e dictionary
		supportPanel = new JPanel();
		supportPanelDict = new JPanel();

		//Pannello della BOX
		//Visualizza il contenuto della box quando richiesto
		boxPanel = new JPanel();
		boxPanel.setOpaque(false);
		boxPanel.setLayout(new BorderLayout());
		lettersTable= new JPanel();
		lettersTable.setLayout(new GridLayout(8,1,5,5));
		lettersTable.setOpaque(false);
		
		JPanel buttonLine = new JPanel();
		buttonLine.setOpaque(false);
		BufferedImage buttonOKIcon = null;
		try {
			buttonOKIcon = ImageIO.read(new File("img/buttons/ok.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		confirmBox = new JButton(new ImageIcon(buttonOKIcon));
		confirmBox.setBorder(BorderFactory.createEmptyBorder());
		confirmBox.setContentAreaFilled(false);
		confirmBox.setActionCommand("confirmBox");
		confirmBox.addActionListener(new ButtonClickListener());
		confirmBox.setVisible(true);
		confirmBox.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/okHover.png");
	            	confirmBox.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/ok.png");
	            	confirmBox.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/okHover.png");
	            	confirmBox.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/okHover.png");
	            	confirmBox.setIcon(img);
	            } 
	        });
		
		buttonLine.add(confirmBox);
		
		JPanel titleBox = new JPanel();
		titleBox.setOpaque(false);
		BufferedImage boxIcon = null;
		try {
			boxIcon = ImageIO.read(new File("img/titles/box.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JButton boxTitle=new JButton(new ImageIcon(boxIcon));
		boxTitle.setBorder(BorderFactory.createEmptyBorder());
		boxTitle.setContentAreaFilled(false);
		titleBox.add(boxTitle);
		
		//Aggiunta dei pannelli al principale(box)
		JPanel northSupport = new JPanel(new GridLayout(2,1,0,0));
		northSupport.setOpaque(false);
		text = new JLabel();
		text.setText("Totale lettere rimanenti: " + box.getNumLetters());
		text.setFont(museo);
		text.setForeground(grey);
		northSupport.add(titleBox);
		northSupport.add(text);
		boxPanel.add(northSupport, BorderLayout.NORTH);
		boxPanel.add(lettersTable, BorderLayout.CENTER);
		boxPanel.add(buttonLine, BorderLayout.SOUTH);
		supportPanel.add(boxPanel);
		
		
		//Parte relativa a VOCABULARY
		//Pannello vocabulary
		vocabularyPanel = new JPanel();
		vocabularyPanel.setOpaque(false);
		vocabularyPanel.setLayout(new BorderLayout(20,20));
		JPanel titleDict = new JPanel();
		titleDict.setOpaque(false);
		BufferedImage dictIcon = null;
		try {
			dictIcon = ImageIO.read(new File("img/titles/diz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JButton dictTitle=new JButton(new ImageIcon(dictIcon));
		dictTitle.setBorder(BorderFactory.createEmptyBorder());
		dictTitle.setContentAreaFilled(false);
		titleDict.add(dictTitle);
		//Parte centrale del pannello
		JPanel dictCenter = new JPanel();
		dictCenter.setOpaque(false);
		dictCenter.setLayout(new BorderLayout(20,20));
		JLabel title2 = new JLabel ("Lettere a disposizione:");
		title2.setFont(museo);
		title2.setForeground(grey);
		letters = new JPanel();
		letters.setOpaque(false);
		letters.setPreferredSize(new Dimension(550, 100));
		for(int i=0; i<NUM_LETTERS; i++){
			String let;
			if(player.getLetters().get(i).isJolly())
				let = "jolly";
			else {
				let = "" + player.getLetters().get(i).getLetter();
				let.toLowerCase();
			}
			BufferedImage letterIcon = null;
			try {
				letterIcon = ImageIO.read(new File("img/tiles/tilesFlat/" + let + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JButton letterBtn=new JButton(new ImageIcon(letterIcon));
			letterBtn.setBorder(BorderFactory.createEmptyBorder());
			letterBtn.setContentAreaFilled(false);
			letters.add(letterBtn);
		}
		
		searchWord = new JPanel(new GridLayout(4,1,0,30));
		searchWord.setOpaque(false);
		JPanel cover1 = new JPanel(new GridLayout(2,1,0,0));
		cover1.setOpaque(false);
		JLabel titleSearchWord = new JLabel ("Cerca una parola sul dizionario");
		titleSearchWord.setFont(museo);
		titleSearchWord.setForeground(grey);
		titleSearchWord.setOpaque(false);
		cover1.add(titleSearchWord);
		textField01 = new JTextField(10);
        textField01.setBorder(new RoundedCornerBorder());
        textField01.setText("Parola da cercare");
        textField01.setPreferredSize(new Dimension(150,40));
        textField01.setFont(proximaNova2);
        textField01.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            	textField01.setText("");
            }

            public void focusLost(FocusEvent e) {
                // nothing
            }
        });
        cover1.add(textField01);
        searchWord.add(cover1);
        //BOTTONE INVIA
		BufferedImage sendIcon = null;
		try {
			sendIcon = ImageIO.read(new File("img/buttons/invia.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final JButton send = new JButton(new ImageIcon(sendIcon));
		send.setBorder(BorderFactory.createEmptyBorder());
		send.setContentAreaFilled(false);
		send.setActionCommand("invia");
		send.addActionListener(new ButtonClickListener());
		send.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/inviaHover.png");
	            	send.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/invia.png");
	            	send.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/inviaHover.png");
	            	send.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/inviaHover.png");
	            	send.setIcon(img);
	            } 
	        });
		searchWord.add(send);
        known = new JLabel("");
        known.setOpaque(false);
        known.setFont(museo);
        known.setVisible(false);
        searchWord.add(known);
		//Aggiunta dei pannelli alla parte centrale
		dictCenter.add(title2, BorderLayout.NORTH);
		dictCenter.add(letters, BorderLayout.CENTER);
		dictCenter.add(searchWord, BorderLayout.SOUTH);
		//Parte south
		JPanel back = new JPanel(new GridLayout(1,2));
		//Bottone torna indietro
		BufferedImage backIcon = null;
		try {
			backIcon = ImageIO.read(new File("img/buttons/back.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final JButton backBtn = new JButton(new ImageIcon(backIcon));
		backBtn.setBorder(BorderFactory.createEmptyBorder());
		backBtn.setContentAreaFilled(false);
		backBtn.setActionCommand("confirmDict");
		backBtn.addActionListener(new ButtonClickListener());
		backBtn.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/backHover.png");
	            	backBtn.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/back.png");
	            	backBtn.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/backHover.png");
	            	backBtn.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/back.png");
	            	backBtn.setIcon(img);
	            } 
	        });
		back.add(backBtn);
		//Bottone sfoglia il dizionario
		BufferedImage sfogliaIcon = null;
		try {
			sfogliaIcon = ImageIO.read(new File("img/buttons/sfoglia.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final JButton sfogliaBtn = new JButton(new ImageIcon(sfogliaIcon));
		sfogliaBtn.setBorder(BorderFactory.createEmptyBorder());
		sfogliaBtn.setContentAreaFilled(false);
		sfogliaBtn.setActionCommand("sfoglia");
		sfogliaBtn.addActionListener(new ButtonClickListener());
		sfogliaBtn.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/sfogliaHover.png");
	            	sfogliaBtn.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/sfoglia.png");
	            	sfogliaBtn.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/sfogliaHover.png");
	            	sfogliaBtn.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/sfoglia.png");
	            	sfogliaBtn.setIcon(img);
	            } 
	        });
		back.add(sfogliaBtn);
		
		//Aggiunta dei pannelli a vocabularyPanel
		vocabularyPanel.add(titleDict, BorderLayout.NORTH);
		vocabularyPanel.add(dictCenter, BorderLayout.CENTER);
		vocabularyPanel.add(back, BorderLayout.SOUTH);
		supportPanelDict.add(vocabularyPanel);
		
		//Pannello sfoglia dizionario
		browse = new JPanel(new BorderLayout(20,30));
		browse.setOpaque(false);
		
	    dictContent = new JTextArea (20,30);
		dictContent.setEditable(false);
		dictContent.setFont(museo2);
		dictContent.setForeground(grey);
		dictContent.setOpaque(false);
		JScrollPane scroll = new JScrollPane (dictContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getHorizontalScrollBar().setUI(new MyScrollbarUI());
		scroll.getVerticalScrollBar().setUI(new MyScrollbarUI());
		JLabel browseTitle = new JLabel("Sfoglia il dizionario");
		browseTitle.setFont(museo3);
		browseTitle.setForeground(pink);
		JLabel description = new JLabel("Clicca su una lettera");
		description.setFont(museo);
		description.setForeground(darkGrey);
		lettersPanel = new JPanel(new GridLayout(3,10,3,3));
		fillBrowseDictPanel();
		JPanel titleDict2=new JPanel(new BorderLayout());
		titleDict2.add(browseTitle, BorderLayout.NORTH);
		titleDict2.add(description, BorderLayout.CENTER);
		titleDict2.add(lettersPanel, BorderLayout.SOUTH);
		JPanel btnDictPanel = new JPanel(new GridLayout(1,2));
		//Bottone OK browse dictionary
		BufferedImage okBrowseIcon = null;
		try {
			okBrowseIcon = ImageIO.read(new File("img/buttons/ok1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final JButton OKBtn = new JButton(new ImageIcon(okBrowseIcon));
		OKBtn.setBorder(BorderFactory.createEmptyBorder());
		OKBtn.setContentAreaFilled(false);
		OKBtn.setActionCommand("OKBrowseDict");
		OKBtn.addActionListener(new ButtonClickListener());
		OKBtn.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/ok1Hover.png");
	            	OKBtn.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/ok1.png");
	            	OKBtn.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/ok1Hover.png");
	            	OKBtn.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/ok1.png");
	            	OKBtn.setIcon(img);
	            } 
	        });
		btnDictPanel.add(OKBtn);
		//Bottone 'torna al gioco' browse dictionary
		BufferedImage backGameIcon = null;
		try {
			backGameIcon = ImageIO.read(new File("img/buttons/tornaGioco.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final JButton BackGameBtn = new JButton(new ImageIcon(backGameIcon));
		BackGameBtn.setBorder(BorderFactory.createEmptyBorder());
		BackGameBtn.setContentAreaFilled(false);
		BackGameBtn.setActionCommand("backGameBrowseDict");
		BackGameBtn.addActionListener(new ButtonClickListener());
		BackGameBtn.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGiocoHover.png");
	            	BackGameBtn.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGioco.png");
	            	BackGameBtn.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGiocoHover.png");
	            	BackGameBtn.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGioco.png");
	            	BackGameBtn.setIcon(img);
	            } 
	        });
		btnDictPanel.add(BackGameBtn);
		
		
		browse.add(titleDict2, BorderLayout.NORTH);
		browse.add(scroll, BorderLayout.CENTER);
		browse.add(btnDictPanel, BorderLayout.SOUTH);
		browseDictionary('a');
		
		
		//PANNELLO GUIDA
		guidePanel = new JPanel(new BorderLayout(10,10));
		//Titolo
		BufferedImage guideTitleImg = null;
		try {
			guideTitleImg = ImageIO.read(new File("img/titles/guidaTitle.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final JButton guideTitleBtn = new JButton(new ImageIcon(guideTitleImg));
		guideTitleBtn.setBorder(BorderFactory.createEmptyBorder());
		guideTitleBtn.setContentAreaFilled(false);
		
		//Pannello bottoni 
		//Bottone 'torna al gioco' browse dictionary
		backGameIcon = null;
		try {
			backGameIcon = ImageIO.read(new File("img/buttons/tornaGioco.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BackGameBtnGuide = new JButton(new ImageIcon(backGameIcon));
		BackGameBtnGuide.setBorder(BorderFactory.createEmptyBorder());
		BackGameBtnGuide.setContentAreaFilled(false);
		BackGameBtnGuide.setActionCommand("backGameGuide");
		BackGameBtnGuide.addActionListener(new ButtonClickListener());
		BackGameBtnGuide.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGiocoHover.png");
	            	BackGameBtnGuide.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGioco.png");
	            	BackGameBtnGuide.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGiocoHover.png");
	            	BackGameBtnGuide.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGioco.png");
	            	BackGameBtnGuide.setIcon(img);
	            } 
	        });
		//Bottone avanti1
		backGameIcon = null;
		try {
			backGameIcon = ImageIO.read(new File("img/buttons/avanti.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		avantiBtn1 = new JButton(new ImageIcon(backGameIcon));
		avantiBtn1.setBorder(BorderFactory.createEmptyBorder());
		avantiBtn1.setContentAreaFilled(false);
		avantiBtn1.setActionCommand("avanti1");
		avantiBtn1.addActionListener(new ButtonClickListener());
		avantiBtn1.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avantiHover.png");
	            	avantiBtn1.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/avanti.png");
	            	avantiBtn1.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avantiHover.png");
	            	avantiBtn1.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avanti.png");
	            	avantiBtn1.setIcon(img);
	            } 
	        });
		//Bottone avanti2
		backGameIcon = null;
		try {
			backGameIcon = ImageIO.read(new File("img/buttons/avanti.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		avantiBtn2 = new JButton(new ImageIcon(backGameIcon));
		avantiBtn2.setBorder(BorderFactory.createEmptyBorder());
		avantiBtn2.setContentAreaFilled(false);
		avantiBtn2.setActionCommand("avanti2");
		avantiBtn2.addActionListener(new ButtonClickListener());
		avantiBtn2.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avantiHover.png");
	            	avantiBtn2.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/avanti.png");
	            	avantiBtn2.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avantiHover.png");
	            	avantiBtn2.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avanti.png");
	            	avantiBtn2.setIcon(img);
	            } 
	        });
		//Bottone avanti3
		backGameIcon = null;
		try {
			backGameIcon = ImageIO.read(new File("img/buttons/avanti.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		avantiBtn3 = new JButton(new ImageIcon(backGameIcon));
		avantiBtn3.setBorder(BorderFactory.createEmptyBorder());
		avantiBtn3.setContentAreaFilled(false);
		avantiBtn3.setActionCommand("avanti3");
		avantiBtn3.addActionListener(new ButtonClickListener());
		avantiBtn3.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avantiHover.png");
	            	avantiBtn3.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/avanti.png");
	            	avantiBtn3.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avantiHover.png");
	            	avantiBtn3.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avanti.png");
	            	avantiBtn3.setIcon(img);
	            } 
	        });
		//Bottone avanti4
		backGameIcon = null;
		try {
			backGameIcon = ImageIO.read(new File("img/buttons/avanti.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		avantiBtn4 = new JButton(new ImageIcon(backGameIcon));
		avantiBtn4.setBorder(BorderFactory.createEmptyBorder());
		avantiBtn4.setContentAreaFilled(false);
		avantiBtn4.setActionCommand("avanti4");
		avantiBtn4.addActionListener(new ButtonClickListener());
		avantiBtn4.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avantiHover.png");
	            	avantiBtn4.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/avanti.png");
	            	avantiBtn4.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avantiHover.png");
	            	avantiBtn4.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/avanti.png");
	            	avantiBtn4.setIcon(img);
	            } 
	        });
		//Bottone indietro1
		try {
			backGameIcon = ImageIO.read(new File("img/buttons/indietro.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		indietro1 = new JButton(new ImageIcon(backGameIcon));
		indietro1.setBorder(BorderFactory.createEmptyBorder());
		indietro1.setContentAreaFilled(false);
		indietro1.setActionCommand("indietro1");
		indietro1.addActionListener(new ButtonClickListener());
		indietro1.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietroHover.png");
	            	indietro1.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/indietro.png");
	            	indietro1.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietroHover.png");
	            	indietro1.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietro.png");
	            	indietro1.setIcon(img);
	            } 
	        });
		//Bottone indietro2
		try {
			backGameIcon = ImageIO.read(new File("img/buttons/indietro.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		indietro2 = new JButton(new ImageIcon(backGameIcon));
		indietro2.setBorder(BorderFactory.createEmptyBorder());
		indietro2.setContentAreaFilled(false);
		indietro2.setActionCommand("indietro2");
		indietro2.addActionListener(new ButtonClickListener());
		indietro2.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietroHover.png");
	            	indietro2.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/indietro.png");
	            	indietro2.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietroHover.png");
	            	indietro2.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietro.png");
	            	indietro2.setIcon(img);
	            } 
	        });
		//Bottone indietro3
		try {
			backGameIcon = ImageIO.read(new File("img/buttons/indietro.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		indietro3 = new JButton(new ImageIcon(backGameIcon));
		indietro3.setBorder(BorderFactory.createEmptyBorder());
		indietro3.setContentAreaFilled(false);
		indietro3.setActionCommand("indietro3");
		indietro3.addActionListener(new ButtonClickListener());
		indietro3.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietroHover.png");
	            	indietro3.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/indietro.png");
	            	indietro3.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietroHover.png");
	            	indietro3.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietro.png");
	            	indietro3.setIcon(img);
	            } 
	        });
		//Bottone indietro4
		try {
			backGameIcon = ImageIO.read(new File("img/buttons/indietro.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		indietro4 = new JButton(new ImageIcon(backGameIcon));
		indietro4.setBorder(BorderFactory.createEmptyBorder());
		indietro4.setContentAreaFilled(false);
		indietro4.setActionCommand("indietro4");
		indietro4.addActionListener(new ButtonClickListener());
		indietro4.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietroHover.png");
	            	indietro4.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/indietro.png");
	            	indietro4.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietroHover.png");
	            	indietro4.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/indietro.png");
	            	indietro4.setIcon(img);
	            } 
	        });
				
		
		btnsPanel = new JPanel(new GridLayout(1,3));
		btnsPanel.add(BackGameBtnGuide);
		btnsPanel.add(avantiBtn1);
		
		//Pannello immaginiGuida
		guideImg = new JPanel();
		//imgGuide1
		BufferedImage guideImgIcon = null;
		try {
			guideImgIcon = ImageIO.read(new File("img/tiles/tilesFlat/warnings/guida1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		guideImgBtn = new JButton(new ImageIcon(guideImgIcon));
		guideImgBtn.setBorder(BorderFactory.createEmptyBorder());
		guideImgBtn.setContentAreaFilled(false);
		//imgGuide2
		try {
			guideImgIcon = ImageIO.read(new File("img/tiles/tilesFlat/warnings/guida2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		guideImgBtn2 = new JButton(new ImageIcon(guideImgIcon));
		guideImgBtn2.setBorder(BorderFactory.createEmptyBorder());
		guideImgBtn2.setContentAreaFilled(false);
		//imgGuide3
		try {
			guideImgIcon = ImageIO.read(new File("img/tiles/tilesFlat/warnings/guida3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		guideImgBtn3 = new JButton(new ImageIcon(guideImgIcon));
		guideImgBtn3.setBorder(BorderFactory.createEmptyBorder());
		guideImgBtn3.setContentAreaFilled(false);
		//imgGuide4
		try {
			guideImgIcon = ImageIO.read(new File("img/tiles/tilesFlat/warnings/guida4.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		guideImgBtn4 = new JButton(new ImageIcon(guideImgIcon));
		guideImgBtn4.setBorder(BorderFactory.createEmptyBorder());
		guideImgBtn4.setContentAreaFilled(false);
		//imgGuide5
		try {
			guideImgIcon = ImageIO.read(new File("img/tiles/tilesFlat/warnings/guida5.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		guideImgBtn5 = new JButton(new ImageIcon(guideImgIcon));
		guideImgBtn5.setBorder(BorderFactory.createEmptyBorder());
		guideImgBtn5.setContentAreaFilled(false);
		
		guideImg.add(guideImgBtn);
		
		
		
		//Aggiunta pannelli al pannello guidePanel
		guidePanel.add(guideTitleBtn, BorderLayout.NORTH);
		guidePanel.add(guideImg, BorderLayout.CENTER);
		guidePanel.add(btnsPanel, BorderLayout.SOUTH);
		
		//PANNELLO STATISTICHE
		statsPanel = new JPanel(new BorderLayout(70,70));
		//Titolo
		BufferedImage statsIcon = null;
		try {
			statsIcon = ImageIO.read(new File("img/titles/stat.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JButton statsBtn = new JButton(new ImageIcon(statsIcon));
		statsBtn.setBorder(BorderFactory.createEmptyBorder());
		statsBtn.setContentAreaFilled(false);
		
		//Bottone
		try {
			statsIcon = ImageIO.read(new File("img/buttons/tornaGioco.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final JButton BackGameBtnStats = new JButton(new ImageIcon(statsIcon));
		BackGameBtnStats.setBorder(BorderFactory.createEmptyBorder());
		BackGameBtnStats.setContentAreaFilled(false);
		BackGameBtnStats.setActionCommand("backGameStats");
		BackGameBtnStats.addActionListener(new ButtonClickListener());
		BackGameBtnStats.addMouseListener(new MouseAdapter()
		{
	            public void mouseEntered(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGiocoHover.png");
	            	BackGameBtnStats.setIcon(img);
	            }
	            public void mouseExited(MouseEvent evt) {
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGioco.png");
	            	BackGameBtnStats.setIcon(img);
	            }
	            public void mousePressed(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGiocoHover.png");
	            	BackGameBtnStats.setIcon(img);
	            }
	            public void mouseReleased(MouseEvent evt){
	            	ImageIcon img = new ImageIcon("img/buttons/tornaGioco.png");
	            	BackGameBtnStats.setIcon(img);
	            } 
	        });
		
		centerStat = new JPanel(new GridLayout(6,2,25,25));
		repaintStatsPanel();
		
		//Aggiunta pannelli a statsPanel
		statsPanel.add(statsBtn, BorderLayout.NORTH);
		statsPanel.add(BackGameBtnStats, BorderLayout.SOUTH);
		
		
		//AGGIUNTA DEI PANNELLI AL PRINCIPALE
		mainPanel.add(cover, BorderLayout.NORTH);
		mainPanel.add(corePanel, BorderLayout.CENTER);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Scarabeo");
		ImageIcon img = new ImageIcon("img/tiles/tilesFlat/s.png");
		setIconImage(img.getImage());
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setVisible(true);
		setResizable(false);
		
		//Img win
		BufferedImage winIcon = null;
		try {
			winIcon = ImageIO.read(new File("img/titles/vinto.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		win = new JButton(new ImageIcon(winIcon));
		win.setBorder(BorderFactory.createEmptyBorder());
		win.setContentAreaFilled(false);
		//Img lost
		BufferedImage lostIcon = null;
		try {
			lostIcon = ImageIO.read(new File("img/titles/perso.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lostBtn = new JButton(new ImageIcon(lostIcon));
		lostBtn.setBorder(BorderFactory.createEmptyBorder());
		lostBtn.setContentAreaFilled(false);
		//Img jolly
		BufferedImage jollyIcon = null;
		try {
			jollyIcon = ImageIO.read(new File("img/titles/jolly.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jollyBtn = new JButton(new ImageIcon(jollyIcon));
		jollyBtn.setBorder(BorderFactory.createEmptyBorder());
		jollyBtn.setContentAreaFilled(false);
		//Img errore Inserire almeno due lettere nel primo turno
		BufferedImage errorIcon = null;
		try {
			errorIcon = ImageIO.read(new File("img/titles/error1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		error1Btn = new JButton(new ImageIcon(errorIcon));
		error1Btn.setBorder(BorderFactory.createEmptyBorder());
		error1Btn.setContentAreaFilled(false);
		//Errore Posizione non corretta
		try {
			errorIcon = ImageIO.read(new File("img/titles/error2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		error2Btn = new JButton(new ImageIcon(errorIcon));
		error2Btn.setBorder(BorderFactory.createEmptyBorder());
		error2Btn.setContentAreaFilled(false);
		//Errore al primo turno lettera sulla stella
		try {
			errorIcon = ImageIO.read(new File("img/titles/error3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		error3Btn = new JButton(new ImageIcon(errorIcon));
		error3Btn.setBorder(BorderFactory.createEmptyBorder());
		error3Btn.setContentAreaFilled(false);
		//Errore Parola non conosciuta
		try {
			errorIcon = ImageIO.read(new File("img/titles/nonConosciuta.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		error4Btn = new JButton(new ImageIcon(errorIcon));
		error4Btn.setBorder(BorderFactory.createEmptyBorder());
		error4Btn.setContentAreaFilled(false);
		//Errore Manca intersezione
		try {
			errorIcon = ImageIO.read(new File("img/titles/error5.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		error5Btn = new JButton(new ImageIcon(errorIcon));
		error5Btn.setBorder(BorderFactory.createEmptyBorder());
		error5Btn.setContentAreaFilled(false);
		//No hint player
		try {
			errorIcon = ImageIO.read(new File("img/titles/noHint.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		noHintPlayer = new JButton(new ImageIcon(errorIcon));
		noHintPlayer.setBorder(BorderFactory.createEmptyBorder());
		noHintPlayer.setContentAreaFilled(false);

		
		//Array di appoggio delle lettere inserite dal player sulla board
		tilesPlaced=new ArrayList<TileBoard>();
		
	}
	
	
	//Disegna il pannello per visualizzare il contenuto della box
	public void paintBoxPanel(char let){
		int numLeft = 0;
   	 	String associatedPath = null;
   	 	BufferedImage letter = null;
   	 	
   	 	numLeft = box.getNumOfALetter(let);
   	 	associatedPath = box.getSinglePath(let);
   	 	System.out.println(associatedPath);
		 try {
			letter = ImageIO.read(new File(associatedPath));
		 } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
		JButton letterToAdd=new JButton(new ImageIcon(letter));
		letterToAdd.setBorder(BorderFactory.createEmptyBorder());
		letterToAdd.setContentAreaFilled(false);
		JLabel number = new JLabel();
   	 	number.setForeground(grey);
   	 	number.setFont(museo);
   	 	number.setText(""+numLeft);
   	 	lettersTable.add(letterToAdd);
   	 	lettersTable.add(number);
	}
	
	public void paintBrowseDictPanel(char let){
   	 	String associatedPath = null;
   	 	BufferedImage letter = null;
   	 	String command ="";
   	 	
   	 	associatedPath = box.getSinglePath(let);
   	 	System.out.println(associatedPath);
		 try {
			letter = ImageIO.read(new File(associatedPath));
		 } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
		command=""+let;
		JButton letterToAdd=new JButton(new ImageIcon(letter));
		letterToAdd.setBorder(BorderFactory.createEmptyBorder());
		letterToAdd.setContentAreaFilled(false);
		letterToAdd.setActionCommand(command);
		letterToAdd.addActionListener(new ButtonClickListener());
		lettersPanel.add(letterToAdd);
		
	}
	
	public void repaintStatsPanel(){
		//Pannello centrale
				
				centerStat.setBackground(lightBlue);
				centerStat.add(new JLabel(""));
				centerStat.removeAll();
				String line="";
				int matchPlay=0;
				int matchWin=0;
				int maxScoreFile=0;
				int percFile=0;
				try {
					BufferedReader read = new BufferedReader(new FileReader("stats.txt"));
					line=read.readLine();
					matchPlay=Integer.parseInt(line);
					line=read.readLine();
					matchWin=Integer.parseInt(line);
					line=read.readLine();
					percFile=Integer.parseInt(line);
					line=read.readLine();
					maxScoreFile=Integer.parseInt(line);
				    read.close();
				
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				JLabel name = new JLabel();
				name.setText(player.getName());
				name.setFont(museo3);
				name.setForeground(pink);
				
				JLabel playerLbl = new JLabel("Giocatore: ");
				playerLbl.setFont(museo);
				playerLbl.setForeground(green);
				
				JLabel totMatch=new JLabel("Partite giocate: ");
				totMatch.setFont(museo);
				totMatch.setForeground(green);
				
				JLabel totMatchValue = new JLabel();
				totMatchValue.setText(matchPlay+"");
				totMatchValue.setForeground(grey);
				totMatchValue.setFont(museo);
				
				JLabel totWin = new JLabel("Partite vinte: ");
				totWin.setFont(museo);
				totWin.setForeground(green);
				
				JLabel totWinValue = new JLabel("");
				totWinValue.setText(""+matchWin);
				totWinValue.setFont(museo);
				totWinValue.setForeground(grey);
				
				JLabel totLost = new JLabel("Partite perse: ");
				totLost.setFont(museo);
				totLost.setForeground(green);
				
				JLabel totLostValue = new JLabel("");
				totLostValue.setFont(museo);
				totLostValue.setForeground(grey);
				int lost=matchPlay-matchWin;
				totLostValue.setText(""+lost);
				
				JLabel perc = new JLabel("% vittorie: ");
				perc.setFont(museo);
				perc.setForeground(green);
				
				JLabel percValue = new JLabel("");
				percValue.setFont(museo);
				percValue.setForeground(grey);
				percValue.setText(""+percFile+"%");
				
				JLabel maxScore = new JLabel("Punteggio massimo: ");
				maxScore.setFont(museo);
				maxScore.setForeground(green);
				
				JLabel maxScoreValue = new JLabel();
				maxScoreValue.setFont(museo);
				maxScoreValue.setForeground(grey);
				maxScoreValue.setText(""+maxScoreFile);
				
				//Aggiunta al gridLayout
				centerStat.add(playerLbl);
				centerStat.add(name);
				centerStat.add(totMatch);
				centerStat.add(totMatchValue);
				centerStat.add(totWin);
				centerStat.add(totWinValue);
				centerStat.add(totLost);
				centerStat.add(totLostValue);
				centerStat.add(perc);
				centerStat.add(percValue);
				centerStat.add(maxScore);
				centerStat.add(maxScoreValue);
				
				statsPanel.add(centerStat, BorderLayout.CENTER);
				centerStat.repaint();
				centerStat.validate();
	}
	
	public void fillBrowseDictPanel(){
		paintBrowseDictPanel('A');
		paintBrowseDictPanel('B');
		paintBrowseDictPanel('C');
		paintBrowseDictPanel('D');
		paintBrowseDictPanel('E');
		paintBrowseDictPanel('F');
		paintBrowseDictPanel('G');
		paintBrowseDictPanel('H');
		paintBrowseDictPanel('I');
		paintBrowseDictPanel('L');
		paintBrowseDictPanel('M');
		paintBrowseDictPanel('N');
		paintBrowseDictPanel('O');
		paintBrowseDictPanel('P');
		paintBrowseDictPanel('Q');
		paintBrowseDictPanel('R');
		paintBrowseDictPanel('S');
		paintBrowseDictPanel('T');
		paintBrowseDictPanel('U');
		paintBrowseDictPanel('V');
		paintBrowseDictPanel('Z');
		
	}
	
	public void fillBoxPanel(){
		//Riempio la griglia del pannello box
   	 	paintBoxPanel('A');
   	 	paintBoxPanel('B');
   	 	paintBoxPanel('C');
   	 	paintBoxPanel('D');
   	 	paintBoxPanel('E');
   	 	paintBoxPanel('F');
   	 	paintBoxPanel('G');
   	 	paintBoxPanel('H');
	   	paintBoxPanel('I');
	   	paintBoxPanel('L');
	   	paintBoxPanel('M');
	   	paintBoxPanel('N');
	   	paintBoxPanel('O');
	   	paintBoxPanel('P');
	   	paintBoxPanel('Q');
	   	paintBoxPanel('R');
	   	paintBoxPanel('S');
	   	paintBoxPanel('T');
	   	paintBoxPanel('U');
	   	paintBoxPanel('V');
	   	paintBoxPanel('Z');
	   	BufferedImage letter = null;
		 try {
			letter = ImageIO.read(new File("img/tiles/tilesFlat/jolly_big.png"));
		 } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
		JButton letterToAdd=new JButton(new ImageIcon(letter));
		letterToAdd.setBorder(BorderFactory.createEmptyBorder());
		letterToAdd.setContentAreaFilled(false);
		JLabel number = new JLabel();
   	 	number.setForeground(grey);
   	 	number.setFont(museo);
   	 	number.setText(""+box.getNumJolly());
	   	lettersTable.add(letterToAdd);
   	 	lettersTable.add(number);
	   	
	}
	
	public void browseDictionary(char letter){
		String wordsToInsert="";
		dictionary=new File("it.txt");
	    try {
	    	LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(dictionary)));
	    	String lineRead = null;									 
	    	while((lineRead = reader.readLine()) != null) {
	    		if((lineRead.charAt(0)==letter)){
	    			//PAROLA TROVATA
	    			wordsToInsert+=lineRead+"\n";
	    			
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
	    dictContent.setText(wordsToInsert);
	}
	
	public void endGame(){
		int result = 0;
		String line="";
		float perc=0;
		int matchPlay=0;
		int matchWin=0;
		int maxScore=0;
		int winPlay=0;
		//Aggiorna statistiche
		try {
			BufferedReader read = new BufferedReader(new FileReader("stats.txt"));
			line=read.readLine();
			matchPlay=Integer.parseInt(line);
			line=read.readLine();
			matchWin=Integer.parseInt(line);
			line=read.readLine();
			line=read.readLine();
			maxScore=Integer.parseInt(line);
		    read.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel pareggio=new JLabel("Pareggio");
		pareggio.setFont(museo2);
		pareggio.setForeground(green);
		
		if(player.getScore()>cpu.getScore()){
			result=0;
			matchWin++;
			matchPlay++;
		}
		if (player.getScore()==cpu.getScore()){
			result=2;
			matchPlay++;
		}
		if (player.getScore()<cpu.getScore()){
			result=1;
			matchPlay++;
		} 
		 JPanel messagePane = new JPanel(new BorderLayout());
		 JPanel results=new JPanel(new GridLayout(2,2,20,0));
		 results.add(cpuNameValue);
		 results.add(cpuScoreValue);
		 results.add(playerNameValue);
		 results.add(playerScoreValue);
		 if(result==0)
			 messagePane.add(win, BorderLayout.NORTH);
		 if(result==1)
			 messagePane.add(lostBtn, BorderLayout.NORTH);
		 if(result==2)
			 messagePane.add(pareggio, BorderLayout.NORTH);
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
		
		
		FileOutputStream file;
		 try {
			file = new FileOutputStream("stats.txt");
			PrintStream output= new PrintStream(file);
			output.println(matchPlay);
			output.println(matchWin);
		    perc=((float)matchWin/(float)matchPlay)*100;	
		    winPlay=(int)perc;
		    output.println(winPlay);
		    if(player.getScore()>maxScore) output.println(player.getScore());
		    else output.println(maxScore);
			output.close();
		 } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
		newGame();
		whoStarts();
	}
	
	public void checkHorizontal(int x,int y){
		 //Controllo scorrendo la riga
		 int countDW=0;
		 int countTW=0;
		 textError="";
		 error=false;		
		 possibleWord="";
		 possibleTotal=0;
		 addHint=false;
		 
		 
		 for(int i=tilesPlaced.get(0).getY1()-1; i<board.getCOLUMNS(); i++){
			 if(board.getBoard()[x][i].isOccupied()==false){
				 y=i-1;
				 break;
			 }	      				 
		 }
		 newWord=new ArrayList<TileBoard>();
		 for(int i=y; i>=0; i--){
			 if((i==0)&&(board.getBoard()[x][i].isOccupied()==true)){
				 newWord.add(board.getBoard()[x][i]);
				 if( (x==0 && i==3) || (x==0 && i==11) || (x==2 && i==6) || (x==2 && i==8) || (x==3 && i==0) || (x==3 && i==14) || (x==6 && i==12) || (x==6 && i==2) || (x==8 && i==2)
						 || (x==8 && i==12) || (x==11 && i==0) || (x==11 && i==14) || (x==12 && i==6) || (x==12 && i==8) || (x==14 && i==3) || (x==14 && i==11)){
							 
							 countDW++;
							 System.out.println("COUNT DW" + countDW);
						 }
							
						 
						 if( (x==0 && i==7) || (x==1 && i==5) || (x==1 && i==9) || (x==5 && i==1) || (x==5 && i==13) || (x==7 && i==0) || (x==7 && i==14) || (x==9 && i==1) || (x==9 && i==13) 
						 || (x==13 && i==5) || (x==13 && i==9) || (x==14 && i==7) ){
							 countTW++;
							 System.out.println("COUNT TW" + countTW);
						 }
			 }
			 if((board.getBoard()[x][i].isOccupied()==false)||((i==0)&&(board.getBoard()[x][i].isOccupied()==true))){      					 
				 if(((newWord.size()==tilesPlaced.size())&&(firstTurn))||((newWord.size()>tilesPlaced.size())&&(!firstTurn))) {
					 for(int j=0; j<newWord.size(); j++){	        						
						 possibleWord=possibleWord+newWord.get(j).getLetter();
						 possibleTotal=possibleTotal+(newWord.get(j).getValue()*newWord.get(j).getMultiScore()); 
					 } 
					 break;
				 }
				 
				 
					 
				 if(newWord.size()<tilesPlaced.size()){
					 error=true;
					 textError="error2";
					 break;
				 }	        				
				 if((newWord.size()==tilesPlaced.size())){
					 error=true;
					 textError="error5";
					 break;
				 }
			 }
			 else{
 				newWord.add(board.getBoard()[x][i]); 
 				if( (x==0 && i==3) || (x==0 && i==11) || (x==2 && i==6) || (x==2 && i==8) || (x==3 && i==0) || (x==3 && i==14) || (x==6 && i==12) || (x==6 && i==2) || (x==8 && i==2)
						 || (x==8 && i==12) || (x==11 && i==0) || (x==11 && i==14) || (x==12 && i==6) || (x==12 && i==8) || (x==14 && i==3) || (x==14 && i==11)){
 					
							 countDW++;
							 System.out.println("COUNT DW" + countDW);
						 }
							
						 
						 if( (x==0 && i==7) || (x==1 && i==5) || (x==1 && i==9) || (x==5 && i==1) || (x==5 && i==13) || (x==7 && i==0) || (x==7 && i==14) || (x==9 && i==1) || (x==9 && i==13) 
						 || (x==13 && i==5) || (x==13 && i==9) || (x==14 && i==7) ){
							 countTW++;
							 System.out.println("COUNT TW" + countTW);
						 }
			 }
		 }
		 if(countDW>0){
			 for(int z=0; z<countDW; z++){
				 possibleTotal=possibleTotal*2;
				 System.out.println("possibleTotal" + possibleTotal);
			 }
		 }
		 
		 if(countTW>0){
			 for(int z=0; z<countTW; z++){
				 possibleTotal=possibleTotal*3;
				 System.out.println("possibleTotal" + possibleTotal);
			 }
		 }
		 
		 //controllo per assegnare eventualmente un nuovo hint
		 //(se punteggio per la parola corrente è almeno 20 punti si aggiunge un hint)
		 if(possibleTotal>=20)
			 addHint=true;
	}
	
	public void checkVertical(int x,int y){
		 //Controllo scorrendo la colonna
		 int countDW=0;
		 int countTW=0;
		 error=false;
		 textError="";		 
		 possibleWord="";
		 possibleTotal=0;
		 addHint=false;
		 
		 for(int i=tilesPlaced.get(0).getX1()-1; i<board.getROWS(); i++){
			 if(board.getBoard()[i][y].isOccupied()==false){
				 x=i-1;
				 break;
			 }	      				 
		 }	        
		 
		
		 newWord=new ArrayList<TileBoard>();
		 for(int i=x; i>=0; i--){
			 if((i==0)&&(board.getBoard()[i][y].isOccupied()==true)){
				 newWord.add(board.getBoard()[i][y]);
				 if( (i==0 && y==3) || (i==0 && y==11) || (i==2 && y==6) || (i==2 && y==8) || (i==3 && y==0) || (i==3 && y==14) || (i==6 && y==12) || (i==6 && y==2) || (i==8 && y==2)
						 || (i==8 && y==12) || (i==11 && y==0) || (i==11 && y==14) || (i==12 && y==6) || (i==12 && y==8) || (i==14 && y==3) || (i==14 && y==11)){
							 countDW++;
							 System.out.println("COUNTDW" + countDW);
						 }
					 
									
								 
						 if( (i==0 && y==7) || (i==1 && y==5) || (i==1 && y==9) || (i==5 && y==1) || (i==5 && y==13) || (i==7 && y==0) || (i==7 && y==14) || (i==9 && y==1) || (i==9 && y==13) 
						 || (i==13 && y==5) || (i==13 && y==9) || (i==14 && y==7) ){
							 countTW++;
							 System.out.println("COUNT TW" + countTW);
						 }
			 }
			 if((board.getBoard()[i][y].isOccupied()==false)||((i==0)&&(board.getBoard()[i][y].isOccupied()==true))){      					 
				 if(((newWord.size()==tilesPlaced.size())&&(firstTurn))||((newWord.size()>tilesPlaced.size())&&(!firstTurn))) {
					 for(int j=0; j<newWord.size(); j++){	        						
						 possibleWord=possibleWord+newWord.get(j).getLetter();
						 possibleTotal=possibleTotal+(newWord.get(j).getValue()*newWord.get(j).getMultiScore());
						 
									
					 } 
					 break;
				 }
				 
				 
				 
				 if(newWord.size()<tilesPlaced.size()){
					 error=true;
					 textError="error2";
					 break;
				 }	 
				 
				 if((newWord.size()==tilesPlaced.size())){
					 error=true;
					 textError="error5";
					 break;
				 }
			 }
			 else {
				newWord.add(board.getBoard()[i][y]); 
				if( (i==0 && y==3) || (i==0 && y==11) || (i==2 && y==6) || (i==2 && y==8) || (i==3 && y==0) || (i==3 && y==14) || (i==6 && y==12) || (i==6 && y==2) || (i==8 && y==2)
						 || (i==8 && y==12) || (i==11 && y==0) || (i==11 && y==14) || (i==12 && y==6) || (i==12 && y==8) || (i==14 && y==3) || (i==14 && y==11)){
							 countDW++;
							 System.out.println("COUNTDW" + countDW);
						 }
					 
									
								 
						 if( (i==0 && y==7) || (i==1 && y==5) || (i==1 && y==9) || (i==5 && y==1) || (i==5 && y==13) || (i==7 && y==0) || (i==7 && y==14) || (i==9 && y==1) || (i==9 && y==13) 
						 || (i==13 && y==5) || (i==13 && y==9) || (i==14 && y==7) ){
							 countTW++;
							 System.out.println("COUNT TW" + countTW);
						 }
			 }
		 }	
		 if(countDW>0){
			 for(int z=0; z<countDW; z++){
				 possibleTotal=possibleTotal*2;
				 System.out.println("possibleTotal" + possibleTotal);
			 }
		 }
		 
		 if(countTW>0){
			 for(int z=0; z<countTW; z++){
				 possibleTotal=possibleTotal*3;
				 System.out.println("possibleTotal" + possibleTotal);
			 }
		 }
		 
		//controllo per assegnare eventualmente un nuovo hint
		 //(se punteggio per la parola corrente è almeno 20 punti si aggiunge un hint)
		 if(possibleTotal>=20)
			 addHint=true;
	}
	
	
	public boolean checkWord (String word){
		String reverseWord="";
		boolean found=false;
		    for (int k = word.length() - 1; k >= 0; k--){
		    	reverseWord = reverseWord + word.charAt(k);
		    }      						 
		    dictionary=new File("it.txt");
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
	
	public void repaintPlayerPanel(){
		southCenter.removeAll();
		player.changeIcon();
   	 	for(int i=0; i<player.getLetters().size(); i++){
			 southCenter.add(player.getLetters().get(i) );
			 player.getLetters().get(i).addActionListener(new ButtonClickListener() );
			 player.getLetters().get(i).setActionCommand("tile");	
   	 	}
   	 	southWest.removeAll();
   	 	southWest.add(playerName);
   	 	southWest.add(playerNameValue);
   	 	southWest.add(playerScore);
   	 	playerScoreValue.setText("" + player.getScore());
   	 	southWest.add(playerScoreValue);
		southEast.removeAll();
		southEast.add(changeLetters);
		southPanel.repaint();
		southPanel.validate();
		
	}
	
	public void repaintCPUPanel(){	
		northPanel.removeAll();
		northWest.removeAll();
		northEast.removeAll();
		northCenter.removeAll();
		northWest.add(cpuName);
		northWest.add(cpuNameValue);
		northWest.add(cpuScore);
		cpuScoreValue.setText(""+cpu.getScore());
		cpuScoreValue.setForeground(orange);
		northWest.add(cpuScoreValue);
		for(int i=0; i<cpu.getLetters().size(); i++)
			northCenter.add(cpu.getLetters().get(i) );
		BufferedImage empty = null;
		try {
			empty = ImageIO.read(new File("img/buttons/empty.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JButton emptyBtn=new JButton(new ImageIcon(empty));
		emptyBtn.setBorder(BorderFactory.createEmptyBorder());
		emptyBtn.setContentAreaFilled(false);
		northEast.add(emptyBtn);
		northPanel.add(northWest);
		northPanel.add(northCenter);
		northPanel.add(northEast);
		northPanel.repaint();
		northPanel.validate();
		//setto il timer in modo che dopo 2 secondi il punteggio pc torni "normale"
		timerScore=new Timer();
		timerScore.schedule(new scoreTimerTask(), 2000);
	}

	public void repaintBoard(){
		 center.removeAll();
    	 for(int i=0; i<board.getROWS(); i++)
 			for(int j=0; j<board.getCOLUMNS(); j++){
 				center.add(board.getBoard()[i][j]);
 			}
    	center.repaint();
		center.validate();
	}
	
	public void repaintButtonsPanel(){
		ImageIcon img = new ImageIcon("img/buttons/box"+box.getNumLetters()+".png");
    	boxButton.setIcon(img);
    	ImageIcon img1 = new ImageIcon("img/buttons/hint" + countHint + ".png");
    	hint.setIcon(img1);
    	ImageIcon img2 = new ImageIcon("img/buttons/book.png");
    	vocabulary.setIcon(img2);
    	
		
	}
	
	public void repaintDictPanel(){
		letters.removeAll();
		for(int i=0; i<NUM_LETTERS; i++){
			String let;
			if(player.getLetters().get(i).isJolly())
				let = "jolly";
			else {
				let = "" + player.getLetters().get(i).getLetter();
				let.toLowerCase();
			}
			BufferedImage letterIcon = null;
			try {
				letterIcon = ImageIO.read(new File("img/tiles/tilesFlat/" + let + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JButton letterBtn=new JButton(new ImageIcon(letterIcon));
			letterBtn.setBorder(BorderFactory.createEmptyBorder());
			letterBtn.setContentAreaFilled(false);
			letters.add(letterBtn);
			letters.repaint();
			letters.validate();
		}
	}
	
	public void cancelAction(int i){
		//Riassegno la tile tilesPlaced.get(i) al player
		Tile support= new Tile(' ',0,"",false,"","","","");
		
		if(tilesPlaced.get(i).isJolly()){
			support.setLetter(' ');
			support.setValue(0);
			support.setPath("img/tiles/jolly.png");
			support.setJolly(true);
			support.setOriginalPath("img/tiles/tilesFlat/jolly.png");
			support.setPathColored("img/tiles/tilesFlat/colored/jolly.png");
			support.setBigPath("img/tiles/tilesFlat/big/jolly.png");
			support.setPlayerSelected("img/tiles/tilesFlat/playerHover/jolly.png");
		}
		else{
			support.setLetter(tilesPlaced.get(i).getLetter());
			support.setValue(tilesPlaced.get(i).getValue());
			support.setPath(tilesPlaced.get(i).getPath());
			support.setPathColored(tilesPlaced.get(i).getPathColored());
			support.setOriginalPath(tilesPlaced.get(i).getOriginalPath());
			support.setBigPath(tilesPlaced.get(i).getBigPath());
			support.setPlayerSelected(tilesPlaced.get(i).getPlayerSelected());
		}
		player.getLetters().add(support);
		changeLetters.setVisible(true);
		
		//Tolgo dalla board la tile tilesPlaced.get(i)
		int j=tilesPlaced.get(i).getX1()-1;
		int k=tilesPlaced.get(i).getY1()-1;
		board.removeTile(j, k);
		//Riabilito tutti i bottoni 
    	for(int r=0; r<board.getROWS(); r++)
				for(int c=0; c<board.getCOLUMNS();c++)
					board.getBoard()[r][c].setEnabled(true);
    	
    	repaintButtonsPanel();
	}
	
	public void removeSingleTile(int i){
		//Riassegno la tile tilesPlaced.get(i) al player
		Tile support= new Tile(' ',0,"",false,"","","","");
		if(tilesPlaced.get(i).isJolly()){
			support.setLetter(' ');
			support.setValue(0);
			support.setPath("img/tiles/jolly.png");
			support.setJolly(true);
			support.setOriginalPath("img/tiles/tilesFlat/jolly.png");
			support.setPathColored("img/tiles/tilesFlat/colored/jolly.png");
			support.setBigPath("img/tiles/tilesFlat/big/jolly.png");
			support.setPlayerSelected("img/tiles/tilesFlat/playerHover/jolly.png");
			
		}
		else{
			support.setLetter(tilesPlaced.get(i).getLetter());
			support.setValue(tilesPlaced.get(i).getValue());
			support.setPath(tilesPlaced.get(i).getPath());
			support.setPathColored(tilesPlaced.get(i).getPathColored());
			support.setOriginalPath(tilesPlaced.get(i).getOriginalPath());
			support.setBigPath(tilesPlaced.get(i).getBigPath());
			support.setPlayerSelected(tilesPlaced.get(i).getPlayerSelected());
		}
		player.getLetters().add(support);
		southEast.removeAll();
		southEast.add(cancel);
		southEast.add(confirm);
		repaintPlayerPanel();
		
		//Tolgo dalla board la tile tilesPlaced.get(i)
		int j=tilesPlaced.get(i).getX1()-1;
		int k=tilesPlaced.get(i).getY1()-1;
		board.removeTile(j, k);
		repaintButtonsPanel();
		
	}
	
	
	public void computerRound(Board board){
		PlaceWord placedWord = null;
		try {
			placedWord=cpu.enemyTurn(box, board, 0, scrabble, scrabbleM);
				
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(placedWord);
		 	
     	//Ridisegno la board
		repaintBoard();
		
		//Assegno nuove lettere al computer
			//Se il primo turno è del pc	 
		if(board.occupiedPositions()==placedWord.getWord().length()){
			for(int i=0; i<placedWord.getWord().length(); i++)
					cpu.setLetter(box.randomExtraction());
		}
		else {  
			 for(int i=0; i<placedWord.getWord().length()-1; i++){
				 if(box.getNumLetters()==0){
					 endGame();
					 break;
				 }
				 cpu.setLetter(box.randomExtraction());
			 	}
		}
		//cpu.setScore(placedWord.getMultiScore()+cpu.getScore());
		
		for(int i=0; i<cpu.getLetters().size(); i++)
			System.out.println(cpu.getLetters().get(i).getLetter());

		//Ridisegno Pannello Computer
		repaintCPUPanel();
		//setto il timer in modo che dopo 2 secondi le lettere del pc tornino "normali"
		timer=new Timer();
		timer.schedule(new lettersTimerTask(), 2000);
		//Ridisegno la board
		repaintBoard();
		repaintButtonsPanel();
		repaint();
		validate();
		 
	}
	
	 private class ButtonClickListener implements ActionListener {
		 public void actionPerformed(ActionEvent e) {
	         String command = e.getActionCommand(); 
	         Tile tileClicked=new Tile(' ',0,"",false,"","","","");
	         TileBoard tileBoardClicked=new TileBoard(' ',0,"","",0,0,0,"","","");
	         
	         
	         //Azione associata al bottone Cambia lettere
	         if( command.equals( "Cambia lettere" ))  {
	        	 carica.setEnabled(false);
	        	 salva.setEnabled(false);
	        	 hint.setEnabled(false);
	        	 vocabulary.setEnabled(false);
	        	 boxButton.setEnabled(false);
	        	 newGame.setEnabled(false);
	        	 statistics.setEnabled(false);
	        	 guide.setEnabled(false);
	        	 
	        	//Riassegno a eventuali lettere selezionate in precedenza, l'immagine originale
	        	 for(int i=0; i<player.getLetters().size();i++){
	        		 player.getLetters().get(i).setPath(player.getLetters().get(i).getOriginalPath());
	        	 }
	        	 changePanel.setVisible(true);
	        	 JPanel changeCenter=new JPanel();
	        	 final String advice="<html><body>" +
	        	            "<img src=file:" +
	        	            "img/tiles/tilesFlat/warnings/turno.png" +
	        	            " width=200 height=100> " ;
	      
	        	 southCenter.setVisible(false);
	        	 southEast.setVisible(false);
	        	 southPanel.add(changePanel);
	        	 changePanel.setOpaque(false);
	        	 final JLabel stringa= new JLabel("Seleziona lettere da sostituire: ");
	        	 stringa.setFont(museo);
	        	 stringa.setForeground(green);
	        	 changePanel.add(stringa, BorderLayout.NORTH);
	        	 option=new ArrayList<JCheckBox>();
	        	
	        	 for(int i = 0; i < player.getLetters().size(); i++) {
	                 option.add(new JCheckBox());
	                 //Cambia grafica ai checkbox
	                 option.get(i).setIcon(new ImageIcon("img/checkboxEmpty.png"));
	                 option.get(i).setSelectedIcon(new ImageIcon("img/checkboxSelected.png"));
	                 option.get(i).setPressedIcon(new ImageIcon("img/checkboxSelected.png"));
	                 option.get(i).setRolloverIcon(new ImageIcon("img/checkboxHover.png"));
	                 option.get(i).setRolloverSelectedIcon(new ImageIcon("img/checkboxSelected.png"));
	                 
	                 String label = "<html><table cellpadding=0><tr><td><img src=file:"
	        		        + player.getLetters().get(i).getBigPath() + "/></td><td width=><td>"
	        		        + option.get(i).getText() + "</td></tr></table></html>";  
	                 option.get(i).setText(label); 
	                 option.get(i).setIconTextGap(2);
	                 option.get(i).setOpaque(false);
	                 option.get(i).setVerticalTextPosition(SwingConstants.TOP);
	                 option.get(i).setHorizontalTextPosition(SwingConstants.CENTER);
	                 changeCenter.add(option.get(i));
	                 
	             }
	        	 changePanel.add(changeCenter, BorderLayout.CENTER);
	        	 JPanel changeEast=new JPanel(new GridLayout(2,1));
	        	 changeCenter.setOpaque(false);
	        	 changeEast.setOpaque(false);
	        	 
	        	 //BOTTONE SOSTITUISCI
	        	 BufferedImage buttonIconConfirm = null;
	     			try {
	     			buttonIconConfirm = ImageIO.read(new File("img/buttons/sostituisci.png"));
	     			} catch (IOException e1) {
	     				// TODO Auto-generated catch block
	     				e1.printStackTrace();
	     			}
	        	 final JButton confirmChange = new JButton(new ImageIcon(buttonIconConfirm));
	        	 
	     		 confirmChange.setBorder(BorderFactory.createEmptyBorder());
	     		 confirmChange.setContentAreaFilled(false);
	        	 confirmChange.setActionCommand("confirmChange");
	        	 confirmChange.setToolTipText(advice);
	        	 //Setta le caratteristiche della tooltip
	        	 ToolTipManager.sharedInstance().setInitialDelay(0);
	        	 UIManager.put("ToolTip.background", new ColorUIResource(255, 255, 255)); 
	        	 Border border = BorderFactory.createLineBorder(new Color(255,255,255));    
	        	 UIManager.put("ToolTip.border", border);
	        	 ToolTipManager.sharedInstance().setDismissDelay(5000);   
	        	 
	             confirmChange.addActionListener(new ButtonClickListener());
	             confirmChange.addMouseListener(new MouseAdapter()
	     		{
	     	            public void mouseEntered(MouseEvent evt){
	     	            	ImageIcon img = new ImageIcon("img/buttons/sostituisciHover.png");
	     	            	confirmChange.setIcon(img);
	     	            	
	     	            }
	     	            public void mouseExited(MouseEvent evt) {
	     	            	ImageIcon img = new ImageIcon("img/buttons/sostituisci.png");
	     	            	confirmChange.setIcon(img);
	     	            	
	     	            }
	     	            public void mousePressed(MouseEvent evt){
	     	            	ImageIcon img = new ImageIcon("img/buttons/sostituisciHover.png");
	     	            	confirmChange.setIcon(img);
	     	            }
	     	            public void mouseReleased(MouseEvent evt){
	     	            	ImageIcon img = new ImageIcon("img/buttons/sostituisci.png");
	     	            	confirmChange.setIcon(img);
	     	            } 
	     	        });
	             changeEast.add(confirmChange);
	             
	             //BOTTONE ANNULLA
	             BufferedImage buttonIconCancel2 = null;
	     		 try {
	     		 buttonIconCancel2 = ImageIO.read(new File("img/buttons/annulla.png"));
	     		 } catch (IOException e1) {
	     			// TODO Auto-generated catch block
	     			e1.printStackTrace();
	     		}
	        	 final JButton cancelChange = new JButton(new ImageIcon(buttonIconCancel2));
	        	 cancelChange.setBorder(BorderFactory.createEmptyBorder());
	     		 cancelChange.setContentAreaFilled(false);
	        	 cancelChange.setActionCommand("cancelChange");
	             cancelChange.addActionListener(new ButtonClickListener());
	             cancelChange.addMouseListener(new MouseAdapter()
		     		{
		     	            public void mouseEntered(MouseEvent evt){
		     	            	ImageIcon img = new ImageIcon("img/buttons/annullaHover.png");
		     	            	cancelChange.setIcon(img);
		     	            }
		     	            public void mouseExited(MouseEvent evt) {
		     	            	ImageIcon img = new ImageIcon("img/buttons/annulla.png");
		     	            	cancelChange.setIcon(img);
		     	            }
		     	            public void mousePressed(MouseEvent evt){
		     	            	ImageIcon img = new ImageIcon("img/buttons/annullaHover.png");
		     	            	cancelChange.setIcon(img);
		     	            }
		     	            public void mouseReleased(MouseEvent evt){
		     	            	ImageIcon img = new ImageIcon("img/buttons/annulla.png");
		     	            	cancelChange.setIcon(img);
		     	            } 
		     	        });
	        	 changeEast.add(cancelChange);
	        	 
	             changePanel.add(changeEast, BorderLayout.EAST);
	        	 southPanel.repaint();
    			 southPanel.validate();
	         }
	         
	         //Azione associata al bottone Sostituisci nel pannello Cambia Lettere
	         if(command.equals("confirmChange")){
	        	 int matchPlay =0;
	        	 int matchWin=0;
	        	 int maxScore=0;
	        	 int winPlay=0;
	        	 float perc=0;
	        	 String line="";
	        	 carica.setEnabled(true);
	        	 salva.setEnabled(true);
	        	 if(countHint>0)
	        		 hint.setEnabled(true);
	        	 vocabulary.setEnabled(true);
	        	 boxButton.setEnabled(true);
	        	 newGame.setEnabled(true);
	        	 statistics.setEnabled(true);
	        	 guide.setEnabled(true);
	        	 countTurn++;
	        	 //PLAYER PASSA TURNO TRE VOLTE-->PARTITA VINTA DA CPU
	        	 if(countTurn==3){
	        		 JPanel messagePane = new JPanel(new BorderLayout());
	        		 JPanel results=new JPanel(new GridLayout(2,2,20,0));
	        		 results.add(cpuNameValue);
	        		 results.add(cpuScoreValue);
	        		 results.add(playerNameValue);
	        		 results.add(playerScoreValue);
	        		 messagePane.add(lostBtn, BorderLayout.NORTH);
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
	        		 
	        		 
	        		 //Aggiorna le statistiche
	        		 BufferedReader read;
					 try {
						read = new BufferedReader(new FileReader("stats.txt"));
						line=read.readLine();
		        		matchPlay=Integer.parseInt(line);
		        		matchPlay++;
		        		line=read.readLine();
		        		matchWin=Integer.parseInt(line);
		        		line=read.readLine();
		        		line=read.readLine();
		        		maxScore=Integer.parseInt(line);
		        		read.close();
					 } catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
					 } catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
					 }   				
	        		
					 FileOutputStream file;
					 try {
						file = new FileOutputStream("stats.txt");
						PrintStream output= new PrintStream(file);
						output.println(matchPlay);
						output.println(matchWin);
					    perc=((float)matchWin/(float)matchPlay)*100;	
					    winPlay=(int)perc;
					    output.println(winPlay);
						output.println(maxScore);
						output.close();
					 } catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					 }
	        		 
	        		 
	        		 carica.setEnabled(true);
		        	 salva.setEnabled(true);
		        	 if(countHint>0)
		        		 hint.setEnabled(true);
		        	 vocabulary.setEnabled(true);
		        	 boxButton.setEnabled(true);
		        	 newGame.setEnabled(true);
		        	 statistics.setEnabled(true);
		        	 guide.setEnabled(true);
		        	 changePanel.setVisible(false);
		        	//Riassegno lettere player dimensione big
		        	 for(int i=0; i<player.getLetters().size();i++){
		        		 player.getLetters().get(i).setPath(player.getLetters().get(i).getBigPath());
		        	 }
		        	 changePanel.removeAll();
		        	  southCenter.setVisible(true);
		        	 southEast.removeAll();
		        	 southEast.add(changeLetters);
		        	 southEast.setVisible(true);
	        		 newGame();
		       		 whoStarts();
	        	 }
	        	 else { 
	        	 for(int i=0; i<player.getLetters().size();i++){
	        		 player.getLetters().get(i).setSelected(false);
	        	 }
	        	 player.changeIcon();
	        	//Riassegno a eventuali lettere selezionate in precedenza, l'immagine originale
	        	 for(int i=0; i<player.getLetters().size();i++){
	        		 player.getLetters().get(i).setPath(player.getLetters().get(i).getOriginalPath());
	        	 }
	        	 for(int i = 0; i < option.size(); i++)
	                 if((option.get(i)).isSelected()){
	                	player.getLetters().get(i).setSelected(true);
	                 }
	        	 
	        	 player.setLetters(box.changeLetters(player.getLetters()));
	        	 for(int i=0; i<player.getLetters().size(); i++){
	        		 player.getLetters().get(i).addActionListener(new ButtonClickListener() );
	     			 player.getLetters().get(i).setActionCommand("tile");
	        		 
	        	 } 
	        	 changePanel.setVisible(false);
	        	 changePanel.removeAll();
	        	 southCenter.removeAll();
	        	 southCenter.setVisible(true);
	        	 southEast.setVisible(true);
	        	 player.changeIcon();
	        	 for(int i=0; i<player.getLetters().size(); i++){
	     			southCenter.add(player.getLetters().get(i) );
	        	 }
	        	 
	        	 southCenter.repaint();
    			 southCenter.validate();
    			 
    			 //Cede turno al computer
    			 computerRound(board);
	        	 } 
	         }
	         
	         //Azione associata al bottone Annulla nel pannello Cambia Lettere
	         if(command.equals("cancelChange")){
	        	 carica.setEnabled(true);
	        	 salva.setEnabled(true);
	        	 if(countHint>0)
	        		 hint.setEnabled(true);
	        	 vocabulary.setEnabled(true);
	        	 boxButton.setEnabled(true);
	        	 newGame.setEnabled(true);
	        	 statistics.setEnabled(true);
	        	 guide.setEnabled(true);
	        	 changePanel.setVisible(false);
	        	//Riassegno lettere player dimensione big
	        	 for(int i=0; i<player.getLetters().size();i++){
	        		 player.getLetters().get(i).setPath(player.getLetters().get(i).getBigPath());
	        	 }
	        	 changePanel.removeAll();
	        	  southCenter.setVisible(true);
	        	 southEast.removeAll();
	        	 southEast.add(changeLetters);
	        	 southEast.setVisible(true);
	        	 
	         }
	         
	         //Azione associata al bottone Conferma per confermare le mosse player
	         if(command.equals("conferma")){	        	      	
	        	 firstTurn=false;
	        	 boolean correct=false;	
	        	 boolean oneLetterError=false;
	        	 addHint=false;
	        	
	        	 changeLetters.setVisible(true);
	        	 confirm.setVisible(false);
	        	 //Riabilito tutti i bottoni 
	        	 for(int i=0; i<board.getROWS(); i++){
        			 for(int j=0; j<board.getCOLUMNS(); j++){
        				 board.getBoard()[i][j].setEnabled(true);
        			 }
        		 }
	        	 
	        	 //Controllo jolly
	        	 Object[] possibilities={" ","A","B","C","D","E","F","G","H","I","L","M","N","O","P","Q","R","S","T","U","V","Z"};
	        	 final JComboBox<Object> combo = new JComboBox<Object>(possibilities);
	        	 combo.setFont(proximaNova);
	        	 combo.setForeground(darkBlue);
	        	 combo.setBackground(lightBlue);
	        	 String[] options = { "OK" };
	        	 String title="Attenzione";
	        	 JPanel message=new JPanel(new BorderLayout());
	        	 message.add(jollyBtn, BorderLayout.NORTH);
	        	 message.add(combo,BorderLayout.CENTER);
	        	 for(int i=0; i<tilesPlaced.size();i++)
	        		 if(tilesPlaced.get(i).isJolly()){
	        			 LookAndFeel previousLF = UIManager.getLookAndFeel();
	        		   		try {
	        					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
	        						| UnsupportedLookAndFeelException e1) {
	        					// TODO Auto-generated catch block
	        					e1.printStackTrace();
	        				}
	        		   		JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,options, options[0]);
	        		   		try {
	        					UIManager.setLookAndFeel(previousLF);
	        				} catch (UnsupportedLookAndFeelException e1) {
	        					// TODO Auto-generated catch block
	        					e1.printStackTrace();
	        				} 
	        			  
	        		      String letter = (String) combo.getSelectedItem();
	        		      if (letter != null) {
	        		         System.out.println("Lettera selezionata "+letter);
	        		         File letters=new File("initializingLetters.txt");
	        				    try {
	        				    	LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(letters)));
	        				    	String lineRead = null;									 
	        				    	while((lineRead = reader.readLine()) != null) {
	        				    		if((lineRead.compareToIgnoreCase(letter)==0)){
	        				    			//Lettera trovata
	        				    			tilesPlaced.get(i).setLetter(letter.charAt(0));
	        				    			tilesPlaced.get(i).setValue(Integer.parseInt(reader.readLine()));
	        				    			tilesPlaced.get(i).setPath(reader.readLine());
	        				    		
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
	        	
	        	 //controllo primo turno
	        	 if(board.occupiedPositions()==tilesPlaced.size()){
	        		 firstTurn=true;	        		 
	        		 if(tilesPlaced.size()>1){
	        			 for(int i=0; i<tilesPlaced.size(); i++)
	        				 if((tilesPlaced.get(i).getX1()==tilesPlaced.get(i).getY1())&&(tilesPlaced.get(i).getX1()==board.getCOLUMNS()/2+1))
	        					 //nel primo turno una lettera è sulla stella
	        					 correct=true;
	        		 }
	        		 else {	        			
	        		    //errore: ha inserito solo una lettera nel primo turno
	        			 oneLetterError=true;
	        			 JOptionPane.showMessageDialog(scrabble, error1Btn, "Attenzione", JOptionPane.PLAIN_MESSAGE);
	        			 cancelAction(0);
	     		    	 repaintPlayerPanel(); 
	     		    	 tilesPlaced.clear();
	        		 }
	        	 }
	        	 
	        	 //non entro nell'algoritmo di estrazione e ricerca parola se è il primo turno e non c'è una lettera
	        	 //sulla stella 
	        	 if((correct)||(!firstTurn)){
	        		 if(tilesPlaced.size()==1){
	        			 if((!board.getBoard()[tilesPlaced.get(0).getX1()-1][tilesPlaced.get(0).getY1()-2].isOccupied())&&(!board.getBoard()[tilesPlaced.get(0).getX1()-1][tilesPlaced.get(0).getY1()].isOccupied())){
	        				 //singola lettera con posizione destra/sinistra libera
	        				 int x=board.getROWS()-1;
	        				 int y=tilesPlaced.get(0).getY1()-1;
	        				 checkVertical(x,y);
	        				 System.out.println(possibleWord.length());
	        				 if(possibleWord.length()==0){
	        					 //anche in alto/basso caselle libere: errore
	        					 textError="Lettera inserita in posizione non corretta";
	    	        			 JOptionPane.showMessageDialog(scrabble, error2Btn, "Attenzione", JOptionPane.PLAIN_MESSAGE);
	    	        			 cancelAction(0);
	    	     		    	 repaintPlayerPanel(); 
	        				 }
	        				 else{
	        					 //alto/basso sono occupate: controllo parola verticale
	        					 boolean found=checkWord(possibleWord);
	        					 if(found){
	        						 countTurn=0;
	        						 //setto occupate le celle della board
	        						 board.getCell(tilesPlaced.get(0).getX1()-1, tilesPlaced.get(0).getY1()-1).setOccupied(true);
	        						 //Se il punteggio è maggiore di 20 aggiungo un hint
	        						 if(addHint && countHint<5)
	        							 countHint++;
	        						 if(countHint>0)
	        							 hint.setEnabled(true);
	        						 else
	        							 hint.setEnabled(false);
	        			
	        						 //Aggiorno i campi per il player
		        					 player.setScore(player.getScore()+possibleTotal);
		        					 //Estrazione nuove lettere player
		        					 if(box.getNumLetters()==0)
		        						 endGame();
		        					 player.setLetter(box.randomExtraction());
		        					 repaintPlayerPanel();
		        					 repaintButtonsPanel();
		        					 //setto il timer in modo che dopo 2 secondi il punteggio player torni "normale"
		        		     		 playerScoreValue.setForeground(orange);
		        		       		 timerScore=new Timer();
		        		      		 timerScore.schedule(new scoreTimerTask(), 1000);
		        					 
		        					//Cede turno al computer
		        					 timerCPU=new Timer();
		        					 timerCPU.schedule(new CPUroundTask(), 1000);
		        		    		 
	        					 }
	        					 else{
	        						 //Parola non trovata nel file
	        						 textError="Parola non conosciuta solo verticale";
		        					 JOptionPane.showMessageDialog(scrabble, error4Btn, "Attenzione", JOptionPane.PLAIN_MESSAGE);				
		        					 cancelAction(0);
		        					 repaintPlayerPanel();
	        					 }
	        				 }
	        			 }
	        			 else{
	        				 if((!board.getCell(tilesPlaced.get(0).getX1()-2,tilesPlaced.get(0).getY1()-1).isOccupied())&&(!board.getCell(tilesPlaced.get(0).getX1(),tilesPlaced.get(0).getY1()-1).isOccupied())){
	        					 //singola lettera con alto/basso libero
	        					 int x=tilesPlaced.get(0).getX1()-1;
	        		  			 int y=board.getCOLUMNS()-1;
		        				 checkHorizontal(x,y);
		        				 
		        				 if(possibleWord.length()==0){
		        					 //anche sinistra/destra è libero: errore
		        					 textError="Lettera inserita in posizione non corretta";
		    	        			 JOptionPane.showMessageDialog(scrabble, error2Btn, "Attenzione", JOptionPane.PLAIN_MESSAGE);
		    	        			 cancelAction(0);
		    	     		    	 repaintPlayerPanel(); 
		        				 }
		        				 else{
		        					 //destra/sinistra sono occupate: controllo orizzontale
		        					 boolean found=checkWord(possibleWord);
		        					 if(found){
		        						 countTurn=0;
		        						 board.getCell(tilesPlaced.get(0).getX1()-1, tilesPlaced.get(0).getY1()-1).setOccupied(true);
		        						//Se il punteggio è maggiore di 20 aggiungo un hint
		        						 if(addHint && countHint<5)
		        							 countHint++;
		        						 if(countHint>0)
		        							 hint.setEnabled(true);
		        						 else
		        							 hint.setEnabled(false);
		        						 System.out.println("countHint: "+countHint);
		        						 //Aggiorno campi player
			        					 player.setScore(player.getScore()+possibleTotal);
			        					 //Estrazione nuove lettere player
			        					 if(box.getNumLetters()==0)
			        						 endGame();
			        					 player.setLetter(box.randomExtraction());
			        					 repaintPlayerPanel();
			        					 repaintButtonsPanel();
			        					//setto il timer in modo che dopo 2 secondi il punteggio player torni "normale"
			        		     		 playerScoreValue.setForeground(orange);
			        		     		 timerScore=new Timer();
			        		     		 timerScore.schedule(new scoreTimerTask(), 1000);
			        					 //Cede turno al computer
			        					 timerCPU=new Timer();
			        					 timerCPU.schedule(new CPUroundTask(), 1000);
		        					 }
		        					 else {
		        						 //Parola non trovata nel file
		        						 textError="Parola non conosciuta orizzontale";
			        					 JOptionPane.showMessageDialog(scrabble, error4Btn, "Attenzione", JOptionPane.PLAIN_MESSAGE);				
			        					 cancelAction(0);
			        					 repaintPlayerPanel(); 
		        					 }
		        				 }
		        			 } 
	        				 else {
	        					 int partialSum=0;
	        					 int x=tilesPlaced.get(0).getX1()-1;
	        		  			 int y=board.getCOLUMNS()-1;
	        					 checkHorizontal(x,y);
	        					 partialSum+=possibleTotal;
	        					 boolean found=checkWord(possibleWord);
		        				 if(found){
		        					 //orizzontale esiste: controllo verticale
		        					 x=board.getROWS()-1;
			        				 y=tilesPlaced.get(0).getY1()-1;
			        				 checkVertical(x,y);
			        				 partialSum+=possibleTotal;
			        				 found=checkWord(possibleWord);
			        				 if(found){
			        					 countTurn=0;
			        					 //anche verticale esiste: aggiorno player tavola e punteggio
			        					 board.getCell(tilesPlaced.get(0).getX1()-1, tilesPlaced.get(0).getY1()-1).setOccupied(true);
			        					//Se il punteggio è maggiore di 20 aggiungo un hint
		        						 if(addHint && countHint<5)
		        							 countHint++;
		        						 if(countHint>0)
		        							 hint.setEnabled(true);
		        						 else
		        							 hint.setEnabled(false);
		        						 System.out.println("countHint: "+countHint);
			        					 player.setScore(player.getScore()+partialSum);
			        					 //Estrazione nuove lettere player
			        					 if(box.getNumLetters()==0)
			        						 endGame();
			        					 player.setLetter(box.randomExtraction());
			        					 repaintPlayerPanel();
			        					 repaintButtonsPanel();
			        					 //setto il timer in modo che dopo 2 secondi il punteggio player torni "normale"
			        		     		 playerScoreValue.setForeground(orange);
			        		     		 timerScore=new Timer();
			        		     		 timerScore.schedule(new scoreTimerTask(), 1000);
			        					 //Cede turno al computer
			        					 timerCPU=new Timer();
			        					 timerCPU.schedule(new CPUroundTask(), 1000);
			        				 }
			        				 else {
			        					 //verticale non esiste: errore
			        					 textError="Parola non conosciuta";
			        					 JOptionPane.showMessageDialog(scrabble, error4Btn, "Attenzione", JOptionPane.PLAIN_MESSAGE);				
			        					 cancelAction(0);
			        					 repaintPlayerPanel(); 
			        				 }
		        				 }
		        				 else{
		        					 //orizzontale non esiste: errore
		        					 textError="Parola non conosciuta";
		        					 JOptionPane.showMessageDialog(scrabble, error4Btn, "Attenzione", JOptionPane.PLAIN_MESSAGE);				
		        					 cancelAction(0);
		        					 repaintPlayerPanel(); 
		        				 }
	        					
	        					 
	        				 }
	        			 }
	        			 newWord.clear();
	        			 tilesPlaced.clear();	
	        		 } 
	        		 else{	      	 
	        			 if(tilesPlaced.get(0).getX1()==tilesPlaced.get(1).getX1()){
	        		  			 int x=tilesPlaced.get(0).getX1()-1;
	        		  			 int y=board.getCOLUMNS()-1;
	        					 checkHorizontal(x,y);    			  						 	 			 		        	
	        			 }
	        			 else{
	        				 //Controllo sulla colonna
	        				 int x=board.getROWS()-1;
	        				 int y=tilesPlaced.get(0).getY1()-1;
	        				 checkVertical(x,y);	     	       			        	  
	        			 }
	        			 if(error){
	        				 if(textError.equals("error2"))
	        					 JOptionPane.showMessageDialog(scrabble, error2Btn, "Attenzione", JOptionPane.PLAIN_MESSAGE);
	        				 if(textError.equals("error5"))
	        					 JOptionPane.showMessageDialog(scrabble, error5Btn, "Attenzione", JOptionPane.PLAIN_MESSAGE);
	        				 for(int i=0; i<tilesPlaced.size();i++)
	        					 cancelAction(i);
	        				 repaintPlayerPanel();
	        			 }	
	        			 else {
	        				 boolean found=checkWord(possibleWord);
	        				 if(found){
	        					 countTurn=0;
	        					 //Confermo le lettere nel tilesPlaced
	        					 for(int i=0;i<tilesPlaced.size();i++)
	        						 board.getCell(tilesPlaced.get(i).getX1()-1, tilesPlaced.get(i).getY1()-1).setOccupied(true);
	        					//Se il punteggio è maggiore di 20 aggiungo un hint
        						 if(addHint && countHint<5)
        							 countHint++;
        						 if(countHint>0)
        							 hint.setEnabled(true);
        						 else
        							 hint.setEnabled(false);
	        					 player.setScore(player.getScore()+possibleTotal);
	        					 //Estrazione nuove lettere player
	        					 for(int i=0;i<tilesPlaced.size();i++){
	        						 if(box.getNumLetters()==0){
		        						 endGame();
	        						 	 break;
	        						 }
	        						 player.setLetter(box.randomExtraction());
	        					 }
	        					 repaintPlayerPanel();
	        					 repaintButtonsPanel();
	        					 //setto il timer in modo che dopo 2 secondi il punteggio player torni "normale"
	        		     		 playerScoreValue.setForeground(orange);
	        		     		 timerScore=new Timer();
	        		     		 timerScore.schedule(new scoreTimerTask(), 1000);
	        					 //Cede turno al computer
	        					 timerCPU=new Timer();
	        					 timerCPU.schedule(new CPUroundTask(), 1000);
	        				 }
	        				 else {
	        					 textError="Parola non conosciuta";
	        					 JOptionPane.showMessageDialog(scrabble, error4Btn, "Attenzione", JOptionPane.PLAIN_MESSAGE);
	        		    	
	        					 for(int i=0; i<tilesPlaced.size();i++)
	        						 cancelAction(i);
	        					 repaintPlayerPanel();
	        				 }      			
	        			 }
	        			 newWord.clear();
	        			 tilesPlaced.clear();	
	        			 
	        		 }
	           }	
	           else {
	        	  if(!oneLetterError){ 
	        	   //errore: siamo al primo turno e non c'è una lettera sulla stella 
	        	   textError="Ci deve essere una lettera sulla stella";	        	   
      			   JOptionPane.showMessageDialog(scrabble, error3Btn, "Attenzione", JOptionPane.PLAIN_MESSAGE);
      			   for(int i=0; i<tilesPlaced.size();i++)
 		    		  cancelAction(i);
 		    	   repaintPlayerPanel();
 		    	   tilesPlaced.clear();
 		    	   
	        	  } 
	           }
	        	 
	        	 carica.setEnabled(true);
	        	 salva.setEnabled(true); 
	        	 if(countHint>0)
	        		 hint.setEnabled(true);
	        	 vocabulary.setEnabled(true);
	        	 boxButton.setEnabled(true);
	        	 repaintButtonsPanel();
	         }
	         
	         //Azione associata al click di una tile player
	         if(command.equals("tile")){
	        	 for(int i=0; i<player.getLetters().size();i++){
	        		 player.getLetters().get(i).setSelected(false);
	        	 }
	        	 //Riassegno a eventuali lettere selezionate in precedenza, l'immagine originale
	        	 for(int i=0; i<player.getLetters().size();i++){
	        		 player.getLetters().get(i).setPath(player.getLetters().get(i).getOriginalPath());
	        	 }
	        	 
	        	//Riassegno lettere player dimensione big
	        	 for(int i=0; i<player.getLetters().size();i++){
	        		 player.getLetters().get(i).setPath(player.getLetters().get(i).getBigPath());
	        	 }
	        	 
	        	 cancel.setVisible(true);
	        	 southEast.removeAll();
	        	 southEast.add(cancel);
	        	 southEast.add(confirm);
	        	 
	        	
	        	 tileClicked=(Tile)e.getSource();
	        	// tileClicked.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
	        	 for(int i=0; i<player.getLetters().size(); i++){
	        		 if(tileClicked==player.getLetters().get(i) ){
	        			 player.getLetters().get(i).setSelected(true);	
	        			 player.getLetters().get(i).setPath(player.getLetters().get(i).getPlayerSelected());
	        			 //player.getLetters().get(i).setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
	        		 }
	        	 }	        	        	 
	         }
	         
	     
	         
	         //Azione associata al click su una tile della board
	         if(command.equals("tileBoard")){
	        	
	        	 tileBoardClicked=(TileBoard)e.getSource();
	        	 boolean verify=false;
	        	 for(int i=0; i<player.getLetters().size(); i++){
	        		 if((player.getLetters().get(i).isSelected()) && (!tileBoardClicked.isOccupied())){
	        			 player.getLetters().get(i).setPath(player.getLetters().get(i).getOriginalPath());
	        			 tileBoardClicked.setLetter(player.getLetters().get(i).getLetter());
	        			 tileBoardClicked.setValue(player.getLetters().get(i).getValue());
	        			 tileBoardClicked.setPath(player.getLetters().get(i).getPath());
	        			 tileBoardClicked.setOriginalPath(player.getLetters().get(i).getOriginalPath());
	        			 tileBoardClicked.setPathColored(player.getLetters().get(i).getPathColored());
	        			 tileBoardClicked.setBigPath(player.getLetters().get(i).getBigPath());
	        			 tileBoardClicked.setPlayerSelected(player.getLetters().get(i).getPlayerSelected());
	        			 tileBoardClicked.setOccupied(true);
	        			 tileBoardClicked.setJolly(player.getLetters().get(i).isJolly());
	        			 southCenter.remove(player.getLetters().get(i));
	        			 player.getLetters().remove(i);
	        			 tilesPlaced.add(tileBoardClicked);
	        			 southCenter.repaint();
	        			 southCenter.validate(); 
	        			 verify=true;
	        			 confirm.setVisible(true);
	        			 salva.setEnabled(false);
	        			 carica.setEnabled(false);
	        			 hint.setEnabled(false);
	    	        	 vocabulary.setEnabled(true);
	    	        	 boxButton.setEnabled(true);
	    	        	 repaintButtonsPanel();
	        			 //changeLetters.setVisible(false);
	        			 
	        		 }
	        		 
	        	 }
	        	 
	        	 //Disabilito i bottoni che non sono sulla riga o sulla colonna della prima lettera inserita
	        	 if(verify==true){ 
	        		 for(int i=0; i<board.getROWS(); i++)
	        			 for(int j=0; j<board.getCOLUMNS(); j++)
	        				 if((board.getBoard()[i][j].getX1()!=tileBoardClicked.getX1()) && (board.getBoard()[i][j].getY1()!=tileBoardClicked.getY1()))
	        					board.getBoard()[i][j].setEnabled(false);
 
	        	 } 
	        	 
	        	 //CASO IN CUI PLAYER NON HA SELEZIONATO ALCUNA LETTERA MA HA CLICCATO SU TILEBOARD OCCUPATA
        		 //--->SE LETTERA NELLA BOARD è STATA MESSA DA LUI NEL TURNO CORRENTE LA RIMUOVE
        		 //ALTRIMENTI NULLA
	        	 if((!verify) && (tileBoardClicked.isOccupied())&&(tileBoardClicked.isBlocked()==false)){
	        		 for(int i=0; i<tilesPlaced.size();i++){
        				 //Se la tile è stata messa dal player nel turno corrente
        				if(tilesPlaced.get(i)==tileBoardClicked) {
        					removeSingleTile(i);
        					repaintPlayerPanel();
        		        	//repaintBoard();
        	        		tilesPlaced.remove(i); //!!!!
        	        		
        	        		
        	        		//Riabilito colonna o riga della lettera rimasta
        	        		if(player.getLetters().size()==6){
        	        			for(int r=0; r<board.getROWS(); r++){
        	        				for(int c=0; c<board.getCOLUMNS();c++){
        	        					if(tilesPlaced.get(0).getX1()==r+1){
        	        						board.getBoard()[r][c].setEnabled(true);
        	        					}
        	        					if(tilesPlaced.get(0).getY1()==c+1){
        	        						board.getBoard()[r][c].setEnabled(true);
        	        					}
        	        				}
        	        			}
        	        		}
        	        		//Riabilito tutti i bottoni
        	        		if(player.getLetters().size()==7){
        	        			changeLetters.setVisible(true);
        	        			southEast.removeAll();
        	        			southEast.add(changeLetters);
        	        			for(int r=0; r<board.getROWS(); r++)
        	        				for(int c=0; c<board.getCOLUMNS();c++)
        	        					board.getBoard()[r][c].setEnabled(true);
        	        			 carica.setEnabled(true);
        	    	        	 salva.setEnabled(true);
        	    	        	 if(countHint>0)
           							 hint.setEnabled(true);
           						 else
           							 hint.setEnabled(false);
        	    	        	 repaintButtonsPanel();
        	        		}
        	        		else {
        	        			southEast.removeAll();
        	        			southEast.add(cancel);
        	        			southEast.add(confirm);
        	        			
       							 hint.setEnabled(false);
        	        		}
        				}
        					
        			 }
        		 }
	        	 
	        	 
	        	 //Se non passo in nessun ramo deseleziono la lettera
	        	 for(int i=0; i<player.getLetters().size();i++)
	        		 if(player.getLetters().get(i).isSelected())
	        			 player.getLetters().get(i).setSelected(false);  
	        	 
	         }
	         
	         //Azione associata al bottone annulla del player
	         if(command.equals("cancel")){
	        	//Riassegno a eventuali lettere selezionate in precedenza, l'immagine originale
	        	 for(int i=0; i<player.getLetters().size();i++){
	        		 player.getLetters().get(i).setPath(player.getLetters().get(i).getOriginalPath());
	        	 }
	        	 //confirm.setVisible(false);
	        	 southEast.removeAll();
	        	 southEast.add(changeLetters);
	        	 for(int i=0; i<tilesPlaced.size();i++)
	        		 cancelAction(i); 
	        	 repaintPlayerPanel();
	        	 repaintBoard();

	        	 //Svuoto tilesPlaced
	        	tilesPlaced.clear();
	        	//player ha di nuovo possibilità di cambiare lettere
	        	changeLetters.setEnabled(true);
	        	
	        	 carica.setEnabled(true);
	        	 salva.setEnabled(true);
	        	 vocabulary.setEnabled(true);
	        	 boxButton.setEnabled(true);
	        	 if(countHint>0)
					 hint.setEnabled(true);
				 else
					 hint.setEnabled(false);
	        	 repaintButtonsPanel();
	         }
	         
	         //Azione associata al bottone Salva partita
	         if(command.equals("salva")){
	        	 String nameSave = null;
	        	 //Caso in cui la partita non è mai stata salvata
	        	 if(saveStatus==false){
	        		saveStatus=true;
	        	 	//Creazione file di salvataggio
	        	 	GregorianCalendar gc= new GregorianCalendar();
	        	 	int month=gc.get(Calendar.MONTH);
	        	 	month++;
	        	 	nameSave="partite/";
	        	 	nameSave+=gc.get(Calendar.YEAR)+"_"+month+"_"+gc.get(Calendar.DAY_OF_MONTH)+"_"+gc.get(Calendar.HOUR_OF_DAY)+"_"+gc.get(Calendar.MINUTE)+".txt";
	        	 	nameFile=nameSave;
	        	 }
	        	 //caso in cui la stessa partita è già stata salvata
	        	 else {
	        		 nameSave=nameFile;
	        		 File file=new File(nameSave);
	        		 file.delete();
	        		 
	        		 //Cancello il file precedente e ne creo uno nuovo ( nel try ) con lo stesso nome (nameSave==nameFile)
	        	 }
	        	    try {
	        	    	FileOutputStream file=new FileOutputStream(nameSave);
	        	    	PrintStream output= new PrintStream(file);
	        	    	output.println(nameFile);
	        	    	output.println(saveStatus);
	        	    	output.println(countHint);
	        	    	output.println(cpu.getDifficulty());
	        	    	output.println(player.getScore());
	        	    	output.println(cpu.getScore());
	        	    	//Inserimento lettere player
	        	    	for(int i=0; i<7; i++){
	        	    		output.println(player.getLetters().get(i).getLetter());
	        	    		output.println(player.getLetters().get(i).getValue());
	        	    		output.println(player.getLetters().get(i).getPath());
	        	    		output.println(player.getLetters().get(i).getPathColored());
	        	    		output.println(player.getLetters().get(i).getOriginalPath());
	        	    		output.println(player.getLetters().get(i).getBigPath());
	        	    		output.println(player.getLetters().get(i).isSelected());
	        	    		output.println(player.getLetters().get(i).getPlayerSelected());
	        	    		output.println(player.getLetters().get(i).isJolly());
	        	    	}
	        	    	
	        	    	//Inserimento lettere cpu
	        	    	for(int i=0; i<7; i++){
	        	    		output.println(cpu.getLetters().get(i).getLetter());
	        	    		output.println(cpu.getLetters().get(i).getValue());
	        	    		output.println(cpu.getLetters().get(i).getPath());
	        	    		output.println(cpu.getLetters().get(i).getPathColored());
	        	    		output.println(cpu.getLetters().get(i).getOriginalPath());
	        	    		output.println(cpu.getLetters().get(i).getBigPath());
	        	    		output.println(cpu.getLetters().get(i).isSelected());
	        	    		output.println(cpu.getLetters().get(i).getPlayerSelected());
	        	    		output.println(cpu.getLetters().get(i).isJolly());
	        	    	}
	        	    	
	        	    	//Inserimento lettere box
	        	    	output.println(box.getNumLetters());
	        	    	for(int i=0; i<box.getNumLetters(); i++){
	        	    		output.println(box.getLettersLeft().get(i).getLetter());
	        	    		output.println(box.getLettersLeft().get(i).getValue());
	        	    		output.println(box.getLettersLeft().get(i).getPath());
	        	    		output.println(box.getLettersLeft().get(i).getPathColored());
	        	    		output.println(box.getLettersLeft().get(i).getOriginalPath());
	        	    		output.println(box.getLettersLeft().get(i).getBigPath());
	        	    		output.println(box.getLettersLeft().get(i).isSelected());
	        	    		output.println(box.getLettersLeft().get(i).isJolly());
	        	    		output.println(box.getLettersLeft().get(i).getPlayerSelected());
	        	    	}
	        	    	
	        	    	//Inserimento stato della board
	        	    	for(int i=0; i<15; i++){
	        	    		for(int j=0; j<15; j++){
	        	    			output.println(board.getCell(i, j).getLetter());
	        	    			output.println(board.getCell(i, j).getValue());
	        	    			output.println(board.getCell(i, j).getPath());
	        	    			output.println(board.getCell(i, j).getPathColored());
	        	    			output.println(board.getCell(i, j).getOriginalPath());
	        	    			output.println(board.getCell(i, j).getBigPath());
	        	    			output.println(board.getCell(i, j).isJolly());
	        	    			output.println(board.getCell(i, j).isSelected());
	        	    			output.println(board.getCell(i, j).getPlayerSelected());
	        	    			output.println(board.getCell(i, j).getX1());
	        	    			output.println(board.getCell(i, j).getY1());
	        	    			output.println(board.getCell(i, j).getMultiScore());
	        	    			output.println(board.getCell(i, j).isOccupied());
	        	    			output.println(board.getCell(i, j).isBlocked());
	        	    			output.println(board.getCell(i, j).getDirection());
	        	    		}
	        	    	}
	        	    	
	        	    	
	        	    	file.close();
	        	    	
	        	    } catch (FileNotFoundException e1) {
	        	    	// TODO Auto-generated catch block
	        	    	e1.printStackTrace();
	        	    } catch (IOException e1) {
	        	    	// TODO Auto-generated catch block
	        	    	e1.printStackTrace();
	        	    }
	        	 
	         }
	         
	         //Azione associata al bottone per caricare la partita
	         if(command.equals("carica")) {
	        	 tilesPlaced.clear();
	        	 addHint=false;
	        	 ArrayList <Tile> support= new ArrayList<Tile>();
	        	 ArrayList <Tile> support2=new ArrayList<Tile>();
	        	 ArrayList <Tile> support3=new ArrayList<Tile>();
	        	 
	        	 String current = System.getProperty("user.dir");
	        	 current+="/partite";
	        	 final File root = new File(current);
	        	 FileSystemView fsv = new SingleRootFileSystemView(root);
	        	 LookAndFeel previousLF = UIManager.getLookAndFeel();
	        	 
	        	 JFileChooser fileChooser = null;
	        	 try {
	        	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        	        fileChooser = new JFileChooser(fsv.getHomeDirectory(),fsv);
	        	        fileChooser.setFileFilter(new TxtFileFilter());
	        	        fileChooser.setAcceptAllFileFilterUsed(false);
	        	        fileChooser.setDialogTitle("Scegli una partita salvata");
	        	        UIManager.setLookAndFeel(previousLF);
	        	 } catch (IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException e1) {}
	        	 
	        	 try {
	        		 int n = fileChooser.showOpenDialog(ScrabbleManager.this);
	        		 if (n == JFileChooser.APPROVE_OPTION) {
	        			 player.getLetters().clear();
	        			 cpu.getLetters().clear();
	        			 File f = fileChooser.getSelectedFile();
	        			 BufferedReader read = new BufferedReader(new FileReader(f));
	        			 String line ="";
	        			 while(line != null) {
	        				 line=read.readLine();
	        				 nameFile=line;
	        				 line=read.readLine();
	        				 saveStatus=Boolean.parseBoolean(line);
	        				 System.out.println("saveStatus" + saveStatus);
	        				 line=read.readLine();
	        				 countHint=Integer.parseInt(line);
	        				 line=read.readLine();
	        				 cpu.setDifficulty(Integer.parseInt(line));
	        				 line=read.readLine();
	        				 player.setScore(Integer.parseInt(line));
	        				 line = read.readLine();
	        				 cpu.setScore(Integer.parseInt(line));
	        				 //carica lettere player
	        				 for(int i=0; i<7; i++){
	        					 support.add(new Tile(' ',0,"",false,"","","",""));
	        					 line = read.readLine();
	        					 support.get(i).setLetter(line.charAt(0));
	        					 line = read.readLine();
	        					 support.get(i).setValue(Integer.parseInt(line));
	        					 line = read.readLine();
	        					 support.get(i).setPath(line);
	        					 line = read.readLine();
	        					 support.get(i).setPathColored(line);
	        					 line = read.readLine();
	        					 support.get(i).setOriginalPath(line);
	        					 line = read.readLine();
	        					 support.get(i).setBigPath(line);
	        					 line = read.readLine();
	        					 support.get(i).setSelected(Boolean.parseBoolean(line));
	        					 line = read.readLine();
	        					 support.get(i).setPlayerSelected(line);
	        					 line = read.readLine();
	        					 support.get(i).setJolly(Boolean.parseBoolean(line));
	        					 
	        				 }
	        				 player.setLetters(support);
	        			
	        				 //carica lettere cpu
	        				 for(int i=0; i<7; i++){
	        					 support2.add(new Tile(' ',0,"",false,"","","",""));
	        					 line = read.readLine();
	        					 support2.get(i).setLetter(line.charAt(0));
	        					 line = read.readLine();
	        					 support2.get(i).setValue(Integer.parseInt(line));
	        					 line = read.readLine();
	        					 support2.get(i).setPath(line);
	        					 line = read.readLine();
	        					 support2.get(i).setPathColored(line);
	        					 line = read.readLine();
	        					 support2.get(i).setOriginalPath(line);
	        					 line = read.readLine();
	        					 support2.get(i).setBigPath(line);
	        					 line = read.readLine();
	        					 support2.get(i).setSelected(Boolean.parseBoolean(line));
	        					 line = read.readLine();
	        					 support2.get(i).setPlayerSelected(line);
	        					 line = read.readLine();
	        					 support2.get(i).setJolly(Boolean.parseBoolean(line));
	        					 
	        				 }
	        				 cpu.setLetters(support2);
	        				 
	        				 //Carica lettere box
	        				 box.getLettersLeft().clear();
	        				 line = read.readLine();
	        				 box.setNumLetters(Integer.parseInt(line));
	        				 for(int i=0; i<box.getNumLetters(); i++){
	        					 support3.add(new Tile(' ',0,"",false,"","","",""));
	        					 line = read.readLine();
	        					 support3.get(i).setLetter(line.charAt(0));
	        					 line = read.readLine();
	        					 support3.get(i).setValue(Integer.parseInt(line));
	        					 line = read.readLine();
	        					 support3.get(i).setPath(line);
	        					 line = read.readLine();
	        					 support3.get(i).setPathColored(line);
	        					 line = read.readLine();
	        					 support3.get(i).setOriginalPath(line);
	        					 line = read.readLine();
	        					 support3.get(i).setBigPath(line);
	        					 line = read.readLine();
	        					 support3.get(i).setSelected(Boolean.parseBoolean(line));
	        					 line = read.readLine();
	        					 support3.get(i).setJolly(Boolean.parseBoolean(line));
	        					 line = read.readLine();
	        					 support3.get(i).setPlayerSelected(line);
	        					 
	        				 }
	        				 
	        				 box.setLettersLeft(support3);
	        				 
	        				 //Parte relativa alla board
	        				 //cancella tavola
	 	        	    	 for(int i=0; i<15; i++){
		        	    		for(int j=0; j<15; j++){
		        	    			board.removeTile(i, j);
		        	    		}
	 	        	    	 }
	 	        	    	 
	 	        	    	 //Carica la board
		        	    	 for(int i=0; i<15; i++){
			        	    	for(int j=0; j<15; j++){
			        	    		line = read.readLine();
			        	    		board.getCell(i, j).setLetter(line.charAt(0));
			        	    		line = read.readLine();
			        	    		board.getCell(i, j).setValue(Integer.parseInt(line));
			        	   			line = read.readLine();
			        	   			board.getCell(i, j).setPath(line);
			        	   			line = read.readLine();
			        	   			board.getCell(i, j).setPathColored(line);
			            			line = read.readLine();
			       	    			board.getCell(i, j).setOriginalPath(line);
			       	    			line = read.readLine();
			       	    			board.getCell(i, j).setBigPath(line);
			       	    			line = read.readLine();
			       	    			board.getCell(i, j).setJolly(Boolean.parseBoolean(line));
			       	    			line = read.readLine();
			       	    			board.getCell(i, j).setSelected(Boolean.parseBoolean(line));
			       	    			line = read.readLine();
		        	    			board.getCell(i, j).setPlayerSelected(line);
		        	    			line = read.readLine();
		        	    			board.getCell(i, j).setX1(Integer.parseInt(line));
			        	    		line = read.readLine();
			        	    		board.getCell(i, j).setY1(Integer.parseInt(line));
			        	    		line = read.readLine();
			        	   			board.getCell(i, j).setMultiScore(Integer.parseInt(line));
			        	   			line = read.readLine();
			        	   			board.getCell(i, j).setOccupied(Boolean.parseBoolean(line));
			        	   			line = read.readLine();
			            			board.getCell(i, j).setBlocked(Boolean.parseBoolean(line));
			       	    			line = read.readLine();
			       	    			board.getCell(i, j).setDirection(Integer.parseInt(line));
		        	    		}
		        	    	}
		        	    	 line = read.readLine();
		        	    	repaintBoard();
	        				 
	        			 }
	        			 read.close();
	        		 }
	        	 	} catch (Exception ex) {}
	        	 
	        	 repaintButtonsPanel();
	        	 repaintPlayerPanel();
	        	 for(int i=0; i<player.getLetters().size(); i++)
	        		 System.out.println(player.getLetters().get(i).getLetter());
	        	 repaintCPUPanel();
	        	 repaintDictPanel();
	        	 if(countHint<=0)
	        		 hint.setEnabled(false);
	        	 else
	        		 hint.setEnabled(true);
	     		 
	     		 salva.setEnabled(true);
	     		 carica.setEnabled(true);
	     		 newGame.setEnabled(true);
	     		 vocabulary.setEnabled(true);
	     		 boxButton.setEnabled(true);
	     		 text.setText("Totale lettere rimanenti: " + box.getNumLetters());
	     		 lettersTable.removeAll();
	        	 fillBoxPanel();
	     		 repaint();
	     		 validate();
	         }
	         
	         
	         
	         //PARTE RELATIVA A HINT PLAYER
	         if(command.equals("hint")) {
	        	 addHint=false;
	        	 //decremento il countHint!
	        	 System.out.println("countHint prima di schiacciare "+countHint);
	        	 countHint--;
	        	 System.out.println("countHint dopo "+countHint);
	        	 southEast.removeAll();
	        	 salva.setEnabled(false);
	        	 carica.setEnabled(false);
	        	 hint.setEnabled(false);
	        	//BOTTONE ANNULLA
	     		BufferedImage buttonIconCancel = null;
	     		try {
	     			buttonIconCancel = ImageIO.read(new File("img/buttons/annulla.png"));
	     		} catch (IOException e1) {
	     			// TODO Auto-generated catch block
	     			e1.printStackTrace();
	     		}
	     		cancelHint=new JButton(new ImageIcon(buttonIconCancel));
	     		cancelHint.setBorder(BorderFactory.createEmptyBorder());
	     		cancelHint.setContentAreaFilled(false);
	     		cancelHint.setActionCommand("cancelHint");
	     		cancelHint.setVisible(true);
	     		cancelHint.addActionListener(new ButtonClickListener() );
	     		cancelHint.addMouseListener(new MouseAdapter()
	     		{
	     	            public void mouseEntered(MouseEvent evt){
	     	            	ImageIcon img = new ImageIcon("img/buttons/annullaHover.png");
	     	            	cancelHint.setIcon(img);
	     	            }
	     	            public void mouseExited(MouseEvent evt) {
	     	            	ImageIcon img = new ImageIcon("img/buttons/annulla.png");
	     	            	cancelHint.setIcon(img);
	     	            }
	     	            public void mousePressed(MouseEvent evt){
	     	            	ImageIcon img = new ImageIcon("img/buttons/annullaHover.png");
	     	            	cancelHint.setIcon(img);
	     	            }
	     	            public void mouseReleased(MouseEvent evt){
	     	            	ImageIcon img = new ImageIcon("img/buttons/annulla.png");
	     	            	cancelHint.setIcon(img);
	     	            } 
	     	        });
	     		
	     		//BOTTONE CONFERMA
	     		BufferedImage buttonIcon = null;
	     		try {
	     			buttonIcon = ImageIO.read(new File("img/buttons/conferma.png"));
	     		} catch (IOException e1) {
	     			// TODO Auto-generated catch block
	     			e1.printStackTrace();
	     		}
	     		
	     		confirmHint=new JButton(new ImageIcon(buttonIcon));
	     		confirmHint.setBorder(BorderFactory.createEmptyBorder());
	     		confirmHint.setContentAreaFilled(false);
	     		confirmHint.setActionCommand("confermaHint");
	     		confirmHint.addActionListener(new ButtonClickListener());
	     		confirmHint.setVisible(true);
	     		confirmHint.addMouseListener(new MouseAdapter()
	     		{
	     	            public void mouseEntered(MouseEvent evt){
	     	            	ImageIcon img = new ImageIcon("img/buttons/confermaHover.png");
	     	            	confirmHint.setIcon(img);
	     	            }
	     	            public void mouseExited(MouseEvent evt) {
	     	            	ImageIcon img = new ImageIcon("img/buttons/conferma.png");
	     	            	confirmHint.setIcon(img);
	     	            }
	     	            public void mousePressed(MouseEvent evt){
	     	            	ImageIcon img = new ImageIcon("img/buttons/confermaHover.png");
	     	            	confirmHint.setIcon(img);
	     	            }
	     	            public void mouseReleased(MouseEvent evt){
	     	            	ImageIcon img = new ImageIcon("img/buttons/conferma.png");
	     	            	confirmHint.setIcon(img);
	     	            } 
	     	        });
	     		
	     		//BOTTONE CAMBIA LETTERE
	    		BufferedImage buttonIconChangeL = null;
	    		try {
	    			buttonIconChangeL = ImageIO.read(new File("img/buttons/cambia.png"));
	    		} catch (IOException e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}
	    		changeHint=new JButton(new ImageIcon(buttonIconChangeL));
	    		changeHint.setBorder(BorderFactory.createEmptyBorder());
	    		changeHint.setContentAreaFilled(false);
	    		changeHint.setActionCommand("Cambia lettere");
	    		changeHint.addActionListener(new ButtonClickListener());
	    		changeHint.addMouseListener(new MouseAdapter()
	    		{
	    	            public void mouseEntered(MouseEvent evt){
	    	            	ImageIcon img = new ImageIcon("img/buttons/cambiaHover.png");
	    	            	changeHint.setIcon(img);
	    	            }
	    	            public void mouseExited(MouseEvent evt) {
	    	            	ImageIcon img = new ImageIcon("img/buttons/cambia.png");
	    	            	changeHint.setIcon(img);
	    	            }
	    	            public void mousePressed(MouseEvent evt){
	    	            	ImageIcon img = new ImageIcon("img/buttons/cambiaHover.png");
	    	            	changeHint.setIcon(img);
	    	            }
	    	            public void mouseReleased(MouseEvent evt){
	    	            	ImageIcon img = new ImageIcon("img/buttons/cambia.png");
	    	            	changeHint.setIcon(img);
	    	            } 
	    	        });
	 
	     		 
	        	 placedWord = null;
	        	 try {
					placedWord=player.hint(box, board, 0);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	 
	        	scoreHint=placedWord.getMultiScore();
	        	System.out.println("score hint "+ scoreHint);
	        	//controllo se aggiungere un hint
	        	if(scoreHint>=20)
	        		addHint=true;
	        	
	        	//Ridisegno pannello player col pannello nuovo!
	        	//Attenzione! nel caso in cui il pc non abbia suggerimenti--> cambia lettere
	        	southPanel.removeAll();
	        	southCenter.removeAll();
	     		player.changeIcon();
	        	 	for(int i=0; i<player.getLetters().size(); i++){
	        	 		southCenter.add(player.getLetters().get(i) );
	        	 		player.getLetters().get(i).addActionListener(new ButtonClickListener() );
	        	 		player.getLetters().get(i).setActionCommand("tile");	
	        	 	}
	        	 //c'è hint
	        	if(placedWord.getWord().length()!=0){
	        		southEast.add(cancelHint);
		        	southEast.add(confirmHint);
	        	}
	        	//non è possibile formare una parola-->unica mossa: cambia lettere
	        	else {
	        		southEast.add(changeHint);
	        		countHint++;
	        		//msg: cambia lettere unica mossa consentita
	        		LookAndFeel previousLF = UIManager.getLookAndFeel();
	    	   		try {
	    				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
	    					| UnsupportedLookAndFeelException e1) {
	    				// TODO Auto-generated catch block
	    				e1.printStackTrace();
	    			}
	    	   		JOptionPane.showMessageDialog(scrabble, noHintPlayer, "Attenzione", JOptionPane.PLAIN_MESSAGE);
	    	   		try {
	    				UIManager.setLookAndFeel(previousLF);
	    			} catch (UnsupportedLookAndFeelException e1) {
	    				// TODO Auto-generated catch block
	    				e1.printStackTrace();
	    			} 
	        	}
	        	
	        	southWest.removeAll();
	        	southWest.add(playerName);
	        	southWest.add(playerNameValue);
	        	southWest.add(playerScore);
	        	playerScoreValue.setText("" + player.getScore());
	        	southWest.add(playerScoreValue);
	        	southPanel.add(southWest);
	        	southPanel.add(southCenter);
	        	southPanel.add(southEast);
	        	
	     		southPanel.repaint();
	     		southPanel.validate();
	     		
	        	 
	         }
	         
	         //Azione associata al bottone annulla relativo a hint player
	         //(Anche se player clicca annulla il countHint deve rimanere decrementato)
	         if(command.equals("cancelHint")) {
	        	 salva.setEnabled(true);
	        	 carica.setEnabled(true);
	        	 if(countHint==0)
	        		 hint.setEnabled(false);
	        	 else
	        		 hint.setEnabled(true);
	        	 
	        	 for(int i=0; i<player.getRevertOriginal().size();i++){
	        		 board.getCell(player.getRevertOriginal().get(i).getRow(),player.getRevertOriginal().get(i).getCol()).setPath(player.getRevertOriginal().get(i).getOriginalPath());
	        		 board.removeTile(player.getRevertOriginal().get(i).getRow(), player.getRevertOriginal().get(i).getCol());
	        		 player.setLetter(player.getRevertOriginal().get(i).getOriginalTile());
				}
	        	 repaintPlayerPanel();	 
	        	 
	         }
	         
	         //Azione associata al bottone conferma hint player
	         if(command.equals("confermaHint")) {
	        	 salva.setEnabled(true);
	        	 carica.setEnabled(true);
	        	 if(addHint && countHint<5)
	        		 countHint++;
	        	 System.out.println("countHint dopo conferma "+countHint);
	        	 if(countHint==0)
	        		 hint.setEnabled(false);
	        	 else
	        		 hint.setEnabled(true);
	        	 scoreHint+=player.getScore();
	        	 player.setScore(scoreHint);
	        	 if(board.occupiedPositions()!=placedWord.getWord().length()){
	     			//setto il timer in modo che dopo 2 secondi il punteggio player torni "normale"
	     			playerScoreValue.setForeground(orange);
	     			timerScore=new Timer();
	     			timerScore.schedule(new scoreTimerTask(), 2000);
	     		}
	     		//Assegno nuove lettere al player
	     			//Se il primo turno è del player	 
	     		if(board.occupiedPositions()==placedWord.getWord().length()){
	     			for(int i=0; i<placedWord.getWord().length(); i++)
	     					player.setLetter(box.randomExtraction());
	     		}
	     		
	     		else {  
	     			 for(int i=0; i<placedWord.getWord().length()-1; i++){
	     				 if(box.getNumLetters()==0){
	     					 endGame();
	     					 break;
	     				 }
	     				  player.setLetter(box.randomExtraction());
	     			 }
	     		}
	     		//rimetto le tiles al colore originario
	     		for(int i=0; i<player.getRevertOriginal().size();i++){
					   board.getCell(player.getRevertOriginal().get(i).getRow(), player.getRevertOriginal().get(i).getCol()).setPath(player.getRevertOriginal().get(i).getOriginalPath());
				}
	     		
	     		repaintPlayerPanel();
	     		repaintButtonsPanel();
	     		//Cede turno al computer
	     		timerCPU=new Timer();
				timerCPU.schedule(new CPUroundTask(), 1000);
	        	 
	         }
	         
	         
	         //Azione associata al bottone box
	         //Mostra il contenuto della box 
	         if(command.equals("box button")) {
	        	 //Ridisegno il pannello
	        	 lettersTable.removeAll();
	        	 fillBoxPanel();
	        	 text.setText("Totale lettere rimanenti: " + box.getNumLetters());
	        	 //Rendo visibile il pannello della box
	        	 infoContainer.remove(playersInfoPanel);
	        	 infoContainer.add(supportPanel);
	        	 infoContainer.repaint();
	        	 infoContainer.validate();	 
	        	 guide.setEnabled(false);
	         }
	         
	         //Azione associata al bottone ok del pannello box
	         if (command.equals("confirmBox")){
	        	 //Nascondo il pannello della box
	        	 infoContainer.remove(supportPanel);
	        	 infoContainer.add(playersInfoPanel);
	        	 infoContainer.repaint();
	        	 infoContainer.validate();  
	        	 //aggiorno bottone box
	        	 repaintButtonsPanel();
	        	 guide.setEnabled(true);
	        	 
	         }
	         
	         //Azione associata al bottone vocabulary
	         if (command.equals("vocabulary")){
	        	 //Rendo visibile il pannello
	        	 infoContainer.remove(playersInfoPanel);
	        	 infoContainer.add(supportPanelDict);
	        	 letters.removeAll();
	        	 for(int i=0; i<NUM_LETTERS; i++){
	     			String let;
	     			if(player.getLetters().get(i).isJolly())
	     				let = "jolly";
	     			else {
	     				let = "" + player.getLetters().get(i).getLetter();
	     				let.toLowerCase();
	     			}
	     			BufferedImage letterIcon = null;
	     			try {
	     				letterIcon = ImageIO.read(new File("img/tiles/tilesFlat/" + let + ".png"));
	     			} catch (IOException e1) {
	     				// TODO Auto-generated catch block
	     				e1.printStackTrace();
	     			}
	     			JButton letterBtn=new JButton(new ImageIcon(letterIcon));
	     			letterBtn.setBorder(BorderFactory.createEmptyBorder());
	     			letterBtn.setContentAreaFilled(false);
	     			letters.add(letterBtn);
	     		}
	        	 letters.repaint();
	        	 letters.validate();
	        	 supportPanelDict.repaint();
	        	 supportPanelDict.validate();
	        	 infoContainer.repaint();
	        	 infoContainer.validate();
	        	 guide.setEnabled(false);
	        	 statistics.setEnabled(false);
	         }
	         
	        //Azione associata al bottone torna indietro del pannello vocabulary
	         if (command.equals("confirmDict")){
	        	 //Rendo invisibili i risultati della ricerca
	        	 known.setVisible(false);
	        	 //resetto la textField
	        	 textField01.setText("Parola da cercare");
	        	 //Nascondo il pannello vocabulary
	        	 infoContainer.remove(supportPanelDict);
	        	 infoContainer.add(playersInfoPanel);
	        	 infoContainer.repaint();
	        	 infoContainer.validate();  
	        	 //aggiorno bottone box
	        	 repaintButtonsPanel();
	        	 guide.setEnabled(true);
	        	 statistics.setEnabled(true);
	        	 
	         }
	         
	         //Azione associata al bottone invia del pannello vocabulary
	         //Cerca nel dizionario se la parola inserita nella textfield esista o no
	         if (command.equals("invia")){
	        	 boolean found = false;
	        	 String wordToFind = textField01.getText();
	        	 System.out.println(wordToFind);
	        	 dictionary=new File("it.txt");
	 		     try {
	 		    	LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(dictionary)));
	 		    	String lineRead = null;									 
	 		    	while((lineRead = reader.readLine()) != null) {
	 		    		if((lineRead.compareToIgnoreCase(wordToFind)==0)){
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
	 		     
	 		     if(found){
	 		    	 known.setText("Parola conosciuta");
	 		    	 known.setForeground(green);
	 		    	 known.setVisible(true);
	 		     }
	 		     
	 		     else {
	 		    	known.setText("Parola non conosciuta");
	 		    	known.setForeground(pink);
	 		    	known.setVisible(true);
	 		     }
	 		    	 
	        	 
	         }
	         
	         //Azione assoiata al bottone sfoglia dizionario
	         if (command.equals("sfoglia")){
	        	 //Rendo visibile il pannello
	        	 supportPanelDict.remove(vocabularyPanel);
	        	 supportPanelDict.add(browse);
	        	 newGame.setEnabled(false);
	        	 supportPanelDict.repaint();
	        	 supportPanelDict.validate();
	        	 
	         }
	         
	         //Azioni associate ai bottoni per ricercare per lettera nel dizionario
	         if (command.equals("A")){
	        	 browseDictionary('a');
	        	 dictContent.setCaretPosition(0);
	         }
			 if (command.equals("B")){
				 browseDictionary('b');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("C")){
				 browseDictionary('c');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("D")){
				 browseDictionary('d');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("E")){
				 browseDictionary('e');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("F")){
				 browseDictionary('f');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("G")){
				 browseDictionary('g');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("H")){
				 browseDictionary('h');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("I")){
				 browseDictionary('i');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("L")){
				 browseDictionary('l');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("M")){
				 browseDictionary('m');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("N")){
				 browseDictionary('n');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("O")){
				 browseDictionary('o');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("P")){
				 browseDictionary('p');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("Q")){
				 browseDictionary('q');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("R")){
				 browseDictionary('r');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("S")){
				 browseDictionary('s');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("T")){
				 browseDictionary('t');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("U")){
				 browseDictionary('u');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("V")){
				 browseDictionary('v');
				 dictContent.setCaretPosition(0);
			 }
			 if (command.equals("Z")){
				 browseDictionary('z');
				 dictContent.setCaretPosition(0);
			 }
			 
	         //Azione associata al bottone ok del pannello browseDict
			 //Torna alla pagina Dizionario
			 if (command.equals("OKBrowseDict")){
				 //Rendo invisibili i risultati della ricerca
	        	 known.setVisible(false);
	        	 //resetto la textField
	        	 textField01.setText("Parola da cercare");
	        	 supportPanelDict.remove(browse);
	        	 supportPanelDict.add(vocabularyPanel);
	        	 supportPanelDict.repaint();
	        	 supportPanelDict.validate();
	        	 newGame.setEnabled(true);

			 }
			 
			 //Azione associata al bottone 'torna al gioco' del pannello browseDict
			 if (command.equals("backGameBrowseDict")){
				 //Rendo invisibili i risultati della ricerca
	        	 known.setVisible(false);
	        	 //resetto la textField
	        	 textField01.setText("Parola da cercare");
	        	 supportPanelDict.remove(browse);
	        	 supportPanelDict.add(vocabularyPanel);
	        	 infoContainer.remove(supportPanelDict);
	        	 infoContainer.add(playersInfoPanel);
	        	 infoContainer.repaint();
	        	 infoContainer.validate();  
	        	 //aggiorno bottone box
	        	 repaintButtonsPanel();
	        	 newGame.setEnabled(true);
	        	 guide.setEnabled(true);
	        	 statistics.setEnabled(true);

			 }
			 
			 //AZIONI MENU!!
			 //Nuova partita
			 if (command.equals("nuova partita")){
				 newGame();
				 whoStarts();
				 
			 }
			 //Statistiche
			 if (command.equals("statistiche")){
				 infoContainer.remove(playersInfoPanel); 
				 repaintStatsPanel();
				 infoContainer.add(statsPanel);
				 infoContainer.repaint();
	        	 infoContainer.validate();  
	        	 repaintButtonsPanel();
	        	 guide.setEnabled(false);
	        	 newGame.setEnabled(false);
	        	 carica.setEnabled(false);
	        	 
			 }
			 if (command.equals("backGameStats")){
				 guide.setEnabled(true);
	        	 newGame.setEnabled(true);
	        	 carica.setEnabled(true);
				 infoContainer.remove(statsPanel); 
				 infoContainer.add(playersInfoPanel);
				 infoContainer.repaint();
	        	 infoContainer.validate();  
	        	 repaintButtonsPanel();
			 }
			 //Guida
			 if (command.equals("guida")){
				 infoContainer.remove(playersInfoPanel); 
				 infoContainer.add(guidePanel);
				 guideImg.removeAll();
				 guideImg.add(guideImgBtn);
				 btnsPanel.removeAll();
				 btnsPanel.add(BackGameBtnGuide);
				 btnsPanel.add(avantiBtn1);
				 infoContainer.repaint();
	        	 infoContainer.validate(); 
	        	 statistics.setEnabled(false);
	        	 
			 }
			 
			 //Azione associata al bottone torna al gioco della guida
			 if (command.equals("backGameGuide")){
				 infoContainer.remove(guidePanel); 
				 infoContainer.add(playersInfoPanel);
				 infoContainer.repaint();
	        	 infoContainer.validate();  
	        	 repaintButtonsPanel();
	        	 statistics.setEnabled(true);
			 }
			 
			 
			 if (command.equals("avanti1")){
				guideImg.removeAll();
				guideImg.add(guideImgBtn2);
				btnsPanel.removeAll();
				btnsPanel.add(BackGameBtnGuide);
				btnsPanel.add(indietro1);
				btnsPanel.add(avantiBtn2);
				infoContainer.repaint();
	        	infoContainer.validate();
	        	 
			 }
			 if (command.equals("avanti2")){
				 guideImg.removeAll();
				 guideImg.add(guideImgBtn3);
				 btnsPanel.removeAll();
				 btnsPanel.add(BackGameBtnGuide);
				 btnsPanel.add(indietro2);
				 btnsPanel.add(avantiBtn3);
				 infoContainer.repaint();
				 infoContainer.validate();
		        	 
			 }
			 if (command.equals("avanti3")){
				 guideImg.removeAll();
				 guideImg.add(guideImgBtn4);
				 btnsPanel.removeAll();
				 btnsPanel.add(BackGameBtnGuide);
				 btnsPanel.add(indietro3);
				 btnsPanel.add(avantiBtn4);
				 infoContainer.repaint();
				 infoContainer.validate();
		        	 
			 }
			 if (command.equals("avanti4")){
				 guideImg.removeAll();
				 guideImg.add(guideImgBtn5);
				 btnsPanel.removeAll();
				 btnsPanel.add(BackGameBtnGuide);
				 btnsPanel.add(indietro4);
				 infoContainer.repaint();
				 infoContainer.validate();
		        	 
			 }
			 if (command.equals("indietro1")){
				 guideImg.removeAll();
				 guideImg.add(guideImgBtn);
				 btnsPanel.removeAll();
				 btnsPanel.add(BackGameBtnGuide);
				 btnsPanel.add(avantiBtn1);
				 infoContainer.repaint();
				 infoContainer.validate();
		        	 
			 }
			 if (command.equals("indietro2")){
				 guideImg.removeAll();
				 guideImg.add(guideImgBtn2);
				 btnsPanel.removeAll();
				 btnsPanel.add(BackGameBtnGuide);
				 btnsPanel.add(indietro1);
				 btnsPanel.add(avantiBtn2);
				 infoContainer.repaint();
				 infoContainer.validate();
		        	 
			 }
			 if (command.equals("indietro3")){
				 guideImg.removeAll();
				 guideImg.add(guideImgBtn3);
				 btnsPanel.removeAll();
				 btnsPanel.add(BackGameBtnGuide);
				 btnsPanel.add(indietro2);
				 btnsPanel.add(avantiBtn3);
				 infoContainer.repaint();
				 infoContainer.validate();
		        	 
			 }
			 if (command.equals("indietro4")){
				 guideImg.removeAll();
				 guideImg.add(guideImgBtn4);
				 btnsPanel.removeAll();
				 btnsPanel.add(BackGameBtnGuide);
				 btnsPanel.add(indietro3);
				 btnsPanel.add(avantiBtn4);
				 infoContainer.repaint();
				 infoContainer.validate();
		        	 
			 }
			 
			 			 
			    
	         
	      }		
	   }
	 
	 
	 public static int askDifficulty(){
		 UIManager.put("OptionPane.background", white);
		 	UIManager.put("Panel.background", white);
		 	BufferedImage titleIcon = null;
			try {
				titleIcon = ImageIO.read(new File("img/titles/difficoltà.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JButton titleBtn = new JButton(new ImageIcon(titleIcon));
			titleBtn.setBorder(BorderFactory.createEmptyBorder());
			titleBtn.setContentAreaFilled(false);
			
	   		Object[] possibilities={"Facile","Medio","Difficile"};
	   		final JComboBox<Object> combo = new JComboBox<Object>(possibilities);
	   		combo.setBackground(lightBlue);
	   		combo.setFont(proximaNova);
	   		combo.setForeground(darkBlue);
	   		String[] options = { "OK" };
	   		String title="Attenzione";
	   		JPanel message=new JPanel(new BorderLayout());
	   		JLabel text= new JLabel("Seleziona la difficoltà: \n");
	   		text.setFont(museo2);
	   		text.setForeground(darkBlue);
	   		text.setHorizontalAlignment(0);
	   		message.add(titleBtn, BorderLayout.NORTH);
	   		message.add(combo,BorderLayout.CENTER);
	   		LookAndFeel previousLF = UIManager.getLookAndFeel();
	   		try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   		JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,options, options[0]);
	   		try {
				UIManager.setLookAndFeel(previousLF);
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	   	   	String difficulty = (String) combo.getSelectedItem();
	   	   	int diff=0;
	   	   	if(difficulty.equals("Facile"))
	   	   		diff=0;
	   	   	if(difficulty.equals("Medio"))
	   	   		diff=1;
	   	   	if(difficulty.equals("Difficile"))
	   	   		diff=2;
	   	   	return diff;
	 }
	 
	 
	 public void newGame(){
		 tilesPlaced.clear();
		 countHint=2;
		 countTurn=0;
	   	 saveStatus=false;
	   	 nameFile="";
		 box = new Box();
		 board = new Board();
		 ArrayList<Tile> extractedLetters=new ArrayList<Tile> ();
		 ArrayList<Tile> extractedLettersCpu=new ArrayList<Tile> ();
		 //Estrazione nuove lettere per player
		 for(int i=0; i<NUM_LETTERS; i++)
				extractedLetters.add(box.randomExtraction());
		 player=new Player(extractedLetters);
			
		//Estrazione nuove lettere per il computer
		for(int i=0; i<NUM_LETTERS; i++)
				extractedLettersCpu.add(box.randomExtraction());
		cpu=new Computer(extractedLettersCpu);
		cpu.setDifficulty(askDifficulty());
		//Ridisegno la board
		center.removeAll();
		for(int i=0; i<board.getROWS(); i++){
			for(int j=0; j<board.getCOLUMNS(); j++){
				center.add(board.getBoard()[i][j]);
				board.getBoard()[i][j].addActionListener(new ButtonClickListener());
				board.getBoard()[i][j].setActionCommand("tileBoard");
			}	
		}
		
		center.repaint();
		center.validate();
		repaintCPUPanel();
		repaintPlayerPanel();
		hint.setEnabled(true);
		salva.setEnabled(true);
		carica.setEnabled(true);
		repaintButtonsPanel();
		repaintDictPanel();
		text.setText("Totale lettere rimanenti: " + box.getNumLetters());
		lettersTable.removeAll();
   	 	fillBoxPanel();
		this.repaint();
		this.validate();
	 }
	 
	 
	 //Decide a chi tocca il primo turno
	 public void whoStarts(){
		 String token=new String();
			
			//Scelta turno iniziale
			int round=new Random().nextInt(2);
			if(round==0)
				token="player";
			else
				token="cpu";
			
			if(token.equals("cpu")){
				//turno cpu
				computerRound(board);
		
			}
	 }
	 
	
	public static void main(String[] args){
   		ScrabbleManager scrabble=new ScrabbleManager();
   		scrabble.scrabbleM=scrabble;
   		cpu.setDifficulty(askDifficulty());
   		scrabble.saveStatus=false;
   		scrabble.nameFile="";
		scrabble.whoStarts();
	}
	
	
 
	
}


