package ananas.lib.dtml.impl;

import java.io.IOException;

import ananas.lib.dtml.DTFile;
import ananas.lib.dtml.DTWorkspace;
import ananas.lib.io.vfs.VFile;

public class DTFileImpl implements DTFile {

	private final DTWorkspace mWorkspace;
	private final String mId;
	private final VFile mFile;
	private final boolean mIsRequired;

	public DTFileImpl(DTWorkspace workspace, String id, VFile file,
			boolean required) {
		this.mWorkspace = workspace;
		this.mId = id;
		this.mFile = file;
		this.mIsRequired = required;
	}

	@Override
	public DTWorkspace getWorkspace() {
		return this.mWorkspace;
	}

	@Override
	public VFile getFile() {
		return this.mFile;
	}

	@Override
	public String getId() {
		return this.mId;
	}

	@Override
	public boolean init() {
		if (!this.mFile.exists()) {
			try {
				this.mFile.createNewFile();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean check() {
		if (!this.mIsRequired) {
			return true;
		}
		if (this.mFile.exists()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean repair() {
		if (!this.mFile.exists()) {
			try {
				this.mFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

}
