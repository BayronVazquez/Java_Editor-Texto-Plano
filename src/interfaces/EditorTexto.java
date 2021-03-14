package interfaces;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class EditorTexto extends JFrame {

	private static final long serialVersionUID = 1L;
	private String TITLE = "Procesador de Texto Plano";
	private final int WIDTH  = 500;
	private final int HEIGHT = 700;
	private final int MIN_WIDTH  = 500;
	private final int MIN_HEIGHT = 700;
	private int FONT_SIZE = 16;
	
	private JFrame frame;
	private File archivo;
	private JTextArea textArea;
	private FileInputStream is;
	private FileOutputStream os;
	private JScrollPane scrollpane;

	/**
	 * Create the frame.
	 */
	public EditorTexto() {
		
		BorderLayout layout = new BorderLayout();
		
		JFileChooser filechooser = new JFileChooser();
		
		// Filtro
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Text File", "txt");
		filechooser.setFileFilter(filtro);
		
		frame = new JFrame();
		frame.setTitle(TITLE);
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension( MIN_WIDTH, MIN_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(layout);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("Archivo");
		menuBar.add(mnFile);
		
		JMenuItem mnItemOpen = new JMenuItem("Abrir Archivo");
		mnItemOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int status = filechooser.showOpenDialog(null);
				if( status == JFileChooser.APPROVE_OPTION) {
					archivo = filechooser.getSelectedFile();
					try {
						is = new FileInputStream(archivo.getAbsolutePath());
						byte[] buffer = new byte[ (int) archivo.length()];
						is.read(buffer, 0, (int)archivo.length());
						String txt = new String(buffer);
						textArea.setText(txt);
						textArea.setCaretPosition(0);
						is.close();
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(null,
								"No se encontro el archivo",
								"Error", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, e,
								"Error",
								JOptionPane.ERROR_MESSAGE);
					}
					}
				}
			});
		mnFile.add(mnItemOpen);
		
		JMenuItem mnItemClose = new JMenuItem("Cerrar Archivo");
		mnItemClose.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			textArea.setText("");
			archivo = null;
			}
		});
		mnFile.add(mnItemClose);
		
		JMenuItem mnItemSave = new JMenuItem("Guardar Archivo");
		mnItemSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if( archivo != null && archivo.canWrite()) {
						int option = JOptionPane.showConfirmDialog(null,
										"Deseas Guradar los cambios?", "Guardar",
										JOptionPane.OK_CANCEL_OPTION);
						if(option == JOptionPane.OK_OPTION) {
							System.out.println("entro");
							byte[] buffer = textArea.getText().getBytes();
							os = new FileOutputStream(archivo.getAbsolutePath());
							os.write(buffer);
							os.close();
						}
					}
				}catch(NullPointerException ex) {
					JOptionPane.showMessageDialog(null, ex, "error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, ex, "error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnFile.add(mnItemSave);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Consolas", Font.PLAIN, FONT_SIZE));
		textArea.setLineWrap(true);
		textArea.setEditable(true);
		scrollpane = new JScrollPane(textArea);
		frame.getContentPane().add(scrollpane, BorderLayout.CENTER);
		frame.setVisible(true);
	}

}
