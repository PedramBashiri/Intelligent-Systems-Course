import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		
		
		Board board;
		long[] result = new long[2];
		int queensNumber; //Number of queens entered by the user which is also the dimension of the board 


		BufferedReader reader;
		reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("\nWe are going to solve the N-Queens Problem");
		System.out.print("\n\nWhat's the number of queens in your problem? ");

		try {
			queensNumber = Integer.valueOf(reader.readLine()).intValue();
		} catch (NumberFormatException e) {
			System.err.println("Caught NumberFormatException: "
					+ e.getMessage());
			return;
		}

		if (queensNumber < 4) {
			System.out.println("\nIt is impossible to to put " + queensNumber
					+ " queens into a " + queensNumber + "*" + queensNumber
					+ " Board!");
			return;
		}
		System.out.println("\nLet's Start Random-Restart Hill Climbing...\n");


		board = new Board(queensNumber);

		long start = System.currentTimeMillis();
		
		result = board.randomRestartHillClimbing();
		
		long stop = System.currentTimeMillis(); 
		
		

		
		System.out.println(board + "\nNumber of Steps of Climbing: " + result[0] + "\n");
		 System.out.println("\nFound in " + ((double)(stop-start))/1000 + "s.");
		System.out.println("\nNumber of Random Restarts: " + result[1] + "\n");
		

	}
}
