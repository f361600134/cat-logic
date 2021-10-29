package com.cat.api;

/**
 * 协议号定义, 用于rpc通讯<br>
 * 协议id = 模块id * 100 + 1
 * @author Jeremy
 */
public class ProtocolId {

	/**
	 * 零零碎碎100
	 */
	public static final int ReqIdentityAuthenticate  = 10001;
	public static final int RespIdentityAuthenticate  = 10002;
	public static final int ReqKickUpPlayer = 10003;
	public static final int RespKickUpPlayer = 10004;


	/* rank 101 */
	/**
	 * 请求初始化排行榜信息
	 */
	public static final int ReqInitRankInfo = 10101;
	/**
	 * 响应排行榜信息
	 */
//	public static final int RespInitRankInfo = 10102;
	/**
	 * 请求排行榜已更新数据
	 */
	public static final int ReqAddDataToRank = 10103;
	/**
	 * 响应排行榜已更新数据
	 */
//	public static final int RespRankUpdateData= 10104;
	/**
	 * 请求覆盖排行榜数据
	 */
	public static final int ReqCoverRank = 10105;



}
