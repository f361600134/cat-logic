package com.cat.server.game.module.gm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.net.network.base.GameSession;
import com.cat.server.core.lifecycle.Lifecycle;
import com.cat.server.game.module.gm.annotation.Command;
import com.cat.server.game.module.gm.type.ICommand;

@Service
class CommandService implements ICommandService, Lifecycle{
	
	@Autowired
	private List<ICommand> commandList;
	/**
	 * key: 命令头
	 * value: 命令
	 * @bag CommandResource.Process
	 */
	private Map<String, ICommand> commands = new HashMap<>();
	
	@Override
	public boolean isCommand(String command) {
		for (String key : commands.keySet()) {
			if(command.startsWith(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean process(GameSession session, String command) {
		String[] cmds = command.split(" ");
		ICommand conmand = null;
		for (String key : commands.keySet()) {
			if (command.startsWith(key) && key.equals(cmds[0])) {
				conmand = commands.get(key);
				break;
			}
		}
		return conmand.process(session, command);
	}
	
	@Override
	public void start() throws Throwable {
		for (ICommand obj : commandList) {
			Command command = obj.getClass().getAnnotation(Command.class);
			String name = command.value();
			this.commands.put(name, obj);
		}
	}

}
