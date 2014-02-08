package ananas.tools.codespider;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileFilter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3518619142035592829L;

	public static void run() {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainFrame mf = new MainFrame();
				mf.onInit();
				mf.setVisible(true);
			}
		});

	}

	interface Menu {

		String open = "open";

	}

	private JFileChooser _file_chooser;
	private JTextArea _output;

	protected void onInit() {

		this.setTitle("Code Spider");
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextArea text = new JTextArea();
		text.setEditable(false);
		this._output = text;
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(text);
		this.add(scroll);

		this.setJMenuBar(this.createMenuBar());

	}

	private JMenuBar createMenuBar() {
		JMenuBar mb = new JMenuBar();

		{
			JMenu menu = new JMenu("File");
			mb.add(menu);
			menu.add(this.createMenuItem(Menu.open));
		}

		return mb;
	}

	private JMenuItem createMenuItem(String name) {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 7558096058642305331L;

			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.onClickMenuItem(e);
			}
		};
		JMenuItem item = new JMenuItem(action);
		item.setName(name);
		item.setText(name);
		item.setActionCommand(name);
		return item;
	}

	protected void onClickMenuItem(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (Menu.open.equals(cmd)) {
			this.onClickOpen();
		}
	}

	private void onClickOpen() {

		JFileChooser fc = this._file_chooser;
		if (fc == null) {
			fc = new JFileChooser();
			this._file_chooser = fc;

			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		int rlt = fc.showOpenDialog(this);
		if (rlt != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File path = fc.getSelectedFile();
		this._output.setText(path.getAbsolutePath());

		this.open(path);
	}

	private void open(File path) {

		FileFilter filter = new FileFilter() {

			@Override
			public boolean accept(File path) {

				if (path.isDirectory()) {
					return true;
				} else {
					String name = path.getName();
					return name.endsWith(".java");
				}
			}
		};

		ResultSet h = new ResultSet();

		FileFinder ff = new FileFinder();
		ff.find(path, filter, h);

		this._output.setText(h.output);
		this._output.setSelectionStart(0);
		this._output.setSelectionEnd(0);

	}

}
