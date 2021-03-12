package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        EditText questioninput = ((EditText) findViewById(R.id.input_question));
        EditText answerinput = ((EditText) findViewById(R.id.input_answer));

        findViewById(R.id.Exit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        answerinput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch(actionId){
                    case EditorInfo.IME_ACTION_DONE:
                        String input_question =((EditText) questioninput).getText().toString();
                        String input_answer = ((EditText) answerinput).getText().toString();
                        if(input_question.isEmpty() || input_answer.isEmpty()){
                            Toast.makeText(getApplicationContext(), "You must enter Both Question and Answer", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent data = new Intent();
                            data.putExtra("question",input_question);
                            data.putExtra("answer", input_answer);
                            setResult(RESULT_OK,data);

                            finish();
                        }
                }
                return false;
            }
        });
    }
}