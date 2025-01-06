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

		// Create a main panel with BoxLayout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		// Add a button to the GUI
		JPanel topButtonPanel = new JPanel();

		// Import stuff
		JTextField input = new JTextField(40);
        JButton importButton = new JButton("Import");
        JPanel importPanel = new JPanel();

        importPanel.add(input);
        importPanel.add(importButton);

        mainPanel.add(importPanel);

		importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = input.getText();
                if (inputText.length() != 81) {
                    JOptionPane.showMessageDialog(frame, "Input must be exactly 81 characters long!");
                    return;
                }

                int[][] board = new int[9][9];
                try {
                    for (int row = 0; row < 9; row++) {
                        for (int col = 0; col < 9; col++) {
                            char c = inputText.charAt(row * 9 + col);
                            if (c < '0' || c > '9') {
                                throw new NumberFormatException("Invalid character in input");
                            }
							board[row][col] = c - '0';
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Input must contain only digits from 0 to 9!");
                    return;
                }

                updateBoard(board);
            }
        });

		JLabel lbl = new JLabel("Select an Algorithm and then press SOLVE");
		lbl.setVisible(true);
	
		topButtonPanel.add(lbl);
	
		String[] choices = {"Backtracking", "Solve It Yourself"};
	
		final JComboBox<String> cb = new JComboBox<String>(choices);
	
		cb.setVisible(true);
		topButtonPanel.add(cb);

		mainPanel.add(topButtonPanel);

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
		mainPanel.add(gridPanel);  // Add to CENTER

		JButton button = new JButton("Solve");
		JButton resetButton = new JButton("Reset");

		JPanel bottomButtonPanel = new JPanel();

		button.setBounds(150, 200, 220, 50);
		bottomButtonPanel.add(button);
		bottomButtonPanel.add(resetButton);
		mainPanel.add(bottomButtonPanel);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] board = getBoard();

                if (!isValid(board)) {
                    JOptionPane.showMessageDialog(frame, "This board is invalid!\nMake sure you have entered all numbers correctly.");
                }
				else {
                    String selectedAlgorithm = (String) cb.getSelectedItem();
                    int[][] solvedBoard = null;

                    if (selectedAlgorithm.equals("Backtracking")) {
                        solvedBoard = Algorithm.solveWithBacktracking(board);
						if (solvedBoard != null) {
							updateBoard(solvedBoard);
						}
						else {
							JOptionPane.showMessageDialog(frame, "No solution found!");
						}
                    }
					else if (selectedAlgorithm.equals("Solve It Yourself")) {
                        JOptionPane.showMessageDialog(frame, "Feel free to start solving it at any time! ;)");
                    }
                }
            }
        });

		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int row = 0; row < 9; row++) {
					for (int col = 0; col < 9; col++) {
						TFS[row][col].setText(""); // Clear the text from the grid
					}
				}
				// More inport stuff
				input.setText(""); // Reset the import section
			}
		});

		frame.add(mainPanel);

		// TBH IDK Yet
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);
		frame.pack();
	}

	public void stringd(int[][] board) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				System.out.print(board[row][col]);
			}
		}
	}

	public void updateBoard(int[][] board) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				TFS[row][col].setText(""); // Clear before setting the value
				if (board[row][col] != 0) {
					TFS[row][col].setText(String.valueOf(board[row][col])); 
				}
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