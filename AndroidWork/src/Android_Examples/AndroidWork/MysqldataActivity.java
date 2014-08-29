package Android_Examples.AndroidWork;


import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.TextView;


public class MysqldataActivity extends Activity {
	
	private static final int MENU2 = 2;
	private static final int MENU3 = 3;
	private static final int MENU1 = 1;
	private String All,Furl,Text,Structure;
	private TextView lab1,lab2;
	private Intent myIntent;
	private HorizontialListView list;
	GetWebImg ImgCache = new GetWebImg(this);
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_sqldata);
         
     

        lab1 = (TextView) findViewById(R.id.label1);
        lab2 = (TextView) findViewById(R.id.label2);
        list = (HorizontialListView) findViewById(R.id.list);
        
        myIntent = this.getIntent();
        All       =      myIntent.getStringExtra("All");
        String Temp1[]  =  All.split("<1>");
        String Purl[]  =  Temp1[5].split("<2>");
        Furl       =  Temp1[2];
        Structure  =  Temp1[3];
        Text       =  Temp1[4];
      
        
        lab1.setText(Structure);
        lab2.setText(MyTextData(Text));
        
        
        
        ArrayList<String[]> alldata = new ArrayList<String[]>();
        int counter=0;
        for(int i=0;i<Purl.length;i++){
        	if(Integer.valueOf(Purl[i].length())>10){
             
             alldata.add(createData("圖片 "+(++counter)+".",Purl[i]));
             
        	}        		
        }
        
        list.setAdapter(new MydataAdapter(MysqldataActivity.this,alldata,ImgCache));
        
        
       
       
        
    }
   
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if(keyCode == KeyEvent.KEYCODE_BACK){
                   Intent replyIntent =new Intent();
    		Bundle bundle =new Bundle();
    		replyIntent.putExtras(bundle);
    		setResult(RESULT_OK,replyIntent);
    		finish();
		}
 
		return super.onKeyDown(keyCode, event);
	}
    
	
	private String[] createData(String text,String imgurl){
    	String temp[]={text,imgurl};
    	return temp;
    }
	private String MyTextData(String text1){
		 String text2[]=text1.split("。");
		 String text3="";
    	
    	for(int i = 0; i < text2.length; i++) 
    		text3=text3+"    "+text2[i]+"。\n"; 
    	
    	return text3;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	menu.add(Menu.NONE, MENU1, 0, "影片介紹")
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
			if(Furl.length()==7)
				Toast.makeText(MysqldataActivity.this, Structure+"尚未設置影片", Toast.LENGTH_LONG).show();
			else{ 
				startActivity(new Intent( Intent.ACTION_VIEW, Uri.parse( Furl ) ));
			}		
		break;
		case MENU2: 
			 android.app.AlertDialog.Builder builder =
	         new android.app.AlertDialog.Builder(this); 
             builder.setTitle("關於").setMessage("版本: 1.0版\n作者: 專題小組").setPositiveButton("確定",null).show();   
	    break;
	    
		case MENU3:  
			Intent replyIntent =new Intent();
    		Bundle bundle =new Bundle();
    		replyIntent.putExtras(bundle);
    		setResult(RESULT_OK,replyIntent);
		    finish(); // 結束程式
		break;
		}
		return super.onOptionsItemSelected((android.view.MenuItem) item);
	}
	
}