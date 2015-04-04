package br.com.bpmlab.acaipaidegua.publico;

import java.util.ArrayList;

import br.com.bpmlab.acaipaidegua.R;
import br.com.bpmlab.acaipaidegua.adapter.NavDrawerListAdapter;
import br.com.bpmlab.acaipaidegua.model.NavDrawerItem;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	// nav drawer title
	private CharSequence mDrawerTitle;
	// used to store app title
	private CharSequence mTitle;
	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private long lastBackPressTime = 0;
	private Toast toast;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	public double lat;
	public double lon;
	public double lastLat;
	public double lastLon;
	private LocationManager locationManager;
	String provider;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		chamaAlertaGPS();
		if (lat == 0.0 && lon == 0.0) {
			obterUltimaLocalizacao();
		}

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		iniciarLocalizacao(locationManager);

	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("onpause");
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.removeUpdates(this);
	}
	
	@Override
	public void onBackPressed() {
	  if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
	    toast = Toast.makeText(this, "Pressione o Botão Voltar novamente para fechar o Aplicativo.", 4000);
	    toast.show();
	    this.lastBackPressTime = System.currentTimeMillis();
	  } else {
	    if (toast != null) {
	    toast.cancel();
	  }
	  super.onBackPressed();
	 }
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent it = new Intent(getBaseContext(), SobreApp.class);
			startActivity(it);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:

			fragment = new MapaFragment();

			break;
		case 1:
			fragment = new LocalizarFragment();
			break;

		case 2:
			fragment = new SeloFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLocationChanged(Location location) {

		lat = location.getLatitude();
		lon = location.getLongitude();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void iniciarLocalizacao(LocationManager locationManager) {

		// Criteria criteria = new Criteria();
		// provider = locationManager.getBestProvider(criteria, false);
		//
		// locationManager.requestLocationUpdates(provider, 0, 0, this);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);

	}

	public void obterUltimaLocalizacao() {
		Criteria criteria = new Criteria();
		LocationManager locationM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String providerLast = locationM.getBestProvider(criteria, false);
		Location loc = locationM.getLastKnownLocation(providerLast);
		if (loc != null) {

			System.out.println("lastLat " + lastLat);
			lastLat = loc.getLatitude();
			lastLon = loc.getLongitude();

		}

	}

	public void chamaAlertaGPS() {
		LocationManager locM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!locM.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

			AlertDialog.Builder alerta = new AlertDialog.Builder(this);

			alerta.setTitle("Ativar Localização");
			alerta.setMessage("Ative o GPS para melhorar a precisão da sua localização."
					+ "Deseja ligar o GPS agora?");
			alerta.setPositiveButton("Sim",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent localSetting = new Intent(
									android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(localSetting);

						}
					});
			alerta.setNegativeButton("Não",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			alerta.show();
		}
	}

	public void chamaAlertaGPSWIFI() {
		LocationManager locM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!locM.isProviderEnabled(LocationManager.GPS_PROVIDER)
				&& !locationManager.isProviderEnabled(locM.NETWORK_PROVIDER)) {

			AlertDialog.Builder alerta = new AlertDialog.Builder(this);

			alerta.setTitle("Ativar Localização");
			alerta.setMessage("Melhore a precisão da sua localização."
					+ "Deseja ligar o GPS ou o WI-FI agora?");
			alerta.setPositiveButton("Sim",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent localSetting = new Intent(
									android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(localSetting);

						}
					});
			alerta.setNegativeButton("Não",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			alerta.show();
		}
	}
	
	
}
