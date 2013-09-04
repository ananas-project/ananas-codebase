package ananas.fileworks.loader;

public interface XmlTreeTemplateLoaderFactory {

	TreeTemplateLoader createLoader(Class<?> ref, String filename);

}
