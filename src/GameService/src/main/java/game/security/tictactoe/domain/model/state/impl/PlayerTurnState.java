package game.security.tictactoe.domain.model.state.impl;

import game.security.tictactoe.domain.factory.GameStateFactory;
import game.security.tictactoe.domain.model.game.CellType;
import game.security.tictactoe.domain.model.game.Game;
import game.security.tictactoe.domain.model.game.GameField;
import game.security.tictactoe.domain.model.player.Player;
import game.security.tictactoe.domain.model.state.GameState;
import game.security.tictactoe.domain.model.state.GameStateType;
import game.security.tictactoe.domain.service.GameAttribute;
import game.security.tictactoe.exception.InvalidPlayerTurnException;
import game.security.tictactoe.exception.InvalidRequestBodyException;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class PlayerTurnState extends TurnState implements GameState {

    private final UUID currentPlayerUuid;

    public PlayerTurnState(Game game, UUID playerUuid) {
        super(game);
        this.currentPlayerUuid = playerUuid;
    }

    @Override
    public String getStatusMessage() {
        return "Player turn with id: " + currentPlayerUuid;
    }

    @Override
    public GameStateType getGameStateType() {
        return GameStateType.PLAYER_TURN;
    }

    @Override
    public UUID getCurrentPlayerUuid() {
        return currentPlayerUuid;
    }

    @Override
    protected void getLogMessage() {
        log.info("Player {} turn", game.getPlayer(currentPlayerUuid));
    }

    @Override
    protected void checkUserId(UUID userId){
        if(!currentPlayerUuid.equals(userId))
            throw new InvalidPlayerTurnException("Invalid player turn");
    }

    @Override
    protected void validateChanges(GameField newGameField) {
        final CellType playerSide = game.getPlayer(currentPlayerUuid).getSide();
        final GameField oldField = game.getGameField();
        int numberOfChangedCells = 0;

        for(int i = 0; i < GameAttribute.ROWS.getValue(); i++) {
            for(int j = 0; j < GameAttribute.COLS.getValue(); j++) {

                final CellType cellType = oldField.getCell(i, j);
                final CellType newCellType = newGameField.getCell(i, j);

                if(cellType != newCellType) {
                    numberOfChangedCells++;
                    if(newCellType != playerSide || numberOfChangedCells > 1)
                        throw new InvalidRequestBodyException("Invalid game field");
                }
            }
        }

        if(numberOfChangedCells == 0)
            throw new InvalidRequestBodyException("Invalid game field");

        game.getGameField().mergeField(newGameField);
    }

    @Override
    protected void makeMove() {
        game.getPlayer(currentPlayerUuid).makeMove(game);
    }

    @Override
    protected void passTurnToPlayer() {
        final Player firstPlayer = game.getFirstPlayer();
        final Player secondPlayer = game.getSecondPlayer();

        final boolean isCurrentPlayerFirst = firstPlayer.getId().equals(currentPlayerUuid);
        final boolean isSecondPlayerComputer = secondPlayer.isComputer();

        final GameStateType gameStateType = isCurrentPlayerFirst && isSecondPlayerComputer
                ? GameStateType.COMPUTER_TURN
                : GameStateType.PLAYER_TURN;

        game.setState(GameStateFactory.createGameState(
                gameStateType,
                game,
                gameStateType.equals(GameStateType.COMPUTER_TURN) ? null : getNextTurnPlayerUuid(game, game.getPlayer(currentPlayerUuid)))
        );
    }

    public UUID getNextTurnPlayerUuid(Game game, Player player) {
        final UUID firstPlayerUuid = game.getFirstPlayer().getId();
        final UUID secondPlayerUuid = game.getSecondPlayer().getId();
        return player.getId().equals(firstPlayerUuid) ? secondPlayerUuid : firstPlayerUuid;
    }
}
