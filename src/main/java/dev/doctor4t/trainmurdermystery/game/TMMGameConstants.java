package dev.doctor4t.trainmurdermystery.game;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public interface TMMGameConstants {
    // Blocks
    int DOOR_AUTOCLOSE_TIME = getInTicks(0, 5);

    // Items
    int KNIFE_COOLDOWN = getInTicks(3, 0);
    int JAMMED_DOOR_TIME = getInTicks(1, 0);
    int LOCKPICK_JAM_COOLDOWN = getInTicks(5, 0);

    // Game areas
    Box READY_AREA = new Box(-981, 1, -364, -813, 3, -358);
    BlockPos PLAY_POS = new BlockPos(-19, 122, -539);

    Box PLAY_AREA = new Box(-140, 118, -535.5f - 15, 230, 200, -535.5f + 15);
    Box BACKUP_TRAIN_LOCATION = new Box(-57, 64, -531, 177, 74, -540);
    Box TRAIN_LOCATION = BACKUP_TRAIN_LOCATION.offset(0, 55, 0);

    static int getInTicks(int minutes, int seconds) {
        return (minutes * 60 + seconds) * 20;
    }
}
