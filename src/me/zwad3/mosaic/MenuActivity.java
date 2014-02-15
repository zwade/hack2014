package me.zwad3.mosaic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuActivity extends Activity {

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	 }

	 @Override
	 public void onResume() {
	    super.onResume();
	    openOptionsMenu();
	 }

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.newitem, menu);
	    return true;
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection.
	    switch (item.getItemId()) {
	        case R.id.clock:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 }

	    @Override
	 public void onOptionsMenuClosed(Menu menu) {
	        // Nothing else to do, closing the Activity.
	    finish();
	 }

	
	
}
