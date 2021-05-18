        package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.answer)).setText(allFlashcards.get(0).getAnswer());
        }


        findViewById(R.id.question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.question).setCameraDistance(25000);
                findViewById(R.id.answer).setCameraDistance(25000);
                findViewById(R.id.question).animate().rotationY(90).setDuration(200).withEndAction(
                    new Runnable(){
                        @Override
                        public void run() {
                            findViewById(R.id.background).setBackgroundResource(R.color.white);
                            findViewById(R.id.question).setVisibility(View.INVISIBLE);
                            findViewById(R.id.answer).setVisibility(View.VISIBLE);
                            findViewById(R.id.answer).setRotationY(-90);
                            findViewById(R.id.answer).animate().rotationY(0).setDuration(200).start();
                            findViewById((R.id.background)).setBackgroundResource(R.color.green);
                        }
                    }).start();
            }
        });
        findViewById(R.id.answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.question).setCameraDistance(25000);
                findViewById(R.id.answer).setCameraDistance(25000);
                findViewById(R.id.answer).animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                        new Runnable(){
                            @Override
                            public void run() {
                                findViewById(R.id.background).setBackgroundResource(R.color.white);
                                findViewById(R.id.answer).setVisibility(View.INVISIBLE);
                                findViewById(R.id.question).setVisibility(View.VISIBLE);
                                findViewById(R.id.question).setRotationY(-90);
                                findViewById(R.id.question).animate().rotationY(0).setDuration(200).start();
                                findViewById(R.id.background).setBackgroundResource(R.color.yellow);
                            }
                        }).start();
            }
        });
        findViewById(R.id.Add_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        findViewById(R.id.right_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findViewById(R.id.answer).getVisibility() != View.VISIBLE) {
                    final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                    final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);
                    leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (allFlashcards.size() == 0)
                                return;
                            currentCardDisplayedIndex++;
                            if (currentCardDisplayedIndex >= allFlashcards.size()) {
                                Snackbar.make(findViewById(R.id.question),
                                        "You've reached the end of the cards, going back to first.",
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                                currentCardDisplayedIndex = 0;
                            }
                            allFlashcards = flashcardDatabase.getAllCards();
                            Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                            ((TextView) findViewById(R.id.answer)).setText(flashcard.getAnswer());
                            ((TextView) findViewById(R.id.question)).setText(flashcard.getQuestion());
                            findViewById(R.id.question).startAnimation(rightInAnim);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    findViewById(R.id.question).startAnimation(leftOutAnim);
                }
            }
        });
        findViewById(R.id.left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findViewById(R.id.answer).getVisibility() != View.VISIBLE){
                    final Animation rightOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_out);
                    final Animation leftInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_in);
                    rightOutAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (allFlashcards.size() == 0)
                                return;
                            currentCardDisplayedIndex--;
                            if (currentCardDisplayedIndex < 0) {
                                Snackbar.make(findViewById(R.id.question),
                                        "You've reached the end of the cards, going back to last.",
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                                currentCardDisplayedIndex = allFlashcards.size() - 1;
                            }
                            allFlashcards = flashcardDatabase.getAllCards();
                            Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                            ((TextView) findViewById(R.id.answer)).setText(flashcard.getAnswer());
                            ((TextView) findViewById(R.id.question)).setText(flashcard.getQuestion());
                            findViewById(R.id.question).startAnimation(leftInAnim);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    findViewById(R.id.question).startAnimation(rightOutAnim);
                }
            }
        });
        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allFlashcards.size() != 1){
                    currentCardDisplayedIndex = 0;
                    flashcardDatabase.deleteCard(((TextView) findViewById(R.id.question)).getText().toString());
                    allFlashcards = flashcardDatabase.getAllCards();
                    Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                    ((TextView) findViewById(R.id.answer)).setText(flashcard.getAnswer());
                    ((TextView) findViewById(R.id.question)).setText(flashcard.getQuestion());
                }else{
                    Toast.makeText(MainActivity.this, "Cannot Delete Only Flashcard Left", Toast.LENGTH_SHORT).show();
                }
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
            flashcardDatabase.insertCard(new Flashcard(Main_question,Main_answer));
            allFlashcards = flashcardDatabase.getAllCards();
        }
    }
}
