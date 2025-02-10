package game.security.tictactoe.datasource.repository.impl;

import game.security.tictactoe.datasource.mapper.GameMapper;
import game.security.tictactoe.datasource.model.GameModel;
import game.security.tictactoe.datasource.repository.GameRepository;
import game.security.tictactoe.datasource.repository.dataLayer.DataLayer;
import game.security.tictactoe.domain.model.game.Game;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository {

    private final DataLayer dataLayer;

    /**
     * Mapper for converting between {@link Game} and {@link GameModel} entities.
     */
    private final GameMapper mapper;

    @Override
    public Game save(Game game){
        if(game == null) return null;

        dataLayer.save(mapper.toModel(game));

        return game;
    }

    @Override
    public Game update(Game game) {
        if (game == null) return null;

        GameModel gameModel = mapper.toModel(game);

        dataLayer.save(gameModel);

        return game;
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        if(uuid != null)
            dataLayer.deleteById(uuid);
    }

    @Override
    public Optional<Game> findByUuid(UUID uuid) {
        return Optional.ofNullable(
                mapper.toEntity(
                        dataLayer.findByUuid(uuid)
                                .orElse(null)
                )
        );
    }

    @Override
    public List<Game> findAll() {
        return mapper.toEntities(dataLayer.findAll());
    }
}
