package model.sudokuSolver.solverFunction;

import java.util.ArrayList;
import java.util.List;

import enums.StaticGameDim;

public abstract class DancingLinks {
	private final static int GRIDSIZE = StaticGameDim.GRIDSIZE;

	/**
	 * @param coverProblem that should get converted to Dancing Links
	 * @return header Node of the Dancing Links
	 */
	public static ColumnNode createDancingLinks(int[][] coverProblem) {
		final int numberOfColumns = coverProblem[0].length;
		ColumnNode headerNode = new ColumnNode("header");
		List<ColumnNode> columnNodes = new ArrayList<>();

		for (int i = 0; i < numberOfColumns; i++) {
			ColumnNode n = new ColumnNode(i + "");
			columnNodes.add(n);
			headerNode = (ColumnNode) headerNode.linkRight(n);
		}

		headerNode = headerNode.right.column;

		for (int[] row : coverProblem) {
			DancingNode prev = null;

			for (int j = 0; j < numberOfColumns; j++) {
				if (row[j] == 1) {
					ColumnNode col = columnNodes.get(j);
					DancingNode newNode = new DancingNode(col);

					if (prev == null)
						prev = newNode;

					col.top.linkDown(newNode);
					prev = prev.linkRight(newNode);
					col.size++;
				}
			}
		}

		headerNode.size = numberOfColumns;
		return headerNode;
	}

	/**
	 * @param Resultset in form of Dancing Links
	 * @return Solved sudoku
	 */
	public static int[][] convertDancingLinksToGrid(List<DancingNode> result) {
		int[][] grid = new int[GRIDSIZE][GRIDSIZE];

		for (DancingNode n : result) {
			DancingNode rcNode = n;
			int min = Integer.parseInt(rcNode.column.name);

			for (DancingNode tmp = n.right; tmp != n; tmp = tmp.right) {
				int val = Integer.parseInt(tmp.column.name);

				if (val < min) {
					min = val;
					rcNode = tmp;
				}
			}

			// read col and row
			int ans1 = Integer.parseInt(rcNode.column.name);
			int ans2 = Integer.parseInt(rcNode.right.column.name);
			int r = ans1 / GRIDSIZE;
			int c = ans1 % GRIDSIZE;
			// read number
			int num = (ans2 % GRIDSIZE) + 1;
			// fill in grid
			grid[r][c] = num;
		}

		return grid;
	}
}
