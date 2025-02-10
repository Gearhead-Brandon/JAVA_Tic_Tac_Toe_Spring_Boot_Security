package game.security.tictactoe.datasource.mapper;

import game.security.tictactoe.datasource.model.GameStateModel;
import game.security.tictactoe.domain.model.state.GameState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameStateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currentPlayerUuid", expression = "java(gameState.getCurrentPlayerUuid())")
    @Mapping(target = "state", expression = "java(gameState.getGameStateType())")
    GameStateModel toModel(GameState gameState);
}
