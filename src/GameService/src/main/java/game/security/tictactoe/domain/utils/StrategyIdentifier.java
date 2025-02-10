package game.security.tictactoe.domain.utils;

import game.security.tictactoe.domain.model.player.move.*;

public class StrategyIdentifier {

    private StrategyIdentifier(){}

    public static MoveStrategyType getStrategyType(MoveStrategy strategy){
        return switch (strategy){
            case DefaultMoveStrategy _ -> MoveStrategyType.DEFAULT;
            case RandomMoveStrategy _ -> MoveStrategyType.RANDOM;
            case MinimaxMoveStrategy _ -> MoveStrategyType.MINIMAX;
            case MoveStrategy _ -> MoveStrategyType.DEFAULT;
        };
    }
}
