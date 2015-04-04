package br.com.bpmlab.acaipaidegua.publico;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import br.com.bpmlab.acaipaidegua.R;
import br.com.bpmlab.acaipaidegua.adapter.EstabelecimentoAdapter;
import br.com.bpmlab.acaipaidegua.entidade.Estabelecimento;
import br.com.bpmlab.acaipaidegua.rn.EstabelecimentoRN;
import br.com.bpmlab.acaipaidegua.util.Distancia;
import br.com.bpmlab.acaipaidegua.util.GlobalUtil;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LocalizarFragment extends Fragment {

	private EstabelecimentoRN estabelecimentoRN;
	ListView listV;
	private List<HashMap<String, Object>> estabelecimentos;
	private ListView listaestab;
	DecimalFormat df = new DecimalFormat("0.0");
	String distanciaFormatada;
	private ProgressDialog progresD;
	LocationManager lm = null;
	ProgressDialog pd = null;
	HashMap<String, Object> itemE;
	double latUsuario;
	double lonUsuario;
	private Estabelecimento estabelecimento;
	List<Distancia<Estabelecimento>> estabelecimentoOrdenadoDistancia;
	private TextView nome;
	private TextView endereco;
	private TextView distancia;
	private TextView bairro;
	private ImageButton btnLigar;

	public LocalizarFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		estabelecimento = new Estabelecimento();
		escolherMelhorLatLng();

		pd = ProgressDialog.show(getActivity(), "Aguarde", "...");

		estabelecimentoRN = new EstabelecimentoRN(Estabelecimento.class,
				getActivity());

		View rootView = inflater.inflate(R.layout.fragment_localizar,
				container, false);

		nome = (TextView) rootView.findViewById(R.id.nomeDestaque);
		endereco = (TextView) rootView.findViewById(R.id.enderecoDestaque);
		distancia = (TextView) rootView.findViewById(R.id.distanciaDestaque);
		bairro = (TextView) rootView.findViewById(R.id.bairroDestaque);
		btnLigar = (ImageButton) rootView.findViewById(R.id.ligarDestaque);
		btnLigar.setBackgroundResource(android.R.drawable.menuitem_background);
		
		EstabelecimentoAdapter adapter = new EstabelecimentoAdapter(
				getActivity(), estabelecimentosSemDestaque());
		listaestab = (ListView) rootView
				.findViewById(R.id.lista_estabelecimento);
		listaestab.setAdapter(adapter);
		estabelecimentoDestaque();
		pd.dismiss();
		
		btnLigar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String telefoneDestaque = estabelecimentoOrdenadoDistancia.get(0).getObjeto().getTelefone();
				realizarChamada(telefoneDestaque);
				
			}
		});
		
		return rootView;

	}

	private List<HashMap<String, Object>> estabelecimentosSemDestaque() {
		estabelecimentos = new ArrayList<HashMap<String, Object>>();
		estabelecimentoOrdenadoDistancia = GlobalUtil.ordenarEstabelecimentos(
				estabelecimentoRN.obterTodos(), new BigDecimal(latUsuario),
				new BigDecimal(lonUsuario));
		
		Distancia<Estabelecimento> ed;
		for (int i=1 ; i < estabelecimentoOrdenadoDistancia.size() ; i++) {
			ed = estabelecimentoOrdenadoDistancia.get(i);
			distanciaFormatada = df.format(ed.getDistancia());
			itemE = new HashMap<String, Object>();
			itemE.put("nome", ed.getObjeto().getNome());
			itemE.put("distancia", distanciaFormatada + " KM");
			itemE.put("endereco", ed.getObjeto().getEndereco());
			itemE.put("telefone", ed.getObjeto().getTelefone());
			itemE.put("bairro", ed.getObjeto().getBairro());
			// item.put("ligar", R.drawable.ligar);
			estabelecimentos.add(itemE);

		}

//		for (Distancia<Estabelecimento> ed : estabelecimentoOrdenadoDistancia) {
//			distanciaFormatada = df.format(ed.getDistancia());
//			itemE = new HashMap<String, Object>();
//			itemE.put("nome", ed.getObjeto().getNome());
//			itemE.put("distancia", distanciaFormatada + " KM");
//			itemE.put("endereco", ed.getObjeto().getEndereco());
//			itemE.put("telefone", ed.getObjeto().getTelefone());
//			// item.put("ligar", R.drawable.ligar);
//			estabelecimentos.add(itemE);
//
//		}
		
		System.out.println("lat: " + latUsuario);
		return estabelecimentos;
	}
	
	
	public void estabelecimentoDestaque(){
		String nomeDestaque = estabelecimentoOrdenadoDistancia.get(0)
				.getObjeto().getNome();
		String enderecoDestaque = estabelecimentoOrdenadoDistancia.get(0)
				.getObjeto().getEndereco();
		String distanciaDestaque = df.format(estabelecimentoOrdenadoDistancia
				.get(0).getDistancia()) +" KM";
		String bairroDestaque = estabelecimentoOrdenadoDistancia.get(0).getObjeto().getBairro();
		
		nome.setText(nomeDestaque);
		endereco.setText(enderecoDestaque);
		distancia.setText(distanciaDestaque);
		bairro.setText(bairroDestaque);
	}
	 private void realizarChamada(final String telefone) {
	
	 if (!telefone.equals("")) {
	
	 AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
	 System.out.println("num "+ telefone);
	 alerta.setTitle("Ligação");
	 alerta.setMessage("Deseja realizar uma ligação para o ponto de venda de açaí ?");
	 alerta.setPositiveButton("Sim",
	 new DialogInterface.OnClickListener() {
	
	 @Override
	 public void onClick(DialogInterface dialog, int which) {
	 Uri uri = Uri.parse("tel:" + telefone);
	
	 Intent it = new Intent(Intent.ACTION_CALL, uri);
	 startActivity(it);
	
	 }
	 });
	 alerta.setNegativeButton("Não",
	 new DialogInterface.OnClickListener() {
	
	 @Override
	 public void onClick(DialogInterface dialog, int which) {
	
	 }
	 });
	 alerta.show();
	 } else {
	 Toast.makeText(getActivity(),"Número de telefone não informado",
	 Toast.LENGTH_SHORT).show();
	 }
	
	 }

	public void escolherMelhorLatLng() {
		MainActivity ma = (MainActivity) getActivity();
		if (ma.lat == 0.0 && ma.lon == 0.0 && ma.lastLat != 0.0
				&& ma.lastLon != 0.0) {
			latUsuario = ma.lastLat;
			lonUsuario = ma.lastLon;

			System.out.println("melhor last");
		} else if (ma.lat != 0.0 && ma.lon != 0.0) {
			System.out.println("melhor lat");
			latUsuario = ma.lat;
			lonUsuario = ma.lon;
		} else {
			latUsuario = -1.4621577;
			lonUsuario = -48.4909634;
		}

	}

}
