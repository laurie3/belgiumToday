package com.example.belgiumtoday;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class LoginActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setting default screen to login.xml
		setContentView(R.layout.login);

		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);

		// Listening to register new account link
		registerScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
			}
		});

		Button loginBtn = (Button) findViewById(R.id.btnLogin);

		// Listening to login button
		loginBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				EditText userNameEditBox = (EditText) findViewById(R.id.login_username);
				EditText passWordEditBox = (EditText) findViewById(R.id.login_password);
				String username = userNameEditBox.getText().toString();
				String password = passWordEditBox.getText().toString();
				// Switching to main screen if success, stay in login screen
				// with warning if fail
				String returnValue = GetLoginStatus(username, password);
				//returnValue = returnValue.replace("\n", "").replace("\r", "");
				if (returnValue.indexOf(username)!=-1&&returnValue!="") {

					Intent i = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(i);
				} else {
					TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
					registerScreen.setText("µÇÂ¼Ê§°Ü£¬ÇëÖØÐÂµÇÂ¼»ò×¢²á");
					registerScreen.setBackgroundColor(Color.RED);
				}

			}
		});

	}

	public static String GetLoginStatus(String usrname, String pwd) {
		String url = "http://api.betoday.be/User.svc/CheckUserLogin?login="
				+ usrname + "&password=" + pwd;
		InputStream is = null;
		// HTTP
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpclient.execute(getRequest);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			System.out.println(is.toString());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

		// Read response to string
		String result = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();

			// System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			return result;

		}

	}

}
