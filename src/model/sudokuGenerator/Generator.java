package model.sudokuGenerator;

import model.sudokuGenerator.generatorFunction.*;

public abstract class Generator {
	private final static int MIN_NUMBERS_EASY = 38;
	private final static int MAX_NUMBERS_EASY = 42;
	private final static int MIN_NUMBERS_MEDIUM = 32;
	private final static int MAX_NUMBERS_MEDIUM = 36;
	private final static int MIN_NUMBERS_HARD = 26;
	private final static int MAX_NUMBERS_HARD = 30;

	/**
	 * @return generated sudoku int[9][9] with the difficulty easy
	 */
	public static int[][] generateEasy() {
		int[][] grid = RandomSolvedGridGenerator.generateRandomlySolvedGrid();

		int numbersInSudoku = RandomNumbers.randomIntBetween(MIN_NUMBERS_EASY, MAX_NUMBERS_EASY);
		try {
			grid = NumbersInGridRemover.removeRandomNumbersFromSolvedGrid(grid, numbersInSudoku);
		} catch (GridNotSolvedException e) {
			System.out.println("Generator tried remove from unsolved Grid:" + e);
			grid = generateEasy();
		}

		return grid;
	}

	/**
	 * @return generated sudoku int[9][9] with the difficulty medium
	 */
	public static int[][] generateMedium() {
		int[][] grid = RandomSolvedGridGenerator.generateRandomlySolvedGrid();

		int numbersInSudoku = RandomNumbers.randomIntBetween(MIN_NUMBERS_MEDIUM, MAX_NUMBERS_MEDIUM);
		try {
			grid = NumbersInGridRemover.removeRandomNumbersFromSolvedGrid(grid, numbersInSudoku);
		} catch (GridNotSolvedException e) {
			System.out.println("Generator tried remove from unsolved Grid:" + e);
			grid = generateMedium();
		}

		return grid;
	}

	/**
	 * @return generated sudoku int[9][9] with the difficulty hard
	 */
	public static int[][] generateHard() {
		int[][] grid = RandomSolvedGridGenerator.generateRandomlySolvedGrid();

		int numbersInSudoku = RandomNumbers.randomIntBetween(MIN_NUMBERS_HARD, MAX_NUMBERS_HARD);
		try {
			grid = NumbersInGridRemover.removeRandomNumbersFromSolvedGrid(grid, numbersInSudoku);
		} catch (GridNotSolvedException e) {
			System.out.println("Generator tried remove from unsolved Grid:" + e);
			grid = generateHard();
		}

		return grid;
	}

	/**
	 * @param amountOfNumbers gives the amount of numbers thats been generated in
	 *                        the sudoku grid int[9][9]
	 * @return the generated sudoku grid int[9][9] with the demanded amount of
	 *         numbers
	 * @throws NoPossibleSudokuException if the generator can't generate the sudoku
	 *                                   in a usefull time
	 */
	public static int[][] generateWithAmountOfNumbers(int amountOfNumbers) throws NoPossibleSudokuException {
		if (amountOfNumbers < 22) {
			if (amountOfNumbers < 17)
				throw new NoPossibleSudokuException("There is no Sudoku with less than 17 numbers");
			throw new NoPossibleSudokuException("Can't create Sudoku in time");
		}
		if (amountOfNumbers > 81) {
			throw new NoPossibleSudokuException("Can't create Sudoku with more than 81 Numbers");
		}

		int[][] grid = RandomSolvedGridGenerator.generateRandomlySolvedGrid();

		try {
			grid = NumbersInGridRemover.removeRandomNumbersFromSolvedGrid(grid, amountOfNumbers);
		} catch (GridNotSolvedException e) {
			System.out.println("Generator tried remove from unsolved Grid:" + e);
			grid = generateHard();
		}

		return grid;
	}
}
