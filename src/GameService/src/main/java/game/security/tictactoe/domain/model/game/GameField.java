package game.security.tictactoe.domain.model.game;

import game.security.tictactoe.domain.service.GameAttribute;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the game field for a Tic-Tac-Toe game.
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class GameField {
    /**
     * Internal representation of the game field as a 2D list of {@link CellType} objects.
     */
    private final List<List<CellType>> fieldMatrix = List.of(
            Arrays.asList(CellType.EMPTY, CellType.EMPTY, CellType.EMPTY),
            Arrays.asList(CellType.EMPTY, CellType.EMPTY, CellType.EMPTY),
            Arrays.asList(CellType.EMPTY, CellType.EMPTY, CellType.EMPTY)
    );

    public CellType getCell(int row, int col) { return fieldMatrix.get(row).get(col); }

    public void setCell(int row, int col, CellType cellType) { fieldMatrix.get(row).set(col, cellType);}

    public void mergeField(final GameField newField) {
        for(int i = 0; i < GameAttribute.ROWS.getValue(); i++) {
            for(int j = 0; j < GameAttribute.COLS.getValue(); j++) {
                final CellType oldFieldCell = this.getCell(i, j);
                final CellType newFieldCell = newField.getCell(i, j);

                if(oldFieldCell != newFieldCell)
                    this.setCell(i, j, newFieldCell);
            }
        }
    }

    public boolean isEmpty() {
        for (int i = 0; i < GameAttribute.ROWS.getValue(); i++)
            for (int j = 0; j < GameAttribute.COLS.getValue(); j++)
                if (!fieldMatrix.get(i).get(j).equals(CellType.EMPTY))
                    return false;

        return true;
    }
}
