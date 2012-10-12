package ananas.app.roadmap.util;

import java.util.Vector;

public interface TaskRunner {

	void addTask(Task task);

	public static class Factory {

		public static TaskRunner newInstance() {
			return new MyImpl();
		}
	}

	static class MyImpl implements TaskRunner {

		private final Vector<Task> mTaskFIFO;

		public MyImpl() {
			this.mTaskFIFO = new Vector<Task>();
		}

		@Override
		public void addTask(Task task) {
			this.mTaskFIFO.addElement(task);
			if (true) {
				Thread thd = (new Thread() {
					public void run() {
						MyImpl.this._run();
					}
				});
				thd.start();
			}
		}

		private synchronized void _run() {
			for (;;) {
				if (this.mTaskFIFO.isEmpty()) {
					break;
				}
				Task task = this.mTaskFIFO.firstElement();
				this.mTaskFIFO.remove(task);
				this._runTask(task);
			}
		}

		private void _runTask(Task task) {
			System.out.println("run task (begin) " + task);
			try {
				task.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("run task (end) " + task);
		}

	}

}
