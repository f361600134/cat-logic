package com.cat.server.game.module.chat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.GameSession;
import com.cat.net.network.controller.IController;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer.ReqChat;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.gm.ICommandService;
import com.cat.server.game.module.player.proto.AckTipsResp;
import com.cat.server.game.module.player.service.PlayerService;

/**
 * 聊天控制器
 *
 * @author Jeremy
 */
@Controller
public class ChatController implements IController{

    private static final Log log = LogFactory.getLog(ChatController.class);

    @Autowired
    private ChatService chatService;
    
    @Autowired
    private ICommandService commandService;
    
    @Autowired
    private PlayerService playerService;

    @Cmd(PBProtocol.ReqChat_VALUE)
    public void chat(GameSession session, ReqChat req) {
        long playerId = session.getPlayerId();
        boolean isGm = this.commandService.isCommand(req.getContent());
        if (isGm) {
            this.commandService.process(session, req.getContent());
        } else {
            //聊天
            ErrorCode code = this.chatService.chat(req, playerId);
            AckTipsResp ack = AckTipsResp.newInstance().setTipsId(code);
            playerService.sendMessage(playerId, ack);
        }
    }

}
