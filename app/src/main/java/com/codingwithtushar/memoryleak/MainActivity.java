package com.codingwithtushar.memoryleak;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.submit_button);
        mContext = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyAsyncTask myAsyncTask = new MyAsyncTask(mContext);
                myAsyncTask.execute();
            }
        });
    }

    private static class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        //private Context asyncTaskContext;
        private WeakReference<Context> weakReferenceContext;
        public MyAsyncTask(Context context) {
            weakReferenceContext = new WeakReference<>(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "MyAsyncTask executed in background");
            try {
                Drawable drawable = weakReferenceContext.get().getDrawable(R.drawable.ic_launcher_background);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "OnPost execute called");
        }
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "OnDestroy called");
        super.onDestroy();
    }
}
