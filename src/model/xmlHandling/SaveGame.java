package model.xmlHandling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.ModelField;
import model.ModelMain;
import model.ModelOptions;
import model.ModelSession;
import enums.*;

/**
 * Class for handling the saving of a savegame file. The savegame is in xml
 * format.
 *
 */
public class SaveGame {
	private static String filename;
	private final ModelMain model;
	ModelField[][] gridValues = new ModelField[StaticGameDim.GRIDSIZE][StaticGameDim.GRIDSIZE];
	ModelOptions modOptions;
	ModelSession modSession;

	public SaveGame(ModelMain m) {
		this.model = m;
	}

	/**
	 * method to generate a recommended file name
	 * 
	 * @return filename as string
	 */
	private void generateFilename() {
		filename = "SudokuSaveGame" + ".xml";
	}

	/**
	 * Method which handles all the actions needed to save the actual state of the
	 * game
	 */
	public void saveGame() {
		Document dom;
		Element e = null;

		// get model fields
		gridValues = model.getGrid().getGrid();
		modOptions = model.getOptions();
		modSession = model.getSession();

		// stop timer
		model.stopTimer();

		// generate filename
		generateFilename();

		// instance of a DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// use factory to get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// create instance of DOM
			dom = db.newDocument();
			// create the root element
			Element rootEle = dom.createElement("Sudoku");

			// create data elements for generated sudoku
			Element generatedEle = dom.createElement("SudokuGenerated");
			String[] generatedValues = getGeneratedValues();
			for (int i = 0; i < generatedValues.length; i++) {
				e = dom.createElement("GeneratedRow");
				e.appendChild(dom.createTextNode(generatedValues[i]));
				generatedEle.appendChild(e);
			}
			rootEle.appendChild(generatedEle);

			// create data elements for played sudoku
			Element playedEle = dom.createElement("SudokuPlayed");
			String[] gridValues = getGridValues();
			for (int i = 0; i < gridValues.length; i++) {
				e = dom.createElement("PlayedRow");
				e.appendChild(dom.createTextNode(gridValues[i]));
				playedEle.appendChild(e);
			}
			rootEle.appendChild(playedEle);

			// create data elements for solved sudoku
			Element solvedEle = dom.createElement("SudokuSolution");
			String[] solvedValues = getSolutionValues();
			for (int i = 0; i < solvedValues.length; i++) {
				e = dom.createElement("SolutionRow");
				e.appendChild(dom.createTextNode(solvedValues[i]));
				solvedEle.appendChild(e);
			}
			rootEle.appendChild(solvedEle);

			// create data elements for time played
			e = dom.createElement("TimePlayed");
			e.appendChild(dom.createTextNode(getTimePlayed()));
			rootEle.appendChild(e);

			// create data elements for game mode
			e = dom.createElement("GameMode");
			e.appendChild(dom.createTextNode(getGameMode()));
			rootEle.appendChild(e);

			// Add root to document
			dom.appendChild(rootEle);

			try {
				Transformer tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				tr.setOutputProperty(OutputKeys.METHOD, "xml");
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

				// ask for file destination
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Speicherort auswählen");
				// allow only xml
				fileChooser.setFileFilter(new FileNameExtensionFilter("xml files (*.xml)", "xml"));
				// suggest file name
				fileChooser.setSelectedFile(new File(filename));
				// open file chooser dialog
				int option = fileChooser.showSaveDialog(null);

				if (option == JFileChooser.APPROVE_OPTION) {
					// send DOM to file
					tr.transform(new DOMSource(dom),
							new StreamResult(new FileOutputStream(fileChooser.getSelectedFile())));
				} else {
					System.out.println("File save has been canceled");
				}

			} catch (TransformerException te) {
				System.out.println(te.getMessage());
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			// restart timer
			model.startTimer();

		} catch (ParserConfigurationException pce) {
			System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Method to save integer values of grid to an string array
	 * 
	 * @return String Array formatted for xml
	 */
	private String[] getGridValues() {
		String[] generatedValues = new String[StaticGameDim.GRIDSIZE];
		StringBuffer row = new StringBuffer();
		int actValue;

		for (int i = 0; i < gridValues.length; i++) {
			for (int j = 0; j < gridValues[i].length; j++) {
				actValue = gridValues[i][j].getValue();
				if (actValue > 0 && actValue <= 9) {
					row.append(actValue);
				} else {
					row.append("-");
				}
				if (j < gridValues[i].length - 1) {
					row.append(",");
				}
			}
			generatedValues[i] = row.toString();

			// reset StringBuffer
			row.delete(0, row.length());
		}
		return generatedValues;
	}

	/**
	 * Method to get generated values
	 * 
	 * @return String Array formatted for xml
	 */
	public String[] getGeneratedValues() {
		String[] generatedValues = new String[9];
		StringBuffer row = new StringBuffer();
		int actValue;

		for (int i = 0; i < gridValues.length; i++) {
			for (int j = 0; j < gridValues[i].length; j++) {
				actValue = gridValues[i][j].getStartValue();
				if (actValue > 0 && actValue <= 9) {
					row.append(actValue);
				} else {
					row.append("-");
				}
				if (j < gridValues[i].length - 1) {
					row.append(",");
				}
			}
			generatedValues[i] = row.toString();

			// reset StringBuffer
			row.delete(0, row.length());
		}
		return generatedValues;
	}

	/**
	 * Method to get solved values
	 * 
	 * @return String Array formatted for xml
	 */
	public String[] getSolutionValues() {
		String[] solutionValues = new String[9];
		StringBuffer row = new StringBuffer();
		int actValue;

		for (int i = 0; i < gridValues.length; i++) {
			for (int j = 0; j < gridValues[i].length; j++) {
				actValue = gridValues[i][j].getSolution();
				if (actValue > 0 && actValue <= 9) {
					row.append(actValue);
				} else {
					row.append("-");
				}
				if (j < gridValues[i].length - 1) {
					row.append(",");
				}
			}
			solutionValues[i] = row.toString();

			// reset StringBuffer
			row.delete(0, row.length());
		}
		return solutionValues;
	}

	/**
	 * Method to get elapsed time
	 * 
	 * @return String formatted for xml
	 */
	public String getTimePlayed() {
		StringBuffer timePlayed = new StringBuffer();
		int elapsedTime = modSession.getElapsedTime();

		// Calculate hours
		timePlayed.append(String.format("%02d:", (elapsedTime / 3600)));
		// Calculate minutes
		timePlayed.append(String.format("%02d:", (elapsedTime / 60) % 60));
		// Calculate seconds
		timePlayed.append(String.format("%02d", (elapsedTime / 1) % 60));

		return timePlayed.toString();
	}

	/**
	 * Method to get game difficulty
	 * 
	 * @return String formatted for xml
	 */
	public String getGameMode() {
		Difficulty diff = modOptions.getDifficulty();
		if (diff == Difficulty.EASY) {
			return "easy";
		} else if (diff == Difficulty.MEDIUM) {
			return "medium";
		} else {
			return "hard";
		}
	}
}
