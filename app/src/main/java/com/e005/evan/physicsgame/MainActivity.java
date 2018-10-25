package com.e005.evan.physicsgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

//https://medium.com/@ssaurel/learn-to-save-data-with-sqlite-on-android-b11a8f7718d3

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabaseHandler db;
    private TextView result;

    private Question qHold;

    // Buttons
    public EditText answerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link with layout
        result = (TextView) findViewById(R.id.result);
        answerButton = findViewById(R.id.answerInput);

        // Init our hold variable
        qHold = null;

        db = new SQLiteDatabaseHandler(this);
        List<Question> test = db.allQ();

        // Check if we already have entries
       if(test == null) {
            // Create DB Entries
            Question q = new Question(1, "Alice", "Who is it", "Alice", 1);
            db.addQ(q);

            q = new Question(2, "Bob", "Where is it", "Bob", 2);
            db.addQ(q);

            q = new Question(3, "Charlie", "Why is it", "Charlie3", 3);
            db.addQ(q);

            q = new Question(4, "David", "What is up", "Sky", 4);
            db.addQ(q);

            List<Question> hold = db.allQ();
            //Log.i( "test",String.valueOf(hold.size()));

        }
        // Grab All
        List<Question> qs = db.allQ();

        //db.removeAll();

        // Display
        StringBuilder builder = new StringBuilder();

        // List all questions to start
        for(Question qP : qs){
            builder.append(qP).append("\n");
        }

        result.setText(builder.toString());

    }

    public void randomQ(android.view.View view){
        // Grab Random
        Question qS = db.randQ();

        // Display
        StringBuilder builder = new StringBuilder();

        builder.append(qS.toString()).append("\n");

        qHold = new Question(qS);

        result.setText(builder.toString());

    }

    public void answerQ(android.view.View view){
        String answerText = null;
        String compare;

        answerText = (answerButton.getText()).toString();

        // Create builder
        StringBuilder builder = new StringBuilder();

        // Check that we have a loaded question
        if(qHold == null || answerText == "") {
            builder.append("Invalid Selection!!");
        } else{
            compare = qHold.answer;

            if( compare.equals(answerText.toString())){
                builder.append("Correct!");
            } else {
                builder.append("Incorrect!");
            }

        }

        result.setText(builder.toString());

    }
}
