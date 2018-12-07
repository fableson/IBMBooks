package com.navitend.books;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.navitend.books.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {


    Button btnXML;
    Button btnJSON;
    TextView tvData;
    String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnXML = (Button) findViewById(R.id.parseXML);
        btnJSON = (Button) findViewById(R.id.parseJSON);
        tvData = (TextView) findViewById(R.id.tvData);
        tvData.setMovementMethod(new ScrollingMovementMethod());


        btnXML.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG,"XML Clicked");
                tvData.setText("attempt to parse XML");
                examineXMLFile();
            }
        });

        btnJSON.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG,"JSON Clicked");
                tvData.setText("attempt to parse JSON");
                examineJSONFile();
            }
        });


    }



    void examineXMLFile() {
        try {
            Log.i(TAG,"Beginning examination of XML data");
            InputSource is = new InputSource(getResources().openRawResource(R.raw.booksxml));

            // create the factory
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // create a parser
            SAXParser parser = factory.newSAXParser();

            // create the reader (scanner)
            XMLReader xmlreader = parser.getXMLReader();

            // instantiate our handler
            bookshandler bh = new bookshandler();

            // assign our handler
            xmlreader.setContentHandler(bh);

            // perform the synchronous parse
            xmlreader.parse(is);

            // should be done... let's display our results
            tvData.setText(Html.fromHtml(bh.getResults()));

        } catch (Exception e) {
            Log.e(TAG, "Error parsing XML data " + e.getMessage());
        }
    }


    void examineJSONFile()
    {
        try
        {
            Log.i(TAG,"Beginning examination of JSON data");
            String x = "";
            InputStream is = this.getResources().openRawResource(R.raw.booksjson);
            byte [] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            String jsontext = new String(buffer);
            JSONArray entries = new JSONArray(jsontext);

            x = "encouraggework.com book list in JSON parsed.\nThere are [" + entries.length() + "] books\n\n";

            int i;
            for (i=0;i<entries.length();i++)
            {
                JSONObject book = entries.getJSONObject(i);
                x += "------------\n";
                x += "Title:" + book.getString("title") + "\n";
                x += "Author:" + book.getString("author") + "\n\n";
            }
            tvData.setText(x);
        }
        catch (Exception je)
        {
            tvData.setText("Error w/file: " + je.getMessage());
        }
    }

}
