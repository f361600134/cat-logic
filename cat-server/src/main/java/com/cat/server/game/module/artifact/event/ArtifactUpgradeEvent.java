package com.cat.server.game.module.artifact.event;

import com.cat.server.core.event.PlayerBaseEvent;

public class ArtifactUpgradeEvent extends PlayerBaseEvent {

    private final int configId; //神器配置id
    private final int level; //等级

    private ArtifactUpgradeEvent(long playerId, int configId, int level) {
    	super(playerId);
        this.configId = configId;
        this.level = level;
    }

    public static ArtifactUpgradeEvent create(long playerId, int configId, int level) {
        return new ArtifactUpgradeEvent(playerId, configId, level);
    }

    public int getConfigId() {
        return configId;
    }

    public int getLevel() {
        return level;
    }
}
