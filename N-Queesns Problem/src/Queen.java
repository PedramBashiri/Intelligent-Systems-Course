
public class Queen {


	private int row;
	private int column;
	private boolean inPosition;

	public Queen() {
		row = 0;
		column = 0;
		inPosition = false;

	}

	public Queen(int n) {
		row = 0;
		column = 0;
		inPosition = false;

	}

	public int getRow() {
		return row;
	}


	public int getColumn() {
		return column;
	}


	public boolean getInPosition() {
		return inPosition;
	}
	
	
	// place a queen at a specific x and y 
	public void placeQueen(int x, int y) {
		row = x;
		column = y;
		inPosition = true;
	}

	// function to remove a queen from a specific x, y
	public void removeQueen(int x, int y) {
		row = 0;
		column = 0;
		inPosition = false;
	}

	@Override
	public String toString() {
		String print = new String("\nQueen\n");

		print = print + "Row: " + row + "\n";
		print = print + "Coloumn: " + column + "\n";
		if (inPosition)
			print = print + "In Position: True\n";
		else
			print = print + "In Position: False\n";

		print = print + "\n";
		return print;
	}
}