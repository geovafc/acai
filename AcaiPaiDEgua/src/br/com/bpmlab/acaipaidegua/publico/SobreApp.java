package br.com.bpmlab.acaipaidegua.publico;

import br.com.bpmlab.acaipaidegua.R;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class SobreApp extends Activity {

	
	private TextView textoapp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sobreapp);
		
		textoapp = (TextView) findViewById(R.id.textoapp);
		Resources res = this.getResources();
		textoapp.setText(Html.fromHtml(res.getString(R.string.texto)));
	}
	
}
