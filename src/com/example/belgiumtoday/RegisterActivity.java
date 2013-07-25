package com.example.belgiumtoday;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class RegisterActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
 
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
 
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });
        
        
        
        Button registerButton = (Button) findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Button registerButton = (Button) findViewById(R.id.btnRegister);
				EditText emailTextBox = (EditText) findViewById(R.id.reg_email);
		        if(GetRegisterStatus(emailTextBox.getText().toString()).indexOf("true")!=-1){
		        	registerButton.setText("邮箱已存在，请重新注册");
		        	registerButton.setTextColor(Color.RED);

		        }
		        else{
		        	//email can be used for further registeration
		        	Intent i = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(i);
		        }
				
				
			}
		});
    }
    
    public static String GetRegisterStatus(String email){
    	String url = "http://api.betoday.be/User.svc/CheckEmailExisted?email="+email;
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
