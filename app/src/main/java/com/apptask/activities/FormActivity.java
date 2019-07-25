package com.apptask.activities;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apptask.App;
import com.apptask.R;
import com.apptask.model.Task;

public class FormActivity extends AppCompatActivity {

    EditText mEditTitle;
    EditText mEditDesc;
    Button mBtnSize;
    AlertDialog alertDialog;
    Task task;
    CharSequence [] values = {"20sp","30sp","40sp"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mBtnSize = findViewById(R.id.mBtn_size);
        mEditTitle = findViewById(R.id.editTitle);
        mEditDesc = findViewById(R.id.editDescription);


         task = (Task) getIntent().getSerializableExtra("task");

        if (task != null){

            mEditTitle.setText(task.getTitle());
            mEditDesc.setText(task.getDescription());
        }

        mBtnSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAlertDialogWithButton();
            }
        });

       /* SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(FormActivity.this);

        String title = prefs.getString("title", " ");
        mEditTitle.setText(title);
        String description = prefs.getString("description", " ");
        mEditDesc.setText(description);*/


        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    public void onClickSave(View view) {
        String title = mEditTitle.getText().toString().trim();
        String description = mEditDesc.getText().toString().trim();
        if (task != null) {
            task.setTitle(title);
            task.setDescription(description);
            App.getInstance().getDatabase().taskDao().update(task);
        } else {
            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            App.getInstance().getDatabase().taskDao().insert(task);
            setResult(RESULT_OK);

        }
        if (title.matches("")){
            Toast.makeText(this, "Введите данные!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
    }

    public void onClickCancel(View view) {

        finish();
    }

   /*@Override
    public void onBackPressed() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(FormActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        String title = mEditTitle.getText().toString().trim();
        String description = mEditDesc.getText().toString().trim();
        task = new Task(title, description);
        editor.putString("title", title);
        editor.putString("description", description);
        editor.apply();
        editor.commit();
        editor.clear();
        super.onBackPressed();*/


    public void CreateAlertDialogWithButton(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
        builder.setTitle("Выберите размер текста");
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item){
                    case 0:
                        mEditTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                        mEditDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                        break;
                    case 1:
                        mEditTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                        mEditDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                        break;
                    case 2:
                        mEditTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
                        mEditDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
                        break;
                }
                alertDialog.dismiss();

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

}


