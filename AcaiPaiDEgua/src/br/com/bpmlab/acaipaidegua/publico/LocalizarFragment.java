package br.com.bpmlab.acaipaidegua.publico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.support.DatabaseConnection;

import br.com.bpmlab.acaipaidegua.R;
import br.com.bpmlab.acaipaidegua.dao.EstabelecimentoDAO;
import br.com.bpmlab.acaipaidegua.entidade.Estabelecimento;
import br.com.bpmlab.acaipaidegua.rn.EstabelecimentoRN;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LocalizarFragment extends ListFragment  {

	private EstabelecimentoRN estabelecimentoRN;
	ListView listV;
	private Estabelecimento estabelecimento1;
	private List<Map<String, Object>> estabelecimentos;
	private double distancia;
	private String[] nome;
	private List<String> nomes ;
	private ListView listaestab;

	public LocalizarFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		estabelecimentoRN = new EstabelecimentoRN(Estabelecimento.class,
				getActivity());

	View rootView = inflater.inflate(R.layout.fragment_localizar,
				container, false);

	
		/*
		 * Calcular a distância entre dois pontos
		 * 
		 * TextView dist= (TextView) findViewById(R.id.dist);
		 * 
		 * Estabelecimento estabelecimento2 = new Estabelecimento();
		 * 
		 * estabelecimento1= estabelecimentoRN.obterId(1); estabelecimento2=
		 * estabelecimentoRN.obterId(3);
		 * 
		 * System.out.println("nome1 "+estabelecimento1.getNome() +
		 * "nome2 "+estabelecimento2.getNome()); double distanciaMetro =
		 * DistanciaUtil.distancia(estabelecimento1.getLatitude(),
		 * estabelecimento1.getLongitude(), estabelecimento2.getLatitude(),
		 * estabelecimento2.getLongitude()); double distanciaKM = distanciaMetro
		 * / 1000.0;
		 * 
		 * dist.setText(Double.toString(distanciaKM));
		 */

//		 for (Estabelecimento e : estabelecimentoRN.obterTodos()) {
//		
//		 nome = new String[] {e.getNome()};
//		 System.out.println(e.getNome());
//		 }
		
		
//	 ArrayAdapter<String> nomesAdapter = new ArrayAdapter<String>(this.getActivity(),
//		 android.R.layout.simple_list_item_1,nomesEstabelecimento());
//		 listV.setAdapter(nomesAdapter);
	
	
	
	listaestab = (ListView) rootView.findViewById(R.id.list);
	
	//EstabelecimentoDAO dao = new EstabelecimentoDAO(getActivity());
	

	//Cursor cursor = db.rawQuery("SELECT * FROM batedores", null);
		 
        String[] de = { "nome"};
		int[] para = { R.id.lista_model_nome};
//
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), listarEstabelecimentos(),R.layout.model_list_estabelecimento, de, para);
//		
		
		listaestab.setAdapter(adapter);
		return rootView;
	
	}
	
//	public List<String> nomesEstabelecimento(){
//		nomes= new ArrayList<String>();
//		for(Estabelecimento e: estabelecimentoRN.obterTodos()){
//			nomes.add(e.getNome());
//		}
//		return nomes;
//	}
//	
//	@Override
//	public void onStart(){
//		
////		setListAdapter(adapter);		
//		//getListView().setOnItemClickListener(this);
//	}
	
	private List<Map<String, Object>> listarEstabelecimentos() {
		estabelecimentos = new ArrayList<Map<String, Object>>();
		Map<String, Object> item;
		for (Estabelecimento e : estabelecimentoRN.obterTodos()) {
			item = new HashMap<String, Object>();
			item.put("nome", e.getNome());
			//item.put("distancia", "km");
//			item.put("ligar", R.drawable.ligar);
			estabelecimentos.add(item);
		}

		return estabelecimentos;
	}

	private void realizarChamada() {

	}



}
