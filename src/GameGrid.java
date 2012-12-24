import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GameGrid extends JPanel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int size , moves = 0, score = 0, limit ;
	
	private ArrayList <Cell> visited = new ArrayList<Cell>();
	private ArrayList <Cell> temp = new ArrayList<Cell>();
	private ArrayList <Cell> checked = new ArrayList<Cell>();
	private ArrayList <Cell> notvisited = new ArrayList<Cell>();
	private Color green1 = new Color(19,109,35) ;
	private Color orange1 = new Color(238,150,27);
	
	
	private Color[] colors = {Color.red, Color.cyan, Color.yellow, green1 , Color.blue, orange1} ;
	private Random generator = new Random();
	private Color currentColor ;
	
	private Cell[][] colorGrid;

	
	/*
	 * 
	 * 
	 * 
	 */
	
	public GameGrid(int i){
		size = i ;
		colorGrid= new Cell[size][size];
		//this.setSize(size*20, size*20);
	}
	
	public void paintComponent(Graphics g){		
		
		//repaint the whole grid black
		for(int i = 0 ; i < this.getHeight() ; i++){
			for(int j = 0 ; j < this.getWidth() ; j++){
				g.setColor(Color.black);
				g.fillRect(i, j, 1, 1);
			}
		}
		
		//paint the grid to be played upon

		for(Cell a : notvisited){
			g.setColor(a.getColor());
			g.fillRect(a.getX()*20, a.getY()*20, 20, 20) ;
		}

		for(Cell a : visited){
			g.setColor(currentColor);
			g.fillRect(a.getX()*20, a.getY()*20, 20, 20) ;
		}

		System.out.println("done painting");
	}

	//make the grid from the begininng 
	public void generateGrid(int s){
		size = s ;

		//reset the lists
		visited.clear();
		notvisited.clear();

		for(int i =0 ; i < size ; i++){

			for(int j = 0 ; j < size ; j++){

				Cell c = new Cell(colors[generator.nextInt(6)], j , i) ;

				colorGrid[i][j] = c ;
				colorGrid[i][j].setNeighboors(size);

				//this could probably be done better !
				if(i==0 && j==0){
					visited.add(c);
					System.out.println("Added to visited");
				}
				else{
					notvisited.add(c);
				}			
			}
		}
		currentColor = visited.get(0).getColor();

		indexGrid(currentColor);

		this.repaint();
	}

	//probably complete !!
	public void indexGrid(Color co){

		currentColor = co ;

		for(Cell a : visited){
			a.setColor(currentColor);
		}

		for(Cell a : visited){
			getNeighbors(a);
		}

		joinLists();
		checked.clear();
					
		this.repaint();
	}
	
	public void setSize(int n ){
		limit = n ;
	}
	
	// this makes sure that the arraylist of visited is always up to date !
	public void joinLists(){
				
		for(Cell a : temp){
			if(!visited.contains(a)){
				visited.add(a);
				score+= (30 + (limit - moves)*20) ;
			}
		}
	}
	
	//HELL LIES INSIDE METHOD !
	public void getNeighbors(Cell c){

		if(c.getDown() != null){
			
			if(colorGrid[c.getDown().getY()][c.getDown().getX()].getColor().getRed() == currentColor.getRed()
					&& colorGrid[c.getDown().getY()][c.getDown().getX()].getColor().getBlue() == currentColor.getBlue()
					&& colorGrid[c.getDown().getY()][c.getDown().getX()].getColor().getGreen() == currentColor.getGreen()){

				if(!(temp.contains (colorGrid[c.getDown().getY()][c.getDown().getX()]))){

					temp.add(colorGrid[c.getDown().getY()][c.getDown().getX()]);
				}

				if(!(checked.contains (colorGrid[c.getDown().getY()][c.getDown().getX()]))){
					checked.add(colorGrid[c.getDown().getY()][c.getDown().getX()] );
					getNeighbors(colorGrid[c.getDown().getY()][c.getDown().getX()]);
				}
			}
		}


		if(c.getLeft() != null){

			if(colorGrid[c.getLeft().getY()][c.getLeft().getX()].getColor().getRed() == currentColor.getRed()
					&& colorGrid[c.getLeft().getY()][c.getLeft().getX()].getColor().getGreen() == currentColor.getGreen()
					&& colorGrid[c.getLeft().getY()][c.getLeft().getX()].getColor().getBlue() == currentColor.getBlue()){
				
				if(!(temp.contains(colorGrid[c.getLeft().getY()][c.getLeft().getX()]))){
					temp.add(colorGrid[c.getLeft().getY()][c.getLeft().getX()]);
				}

				if(!(checked.contains (colorGrid[c.getLeft().getY()][c.getLeft().getX()]))){
					checked.add(colorGrid[c.getLeft().getY()][c.getLeft().getX()] );
					getNeighbors(colorGrid[c.getLeft().getY()][c.getLeft().getX()]);
				}
			}
		}

		if(c.getUp() != null){
			
			if(colorGrid[c.getUp().getY()][c.getUp().getX()].getColor().getRed() == currentColor.getRed()
					&& colorGrid[c.getUp().getY()][c.getUp().getX()].getColor().getGreen() == currentColor.getGreen()
					&& colorGrid[c.getUp().getY()][c.getUp().getX()].getColor().getBlue() == currentColor.getBlue()){


				if(!(temp.contains(colorGrid[c.getUp().getY()][c.getUp().getX()]))){
					temp.add(colorGrid[c.getUp().getY()][c.getUp().getX()]);
				}

				if(!(checked.contains (colorGrid[c.getUp().getY()][c.getUp().getX()]))){
					checked.add(colorGrid[c.getUp().getY()][c.getUp().getX()] );
					getNeighbors(colorGrid[c.getUp().getY()][c.getUp().getX()]);
				}
			}
		}

		if(c.getRight() != null){

			if(colorGrid[c.getRight().getY()][c.getRight().getX()].getColor().getRed() == currentColor.getRed()
					&& colorGrid[c.getRight().getY()][c.getRight().getX()].getColor().getGreen() == currentColor.getGreen()
					&& colorGrid[c.getRight().getY()][c.getRight().getX()].getColor().getBlue() == currentColor.getBlue()){

				if(!(temp.contains(colorGrid[c.getRight().getY()][c.getRight().getX()]))){
					temp.add(colorGrid[c.getRight().getY()][c.getRight().getX()]);
				}

				if(!(checked.contains (colorGrid[c.getRight().getY()][c.getRight().getX()]))){
					checked.add(colorGrid[c.getRight().getY()][c.getRight().getX()] );
					getNeighbors(colorGrid[c.getRight().getY()][c.getRight().getX()]);
				}
			}
		}
	}
	
	//method to clear everything for a new game !
	public void clearAll(){
		visited.clear();
		notvisited.clear();
		temp.clear();
		checked.clear();
		score = 0 ;
		moves = 0 ;
	}

	//get the starting colour !
	public Color getInitialColor(){
		return visited.get(0).getColor();
	}

	//get the score here
	public int getScore(){
		return score ;
	}

	//see if the grid is all a single colour 
	public boolean getStatus(){
		
		return visited.size()==size*size ;
	}

	public int getMoves(){
		return moves ;
	}
	
	public Color getCurrentColour(){
		
		return currentColor ;
		
	}
	
	
	public void updateMoves(){
		moves++ ;
	}
}