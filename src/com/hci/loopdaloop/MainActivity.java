package com.hci.loopdaloop;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

/**
 * MainActivity is the primary class which is responsible for inflating the first menu
 * as well as instantiating the buttons on that page
 * 
 * @author Chris Spanos, Will, Rhodes, John Friedfeld, Scotty Boutin
 *
 */
public class MainActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {	
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		public PlaceholderFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			
			//Instantiates the "New" button
			Button newButton = (Button) rootView.findViewById(R.id.newButton);
			
			//Sets the action for the "New" button for when it is clicked
			newButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 //when clicking the start button
	            	 Intent intent = new Intent(v.getContext(), LoopActivity.class);
	            	 startActivity(intent);
	             }
	         });
			 
			//Instantiates the "Tutorial" button
			Button tutorialButton = (Button) rootView.findViewById(R.id.tutorialButton);
			 
			//Sets the action for the "Tutorial" button for when it is clicked
			tutorialButton.setOnClickListener(new View.OnClickListener() {
				 public void onClick(View v) {
					 Intent tutorialIntent = new Intent(v.getContext(), TutorialActivity.class);
					 startActivity(tutorialIntent);
				 }
			 });
			return rootView;
		}
	}

}
