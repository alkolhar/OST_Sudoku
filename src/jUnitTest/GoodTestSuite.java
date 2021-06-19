/**
 * 
 */
package jUnitTest;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author mustafa
 *
 */
@RunWith(Categories.class)
@IncludeCategory(GoodTestsCategory.class)
@ExcludeCategory(BadCategory.class)
@Suite.SuiteClasses({ SolverTimeoutTest.class, ModelGridTest.class, SolutionTest.class, SolverTest.class,
		AlteTest.class, SudokuSinglesTest.class })
public class GoodTestSuite {

}
