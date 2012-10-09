package ananas.lib.io;

public interface IContentConnection extends IStreamConnection {

	String getType();

	long getLength();

	String getEncoding();

}
