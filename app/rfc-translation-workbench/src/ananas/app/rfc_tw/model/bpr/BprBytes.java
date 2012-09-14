package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class BprBytes extends BprObjectBase {

	private final StringBuilder mTextBuffer;
	private byte[] mBytes;

	public BprBytes() {
		this.mTextBuffer = new StringBuilder();
	}

	@Override
	public boolean bind(Object child) {
		if (child == null) {
			return false;
		} else if (child instanceof String) {
			this.mTextBuffer.append((String) child);
			return true;
		} else {
			return false;
		}
	}

	public void setBytes(byte[] buf, int offset, int count) {
		byte[] ba = new byte[count];
		for (int i = count - 1; i >= 0; i--) {
			ba[i] = buf[offset + i];
		}
		this.mBytes = ba;
	}

	public byte[] getBytes() {
		return this.mBytes;
	}

	public byte[] base64ToBytes() {
		return null;
	}

	public String bytesToBase64() {
		return null;
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
