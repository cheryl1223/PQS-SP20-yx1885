package test;

import static org.junit.Assert.*;
import java.awt.Color;
import model.Checker;
import model.Constants;
import model.HumanPlayer;
import model.Move;
import model.Player;
import org.junit.Test;

public class CheckerTest {
  private Checker checker = new Checker();
  private Color[][] board;
  private Move move;
  private Player player;
  private final Color c = Color.red; 

  @Test
  public void testCheckRow() {
    board = new Color[Constants.ROWS][Constants.COLUMNS];
    for (int i = 1; i < Constants.WIN; i++) {
      board[0][i] =  c;
    }
    player = new HumanPlayer();
    player.setColor(c);
    move = new Move(0, 0, player);
    assertEquals(true, checker.checkRow(move, board));
  }
  
  @Test
  public void testCheckColumn() {
    board = new Color[Constants.ROWS][Constants.COLUMNS];
    for (int i = 1; i < Constants.WIN; i++) {
      board[i][0] =  c;
    }
    player = new HumanPlayer();
    player.setColor(c);
    move = new Move(0, 0, player);
    assertEquals(true, checker.checkColumn(move, board));
  }
  
  @Test
  public void testCheckDiagonal() {
    board = new Color[Constants.ROWS][Constants.COLUMNS];
    for (int i = 1; i < Constants.WIN; i++) {
      board[i][i] =  c;
    }
    player = new HumanPlayer();
    player.setColor(c);
    move = new Move(0, 0, player);
    assertEquals(true, checker.checkDiagonal(move, board));
  }
  
  @Test
  public void testWin() {
    board = new Color[Constants.ROWS][Constants.COLUMNS];
    for (int i = 1; i < Constants.WIN; i++) {
      board[i][i] =  c;
    }
    player = new HumanPlayer();
    player.setColor(c);
    move = new Move(0, 0, player);
    assertEquals(true, checker.checkWin(move, board));
  }
  
  @Test
  public void testBoardFull() {
    board = new Color[Constants.ROWS][Constants.COLUMNS];
    for (int i = 0; i < Constants.ROWS; i++) {
      for (int j = 0; j < Constants.COLUMNS; j++) {
        board[i][j] = c;
      }
    }
    assertEquals(true, checker.checkFull(board));
  }
}
