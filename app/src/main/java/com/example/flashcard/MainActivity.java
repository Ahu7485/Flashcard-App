package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer).setVisibility(View.VISIBLE);
                findViewById(R.id.question).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer).setVisibility(View.GONE);
                findViewById(R.id.question).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.Add_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String Main_question = data.getExtras().getString("question");
            String Main_answer = data.getExtras().getString("answer");
            ((TextView) findViewById(R.id.question)).setText(Main_question);
            ((TextView) findViewById(R.id.answer)).setText(Main_answer);
            Snackbar.make(findViewById(R.id.question), "Flashcard Sucessfully Added", Snackbar.LENGTH_SHORT).show();
        }
    }
}
