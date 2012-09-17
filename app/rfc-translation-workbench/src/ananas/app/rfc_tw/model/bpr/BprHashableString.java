package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class BprHashableString extends BprObjectBase {

	private String mEncoding;
	private String mSHA1;
	private final List<BprBytes> mBytes;
	private String mText;

	public BprHashableString() {
		this.mBytes = new ArrayList<BprBytes>();
	}

	@Override
	public boolean bind(Object child) {
		if (child == null) {
			return false;
		} else  if ( child instanceof String  ) {
			this.mText =(String) child;
			return true;
		} else {
			return super.bind(child);
		}
	}

	public void setBprBytes(BprBytes bytes) {
		this.mBytes.add(bytes);
	}

	public void setSha1(String sha1) {
		this.mSHA1 = sha1;
	}

	public void setEncoding(String enc) {
		this.mEncoding = enc;
	}

	@Override
	public void onSaveBegin(OutputStreamWriter osw) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveContent(OutputStreamWriter osw) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEnd(OutputStreamWriter osw) throws IOException {
		// TODO Auto-generated method stub

	}

}
