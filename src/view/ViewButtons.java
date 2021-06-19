package view;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.EventListener;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import enums.Difficulty;
import enums.ListenerKey;
import model.ModelOptions;

/**
 * Class for Buttons like save, load, difficulty selector etc. Generates left
 * side of the GUI, adds buttons to the panel and implements the actionlisteners
 *
 */
public class ViewButtons extends JPanel {
	private static final long serialVersionUID = 1L;
	private ViewTimer timer;

	// buttons with toggle function
	private JButton showWrongFieldBtn;
	private JButton showHelpNrBtn;
	private JButton showSolutionBtn;
	private JRadioButton easyMode;
	private JRadioButton mediumMode;
	private JRadioButton hardMode;

	// GridLayout Dimensions
	private static final int rowCount = 6;
	private static final int colCount = 0;
	private static final int rowGap = 0;
	private static final int colGap = 10;

	// Icon and Button Dimensions
	private static final int iconSize = 30;
	private static final int btnSize = iconSize + 5;

	// Button Color
	private static final Color bgColorNormal = Color.WHITE;
	private static final Color bgColorActive = Color.GREEN;

	// Tooltip Strings
	private static final String newGameTTString = "Ein neues Spiel starten";
	private static final String showHelpNrTTString = "Hilfszahlen anzeigen";
	private static final String saveTTString = "Spiel speichern";
	private static final String openTTString = "Spiel laden";
	private static final String undoTTString = "Schritt zurück";
	private static final String restartTTString = "Neustarten";
	private static final String redoTTString = "Schritt vorwärts";
	private static final String showWrongFieldTTString = "Kontrolle und Fehler anzeigen";
	private static final String showSolutionTTString = "Lösung anzeigen";
	private static final String autoHelpNrTTString = "mögliche Hilfzahlen automatisch eintragen oder alle Hilfszahlen entfernen";

	/**
	 * Constructor creates GridLayout with each row being a different buttons group
	 */
	public ViewButtons(Hashtable<String, EventListener> table) {
		this.setLayout(new GridLayout(rowCount, colCount, rowGap, colGap));

		// Add Timer
		timer = new ViewTimer(this);
		this.add(timer);

		// Ad New Game Button
		this.add(buildNewGame(table));

		// Add Mode Selector
		this.add(buildModeSelector(table));

		// Add Save/Load Button
		this.add(buildSave(table));

		// Add Undo/Redo/Restore Buttons
		this.add(buildUndo(table));

		// Add Check for Mistakes/Show Solution Buttons
		this.add(buildOptions(table));
	}

	/**
	 * set difficulty option
	 * 
	 * @param d Difficulty
	 */
	private void setDiffuclty(Difficulty d) {
		switch (d) {
		case EASY:
			easyMode.setSelected(true);
			mediumMode.setSelected(false);
			hardMode.setSelected(false);
			break;
		case HARD:
			easyMode.setSelected(false);
			mediumMode.setSelected(false);
			hardMode.setSelected(true);
			break;
		case MEDIUM:
			easyMode.setSelected(false);
			mediumMode.setSelected(true);
			hardMode.setSelected(false);
			break;
		default:
			break;
		}
	}

	/**
	 * set Button showWrongField active -> change BG-Color
	 * 
	 * @param b boolean
	 */
	private void setShowWrongFieldActive(boolean b) {
		if (b) {
			showWrongFieldBtn.setBackground(bgColorActive);
		} else {
			showWrongFieldBtn.setBackground(bgColorNormal);
		}
	}

	/**
	 * set Button showHelpNr active -> change BG-Color
	 * 
	 * @param b boolean
	 */
	private void setShowHelpNrActive(boolean b) {
		if (b) {
			showHelpNrBtn.setBackground(bgColorActive);
		} else {
			showHelpNrBtn.setBackground(bgColorNormal);
		}
	}

	/**
	 * set Button showSolution active -> change BG-Color
	 * 
	 * @param b boolean
	 */
	private void setShowSolutionActive(boolean b) {
		if (b) {
			showSolutionBtn.setBackground(bgColorActive);
		} else {
			showSolutionBtn.setBackground(bgColorNormal);
		}
	}

	/**
	 * Creates the 'new game' button
	 * 
	 * @param table hashtable with eventlisteners
	 * @return JPanel object containing the buttons
	 */
	private JPanel buildNewGame(Hashtable<String, EventListener> table) {
		JPanel newGamePanel = new JPanel();

		// Create GridLayout for 1 button
		newGamePanel.setLayout(new GridLayout(1, 1));

		// Create Buttons and set Background to white
		JButton newGameBtn = new JButton();
		newGameBtn.setSize(btnSize, btnSize);
		newGameBtn.setBackground(bgColorNormal);

		// Add Button Text
		newGameBtn.setText("Neues Spiel");

		// Add ActionListener
		ActionListener newGame = (ActionListener) table.get(ListenerKey.NEWGAME.getKey());
		newGameBtn.addActionListener(newGame);

		// Add ToolTip Text
		newGameBtn.setToolTipText(newGameTTString);

		// Add Buttons to Panel
		newGamePanel.add(newGameBtn);

		return newGamePanel;
	}

