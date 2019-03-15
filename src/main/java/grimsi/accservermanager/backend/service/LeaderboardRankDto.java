package grimsi.accservermanager.backend.service;

import grimsi.accservermanager.backend.dto.PlayerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardRankDto {
    private PlayerDto player;
    private int rank;
    private float bestTime;
}
