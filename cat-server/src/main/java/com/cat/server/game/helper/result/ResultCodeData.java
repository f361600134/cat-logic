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
	
	public static <T> ResultCodeData<T> of(ErrorCode errorCode, T data) {
		return new ResultCodeData<>(errorCode, data);
	}
	
	public static <T> ResultCodeData<T> of(ErrorCode errorCode) {
		return new ResultCodeData<>(errorCode, null);
	}
}
