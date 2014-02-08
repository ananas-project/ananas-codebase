package ananas.tools.codespider;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4037921384202497235L;
	private List<String> _list;
	private JTextField _text_suffix_list;

	interface Command {

		String ok = "ok";
		String cancel = "cancel";
	}

	public void onInit(List<String> list) {

		this._list = list;

		this.setSize(480, 320);
		this.setTitle("Settings");

		JPanel panelMain = new JPanel();
		{
			panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
			JLabel label = new JLabel("file name suffix, split with char ';'");
			JTextField text = new JTextField();
			panelMain.add(label);
			panelMain.add(text);

			StringBuilder sb = new StringBuilder();
			for (String s : list) {
				sb.append(s);
				sb.append("; ");
			}
			text.setText(sb.toString());
			this._text_suffix_list = text;
		}
		JPanel panelBottom = new JPanel();
		{
			JButton btnOk = this.createButton("OK", Command.ok);
			JButton btnCancel = this.createButton("Cancel", Command.cancel);
			panelBottom.add(btnOk);
			panelBottom.add(btnCancel);
		}

		this.add(panelMain, BorderLayout.CENTER);
		this.add(panelBottom, BorderLayout.SOUTH);

	}

	private JButton createButton(String label, String cmd) {

		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				SettingsDialog.this.onClickCommand(cmd);
			}
		};
		JButton btn = new JButton(label);
		btn.setActionCommand(cmd);
		btn.addActionListener(listener);
		return btn;
	}

	protected void onClickCommand(String cmd) {

		if (cmd == null) {
		} else if (cmd.equals(Command.ok)) {
			this.onClickOK();
			this.setVisible(false);
		} else if (cmd.equals(Command.cancel)) {
			this.setVisible(false);
		} else {
		}
	}

	private void onClickOK() {

		this._list.clear();
		String str = this._text_suffix_list.getText();
		String[] array = str.split(";");
		for (String s : array) {
			s = s.trim();
			if (s.length() > 0) {
				this._list.add(s);
			}
		}

	}

}
