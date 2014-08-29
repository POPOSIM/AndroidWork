package Android_Examples.AndroidWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class AndroidSelect3 extends Activity {
	android.app.AlertDialog.Builder builder;
	private static final int MENU2 = 2;
	private static final int MENU3 = 3;
	private static final int MENU1 = 1;
	public static Handler mHandler = new Handler();
	TextView TextView01;	// 用來顯示文字訊息
	EditText EditText02;	// 文字方塊
	String tmp;				// 暫存文字訊息
	Socket clientSocket;	// 客戶端socket
	GlobalVariable globalVariable;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_select3);
		
		// 從資源檔裡取得位址後強制轉型成文字方塊
		TextView01=(TextView)findViewById(R.id.TextView01);
        
		EditText02=(EditText) findViewById(R.id.EditText02);
		
		
		globalVariable = (GlobalVariable)getApplicationContext();
		
        //globalVariable.setGlobalVariable("林家熯");
		// 以新的執行緒來讀取資料
		
		Thread t = new Thread(readData);
		
		// 啟動執行緒
		t.start();
		
		// 從資源檔裡取得位址後強制轉型成按鈕
		Button button1=(Button) findViewById(R.id.Button01);
		
		// 設定按鈕的事件
		button1.setOnClickListener(new Button.OnClickListener() {		
			// 當按下按鈕的時候觸發以下的方法
			public void onClick(View v) {
				// 如果已連接則
				if(clientSocket.isConnected()){
					
					BufferedWriter bw;
					
					try {
						// 取得網路輸出串流
						bw = new BufferedWriter( new OutputStreamWriter(clientSocket.getOutputStream()));
						
						// 寫入訊息
						bw.write(globalVariable.getGlobalVariable()+" : "+EditText02.getText()+"\n");
						
						// 立即發送
						bw.flush();
					} catch (IOException e) {
						
					}
					// 將文字方塊清空
					EditText02.setText("");
				}	
			}
		});
		
	}

	// 顯示更新訊息
	private Runnable updateText = new Runnable() {
		public void run() {
			// 加入新訊息並換行
			TextView01.append(tmp + "\n");
		}
	};

	// 取得網路資料
	private Runnable readData = new Runnable() {
		public void run() {
			// server端的IP
			InetAddress serverIp;

			try {
				// 以內定(本機電腦端)IP為Server端
				serverIp = InetAddress.getByName("1.34.6.116");
				
				int serverPort = 5050;
				clientSocket = new Socket(serverIp, serverPort);
				
				// 取得網路輸入串流
				BufferedReader br = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				
				// 當連線後
				while (clientSocket.isConnected()) {
					// 取得網路訊息
					tmp = br.readLine();
					
					// 如果不是空訊息則
					if(tmp!=null)
						// 顯示新的訊息
						mHandler.post(updateText);
				}

			} 
			catch (IOException e) {
				
			} 
			
		}
	};
    public boolean onCreateOptionsMenu(Menu menu) {
    	
	    menu.add(Menu.NONE, MENU1, 0, "使用說明")
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
			 builder =new android.app.AlertDialog.Builder(this); 
             builder.setTitle("使用說明").setMessage("輸入使用者代號再輸入欲發表文字，並且按下送出。").setPositiveButton("確定",null).show();        
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
    
}