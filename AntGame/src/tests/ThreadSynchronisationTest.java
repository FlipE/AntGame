/**
 * 
 */
package tests;

/**
 * ThreadSynchronisationTest.java
 * 
 * @author Chris B
 * @date 4 Apr 2013
 * @version 1.0
 */
public class ThreadSynchronisationTest {

	public static void main(String[] args) {
		ThreadSynchronisationTest test = new ThreadSynchronisationTest();
		test.run();
	}

	private synchronized void run() {
		ThreadedModel m = new ThreadedModel();
		ThreadedRenderer r = new ThreadedRenderer();

		m.setRenderer(r);
		r.setModel(m);

	}

	private class ThreadedModel implements Runnable {

		private int roundNum;
		private boolean isRendering;
		private ThreadedRenderer renderer;

		public ThreadedModel() {

			this.isRendering = false;
			this.roundNum = 0;

			new Thread(this).start();
		}

		@Override
		public void run() {
			while (true) {

				while (this.isRendering) {
					try {
						System.out.println("stuck");
						wait();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// notify the renderer that model is updating
				if (renderer != null) {
					this.renderer.setUpdating(true);
				}

				// update everything
				roundNum += 1;

				// notify renderer that model is done updating
				if (renderer != null) {
					this.renderer.setUpdating(false);
				}

				synchronized (this) {
					notify();
				}
			}
		}

		public synchronized int getRoundNum() {
			return roundNum;
		}

		public synchronized void setRendering(boolean isRendering) {
			this.isRendering = isRendering;
		}

		public synchronized void setRenderer(ThreadedRenderer renderer) {
			this.renderer = renderer;
		}

	}

	private class ThreadedRenderer implements Runnable {

		private boolean isUpdating;
		private ThreadedModel model;

		public ThreadedRenderer() {
			new Thread(this).start();
		}

		@Override
		public void run() {

			while (true) {

				while (isUpdating) {
					try {
						wait();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// notify the renderer that model is updating
				if (model != null) {
					this.model.setRendering(true);
				}

				// render everything
				if (model != null) {
					System.out.println(this.model.getRoundNum());
				}

				// notify renderer that model is done updating
				if (model != null) {
					this.model.setRendering(false);
				}
				synchronized (this) {
					notify();
				}
			}
		}

		public synchronized void setUpdating(boolean isUpdating) {
			this.isUpdating = isUpdating;
		}

		public synchronized void setModel(ThreadedModel model) {
			this.model = model;
		}

	}

	// http://gamedev.stackexchange.com/questions/44286/separate-update-and-render
	
}