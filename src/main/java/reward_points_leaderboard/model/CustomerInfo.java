package reward_points_leaderboard.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInfo {

    private final int id;
    private int rewardPoints;

    public CustomerInfo(int id, int rewardPoints) {
        this.id = id;
        this.rewardPoints = rewardPoints;
    }
}
