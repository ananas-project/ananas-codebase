package ananas.lib.cmdlinekit.impl;

import java.util.Hashtable;

import ananas.lib.cmdlinekit.CLKCommand;
import ananas.lib.cmdlinekit.CLKCommandSet;
import ananas.lib.cmdlinekit.CLKMutableCommandSet;
import ananas.lib.cmdlinekit.command.TheExitCommand;
import ananas.lib.cmdlinekit.command.TheHelpCommand;


class CLKCommandSetImpl implements CLKCommandSet, CLKMutableCommandSet {

	private final Hashtable<String, CLKCommand> mTable;

	public CLKCommandSetImpl() {
		this.mTable = new Hashtable<String, CLKCommand>();
		this.addCommand(new TheHelpCommand());
		this.addCommand(new TheExitCommand());
	}

	@Override
	public void addCommand(CLKCommand cmd) {
		if (cmd != null) {
			String name = cmd.getName();
			if (name != null) {
				this.mTable.put(name, cmd);
			}
		}
	}

	@Override
	public void removeCommand(CLKCommand cmd) {
		if (cmd != null) {
			String name = cmd.getName();
			if (name != null) {
				this.mTable.remove(name);
			}
		}
	}

	@Override
	public CLKCommand getCommand(String name) {
		return this.mTable.get(name);
	}

	@Override
	public CLKCommand[] listCommands() {
		return this.mTable.values().toArray(new CLKCommand[0]);
	}

}
