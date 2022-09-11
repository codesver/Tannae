package codesver.tannae.service;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    private static Toast t;
    public static void toast(Context context, String message) {
        if (t != null)
            t.cancel();
        t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        t.show();
    }
}
