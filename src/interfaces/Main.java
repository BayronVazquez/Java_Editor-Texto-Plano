package interfaces;

import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorTexto frame = new EditorTexto();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
