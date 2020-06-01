package view;

import model.Move;

/**
 * This ConnectFourObserver interface contains methods that the
 * model will call to notify a change in the view.
 * 
 */
public interface ConnectFourObserver {
  /**
   * To notify that there's a winner and game over.
   */
  void showWinner();
  
  /**
   * To notify that a new move is added to a column.
   * @param col the column to add the new move 
   */
  void makeMove(int col);
  
  /**
   * To notify that a column is full.
   * @param col the column that is full
   */
  void columnFull(int col);
  
  /**
   * To notify that the board is full and the game tied.
   */
  void boardFull();
}
