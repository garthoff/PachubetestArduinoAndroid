package se.mah.k3.pp3;

import java.text.DecimalFormat;
import java.util.Random;

import Pachube.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ToggleButton;

public class PachubetestAndroidArduino extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	private String API_KEY = "YOUR KEY HERE";
	private static int SHARE_FEED_ID = 40504;
	private static int CONTROL_FEED_ID = 40525;   
	private Random rand = new Random();
	//Pachube
	Pachube p;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = new Pachube(API_KEY);
        setContentView(R.layout.main);
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
        setAllDigitalPinsToZero();
//        View button2 = findViewById(R.id.checkBox1);
//        button2.setOnClickListener(this);
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
	
	//Writes values to arduino
	void updateDigital(ToggleButton toggleButton,int dataStream){
		try {
			//Pachube p = new Pachube(API_KEY);
			Feed f = p.getFeed(CONTROL_FEED_ID);	
			if (toggleButton.isChecked()){
				f.updateDatastream(dataStream, (double)1);
			}else{
				f.updateDatastream(dataStream, (double)0);
			}
	    } catch (PachubeException ex) {
	            System.out.println(ex.errorMessage);
	    }
	}

	//Reads values from Arduino
	void update(){
		try {
	        //Pachube p = new Pachube(API_KEY);
	        Feed f = p.getFeed(SHARE_FEED_ID);
	        EditText text0 = (EditText)findViewById(R.id.editText0);
	        text0.setText("A0 = "+ f.getDatastream(0)); 
	        EditText text1 = (EditText)findViewById(R.id.editText1);
	        text1.setText("A1 = "+ f.getDatastream(1));       
	        EditText text2 = (EditText)findViewById(R.id.editText2);
	        text2.setText("A2 = "+ f.getDatastream(2));        
	        EditText text3 = (EditText)findViewById(R.id.editText3);
	        text3.setText("A3 = "+ f.getDatastream(3));
	        EditText text4 = (EditText)findViewById(R.id.editText4);
	        text4.setText("A4 = "+ f.getDatastream(4));
	        EditText text5 = (EditText)findViewById(R.id.editText5);
	        text5.setText("A5 = "+ f.getDatastream(5));
	        Feed control = p.getFeed(CONTROL_FEED_ID);
	        //update digital pins
	        if (control.getDatastream(0).intValue()==0){
	        	((ToggleButton)findViewById(R.id.toggleButton0)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton0)).setChecked(false);
	        }
	        if (control.getDatastream(3).intValue()==0){
	        	((ToggleButton)findViewById(R.id.toggleButton3)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton3)).setChecked(false);
	        }
	        if (control.getDatastream(5).intValue()==0){
	        	((ToggleButton)findViewById(R.id.toggleButton5)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton5)).setChecked(false);
	        }
	        if (control.getDatastream(6).intValue()==0){
	        	((ToggleButton)findViewById(R.id.toggleButton6)).setChecked(true);
	        }else{
	        	((ToggleButton)findViewById(R.id.toggleButton6)).setChecked(false);
	        }
	        if (control.getDatastream(9).intValue()==0){
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

	private void setAllDigitalPinsToZero() {
		// TODO Auto-generated method stub
		try {
			//Pachube p = new Pachube(API_KEY);
			Feed f = p.getFeed(CONTROL_FEED_ID);
			for (int i = 0;i <10;i++){ 
				f.updateDatastream(i, (double)0);
			}
	    } catch (PachubeException ex) {
	            System.out.println(ex.errorMessage);
	    }
	}
	//helpers 
		double roundTwoDecimals(double d) {            
			DecimalFormat twoDForm = new DecimalFormat("#.#");        
			return Double.valueOf(twoDForm.format(d));
		} 
	}