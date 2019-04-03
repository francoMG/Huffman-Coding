package com.temp.user.matecomputacional_huffman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.mainButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText input = (EditText)findViewById(R.id.mainInput);
                TextView toModify = (TextView)findViewById(R.id.mainOutput);
                String text = input.getText().toString();

                String freqs = "";

                HashMap<Character, String> code = Compress.compress(text);
                for (HashMap.Entry<Character, String> entry : code.entrySet()) {
                    freqs += "\n" + (entry.getKey() + " : " + entry.getValue());
                }

                System.out.println(freqs);

                toModify.setText(freqs);
            }
        });
    }
}
