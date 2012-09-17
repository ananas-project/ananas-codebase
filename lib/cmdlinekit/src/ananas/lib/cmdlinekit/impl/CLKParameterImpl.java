package ananas.lib.cmdlinekit.impl;

import ananas.lib.cmdlinekit.CLKMutableParameter;
import ananas.lib.cmdlinekit.CLKParameter;

class CLKParameterImpl implements CLKParameter, CLKMutableParameter {

	private final String mName;
	private String mValue;
	private String mDescription;
	private boolean mIsOption;

	public CLKParameterImpl(String name) {
		this.mName = name;
	}

	@Override
	public String getName() {
		return this.mName;
	}

	@Override
	public String getValue() {
		return this.mValue;
	}

	@Override
	public void setValue(String value) {
		this.mValue = value;
	}

	@Override
	public boolean isOption() {
		return this.mIsOption;
	}

	@Override
	public void setDescription(String description) {
		this.mDescription = description;
	}

	@Override
	public void setOption(boolean isOption) {
		this.mIsOption = isOption;
	}

	@Override
	public String getDescription() {
		return this.mDescription;
	}

}
