package test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.awt.Color;
import org.junit.Test;
import model.ConnectFourModel;
import model.Constants;
import model.HumanPlayer;
import model.Mode;
import model.Player;
import view.ConnectFourView;

public class ConnectFourObserverTest {
  private ConnectFourModel model = ConnectFourModel.getInstance();
  private ConnectFourView view = new ConnectFourView(model);
  
  @Test
  public void testShowWinner() {
    model.resetGame();
    Mode mode = Mode.TWO;
    Player player = new HumanPlayer();
    player.setColor(Color.red);
    model.startGame(mode);
    for (int i = 0; i < Constants.WIN; i++) {
      model.addMove(0, player);
    }
    assertThat(view.getMessage().toString(), containsString("red"));
  }
  
  @Test
  public void testMakeMove() {
    model.resetGame();
    Mode mode = Mode.TWO;
    Player player = new HumanPlayer();
    player.setColor(Color.red);
    model.startGame(mode);
    model.addMove(0, player);
    assertEquals(Color.red, view.getBoard()[Constants.ROWS - 1][0].getBackground());
  }
  
  @Test
  public void testColumnFull() {
    model.resetGame();
    Mode mode = Mode.TWO;
    Player player = new HumanPlayer();
    player.setColor(Color.red);
    model.startGame(mode);
    for (int i = 0; i <= Constants.ROWS; i++) {
      model.addMove(0, player);
    }
    assertThat(view.getMessage().toString(), containsString("full"));
  }
  

}
