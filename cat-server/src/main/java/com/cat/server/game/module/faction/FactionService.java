package com.cat.server.game.module.faction;

import org.springframework.stereotype.Service;

@Service
public class FactionService implements IFactionService{

	@Override
	public boolean checkOpen(long playerId, int moduleId) {
		return true;
	}

}
