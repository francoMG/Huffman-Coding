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
        EditText et = (EditText)findViewById(R.id.mainInput);
        et.setText("Tengo un piso enladrillado. Quien lo desenladrillará. El desenladrillador que lo desenladrille buen desenladrillador será.");


        Button button = (Button) findViewById(R.id.mainButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText input = (EditText)findViewById(R.id.mainInput);
                TextView toModify = (TextView)findViewById(R.id.mainOutput);
                String text = input.getText().toString();

                String freqs = "";

                HashMap<Character, String> code = Compress.compress(text);

                String textComp = "";
                for(int i = 0; i < text.length(); i++)
                    textComp += code.get(text.charAt(i));


                int sz = text.length();
                int szc = textComp.length() / 8;
                double por = (szc*100/sz);

                freqs +=  "\nTamaño normal        : " + sz + " bytes";
                freqs +=  "\nTamaño comprimido : " + szc + " bytes";
                freqs +=  "\nReducido al              : " + String.format("%.2f", por) + "%";
                freqs +=  "\n";

                for (HashMap.Entry<Character, String> entry : code.entrySet())
                    freqs += "\n" + (entry.getKey() + " : " + entry.getValue());

                freqs += "\n\nMensaje comprimido:\n\n";
                freqs += textComp;

                toModify.setText(freqs);
            }
        });
    }
}
