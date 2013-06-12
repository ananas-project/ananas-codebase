package ananas.fileworks;

public interface ComponentRegistrar {

	void register(Class<?> api, Class<?> aClass);

	Class<?> getComponentClass(Class<?> api);

	Component createComponent(Class<?> api);

}
