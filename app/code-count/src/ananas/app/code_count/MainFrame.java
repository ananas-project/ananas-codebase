package ananas.app.code_count;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9081364267380976824L;
	private JTextArea mTextOutput;
	private JTextField mTextPath;

	public MainFrame() {

	}

	public void init() {

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JTextField path = new JTextField();
		path.setText("/home/user");
		path.setEditable(false);
		panel.add(path, BorderLayout.CENTER);
		this.mTextPath = path;

		JButton browse = new JButton();
		browse.setText("Browse ...");
		panel.add(browse, BorderLayout.EAST);
		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.this.onClickBrowse();
			}
		});

		JTextArea output = new JTextArea();
		output.setText("123\n123\n123\n123\n123\n");
		output.setEditable(false);
		this.mTextOutput = output;

		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.SOUTH);
		this.add(output, BorderLayout.CENTER);
		this.setTitle("Code Count");

	}

	protected void onClickBrowse() {

		JFileChooser fc = new JFileChooser();
		int rlt = fc.showOpenDialog(null);
		if (rlt != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = fc.getSelectedFile();
		this.mTextPath.setText(file.getAbsolutePath());

	}
}
