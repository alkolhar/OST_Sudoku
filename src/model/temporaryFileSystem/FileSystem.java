package model.temporaryFileSystem;

import java.util.ArrayList;
import model.ModelField;

public class FileSystem {
	private ArrayList<Memento> mementoList = new ArrayList<>();
	private final int noSaveData = -1;
	private int currentSavePosition = noSaveData; // Has the position in the mementoList of the last save

	/**
	 * @param Saves an Sudoku grid ModelField[9][9]. All the saves that were redo
	 *              will be deleted
	 */
	public void save(ModelField[][] grid) {
		currentSavePosition++;
		equalizePositionAndSizeOfList();
		mementoList.add(new Memento(grid));
	}

	/**
	 * @return Last saved Sudoku grid int[9][9]
	 * @throws NoSaveDataException if there are less than 2 Saves
	 */
	public ModelField[][] undo() throws NoSaveDataException {
		currentSavePosition--;
		if (currentSavePosition >= 0) {
			return mementoList.get(currentSavePosition).getState();
		} else {
			currentSavePosition++;
			throw new NoSaveDataException("No Data to undo");
		}
	}

	/**
	 * @return Last undo Sudoku grid int[9][9]
	 * @throws NoSaveDataException if there were no undos
	 */
	public ModelField[][] redo() throws NoSaveDataException {
		currentSavePosition++;
		if (currentSavePosition < mementoList.size()) {
			return mementoList.get(currentSavePosition).getState();
		} else {
			currentSavePosition--;
			throw new NoSaveDataException("No Data to redo");
		}
	}

	/**
	 * Delets all the saves in the List
	 */
	public void clear() {
		currentSavePosition = noSaveData;
		mementoList.clear();
	}

	/**
	 * If the users makes a change after a redo, all the saves that were redo will
	 * be deleted
	 */
	private void equalizePositionAndSizeOfList() {
		if (mementoList.size() > currentSavePosition) {
			for (int size = mementoList.size(); size > currentSavePosition; size--) {
				mementoList.remove(size - 1);
			}
		}
	}
}
