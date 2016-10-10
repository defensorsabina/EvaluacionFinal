package com.usuario.mariana_pc.evaluacionfinal;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Mariana_PC on 05/10/2016.
 */

public class MiAplicacion extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        mostrarHashKey(); 
    }

    private void mostrarHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ejemplo.ejemploapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.d("MiHashKey", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {
        }
    }
}
