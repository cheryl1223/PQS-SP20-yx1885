package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;
import model.ConnectFourModel;
import model.Constants;
import model.Mode;

/**
 * This ConnectFourView class contains the GUI codes and the action-event
 * on the GUI to start, play or exit the game. 
 * 
 */
public class ConnectFourView implements ConnectFourObserver, ActionListener {
  private ConnectFourModel model;
  private JFrame frame = new JFrame("Connect Four");
  private JPanel grid = new JPanel();
  private JPanel config = new JPanel();
  @Getter
  private JButton[][] board = new JButton[Constants.ROWS][Constants.COLUMNS];
  private JButton modeOne = new JButton("One Player");
  private JButton modeTwo = new JButton("Two Player");
  private JButton start = new JButton("Start");
  private JButton exit = new JButton("Exit");
  private JButton reset = new JButton("Reset");
  @Getter
  private JLabel message = new JLabel("To start, first select a mode then press start.");
  
  public ConnectFourView() {
    
  }
  
  /**
   * Construct the ConnectFourView and add the view to the model's observer.
   * @param model the ConnectFourModel for the game
   * 
   */
  public ConnectFourView(ConnectFourModel model) {
    this.model = model;
    this.model.addObserver(this);
    drawStartWindow();
  }
  
  /**
   * Draw the main window with board, message and configuration buttons.
   */
  private void drawStartWindow() {
    initializeBoard();
    message.setHorizontalAlignment(JLabel.CENTER);
    frame.add(message, BorderLayout.NORTH);
    message.setVisible(true);
    frame.add(BorderLayout.CENTER, grid);
    frame.add(BorderLayout.SOUTH, config);
    frame.setSize(600, 600);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
  
  /**
   * Draw the grid.
   */
  private void initializeBoard() {

    grid.setLayout(new GridLayout(Constants.ROWS, Constants.COLUMNS));
    for (int i = 0; i < Constants.ROWS; ++i) {
      for (int j = 0; j < Constants.COLUMNS; ++j) {
        JButton button = new JButton();
        button.addActionListener(this);
        grid.add(button);
        board[i][j] = button;
      }
    }
    config.add(modeOne);
    config.add(modeTwo);
    config.add(start);
    config.add(reset);
    config.add(exit);
    modeOne.addActionListener(this);
    modeTwo.addActionListener(this);
    start.addActionListener(this);
    exit.addActionListener(this);
    reset.addActionListener(this);

  }
  
  /**
   * The actions after click the buttons.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    // if no mode was set, the game won't start and the message
    // will inform the user to select a mode
    if (e.getSource() == start && model.getMode() == null) {
      message.setText("Please select a mode.");
    }
    // start the game if mode is set and click start
    if (e.getSource() == start && model.getMode() != null) {
      message.setText("Game Started!");
      model.startGame(model.getMode()); 
    }
    // exit the game if click exit
    if (e.getSource() == exit) {
      model.removeObserver(this);
      System.exit(0);
    }
    // reset the game if click reset
    if (e.getSource() == reset) {
      model.resetGame();
      frame.dispose();
      frame = new JFrame("Connect Four");
      grid = new JPanel();
      message = new JLabel("To start, first select a mode then press start.");
      board = new JButton[Constants.ROWS][Constants.COLUMNS];
      drawStartWindow();
    }
    // choose one player mode
    if (e.getSource() == modeOne) {
      model.setMode(Mode.ONE);
    }
    // choose two player mode
    if (e.getSource() == modeTwo) {
      model.setMode(Mode.TWO);
    }
    // click the grid buttons to add a new move after game started
    for (int i = 0; i < Constants.ROWS; i++) {
      for (int j = 0; j < Constants.COLUMNS; j++) {
        if (e.getSource() == board[i][j]) {
          message.setText("Selected column: " + (j + 1));
          message.setVisible(true);
          model.addMove(j, model.getCurrentPlayer());
          break;
        }
      }
    }
  }




  @Override
  public void showWinner() {
    message.setText("The winner is " + model.getCurrentPlayer().getColorName());
    for (int i = 0; i < Constants.ROWS; ++i) {
      for (int j = 0; j < Constants.COLUMNS; ++j) {
        board[i][j].removeActionListener(this);
      }
    }
  }


  @Override
  public void makeMove(int col) {
    int row = model.getCurrentRow(col);
    Color c = model.getCurrentPlayer().getColor();
    board[Constants.ROWS - row][col].setBackground(c);
    board[Constants.ROWS - row][col].setOpaque(true);
  }

  @Override
  public void columnFull(int col) {
    message.setText("Column " + (col + 1) + " is full.");
  }

  @Override
  public void boardFull() {
    message.setText("The board is full, game tied!");
  }



}
