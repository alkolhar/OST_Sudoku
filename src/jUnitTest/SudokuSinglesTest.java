/**
 * 
 */
package jUnitTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author mustafa
 *
 */
/*
 * test case defines the fixture to run multiple tests. To define a test
 * case<br/> Hinweis Dieser Test wurde durchgeführt, bevor die GUI erstellt
 * wurde
 */
public class SudokuSinglesTest {
	int[][] board1 = new int[9][9];
	int[][] board2 = new int[9][9];

	ByteArrayOutputStream outContent;
	@Before
	public void setUp() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	public void testDefault() {
		SudokuSingles s = new SudokuSingles();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				assertEquals(0, s.board[i][j]);
			}
		}
	}

	public void testConstructor() {
		SudokuSingles s = new SudokuSingles(board1);
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1.length; j++) {
				assertEquals(board1[i][j], s.board[i][j]);
			}
		}
	}
	@Test
	public void testCandidates() {
		String input = "740800090009075000800090205014056008000948000600210540106080003000530800080007059";
		int[][] board1 = new int[9][9];
		int x = 0;
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1.length; j++) {
				int q = Character.getNumericValue(input.charAt(x));
				board1[i][j] = q;
				x++;
			}
		}
		SudokuSingles s = new SudokuSingles(board1);
		boolean[] z = s.candidates(0, 0);
		for (int i = 0; i < z.length; i++) {
			assertFalse(z[i]);
		}
		boolean[] y = s.candidates(3, 3);
		assertTrue(y[7]);
		assertTrue(y[3]);
		for (int i = 0; i < y.length; i++) {
			if (i != 7 && i != 3)
				assertFalse(y[i]);
		}
	}
	@Test
	@Category(GoodTestsCategory.class)
	public void testNakedSingles() {
		String input = "002193000000007000700040019803000600045000230007000504370080006000600000000534100";
		int x = 0;
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1.length; j++) {
				int q = Character.getNumericValue(input.charAt(x));
				board1[i][j] = q;
				x++;
			}
		}
		SudokuSingles s = new SudokuSingles(board1);
		s.nakedSingles();
		s.solve();
		assertEquals(4, s.board[0][0]);
		assertEquals(9, s.board[6][3]);
	}
	@Test
	@Category(GoodTestsCategory.class)
	public void testHiddenSingles() {
		String input = "010900740000800003070320690004030200000602000008010300081070030300008000069003020";
		String onlyHidden = "100200300020010040003005006700600500050080070008004001800700400030060020009002007";
		// tests input puzzle
		int x = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int q = Character.getNumericValue(input.charAt(x));
				board1[i][j] = q;
				x++;
			}
		}
		SudokuSingles s = new SudokuSingles(board1);
		assertEquals(true, s.hiddenSingles());
		s.solve();
		assertEquals(8, s.board[0][0]);
		assertEquals(1, s.board[6][2]);
		assertEquals(5, s.board[3][1]);
		// tests onlyHidden puzzle
		int y = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int hidden = Character.getNumericValue(onlyHidden.charAt(y));
				board2[i][j] = hidden;
				y++;
			}
		}
		SudokuSingles hidden = new SudokuSingles(board2);
		hidden.hiddenSingles();
		hidden.solve();
		assertEquals(8, hidden.board[0][1]);
	}
	@Test
	public void testCopy() {
		SudokuSingles s = new SudokuSingles(board1);
		assertArrayEquals(board1, s.board());
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1.length; j++) {
				assertEquals(board1[i][j], s.board[i][j]);
			}
		}
	}

	public void testGetRow() {
		SudokuSingles s = new SudokuSingles(board1);
		int x = 2;
		int y = 3;
		int z = 7;
		assertEquals(0, s.getRow(x));
		assertEquals(3, s.getRow(y));
		assertEquals(6, s.getRow(z));
	}
	@Test
	public void testGetColumn() {
		SudokuSingles s = new SudokuSingles(board1);
		int x = 2;
		int y = 4;
		int z = 8;
		assertEquals(0, s.getColumn(x));
		assertEquals(3, s.getColumn(y));
		assertEquals(6, s.getColumn(z));
	}
	@Test
	public void testIsSolved() {
		String input = "010900740000800003070320690004030200000602000008010300081070030300008000069003020";
		int x = 0;
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1.length; j++) {
				int q = Character.getNumericValue(input.charAt(x));
				board1[i][j] = q;
				x++;
			}
		}
		SudokuSingles s = new SudokuSingles(board1);
		String bit;
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1.length; j++) {
				if (board1[i][j] == 0)
					assertEquals(false, s.isSolved());
				if (i == 8) {
					bit = input.substring(0, i);
					assertEquals("01090074", bit);
				}
			}
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (i + 1 < 9 && j + 1 < 9) {
					if ((board1[i + 1][j] == board1[i][j]) || (board1[i][j + 1] == board1[i][j]))
						assertEquals(false, s.isSolved());
				}
			}
		}
	}
	@Test
	public void testSolve() {
		String input = "002193000000007000700040019803000600045000230007000504370080006000600000000534100";
		int x = 0;
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1.length; j++) {
				int q = Character.getNumericValue(input.charAt(x));
				board1[i][j] = q;
				x++;
			}
		}
		SudokuSingles s = new SudokuSingles(board1);
		int value = board1[0][6];
		assertEquals(0, value);
		s.solve();
		assertEquals(7, s.board[0][6]); // check to see that the value changed
	}
	@Test
	public void testCheckRow() {
		String input = "002193000000007000700040019803000600045000230007000504370080006000600000000534100";
		int x = 0;
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1.length; j++) {
				int q = Character.getNumericValue(input.charAt(x));
				board1[i][j] = q;
				x++;
			}
		}
		SudokuSingles s = new SudokuSingles(board1);
		boolean[] b = s.candidates(5, 3);
		s.checkRow(b, 5, 3);
		assertEquals(3, s.board[5][3]);
	}
	@Test
	public void testCheckCol() {
		String input = "002193000000007000700040019803000600045000230007000504370080006000600000000534100";
		int x = 0;
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1.length; j++) {
				int q = Character.getNumericValue(input.charAt(x));
				board1[i][j] = q;
				x++;
			}
		}
		SudokuSingles s = new SudokuSingles(board1);
		boolean[] b = s.candidates(5, 3);
		s.checkRow(b, 5, 3);
		assertEquals(3, s.board[5][3]);
	}
	@Test
	public void testCheckBox() {
		String input = "002193000000007000700040019803000600045000230007000504370080006000600000000534100";
		int x = 0;
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1.length; j++) {
				int q = Character.getNumericValue(input.charAt(x));
				board1[i][j] = q;
				x++;
			}
		}
		SudokuSingles s = new SudokuSingles(board1);
		boolean[] b = s.candidates(5, 3);
		s.checkRow(b, 5, 3);
		assertEquals(3, s.board[5][3]);
	}

	public void testDraw() {
		String input = "050704060060090380004000005500007603496203857308900001800000500045070030020305070";
		int x = 0;
		SudokuSingles s = new SudokuSingles(board1);
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1.length; j++) {
				int q = Character.getNumericValue(input.charAt(x));
				board1[i][j] = q;
				x++;
			}
		}
		s.draw();
		assertTrue(!outContent.toString().matches(""));
	}
}
