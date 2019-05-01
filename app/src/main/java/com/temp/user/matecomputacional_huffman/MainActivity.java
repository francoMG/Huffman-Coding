package com.temp.user.matecomputacional_huffman;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.ClipboardManager;

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

                freqs +=  "\nTamaño normal : " + sz + " bytes";
                freqs +=  "\nTamaño comprimido : " + szc + " bytes";
                freqs +=  "\nReducido al : " + String.format("%.2f", por) + "%";
                freqs +=  "\n";

                for (HashMap.Entry<Character, String> entry : code.entrySet())
                    freqs += "\n" + (entry.getKey() + " : " + entry.getValue());

                freqs += "\n\nMensaje comprimido:\n\n";
                freqs += textComp;

                toModify.setText(freqs);
            }
        });

        Button buttonClip = (Button) findViewById(R.id.toClipBoard);
        buttonClip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText input = (EditText)findViewById(R.id.mainInput);
                TextView toModify = (TextView)findViewById(R.id.mainOutput);
                String text = input.getText().toString();

                String freqs = "";

                HashMap<Character, String> code = Compress.compress(text);

                String textComp = "";
                for(int i = 0; i < text.length(); i++)
                    textComp += code.get(text.charAt(i));

                String comp = "";
                String separator = "~";
                comp += (code.size());
                for (HashMap.Entry<Character, String> entry : code.entrySet()) {
                    comp += (separator + entry.getKey() + separator + entry.getValue());
                }
                comp += (separator + textComp);

                final android.content.ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Source Text", comp);
                clipboardManager.setPrimaryClip(clipData);
            }
        });

        Button buttonDesc = (Button) findViewById(R.id.desComp);
        buttonDesc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText input = (EditText)findViewById(R.id.mainInput);
                TextView toModify = (TextView)findViewById(R.id.mainOutput);

                try {
                    String comp = input.getText().toString();
                    String[] parts = comp.split("~");

                    int n = Integer.parseInt(parts[0]);

                    HashMap<String, String> codes = new HashMap<String, String>();
                    for(int i = 1; i <= n; i++) {
                        String key = parts[i*2];
                        codes.put(key, parts[i*2-1]);
                    }

                    String msg = "\nMensaje descomprimido:\n\n";
                    String acum = "";
                    String bins = parts[parts.length-1];

                    for(int i = 0; i < bins.length(); i++) {
                        String s = bins.substring(i, i+1);
                        if (codes.containsKey(acum)) {
                            msg += codes.get(acum);
                            acum = s;
                        } else {
                            acum += s;
                        }
                    }
                    if (codes.containsKey(acum))
                        msg += codes.get(acum);
                    toModify.setText(msg);
                }
                catch(Exception e) {
                    toModify.setText(e.getMessage());
                }
            }
        });


    }
}
