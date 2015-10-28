// A class for each piece of the chess board
public class Tile {

	private boolean occupied;
	private boolean attacked; //whether or not the tile is under attack by any queens
	

	public Tile() {
		occupied = false;
		attacked = false;
	}


	public void setAttacked() {
		attacked = true;
	}
	
	public boolean getAttacked() {
		return attacked;
	}

	public void liftAttack() {
		attacked = false;
	}
	
	
	// Place a queen on this tile
	public void placeQueen() {
		occupied = true;
		attacked = true;
	}

	
	public void removeQueen() {
		occupied = false;
		attacked = false;
	}
	
	public boolean getOccupied() {
		return occupied;
	}

	@Override
	public String toString() {

		String toPrint = new String("Square\n\n");

		if (attacked)
			toPrint = toPrint + "Under Attack: True\n";
		else
			toPrint = toPrint + "Under Attack: False\n";

		if (occupied)
			toPrint = toPrint + "Occupied: True\n";
		else
			toPrint = toPrint + "Occupied: False\n";

		toPrint = toPrint + "\n";

		return toPrint;
	}
} 