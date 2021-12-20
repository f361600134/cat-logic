package com.cat.server.admin;

import java.util.List;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.cat.net.http.annatation.Param;
import com.cat.net.http.annatation.RequestMapping;
import com.cat.server.utils.HttpClientUtil;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

@Controller
@RequestMapping("/hello")
public class HelloJeremyHandler {
	
	//http://localhost:8001/hello/index
	@RequestMapping("/index")
	public String index() {
		return "Hello Jeremy";
	}
	/**
	 * 参数简单类型
	 * http://localhost:8001/hello/index2?a=1&b=aac&c=0.1&dd=1111
	 */
	@RequestMapping("/index2")
	public String index2(int a, @Param("b") String b, double c, @Param("dd") Long d) {
		return a+","+b+","+c+","+d;
	}
	
	/**
	 * 参数是Map对象
	 * @test http://localhost:8001/hello/index3?a=1&b=aa
	 */
	@RequestMapping("/index3")
	public String index3(Map<String, Object> map) {
		return "map:"+map;
	}
	
	/**
	 * 参数复杂对象
	 * @test http://localhost:8001/hello/index4?a=1&b=aa
	 */
	@RequestMapping("/index4")
	public String index4(Stu stu) {
		return "Stu:"+stu;
	}

	/**
	 * 参数简单类型和List类型
	 * @test http://localhost:8001/hello/index5?a=1&list=[1,2]
	 */
	@RequestMapping("/index5")
	public String index5(@Param("a") int a, @Param("list") List<Integer> list) {
		System.out.println(list);
		return "a:"+a+", list:"+list;
	}
	
	/**
	 * 参数携带FullHttpRequest,FullHttpResponse
	 *  @test http://localhost:8001/hello/index7
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "/index7")
	public String index7(FullHttpRequest request, FullHttpResponse response) {
		response.content().writeCharSequence("=====", CharsetUtil.UTF_8);
		return "hahahhahahahahaha";
	}
	
	/**
	 * 参数携带json
	 *  @test http://localhost:8001/hello/index8
	 * @param parameter
	 * @return
	 * 
	 */
	@RequestMapping(value = "/index8")
	public String index8(Stu stu) {
		return "json===> stu:"+stu;
	}
	
	public static void main(String[] args) {
		Stu stu = new Stu();
		stu.setA(1);
		stu.setB("aa");
		String json = JSON.toJSONString(stu);
		System.out.println(json);
//		String url = "http://localhost:8001/hello/index6";
//		String ret = HttpClientUtil.doHttpPost(url, json, ContentType.APPLICATION_JSON);
//		System.out.println(ret);
	}
	
	public static class Stu{
		int a;
		String b;
		
		public Stu() {}
		public int getA() {
			return a;
		}
		public void setA(int a) {
			this.a = a;
		}
		public String getB() {
			return b;
		}
		public void setB(String b) {
			this.b = b;
		}
		@Override
		public String toString() {
			return "Stu [a=" + a + ", b=" + b + "]";
		}
		
	}
}
