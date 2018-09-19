package com.example.emanb.student_provider;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.content.ContentValues;
import android.content.CursorLoader;

import android.database.Cursor;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button add_but;
    Button ret_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add a new student record

        add_but = (Button) findViewById(R.id.add_but);
        add_but.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                //NOTE
                //managing data on UI thread is not good
                //we do it here since data here is not heavy

                ContentValues values = new ContentValues();
                values.put(StudentsProvider.NAME,
                        ((EditText)findViewById(R.id.editText)).getText().toString());

                values.put(StudentsProvider.GRADE,
                        ((EditText)findViewById(R.id.editText2)).getText().toString());
                Uri uri = getContentResolver().insert(StudentsProvider.CONTENT_URI, values);

                Toast.makeText(MainActivity.this,uri.toString(), Toast.LENGTH_LONG).show();


            }
        });
        // Retrieve student records

        ret_but = (Button) findViewById(R.id.ret);
        ret_but.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                //NOTE
                //managing data on UI thread is not good
                //we do it here since data here is not heavy


                String URL = "content://com.example.emanb.student_provider/students";

                Uri students = Uri.parse(URL);
                /*
                Cursor c = managedQuery(students, null, null, null, "name");
                if (c.moveToFirst()) {
                   do {
                        Toast.makeText(MainActivity.this,
                                c.getString(c.getColumnIndex(StudentsProvider._ID)) +
                                        ", " + c.getString(c.getColumnIndex(StudentsProvider.NAME)) +
                                        ", " + c.getString(c.getColumnIndex(StudentsProvider.GRADE)),
                                Toast.LENGTH_SHORT).show();
                    }while (c.moveToNext());
                }
*/
                Cursor cursorStu = getApplicationContext().getContentResolver().query(students, null, null,
                        null, StudentsProvider._ID + " DESC");


                if (cursorStu != null &&cursorStu.moveToFirst()) {
                    do {
                        Toast.makeText(MainActivity.this,
                                cursorStu.getString(cursorStu.getColumnIndex(StudentsProvider._ID)) +
                                        ", " + cursorStu.getString(cursorStu.getColumnIndex(StudentsProvider.NAME)) +
                                        ", " + cursorStu.getString(cursorStu.getColumnIndex(StudentsProvider.GRADE)),
                                Toast.LENGTH_SHORT).show();

                        Log.d("Student",""+cursorStu.getString(cursorStu.getColumnIndex(StudentsProvider.NAME))+
                              ", "+  cursorStu.getString(cursorStu.getColumnIndex(StudentsProvider.GRADE)));

                        // do what you need with the cursor here
                    } while (cursorStu.moveToNext());
                }

                if (cursorStu != null && !cursorStu.isClosed())
                    cursorStu.close();


            }
            });
    }

}
