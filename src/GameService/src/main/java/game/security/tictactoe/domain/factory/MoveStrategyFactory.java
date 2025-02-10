package game.security.tictactoe.domain.factory;

import game.security.tictactoe.domain.model.player.move.*;

public class MoveStrategyFactory {

    private MoveStrategyFactory() {}

    public static MoveStrategy createMoveStrategy(MoveStrategyType type) {
        return switch (type) {
            case DEFAULT -> new DefaultMoveStrategy();
            case MINIMAX -> new MinimaxMoveStrategy();
            case RANDOM -> new RandomMoveStrategy();
        };
    }
}
