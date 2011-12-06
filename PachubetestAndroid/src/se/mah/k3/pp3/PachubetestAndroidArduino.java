package se.mah.k3.pp3;

import java.util.Random;
import Pachube.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
/**
 * Example Pachube Android client for controlling Arduino over Pachube.
 * 
 * @author Lars Rauer
 * 
 * Version 1. 
 * Known issues: Update doesnt work if in PWM state.
 */

public class PachubetestAndroidArduino extends Activity implements OnClickListener, SeekBar.OnSeekBarChangeListener  {
    /** Called when the activity is first created. */
	private String API_KEY = "Your own Api Pachube Key";
	private static int SHARE_FEED_ID = 40504;
	private static int CONTROL_FEED_ID = 40525;   
	private Pachube p;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //buttonlistener
        View button = findViewById(R.id.button1);
        button.setOnClickListener(this);
        findViewById(R.id.toggleButton0).setOnClickListener(this);
        findViewById(R.id.toggleButton1).setOnClickListener(this);
        findViewById(R.id.toggleButton2).setOnClickListener(this);
        findViewById(R.id.toggleButton3).setOnClickListener(this);
        findViewById(R.id.toggleButton4).setOnClickListener(this);
        findViewById(R.id.toggleButton5).setOnClickListener(this);
        findViewById(R.id.toggleButton6).setOnClickListener(this);
        findViewById(R.id.toggleButton7).setOnClickListener(this);
        findViewById(R.id.toggleButton8).setOnClickListener(this);
        findViewById(R.id.toggleButton9).setOnClickListener(this);
        //set seekbar listeners    
        ((SeekBar)findViewById(R.id.seekBar1)).setOnSeekBarChangeListener(this);  
        ((SeekBar)findViewById(R.id.seekBar2)).setOnSeekBarChangeListener(this);  
        ((SeekBar)findViewById(R.id.seekBar3)).setOnSeekBarChangeListener(this);  
        ((SeekBar)findViewById(R.id.seekBar4)).setOnSeekBarChangeListener(this);  
        p = new Pachube(API_KEY);
	    update();
    }

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		  
	}
    @Override
    protected void onResume() {
    	// TODO Retreive saved data
    	super.onResume();
    }
     @Override
    protected void onPause() {
    	// TODO Save if needed
    	super.onPause();
    }
	@Override
	public void onClick(View v) {
		Log.i("k3larra","Click");
		if(v.getId() == R.id.toggleButton0){
			updateDigital((ToggleButton)findViewById(R.id.toggleButton0),0);
		 }
		if(v.getId() == R.id.toggleButton1){
			updateDigital((ToggleButton)findViewById(R.id.toggleButton1),1);
		 }
		if(v.getId() == R.id.toggleButton2){
			updateDigital((ToggleButton)findViewById(R.id.toggleButton2),2);
		 }
		if(v.getId() == R.id.toggleButton3){
			updateDigital((ToggleButton)findViewById(R.id.toggleButton3),3);
		 }
		if(v.getId() == R.id.toggleButton4){
			updateDigital((ToggleButton)findViewById(R.id.toggleButton4),4);
		 }
		if(v.getId() == R.id.toggleButton5){
			updateDigital((ToggleButton)findViewById(R.id.toggleButton5),5);
		 }
		if(v.getId() == R.id.toggleButton6){
			updateDigital((ToggleButton)findViewById(R.id.toggleButton6),6);
		 }
		if(v.getId() == R.id.toggleButton7){
			updateDigital((ToggleButton)findViewById(R.id.toggleButton7),7);
		 }
		if(v.getId() == R.id.toggleButton8){
			updateDigital((ToggleButton)findViewById(R.id.toggleButton8),8);
		 }
		if(v.getId() == R.id.toggleButton9){
			updateDigital((ToggleButton)findViewById(R.id.toggleButton9),9);
		 }	
		if (v.getId() == R.id.button1){ //Kanske alltid?
			Log.i("k3larra","Button1");
			update();
		}

	}
	
	//seekbar stuff:
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		// TODO Auto-generated method stub
		 Log.i("k3larra",progress + " " +"onProgressChanged"+ "=" + fromTouch);
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		Log.i("k3larra","onStartTrackingTouch");	
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		Log.i("k3larra","onStopTrackingTouch progress: " + seekBar.getProgress());
		
			//Get a value from the bar between 0 and 255 for the pwm pin
			ToggleButton toggleButton;
			TextView textView;
			if (seekBar.getId() ==  R.id.seekBar1){
				textView = ((TextView)findViewById(R.id.textView1));
				toggleButton= (ToggleButton)findViewById(R.id.toggleButton3);
				updateSeekBar(seekBar,textView, 3,  toggleButton );
			}
			if (seekBar.getId() ==  R.id.seekBar2){
				textView = ((TextView)findViewById(R.id.textView2));
				toggleButton= (ToggleButton)findViewById(R.id.toggleButton5);
				updateSeekBar(seekBar,textView, 5,  toggleButton );
			}
			if (seekBar.getId() ==  R.id.seekBar3){
				textView = ((TextView)findViewById(R.id.textView3));
				toggleButton= (ToggleButton)findViewById(R.id.toggleButton6);
				updateSeekBar(seekBar,textView, 6,  toggleButton );
			}
			if (seekBar.getId() ==  R.id.seekBar4){
				textView = ((TextView)findViewById(R.id.textView4));
				toggleButton= (ToggleButton)findViewById(R.id.toggleButton9);
				updateSeekBar(seekBar,textView, 9,  toggleButton );
			}
	} 	
		
	void updateSeekBar(SeekBar seekBar,TextView textView, int dataStream, ToggleButton toggleButton){
		try {
			Feed control_feed = p.getFeed(CONTROL_FEED_ID);	
			double seekBarValue = (double)Math.round(seekBar.getProgress()*2.55);
			if (seekBar.getProgress()>1){		
				control_feed.updateDatastream(dataStream, seekBarValue);
				toggleButton.setVisibility(Button.INVISIBLE);
				textView.setText("PWM "+String.valueOf(dataStream)+" Value: "+  seekBarValue);
			}else{
				toggleButton.setVisibility(Button.VISIBLE);
				toggleButton.setChecked(false);
				textView.setText("PWM "+String.valueOf(dataStream)+" Not active: "); 
				updateDigital(toggleButton,3);	
			}
	    } catch (PachubeException ex) {
            System.out.println(ex.errorMessage);
	    }
	}
	//end seekbar stuff
	
	
	//Helper methods
	//Writes values to Arduino
	void updateDigital(ToggleButton toggleButton,int dataStream){
		try {
			Feed control_feed = p.getFeed(CONTROL_FEED_ID);	
			if (toggleButton.isChecked()){
				control_feed.updateDatastream(dataStream, (double)1);
			}else{
				control_feed.updateDatastream(dataStream, (double)0);
			}
	    } catch (PachubeException ex) {
	            System.out.println(ex.errorMessage);
	    }
	}

	//Reads values from Arduino
	void update(){
		//update if PWM active is missing
		try {
	        Feed share_feed = p.getFeed(SHARE_FEED_ID);
	        EditText text0 = (EditText)findViewById(R.id.editText0);
	        text0.setText("A0 = "+ share_feed.getDatastream(0)); 
	        EditText text1 = (EditText)findViewById(R.id.editText1);
	        text1.setText("A1 = "+ share_feed.getDatastream(1));       
	        EditText text2 = (EditText)findViewById(R.id.editText2);
	        text2.setText("A2 = "+ share_feed.getDatastream(2));        
	        EditText text3 = (EditText)findViewById(R.id.editText3);
	        text3.setText("A3 = "+ share_feed.getDatastream(3));
	        EditText text4 = (EditText)findViewById(R.id.editText4);
	        text4.setText("A4 = "+ share_feed.getDatastream(4));
	        EditText text5 = (EditText)findViewById(R.id.editText5);
	        text5.setText("A5 = "+ share_feed.getDatastream(5));
	        Feed control_feed = p.getFeed(CONTROL_FEED_ID);
	        //update digital pins
	        if (control_feed.getDatastream(0).intValue()==1){
	        	((ToggleButton)findViewById(R.id.toggleButton0)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton0)).setChecked(false);
	        }
	        if (control_feed.getDatastream(1).intValue()==1){
	        	((ToggleButton)findViewById(R.id.toggleButton1)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton1)).setChecked(false);
	        }
	        if (control_feed.getDatastream(2).intValue()==1){
	        	((ToggleButton)findViewById(R.id.toggleButton2)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton2)).setChecked(false);
	        }
	        if (control_feed.getDatastream(3).intValue()==1){
	        	((ToggleButton)findViewById(R.id.toggleButton3)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton3)).setChecked(false);
	        }
	        if (control_feed.getDatastream(4).intValue()==1){
	        	((ToggleButton)findViewById(R.id.toggleButton4)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton4)).setChecked(false);
	        }
	        if (control_feed.getDatastream(5).intValue()==1){
	        	((ToggleButton)findViewById(R.id.toggleButton5)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton5)).setChecked(false);
	        }
	        if (control_feed.getDatastream(6).intValue()==1){
	        	((ToggleButton)findViewById(R.id.toggleButton6)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton6)).setChecked(false);
	        }
	        if (control_feed.getDatastream(7).intValue()==1){
	        	((ToggleButton)findViewById(R.id.toggleButton7)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton7)).setChecked(false);
	        }
	        if (control_feed.getDatastream(8).intValue()==1){
	        	((ToggleButton)findViewById(R.id.toggleButton8)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton8)).setChecked(false);
	        }
	        if (control_feed.getDatastream(9).intValue()==1){
	        	((ToggleButton)findViewById(R.id.toggleButton9)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton9)).setChecked(false);
	        }	   
	        Log.i("k3larra","Updated");
	    } catch (PachubeException ex) {
	    	Log.i("k3larra","Error in update");
	            System.out.println(ex.errorMessage);
	    }
	}
}