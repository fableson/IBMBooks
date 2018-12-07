/*
 *
 * fableson@navitend.com
 *
 */

package com.navitend.books;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;



public class bookshandler extends DefaultHandler {

    StringBuilder sb = null;
    String ret = "";
    boolean bStore = false;
    int howMany = 0;


    bookshandler() {
    }

    String getResults()
    {
        return "<p>Books from<br>https://www.encouragework.com/books</p><p>There are [" + howMany + "] books:</p>" + ret;
    }
    @Override

    public void startDocument() throws SAXException {
        // initialize "list"
    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

        try {
            if (localName.equals("book")) {
                this.sb = new StringBuilder("");
                bStore = false;
            }
            if (localName.equals("title") || localName.equals(("author"))) {
                bStore = true;
            }
        } catch (Exception ee) {

            Log.d("error in startElement", ee.getStackTrace().toString());
        }
    }



    @Override

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {

        if (bStore) {
            if (localName.equals("title")) {
                ret += "<u>" + sb.toString() + "</u><br>";
                sb = new StringBuilder("");
                return;

            }

            if (localName.equals("author")) {
                ret += "by:" + sb.toString() + "<br/><br/>";
                sb = new StringBuilder("");
                return;

            }

        }
        if (localName.equals("book")) {
            howMany++;
            bStore = false;
        }
    }



    @Override

    public void characters(char ch[], int start, int length) {

        if (bStore) {
            String theString = new String(ch, start, length);

            this.sb.append(theString);
        }
    }

}

