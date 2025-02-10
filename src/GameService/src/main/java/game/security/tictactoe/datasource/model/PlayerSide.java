package game.security.tictactoe.datasource.model;

import game.security.tictactoe.domain.model.game.CellType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the side of a player in a Tic-Tac-Toe game.
 */
@Getter
@AllArgsConstructor
public enum PlayerSide {
    X("X"),
    O("O");

    private final String value;

    public static PlayerSide fromChar(Character playerSide) { return playerSide == 'X' ? PlayerSide.X : PlayerSide.O; }
}
