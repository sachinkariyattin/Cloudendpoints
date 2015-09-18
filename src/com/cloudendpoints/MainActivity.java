package com.cloudendpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudendpoints.noteendpoint.Noteendpoint;
import com.cloudendpoints.noteendpoint.model.Note;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * The Main Activity.
 * 
 * This activity starts up the RegisterActivity immediately, which communicates
 * with your App Engine backend using Cloud Endpoints. It also receives push
 * notifications from backend via Google Cloud Messaging (GCM).
 * 
 * Check out RegisterActivity.java for more details.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final EditText email=(EditText) findViewById(R.id.editText1);
		final EditText description=(EditText) findViewById(R.id.editText2);
		final Button submitButton=(Button) findViewById(R.id.button1);
		
		
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String toEmail=email.getText().toString();
				final String toDescr=description.getText().toString();
				final String[] args={toDescr,toEmail};
				
				new EndpointsTask().execute(args);
			}
		});
		
		
		
	}


public class EndpointsTask extends AsyncTask<String, String, Boolean> {
    protected Boolean doInBackground(String... args) {

      Noteendpoint.Builder endpointBuilder = new Noteendpoint.Builder(
          AndroidHttp.newCompatibleTransport(),
          new JacksonFactory(),
          new HttpRequestInitializer() {
          public void initialize(HttpRequest httpRequest) { }
          });
  Noteendpoint endpoint = CloudEndpointUtils.updateBuilder(
  endpointBuilder).build();
  try {
	  
      Note note = new Note().setDescription(args[0]);
      String noteID = new Date().toString();
      note.setId(noteID);

      note.setEmailAddress(args[1]);
      
      Note result = endpoint.insertNote(note).execute();
  } catch (IOException e) {
    e.printStackTrace();
  }
      return true;
    }
    

    protected void onPostExecute(Boolean res){
    	if(res==true){
    		Toast.makeText(getApplicationContext(), "Pushed Successfully!!",Toast.LENGTH_LONG ).show();
    	}
    }
}
}
