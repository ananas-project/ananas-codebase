package ananas.fileworks.task;

import java.util.List;

public interface TaskRunner {

	List<Task> listUnfinished();

	void addTask(Task task);

	Task getCurrent();

}
