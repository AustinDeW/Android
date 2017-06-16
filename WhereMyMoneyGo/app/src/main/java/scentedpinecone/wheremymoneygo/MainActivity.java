package scentedpinecone.wheremymoneygo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category = (Spinner)findViewById(R.id.category);
        String[] categories = new String[]
                {
                    "On",
                    "- Create Category -"
                };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, categories);
        category.setAdapter(adapter);

//        Create a file called fadein.xml in res/anim
//
//                <?xml version="1.0" encoding="utf-8"?>
//<alpha xmlns:android="http://schemas.android.com/apk/res/android"
//        android:interpolator="@android:anim/accelerate_interpolator"
//        android:fromAlpha="0.0" android:toAlpha="1.0" android:duration="2000" />
//                Create a file called fadeout.xml in res/anim
//
//                <?xml version="1.0" encoding="utf-8"?>
//
//<alpha xmlns:android="http://schemas.android.com/apk/res/android"
//        android:interpolator="@android:anim/accelerate_interpolator"
//        android:fromAlpha="1.0" android:toAlpha="0.0" android:duration="2000" />
//                If you want to fade from Activity A to Activity B, put the following in the onCreate method for Activity B. Before setContentView works for me.
//
//                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
