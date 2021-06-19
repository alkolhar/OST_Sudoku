/**
 * 
 */
package jUnitTest;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author mustafa
 *
 */
public class SolverClassManualTestOhneJUnit {

	// first has singles, second only hidden singles
	static int[][] testBoard2 = { { 0, 0, 4, 7, 2, 0, 9, 0, 0 }, { 0, 3, 9, 0, 0, 8, 0, 0, 5 },
			{ 0, 0, 1, 5, 0, 6, 0, 0, 4 }, { 0, 4, 0, 0, 1, 0, 5, 2, 0 }, { 0, 2, 8, 0, 5, 0, 1, 7, 0 },
			{ 0, 1, 6, 0, 3, 0, 0, 9, 0 }, { 4, 0, 0, 9, 0, 1, 3, 0, 0 }, { 1, 0, 0, 3, 0, 0, 8, 4, 0 },
			{ 0, 0, 7, 0, 8, 5, 6, 0, 0 } };
	static int[][] testBoard = { { 1, 0, 0, 2, 0, 0, 3, 0, 0 }, { 0, 2, 0, 0, 1, 0, 0, 4, 0 },
			{ 0, 0, 3, 0, 0, 5, 0, 0, 6 }, { 7, 0, 0, 6, 0, 0, 5, 0, 0 }, { 0, 5, 0, 0, 8, 0, 0, 7, 0 },
			{ 0, 0, 8, 0, 0, 4, 0, 0, 1 }, { 8, 0, 0, 7, 0, 0, 4, 0, 0 }, { 0, 3, 0, 0, 6, 0, 0, 2, 0 },
			{ 0, 0, 9, 0, 0, 2, 0, 0, 7 } };
	static int[][] solution = { { 6, 5, 4, 7, 2, 3, 9, 8, 1 }, { 2, 3, 9, 1, 4, 8, 7, 6, 5 },
			{ 8, 7, 1, 5, 9, 6, 2, 3, 4 }, { 7, 4, 3, 8, 1, 9, 5, 2, 6 }, { 9, 2, 8, 6, 5, 4, 1, 7, 3 },
			{ 5, 1, 6, 2, 3, 7, 4, 9, 8 }, { 4, 8, 2, 9, 6, 1, 3, 5, 7 }, { 1, 6, 5, 3, 7, 2, 8, 4, 9 },
			{ 3, 9, 7, 4, 8, 5, 6, 1, 2 } };

	@SuppressWarnings("unchecked")
	static ArrayList<Integer>[][] candidates = new ArrayList[9][9];

	static void eliminateAndInsert(int x, int y) {
		eliminateCandidates(x, y);
		insertNakedSingles(x, y);
		insertHiddenSingles(); // bugged
	}

	static void eliminateCandidates(int x, int y) { // works fine
		int rowOffset = (x / 3) * 3;
		int colOffset = (y / 3) * 3;
		for (int i = 0; i < 9; i++)// xy's row
			if (candidates[x][y].contains(testBoard[x][i]))
				candidates[x][y].remove(candidates[x][y].indexOf(testBoard[x][i]));
		// remove numbers from candidate list if they appear in the row
		for (int i = 0; i < 9; i++)// xy's column
			if (candidates[x][y].contains(testBoard[i][y]))
				candidates[x][y].remove(candidates[x][y].indexOf(testBoard[i][y]));
		// remove numbers from candidate list if they appear in the column
		for (int i = 0; i < 3; i++) // xy's box
			for (int j = 0; j < 3; j++)
				if (candidates[x][y].contains(testBoard[rowOffset + i][colOffset + j]))
					candidates[x][y].remove(candidates[x][y].indexOf(testBoard[rowOffset + i][colOffset + j]));
		// remove numbers from candidate list if they appear in the box
	}

	private static void insertNakedSingles(int x, int y) { // obviously works
		if (candidates[x][y].size() == 1) // if only one candidate, insert
			testBoard[x][y] = candidates[x][y].remove(0);
	}

