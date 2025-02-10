package game.security.tictactoe.web.controller;

import game.security.tictactoe.domain.model.game.GameCreationResult;
import game.security.tictactoe.domain.service.game.GameService;
import game.security.tictactoe.exception.InvalidRequestBodyException;
import game.security.tictactoe.exception.ResourceNotFoundException;
import game.security.tictactoe.web.annotation.GameExceptionHandler;
import game.security.tictactoe.web.mapper.MoveResultMapper;
import game.security.tictactoe.web.model.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * This class defines the REST API endpoints for Tic-Tac-Toe game management.
 * It handles requests for creating new games, making moves, and retrieving game state.
 */
@Slf4j
@Validated
@RestController
@GameExceptionHandler
@RequestMapping("/api")
@Tag(name = "TicTacToe")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final MoveResultMapper moveResultMapper;

    /**
     * Creates a new game and makes the first move for the specified player side.
     *
     * @param request The request body containing game creation details.
     * @return A {@link ResponseEntity} containing the created game's location URI and the first move result.
     * @throws IllegalArgumentException if the playerSide or gameField is invalid.
     */
    @PostMapping(value = "/game")
    public ResponseEntity<MoveResultDTO> createGame(
            @RequestAttribute("User-UUID") UUID userId,
            @Valid @RequestBody GameCreationRequest request
    ) {
        log.info("Request received: POST /api/game, GameCreationRequest = {}", request);

        GameCreationResult gcr = gameService.createGame(userId, request);

        URI location = UriComponentsBuilder.fromUriString("/api/v1/game/{uuid}")
                .buildAndExpand(gcr.uuid())
                .toUri();

        return ResponseEntity.created(location)
                .contentType(MediaType.APPLICATION_JSON)
                .body(moveResultMapper.toDTO(gcr.moveResult()));
    }

    @GetMapping(value = "/game/available")
    public ResponseEntity<List<AvailableGameDTO>> getAvailableGames(@RequestAttribute("User-UUID") UUID userId) {

        log.info("Request received: GET /api/v1/game/available");
        return ResponseEntity.ok(gameService.getAvailableGames(userId));
    }

    @GetMapping(value = "/game/{uuid}")
    public ResponseEntity<GameDTO> getGame( @PathVariable("uuid") UUID uuid ) {

        log.info("Request received: GET /api/game/{uuid}, UUID = {}", uuid);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(gameService.getGame(uuid));
    }

    @PutMapping(value = "/game/{uuid}/join")
    public void joinGame(@PathVariable("uuid") UUID uuid, @RequestAttribute("User-UUID") UUID userId) {

        log.info("Request received: PUT /api/v1/game/{uuid}/join, UUID = {}", uuid);
        gameService.joinGame(uuid, userId);
    }

    /**
     * Updates the game state by making a move on the provided game board.
     *
     * @param uuid The unique identifier of the game.
     * @param gameDTO The DTO containing the updated game board.
     * @return A ResponseEntity containing the updated game state after the move.
     * @throws ResourceNotFoundException if the game with the specified UUID is not found.
     * @throws InvalidRequestBodyException if the move is invalid.
     */
    @PutMapping(value = "/game/{uuid}")
    public ResponseEntity<MoveResultDTO> updateGame(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody GameFieldDTO gameDTO,
            @RequestAttribute("User-UUID") UUID userId
    ) {
        log.info("Request received: PUT /api/game/{uuid}, UUID = {}, NewGameField = {}", uuid, gameDTO);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(moveResultMapper.toDTO(gameService.updateGame(uuid, gameDTO, userId)));
    }
}
