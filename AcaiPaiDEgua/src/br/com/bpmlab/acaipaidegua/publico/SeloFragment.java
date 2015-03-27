package br.com.bpmlab.acaipaidegua.publico;

import br.com.bpmlab.acaipaidegua.R;
import android.app.Fragment;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class SeloFragment extends Fragment {
	View rootView;


	public SeloFragment() {
	// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	Bundle savedInstanceState) {

	rootView = inflater.inflate(R.layout.fragment_selo,
	container, false);
//	WebView wv = (WebView) rootView.findViewById(R.id.webView1);
//
//	String url = "http://www.sagri.pa.gov.br/noticias/view/371/lista_de_estabelecimentos_com_certificado_de_qualidade";
//	WebSettings ws = wv.getSettings();
//	ws.setJavaScriptEnabled(true);
//	ws.setSupportZoom(false);
//
//
//	wv.loadUrl(url );

	TextView noteView = (TextView) rootView.findViewById(R.id.textView1);
	String url = "http://www.sagri.pa.gov.br/noticias/view/371/lista_de_estabelecimentos_com_certificado_de_qualidade";
	noteView.setText(url);
	Linkify.addLinks(noteView, Linkify.ALL);

	return rootView;
	}
}
