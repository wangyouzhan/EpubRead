package com.ma.readerdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.koolearn.android.kooreader.KooReader;
import com.koolearn.android.kooreader.libraryService.BookCollectionShadow;
import com.koolearn.kooreader.book.Book;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private final BookCollectionShadow myCollection = new BookCollectionShadow();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(MainActivity.this);
        mButton = (Button) findViewById(R.id.button);
        final String path = Environment.getExternalStorageDirectory() + "/天道逍遥传.epub";
        File file = new File(path);
        if (file.exists()) {
            Log.i("path", path);
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCollection.bindToService(MainActivity.this, new Runnable() {
                    public void run() {
                        Book book = myCollection.getBookByFile(path);
                        if (book != null) {
                            openBook(book);
                        } else {
                            Toast.makeText(MainActivity.this, "打开失败,请重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void openBook(Book data) {
        KooReader.openBookActivity(this, data, null);
        overridePendingTransition(com.ninestars.android.R.anim.tran_fade_in, com.ninestars.android.R.anim.tran_fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myCollection.unbind();
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

}
