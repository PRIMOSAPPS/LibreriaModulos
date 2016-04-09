package com.modulos.libreria.utilidadeslibreria.menulateral;

/**
 * Created by h on 17/03/16.
 */
public class ConfigMenuLateralFactory {
    private static ConfigMenuLateralFactory instance;

    private IConfigMenuLateral configMenuLateral;

    private ConfigMenuLateralFactory() {}

    public static ConfigMenuLateralFactory getInstance() {
        if(instance == null) {
            instance = new ConfigMenuLateralFactory();
        }
        return instance;
    }

    public void setConfigMenuLateral(IConfigMenuLateral configMenuLateral) {
        this.configMenuLateral = configMenuLateral;
    }

    public IConfigMenuLateral getConfigMenuLateral() {
        return configMenuLateral;
    }
}
