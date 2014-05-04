package com.hci.loopdaloop;

import com.hci.loopdaloop.util.Loop;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.SystemClock; 

/**
 * The LoopActivity class handles all activities that will occur on the track recording
 * screen, such as the metronome and all recording, deleting, and volume controls
 * 
 * @author Chris Spanos, Will Rhodes, John Friedfeld, Scotty, Boutin
 *
 */
public class LoopActivity extends ActionBarActivity {

	///////////////////////////////////
	//FIELD VARIABLES
	///////////////////////////////////
	static Loop newLoop;
	static Button recordButton;
	static Button deleteButton;
	static Button muteButton;
	static Button volUp;
	static Button volDown;
	static Loop newLoop2;
	static Button recordButton2;
	static Button deleteButton2;
	static Button muteButton2;
	static Button volUp2;
	static Button volDown2;
	static Loop newLoop3;
	static Button recordButton3;
	static Button deleteButton3;
	static Button muteButton3;
	static Button volUp3;
	static Button volDown3;
	static Loop newLoop4;
	static Button recordButton4;
	static Button deleteButton4;
	static Button muteButton4;
	static Button volUp4;
	static Button volDown4;
	static Button plus;
	static Button minus;
	static Button metroStart;
	static TextView bpmdisplay;
	static Boolean muted = false;
	static Boolean muted2 = false;
	static Boolean muted3 = false;
	static Boolean muted4 = false;
	static Boolean metroRunning = false;
	static float volA = 1;
	static float volB = 1;
	static float volA2 = 1;
	static float volB2 = 1;
	static float volA3 = 1;
	static float volB3 = 1;
	static float volA4 = 1;
	static float volB4 = 1;
	static private Handler handler = new Handler();
	static int bpm = 120;
	static int waitTime = 500;
	static int i = 0;
	static int recordDelay = 0;
	static Boolean recording = false;
	static Boolean recording2 = false;
	static Boolean recording3 = false;
	static Boolean recording4 = false;
	///////////////////////////////////
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loop);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.loop, menu);
		return true;
	}

	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//The method that will act as the metronome for synchronizing the loops for
	//recording and playing.
	private static Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (i == 16) {
				i = 1;
			}
			else {
				i++;	
			}
			//Starts playing at the beginning of the metronome
			if (i == 1) {
				newLoop.Start();
				newLoop2.Start();
				newLoop3.Start();
				newLoop4.Start();
			}
			metroStart.setText(String.valueOf(i));
			//Allows the method to call itself
			handler.postDelayed(this, waitTime);
		}
		
	};
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_loop, container,
					false);
			
			//Instantiates a new loop
			newLoop = new Loop(rootView.getContext());
			
			//Instantiates a record button
			recordButton = (Button)  rootView.findViewById(R.id.record);
			
			//Sets the action of the record button for when it is clicked 
			recordButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 if (!recording) {
	            		 //If the metronome is not running, starts the metronome
	            		 if (!metroRunning) {
	            			 i = 0;
	            			 metroRunning = true;
	            			 handler.postDelayed(runnable, 0);
	            		 }
	            		recording = true;
	            		//recordDelay is the time that each track has to wait before it starts
	            		//recording from when its record button is clicked
	            		recordDelay = (60000/bpm) * (16 - i);
	            		//Displays a count down for the user that goes along with the metronome
	            		new CountDownTimer(recordDelay,waitTime) {
	            			public void onTick(long millisUntilFinished) {
	            				recordButton.setText(String.valueOf(15 - i));
	            			}
	            			public void onFinish() {
	            				if (recording) {
	            					newLoop.record();
	            					recordButton.setText("Recording...");
	            					//Determines how long a track will record for.
	            					new CountDownTimer(((60000/bpm)*16),waitTime) {
	        	            			public void onTick(long millisUntilFinished) {
	        	            			}
	        	            			public void onFinish() {
	        	            				newLoop.stopRecord();
	        	            				recording = false;
	        	            				newLoop.prepare();
	        	    	            		recordButton.setText("Playing...");
	        	    	            		//Resets muting function.
	        	    	            		muted = false;
	        	    	            		newLoop.resume(1,1);
	        	    	            		muteButton.setText("Mute");
	        	            			}
	        	            		}.start();
	            				}
	            			}
	            		}.start();
	            	 }
	             }});
			
			//Same as above
			newLoop2 = new Loop(rootView.getContext());
			recordButton2 = (Button)  rootView.findViewById(R.id.record2);
			recordButton2.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 if (!recording2) {
	            		 if (!metroRunning) {
	            			 i = 0;
	            			 metroRunning = true;
	            			 handler.postDelayed(runnable, 0);
	            		 }
	            		 	recording2 = true;
		            		recordDelay = (60000/bpm) * (16 - i);
		            		new CountDownTimer(recordDelay,waitTime) {
		            			public void onTick(long millisUntilFinished) {
		            				recordButton2.setText(String.valueOf(15 - i));
		            			}
		            			public void onFinish() {
		            				if (recording2) {
		            					newLoop2.record();
		            					recordButton2.setText("Recording...");
		            					new CountDownTimer(((60000/bpm)*16),waitTime) {
		        	            			public void onTick(long millisUntilFinished) {
		        	            			}
		        	            			public void onFinish() {
		        	            				newLoop2.stopRecord();
		        	            				newLoop2.prepare();
		        	    	            		recordButton2.setText("Playing...");
		        	    	            		muted2 = false;
		        	    	            		newLoop2.resume(1,1);
		        	    	            		muteButton2.setText("Mute");
		        	            			}
		        	            		}.start();
		            				}
		            			}
		            		}.start();
		            	 }
		             }});	
			
			//Same as above
			newLoop3 = new Loop(rootView.getContext());
			recordButton3 = (Button)  rootView.findViewById(R.id.record3);
			recordButton3.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 if (!recording3) {
	            		 if (!metroRunning) {
	            			 i = 0;
	            			 metroRunning = true;
	            			 handler.postDelayed(runnable, 0);
	            		 }
	            		 	recording3 = true;
		            		recordDelay = (60000/bpm) * (16 - i);
		            		new CountDownTimer(recordDelay,waitTime) {
		            			public void onTick(long millisUntilFinished) {
		            				recordButton3.setText(String.valueOf(15 - i));
		            			}
		            			public void onFinish() {
		            				if (recording3) {
		            					newLoop3.record();
		            					recordButton3.setText("Recording...");
		            					new CountDownTimer(((60000/bpm)*16),waitTime) {
		        	            			public void onTick(long millisUntilFinished) {
		        	            			}
		        	            			public void onFinish() {
		        	            				newLoop3.stopRecord();
		        	            				newLoop3.prepare();
		        	    	            		recordButton3.setText("Playing...");
		        	    	            		muted3 = false;
		        	    	            		newLoop3.resume(1,1);
		        	    	            		muteButton3.setText("Mute");
		        	            			}
		        	            		}.start();
		            				}
		            			}
		            		}.start();
		            	 }
		             }});
			
			//Same as above
			newLoop4 = new Loop(rootView.getContext());
			recordButton4 = (Button)  rootView.findViewById(R.id.record4);
			recordButton4.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 if (!recording4) {
	            		 if (!metroRunning) {
	            			 i = 0;
	            			 metroRunning = true;
	            			 handler.postDelayed(runnable, 0);
	            		 }
	            		 	recording4 = true;
		            		recordDelay = (60000/bpm) * (16 - i);
		            		new CountDownTimer(recordDelay,waitTime) {
		            			public void onTick(long millisUntilFinished) {
		            				recordButton4.setText(String.valueOf(15 - i));
		            			}
		            			public void onFinish() {
		            				if (recording4) {
		            					newLoop4.record();
		            					recordButton4.setText("Recording...");
		            					new CountDownTimer(((60000/bpm)*16),waitTime) {
		        	            			public void onTick(long millisUntilFinished) {
		        	            			}
		        	            			public void onFinish() {
		        	            				newLoop4.stopRecord();
		        	            				newLoop4.prepare();
		        	    	            		recordButton4.setText("Playing...");
		        	    	            		muted4 = false;
		        	    	            		newLoop4.resume(1,1);
		        	    	            		muteButton4.setText("Mute");
		        	            			}
		        	            		}.start();
		            				}
		            			}
		            		}.start();
		            	 }
		             }});
			
			//Instantiates the delete buttons
			deleteButton = (Button) rootView.findViewById(R.id.delete);
			
			//Sets the action of the delete buttons for when it is clicked
			deleteButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 //Deletes the track
	            	 newLoop.delete();
	            	 recordButton.setText("Record");
	            	 //Resets muting function
	            	 muted = false;
	            	 recording = false;
	             }});
	        
			//Same as above
			deleteButton2 = (Button) rootView.findViewById(R.id.delete2);
			deleteButton2.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 newLoop2.delete();
	            	 recordButton2.setText("Record");
	            	 muted2 = false;
	            	 recording2 = false;
	             }});
			
			//Same as above
			deleteButton3 = (Button) rootView.findViewById(R.id.delete3);
			deleteButton3.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 newLoop3.delete();
	            	 recordButton3.setText("Record");
	            	 muted3 = false;
	            	 recording3 = false;
	             }});
			
			//Same as above
			deleteButton4 = (Button) rootView.findViewById(R.id.delete4);
			deleteButton4.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 newLoop4.delete();
	            	 recordButton4.setText("Record");
	            	 muted4 = false;
	            	 recording4 = false;
	             }});
			
			//Instantiates the mute buttons
			muteButton = (Button) rootView.findViewById(R.id.mute);
			
			//Sets the action of the mute button for when it is clicked
			muteButton.setOnClickListener(new View.OnClickListener()	{
				public void onClick(View v)	{
					if(newLoop.isPlaying()) {
						//Mutes only if the muted boolean is not yet set to true
						if(!muted) {
							newLoop.mute();
							muted = true;
							muteButton.setText("Unmute");
						}
						//If mute boolean is true, set volume to current values of volA and volB
						else {
							newLoop.resume(volA,volB);
							muted = false;
							muteButton.setText("Mute");
						}
					}
				}});
			
			//Same as above
			muteButton2 = (Button) rootView.findViewById(R.id.mute2);
			muteButton2.setOnClickListener(new View.OnClickListener()	{
				public void onClick(View v)	{
					if(!muted2) {
						newLoop2.mute();
						muted2 = true;
						muteButton2.setText("Unmute");
					}
					else {
						newLoop2.resume(volA2,volB2);
						muted2 = false;
						muteButton2.setText("Mute");
					}
				}});
			
			//Same as above
			muteButton3 = (Button) rootView.findViewById(R.id.mute3);
			muteButton3.setOnClickListener(new View.OnClickListener()	{
				public void onClick(View v)	{
					if(!muted3) {
						newLoop3.mute();
						muted3 = true;
						muteButton3.setText("Unmute");
					}
					else {
						newLoop3.resume(volA3,volB3);
						muted3 = false;
						muteButton3.setText("Mute");
					}
				}});
			
			//Same as above
			muteButton4 = (Button) rootView.findViewById(R.id.mute4);
			muteButton4.setOnClickListener(new View.OnClickListener()	{
				public void onClick(View v)	{
					if(!muted4) {
						newLoop4.mute();
						muted4 = true;
						muteButton4.setText("Unmute");
					}
					else {
						newLoop4.resume(volA4,volB4);
						muted4 = false;
						muteButton4.setText("Mute");
					}
				}});
			
			//Instantiates volume up button
			volUp = (Button) rootView.findViewById(R.id.volup);
			
			//Sets the action of the volUp button for when it is clicked
			volUp.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Only allows volA and volB to be set to 1 at maximum.  If they are less than
					//one and muted is not true, increments volA and volB by 0.1
					if(volA < 1 && volB < 1 && muted == false) {
						volA = (float) (volA + 0.1);
						volB = (float) (volB + 0.1);
						newLoop.resume(volA, volB);
					}
				}
			});
			
			//Instantiates the volume down button
			volDown = (Button) rootView.findViewById(R.id.voldown);
			
			//Sets the action for the volDown button for when it is clicked
			volDown.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Only allows volA and volB to be set to 0 at minimum.  If they are greater than
					//zero and muted is true, decrements volA and volB by 0.1
					if(volA > 0 && volB > 0 && muted == false) {
						volA = (float) (volA - 0.1);
						volB = (float) (volB - 0.1);
						newLoop.resume(volA, volB);
					}
				}
			});
			
			//Same as above
			volUp2 = (Button) rootView.findViewById(R.id.volup2);
			volUp2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(volA2 < 1 && volB2 < 1 && muted2 == false) {
						volA2 = (float) (volA2 + 0.1);
						volB2 = (float) (volB2 + 0.1);
						newLoop2.resume(volA2, volB2);
					}
				}
			});
			
			//Same as above
			volDown2 = (Button) rootView.findViewById(R.id.voldown2);
			volDown2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(volA2 > 0 && volB2 > 0 && muted2 == false) {
						volA2 = (float) (volA2 - 0.1);
						volB2 = (float) (volB2 - 0.1);
						newLoop2.resume(volA2, volB2);
					}
				}
			});
			
			//Same as above
			volUp3 = (Button) rootView.findViewById(R.id.volup3);
			volUp3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(volA3 < 1 && volB3 < 1 && muted3 == false) {
						volA3 = (float) (volA3 + 0.1);
						volB3 = (float) (volB3 + 0.1);
						newLoop3.resume(volA3, volB3);
					}
				}
			});
			
			//Same as above
			volDown3 = (Button) rootView.findViewById(R.id.voldown3);
			volDown3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(volA3 > 0 && volB3 > 0 && muted3 == false) {
						volA3 = (float) (volA3 - 0.1);
						volB3 = (float) (volB3 - 0.1);
						newLoop3.resume(volA3, volB3);
					}
				}
			});
			
			//Same as above
			volUp4 = (Button) rootView.findViewById(R.id.volup4);
			volUp4.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(volA4 < 1 && volB4 < 1 && muted4 == false) {
						volA4 = (float) (volA4 + 0.1);
						volB4 = (float) (volB4 + 0.1);
						newLoop4.resume(volA4, volB4);
					}
				}
			});
			
			//Same as above
			volDown4 = (Button) rootView.findViewById(R.id.voldown4);
			volDown4.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(volA4 > 0 && volB4 > 0 && muted4 == false) {
						volA4 = (float) (volA4 - 0.1);
						volB4 = (float) (volB4 - 0.1);
						newLoop4.resume(volA4, volB4);
					}
				}
			});
			
			//Instantiates the metronome start button
			metroStart = (Button) rootView.findViewById(R.id.metroStart);
			
			//Sets the action of the metroStart button for when it is clicked
			metroStart.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					i = 0;
					//If the metronome is not already running, calls the runnable method
					if (!metroRunning) {
						metroRunning = true;
						handler.postDelayed(runnable, 0);
					}
					//If it is running, stops the runnable method and returns text to "Start"
					else
					{
						metroStart.setText("Start");
						handler.removeCallbacks(runnable);
						metroRunning = false;
					}
				}
			});
			
			//Instantiates the bpmdisplay text view to be edited
			bpmdisplay = (TextView) rootView.findViewById(R.id.bpmdisplay);
			
			//Instantiates the tempo increment button
			plus = (Button) rootView.findViewById(R.id.plus);
			
			//Sets the action for the plus button for when it is clicked
			plus.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//If the metronome is running, stops the runnable method and increments bpm by 2
					if (metroRunning) {
						metroStart.setText("Start");
						handler.removeCallbacks(runnable);
						metroRunning = false;
					}
					bpm += 2;
					//waitTime is the time between tempo beats
					waitTime = 60000/bpm;
					//Changes text view to display current tempo in bpm
					bpmdisplay.setText(String.valueOf(bpm));			
				}
			});
			
			//Instantiates the tempo decrement button
			minus = (Button) rootView.findViewById(R.id.minus);
			
			//Sets the action of the minus button for when it is clicked
			minus.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Enforces a minimum tempo of 40 bpm.  Also checks if metronome is running.
					//If it is not running, stops the runnable method and decrements bpm by 2.
					if (bpm > 40) {
						if (metroRunning) {
							metroStart.setText("Start");
							handler.removeCallbacks(runnable);
							metroRunning = false;
						}
						bpm -= 2;
						waitTime = 60000/bpm;
						bpmdisplay.setText(String.valueOf(bpm));
					}
				}
			});
			return rootView;
		}
	}
}