	private static void insertHiddenSingles() { 
	eliminateAll();
		for (int row = 0; row < 9; row++) { // rows
			int[] multiplicityOf = new int[10];
			for (int col = 0; col < 9; col++)
				if (testBoard[row][col] == 0)
					for (int candidate : candidates[row][col])
						multiplicityOf[candidate] += 1;

			for (int candidate = 1; candidate <= 9; candidate++)
				if (multiplicityOf[candidate] == 1)
					for (int col = 0; col < 9; col++)
						tryInsert(row, col, candidate);
		}

		eliminateAll();

		for (int col = 0; col < 9; col++) { // columns
			int[] multiplicityOf = new int[10];
			for (int row = 0; row < 9; row++)
				if (testBoard[row][col] == 0)
					for (int candidate : candidates[row][col])
						multiplicityOf[candidate] += 1;

			for (int candidate = 1; candidate <= 9; candidate++)
				if (multiplicityOf[candidate] == 1)
					for (int row = 0; row < 9; row++)
						tryInsert(row, col, candidate);
		}

		eliminateAll();

		for (int x = 0; x < 3; x++) { // all nine boxes
			for (int y = 0; y < 3; y++) {
				int[] multiplicityOf = new int[10];
				int rowOffset = x * 3;
				int colOffset = y * 3;
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++)
						if (testBoard[rowOffset + i][colOffset + j] == 0)
							for (int candidate : candidates[rowOffset + i][colOffset + j])
								multiplicityOf[candidate] += 1;
				}

				for (int candidate = 1; candidate <= 9; candidate++)
					if (multiplicityOf[candidate] == 1)
						for (int i = 0; i < 3; i++)
							for (int j = 0; j < 3; j++)
								tryInsert(rowOffset + i, colOffset + j, candidate);
			}
		}
	}

	static void tryInsert(int row, int col, int candidate) {
		if (testBoard[row][col] == 0 && candidates[row][col].contains(candidate))
			testBoard[row][col] = candidates[row][col].remove(candidates[row][col].indexOf(candidate));
	}

	static void eliminateAll() {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				if (testBoard[i][j] == 0)
					eliminateCandidates(i, j);
	}

	static void fillCandidates() { // fills the candidate lists,
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (testBoard[i][j] == 0) {
					ArrayList<Integer> oneToNine = new ArrayList<Integer>();
					for (int k = 1; k < 10; k++)
						oneToNine.add(k);
					candidates[i][j] = (oneToNine);
				}
			}
		}
		eliminateAll();
	}

	static int[][] deepCopyArray(int[][] a1) {
		int[][] a2 = new int[a1.length][a1[0].length];
		for (int i = 0; i < a1.length; i++) {
			for (int j = 0; j < a1[0].length; j++) {
				a2[i][j] = a1[i][j];
			}
		}
		return a2;
	}

	static void SolverTest() { // solves
		fillCandidates();
		int counter = 0;
		boolean epicWin = false;
		while (!epicWin) {
			int[][] lastMove = deepCopyArray(testBoard);
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (testBoard[i][j] == 0) {
						eliminateAndInsert(i, j);
						printBoard(testBoard);
						counter++;
					}
					if (Arrays.deepEquals(testBoard, solution)) {
						epicWin = true;
						break;
					}
				}
			}
			if (Arrays.deepEquals(testBoard, lastMove)) {
				System.out.println("FAIL! Iterations: " + counter);
				return;
			}
		}
		System.out.println("WIN! Iterations: " + counter);
	}

	static void printBoard(int[][] array) { // prints
		for (int i = 0; i < 9; ++i) {
			if (i % 3 == 0)
				System.out.println(" -----------------------");
			for (int j = 0; j < 9; ++j) {
				if (j % 3 == 0)
					System.out.print("| ");
				System.out.print(array[i][j] == 0 ? " " : array[i][j]);
				System.out.print(' ');
			}
			System.out.println("|");
		}
		System.out.println(" -----------------------");
		System.out.println();
	}

	public static void main(String[] args) {
		SolverTest();
	}
}
