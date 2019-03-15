package grimsi.accservermanager.backend.dto;

import grimsi.accservermanager.backend.enums.Car;
import grimsi.accservermanager.backend.enums.Track;
import grimsi.accservermanager.backend.service.LeaderboardRankDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstanceStatsDto {
    private int maxPlayerCount;
    private int currentPlayerCount;
    private List<PlayerDto> currentPlayers;
    private List<LeaderboardRankDto> leaderboard;
    private Track currentTrack;
    private List<Track> tracks;
    private Track nextTrack;
    private List<Car> cars;
}
