package org.xtion.sample.nite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Xtion_SampleForNiteActivity extends Activity {
	
	
	private final String TAG = "markal" ;
	public static Activity activity ;
	private   Intent it ;
	private Button btn_osd,btn_start;
	private boolean isStart = true ;
	public static boolean haveOSD = true ;
	private boolean bsrun ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN) ;
        
        setContentView(R.layout.main);      
        btn_osd = (Button)findViewById(R.id.btn_osd) ;
        btn_start = (Button)findViewById(R.id.btn_start);
        activity = this ;
        
        it = new Intent(this,Xtion_SampleForNiteService.class) ;
        startService(it) ;
        btn_start.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(isStart){
					isStart = false ;
					stopService(it) ;
					btn_start.setText("start") ;
				}else {
					isStart = true ;
					startService(it) ;
					btn_start.setText("stop") ;
				}  
			}
        });
       
        btn_osd.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(haveOSD){
					haveOSD = false ;
					btn_osd.setText("OSD") ;
				}else {
					haveOSD = true ;
					btn_osd.setText("closeOSD") ;
				}
				
			}
        	
        });
    } 
      
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK &&( event.getRepeatCount() == 1 || event.getRepeatCount() == 0)){

			stopService(it) ;
			super.onKeyDown(keyCode, event) ;
		}
		
		return true;
	}
}