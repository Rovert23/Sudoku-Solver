import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JFormattedTextField TFS[][] = new JFormattedTextField[9][9];
	private Font f = new Font("serif", Font.PLAIN, 30);

	public void createMainGUI() {
		
		// Create an instance of JFrame
		JFrame frame = new JFrame("Sudoku Solver");

		JPanel gridPanel = new JPanel();

		GridLayout GL = new GridLayout(9,9);
		gridPanel.setLayout(GL);

		int x = 50, y = 50;

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				JFormattedTextField TF = new JFormattedTextField();
				TF.setFont(f);
				TF.setPreferredSize(new Dimension(x, y));

				// Add border - thicker for 3x3 block boundaries
				// Ternary Operation (?) (Basically an if-else statement)
				int top = (row % 3 == 0 && row != 0) ? 4 : 1;
				int left = (col % 3 == 0 && col != 0) ? 4 : 1;
				TF.setBorder(new MatteBorder(top, left, 1, 1, Color.BLACK));

				TF.setHorizontalAlignment(JTextField.CENTER);


				// This code block is used to make sure only one single integer can be placed inside each box (tbh idk how it works)
				// WARNING THIS CODE BLOCK MAY BE HIGHLY CONFUSING!!!
				//---------------------------------------------------------------------------------------------------------------------------------
                ((AbstractDocument) TF.getDocument()).setDocumentFilter(new DocumentFilter() {
                    @Override
                    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                        if (TF.getText().length() == 0 && text.matches("[0-9]")) {
                            super.replace(fb, offset, length, text, attrs);
                        } else if (text.isEmpty()){
                            super.replace(fb, offset, length, text, attrs);
                        }
                    }
                });
				// --------------------------------------------------------------------------------------------------------------------------------


				gridPanel.add(TF);
				TFS[row][col] = TF;
			}
		}

		gridPanel.setPreferredSize(new Dimension(x * 9, y * 9)); // Preferred size for the grid
		frame.add(gridPanel);  // Add to CENTER




	










		// Add a button to the GUI
		JPanel buttonPanel = new JPanel();



		JLabel lbl = new JLabel("Select an Algorithm and then press SOLVE");
		lbl.setVisible(true);
	
		buttonPanel.add(lbl);
	
		String[] choices = {"DIY (Do It Yourself)", "Backtracking","Use A Human", "Random","Speed Deamon"};
	
		final JComboBox<String> cb = new JComboBox<String>(choices);
	
		cb.setVisible(true);
		buttonPanel.add(cb);





		JButton button = new JButton("Solve");

		button.setBounds(150, 200, 220, 50);
		buttonPanel.add(button);
		frame.add(buttonPanel);














		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = ""; // Get the text
				for (int row = 0; row < 9; row++) {
					for (int col = 0; col < 9; col++) {
						if (TFS[row][col].getText() != null) {
							text += TFS[row][col].getText(); // Append the number
						}
						else {
							text += "0"; // Append spaces for empty cells (or "0 " if empty=0)
						}
					}
				}
				if (text.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Text field is empty!");
				} else {
					JOptionPane.showMessageDialog(frame, "The text is: " + text);
				}
			}
		});

		// Set the GUI size
		frame.setSize(x*9+20, y*9+90);

		// TBH IDK Yet
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);

	}
}