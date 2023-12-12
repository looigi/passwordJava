package com.looigi.passwordjava;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class Permessi {
    public boolean ControllaPermessi() {
        int permissionRequestCode1 = 1197;

        String[] PERMISSIONS = new String[] {
                Manifest.permission.INTERNET,
                // Manifest.permission.ACCESS_NETWORK_STATE,
                // Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                // Manifest.permission.MODIFY_AUDIO_SETTINGS,
                // Manifest.permission.BLUETOOTH,
                // Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                // Manifest.permission.WAKE_LOCK,
                // Manifest.permission.READ_PHONE_STATE,
                // android.Manifest.permission.PROCESS_OUTGOING_CALLS,
                // Manifest.permission.READ_CONTACTS,
                // Manifest.permission.GET_ACCOUNTS,
                // Manifest.permission.READ_PHONE_STATE,
                // Manifest.permission.REQUEST_INSTALL_PACKAGES,
                // Manifest.permission.WAKE_LOCK,
                // Manifest.permission.DISABLE_KEYGUARD,
                // Manifest.permission.RECEIVE_BOOT_COMPLETED,
                // android.Manifest.permission.ACCESS_COARSE_LOCATION,
                // Manifest.permission.MODIFY_AUDIO_SETTINGS,
                // Manifest.permission.SET_WALLPAPER,
                // Manifest.permission.SET_WALLPAPER_HINTS,
                // Manifest.permission.SYSTEM_ALERT_WINDOW
        };

        if(!hasPermissions(VariabiliGlobali.getInstance().getContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(VariabiliGlobali.getInstance().getActivity(),
                    PERMISSIONS, permissionRequestCode1);
            return false;
        } else {
            return true;
        }
    }

    private boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }
}
