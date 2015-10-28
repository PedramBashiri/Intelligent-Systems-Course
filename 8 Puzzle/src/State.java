import java.util.Stack;

public class State {
	private final int N;
	private final int[][] Tiles;

	public State(int[][] positions) {
		N = positions.length;
		Tiles = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				Tiles[i][j] = positions[i][j];
			}
		}
	}

	// is this board the goal board?
	public boolean isGoalState(int[][] goalState) {

		return compare(this.Tiles, goalState);
	}

	private boolean compare(int[][] first, int[][] second) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (first[i][j] != second[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	// sum of Manhattan distances between tiles and goal State
	public int manhattan(int[][] finalState) {
		int sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int value = Tiles[i][j];
				if (value != 0 && value != finalState[i][j]) {
					int distance = Math.abs(i
							- getFinalPosition(value, finalState)[0])
							+ Math.abs(j
									- getFinalPosition(value, finalState)[1]);
					sum += distance;
				}
			}
		}
		return sum;
	}

	public int[] getFinalPosition(int valueToSearch, int[][] finalState) {

		int[] result = new int[2];

		for (int i = 0; i < finalState.length; i++)
			for (int j = 0; j < finalState[0].length; j++) {
				if (finalState[i][j] == valueToSearch) {
					result[0] = i;
					result[1] = j;
					return result;
				}
			}
		return result;

	}

	// Returns a new Board result of swapping to adjacent tiles
	public State twin() {
		State board = new State(Tiles);

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N - 1; j++) {
				if (Tiles[i][j] != 0 && Tiles[i][j + 1] != 0) {
					board.swap(i, j, i, j + 1);
					return board;
				}
			}
		}

		return board;
	}

	private boolean swap(int i, int j, int it, int jt) {
		if (it < 0 || it >= N || jt < 0 || jt >= N) {
			return false;
		}
		int temp = Tiles[i][j];
		Tiles[i][j] = Tiles[it][jt];
		Tiles[it][jt] = temp;
		return true;
	}

	// returns neighboring states
	public Iterable<State> neighbors() {
		int i0 = 0, j0 = 0;
		boolean found = false;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (Tiles[i][j] == 0) {
					i0 = i;
					j0 = j;
					found = true;
					break;
				}
			}
			if (found) {
				break;
			}
		}

		Stack<State> boards = new Stack<State>();
		State board = new State(Tiles);
		boolean isNeighbor = board.swap(i0, j0, i0 - 1, j0);
		if (isNeighbor) {
			boards.push(board);
		}
		board = new State(Tiles);
		isNeighbor = board.swap(i0, j0, i0, j0 - 1);
		if (isNeighbor) {
			boards.push(board);
		}
		board = new State(Tiles);
		isNeighbor = board.swap(i0, j0, i0 + 1, j0);
		if (isNeighbor) {
			boards.push(board);
		}
		board = new State(Tiles);
		isNeighbor = board.swap(i0, j0, i0, j0 + 1);
		if (isNeighbor) {
			boards.push(board);
		}

		return boards;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", Tiles[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	@Override
	public boolean equals(Object x) {
		if (x == this)
			return true;
		if (x == null)
			return false;
		if (x.getClass() != this.getClass())
			return false;

		State that = (State) x;
		return this.N == that.N && compare(this.Tiles, that.Tiles);
	}
}