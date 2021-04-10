package com.cat.server.game.module.artifact.event;

import com.cat.server.core.event.PlayerEventBase;
import com.cat.server.game.module.artifact.domain.Artifact;

public class ArtifactRefineEvent extends PlayerEventBase {
    private Artifact artifact;

    public ArtifactRefineEvent(long playerId, Artifact artifact) {
    	super(playerId);
        this.artifact = artifact;
    }

    public Artifact getArtifact() {
        return artifact;
    }
}
