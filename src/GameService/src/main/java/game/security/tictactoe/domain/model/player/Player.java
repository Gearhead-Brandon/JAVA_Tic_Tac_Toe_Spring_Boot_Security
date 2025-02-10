package game.security.tictactoe.domain.model.player;

import game.security.tictactoe.domain.model.game.CellType;
import game.security.tictactoe.domain.model.game.Game;
import game.security.tictactoe.domain.model.player.move.MoveStrategy;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class Player {
    public static final UUID COMPUTER_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private final UUID id;

    private final CellType side;

    @Setter
    private MoveStrategy moveStrategy;

    public Player(UUID id, CellType side, MoveStrategy moveStrategy) {
        this.id = id;
        this.side = side;
        this.moveStrategy = moveStrategy;
    }

    public void makeMove(Game game) {
        moveStrategy.makeMove(game, side);
    }

    public boolean isComputer() {
        return COMPUTER_UUID.equals(id);
    }
}
