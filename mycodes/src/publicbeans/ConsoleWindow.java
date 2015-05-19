package publicbeans;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsoleWindow {

	public static void init() {
		JFrame frame = new JFrame();
		frame.setTitle("LogWindow");
		frame.setSize(400, 500);
		final JTextArea textarea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textarea);
		frame.add(scrollPane);
		frame.setLocation(450, 100);
		textarea.setBackground(Color.black);
		textarea.setEnabled(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		PrintStream consoleStream = new PrintStream(new OutputStream() {

			@Override
			public void write(byte[] b, int off, int len) {
				textarea.append(new String(b, off, len));

			}

			@Override
			public void write(int b) throws IOException {
				// TODO Auto-generated method stub

			}
		});
		System.setOut(consoleStream);
		System.setErr(consoleStream);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
