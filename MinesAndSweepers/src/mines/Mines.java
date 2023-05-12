package mines;

import java.util.Random;
//import java.util.Scanner;


public class Mines {
	
	public class Place {
		//this class represents a single place inside the game's board
		private int i,j;
		private boolean open;
		private boolean flag;
		private int val;//will store number of mines in neighbors(num>=0) or if it is a mine(-1)
		
		public Place(int idx,int jdx){
			//constructor
			i=idx;
			j=jdx;
			open=false;
			flag=false;
			val=0;
			
		}
		protected int getI() {return i;}//returns i of place
		protected int getJ() {return j;}//returns j of place
		protected boolean checkOpen() {return open;}//returns wether or not this place is open
		protected boolean checkFlag() {return flag;}//checks if theres a flag at this place
		protected int getVal(){ return val;}//returns value of place (mine/number of mines in neighbors) 

		protected boolean setOpen() {
			//if place is already opened, or theres a a flag, returns false(cannot set Open)
			//if place is not opened, opens it and returns true
			open=true;
			if(val==-1) {
				return false;
			}
			else {
				return true;
			}
		}
		
		protected boolean togFlag(){
			//if place is open returns false(cannot toggle flag)
			//if place is closed, toggles flag and returns true
			if(!open) {
				flag=!flag;
				return true;
			}
			else {
				return false;
			}
		}
		
		protected void setVal(int newval) {
			//sets the Place's value to newval
			val=newval;
		}
		
		protected String rep() {
			//returns the representation of place(what will appear in the representation of the matrix)
			if(open) {
				if(val==-1) 
					return ".";
				else if(val==0)
					return " ";
				else
					return Integer.toString(val);
			}
			else {
				if(flag)
					return "F";
				else
					return ".";
			}
		}
		
		protected Place[] neighbours() {
			//returns an array with the Place's neighbors
			Place[] neighbors= new Place[8];
			int count=0;
			
			//neighbors above
			if(i-1>=0) {
				if(j-1>=0)
					neighbors[count++]=matrix[i-1][j-1];
				neighbors[count++]=matrix[i-1][j];
				if(j+1<width)
					neighbors[count++]=matrix[i-1][j+1];
			}
			
			//neighbors on the sides
			if(j-1>=0)
				neighbors[count++]=matrix[i][j-1];
			if(j+1<width)
				neighbors[count++]=matrix[i][j+1];
			
			//neighbors below
			if(i+1<height) {
				if(j-1>=0)
					neighbors[count++]=matrix[i+1][j-1];
				neighbors[count++]=matrix[i+1][j];
				if(j+1<width)
					neighbors[count++]=matrix[i+1][j+1];
			}
			return neighbors;
		}
	
	}
	
	private Place[][] matrix;
	private int height,width;
	private boolean showAll;
	//private int openCount;
	
	public Mines(int height, int width, int numMines) {
		//constructor for class Mines 
		this.height=height;
		this.width=width;
		showAll=false;
		matrix =new Place[height][width];
		//initiates board with empty places
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				matrix[i][j]=new Place(i,j);
			}
		}
		Random rand= new Random(System.currentTimeMillis());
		int randx,randy;
		
		int i=0;
		//generates random Places to place mines in
		while(i<numMines) {
			randx= rand.nextInt(width);
			randy= rand.nextInt(height);
			if(addMine(randx,randy))				
				i++;	
		}
	}
	
	public boolean addMine(int i, int j) {
		//adds a mine to the board
		//returns true if it was added
		//false if a mine already exists there
		if(matrix[i][j].getVal()==-1)
			return false;
		matrix[i][j].setVal(-1);
		//updating near-mines number for neighbors
		Place[] neighbors=matrix[i][j].neighbours();
		for(int k=0;k<8;k++) {
			if(neighbors[k]!=null) {
				if(neighbors[k].getVal()!=-1)
					neighbors[k].setVal(neighbors[k].getVal()+1);
			}
		}
		return true;
	}
	
	
	public boolean open(int i, int j) {//continue here
		//if the requested Place(the main request meaning the first indexes this function is called upon) has a flag, it does not open
		//otherwise, if it has a mine, it opens and returns false
		//if it has zero mines in its neighbors, it opens them, and all other neighbors with zero near-mines recursively and returns true
		if(matrix[i][j].checkFlag() || matrix[i][j].checkOpen()) //if has flag or already open, doesnt open again
			return true;
		if(matrix[i][j].getVal()!=0)
			return matrix[i][j].setOpen();//opens 
		else if(matrix[i][j].getVal()==0) {
			recOpen(i,j);
		}
			
		return true;
	}
	
	protected void recOpen(int i,int j) {
		// a helping recursive function for open
		//System.out.println("current cel:"+i+","+j);
		matrix[i][j].setOpen();
		if(matrix[i][j].getVal()==0) {
			Place[] neighbors=matrix[i][j].neighbours();
		
			for(int k=0;k<8;k++) {
				if(neighbors[k]!=null && !neighbors[k].checkOpen()) {
					recOpen(neighbors[k].getI(),neighbors[k].getJ());
				}
				if(neighbors[k]==null) break;
			}
		}
		return;
	}
	
	public void toggleFlag(int x, int y) {
		//toggles the flag of place i,j
		matrix[x][y].togFlag();
	}
	
	public boolean isDone() {
		//returns true if all non mines are open
		//false otherwise
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				if(!matrix[i][j].checkOpen() && matrix[i][j].getVal()!=-1)
					return false;
			}
		}
		return true;
		
	} 
	public String get(int i, int j) {
		//returns string representation of a certain place i,j
		if(!showAll)
			return matrix[i][j].rep();
		else {
			switch (matrix[i][j].getVal()) {
				case -1://mine
					return "X";
				case 0://no near-mines
					return " ";
				default:
					return Integer.toString(matrix[i][j].getVal()); 
			}
		}
	} 
	public void setShowAll(boolean showAll) {
		//sets variable showAll(indicates if to show all places as open) 
		this.showAll=showAll;
	}
	
	public String toString() {
		//returns the board of the game as a string with consideration to showAll value
		StringBuilder board=new StringBuilder();
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				board.append(get(i,j));
			}
			board.append("\n");
		}
		return board.toString();
	} 

}
