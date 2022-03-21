package com.cat.server.game.helper.result;

/**
 * 带有返回结果的的错误码
 * @author Jeremy
 */
public class ResultCodeData<T> {
	
	public static final ResultCodeData<?> SUCCESS = ResultCodeData.of(ErrorCode.SUCCESS);
	
	private final ErrorCode errorCode;
	
	private final T data;
	
	private ResultCodeData(ErrorCode errorCode, T data) {
		this.errorCode = errorCode;
		this.data = data;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public T getData() {
		return data;
	}
	
	public boolean isSuccess() {
		return errorCode.isSuccess();
	}
	
	/**
	 * 构建携带错误码，返回数据对象的ResultCodeData
	 * @param errorCode
	 * @param data
	 */
	public static <T> ResultCodeData<T> of(ErrorCode errorCode, T data) {
		return new ResultCodeData<>(errorCode, data);
	}
	
	/**
	 * 构建携带错误码，不携带返回数据对象的ResultCodeData
	 * @param <T>
	 * @param errorCode
	 * @return  
	 * @return ResultCodeData<T>  
	 * @date 2022年3月19日下午11:24:17
	 */
	public static <T> ResultCodeData<T> of(ErrorCode errorCode) {
		return new ResultCodeData<>(errorCode, null);
	}
	
	/**
	 * 构建携带返回数据对象的ResultCodeData，错误码默认为SUCCESS
	 * @param <T>
	 * @param data
	 * @return  
	 * @return ResultCodeData<T>  
	 * @date 2022年3月19日下午11:24:29
	 */
	public static <T> ResultCodeData<T> of(T data) {
		return new ResultCodeData<>(ErrorCode.SUCCESS, data);
	}
}
