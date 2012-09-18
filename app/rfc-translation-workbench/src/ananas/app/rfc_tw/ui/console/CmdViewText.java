package ananas.app.rfc_tw.ui.console;

import java.util.HashMap;
import java.util.Map;

import ananas.app.rfc_tw.model.IDictionary;
import ananas.app.rfc_tw.model.IDictionaryItem;
import ananas.app.rfc_tw.model.IProject;
import ananas.app.rfc_tw.model.IWord;
import ananas.app.rfc_tw.model.IWordSet;
import ananas.lib.cmdlinekit.CLKCommand;
import ananas.lib.cmdlinekit.CLKExecuteContext;
import ananas.lib.cmdlinekit.CLKParameterList;

public class CmdViewText implements CLKCommand {

	private final Map<String, Runnable> mSubCmd = new HashMap<String, Runnable>();

	public CmdViewText() {
		this._initSubCmds();
	}

	private void _initSubCmds() {

		this.mSubCmd.put("original", new Runnable() {

			@Override
			public void run() {
				IProject prj = ConsoleCore.Agent.getCore().getProject();
				String text = prj.getDocument().getOriginal().getText();
				System.out.println("original text = [" + text + "]");
			}
		});

		this.mSubCmd.put("words", new Runnable() {

			@Override
			public void run() {
				IProject prj = ConsoleCore.Agent.getCore().getProject();
				IWordSet words = prj.getDocument().getWordSet();
				IWord[] all = words.allWords();
				for (IWord w : all) {
					String text = w.getText();
					System.out.println(text);
				}
				System.out.println("total: " + all.length + " words");
			}
		});

		this.mSubCmd.put("dict", new Runnable() {

			@Override
			public void run() {
				IProject prj = ConsoleCore.Agent.getCore().getProject();
				IDictionary dict = prj.getDocument().getDictionary();
				IDictionaryItem[] items = dict.allItems();
				for (IDictionaryItem item : items) {
					String key = item.key();
					String value = item.value();
					System.out.println(key + ":" + value + ";");
				}
				System.out.println("total: " + items.length + " items");
			}
		});

	}

	@Override
	public String getName() {
		return "show";
	}

	@Override
	public String getHelpTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHelpContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(CLKExecuteContext context) {
		String id = context.getParameterList().getParameter("id").getValue();
		Runnable subcmd = this.mSubCmd.get(id);
		subcmd.run();
	}

	@Override
	public CLKParameterList getParameterList() {
		// TODO Auto-generated method stub
		return null;
	}

}
