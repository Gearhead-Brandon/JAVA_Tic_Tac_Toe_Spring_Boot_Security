package game.security.tictactoe.datasource.model;

import game.security.tictactoe.domain.service.GameAttribute;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "game_field")
@AllArgsConstructor
@NoArgsConstructor
public class GameFieldModel {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *The game board, represented as a list of characters.
     * ' ' represents an empty cell, 'X' represents a cross, and 'O' represents a nought.
     */
    @Column(name = "field", nullable = false, length = 1)
    private List<Character> gameField = Arrays.asList(
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '
    );

    /**
     * Sets the value of a cell on the game board.
     *
     * @param row The row index.
     * @param col The column index.
     * @param cell The value to set ('X', 'O', or ' ').
     */
    public void setCell(int row, int col, Character cell) {
        gameField.set(row * GameAttribute.COLS.getValue() + col, cell);
    }

    /**
     * Gets the value of a cell on the game board.
     *
     * @param row The row index.
     * @param col The column index.
     * @return The value of the cell ('X', 'O', or ' ').
     */
    public Character getCell(int row, int col) {
        return gameField.get(row * GameAttribute.COLS.getValue() + col);
    }
}
