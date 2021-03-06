package com.modulos.libreria.utilidadeslibreria.permisos;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by h on 2/07/16.
 */
public class Permisos {
    public final static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    public final static int REQUEST_CODE_ASK_PERMISSIONS = 123;

    public static String[] PERMISOS_CAMARA = {Manifest.permission.CAMERA};
    public static String[] PERMISOS_LOCALIZACION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    public static String[] PERMISOS_CUENTAS_USUARIO = {Manifest.permission.GET_ACCOUNTS};
    public static String[] PERMISOS_NUMERO_TELEFONO = {Manifest.permission.READ_PHONE_STATE};

    public boolean preguntarPermisos(Activity actividad, String[] permisos) {
        return preguntarPermisos(actividad, Arrays.asList(permisos));
    }

    public boolean preguntarPermisos(Activity actividad, Iterable<String> permisos) {
        boolean resul = false;

        List<String> permisosPreguntar = new ArrayList<>();
        for(String permiso : permisos) {
            if(deboPreguntarPermiso(actividad, permiso)) {
                permisosPreguntar.add(permiso);
            }
        }

        if(!permisosPreguntar.isEmpty()) {
            resul = true;
            permisosConcedidos(actividad, permisosPreguntar.toArray(new String[permisosPreguntar.size()]));
        }

        return resul;
    }

    public boolean permisosConcedido(Activity actividad, String[] permisos) {
        boolean resul = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permiso : permisos) {
                int permissionCheck = ContextCompat.checkSelfPermission(actividad,
                        permiso);
                resul = permissionCheck == PackageManager.PERMISSION_GRANTED;
                if (!resul) {
                    break;
                }
            }
        }
        return resul;
    }

    private boolean permisosConcedidos(Activity actividad, String[] permisos) {
        boolean resul = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            actividad.requestPermissions(permisos, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
        return resul;
    }

    private boolean permisoConcedido(Activity actividad, String permiso) {
        boolean resul = true;
        int permissionCheck = ContextCompat.checkSelfPermission(actividad,
                permiso);
        resul = permissionCheck == PackageManager.PERMISSION_GRANTED;
        return resul;
    }

    private boolean deboPreguntarPermiso(Activity actividad, String permiso) {
        boolean resul = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!permisoConcedido(actividad, permiso)) {
                resul = !actividad.shouldShowRequestPermissionRationale(permiso);
            }
        }
        return resul;
    }

    public boolean checkSiPermisosConcedidos(String[] permissions, int[] grantResults) {
        boolean resul = true;

        for(int grantResult : grantResults) {
            if(grantResult != PackageManager.PERMISSION_GRANTED) {
                resul = false;
                break;
            }
        }

        return resul;
    }

    public boolean coincidenPermisos(String[] permissions1, String[] permissions2) {
        if(permissions1 == null || permissions2 == null) {
            return false;
        }
        if(permissions1.length != permissions2.length) {
            return false;
        }
        boolean resul = true;
        List<String> permisos1 = Arrays.asList(permissions1);
        List<String> permisos2 = Arrays.asList(permissions2);
        for(String permiso1 : permisos1) {
            if(!permisos2.contains(permiso1)) {
                resul = false;
                break;
            }
        }
        return resul;
    }

}