	/**
	 * Adds two buttons with functions to load and save the game
	 * 
	 * @param table hashtable with eventlisteners
	 * @return JPanel object containing the buttons
	 */
	private JPanel buildSave(Hashtable<String, EventListener> table) {
		JPanel savePanel = new JPanel();
		// Create GridLayout for 2 buttons with horizontal alignment
		savePanel.setLayout(new GridLayout(1, 2));

		// Create Buttons and set Background to white
		JButton saveBtn = new JButton();
		saveBtn.setSize(btnSize, btnSize);
		saveBtn.setBackground(bgColorNormal);
		JButton openBtn = new JButton();
		openBtn.setSize(btnSize, btnSize);
		openBtn.setBackground(bgColorNormal);

		// Add ActionListener
		ActionListener save = (ActionListener) table.get(ListenerKey.SAVE.getKey());
		ActionListener load = (ActionListener) table.get(ListenerKey.LOAD.getKey());
		saveBtn.addActionListener(save);
		openBtn.addActionListener(load);

		// Add Icons to Buttons
		try {
			saveBtn.setIcon(loadRescaleImage("save.png"));
			openBtn.setIcon(loadRescaleImage("open.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Add ToolTip Text
		saveBtn.setToolTipText(saveTTString);
		openBtn.setToolTipText(openTTString);

		// Add Buttons to Panel
		savePanel.add(saveBtn);
		savePanel.add(openBtn);

		return savePanel;
	}

	/**
	 * Adds three buttons with functions to undo, redo and restart the game
	 * 
	 * @param table hashtable with eventlisteners
	 * @return JPanel object containing the buttons
	 */
	private JPanel buildUndo(Hashtable<String, EventListener> table) {
		JPanel undoPanel = new JPanel();
		// Create GridLayout for 3 buttons with horizontal alignment
		undoPanel.setLayout(new GridLayout(1, 3));

		// Create Buttons and set Background to white
		JButton undoBtn = new JButton();
		undoBtn.setSize(btnSize, btnSize);
		undoBtn.setBackground(bgColorNormal);
		JButton restartBtn = new JButton();
		restartBtn.setSize(btnSize, btnSize);
		restartBtn.setBackground(bgColorNormal);
		JButton redoBtn = new JButton();
		redoBtn.setSize(btnSize, btnSize);
		redoBtn.setBackground(bgColorNormal);

		// Add ActionListener
		ActionListener undo = (ActionListener) table.get(ListenerKey.UNDO.getKey());
		ActionListener redo = (ActionListener) table.get(ListenerKey.REDO.getKey());
		ActionListener restart = (ActionListener) table.get(ListenerKey.RESTART.getKey());
		undoBtn.addActionListener(undo);
		redoBtn.addActionListener(redo);
		restartBtn.addActionListener(restart);

		// Add Icons to Buttons
		try {
			undoBtn.setIcon(loadRescaleImage("back.png"));
			restartBtn.setIcon(loadRescaleImage("restart.png"));
			redoBtn.setIcon(loadRescaleImage("forward.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Add ToolTip Text
		undoBtn.setToolTipText(undoTTString);
		restartBtn.setToolTipText(restartTTString);
		redoBtn.setToolTipText(redoTTString);

		// Add Buttons to Panel
		undoPanel.add(undoBtn);
		undoPanel.add(restartBtn);
		undoPanel.add(redoBtn);

		return undoPanel;
	}

	/**
	 * Adds four buttons with functions to check for mistakes, enable helpNr,
	 * automatically fill out possibilities and show solution check for mistakes,
	 * show solution and enable helpNr can be toggled
	 * 
	 * @param table hashtable with eventlisteners
	 * @return JPanel object containing the buttons
	 */
	private JPanel buildOptions(Hashtable<String, EventListener> table) {
		JPanel optionsPanel = new JPanel();
		// Create GridLayout for 4 buttons 2x2 alignment
		optionsPanel.setLayout(new GridLayout(2, 2));

		// Create Buttons and set Background to white
		showWrongFieldBtn = new JButton();
		showWrongFieldBtn.setSize(btnSize, btnSize);
		showWrongFieldBtn.setBackground(bgColorNormal);
		showWrongFieldBtn.setToolTipText(showWrongFieldTTString);

		showHelpNrBtn = new JButton();
		showHelpNrBtn.setSize(btnSize, btnSize);
		showHelpNrBtn.setBackground(bgColorNormal);
		showHelpNrBtn.setToolTipText(showHelpNrTTString);

		JButton autoFillPossibilityBtn = new JButton();
		autoFillPossibilityBtn.setSize(btnSize, btnSize);
		autoFillPossibilityBtn.setBackground(bgColorNormal);
		autoFillPossibilityBtn.setToolTipText(autoHelpNrTTString);

		showSolutionBtn = new JButton();
		showSolutionBtn.setSize(2 * btnSize, 2 * btnSize);
		showSolutionBtn.setBackground(bgColorNormal);
		showSolutionBtn.setToolTipText(showSolutionTTString);

		// Add ActionListener
		ActionListener showWrongFieldL = (ActionListener) table.get(ListenerKey.SHOWWRONGFIELD.getKey());
		ActionListener showHelpNrL = (ActionListener) table.get(ListenerKey.SHOWHELPNR.getKey());
		ActionListener autoFillPossibilityL = (ActionListener) table.get(ListenerKey.AUTOFILLPOSSIBILITY.getKey());
		ActionListener showSolutionL = (ActionListener) table.get(ListenerKey.SHOWSOLUTION.getKey());
		showWrongFieldBtn.addActionListener(showWrongFieldL);
		showHelpNrBtn.addActionListener(showHelpNrL);
		autoFillPossibilityBtn.addActionListener(autoFillPossibilityL);
		showSolutionBtn.addActionListener(showSolutionL);

		// Add Icons to Buttons
		try {
			showWrongFieldBtn.setIcon(loadRescaleImage("checked.png"));
			showHelpNrBtn.setIcon(loadRescaleImage("soloHint.png"));
			showSolutionBtn.setIcon(loadRescaleImage("solved.png"));
			;
			autoFillPossibilityBtn.setIcon(loadRescaleImage("helpmode2.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Add Buttons to Panel
		optionsPanel.add(showWrongFieldBtn);
		optionsPanel.add(showHelpNrBtn);
		optionsPanel.add(autoFillPossibilityBtn);
		optionsPanel.add(showSolutionBtn);

		return optionsPanel;
	}

	/**
	 * Adds group of radio buttons to select game play mode
	 * 
	 * @param table hashtable with eventlisteners
	 * @return JPanel object containing the radio buttons
	 */
	private JPanel buildModeSelector(Hashtable<String, EventListener> table) {
		JPanel modePanel = new JPanel();
		modePanel.setLayout(new GridBagLayout());

		// Create radio buttons
		easyMode = new JRadioButton("Leicht");
		mediumMode = new JRadioButton("Mittel");
		hardMode = new JRadioButton("Schwer");

		// Set standard mode
		mediumMode.setSelected(true);

		// Add ActionListener
		ActionListener easyL = (ActionListener) table.get(ListenerKey.RBEASY.getKey());
		ActionListener mediumL = (ActionListener) table.get(ListenerKey.RBMEDIUM.getKey());
		ActionListener hardL = (ActionListener) table.get(ListenerKey.RBHARD.getKey());
		easyMode.addActionListener(easyL);
		mediumMode.addActionListener(mediumL);
		hardMode.addActionListener(hardL);

		// Group the radio buttons
		ButtonGroup group = new ButtonGroup();
		group.add(easyMode);
		group.add(mediumMode);
		group.add(hardMode);

		// Add Buttons to panel
		modePanel.add(easyMode);
		modePanel.add(mediumMode);
		modePanel.add(hardMode);

		return modePanel;
	}

	/**
	 * Loads image file from 'res' folder and scales it to icon size. Icon source:
	 * https://www.flaticon.com/packs/basic-ui-5/2
	 * 
	 * @param pathToImg path to image file with file type, e.g.
	 *                  '<code>fileName.jpeg</code>'
	 * @return scaled ImageIcon object
	 */
	private ImageIcon loadRescaleImage(String pathToImg) {
		Image img, imgScaled = null;

		try {
			img = ImageIO.read(getClass().getResource("/" + pathToImg));
			imgScaled = img.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ImageIcon(imgScaled);
	}

	/**
	 * Transmits the call to the ViewTimer
	 * 
	 * @param time elapsedTime
	 */
	public void doTick(int time) {
		timer.tick(time);
	}

	/**
	 * colors the buttons selected and in options and selects the
	 * difficulty from there
	 * 
	 * @param modelOptions
	 */
	public void update(ModelOptions options) {
		setDiffuclty(options.getDifficulty());
		setShowHelpNrActive(options.getShowHelpNr());
		setShowSolutionActive(options.getShowSolution());
		setShowWrongFieldActive(options.getShowWrongField());
	}
	
}