package com.cloudendpoints;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudendpoints.MainActivity.EndpointsTask;
import com.cloudendpoints.messageEndpoint.MessageEndpoint;
import com.cloudendpoints.noteendpoint.Noteendpoint;
import com.cloudendpoints.noteendpoint.model.CollectionResponseNote;
import com.cloudendpoints.noteendpoint.model.Note;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayItems extends ListActivity {
	
	 private TextView tv = null;
     private ArrayList<Map<String,String>> list = null;
     private SimpleAdapter adapter = null;
     private String[] from = { "email", "description" };
     private int[] to = { android.R.id.text1, android.R.id.text2 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tv = new TextView(this);
		tv.setText("Retreived Details");
        tv.setGravity(Gravity.CENTER);
        getListView().addHeaderView(tv);
		new ListItems().execute();
	}

	private class ListItems 
    extends AsyncTask<Void, Void, CollectionResponseNote> {
		Exception exceptionThrown = null;
		protected CollectionResponseNote doInBackground(Void... args) {
		Noteendpoint.Builder endpointBuilder = new Noteendpoint.Builder(
		          AndroidHttp.newCompatibleTransport(),
		          new JacksonFactory(),
		          new HttpRequestInitializer() {
		          public void initialize(HttpRequest httpRequest) { }
		          });
		  Noteendpoint endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
		  try {
			  CollectionResponseNote note=endpoint.listNote().execute();
			  return note;
		  } catch (IOException e) {
			  exceptionThrown=e;
			  e.printStackTrace();
			  return null;
		  }
		      
		    }
		protected void onPostExecute(CollectionResponseNote notes){
		
			 if (exceptionThrown != null) {
			        Log.e(DisplayItems.class.getName(), 
			            "Exception when listing Messages", exceptionThrown);
			        
			      }
			      else {
			    	  ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
                      List<Note> _list = notes.getItems();
                           for (Note note : _list) {
                                   HashMap<String, String> item = new HashMap<String, String>();
                                   item.put("email", note.getEmailAddress());
                                   item.put("description", note.getDescription());
                                list.add(item);
                           }
                           adapter = new SimpleAdapter(DisplayItems.this, list,android.R.layout.simple_list_item_2, from, to);
                           setListAdapter(adapter);
             
			        }
			      }
		
		}
		
  }   



