package org.xtion.sample.nite;

//import org.OpenNI.Point3D;
import org.xtion.nite.ScreenTouch;
import org.xtion.nite.SkeletonInfo;
import org.xtion.nite.XTPoint3D;
import org.xtion.nite.XtionNiteFunction; 

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Xtion_SampleForNiteService extends Service implements Runnable {

	private final String TAG = getClass().getSimpleName();
	private boolean isExit;

	XtionNiteFunction xnf;
	ScreenTouch st;
 
	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}
 
	@Override
	public void onCreate() {
		super.onCreate();
		st = new ScreenTouch(this) ;
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		Log.i(TAG, "onDestroy");
		xnf.cleanUp();
		st.cleanUp();
		isExit = true;
	} 

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand");

		xnf = new XtionNiteFunction(Xtion_SampleForNiteActivity.activity); 
		isExit = false ;

		new Thread(new Runnable() { 

			public void run() {

				while (!isExit) { 

					/**  get users' 15 skeleton Node data  */
					SkeletonInfo info[] = xnf.getSkeletonInfo(true);
					
					/**  guid hands position in screen by white circle     */
					st.guidPosition() ; 
					/**  trigger click     */
					st.triggerClick(xnf.getNiteData()) ;
					
					/**  open OSD    ,very consume resource */
//					 xnf.drawOSD(true) ; 
					/**  get RGB Data  */
//					xnf.getRgbBitmap()  ;  
					
					/**  G-sensor  */
					/*if(info!=null && info[0]!=null){
						XTPoint3D posRH = info[0].getRightHand(true);
						XTPoint3D posH = info[0].getHead(true) ;
						int axisX = (int)(posH.getY() - posRH.getY()+0.5) ;
						int axisY = (int)(posH.getX() - posRH.getX()+0.5);
					
						st.controlGSensor(axisX, axisY, 0);
					}*/
				}
			}

		}).start();

		return 0;
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

}
