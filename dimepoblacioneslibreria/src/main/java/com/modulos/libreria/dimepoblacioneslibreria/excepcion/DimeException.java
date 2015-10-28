package com.modulos.libreria.dimepoblacioneslibreria.excepcion;

public class DimeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3010903443481024846L;

	public DimeException() {
		super();
	}

	public DimeException(String detailMessage) {
		super(detailMessage);
	}

	public DimeException(Throwable throwable) {
		super(throwable);
	}

	public DimeException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
