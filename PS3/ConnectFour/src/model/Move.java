package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class represents the Move object,
 * which contains fields: row, column, player.
 */
@AllArgsConstructor
public class Move {
  @Getter
  private final int row;
  @Getter
  private final int column;
  @Getter
  private final Player player;
}
