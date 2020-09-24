package vn.poly.edu.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getContact(View view) {

        // kiem tra, nếu có quyền r, thì truy vấn luôn
        if (ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            layDanhBa();
        }
        // nếu ko có, thì xin confirm của người dùng
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS
            }, 999);
            // requestCode để đánh dấu sự kiện xin quyền và kiểm tra lại ở hàm lắng nghe
            // kết quả xin quyền
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 999 là code request xin quyền đọc danh bạ
        if (requestCode == 999) {
            if (ContextCompat.checkSelfPermission(
                    MainActivity.this, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                layDanhBa();
            } else {
                Toast.makeText(this, "Cần cấp quyền, mới xem được", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 888) {
            if (ContextCompat.checkSelfPermission(
                    MainActivity.this, Manifest.permission.READ_CALL_LOG)
                    == PackageManager.PERMISSION_GRANTED) {
                layCallLog();
            } else {
                Toast.makeText(this, "Cần cấp quyền, mới xem được", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void getCallLog(View view) {
        // kiem tra, nếu có quyền r, thì truy vấn luôn
        if (ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_CALL_LOG)
                == PackageManager.PERMISSION_GRANTED) {
            layCallLog();
        }
        // nếu ko có, thì xin confirm của người dùng
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG
            }, 888);
            // requestCode để đánh dấu sự kiện xin quyền và kiểm tra lại ở hàm lắng nghe
            // kết quả xin quyền
        }
    }

    public void getMediaStore(View view) {

    }

    public void layCallLog() {
        // khai báo địa chỉ
        Uri uri = CallLog.Calls.CONTENT_URI;

        // Truy vấn
        Cursor cursor = getContentResolver().
                query(uri, null, null, null, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    Log.e("DATA", date + ":" + number + ":" + duration);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
    }

    public void layDanhBa() {
        // khai báo địa chỉ
        Uri uri = Uri.parse("content://contacts/people");

        // Truy vấn
        Cursor cursor = getContentResolver().
                query(uri, null, null, null, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String id = cursor.getString(cursor.
                            getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.
                            getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    Log.e("CONTACT", id + ":" + name);
                    //String number =  cursor.getString(cursor.getColumnIndex(""));;
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }


    }
}