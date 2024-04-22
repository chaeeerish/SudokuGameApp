package com.example.sudoku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MemoPadActivity extends AppCompatActivity {

    ToggleButton[] toggleButtons;
    Button[] memoPadButtons;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_memopad);

        Intent getIntent = getIntent();

        toggleButtons = new ToggleButton[10];
        memoPadButtons = new Button[3];

        toggleButtons[1] = (ToggleButton) findViewById(R.id.toggleButton1);
        toggleButtons[2] = (ToggleButton) findViewById(R.id.toggleButton2);
        toggleButtons[3] = (ToggleButton) findViewById(R.id.toggleButton3);
        toggleButtons[4] = (ToggleButton) findViewById(R.id.toggleButton4);
        toggleButtons[5] = (ToggleButton) findViewById(R.id.toggleButton5);
        toggleButtons[6] = (ToggleButton) findViewById(R.id.toggleButton6);
        toggleButtons[7] = (ToggleButton) findViewById(R.id.toggleButton7);
        toggleButtons[8] = (ToggleButton) findViewById(R.id.toggleButton8);
        toggleButtons[9] = (ToggleButton) findViewById(R.id.toggleButton9);

        memoPadButtons[0] = (Button) findViewById(R.id.delete_button); // del
        memoPadButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("button", "delete");
                setResult(0, intent);
                finish();
            }
        });

        memoPadButtons[1] =  (Button) findViewById(R.id.cancel_button); // cancel
        memoPadButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("button", "cancel");
                setResult(0, intent);
                finish();
            }
        });

        memoPadButtons[2] =  (Button) findViewById(R.id.ok_button); // ok
        memoPadButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log.i("button", "ok가 눌렸습니다.");

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                boolean[] booleans = new boolean[10];
                booleans[0] = false;
                for (int i = 1; i < 10; i++) {
                    if (toggleButtons[i].isChecked() == true) booleans[i] = true;
                    else booleans[i] = false;
                }
                intent.putExtra("button", "ok");
                intent.putExtra("booleans", booleans);
                setResult(0, intent);
                finish();


            }
        });


    }
}
