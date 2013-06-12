package ananas.fileworks.task;

import ananas.fileworks.Context;

public interface Task {

	String status_init = "init";
	String status_prepare = "prepare";
	String status_running = "running";
	String status_success = "success";
	String status_error = "error";
	String status_warning = "warning";

	String getStatus();

	TaskRunner getRunner();

	void setRunner(TaskRunner runner);

	Context getContext();

	void cancel();

	void start();

	Runnable getRunnable();

}
