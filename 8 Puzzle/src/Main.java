import java.util.Collections;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		AStar Solver = new AStar();

		int count = 0;
		int n = 3;

		// Get Initial State form input
		int[][] initialState = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				initialState[i][j] = Integer.parseInt(args[count]);
				count++;
			}
		}

		// Initialize the board
		State initial = new State(initialState);

		// Get Final State from input
		Solver.finalState = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Solver.finalState[i][j] = Integer.parseInt(args[count]);
				count++;
			}
		}

		// solve the puzzle
		int nodesGenerated = Solver.solve(initial);

		// print solution to standard output
		if (!Solver.isSolvable())
			System.out.println("Unsolvable");
		else {
			System.out.println("Number of nodes expanded = "
					+ Solver.numberOfMoves() + "\n");

			System.out.println("Number of nodes generated = " + nodesGenerated
					+ "\n");

			Iterable<State> result = Solver.solution();
			List<State> resultList = (List<State>) result;

			if (result != null) {
				Collections.reverse(resultList);

				int counter = 0;
				System.out.println("Initial State:");

				for (State board : resultList) {
					if (counter == 0)
						counter++;
					else if (counter == resultList.size() - 1)
						System.out.println("Goal State" + ":");
					else
						System.out.println("State " + ++counter + ":");
					System.out.println(board);
				}
			}
		}
	}
}
