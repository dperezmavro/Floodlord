import java.awt.Color;
import java.io.Serializable;

public class Cell implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Position up, down, left, right;
	private Color gridColor ;
	private int x, y ;
	private Position position ;

	public Cell(Color col, int X , int Y){
		position = new Position(X,Y);
		x = X ;
		y = Y;
		gridColor = col ;
	}

	public void setColor(Color col){
		gridColor = col ;
	}

	public Position getPosition(){
		return position ;
	}

	public int getX(){
		return x ;
	}

	public int getY(){
		return y; 
	}

	public Color getColor(){
		return gridColor ;
	}

	public void setNeighboors(int s ){
//check if index is out of bounds/ s is puzzle size !
		if(x-1 < 0 || y-1 <0 || x+1 > s-1 || y+1 > s-1 ){	


			up = new Position(x,y-1);
			down = new Position(x,y+1);
			left = new Position(x-1,y);
			right = new Position(x+1,y);


			if (x-1<0){
				left = null ;
			}else if(x+1 >s-1){
				right = null ;
			}

			if(y-1 <0){
				up = null ;
			}else if(y+1>s -1 ){
				down = null ;
			}	
			
		}else{

			up = new Position(x,y-1);
			down = new Position(x,y+1);
			left = new Position(x-1,y);
			right = new Position(x+1,y);
		}

	}

	public Position getUp(){
		return up ;
	}

	public Position getDown(){
		return down ;
	}

	public Position getRight(){
		return right ;
	}

	public Position getLeft(){
		return left ;
	}
}