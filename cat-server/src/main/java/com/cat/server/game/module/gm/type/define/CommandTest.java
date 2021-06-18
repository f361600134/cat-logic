package com.cat.server.game.module.gm.type.define;

import org.springframework.stereotype.Component;

import com.cat.server.game.module.gm.annotation.Command;
import com.cat.server.game.module.gm.type.AbstractModuleCommand;

/**
 * 测试命令, 用于调用指定代码
 * @author Jeremy
 */
@Component
@Command("@test")
public class CommandTest extends AbstractModuleCommand {



}
