import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow {

	//size of puzzle, moves done, max moves allowed
	private int size=0, limit;

	//for initial panel 
	private JButton easy =  new JButton("Small"),
			medium = new JButton("Medium"), 
			large = new JButton("Large");

	//in game controls
	private JButton newgame = new JButton("New Game"),
			changeSize = new JButton("Change Size"),
			quit = new JButton("Quit"); 

	private JLabel movelabel = new JLabel(), scorelabel = new JLabel();
	//small is 14, medium is 21, large is 28

	//private Color currentColor ;

	private GameGrid gmgrd ;

	private JFrame mainFrame = new JFrame("");
	private BorderLayout b = new BorderLayout();
	private JFrame tempfr = new JFrame("Select the size of the puzzle!");
	private ImageIcon im = new ImageIcon(getClass().getClassLoader().getResource("images/smiley_tongue.gif"));

	private JPanel colorsPanel = new JPanel();
	private JPanel scorePanel = new JPanel();	
	private JPanel controlPanel = new JPanel();

	private JButton yellowb = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/yellow.png")));
	private JButton cyanb = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/cyan.png")));
	private JButton greenb = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/green.png")));
	private JButton redb = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/red.png")));
	private JButton blueb = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/blue.png")));
	private JButton orangeb = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/orange.png")));

	//listeners for size buttons
	private ButtonListener el = new ButtonListener(14,25);
	private ButtonListener ml = new ButtonListener(21,35);
	private ButtonListener ll = new ButtonListener(28,50);

	private Color green1 = new Color(19,109,35) ; 
	private Color orange1 = new Color(238,150,27);

	//listeners for colour buttons 
	private ColorListener yl = new ColorListener(Color.yellow);
	private ColorListener rl = new ColorListener(Color.red);
	private ColorListener bl = new ColorListener(Color.blue);
	private ColorListener cl = new ColorListener(Color.cyan);
	private ColorListener gl = new ColorListener(green1);
	private ColorListener ol = new ColorListener(orange1);

	private JFrame fr = new JFrame();

	private JMenuBar menuBar = new JMenuBar();
	private JMenu game = new JMenu("Game");
	private JMenu help = new JMenu("Help");
	private JMenuItem save = new JMenuItem("Save Game");
	private JMenuItem load = new JMenuItem("Load Game");
	private JMenuItem about = new JMenuItem("About this game..");
	private JMenuItem howto = new JMenuItem("How To Play Flood Lord");
	

	//objects used for saving and loading 
	private String saveName ;
	private File savedGameFile ;
	private FileOutputStream fos ;
	private ObjectOutputStream oos ;//
	private FileInputStream fis ;
	private ObjectInputStream ois ;
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("FloodLord save files ", "FLDSave");
	private final JFileChooser jfc = new JFileChooser();

	public static void main(String[] args){
		MainWindow m = new MainWindow();
		m.ShowInitialWindow();

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			//	e.printStackTrace();
		}
	}

	//add listeners here to add them just once 
	public MainWindow(){

		jfc.setMultiSelectionEnabled(false);

		gmgrd = new GameGrid(0);

		easy.addActionListener(el);
		medium.addActionListener(ml);
		large.addActionListener(ll);

		yellowb.addActionListener(yl);
		blueb.addActionListener(bl);
		orangeb.addActionListener(ol);
		redb.addActionListener(rl);
		cyanb.addActionListener(cl);
		greenb.addActionListener(gl);

		BorderLayout b = new BorderLayout();
		b.setHgap(10);
		mainFrame.setLayout(b);
	}

	//ShowInitialWindow is probably done !
	public void ShowInitialWindow(){

		tempfr.setLayout(new GridBagLayout());
		GridBagConstraints c23 = new GridBagConstraints();

		c23.gridx = 0 ;
		c23.gridy = 0 ;
		c23.anchor = GridBagConstraints.CENTER;
		c23.gridwidth = 3 ;
		c23.insets = new Insets(15,15,15,15) ;

		JLabel lbl = new JLabel("Choose puzzle size!");
		lbl.setHorizontalTextPosition(JLabel.LEADING);
		lbl.setIcon(im);
		tempfr.add(lbl,c23);


		GridBagConstraints c24 = new GridBagConstraints();

		c24.gridx = 0 ;
		c24.gridy = 1 ;
		c24.insets = new Insets(10,10,10,10) ;


		easy.setBackground(Color.white);
		tempfr.add(easy,c24);

		c24.gridx = 1;
		medium.setBackground(Color.yellow);
		tempfr.add(medium,c24);

		c24.gridx = 2;
		large.setBackground(Color.red);
		tempfr.add(large,c24);


		tempfr.pack();
		tempfr.setSize(400,150);
		tempfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tempfr.setVisible(true);
	}

	//recreate each time to make it fresh !
	public void makeWindow(){

		makeFooter();
		makeColorsPanel();
		makeScorePanel();
		makeMenuBar();

		mainFrame = new JFrame("FloodLord v2.2 by Dennis Perez Mavrogenis");

		BorderLayout b = new BorderLayout();
		b.setHgap(10);
		mainFrame.getContentPane().setLayout(b);

		gmgrd.generateGrid(size);

		//currentColor = gmgrd.getInitialColor();

		mainFrame.add(scorePanel, BorderLayout.EAST);
		mainFrame.add(colorsPanel, BorderLayout.NORTH);
		mainFrame.add(gmgrd, BorderLayout.CENTER);
		mainFrame.add(controlPanel,BorderLayout.SOUTH);	
		mainFrame.setJMenuBar(menuBar);

		mainFrame.pack();
		mainFrame.setSize(750,750);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//done ! not specifying a GridBagContraints object causes everything to center smoothly :)
	public void makeFooter(){
		controlPanel.setLayout(new GridBagLayout());

		newgame.addActionListener(new NGListener());
		quit.addActionListener(new QuitListener());
		changeSize.addActionListener(new SizeListener());

		controlPanel.add(newgame);
		controlPanel.add(changeSize);
		controlPanel.add(quit);
	}

	//probably done !
	public void makeColorsPanel(){
		colorsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0 ;
		c.gridy = 0 ;

		colorsPanel.add(yellowb,c);

		c.gridx = 1 ;

		colorsPanel.add(greenb,c);
		c.gridx = 2 ;
		colorsPanel.add(cyanb,c);

		c.gridx = 3 ;

		colorsPanel.add(redb,c);

		c.gridx = 4 ;
		colorsPanel.add(blueb,c);

		c.gridx = 5;
		colorsPanel.add(orangeb,c);

		colorsPanel.setPreferredSize(new Dimension(400,80));		
	}

	//make scirepanel !
	public void makeScorePanel(){
		scorePanel.setPreferredSize(new Dimension(150, mainFrame.getHeight()));
		scorePanel.add(movelabel);
		scorePanel.add(scorelabel);
	}	

	public void displayMessageWindow(String msg,String ttl,boolean b){
		if(b == true ){
			JOptionPane.showMessageDialog(mainFrame,msg,ttl,JOptionPane.PLAIN_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(mainFrame,msg,ttl,JOptionPane.ERROR_MESSAGE);
		}
	}

	public void makeMenuBar(){
		about.addActionListener(new AboutListener());
		save.addActionListener(new SaveListener());
		load.addActionListener(new LoadListener());
		howto.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String msg = "Flood the whole board with one color within the allowed steps.\nYou start from the top left corner and progress by selecting \n one of the colored balls on the left.\n When you change your current area color, every adjacent \n square with the same color also changes, that way you can flood other areas of the board.\n Select from 3 sizes of the board and become the flood lord in the least amount of steps!\n Addictive and Fun!" ;
				JOptionPane.showMessageDialog(mainFrame,msg,"How To Play!?",JOptionPane.PLAIN_MESSAGE);
				
			}});

		game.add(save);
		game.add(load);
		help.add(about);
		help.add(howto);

		menuBar.add(game);
		menuBar.add(help);
	}

	public void enableButtons(){
		cyanb.setEnabled(true);
		orangeb.setEnabled(true);
		greenb.setEnabled(true);
		blueb.setEnabled(true);
		yellowb.setEnabled(true);
		redb.setEnabled(true);
	}

	public void disableButtons(){	
		cyanb.setEnabled(false);
		orangeb.setEnabled(false);
		greenb.setEnabled(false);
		blueb.setEnabled(false);
		yellowb.setEnabled(false);
		redb.setEnabled(false);
	}

	public void checkGameStatus(){
		if(gmgrd.getStatus()){
			disableButtons();
			displayMessageWindow("Congratulations :) !", "You WON!", true);
		}else if(gmgrd.getMoves()>=limit&&!gmgrd.getStatus()){
			disableButtons();
			displayMessageWindow("Try harder!", "Sorry...", false);
		}
	}

	public void SaveGame() {
		savedGameFile = new File(saveName+".FLDSave");
		try {
			fos = new FileOutputStream(savedGameFile);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(gmgrd);
			oos.flush();
			oos.close();

			System.out.println("Done Saving. Savd as : "+saveName);
			JOptionPane.showMessageDialog(mainFrame, "Saved Game Successfully as :"+saveName+".FLDSave","Saved Successfully",JOptionPane.PLAIN_MESSAGE);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void LoadGame(File f){
		savedGameFile = f ;
		try {
			fis = new FileInputStream(f);
			ois = new ObjectInputStream(fis);
			
			b.removeLayoutComponent(gmgrd);
			mainFrame.remove(gmgrd);
			
			gmgrd = (GameGrid) ois.readObject();
					
			ois.close();
			mainFrame.add(gmgrd,BorderLayout.CENTER);
			gmgrd.repaint();
			updateVars();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		//
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		enableButtons();
	}
	
	public void updateVars(){
		movelabel.setText("Moves : \n"+gmgrd.getMoves()+" / "+limit);
		scorelabel.setText("Score : "+gmgrd.getScore());
	}

	class ButtonListener implements ActionListener{
		int s , t;

		public ButtonListener(int i, int T ){
			s = i ;
			t = T ;
			size = s ;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			size = s ;
			limit = t ;	
			tempfr.setVisible(false);
			gmgrd = new GameGrid(size);
			gmgrd.setSize(limit);
			makeWindow();
			updateVars();

		}	
	}

	class SizeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			gmgrd.clearAll();
			mainFrame.dispose();
			tempfr.setVisible(true);
			enableButtons();
		}		
	}

	class NGListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			fr.dispose();
			enableButtons();
			gmgrd.clearAll();
			gmgrd.generateGrid(size);
			updateVars();
		}
	}

	class QuitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

	class ColorListener implements ActionListener{
		Color c ;
		public ColorListener(Color co){
			c = co ;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(c != gmgrd.getCurrentColour()){
				if(gmgrd.getMoves() <=limit){
					gmgrd.updateMoves();
					gmgrd.indexGrid(c);
					updateVars();
				}
				checkGameStatus();
			}
		}
	}

	class AboutListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFrame fr2 = new JFrame("About This Game");

			JLabel lbl4 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/header.png")));
			JLabel lbl1 = new JLabel("This game is inspired after Flood It, found on iGoogle.");
			JLabel lbl2 = new JLabel("Coded by Dionisio Perez-Mavrogenis.");
			JLabel lbl3 = new JLabel("Contact me at http://diosplace.zzl.org .");		

			GridBagLayout grb = new GridBagLayout();
			GridBagConstraints c45 = new GridBagConstraints();

			fr2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			fr2.setLayout(grb);

			c45.anchor = GridBagConstraints.CENTER;

			c45.gridx = 1 ;
			c45.gridy = 0 ;

			fr2.add(lbl4, c45);


			c45.gridy = 1 ;


			fr2.add(lbl1,c45);

			c45.gridy  = 2;
			fr2.add(lbl2,c45);

			c45.gridy  = 3;
			fr2.add(lbl3,c45);

			fr2.pack();
			fr2.setSize(400,250);
			fr2.setVisible(true);
		}
	}

	class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {

			JOptionPane jop = new JOptionPane();
			jop.setWantsInput(true);		

			saveName  = JOptionPane.showInputDialog(mainFrame,"Save game as...", "Save Your Game!",1);

			if(!saveName.equals("")){
				SaveGame();
			}else{
				JOptionPane.showMessageDialog(mainFrame,"Save name can not be empty!","Error Saving",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class LoadListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			jfc.setFileFilter(filter);
			int returnVal = jfc.showOpenDialog(mainFrame);

			if(returnVal == JFileChooser.APPROVE_OPTION){
				LoadGame(jfc.getSelectedFile());	
			}
			else{
				JOptionPane.showMessageDialog(mainFrame,"Error in loading Save File!","Loading Error!",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
