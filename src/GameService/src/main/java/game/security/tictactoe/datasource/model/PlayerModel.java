package game.security.tictactoe.datasource.model;

import game.security.tictactoe.domain.model.player.move.MoveStrategyType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Table(name = "player")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "side", nullable = false, length = 1)
    private PlayerSide side;

    @Enumerated(EnumType.STRING)
    @Column(name = "move_strategy", nullable = false, length = 15)
    private MoveStrategyType moveStrategyType;
}
