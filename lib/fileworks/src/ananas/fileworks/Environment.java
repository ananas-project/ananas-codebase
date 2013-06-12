package ananas.fileworks;

public interface Environment {

	ComponentManager getSingletonManager();

	ComponentRegistrar getComponentRegistrar();

}
