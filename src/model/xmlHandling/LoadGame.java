package model.xmlHandling;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import enums.*;
import model.ModelField;
import model.ModelGrid;
import model.ModelMain;
import model.ModelOptions;
import model.ModelSession;
import view.ViewMain;

/**
 * Class for handling the loading of a savegame file. The savegame is in xml
 * format.
 *
 */
public class LoadGame {
	private final ViewMain view;
	private final ModelGrid grid;
	private final ModelMain model;
	private final ModelOptions options;
	ModelField[][] gridValues = new ModelField[StaticGameDim.GRIDSIZE][StaticGameDim.GRIDSIZE];
	ModelOptions modOptions;
	ModelSession modSession;

	public LoadGame(ModelMain m) {
		this.model = m;
		view = m.getViewMain();
		grid = m.getGrid();
		options = m.getOptions();
	}

	/**
	 * This method opens a file chooser dialog to select the desired file to load.
	 * 
	 */
	public void loadGame() {
		// stop timer
		model.stopTimer();

		// get model fields
		gridValues = model.getGrid().getGrid();
		modOptions = model.getOptions();
		modSession = model.getSession();

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// prepare for reset

			grid.resetGrid();
			options.setFocusEnabled(true);
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName()); // XXX: debug

			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();

				Document document = builder.parse(chooser.getSelectedFile());
				document.getDocumentElement().normalize();

				// load played values
				loadData2Grid(document);
				// load generated values
				setLoadedGenerator(document);
				// load solved values
				setLoadedSolution(document);
				// load elapsed time
				loadTimePlayed(document);
				// load gamemode
				loadGameMode(document);
				// refresh view & grid
				grid.resetHighlight();
				view.update();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// restart timer
		model.startTimer();

	}

	/**
	 * This method loads the values from the xml file to the grid
	 * 
	 * @param document the savegame file
	 */
	private void loadData2Grid(Document document) {
		NodeList nodeList = document.getElementsByTagName("PlayedRow");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node actNode = nodeList.item(i);

			if (actNode.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) actNode;

				String[] tempArray;
				String delimiter = ",";

				tempArray = e.getTextContent().split(delimiter);
				for (int j = 0; j < tempArray.length; j++) {
					if (tempArray[j].equals("-")) {
						gridValues[i][j].setValue(0);
					} else {
						gridValues[i][j].setValue(Integer.parseInt(tempArray[j]));
					}
				}

			}
		}
	}

	/**
	 * This method loads the solution values from the xml file to the model
	 * 
	 * @param document the savegame file
	 */
	private void setLoadedSolution(Document document) {
		NodeList nodeList = document.getElementsByTagName("SolutionRow");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node actNode = nodeList.item(i);

			if (actNode.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) actNode;

				String[] tempArray;
				String delimiter = ",";

				tempArray = e.getTextContent().split(delimiter);
				for (int j = 0; j < tempArray.length; j++) {
					if (tempArray[j].equals("-")) {
						gridValues[i][j].setSolution(0);
					} else {
						gridValues[i][j].setSolution(Integer.parseInt(tempArray[j]));
					}
				}
			}
		}
	}

	/**
	 * This method loads the generated values from the xml file to the model
	 * 
	 * @param document the savegame file
	 */
	private void setLoadedGenerator(Document document) {
		NodeList nodeList = document.getElementsByTagName("GeneratedRow");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node actNode = nodeList.item(i);

			if (actNode.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) actNode;

				String[] tempArray;
				String delimiter = ",";

				tempArray = e.getTextContent().split(delimiter);
				for (int j = 0; j < tempArray.length; j++) {
					if (tempArray[j].equals("-")) {
						gridValues[i][j].setStartValue(0);
					} else {
						gridValues[i][j].setStartValue(Integer.parseInt(tempArray[j]));
					}
				}
			}
		}
	}

	/**
	 * This method loads the elapsed time from the xml file to the model
	 * 
	 * @param document the savegame file
	 */
	private void loadTimePlayed(Document document) {
		NodeList nodeList = document.getElementsByTagName("TimePlayed");
		Node actNode = nodeList.item(0);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		int seconds = 0;

		if (actNode.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) actNode;

			try {
				Date reference = dateFormat.parse("00:00:00");
				Date date = dateFormat.parse(e.getTextContent());
				seconds = (int) ((date.getTime() - reference.getTime()) / 1000);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			modSession.setElapsedTime(seconds);
		}
	}

	/**
	 * MThis method loads the game mode from the xcml file to the model
	 * 
	 * @param document the savegame file
	 */
	private void loadGameMode(Document document) {
		NodeList nodeList = document.getElementsByTagName("GameMode");
		Node actNode = nodeList.item(0);

		if (actNode.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) actNode;

			if (e.getTextContent().equals("easy")) {
				modOptions.setDifficulty(Difficulty.EASY);
			} else if (e.getTextContent().equals("medium")) {
				modOptions.setDifficulty(Difficulty.MEDIUM);
			} else {
				modOptions.setDifficulty(Difficulty.HARD);
			}

		}
	}
}
