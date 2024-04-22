package com.example.sudoku;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout numberPadLinearLayout;
    TableLayout numberPadTableLayout;
    TableRow[] numberPadTableRows;
    TextView title;
    Button[] numberPadbuttons;

    TableLayout mainTableLayout;
    TableRow[] mainTableRows;
    CustomButton[][] customButtons;

    public int clickRow, clickCol;

    FrameLayout memoNumperPadFrameLayout;

    public int playCount = 1;
    long startTime;
    long endTime;

    public int randomLevel = 56;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickCol = 10;
        clickRow = 10;

        // number pad
        numberPadLinearLayout = new LinearLayout(this);
        numberPadTableLayout = new TableLayout(this);

        numberPadTableRows = new TableRow[5];
        numberPadbuttons = new Button[12]; // 1~9, 10, 11

        numberPadTableLayout.setGravity(Gravity.CENTER);
        numberPadTableLayout.setBackgroundColor(Color.WHITE);

        for (int i = 0; i < 5; i++) {
            numberPadTableRows[i] = new TableRow(this);
            numberPadTableRows[i].setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            numberPadTableRows[i].setPadding(15, 5, 15, 5);
            numberPadTableRows[i].setGravity(Gravity.CENTER);
            numberPadTableLayout.addView(numberPadTableRows[i]);
        }

        title = new TextView(this);
        title.setText("Input Number");

        numberPadTableRows[0].addView(title);

        for (int i = 1; i <= 9; i++) {
            numberPadbuttons[i] = new Button(this);
            numberPadbuttons[i].setText(Integer.toString(i));
        }


        // tableRow에 button 추가하기
        numberPadTableRows[1].addView(numberPadbuttons[1]);
        numberPadTableRows[1].addView(numberPadbuttons[2]);
        numberPadTableRows[1].addView(numberPadbuttons[3]);

        numberPadTableRows[2].addView(numberPadbuttons[4]);
        numberPadTableRows[2].addView(numberPadbuttons[5]);
        numberPadTableRows[2].addView(numberPadbuttons[6]);

        numberPadTableRows[3].addView(numberPadbuttons[7]);
        numberPadTableRows[3].addView(numberPadbuttons[8]);
        numberPadTableRows[3].addView(numberPadbuttons[9]);

        numberPadTableRows[4].setGravity(Gravity.END);

        numberPadbuttons[10] = new Button(this);
        numberPadbuttons[11] = new Button(this);
        numberPadbuttons[10].setText("CANCEL");
        numberPadbuttons[11].setText("DEL");
        numberPadTableRows[4].addView(numberPadbuttons[10]);
        numberPadTableRows[4].addView(numberPadbuttons[11]);


        numberPadLinearLayout.setBackgroundColor(Color.argb(50, 0, 0, 0));
        numberPadLinearLayout.addView(numberPadTableLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        numberPadLinearLayout.setOrientation(LinearLayout.VERTICAL);
        numberPadLinearLayout.setGravity(Gravity.CENTER);
        numberPadLinearLayout.setClickable(true);
        numberPadLinearLayout.setFocusable(true);
        numberPadLinearLayout.setFocusableInTouchMode(true);

        FrameLayout f = (FrameLayout)findViewById(R.id.frameLayout);
        f.addView(numberPadLinearLayout);

        numberPadLinearLayout.setVisibility(View.INVISIBLE);


        // memo number pad 만들기
        LayoutInflater layoutInflater =
                (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        memoNumperPadFrameLayout = (FrameLayout) layoutInflater.inflate(R.layout.acitivity_memopad, f, true);
        memoNumperPadFrameLayout.setVisibility(View.VISIBLE);





        // main game
        // 초기화
        mainTableLayout = new TableLayout(this);
        mainTableLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainTableLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        mainTableRows = new TableRow[9];
        customButtons = new CustomButton[9][9];

        mainTableLayout.setBackgroundColor(Color.GRAY);



        for (int i = 0; i < 9; i++) {
            mainTableRows[i] = new TableRow(this);
            mainTableRows[i].setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));

            if (i % 3 == 2) mainTableRows[i].setPadding(30, 5, 30, 30);
            else if (i == 0) mainTableRows[i].setPadding(30, 30, 30, 0);
            else mainTableRows[i].setPadding(30, 5, 30, 5);

            mainTableLayout.addView(mainTableRows[i]);
        }

        // 버튼 생성하기

        // layoutparams
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );
        layoutParams.setMargins(5, 5, 5, 5);

        TableRow.LayoutParams layoutParams2 = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );
        layoutParams2.setMargins(5, 5, 15, 5);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                customButtons[i][j] = new CustomButton(this, i, j);

                if (j == 2 || j == 5) customButtons[i][j].setLayoutParams(layoutParams2);
                else customButtons[i][j].setLayoutParams(layoutParams);

                mainTableRows[i].addView(customButtons[i][j]);
            }
        }

        // 랜덤으로 가리기
        Random random = new Random(); // 랜덤 객체 생성
        random.setSeed(System.currentTimeMillis());
        BoardGenerator board = new BoardGenerator();

        int count = 0;
        while(count <= randomLevel) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            int number = board.get(row, col);

            if(customButtons[row][col].fixed == true) continue;
            else {
                customButtons[row][col].set(number);
                customButtons[row][col].fixed = true;
                customButtons[row][col].textView.setTypeface(customButtons[row][col].textView.getTypeface(), Typeface.BOLD);
                customButtons[row][col].setClickable(false);
                customButtons[row][col].textView.setClickable(false);
                customButtons[row][col].textView.setTextColor(Color.BLACK);
                customButtons[row][col].setBackgroundColor(Color.WHITE);
                count = count + 1;
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (customButtons[i][j].value == 0) {

                    customButtons[i][j].textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { // 이 매개변수 view는 텍스트 뷰이다.
//                            Log.i("여기", view.getParent().toString());
                            CustomButton customButton = (CustomButton) view.getParent();
                            clickRow = customButton.row;
                            clickCol = customButton.col;

                            numberPadLinearLayout.setVisibility(View.VISIBLE);
                            numberPadLinearLayout.bringToFront();
                        }
                    });


                    customButtons[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CustomButton cb = (CustomButton) view;
                            clickRow = cb.row;
                            clickCol = cb.col;

                            numberPadLinearLayout.setVisibility(View.VISIBLE);
                            numberPadLinearLayout.bringToFront();
                        }
                    });

                    customButtons[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {

                            CustomButton cb = (CustomButton) view;
                            clickRow = cb.row;
                            clickCol = cb.col;

                            if (customButtons[clickRow][clickCol].value == 0) {
                                startActivityForResult(new Intent(MainActivity.this, MemoPadActivity.class), 0);
                            }
                            // 메모 넘버 패드 올라감
                            return true;
                        }


                    });
                    customButtons[i][j].textView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            CustomButton customButton = (CustomButton) view.getParent();
                            clickRow = customButton.row;
                            clickCol = customButton.col;

                            if (customButtons[clickRow][clickCol].value == 0) {
                                startActivityForResult(new Intent(MainActivity.this, MemoPadActivity.class), 0);
                            }

                            // 메모 넘버 패드 올라감
                            return true;
                        }
                    });


                 }
            }
        }


        f.addView(mainTableLayout);

        numberPadbuttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPadbuttonsClick(clickRow, clickCol, 1);
            }
        });
        numberPadbuttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPadbuttonsClick(clickRow, clickCol, 2);
            }
        });
        numberPadbuttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPadbuttonsClick(clickRow, clickCol, 3);
            }
        });
        numberPadbuttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPadbuttonsClick(clickRow, clickCol, 4);
            }
        });
        numberPadbuttons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPadbuttonsClick(clickRow, clickCol, 5);
            }
        });
        numberPadbuttons[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPadbuttonsClick(clickRow, clickCol, 6);
            }
        });
        numberPadbuttons[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPadbuttonsClick(clickRow, clickCol, 7);
            }
        });
        numberPadbuttons[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPadbuttonsClick(clickRow, clickCol, 8);
            }
        });
        numberPadbuttons[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPadbuttonsClick(clickRow, clickCol, 9);
            }
        });

        numberPadbuttons[10].setOnClickListener(new View.OnClickListener() { // CANCEL
            @Override
            public void onClick(View view) {
                numberPadLinearLayout.setVisibility(View.INVISIBLE);
                customButtons[clickRow][clickCol].memoTablelayout.bringToFront();
            }
        });
        numberPadbuttons[11].setOnClickListener(new View.OnClickListener() { // DEL
            @Override
            public void onClick(View view) {
                numberPadbuttonsClick(clickRow, clickCol, 0);
                unsetConflict(clickRow, clickCol);
                customButtons[clickRow][clickCol].memoTablelayout.bringToFront();

            }
        });
        startTime = System.currentTimeMillis();
        Toast.makeText(this.getApplicationContext(),playCount+"번째 게임 시작합니다.", Toast.LENGTH_LONG).show();
    }



    public void numberPadbuttonsClick(int row, int col, int number) {
        customButtons[row][col].set(number);
        numberPadLinearLayout.setVisibility(View.INVISIBLE);

        if (number != 0) customButtons[row][col].setMemoTablelayoutInvisible();

        checkConflict();
        checkBox(row, col);

        if (isFinish() == true) {
            endTime = System.currentTimeMillis();
            Toast.makeText(this.getApplicationContext(),"게임이 종료되었습니다.\n축하드립니다.", Toast.LENGTH_LONG).show();
            String tmp = (endTime-startTime)+"ms가 소요되었습니다.";
            Toast.makeText(this.getApplicationContext(),tmp, Toast.LENGTH_LONG).show();

        }
    }

    public void checkConflict() {
        // Log.i("", "checkConflict 시작");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                unsetConflict(i, j);
            }
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (customButtons[row][col].value == 0) continue;
                if (checkRow(row, col) == true || checkCol(row, col) == true) {
                    setConflict(row, col);
                }
            }
        }
    }

    public void checkBox(int row, int col) {
        checkBoxInDetail(0, 2, 0, 2);
        checkBoxInDetail(0, 2, 3, 5);
        checkBoxInDetail(0, 2, 6, 8);
        checkBoxInDetail(3, 5, 0, 2);
        checkBoxInDetail(3, 5, 3, 5);
        checkBoxInDetail(3, 5, 6, 8);
        checkBoxInDetail(6, 8, 0, 2);
        checkBoxInDetail(6, 8, 3, 5);
        checkBoxInDetail(6, 8, 6, 8);


    }


    public boolean checkBoxInDetail(int startrow, int endrow, int startcol, int endcol) {
        // 방금 바뀐 row col box만 전체 검사할 것이다.
        // Log.i("checkBoxInDetail", startrow+" "+endrow+" "+startcol+" "+endcol);

        boolean flag = false;

        for (int i = startrow; i <= endrow; i++) {
            for (int j = startcol; j <= endcol; j++) {
                flag = false;

                int pivot = customButtons[i][j].value;
                if (pivot == 0) continue;

                for (int m = startrow; m <= endrow; m++) {
                    for (int n = startcol; n <= endcol; n++) {
                        if (i == m && j == n) continue;
                        if (customButtons[m][n].value == pivot) {
                            flag = true;

                            // Log.i("checkBox", i+","+j+"/"+m+","+n);
                        }
                    }
                }

                if (flag == true) setConflict(i, j);

            }
        }
        return flag;
    }


    public boolean checkRow(int row, int col) { // 행
        boolean flag = false;
        int pivot = customButtons[row][col].value;
        for (int i = 0; i < 9; i++) {
            if (i == col) continue;
            if (customButtons[row][i].value == pivot) {
                flag = true;
                // Log.i("checkRow", row+","+i+"/"+row+","+col);
                return true;

            }
        }
        return flag;
    }



    public boolean checkCol(int row, int col) { // 열
        boolean flag = false;

        int pivot = customButtons[row][col].value;
        for (int i = 0; i < 9; i++) {

            if (i == row) continue;
            if (customButtons[i][col].value == pivot) {
                // Log.i("checkCol", i+","+col+"/"+row+","+col);

                flag = true;
                return true;
            }
        }
        return flag;

    }

    public boolean isFinish() {
        // Log.i("isFinish", "이제 검사 시작 ");
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (customButtons[row][col].isClickable()) {
                    if (customButtons[row][col].conflict == true) return false;
                    if (customButtons[row][col].value == 0) return false;
                }
            }
        }
        return true;
    }

    public void setConflict(int row, int col) {
        customButtons[row][col].conflict = true;
        customButtons[row][col].setBackgroundResource(R.drawable.button_conflict);
    }

    public void unsetConflict(int row, int col) {
        customButtons[row][col].conflict = false;
        customButtons[row][col].setBackgroundResource(R.drawable.button_selector);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Toast.makeText(getApplicationContext(), "onActivityResult",Toast.LENGTH_LONG).show();

        if (resultCode == 0) { // del

            if (data.getStringExtra("button").equals("delete")) {
                // Log.i("button", "delete");

                customButtons[clickRow][clickCol].setMemoTablelayoutInvisible();
            }
            else if (data.getStringExtra("button").equals("cancel")) {
                // Log.i("button", "cancel.");

                return;
            }
            else if (data.getStringExtra("button").equals("ok")) {
                // Log.i("button", "ok");

                boolean[] booleans;
                booleans = data.getBooleanArrayExtra("booleans");

                customButtons[clickRow][clickCol].setMemoTablelayoutVisible(booleans);
                // Log.i("ok", "여기다ㅠ");
            }
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.itemReset:
                reset();
                return true;
            case R.id.itemRestart:
                restart();
                return true;
            case R.id.itemLevelup:
                if (randomLevel < 20) {
                    Toast.makeText(this.getApplicationContext(),"더 이상 레벨을 높일 수 없습니다.", Toast.LENGTH_LONG).show();
                }
                else {
                    randomLevel = randomLevel - 5;
                }
                restart();
                return true;
            case R.id.itemLeveldown:
                if (randomLevel > 80) {
                    Toast.makeText(this.getApplicationContext(),"더 이상 레벨을 낮출 수 없습니다.", Toast.LENGTH_LONG).show();
                }
                else {
                    randomLevel = randomLevel + 5;
                }
                restart();
                return true;
        }
        return false;
    }

    public void reset() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (customButtons[i][j].fixed == false) {
                    customButtons[i][j].set(0);
                    customButtons[i][j].setMemoTablelayoutInvisible();
                }
                unsetConflict(i, j);
            }
        }
    }

    public void restart() {
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                customButtons[i][j].set(0);
                customButtons[i][j].setClickable(true);
                customButtons[i][j].fixed = false;
                customButtons[i][j].setBackgroundResource(R.drawable.button_selector);
                customButtons[i][j].setMemoTablelayoutInvisible();
                customButtons[i][j].setLongClickable(true);
                customButtons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        return;
                    }
                });
                customButtons[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return false;
                    }
                });


                customButtons[i][j].textView.setClickable(true);
                customButtons[i][j].textView.setBackgroundResource(R.drawable.button_selector_textview);
                customButtons[i][j].textView.setTypeface(customButtons[i][j].textView.getTypeface(), Typeface.NORMAL);
                customButtons[i][j].textView.setTextColor(Color.DKGRAY);
                customButtons[i][j].textView.setLongClickable(true);
                customButtons[i][j].textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        return;
                    }
                });
                customButtons[i][j].textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return false;
                    }
                });
            }
        }
        Log.i("위치 확인", "Random random = new Random()");
        // 랜덤으로 가리기
        Random random = new Random(); // 랜덤 객체 생성
        random.setSeed(System.currentTimeMillis());
        BoardGenerator board = new BoardGenerator();

        int count = 0;
        while(count <= randomLevel) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            int number = board.get(row, col);

            if(customButtons[row][col].fixed == true) continue;
            else {
                customButtons[row][col].set(number);
                customButtons[row][col].fixed = true;
                customButtons[row][col].textView.setTypeface(customButtons[row][col].textView.getTypeface(), Typeface.BOLD);
                customButtons[row][col].textView.setTextColor(Color.BLACK);
                customButtons[row][col].setBackgroundColor(Color.WHITE);
                count = count + 1;
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (customButtons[i][j].fixed  == false) {
                    customButtons[i][j].textView.setTypeface(customButtons[i][j].textView.getTypeface(), Typeface.NORMAL);
                    customButtons[i][j].textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { // 이 매개변수 view는 텍스트 뷰이다.
//                            Log.i("여기", view.getParent().toString());
                            CustomButton customButton = (CustomButton) view.getParent();
                            clickRow = customButton.row;
                            clickCol = customButton.col;

                            numberPadLinearLayout.setVisibility(View.VISIBLE);
                            numberPadLinearLayout.bringToFront();
                        }
                    });


                    customButtons[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CustomButton cb = (CustomButton) view;
                            clickRow = cb.row;
                            clickCol = cb.col;

                            numberPadLinearLayout.setVisibility(View.VISIBLE);
                            numberPadLinearLayout.bringToFront();
                        }
                    });

                    customButtons[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {

                            CustomButton cb = (CustomButton) view;
                            clickRow = cb.row;
                            clickCol = cb.col;

                            if (customButtons[clickRow][clickCol].value == 0) {
                                startActivityForResult(new Intent(MainActivity.this, MemoPadActivity.class), 0);
                            }
                            // 메모 넘버 패드 올라감
                            return true;
                        }


                    });
                    customButtons[i][j].textView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            CustomButton customButton = (CustomButton) view.getParent();
                            clickRow = customButton.row;
                            clickCol = customButton.col;

                            if (customButtons[clickRow][clickCol].value == 0) {
                                startActivityForResult(new Intent(MainActivity.this, MemoPadActivity.class), 0);
                            }

                            // 메모 넘버 패드 올라감
                            return true;
                        }
                    });


                }
                else {
                    customButtons[i][j].setClickable(false);
                    customButtons[i][j].textView.setClickable(false);
                    customButtons[i][j].setLongClickable(false);
                    customButtons[i][j].textView.setLongClickable(false);
                }
            }
        }

        playCount++;
        Toast.makeText(this.getApplicationContext(),playCount+"번째 게임 시작합니다.", Toast.LENGTH_LONG).show();
    }
}