package br.com.bpmlab.acaipaidegua.publico;

import br.com.bpmlab.acaipaidegua.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

public class SobreApp extends Activity {

	private TextView texto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sobreapp);

		 ActionBar actionBar = getActionBar();

	        if (actionBar != null){
	            actionBar.setDisplayHomeAsUpEnabled(true);
	            actionBar.setHomeButtonEnabled(true);
	        } 
		texto = (TextView) this.findViewById(R.id.textoapp);
		Resources res = this.getResources();
		texto.setText(Html.fromHtml(res.getString(R.string.textoapp)));
	}

	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
