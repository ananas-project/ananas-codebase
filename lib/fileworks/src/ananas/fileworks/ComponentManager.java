package ananas.fileworks;

public interface ComponentManager {

	Component get(Class<?> api, String name);

	boolean put(Class<?> api, String name, Component comp);

	boolean declare(Class<?> api, String name, boolean isFinal);

	boolean hasDeclare(Class<?> api, String name);
}
