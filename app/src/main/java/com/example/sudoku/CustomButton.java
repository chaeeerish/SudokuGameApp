package com.example.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Dimension;

public class CustomButton extends FrameLayout {
    public int row, col, value;
    public boolean conflict;
    public boolean fixed;
    // 여기서의 value는 값이 아니라, 앞에 보여져 있는 값이다.
    TextView textView;

    TableLayout memoTablelayout;
    TableRow[] memoTableRows;
    TextView[] memoTextViews;

    int previous = 10;

    public CustomButton(Context context, int row, int col) {
        super(context);
        this.row = row;
        this.col = col;
        this.conflict = false;
        this.value = 0;
        this.fixed = false;

        textView = new TextView(context);


        this.setClickable(true);
        this.setBackgroundResource(R.drawable.button_selector);
        this.setForegroundGravity(Gravity.CENTER);
        this.setPadding(5, 5, 5, 5);

        textView.setText("");
        textView.setClickable(true);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.button_selector_textview);
        textView.setTextSize(Dimension.DP, 50);
        textView.setTextColor(Color.DKGRAY);



        this.addView(textView);


        memoTablelayout = new TableLayout(context);
        memoTablelayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        memoTablelayout.setBackgroundColor(Color.argb(0, 0, 0, 0));

        memoTableRows = new TableRow[3];
        memoTextViews = new TextView[10];

        for (int i = 0; i < 3; i++) {
            memoTableRows[i] = new TableRow(context);
            memoTableRows[i].setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            memoTableRows[i].setGravity(Gravity.CENTER);
            memoTablelayout.addView(memoTableRows[i]);

        }


        for (int i = 1; i < 10; i++) {
            memoTextViews[i] = new TextView(context);
            memoTextViews[i].setText(Integer.toString(i));
            memoTextViews[i].setTextSize(Dimension.DP, 50);
            memoTextViews[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
            memoTextViews[i].setGravity(Gravity.CENTER);
//            memoTextViews[i].setWidth();
        }


        memoTableRows[0].addView(memoTextViews[1]);
        memoTableRows[0].addView(memoTextViews[2]);
        memoTableRows[0].addView(memoTextViews[3]);

        memoTableRows[1].addView(memoTextViews[4]);
        memoTableRows[1].addView(memoTextViews[5]);
        memoTableRows[1].addView(memoTextViews[6]);

        memoTableRows[2].addView(memoTextViews[7]);
        memoTableRows[2].addView(memoTextViews[8]);
        memoTableRows[2].addView(memoTextViews[9]);


        memoTablelayout.setBackgroundResource(R.drawable.button_selector2);
        memoTablelayout.setVisibility(VISIBLE);
        this.addView(memoTablelayout);
        // memoTablelayout.bringToFront();

        setMemoTablelayoutInvisible();

    }

    public void set(int a) {
        if (a == 0) {
            value = 0;
            textView.setText("");
        }
        else {
            value = a;
            textView.setText(Integer.toString(a));
        }

    }

    public void setMemoTablelayoutInvisible() {
        // Log.i("custombuttons: ", "setMemoTablelayoutInVisible");

        for (int i = 1; i < 10; i++) {
            memoTextViews[i].setVisibility(INVISIBLE);
        }
    }

    public void setMemoTablelayoutVisible(boolean[] booleans) {
        // Log.i("custombuttons: ", "setMemoTablelayoutVisible");

        setMemoTablelayoutInvisible();
        for (int i = 1; i < 10; i++) {

            if (booleans[i] == true) {
                // Log.i("visible i: ", i + "visible 하는 중");
                memoTextViews[i].setVisibility(VISIBLE);
            }
        }
    }
}
