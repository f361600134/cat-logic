package com.cat.server.game.module.user;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.game.module.user.domain.UserPo;

/**
* @author Jeremy
*/
@PO(name = "user")
public class User extends UserPo {
	
	
	@Column(PROP_DATA)
	private Stu stu;

	public User() {

	}
	
}
