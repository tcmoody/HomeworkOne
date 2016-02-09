package com.lclark.homeworkone;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText notesForTheDay;
    private SharedPreferences savedNotes;
    private SharedPreferences.Editor savedNotesEditor;
    private ArrayAdapter<CharSequence> daysOfTheWeekAdapter;
    private Spinner daysOfTheWeek;
    private Button leftButton;
    private Button saveButton;
    private Button rightButton;
    private String[] days;
    private int whichDayOfTheWeek;
    private int previousDayOfTheWeek;
    private int nextDayOfTheWeek;

    //override getView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daysOfTheWeek = (Spinner) findViewById(R.id.days_of_the_week_spinner);
        notesForTheDay = (EditText) findViewById(R.id._edit_text_calendar);

        //adapter and spinner stuff
        daysOfTheWeekAdapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_the_week, android.R.layout.simple_spinner_item);
        daysOfTheWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daysOfTheWeek.setAdapter(daysOfTheWeekAdapter);
        daysOfTheWeek.setOnItemSelectedListener(this);

        //button stuff
        leftButton = (Button) findViewById(R.id.left_button);
        saveButton = (Button) findViewById(R.id.save_button);
        rightButton = (Button) findViewById(R.id.right_button);

        leftButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);

        //setting previous day and next day and getting the string array of the week
        days = getResources().getStringArray(R.array.days_of_the_week);
        leftButton.setText(days[6]);
        rightButton.setText(days[1]);

        previousDayOfTheWeek=6;
        nextDayOfTheWeek=1;

        //edittext stuff
        savedNotes = getPreferences(Context.MODE_PRIVATE);
        savedNotesEditor = savedNotes.edit();
     }

    @Override
    public void onClick(View view) {
        String text = daysOfTheWeek.getSelectedItem().toString();
        whichDayOfTheWeek = daysOfTheWeekAdapter.getPosition(text);

        //left button hit
        if(view.getId() == R.id.left_button){
            whichDayOfTheWeek--;

            if(whichDayOfTheWeek<0) {
                whichDayOfTheWeek = 6;
                nextDayOfTheWeek = 0;
                previousDayOfTheWeek = 5;

            }else if(whichDayOfTheWeek == 5) {
                previousDayOfTheWeek = 4;
                nextDayOfTheWeek = 6;

            }else if(whichDayOfTheWeek==0){
                previousDayOfTheWeek = 6;
                nextDayOfTheWeek = 1;

            }else{
                nextDayOfTheWeek--;
                previousDayOfTheWeek--;

            }

            leftButton.setText(days[previousDayOfTheWeek]);
            rightButton.setText(days[nextDayOfTheWeek]);
            daysOfTheWeek.setSelection(whichDayOfTheWeek);
            notesForTheDay.setText(savedNotes.getString(days[whichDayOfTheWeek], ""));

        //right button hit
        }else if(view.getId() == R.id.right_button){
            whichDayOfTheWeek++;

            if(whichDayOfTheWeek > 6) {
                whichDayOfTheWeek = 0;
                previousDayOfTheWeek = 6;
                nextDayOfTheWeek = 1;

            }else if(whichDayOfTheWeek==1) {
                previousDayOfTheWeek = 0;
                nextDayOfTheWeek = 2;

            }else if(whichDayOfTheWeek == 6){
                previousDayOfTheWeek++;
                nextDayOfTheWeek = 0;

            }else{
                previousDayOfTheWeek++;
                nextDayOfTheWeek++;

            }

            leftButton.setText(days[previousDayOfTheWeek]);
            rightButton.setText(days[nextDayOfTheWeek]);
            daysOfTheWeek.setSelection(whichDayOfTheWeek);
            notesForTheDay.setText(savedNotes.getString(days[whichDayOfTheWeek], ""));

        //save button hit
        }else{
            savedNotesEditor.putString(days[whichDayOfTheWeek], notesForTheDay.getText().toString());
            savedNotesEditor.apply();
            Toast.makeText(this, "Notes saved!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String text = daysOfTheWeek.getSelectedItem().toString();
        whichDayOfTheWeek = daysOfTheWeekAdapter.getPosition(text);
        //normal case
        if(whichDayOfTheWeek < 6 && whichDayOfTheWeek > 0){
            previousDayOfTheWeek = whichDayOfTheWeek-1;
            nextDayOfTheWeek = whichDayOfTheWeek+1;

        //if day is last day of the week
        }else if(whichDayOfTheWeek == 6){
            previousDayOfTheWeek = 5;
            nextDayOfTheWeek = 0;

        //if day is first day of the week
        }else{
            previousDayOfTheWeek = 6;
            nextDayOfTheWeek = 1;
        }

        leftButton.setText(days[previousDayOfTheWeek]);
        rightButton.setText(days[nextDayOfTheWeek]);
        notesForTheDay.setText(savedNotes.getString(days[whichDayOfTheWeek], ""));

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
