package test;

import static org.junit.Assert.*;
import java.awt.Color;
import org.junit.Test;
import model.ComputerPlayer;
import model.HumanPlayer;
import model.Player;

public class PlayerTest {

  @Test
  public void testGetColorNameRed() {
    Player player = new HumanPlayer();
    player.setColor(Color.red);
    assertEquals("red", player.getColorName());
    player = new ComputerPlayer();
    player.setColor(Color.red);
    assertEquals("red", player.getColorName());
  }
  @Test
  public void testGetColorNameBlue() {
    Player player = new HumanPlayer();
    player.setColor(Color.blue);
    assertEquals("blue", player.getColorName());
    player = new ComputerPlayer();
    player.setColor(Color.blue);
    assertEquals("blue", player.getColorName());
  }
  @Test
  public void testGetColorNameNull() {
    Player player = new HumanPlayer();
    player.setColor(Color.yellow);
    assertEquals(null, player.getColorName());
    player = new ComputerPlayer();
    player.setColor(Color.yellow);
    assertEquals(null, player.getColorName());
  }
}
