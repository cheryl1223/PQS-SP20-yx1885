package model;

import java.awt.Color;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the HumanPlayer,
 * whose playerType is HUMAN.
 */
public class HumanPlayer implements Player {
  @Getter @Setter
  private Color color;
  @Getter 
  private final PlayerType playerType = PlayerType.HUMAN;
  
  /**
   * {@inheritDoc}
   */
  @Override
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
