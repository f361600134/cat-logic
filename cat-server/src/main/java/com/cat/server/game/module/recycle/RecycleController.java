package com.cat.server.game.module.recycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBRecycle.ReqResourceRecycleInfo;

/**
 * Recycle控制器
 */
@Controller
public class RecycleController {
	
	private static final Logger log = LoggerFactory.getLogger(RecycleController.class);
  
	@Autowired
	private RecycleService recycleService;
	
	/*
	*请求资源回收信息
	*/
	@Cmd(value = PBProtocol.ReqResourceRecycleInfo_VALUE)
	public void reqResourceRecycleInfo(ISession session, ReqResourceRecycleInfo req) {
		long playerId = session.getUserData();
		recycleService.reqResourceRecycleInfo(playerId, req);
	}
	

}
