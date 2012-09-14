package ananas.app.rfc_tw.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ananas.app.rfc_tw.event.IEventListener;

public interface IProject {

	String getOriginalText();

	void setOriginalText(String text);

	void addOriginalTextListener(IEventListener listener);

	void removeOriginalTextListener(IEventListener listener);

	IDictionary getDictionary();

	void load(InputStream is) throws IOException;

	void load(File file) throws IOException;

	void save(OutputStream os) throws IOException;

	void save(File file) throws IOException;

	public static class Factory {
		public static IProject newProject() {
			return new ImplProject();
		}
	}

}
