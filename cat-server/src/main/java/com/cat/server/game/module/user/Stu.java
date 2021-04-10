package com.cat.server.game.module.user;

import com.cat.orm.core.annotation.PO;
import com.cat.server.game.module.user.domain.StuPo;
import org.springframework.stereotype.Repository;

/**
* @author Jeremy
*/
@PO(name = "stu")
public class Stu extends StuPo {
	
	public static final String ID = Stu.class.getSimpleName();
	
	public Stu() {

	}
	
	public Stu(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static Stu create(String name) {
		Stu stu = new Stu();
		stu.setId(1);
		stu.setAge(1);
		stu.setName(name);
		return stu;
	}

	@Override
	public String toString() {
		return "Stu [id=" + id + ", name=" + name + ", age=" + age + ", birthday=" + birthday + "]";
	}
	
}
