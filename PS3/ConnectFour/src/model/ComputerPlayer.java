package model;

import java.awt.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the ComputerPlayer,
 * whose playerType is COMPUTER.
 */
public class ComputerPlayer implements Player {
  @Getter @Setter
  private Color color;
  @Getter 
  private final PlayerType playerType = PlayerType.COMPUTER;
  
  @Override
  /**
   * {@inheritDoc}
   */
  public String getColorName() {
    if (color == Color.RED) {
      return "red";
    }
    if (color == Color.BLUE) {
      return "blue";
    }
    return null;
  }
 
}
