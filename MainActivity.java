private static final int REQ = 101;

private void ensureContactsPermission() {
    if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.READ_CONTACTS}, REQ);
    } else {
        ContactsSync.start(getApplicationContext());
    }
}

@Override
public void onRequestPermissionsResult(int code, String[] p, int[] r) {
    super.onRequestPermissionsResult(code, p, r);
    if (code == REQ && r.length > 0 && r[0] == PackageManager.PERMISSION_GRANTED) {
        ContactsSync.start(getApplicationContext());
    }
}
