package model;

import view.ViewMain;

/**
 * class for measuring the playing time
 */
public class ModelSession implements Runnable {
	private ViewMain view;
	private int elapsedTime;
	private long diff;
	private long start;
	private long end;
	private long tick;

	public void setViewReference(ViewMain v) {
		view = v;
	}

	/**
	 * increment the elapsedTime and updates the view every second
	 */
	public void run() {
		try {
			while (true) {
				start = System.currentTimeMillis();

				tick = 1000 - diff;
				Thread.sleep(tick);
				elapsedTime++;
				view.doTick(elapsedTime);

				end = System.currentTimeMillis();
				diff = end - start - tick;
			}
		} catch (InterruptedException e) {
			setElapsedTime(0);
		}
	}

	/**
	 * Returns the elapsedTime
	 * 
	 * @return elapsedTime
	 */
	public int getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * Set the elapsedTime and updates the view
	 * 
	 * @param time elapsedTime
	 */
	public synchronized void setElapsedTime(int time) {
		elapsedTime = time;
		view.doTick(elapsedTime);
	}

}