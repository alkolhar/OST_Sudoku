/**
 * 
 */
package jUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author mustafa
 *
 */
public class AlteTest {
	private static AltModelGrid game ;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		game = new AltModelGrid();
	}

	// Der Test der Zellen im Spiel.
	@Test
	public void fieldsTest() {
		game.setNumber(2, 2, 9);
		assertEquals(game.getNumber(2, 2), 9);
	}

	// Die Summe der Spalten, Zeilen oder Blöcke in Sudoku muss 45 betragen.
	@Test
	@Category(GoodTestsCategory.class)
	public void sumTotalsTest() {
		for (int i = 0; i < 9; i++) {
			assertEquals(sumRow(game.getSolution(), i), 45);
			assertEquals(sumColumn(game.getSolution(), i), 45);
			for (int j = 0; j < 3; j++)
				assertEquals(sumBlock(game.getSolution(), i, j * 3), 45);
		}
	}

	private int sumRow(int[][] solution, int row) {
		int sum = 0;
		for (int i = 0; i < solution[row].length; i++)
			sum += solution[row][i];
		return sum;
	}

	private int sumColumn(int[][] solution, int col) {
		int sum = 0;
		for (int i = 0; i < solution[col].length; i++)
			sum += solution[i][col];
		return sum;
	}

	private int sumBlock(int[][] solution, int index1, int index2) {
		int sum = 0;
		index1 = index1 - (index1 % 3);
		index2 = index2 - (index2 % 3);
		for (int i = index1; i < index1 + 3; i++)
			for (int j = index2; j < index2 + 3; j++)
				sum += solution[i][j];
		return sum;
	}

	// Test der Genauigkeit des Benutzereingabewerts.
	@Test
	@Category({GoodTestsCategory.class, BadCategory.class})
	public void userInputTest() {
		game.setNumber(0, 0, game.getSolution(0, 0));
		game.checkGame();
		assertTrue(game.isCheckValid(0, 0));
	}

	// Testen Sie, ob das Spiel korrekt erstellt wurde.
	@Test
	public void isEqualsTest() {
		assertTrue(game.getGame().equals(game.getGame()));
	}
}
