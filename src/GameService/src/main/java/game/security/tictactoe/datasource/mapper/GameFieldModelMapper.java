package game.security.tictactoe.datasource.mapper;

import game.security.tictactoe.datasource.model.GameFieldModel;
import game.security.tictactoe.domain.model.game.CellType;
import game.security.tictactoe.domain.model.game.GameField;
import game.security.tictactoe.domain.service.GameAttribute;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameFieldModelMapper {

    default GameFieldModel toModel(final GameField gameField){
        GameFieldModel gameFieldDTO = new GameFieldModel();

        for(int i = 0; i < GameAttribute.ROWS.getValue(); i++)
            for(int j = 0; j < GameAttribute.COLS.getValue(); j++)
                gameFieldDTO.setCell(i, j, gameField.getCell(i, j).getValue());

        return gameFieldDTO;
    }

    default GameField toEntity(final GameFieldModel gameFieldModel) {
        GameField gameField = new GameField();

        for(int i = 0; i < GameAttribute.ROWS.getValue(); i++)
            for(int j = 0; j < GameAttribute.COLS.getValue(); j++)
                gameField.setCell(i, j, CellType.valueOf(gameFieldModel.getCell(i, j)));

        return gameField;
    }
}
