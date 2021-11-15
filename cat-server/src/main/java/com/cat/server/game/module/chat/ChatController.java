package com.cat.server.game.module.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IController;
import com.cat.server.game.data.proto.PBChat.ReqChat;
import com.cat.server.game.data.proto.PBDefine;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.gm.ICommandService;
import com.cat.server.game.module.player.IPlayerService;

/**
 * 聊天控制器
 *
 * @author Jeremy
 */
@Controller
public class ChatController implements IController{

    @Autowired
    private ChatService chatService;
    
    @Autowired
    private ICommandService commandService;
    
    @Autowired
    private IPlayerService playerService;

    @Cmd(PBDefine.PBProtocol.ReqChat_VALUE)
    public void chat(ISession session, ReqChat req) {
        long playerId = session.getUserData();
        boolean isGm = this.commandService.isCommand(req.getContent());
        if (isGm) {
            this.commandService.process(session, req.getContent());
        } else {
            //聊天
            ErrorCode code = this.chatService.chat(req, playerId);
            playerService.sendMessage(playerId, code.toProto());
        }
    }

}
