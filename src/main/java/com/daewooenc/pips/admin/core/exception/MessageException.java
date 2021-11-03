package com.daewooenc.pips.admin.core.exception;

import com.daewooenc.pips.admin.core.util.message.MessageUtil;

/**
 * 메시지 프로퍼티 파일의 프로퍼티 키와 인수 배열, 그리고 원인이 되는 Throwable 등을 전달
 */
public class MessageException extends RuntimeException {
	

	/** 에러코드. */
	private String errorCode;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4959004804517212424L;

	/**
	 * 입력변수.
	 */
	private Object[] args;

	public void setArgs(Object[] args) {
		this.args = args.clone();
	}

	public Object[] getArgs() {
		return args.clone();
	}
	
	/**
	 * 프로퍼티 파일의 키를 지정한다.
	 *
	 * @param propKey String
	 */
	public MessageException(String propKey) {
		super(MessageUtil.getMessage(propKey));
		this.errorCode = propKey;
	}

	/**
	 * 프로퍼티 파일의 키, arg를 지정한다.
	 *
	 * @param propKey String
	 * @param args Object[]
	 */
	public MessageException(String propKey, Object[] args) {
		super(MessageUtil.getMessage(propKey));
		this.args = args.clone();
	}
	
	/**
	 * 프로퍼티 파일의 키와 내포할 예외를 지정한다.
	 *
	 * 프로퍼티 파일의 템플릿 문자열 처리시 첫번째(그리고 단 하나의) arg는 t.getMessage()가 된다.
	 *
	 * @param propKey String
	 * @param t Throwable
	 */
	public MessageException(String propKey, Throwable t) {
		super(MessageUtil.getMessage(propKey), t);
		this.args = new Object[]{t.getMessage()};
	}
	
	/**
	 * 프로퍼티 파일의 키, arg 배열, 내포할 예외를 지정한다.
	 *
	 * 프로퍼티 파일의 템플릿 문자열 처리시 Object[] args가 순서대로 들어가는데 더하여 
	 * 마지막 arg로 t.getMessage()를 추가해준다.
	 * @param propKey String
	 * @param args Object[]
	 * @param t Throwable
	 */
	public MessageException(String propKey, Object[] args, Throwable t) {
		super(MessageUtil.getMessage(propKey), t);
		
		this.errorCode = propKey;
		
		this.args = new Object[args.length + 1];
		System.arraycopy(args, 0, this.args, 0, args.length);
		this.args[args.length] = t.getMessage();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}	
}
