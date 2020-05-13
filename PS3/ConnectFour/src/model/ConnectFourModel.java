package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.Setter;
import view.ConnectFourObserver;

/**
 * This class is the ConnectFourModel class which records the game states,
 * contains the game logic and the computer opponent logic.
 */
public class ConnectFourModel {
  @Getter @Setter
  private Color[][] board;
  private Player player1;
  private Player player2;
  @Getter
  private Player currentPlayer;
  @Getter @Setter
  private Mode mode;
  private static Checker checker = new Checker();
  private static ConnectFourModel singleton = null;

  private List<ConnectFourObserver> observers = new ArrayList<>();
  
  private ConnectFourModel(){
    
  }  
  
  /**
   * Singleton pattern to allow only one ConnectFourModel object.
   * @return singleton
   */
  public static ConnectFourModel getInstance() {
    if (singleton == null) {
      singleton = new ConnectFourModel();
    } 
    return singleton;
  }
  
  /**
   * Observer pattern to add a new observer to model.
   * @param observer the ConnectFourObserver object to add to the model
   * @return true if a new observer is added
   */  
  public boolean addObserver(final ConnectFourObserver observer) {
     return observers.add(observer);
  }
  
  /**
   * to remove a new observer from model.
   * @param observer the ConnectFourObserver object to remove from the model
   * @return true if observer is removed
   */  
  public boolean removeObserver(final ConnectFourObserver observer) {
    return observers.remove(observer);
  }
  
  /**
   * Reset the game and remove all game states information.
   */ 
  public void resetGame() {
    board = new Color[Constants.ROWS][Constants.COLUMNS];
    player1 = null;
    player2 = null;
    currentPlayer = null;
    mode = null;
  }
  
  /**
   * Start the game to initialize player, board according to mode.
   * @param mode the game mode (ONE: single player, TWO: two player)
   */  
  public void startGame(final Mode mode) {
    this.mode = mode;
    if (mode == Mode.ONE) {
      this.player1 = new HumanPlayer();
      player1.setColor(Color.RED);
      this.player2 = new ComputerPlayer();
      player2.setColor(Color.blue);
    } else if (mode == Mode.TWO) {
      this.player1 = new HumanPlayer();
      player1.setColor(Color.RED);
      this.player2 = new HumanPlayer();
      player2.setColor(Color.blue);
    }
    this.board = new Color[Constants.ROWS][Constants.COLUMNS];
    this.currentPlayer = player1;
  }
  
  /**
   * Compute the row index that is not occupied for a column.
   * @param col the column to check
   * @return int the row index that is not occupied for a column
   */
  public int getCurrentRow(final int col) {
    int row = 0;
    while (row < Constants.ROWS) {
      if (board[row][col] == null) {
        return row;
      }
      row++;
    }
    return row;
  }
  
  /**
   * Add a move to column for a player. If next player is the computer opponent,
   * the computer makes a move automatically.
   * @param col the column to add move
   * @param player the player that added the move
   */
  public void addMove(final int col, final Player player) {
    int row = getCurrentRow(col);
    if (row == Constants.ROWS) {
      notifyColumnFull(col);
      return;
    } else {
      Move move = new Move(row, col, player);
      board[row][col] = player.getColor();
      notifyMakeMove(col);
      if (checker.checkWin(move, board)) {
        notifyShowWinner();
      } else if (checker.checkFull(board)) {
        notifyBoardFull();
      } else {
        switchPlayer();
        if (currentPlayer.getPlayerType() == PlayerType.COMPUTER) {
          computerMakeMove();
        }
      }
      
    }
  }
  
  /**
   * a computer opponent that looks ahead a single move,
   * and makes that move if it results in a win. If not, 
   * choose a column with highest score. If the score is
   * 0, choose a random column.
   */
  public void computerMakeMove() {
    int maxScore = 0;
    int maxScoreCol = 0;
    for (int col = 0; col < Constants.COLUMNS; col++) {
      int row = getCurrentRow(col);
      if (row == Constants.ROWS) {
        continue;
      }
      Move move = new Move(row, col, currentPlayer);
      if (checker.checkWin(move, board)) {
        board[row][col] = currentPlayer.getColor();
        notifyMakeMove(col);
        notifyShowWinner();
        return;
      }
      int moveScore = getMoveScore(move, currentPlayer);
      if (moveScore >= maxScore) {
        maxScore = moveScore;
        maxScoreCol = col;
      }
    }
    if (maxScore == 0) {
      maxScoreCol = ThreadLocalRandom.current().nextInt(0, Constants.COLUMNS);
    }
    addMove(maxScoreCol, currentPlayer);

  }
  
  /**
   * Compute the score for a move. The score is the count of 
   * moves which have the same color as player and is horizontally, 
   * Vertically or diagonally adjacent to the move.
   * @param move the move to compute score
   * @param player the player to add the move
   * @return int the score for the move
   */
  public int getMoveScore(final Move move, final Player player) {
    int score = 0;
    int row = move.getRow() + 1;
    int col = move.getColumn() + 1;    
    while (row < Constants.ROWS) {
      if (board[row][move.getColumn()] == player.getColor()) {
        score += 1;
      } else {
        break;
      }
      row++;
    }
    while (col < Constants.COLUMNS) {
      if (board[move.getRow()][col] == player.getColor()) {
        score += 1;
      } else {
        break;
      }
      col++;
    }
    row = move.getRow() - 1;
    col = move.getColumn() - 1;    
    while (row >= 0) {
      if (board[row][move.getColumn()] == player.getColor()) {
        score += 1;
      } else {
        break;
      }
      row--;
    }
    while (col >= 0) {
      if (board[move.getRow()][col] == player.getColor()) {
        score += 1;
      } else {
        break;
      }
      col--;
    }
    row = move.getRow() + 1;
    col = move.getColumn() + 1;
    while (row < Constants.ROWS && col < Constants.COLUMNS) {
      if (board[row][col] == player.getColor()) {
        score += 1;
      } else {
        break;
      }
      row++;
      col++;
    }
    row = move.getRow() - 1;
    col = move.getColumn() - 1;
    while (row >= 0 && col >= 0) {
      if (board[row][col] == player.getColor()) {
        score += 1;
      } else {
        break;
      }
      row--;
      col--;
    }
    row = move.getRow() + 1;
    col = move.getColumn() - 1;
    while (row < Constants.ROWS && col >= 0) {
      if (board[row][col] == player.getColor()) {
        score += 1;
      } else {
        break;
      }
      row++;
      col--;
    }
    row = move.getRow() - 1;
    col = move.getColumn() + 1;
    while (row >= 0 && col < Constants.COLUMNS ){
      if (board[row][col] == player.getColor()) {
        score += 1;
      } else {
        break;
      }
      row--;
      col++;
    }
    return score;
  }

    
  private void switchPlayer() {
    if (currentPlayer == player1) {
      currentPlayer = player2;

    } else {
      currentPlayer = player1; 
    }
  }

  
  private void notifyBoardFull() {
    for (ConnectFourObserver o : observers) {
      o.boardFull();
    }
  }

  private void notifyShowWinner() {
    for (ConnectFourObserver o : observers) {
      o.showWinner();
    }
  }

  private void notifyMakeMove(int col) {
    for (ConnectFourObserver o : observers) {
      o.makeMove(col);
    }
  }

  private void notifyColumnFull(int col) {
    for (ConnectFourObserver o : observers) {
      o.columnFull(col);
    }
  }

  
}
