package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;

public interface ISaveSupportEx {

	void onSaveBegin(OutputStreamWriter osw) throws IOException;

	void onSaveContent(OutputStreamWriter osw) throws IOException;

	void onSaveEnd(OutputStreamWriter osw) throws IOException;

}
