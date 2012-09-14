package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;

public interface ISaveSupport {

	void save(OutputStreamWriter osw) throws IOException;

}
