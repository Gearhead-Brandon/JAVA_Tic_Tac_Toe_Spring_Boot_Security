package game.security.tictactoe.domain.model.player.move;

import game.security.tictactoe.domain.model.game.CellType;
import game.security.tictactoe.domain.model.game.Game;
import game.security.tictactoe.domain.service.GameAttribute;
import lombok.ToString;

import java.util.concurrent.ThreadLocalRandom;

@ToString
public class RandomMoveStrategy implements MoveStrategy {

    @Override
    public void makeMove(Game game, CellType side) {
        int i = ThreadLocalRandom.current().nextInt(GameAttribute.ROWS.getValue());
        int j = ThreadLocalRandom.current().nextInt(GameAttribute.ROWS.getValue());
        game.getGameField().setCell(i, j, side);
    }
}
