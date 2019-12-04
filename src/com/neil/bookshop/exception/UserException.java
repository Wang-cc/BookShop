package com.neil.bookshop.exception;

/** 自定义异常类
 * 1.自定义异常继承类可以为：Throwable,Exception,RuntimeException,一般会选择继承Exception和RuntimeException，
 * 	 如果不要求调用者一定要处理抛出的异常，就继承RuntimeException.
 * 2.方法名后何时需要throws（声明）异常？
 * 		在方法内throw RuntimeException以及其子类,方法名后可用throws声明也可不声明该异常;
 *   	在方法内throw Throwable以及其子类（不包括RuntimeException以及其子类）  方法名后必须用throws声明该异常。
 *   何时捕捉异常，什么样的异常必须捕捉?
 *   	RuntimeException以及其子类 不是必须捕捉， 其他的异常必须捕捉或者向上抛出。
 *
 * 注意：本项目考虑到更进一步理解Java异常，继承Exception，要求方法自身必须声明，调用方法必须捕捉或向上抛出
 */
public class UserException extends Exception {
	public UserException() {
		super();
	}

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserException(Throwable cause) {
		super(cause);
	}

	protected UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
