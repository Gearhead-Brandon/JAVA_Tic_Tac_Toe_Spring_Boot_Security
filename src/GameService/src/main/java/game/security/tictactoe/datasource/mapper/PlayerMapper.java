package game.security.tictactoe.datasource.mapper;

import game.security.tictactoe.datasource.model.PlayerModel;
import game.security.tictactoe.domain.factory.MoveStrategyFactory;
import game.security.tictactoe.domain.model.player.Player;
import game.security.tictactoe.domain.model.player.move.MoveStrategy;
import game.security.tictactoe.domain.model.player.move.MoveStrategyType;
import game.security.tictactoe.domain.utils.StrategyIdentifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "side", expression = "java(PlayerSide.fromChar(player.getSide().getValue()))")
    @Mapping(target = "moveStrategyType", expression = "java(mapMoveStrategy(player.getMoveStrategy()))")
    PlayerModel toModel(Player player);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "side", expression = "java(CellType.valueOf(playerModel.getSide().name()))")
    @Mapping(target = "moveStrategy", expression = "java(mapMoveStrategyType(playerModel.getMoveStrategyType()))")
    Player toEntity(PlayerModel playerModel);

    default MoveStrategyType mapMoveStrategy(MoveStrategy moveStrategy) {
        return StrategyIdentifier.getStrategyType(moveStrategy);
    }

    default MoveStrategy mapMoveStrategyType(MoveStrategyType moveStrategyType) {
        return MoveStrategyFactory.createMoveStrategy(moveStrategyType);
    }
}
