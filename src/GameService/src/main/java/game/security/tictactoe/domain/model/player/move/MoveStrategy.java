package game.security.tictactoe.domain.model.player.move;

import game.security.tictactoe.domain.model.Position;
import game.security.tictactoe.domain.model.game.CellType;
import game.security.tictactoe.domain.model.game.Game;

public interface MoveStrategy {

    default void makeMove(Game game, CellType side) {}
}
