package model;

import java.awt.Color;

/**
 * This class contains checker codes to check if a new move can result a win
 * or the board is full.
 */
public class Checker {
  /**
   * Check if the new move results in a column-win.
   * @param move the new Move object
   * @return true if there's a column-win
   */
  public boolean checkColumn(Move move, Color[][] board) {
    int count = 0;
    int row = move.getRow() - 1;
    int col = move.getColumn();
    Color c = move.getPlayer().getColor(); 
    while (row >= 0  && board[row][col] == c) {
      count++;
      row--;
    }
    row = move.getRow() + 1;
    while (row < Constants.ROWS  && board[row][col] == c) {
      count++;
      row++;
    }
    if (count == Constants.WIN - 1) {
      return true;
    }
    return false;
  }
  
  /**
   * Check if the new move results in row-win.
   * @param move the new Move object
   * @return true if there's a row-win
   */
  public boolean checkRow(Move move, Color[][] board) {
    int count = 0;
    int row = move.getRow();
    int col = move.getColumn() - 1;
    Color c = move.getPlayer().getColor(); 
    while (col >= 0  && board[row][col] == c) {
      count++;
      col--;
    }
    col = move.getColumn() + 1;
    while (col < Constants.COLUMNS  && board[row][col] == c) {
      count++;
      col++;
    }
    if (count == Constants.WIN - 1) {
      return true;
    }
    return false;

  }
  
  /**
   * Check if the new move results in a diagonal-win.
   * @param move the new Move object
   * @return true if there's a diagonal-win
   */
  public boolean checkDiagonal(Move move, Color[][] board) {
    int count = 0;
    int row = move.getRow() - 1;
    int col = move.getColumn() - 1;
    Color c = move.getPlayer().getColor(); 
    while (row >= 0 && col >= 0 && board[row][col] == c) {
      count++;
      row--;
      col--;
    }
    
    row = move.getRow() + 1;
    col = move.getColumn() + 1;
    
    while (row < Constants.ROWS && col < Constants.COLUMNS && board[row][col] == c) {
      count++;
      row++;
      col++;
    }
    if (count >= Constants.WIN - 1) {
      return true;
    }
    
    count = 0;
    row = move.getRow() + 1;
    col = move.getColumn() - 1;
    while (row < Constants.ROWS && col >= 0 && board[row][col] == c) {
      count++;
      row++;
      col--;
    }
    
    row = move.getRow() - 1;
    col = move.getColumn() + 1;
    
    while (row >= 0 && col < Constants.COLUMNS && board[row][col] == c) {
      count++;
      row--;
      col++;
    }
    if (count >= Constants.WIN - 1) {
      return true;
    }
    
    return false;
  }
  
  /**
   * Checks the new move results in a win.
   * @param move the new Move object
   * @return true if there's a diagonal-win
   */
  public boolean checkWin(Move move, Color[][] board) {
    if (checkRow(move, board) || checkColumn(move, board) || checkDiagonal(move, board)) {
      return true;
    }
    return false;

  }
  /**
   * Checks if the board is full.
   * @return true if the board is full
   */
  
  public boolean checkFull(Color[][] board) {
    for (int i = 0; i < Constants.ROWS; i++) {
      for (int j = 0; j < Constants.COLUMNS; j++) {
        if (board[i][j] == null) {
          return false;
        }
      }
    }
    return true;

  }
  
  
}
