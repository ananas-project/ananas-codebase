package ananas.fileworks;

public interface ComponentRegistrar {

	void register(Class<?> api, Class<?> aFactoryClass);

	ComponentFactory getFactory(Class<?> api);

	Component create(Context context, Class<?> api);

}
