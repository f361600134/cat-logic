package com.cat.server.game.module.artifact;

public interface IArtifactService {
	
	/**
	 * 检测神器是否已激活
	 * @param playerId 玩家id
	 * @param artifactId 神器id
	 */
	public boolean checkArtifactActive(long playerId, int artifactId);
	
	/**
	 * 激活神器
	 * @param playerId 玩家id
	 * @param artifactId 神器id
	 */
	public void artifactActive(long playerId, int artifactId);
	

}