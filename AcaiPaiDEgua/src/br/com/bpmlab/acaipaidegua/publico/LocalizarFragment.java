package br.com.bpmlab.acaipaidegua.publico;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.com.bpmlab.acaipaidegua.R;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
	double latUsuario;
	double lonUsuario;
	private Estabelecimento estabelecimento;
	List<Distancia<Estabelecimento>> estabelecimentoOrdenadoDistancia;

	public LocalizarFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MainActivity ma = (MainActivity) getActivity();
		latUsuario = ma.lat;
		lonUsuario = ma.lon;

		pd = ProgressDialog.show(getActivity(), "Aguarde", "...");

		estabelecimentoRN = new EstabelecimentoRN(Estabelecimento.class,
				getActivity());

		View rootView = inflater.inflate(R.layout.fragment_localizar,
				container, false);

		String[] de = { "nome", "distancia", "endereco", "telefone" };
		int[] para = { R.id.lista_model_nome, R.id.lista_model_distancia,
				R.id.lista_model_endereco, R.id.lista_model_telefone };

		SimpleAdapter adapter = new SimpleAdapter(getActivity()
				.getBaseContext(), listarEstabelecimentos(),
				R.layout.model_list_estabelecimento, de, para);

		//
		listaestab = (ListView) rootView
				.findViewById(R.id.lista_estabelecimento);
		listaestab.setAdapter(adapter);
		pd.dismiss();

		listaestab
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> item, View v,
							int position, long id) {
						System.out.println("clicou");

						// estabelecimento =(Estabelecimento)
						// item.getItemAtPosition(position);
						// realizarChamada(estabelecimento.getTelefone());
					}
				});

		return rootView;

	}

	//
	// public void onListItemClick(ListView l, View v, int position, long d){
	// System.out.println("clicou");
	//
	// estabelecimento =(Estabelecimento) l.getAdapter().getItem(position);
	// realizarChamada(estabelecimento.getTelefone());
	// }

	private List<HashMap<String, Object>> listarEstabelecimentos() {
		estabelecimentos = new ArrayList<HashMap<String, Object>>();
		estabelecimentoOrdenadoDistancia = GlobalUtil.ordenarEstabelecimentos(
				estabelecimentoRN.obterTodos(), new BigDecimal(latUsuario),
				new BigDecimal(lonUsuario));
		HashMap<String, Object> item;
		for (Distancia<Estabelecimento> ed : estabelecimentoOrdenadoDistancia) {
			distanciaFormatada = df.format(ed.getDistancia());
			item = new HashMap<String, Object>();
			item.put("nome", ed.getObjeto().getNome());
			item.put("distancia", distanciaFormatada + " KM");
			item.put("endereco", ed.getObjeto().getEndereco());
			item.put("telefone", ed.getObjeto().getTelefone());
			// item.put("ligar", R.drawable.ligar);
			estabelecimentos.add(item);

		}
		System.out.println("lat: " + latUsuario);
		return estabelecimentos;
	}

	private void realizarChamada(final String telefone) {

		if (telefone != null) {
			AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
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

		}

	}

}
