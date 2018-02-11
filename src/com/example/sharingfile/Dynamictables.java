package com.example.sharingfile;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dynamictables extends Activity {
	ConnectionDetector cd;
	Boolean isInternetPresent=false;
	JSONParser jsonParser=new JSONParser();
	ProgressDialog progressDialog;
	CommonIpadd commonIpadd;
	String Tag,username,passcode,email,emailtabel=null;
	Button createtable;
	EditText name,password,mail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamictables);
		createtable=(Button)findViewById(R.id.createtable);
		
		name=(EditText)findViewById(R.id.name);
		password=(EditText)findViewById(R.id.password);
		mail=(EditText)findViewById(R.id.mail);
		
		cd=new ConnectionDetector(getApplicationContext());
		commonIpadd=new CommonIpadd();
		
		
		
		createtable.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				username=name.getText().toString();
				passcode=password.getText().toString();
				email=mail.getText().toString();
				
				String[] For_split_email=email.split("[@|\\.|_]");
		        for (int j = 0; j <= For_split_email.length - 1; j++) {
		        	
		        	 emailtabel=For_split_email[0];
		        	
		     
		         }
				
				
				if(username.equalsIgnoreCase("")||passcode.equalsIgnoreCase("")||email.equalsIgnoreCase("")){
					
					Toast.makeText(getApplicationContext(), "Fill All Fields First",Toast.LENGTH_SHORT).show();
				}
				else{
				
				Tag="createtable";
				
				new check().execute();
				}
			}
		});
		
	}

	class check extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Dynamictables.this);
			progressDialog.setMessage("Logging In..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("tag", Tag));
				param.add(new BasicNameValuePair("username", username));
				param.add(new BasicNameValuePair("password", passcode));
				param.add(new BasicNameValuePair("email", email));
				param.add(new BasicNameValuePair("emailtabel", emailtabel));
			
				
				JSONObject json=jsonParser.makeHttpRequest(commonIpadd.url, "POST", param);
				try 
				{
					final String Result=json.getString("result" );
					//final String id=json.getString("id" );
					
					
					
					//with in doInBackground we must use runOnUiThread for display toast
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), Result, Toast.LENGTH_LONG).show();
							
						}
					});
					
				}
				catch (JSONException e) 
				{
						e.printStackTrace();
				}
				
				
		return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			
			
			name.setText("");
			password.setText("");
			mail.setText("");
			
			Intent intent=new Intent(Dynamictables.this,LoginArea.class);
			startActivity(intent);
			
		//	Intent intent=new Intent(Dynamictables.this,UploadMainPage.class);
			//startActivity(intent);
		}
	
	
	}


}
