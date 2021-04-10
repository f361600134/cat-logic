package com.cat.server.game.module.artifact.event;

import com.cat.server.core.event.PlayerEventBase;
import com.cat.server.game.module.artifact.domain.Artifact;

public class ArtifactSkillUpgradeEvent extends PlayerEventBase {
	
    private final Artifact artifact;

    public ArtifactSkillUpgradeEvent(long playerId, Artifact artifact) {
    	super(playerId);
        this.artifact = artifact;
    }

    public Artifact getArtifact() {
        return artifact;
    }
}
