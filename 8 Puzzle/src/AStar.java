import java.util.PriorityQueue;
import java.util.Stack;

public class AStar {

	private boolean isSolvable;
	public int[][] finalState;
	private int numberOfMoves;
	private final Stack<State> states;

	private class SearchNode implements Comparable<SearchNode> {
		private State state;
		private int moves;
		private SearchNode previous;
		private int cachedPriority = -1;

		SearchNode(State state, int moves, SearchNode previous) {
			this.state = state;
			this.moves = moves;
			this.previous = previous;
		}

		private int priority() {
			if (cachedPriority == -1) {
				cachedPriority = moves + state.manhattan(finalState);
			}
			return cachedPriority;
		}

		@Override
		public int compareTo(SearchNode that) {
			if (this.priority() < that.priority()) {
				return -1;
			}
			if (this.priority() > that.priority()) {
				return +1;
			}
			return 0;
		}
	}

	public boolean isSolvable() {
		return isSolvable;
	}

	public int numberOfMoves() {
		if (isSolvable) {
			return states.size() - 1;
		} else {
			return -1;
		}
	}

	// Sequence of states from initial to goal state
	public Iterable<State> solution() {
		if (isSolvable) {
			return states;
		} else {
			return null;
		}
	}

	// Apply A* algorithm
	public int solve(State initial) {

		if (initial.isGoalState(finalState)) {
			isSolvable = true;
			this.states.push(initial);
			return 0;
		}
		if (initial.twin().isGoalState(finalState)) {
			isSolvable = false;
			return 0;
		}

		PriorityQueue<SearchNode> queue = new PriorityQueue<SearchNode>();
		PriorityQueue<SearchNode> twinQueue = new PriorityQueue<SearchNode>();

		numberOfMoves = 0;

		State board = initial;
		State boardTwin = initial.twin();

		SearchNode node = new SearchNode(board, 0, null);
		SearchNode nodeTwin = new SearchNode(boardTwin, 0, null);

		queue.add(node);
		twinQueue.add(nodeTwin);

		while (numberOfMoves < 100) {
			node = queue.remove();
			nodeTwin = twinQueue.remove();

			board = node.state;
			boardTwin = nodeTwin.state;

			// Just one of a board and its twin is solvable
			if (boardTwin.isGoalState(finalState)) {
				isSolvable = false;
				return queue.size();
			}
			if (board.isGoalState(finalState)) {
				isSolvable = true;
				this.states.push(board);
				while (node.previous != null) {
					node = node.previous;
					this.states.push(node.state);
				}
				return queue.size();
			}

			node.moves++;
			nodeTwin.moves++;

			Iterable<State> neighbors = board.neighbors();
			for (State neighbor : neighbors) {
				if (node.previous != null
						&& neighbor.equals(node.previous.state)) {
					continue;
				}
				SearchNode newNode = new SearchNode(neighbor, node.moves, node);
				queue.add(newNode);
			}
			Iterable<State> neighborsTwin = boardTwin.neighbors();
			for (State neighbor : neighborsTwin) {
				if (nodeTwin.previous != null
						&& neighbor.equals(nodeTwin.previous.state)) {
					continue;
				}
				SearchNode newNode = new SearchNode(neighbor, nodeTwin.moves,
						nodeTwin);
				twinQueue.add(newNode);
			}
		}
		return queue.size();

	}

	public AStar() {
		// TODO Auto-generated constructor stub
		states = new Stack<State>();
	}

}