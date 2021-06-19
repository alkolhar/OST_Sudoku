package model.sudokuSolver.solverFunction;

import java.util.LinkedList;
import java.util.List;

public class AlgorithmX {
	private ColumnNode header;
	private List<DancingNode> answer;
	private ColumnNode column;

	public int solutions;
	public List<DancingNode> result;

	public AlgorithmX(ColumnNode header) {
		this.header = header;
	}

	public void solve() {
		answer = new LinkedList<DancingNode>();
		solutions = 0;
		process();
	}

	private void process() {
		if (header.right == header) {
			// Matrix empty => found solution
			solutions++;

			if (solutions == 1) {
				result = new LinkedList<>(answer);
			}

		} else if (solutions >= 2) {
			// more than one solution => exit
			return;
		} else {
			// choose the column with the least possible rows
			column = selectColumnNodeSHeuristic();
			column.cover();

			for (DancingNode r = column.bottom; r != column; r = r.bottom) {
				// add a node to the solution set
				answer.add(r);

				// cover all column with the same links
				for (DancingNode j = r.right; j != r; j = j.right) {
					j.column.cover();
				}

				// recursive call
				process();

				// remove column from the solution set
				r = answer.remove(answer.size() - 1);
				column = r.column;

				// uncover possible links
				for (DancingNode j = r.left; j != r; j = j.left) {
					j.column.uncover();
				}
			}
			column.uncover();
		}
	}

	private ColumnNode selectColumnNodeSHeuristic() {
		int min = Integer.MAX_VALUE;
		ColumnNode ret = null;
		for (ColumnNode c = (ColumnNode) header.right; c != header; c = (ColumnNode) c.right) {
			if (c.size < min) {
				min = c.size;
				ret = c;
			}
		}
		return ret;
	}
}
