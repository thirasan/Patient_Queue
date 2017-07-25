package com.example.thirasan.patient_queue;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by thirasan on 7/25/2017 AD.
 */

public class StartActivity extends AppCompatActivity {

    public static int DATABASE_VERSION = 1;

    private CategoryDB cHelper;
    private PatientDB mHelper;

    private List<String> category;

    LinearLayout categoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        this.cHelper = new CategoryDB(this);
        this.categoryList = (LinearLayout)findViewById(R.id.categoryList);

        initCategory();

    }

    @Override
    public void onResume(){
        super.onResume();
        initCategory();
    }

    private void initCategory(){
        this.categoryList.removeAllViews();
        this.category = cHelper.getCategoryList();
        DATABASE_VERSION = this.category.size();

        for(int i=0;i < this.category.size() ; i++){
            if(!this.category.get(i).equals("null")) {
                Button button = new Button(this);
                button.setLayoutParams(new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                button.setText(this.category.get(i));
                final String finalI = (i + 1) + "";
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent main = new Intent(StartActivity.this, MainActivity.class);

                        Button b = (Button) v;
                        String buttonText = b.getText().toString();

                        main.putExtra("table", buttonText + "");
                        main.putExtra("id", finalI);
                        startActivity(main);
                        overridePendingTransition(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                    }
                });

                this.categoryList.addView(button);
            }
        }
    }


    public void addSort(View view) {
        EditText sort = (EditText)findViewById(R.id.sort);
        String sorts = sort.getText().toString();
        if(!this.category.contains(sorts) && !sorts.equals("")) {
            cHelper.addCategory(sorts);
            initCategory();

            mHelper = new PatientDB(this);
        }
        sort.setText("");
    }
}
