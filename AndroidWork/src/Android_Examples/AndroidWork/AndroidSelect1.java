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


import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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



public class AndroidSelect1 extends Activity {
	
	private int Button = 1;
	private static final int SET_HOROSCOPE = 1;

	private static final int MENU1 = 1;
	private static final int MENU2 = 2;
	private static final int MENU3 = 3;

	private ProgressDialog myDialog;
    private NfcAdapter mAdapter;
	private IntentFilter[] mFilters;
	private PendingIntent mPendingIntent;
	private String[][] mTechLists;
	private LayoutInflater inflater;
	android.app.AlertDialog.Builder builder;
	
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_select1);
        
        inflater = LayoutInflater.from(AndroidSelect1.this);
        mAdapter = NfcAdapter.getDefaultAdapter(this);
       
        
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
                    IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
                    
                    try {
                        ndef.addDataType("*/*");
                    } catch (MalformedMimeTypeException e) {
                        throw new RuntimeException("fail", e);
                    }
                    IntentFilter[] mFilters = new IntentFilter[] {
                            ndef,
                    };

                    // Setup a tech list for all NfcF tags
                    String[][] mTechLists = new String[][] { new String[] { MifareClassic.class.getName() } };
                    
                    Intent intent = getIntent();
                    resolveIntent(intent);
         
        
        
    }
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	menu.add(Menu.NONE, MENU1, 0, "使用說明")
		.setIcon(android.R.drawable.ic_menu_add);
		menu.add(Menu.NONE, MENU2, 0, "關於")
		.setIcon(android.R.drawable.ic_menu_help);
		menu.add(Menu.NONE, MENU3, 0, "返回")
		.setIcon(android.R.drawable.ic_menu_close_clear_cancel);	
		return super.onCreateOptionsMenu(menu);
	}
    public boolean onOptionsItemSelected(MenuItem item) {
		
		
		switch(item.getItemId()) {
		case MENU1:
			 builder =new android.app.AlertDialog.Builder(this); 
             builder.setTitle("使用說明").setMessage("將NFC感應器對準TAG，並等待資料輸出。").setPositiveButton("確定",null).show();        
		break;
			
		case MENU2: 
			 builder =new android.app.AlertDialog.Builder(this); 
             builder.setTitle("關於").setMessage("版本: 1.0版\n作者: 專題小組").setPositiveButton("確定",null).show();         
	    break; 
	    
		case MENU3:  
		   finish(); // 結束程式
		break;
		
		}
		return super.onOptionsItemSelected((android.view.MenuItem) item);
	}
    @Override
    protected void onNewIntent(Intent intent) {
       
          resolveIntent(intent);
        
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
    }
    	
    void resolveIntent(Intent intent) {
       
   
        String action = intent.getAction();
   
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
        	
          
           Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
           byte[] id = tagFromIntent.getId();
           
           try {
        	   if(Button==1){
               	Button=0;
                new myIntThread(getHexString(id),"http://1.34.6.116/PostAndroid.php");
               
                myDialog = ProgressDialog.show(AndroidSelect1.this,"","Loading Please wait...",true);
        	   }
             
           }  
           catch (Exception e1) {
              e1.printStackTrace();
           }  
       }
       
   }
    
   public static String getHexString(byte[] b) throws Exception {  
      String result = "";  
      for (int i=0; i < b.length; i++) {  
        result +=  
              Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );  
      }  
      return result;  
   }
    
    
    
    
    
    
    protected void onActivityResult(int A1,int A2,Intent data){
    	super.onActivityResult(A1,A2,data);
    	
    	switch(A1){
    	case SET_HOROSCOPE:
    		if(A2==RESULT_OK){
    			Button=1;
    			myDialog.dismiss();
    			
    		 
    			
    		}
    	}
    	
    	
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
   	    		else if(GetData.length()==10){
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
					Intent myIntent=new Intent(AndroidSelect1.this, MysqldataActivity.class);
					myIntent.putExtra("All", msg.obj.toString());
					startActivityForResult(myIntent,1);
					break;
   	    		case HANDLER_nOK1:
   	    			Button=1;
   	    			Toast.makeText(AndroidSelect1.this, "網路連線失敗", Toast.LENGTH_LONG).show();
   	    			myDialog.dismiss();
   	    			break;
   	    		case HANDLER_nOK2:
   	    			Button=1;
   	    			Toast.makeText(AndroidSelect1.this, "輸入卡號或相關資料錯誤", Toast.LENGTH_LONG).show();
   	    			myDialog.dismiss();
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