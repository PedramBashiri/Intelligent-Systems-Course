import java.util.Random;

public class Board {

	private int N;
	private Tile[][] tiles;
	private Queen[] queens;

	// If number of queens(dimension) is not specified, the default value will
	// be 8
	public Board() {
		N = 8;
		tiles = new Tile[N][N];
		queens = new Queen[N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				tiles[j][i] = new Tile();
			}
			queens[i] = new Queen(N);
		}
	}

	public Board(int squares) {
		N = squares;
		tiles = new Tile[N][N];
		queens = new Queen[N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				tiles[j][i] = new Tile();
			}
			queens[i] = new Queen(N);
		}
	}

	public boolean isSolution() {
		boolean flag = false;

		for (int i = 0; i < N; i++) {
			if (queens[i].getInPosition()) {
				flag = true;
			} else {
				flag = false;
				break;
			}
		}
		return flag;
	}

	// place a queen at a specific x and y
	public void placeQueen(int x, int y) {
		tiles[x][y].placeQueen();
		queens[x].placeQueen(x, y);
		setAttacks(x, y);
	}

	// Remove a queen at a specific x and y
	public void removeQueen(int x, int y) {
		tiles[x][y].removeQueen();
		queens[x].removeQueen(x, y);
		liftAttacks(x, y);

		// Removing a queen may have undesired effects on some tiles
		// We put all the queens to set the attacks once again
		for (int i = 0; i < N; i++) {
			if (queens[i].getInPosition()) {
				placeQueen(queens[i].getRow(), queens[i].getColumn());
			}
		}
	}

	// Set the "attacked" variable of all under-attack tiles
	public void setAttacks(int x, int y) {
		int tempx;
		int tempy;

		// Tiles on the Same Row
		for (int i = 0; i < N; i++) {
			tiles[i][y].setAttacked();
		}

		// Tiles on the same column
		for (int j = 0; j < N; j++) {
			tiles[x][j].setAttacked();
		}

		// Tiles diagonally under attack
		tempx = x - 1;
		tempy = y - 1;

		while (tempx >= 0 && tempy >= 0) {
			tiles[tempx][tempy].setAttacked();
			tempx--;
			tempy--;
		}

		tempx = x + 1;
		tempy = y - 1;

		while (tempx < N && tempy >= 0) {
			tiles[tempx][tempy].setAttacked();
			tempx++;
			tempy--;
		}

		tempx = x - 1;
		tempy = y + 1;

		while (tempx >= 0 && tempy < N) {
			tiles[tempx][tempy].setAttacked();
			tempx--;
			tempy++;
		}

		tempx = x + 1;
		tempy = y + 1;

		while (tempx < N && tempy < N) {
			tiles[tempx][tempy].setAttacked();
			tempx++;
			tempy++;
		}
	}

	// Relieve attack from tiles
	public void liftAttacks(int x, int y) {
		int tempx;
		int tempy;

		// Tiles on the Same Row
		for (int i = 0; i < N; i++) {
			tiles[i][y].liftAttack();
		}

		// Tiles on the same column
		for (int j = 0; j < N; j++) {
			tiles[x][j].liftAttack();
		}

		// Tiles diagonally under attack
		tempx = x - 1;
		tempy = y - 1;

		while (tempx >= 0 && tempy >= 0) {
			tiles[tempx][tempy].liftAttack();
			tempx--;
			tempy--;
		}

		tempx = x + 1;
		tempy = y - 1;

		while (tempx < N && tempy >= 0) {
			tiles[tempx][tempy].liftAttack();
			tempx++;
			tempy--;
		}

		tempx = x - 1;
		tempy = y + 1;

		while (tempx >= 0 && tempy < N) {
			tiles[tempx][tempy].liftAttack();
			tempx--;
			tempy++;
		}

		tempx = x + 1;
		tempy = y + 1;

		while (tempx < N && tempy < N) {
			tiles[tempx][tempy].liftAttack();
			tempx++;
			tempy++;
		}
	}


	// Heuristic function: the number of conflicting queens
	public int heuristic(int[] board) {
		int conflicts = 0;
		int index = 0;

		for (int i = 0; i < N; i++) {
			index = board[i];
			for (int j = 0; j < N; j++) {
				if (i != j) {
					placeQueen(j, board[j]);
					if (tiles[i][index].getAttacked()) {
						conflicts++;
					}
					removeQueen(j, board[j]);
				}
			}
		}
		return conflicts;
	}

	// Creates a board from a genetically created array
	public void createBoard(int[] array) {
		for (int i = 0; i < N; i++) {
			placeQueen(i, array[i]);
		}
	}

	// Hill Climbing algorithm (Steepest Ascent version) to solve the problem
	public long[] randomRestartHillClimbing() {
		Random rand = new Random();
		boolean solved = false;

		long iterations = 0;
		long restartNumbers = 0;

		int row = 0;
		int index = 0;

		int[] parent = new int[(N + 1)];
		int[][] children = new int[(N * 2)][(N + 1)];

		int bestChildIndex = -1;
		int bestChildHeuristic = 0;

		// Initialize the parent
		for (int i = 0; i < N; i++) {
			parent[i] = rand.nextInt(N);
		}
		parent[N] = heuristic(parent);

		while (!(solved)) {
			// Set the heuristic of the best Child to the parent initially
			bestChildHeuristic = parent[N];

			bestChildIndex = -1;

			// Initialize all the children to be the same as the parent
			for (int j = 0; j < (N * 2); j++) {
				for (int k = 0; k < N; k++) {
					children[j][k] = parent[k];
				}
				children[j][N] = heuristic(children[j]);
			}

			for (int l = 0; l < (N * 2); l++) {
				if (parent[N] == 0) {
					solved = true;
					break;
				}

				// Find all children that are one more than their parent
				if (l < N) {
					children[l][row] += 1;
					if (children[l][row] >= N) {
						children[l][row] = 0;
					}
				}

				// Find all children that are one less than their parent
				else {
					children[l][row] -= 1;
					if (children[l][row] <= 0) {
						children[l][row] = N - 1;
					}
				}

				// Set the child's heuristic
				children[l][N] = heuristic(children[l]);

				// Pick the best child
				if (children[l][N] < bestChildHeuristic) {
					bestChildHeuristic = children[l][N];
					bestChildIndex = l;
				}

				// Reset the row if it has reached the end of the board
				// increment otherwise
				if (row >= N) {
					row = 0;
				} else {
					row++;
				}
			}

			if (bestChildIndex != -1) {
				// Now we have a better hill to climb to
				for (int m = 0; m <= N; m++) {
					parent[m] = children[bestChildIndex][m];
				}
			} else {
				// We could'nt find a better hill to climb
				// So let's start over
				// Initialize the parent
				for (int i = 0; i < N; i++) {
					parent[i] = rand.nextInt(N);
				}
				parent[N] = heuristic(parent);

				// increment the number of times algorithm restarts
				restartNumbers++;

			}

			// Is the new hill a solution?
			if (parent[N] == 0) {
				solved = true;
				index = bestChildIndex;
			}

			// reset the row to start from scratch
			row = 0;

			// One iteration passed
			iterations++;
		}

		// When solution is found
		if (children[index][N] == 0) {
			createBoard(children[index]);
		} else {
			createBoard(parent);
		}

		long[] result = { iterations, restartNumbers };

		return result;
	}

	@Override
	public String toString() {
		String print = new String("\n" + N + " Queen Problem Board Layout:\n\n");

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (tiles[j][i].getOccupied())
					print = print + "Q ";
				else if (tiles[j][i].getAttacked())
					print = print + "1 ";
				else
					print = print + "0 ";
			}

			print = print + "\n";
		}
		return print;
	}

}