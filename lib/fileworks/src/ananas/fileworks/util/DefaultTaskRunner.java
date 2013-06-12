package ananas.fileworks.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ananas.fileworks.Component;
import ananas.fileworks.task.Task;
import ananas.fileworks.task.TaskRunner;

public class DefaultTaskRunner implements TaskRunner, Component {

	private final List<Task> mTaskList;
	private Task mCurrent;
	private Runnable mRunnable;

	public DefaultTaskRunner() {
		this.mTaskList = new Vector<Task>();
	}

	@Override
	public List<Task> listUnfinished() {
		return new ArrayList<Task>(this.mTaskList);
	}

	@Override
	public void addTask(Task task) {
		this.mTaskList.add(task);
		if (this.mRunnable == null) {
			Runnable runn = new MyRunnable();
			(new Thread(runn)).start();
			this.__sleep(100);
		}
	}

	private void __sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Task getCurrent() {
		return this.mCurrent;
	}

	class MyRunnable implements Runnable {

		@Override
		public void run() {
			DefaultTaskRunner.this._sync_run(this);
		}
	}

	private synchronized void _sync_run(Runnable runn) {
		this.mRunnable = runn;

		for (int tout = 5000; tout > 0;) {
			Task task = this._getTask();
			if (task == null) {
				int interval = 100;
				this.__sleep(interval);
				tout -= interval;
			} else {
				this._runTask(task);
			}
		}

		this.mRunnable = null;
	}

	private void _runTask(Task task) {
		try {
			task.getRunnable().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Task _getTask() {

		if (this.mTaskList.size() > 0) {
			Task task = this.mTaskList.remove(0);
			this.mCurrent = task;
			return task;
		} else {
			return null;
		}

	}

}
