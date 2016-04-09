package com.modulos.libreria.utilidadeslibreria.menulateral;

public class DatosItemMenuLateral {
	private int textoMenu;
	private int identificadorIcono;
	
	public DatosItemMenuLateral(int textoMenu, int identificadorIcono) {
		super();
		this.textoMenu = textoMenu;
		this.identificadorIcono = identificadorIcono;
	}

	public int getTextoMenu() {
		return textoMenu;
	}

	public int getIdentificadorIcono() {
		return identificadorIcono;
	}

}
