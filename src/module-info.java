module Sudoku {
	exports control.listener;
	exports model.sudokuSolver.solverFunction;
	exports model.sudokuGenerator;
	exports start;
	exports control;
	exports model.sudokuSolver;
	exports enums;
	exports view;
	exports model.temporaryFileSystem;
	exports model;
	exports model.sudokuGenerator.generatorFunction;
	exports model.xmlHandling;
	exports jUnitTest;

	requires transitive java.desktop;
	requires java.xml;
	requires junit;
	requires org.junit.jupiter.api;
}