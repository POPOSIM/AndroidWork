package Android_Examples.AndroidWork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import Android_Examples.AndroidWork.AndroidSelect1.myIntThread;
import Android_Examples.AndroidWork.AndroidSelect2.myIntThread.myThread;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidWorkActivity extends Activity {
	private static final int MENU2 = 2;
	private static final int MENU3 = 3;
	private static final int MENU1 = 1;
	private int Button=1;
	private LayoutInflater inflater;
	private String Password=null,Account=null;
	
	GlobalVariable globalVariable;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		inflater = LayoutInflater.from(AndroidWorkActivity.this);
		
		Button select1 = (Button) findViewById(R.id.select1);
		select1.setTextColor(Color.WHITE);
		Button select2 = (Button) findViewById(R.id.select2);
		select2.setTextColor(Color.WHITE);
		Button select3  = (Button) findViewById(R.id.select3);
		select3.setTextColor(Color.WHITE);
		
		select1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AndroidWorkActivity.this, AndroidSelect1.class);
				
				startActivityForResult(intent,1); // 呼叫新的 Activity
			}
		});

		select2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AndroidWorkActivity.this, AndroidSelect2.class);
				
				startActivity(intent); // 呼叫新的 Activity
			}
		});

		select3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
                Intent intent = new Intent(AndroidWorkActivity.this, AndroidSelect3.class);
				
				startActivity(intent); // 關閉目前 Activity
			}
		});
	}
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	  if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			new AlertDialog.Builder(AndroidWorkActivity.this) 
    		 	.setTitle("關閉程式")
    		 	.setMessage("您確認要關閉且離開程式")
    		 	.setPositiveButton("確認", new DialogInterface.OnClickListener() {

    				
    			    public void onClick(DialogInterface dialog, int which) {
    			    	globalVariable = (GlobalVariable)getApplicationContext();
    					   globalVariable.setGlobalVariable("國北教訪客");
    					   finish(); // 結束程式 
    			    	
    			    }
    			})
    		 	.setNegativeButton("取消", null)
    		 	.show();
    	    
    	    
    	    return true;
    	  }

    	  return super.onKeyDown(keyCode, event);
    	}
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	
	    menu.add(Menu.NONE, MENU1, 0, "學生登入")
	    .setIcon(android.R.drawable.ic_menu_add);
		menu.add(Menu.NONE, MENU2, 0, "關於")
		.setIcon(android.R.drawable.ic_menu_help);
		menu.add(Menu.NONE, MENU3, 0, "結束")
		.setIcon(android.R.drawable.ic_menu_close_clear_cancel);	
		return super.onCreateOptionsMenu(menu);
	}
    public boolean onOptionsItemSelected(MenuItem item) {
		
		
		switch(item.getItemId()) {
		case MENU1:
			
			
			final View v = inflater.inflate(R.layout.student, null);
			EditText A = (EditText) (v.findViewById(R.id.Account));
	    	EditText P = (EditText) (v.findViewById(R.id.Password));
	    	P.setTransformationMethod(PasswordTransformationMethod.getInstance());
			new AlertDialog.Builder(AndroidWorkActivity.this)
			.setTitle("學生登入")
			.setView(v)
			.setNegativeButton("取消", null)
			.setPositiveButton("確定", new DialogInterface.OnClickListener() {

				
			    public void onClick(DialogInterface dialog, int which) {
			    	if(Button==1){	    		         
			    	      Button=0;    	      
			    	      Account=((EditText) (v.findViewById(R.id.Account))).getText().toString();
			    	      Password=((EditText) (v.findViewById(R.id.Password))).getText().toString();
			    	      new myIntThread(Account,"http://1.34.6.116/PostStudent.php");
			    	}
			    	
			    }
			})
			.show();
			
		break;
		
		case MENU2: 
			android.app.AlertDialog.Builder builder =
	        new android.app.AlertDialog.Builder(this); 
            builder.setTitle("關於").setMessage("版本: 1.0版\n作者: 專題小組").setPositiveButton("確定",null).show();         
	    break; 
	    
		case MENU3: 
		   globalVariable = (GlobalVariable)getApplicationContext();
		   globalVariable.setGlobalVariable("國北教訪客");
		   finish(); // 結束程式 
		break;
		
		}
		return super.onOptionsItemSelected((android.view.MenuItem) item);
	}
    
    
    public class myIntThread 
    {
			
   	 
	   private static final int HANDLER_OK   = 0;
	   private static final int HANDLER_nOK1 = 1;
	   private static final int HANDLER_nOK2 = 2;
	   
       
   	   private String Card=null,Url=null;
   	   
   	   public myIntThread(String card,String url)
   	   {
   		   Card=card;
   		   Url=url;
   		   new myThread().start();
   		  
   	   }
   	    
   	 
   		
   	    class myThread extends Thread
   	    {
   	    	public void run()
   	    	{
   	    		Message msg = new Message();
   	    		
   	  
   	    		String GetData=null;
   	    		GetData=sendPostDataToInternet(Card,Url);
   	    		if(GetData==null){
   	    			msg.what = HANDLER_nOK1;
   	    			h.sendMessage(msg);
   	    			
   	    		}
   	    		else if(GetData.length()==0){
   	    			msg.what = HANDLER_nOK2;
   	    			h.sendMessage(msg);
   	    			
   	    			
   	    		}
   	    	    else{
   	    	    	
   	    	    	msg.what = HANDLER_OK;
   	    	    	msg.obj = GetData;
   	    		    h.sendMessage(msg);
   	    		    
   	    	    }
   	    	}
   	    }
   	    
   	    
   	    Handler h = new Handler(){
   	    	
   	    	@Override
			public void handleMessage (Message msg)
   	    	{
   	    		switch(msg.what)
   	    		{
   	    		case HANDLER_OK:
   	    			Button=1;
   	    			String Temp1[]  =  msg.obj.toString().split("<1>");
   	    			if(Temp1[2].equals(Password)){
   	    				Toast.makeText(AndroidWorkActivity.this, "登入成功，歡迎回來"+Temp1[0]+"同學", Toast.LENGTH_LONG).show();
   	    				globalVariable = (GlobalVariable)getApplicationContext();
   	    		        globalVariable.setGlobalVariable(Temp1[0]+"同學");
   	    			}
   	    			else{
   	    				Toast.makeText(AndroidWorkActivity.this, "輸入帳號或密碼錯誤", Toast.LENGTH_LONG).show();
   	    				
   	    			}
   	    			break;
   	    		case HANDLER_nOK1:
   	    			Button=1;
   	    			Toast.makeText(AndroidWorkActivity.this, "網路連線失敗", Toast.LENGTH_LONG).show();
   	    			
   	    			break;
   	    		case HANDLER_nOK2:
   	    			Button=1;
   	    			Toast.makeText(AndroidWorkActivity.this, "輸入帳號或密碼錯誤", Toast.LENGTH_LONG).show();
   	    			
   	    		    break;
   	    		}
   	    	}
   	    };
   	    
   	    private String sendPostDataToInternet(String strTxt,String uriAPI){
   	   	 
   	    	HttpPost httpRequest = new HttpPost(uriAPI);    	
   	    	List<NameValuePair> params = new ArrayList<NameValuePair>();
   	    	params.add(new BasicNameValuePair("data",strTxt));
   	    	try{
   	    		httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
   	    		HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
   	    		
   	    		if(httpResponse.getStatusLine().getStatusCode()==200)
   	    		{
   	        		String strResult = EntityUtils.toString(httpResponse.getEntity());
   	        		
   	        		return strResult;
   	        		
   	        	}
   	    		else
   	    		{
   	    			Log.d("Http","Error State:"+httpResponse.getStatusLine().toString()+ "\n");
   	            }
   	    		
   	    	}
   	    	
   	    	catch(ClientProtocolException e){
   	    		e.printStackTrace();
   	    		Log.d("Http","Error State:"+e.getMessage().toString()+ "\n");
   	    	}
   	    	
   	    	catch(IOException e){
   	    		e.printStackTrace();
   	    		Log.d("Http","Error State:"+e.getMessage().toString()+ "\n");
   	    		
   	    	}
   	    	catch (Exception e){
   	            e.printStackTrace();
   	            Log.d("Http","Error State:"+e.getMessage().toString()+ "\n");
   	        }
   	    	
   	        return null;	
   	    	 
   	    }
    }
    
}
