package ananas.app.code_count;

public class Main {

	public static void main(String[] arg) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainFrame mf = new MainFrame();
				mf.init();
				mf.setBounds(0, 0, 640, 320);
				mf.setVisible(true);
			}
		});

	}
}
