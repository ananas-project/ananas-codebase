package ananas.lib.dtml.impl;

import ananas.lib.dtml.DTDirectory;
import ananas.lib.dtml.DTWorkspace;
import ananas.lib.io.vfs.VFile;

public class DTDirectoryImpl implements DTDirectory {

	private final DTWorkspace mWorkspace;
	private final VFile mFile;
	private final String mId;
	private final boolean mRequired;

	public DTDirectoryImpl(DTWorkspace workspace, String id, VFile file,
			boolean required) {
		this.mWorkspace = workspace;
		this.mFile = file;
		this.mId = id;
		this.mRequired = required;
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
		if (this.mFile.exists()) {
			return false;
		} else {
			this.mFile.mkdirs();
			return true;
		}
	}

	@Override
	public boolean check() {
		if (!this.mRequired) {
			return true;
		}
		return this.mFile.exists();
	}

	@Override
	public boolean repair() {
		this.mFile.mkdirs();
		return true;
	}

}
