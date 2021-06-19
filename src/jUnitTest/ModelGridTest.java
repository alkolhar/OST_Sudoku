/**
 * 
 */
package jUnitTest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import model.sudokuGenerator.Generator;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import model.ModelField;
//import model.ModelGrid;
/**
 * @author mustafa
 *
 */
public class ModelGridTest {
	private ModelField[][] game;
	//private ModelGrid mg;
	int[][] easy, medium, hard;
	int [][] copy;
	@Before
	public void setUp() throws Exception {
		game = new ModelField[9][9];
		for (int y = 0; y < game.length; y++) {
			for (int x = 0; x < game[y].length; x++) {
				game[y][x] = new ModelField();
			}
		}
	//	mg = new ModelGrid();
	}
	@Test
	@Category(GoodTestsCategory.class)
	public void testSetValue() {
		game[0][0].setValue(1);
		game[8][8].setValue(9);
		game[0][7].setValue(6);
		assertEquals(1, game[0][0].getValue());
		assertEquals(9, game[8][8].getValue());
		assertEquals(6, game[0][7].getValue());
	}
	@Test
	@Category(GoodTestsCategory.class)
	public void testGeneratorEasyGame() {
		copy = Generator.generateEasy();
		int cnt=0;
		System.out.println("befor Easy" );
		for (int row = 0; row < game.length; row++) {
			for (int col = 0; col < game.length; col++) {
				System.out.print(copy[row][col]);
				if(copy[row][col] > 0)
						cnt ++;
			}
		}
		System.out.println();
		System.out.println("after Easy : " + cnt );
		assertTrue(cnt > 38 && cnt <= 42);
		System.out.println();
	}
	@Test
	@Category(GoodTestsCategory.class)
	public void testGeneratorMediumGame() {
		copy = Generator.generateMedium();
		int cnt=0;
		System.out.println("befor Medium" );
		for (int row = 0; row < game.length; row++) {
			for (int col = 0; col < game.length; col++) {
				System.out.print(copy[row][col]);
				if(copy[row][col] > 0)
						cnt ++;
			}
		}
		System.out.println();
		System.out.println("after Medium : " + cnt );
		assertTrue(cnt > 32 && cnt <= 36);
		
		System.out.println();
	}
	@Test
	@Category(GoodTestsCategory.class)
	public void testGeneratorHardGame() {
		copy = Generator.generateHard();
		int cnt=0;
		System.out.println("befor Hard" );
		for (int row = 0; row < game.length; row++) {
			for (int col = 0; col < game.length; col++) {
				System.out.print(copy[row][col]);
				if(copy[row][col] > 0)
						cnt ++;
			}
		}
		System.out.println();
		System.out.println("after Hard : " + cnt );
		assertTrue(cnt > 26 && cnt <= 30);
		
		System.out.println();
	}
}
