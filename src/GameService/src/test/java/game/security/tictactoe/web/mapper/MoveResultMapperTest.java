package game.security.tictactoe.web.mapper;

import game.security.tictactoe.domain.model.CellType;
import game.security.tictactoe.domain.model.GameField;
import game.security.tictactoe.domain.model.MoveResult;
import game.security.tictactoe.domain.service.GameAttribute;
import game.security.tictactoe.domain.service.WinState;
import game.security.tictactoe.web.model.MoveResultDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MoveResultMapperTest {

    private final MoveResultMapper moveResultMapper = new MoveResultMapperImpl(new GameFieldMapperImpl());

    @Test
    void testToDTO() {
        // Arrange
        GameField gameField = new GameField();

        for(int i = 0; i < GameAttribute.ROWS.getValue(); i++)
            for(int j = 0; j < GameAttribute.COLS.getValue(); j++)
                gameField.setCell(i, j, CellType.X);

        // Act
        MoveResult moveResult = new MoveResult(WinState.DRAW.toString(), gameField);

        MoveResultDto moveResultDto = moveResultMapper.toDTO(moveResult);

        // Assert
        assertThat(moveResultDto)
                .isNotNull();

        assertThat(moveResultDto.getStatus())
                .isEqualTo(WinState.DRAW.toString());

        assertThat(moveResultDto.getGameField())
                .isNotNull();

        List<List<Character>> gameFieldListDTO = moveResultDto.getGameField();

        for(int i = 0; i < GameAttribute.ROWS.getValue(); i++)
            for(int j = 0; j < GameAttribute.COLS.getValue(); j++)
                assertThat(gameFieldListDTO.get(i).get(j))
                        .isEqualTo(CellType.X.getValue());
    }

    @Test
    void testEntityIsNull() {
        // Arrange
        final MoveResultDto moveResultDto = moveResultMapper.toDTO(null);

        // Assert
        assertThat(moveResultDto)
                .isNull();
    }
}
