package game.security.tictactoe.datasource.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@Table(name = "game")
public class GameModel {

    @Id
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "first_player_id", nullable = false)
    private PlayerModel firstPlayer;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "second_player_id", nullable = false)
    private PlayerModel secondPlayer;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "game_field_id", nullable = false)
    private GameFieldModel gameField;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "game_state_id", nullable = false)
    private GameStateModel gameState;

    public GameModel(UUID uuid, PlayerModel firstPlayer, PlayerModel secondPlayer, GameStateModel gameState) {
        this.uuid = uuid;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.gameField = new GameFieldModel();
        this.gameState = gameState;
    }

    public GameModel(){
        this.uuid = null;
        this.firstPlayer = null;
        this.secondPlayer = null;
        this.gameField = null;
        this.gameState = null;
    }
}
