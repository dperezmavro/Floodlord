import java.io.Serializable;

public class Position implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int x ,y;
	
	public Position(int X , int Y ){
		x = X ;
		y = Y ;
		
	}
	
	int getX(){
		return x ;
	}
	
	int getY(){
		return y; 
	}
}
