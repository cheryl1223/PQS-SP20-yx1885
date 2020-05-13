package model;

import java.awt.Color;

public interface Player {
  /**
   * set the player's color.
   */
  public void setColor(Color c);
  
  /**
   ** Returns the player's color.
   * @return player's color
   */
  public Color getColor();
  
  /**
   ** Returns the player's color name.
   * @return player's color name
   */
  public String getColorName();
  
  /**
   * Returns the player's type.
   * @return player's Type
   */
  public PlayerType getPlayerType();
}
