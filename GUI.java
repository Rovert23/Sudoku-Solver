import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
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
	
		String[] choices = {"Backtracking", "DIY (Do It Yourself)", "Use A Human", "Speed Deamon"};
	
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
                int[][] board = getBoard();
                String selectedAlgorithm = (String) cb.getSelectedItem();
                int[][] solvedBoard = null;

				if (!isValid(board)) {
					JOptionPane.showMessageDialog(frame, "This board is inValid!");
				}
				else {
					// YOU NEED TO FINISH THIS SECTION BY ADDING IN ALL OF THE OTHER ALGORITHMS
					if (selectedAlgorithm.equals("Backtracking")) {
						solvedBoard = Algorithm.solveWithBacktracking(board);
					} else if (selectedAlgorithm.equals("Dancing Links")) {
						solvedBoard = Algorithm.solveWithDancingLinks(board);
					}
	
	
					if (solvedBoard != null) {
						updateBoard(solvedBoard);
					} else {
						JOptionPane.showMessageDialog(frame, "No solution found!");
					}
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

	public void updateBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TFS[row][col].setText(String.valueOf(board[row][col]));
            }
        }
    }

	public int[][] getBoard() {
		int[][] board = new int[9][9];
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				String text = TFS[row][col].getText();
				if (text != null && !text.isEmpty()) {
					board[row][col] = Integer.parseInt(text);
				} else {
					board[row][col] = 0;
				}
			}
		}

		return board;
	}

	public boolean isSolved(int[][] board) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (board[row][col] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isValid(int[][] board) {
		// Create a HashSet to store the unique elements
		Set<Integer> rowCheck;
		Set<Integer> colCheck;
		Set<Integer> boxCheck;
	
		// Iterate through each row
		for (int row = 0; row < 9; row++) { 
			rowCheck = new HashSet<>(); 
			for (int col = 0; col < 9; col++) {
				if (board[row][col] != 0 && !rowCheck.add(board[row][col])) { 
					return false; 
				}
			}
		}
	
		// Check columns
		for (int col = 0; col < 9; col++) {
			colCheck = new HashSet<>();
			for (int row = 0; row < 9; row++) {
				if (board[row][col] != 0 && !colCheck.add(board[row][col])) {
					return false;
				}
			}
		}
	
		// Check 3x3 subgrids
		for (int boxRow = 0; boxRow < 3; boxRow++) {
			for (int boxCol = 0; boxCol < 3; boxCol++) {
				boxCheck = new HashSet<>();
				for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
					for (int col = boxCol * 3; col < boxCol * 3 + 3; col++) {
						if (board[row][col] != 0 && !boxCheck.add(board[row][col])) {
							return false;
						}
					}
				}
			}
		}
	
		return true;
	}
}