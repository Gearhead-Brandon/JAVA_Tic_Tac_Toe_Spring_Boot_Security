package game.security.tictactoe.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ToString
public class GameDTO{
    @JsonProperty("uuid")
    private final UUID uuid;

    @JsonProperty("firstPlayerName")
    private final String firstPlayerName;

    @JsonProperty("firstPlayerUuid")
    private final String firstPlayerUuid;

    @JsonProperty("firstPlayerSide")
    private final String firstPlayerSide;

    @JsonProperty("secondPlayerName")
    private final String secondPlayerName;

    @JsonProperty("secondPlayerUuid")
    private final String secondPlayerUuid;

    @JsonProperty("secondPlayerSide")
    private final String secondPlayerSide;

    @JsonProperty("gameField")
    private final List<List<Character>> gameField = List.of(
            Arrays.asList(' ', ' ', ' '),
            Arrays.asList(' ', ' ', ' '),
            Arrays.asList(' ', ' ', ' ')
    );

    public GameDTO(
            UUID uuid,
            String firstPlayerName, String firstPlayerUuid, String firstPlayerSide,
            String secondPlayerName, String secondPlayerUuid, String secondPlayerSide) {
        this.uuid = uuid;
        this.firstPlayerName = firstPlayerName;
        this.firstPlayerUuid = firstPlayerUuid;
        this.firstPlayerSide = firstPlayerSide;
        this.secondPlayerName = secondPlayerName;
        this.secondPlayerUuid = secondPlayerUuid;
        this.secondPlayerSide = secondPlayerSide;
    }

    public Character getCell(int row, int col) { return gameField.get(row).get(col); }

    public void setCell(int row, int col, Character cell) { gameField.get(row).set(col, cell); }
}
