package com.cg.cgcheck;

public class CgCheckException extends Exception {
	private String error;

	public CgCheckException(String error) {
		super();
		this.error = error;
	}

	@Override
	public String toString() {
		return error;
	}

}
