package ananas.lib.cmdlinekit.command;

import ananas.lib.cmdlinekit.CLKCommand;
import ananas.lib.cmdlinekit.CLKElementsFactory;
import ananas.lib.cmdlinekit.CLKMutableParameterList;
import ananas.lib.cmdlinekit.CLKParameter;
import ananas.lib.cmdlinekit.CLKParameterList;
import ananas.lib.cmdlinekit.DefaultCLKElementsFactory;

public abstract class AbstractCLKCommand implements CLKCommand {

	private final CLKMutableParameterList mParamList;

	public AbstractCLKCommand() {
		CLKElementsFactory factory = DefaultCLKElementsFactory.getFactory();
		this.mParamList = factory.newParameterList();
	}

	protected void registerParameter(String name, String defaultValue,
			boolean option, String description) {
		CLKElementsFactory factory = DefaultCLKElementsFactory.getFactory();
		this.mParamList.addParameter(factory.newParameter(name, defaultValue,
				option, description));
	}

	protected String getParameterValue(CLKParameterList plist, String name) {

		String value = null;
		if (plist != null) {
			CLKParameter param = plist.getParameter(name);
			if (param != null) {
				value = param.getValue();
			}
		}
		if (value != null) {
			return value;
		}
		CLKParameterList mylist = this.getParameterList();
		CLKParameter param = mylist.getParameter(name);
		if (!param.isOption()) {
			throw new RuntimeException("no required parameter: '" + name + "'");
		}
		return param.getValue();
	}

	@Override
	public CLKParameterList getParameterList() {
		return this.mParamList;
	}

	@Override
	public String getHelpTitle() {
		return "(no title)";
	}

	@Override
	public String getHelpContent() {
		return "(no content)";
	}

}
