package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.awt.Color;
import org.junit.Test;
import model.ConnectFourModel;
import model.Constants;
import model.HumanPlayer;
import model.Mode;
import model.Move;
import model.Player;
import model.PlayerType;
import view.ConnectFourObserver;
import view.ConnectFourView;

public class ConnectFourModelTest {
  private ConnectFourModel model = ConnectFourModel.getInstance();
  //private ConnectFourView view = new ConnectFourView(model);
  
  @Test
  public void testStartGame() {
    Mode mode = Mode.TWO;
    model.startGame(mode);
    assertNotEquals(null, model.getCurrentPlayer());
  }
  
  @Test
  public void testResetGame() {
    Mode mode = Mode.ONE;
    model.startGame(mode);
    assertNotEquals(null, model.getCurrentPlayer());
    model.resetGame();
    assertEquals(null, model.getCurrentPlayer());
  }
  
  @Test
  public void testGetCurrentRow() {
    Mode mode = Mode.TWO;
    Player player = new HumanPlayer();
    player.setColor(Color.black);
    model.startGame(mode);
    model.addMove(0, player);
    assertEquals(1, model.getCurrentRow(0));
  }  
  
  @Test
  public void testAddMove() {
    Mode mode = Mode.ONE;
    Player player = new HumanPlayer();
    player.setColor(Color.black);
    model.startGame(mode);
    model.addMove(0, player);
    assertEquals(Color.black, model.getBoard()[0][0]);
    
  }
  
  @Test
  public void testAddMoveToColumnFull() {
    Mode mode = Mode.TWO;
    Player player = new HumanPlayer();
    player.setColor(Color.black);
    model.startGame(mode);
    for (int i = 0; i <= Constants.ROWS; i++) {
      model.addMove(0, player);
    }
    assertEquals(Color.black, model.getBoard()[0][0]);
    
  }

  @Test
  public void testComputerMakeMove() {
    Mode mode = Mode.ONE;
    model.startGame(mode);
    model.addMove(0, model.getCurrentPlayer());
    assertEquals(Color.red, model.getBoard()[0][0]);
    assertEquals(PlayerType.HUMAN, model.getCurrentPlayer().getPlayerType());

  }
  
  @Test
  public void testGetMoveScore() {
    Mode mode = Mode.TWO;
    model.startGame(mode);
    Player player = new HumanPlayer();
    player.setColor(Color.black);
    model.addMove(0, player);
    Move move = new Move(1, 0, player);
    assertEquals(1, model.getMoveScore(move, player));

  }
  
  @Test
  public void testAddAndRemoveObserver() {
    ConnectFourObserver observer = new ConnectFourView();
    assertEquals(false, model.removeObserver(observer));
    assertEquals(true, model.addObserver(observer));
    assertEquals(true, model.removeObserver(observer));

  }
  
}
