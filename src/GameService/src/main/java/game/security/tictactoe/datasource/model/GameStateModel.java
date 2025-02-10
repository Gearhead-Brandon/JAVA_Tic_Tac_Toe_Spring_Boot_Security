package game.security.tictactoe.datasource.model;

import game.security.tictactoe.domain.model.state.GameStateType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "game_state")
@AllArgsConstructor
@NoArgsConstructor
public class GameStateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, length = 15)
    private GameStateType state;

    @Column(name = "current_player_uuid", nullable = false)
    private UUID currentPlayerUuid;
}
