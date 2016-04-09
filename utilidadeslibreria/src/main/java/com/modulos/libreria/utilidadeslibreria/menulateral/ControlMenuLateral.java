package com.modulos.libreria.utilidadeslibreria.menulateral;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.widget.LinearLayout;

/**
 * Created by h on 17/03/16.
 */
public class ControlMenuLateral {
    private DrawerLayout drawerLayout;
    private LinearLayout linearLayoutMenuLateral;

    private Activity activity;

    public ControlMenuLateral(Activity activity, int idLayoutMenuLateral, int idDrawerLayout) {
        this.activity = activity;

        linearLayoutMenuLateral = (LinearLayout) this.activity.findViewById(idLayoutMenuLateral);
        drawerLayout = (DrawerLayout) this.activity.findViewById(idDrawerLayout);
    }

    public void mostrarOcultarMenuLateral() {
        if (drawerLayout.isDrawerOpen(linearLayoutMenuLateral)) {
            drawerLayout.closeDrawers();
        } else {
            drawerLayout.openDrawer(linearLayoutMenuLateral);
        }
    }
}
