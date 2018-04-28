package com.example.demo.datasource.tcc.exception;

/**
 * Created by xiong.j on 2016/7/21
 */
public class DistributedTransactionException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3123457774348164723L;

	public DistributedTransactionException(String message) {
        super(message);
    }

    public DistributedTransactionException(Throwable e) {
        super(e);
    }

    public DistributedTransactionException(String message,Throwable e){
        super(message,e);
    }
}
