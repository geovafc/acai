package br.com.bpmlab.acaipaidegua.publico;

import br.com.bpmlab.acaipaidegua.R;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class SeloFragment extends Fragment {
	View rootView;
	private TextView textoselo;

	public SeloFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_selo, container, false);
		// WebView wv = (WebView) rootView.findViewById(R.id.webView1);
		//
		// String url =
		// "http://www.sagri.pa.gov.br/noticias/view/371/lista_de_estabelecimentos_com_certificado_de_qualidade";
		// WebSettings ws = wv.getSettings();
		// ws.setJavaScriptEnabled(true);
		// ws.setSupportZoom(false);
		//
		//
		// wv.loadUrl(url );
		textoselo = (TextView) rootView.findViewById(R.id.txvoselo);
		Resources res = this.getResources();
		textoselo.setText(Html.fromHtml(res.getString(R.string.textoselo)));

		TextView noteView = (TextView) rootView.findViewById(R.id.link);
		String url = "http://www.agenciabelem.com.br/noticias/detalhes/109964";
		noteView.setText(url);
		Linkify.addLinks(noteView, Linkify.WEB_URLS);

		return rootView;
	}
}
