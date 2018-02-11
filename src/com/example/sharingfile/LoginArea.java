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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginArea extends Activity {
EditText gmail,password;
Button login,signup;
String mail,passcode,Tag,error,Results;

ConnectionDetector cd;
Boolean isInternetPresent=false;
JSONParser jsonParser=new JSONParser();
ProgressDialog progressDialog;
CommonIpadd commonIpadd;

SharedPreferences fortabelpreference;
SharedPreferences.Editor fortabeleditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_area);
		
		gmail=(EditText)findViewById(R.id.gmail);
		password=(EditText)findViewById(R.id.password);
		
		login=(Button)findViewById(R.id.login);
		signup=(Button)findViewById(R.id.signup);
		
		cd=new ConnectionDetector(getApplicationContext());
		commonIpadd=new CommonIpadd();
		
		 fortabelpreference = getApplicationContext().getSharedPreferences("TABELNAME", Context.MODE_PRIVATE);
		
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				mail=gmail.getText().toString();
				passcode=password.getText().toString();
				
				
				if(mail.equalsIgnoreCase("")||passcode.equalsIgnoreCase("")){
					Toast.makeText(getApplicationContext(), "Fill Fields First", Toast.LENGTH_SHORT).show();
				}
				else{
					Tag="logins";
					new check().execute();
				}
				
			}
		});
		
		
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginArea.this,Dynamictables.class);
				startActivity(intent);
			}
		});
		
	}

class check extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(LoginArea.this);
			progressDialog.setMessage("Logging In..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("tag", Tag));
				param.add(new BasicNameValuePair("email", mail));
				param.add(new BasicNameValuePair("password", passcode));
				
				
			
			
				
				JSONObject json=jsonParser.makeHttpRequest(commonIpadd.url, "POST", param);
				try 
				{
					final String Result=json.getString("dbtabelname");
					  Results=json.getString("result");
					 error=json.getString("error");
					
					 fortabeleditor=fortabelpreference.edit();
						fortabeleditor.remove("tabel");
						fortabeleditor.clear();
						fortabeleditor.commit();
						
						fortabeleditor=fortabelpreference.edit();
						 fortabeleditor.putString("tabel", Result);
						 fortabeleditor.commit();
					
					
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
			
			if(error.equals("0")){
				
				Toast.makeText(getApplicationContext(), Results, Toast.LENGTH_SHORT).show();
				
				Intent intent=new Intent(LoginArea.this,Uplaodvideofile.class);
				startActivity(intent);
				
			}
			else{
				Toast.makeText(getApplicationContext(), Results, Toast.LENGTH_SHORT).show();
			}
			
			
			
		}
	
	
	}


}
