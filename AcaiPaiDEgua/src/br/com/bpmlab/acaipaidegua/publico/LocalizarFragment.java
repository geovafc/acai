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
import br.com.bpmlab.acaipaidegua.util.LatLonUtil;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LocalizarFragment extends Fragment {

	private EstabelecimentoRN estabelecimentoRN;
	ListView listV;
	private Estabelecimento estabelecimento1;
	private List<HashMap<String, Object>> estabelecimentos;
	private double distancia;
	private String[] nome;
	private List<String> nomes;
	private ListView listaestab;
	private double latUsuario=LatLonUtil.latUsuario;
	private double lonUsuario=LatLonUtil.lonUsuario;
	DecimalFormat df = new DecimalFormat("0.0");  
	String distanciaFormatada ;


	public LocalizarFragment() {
	}
	
	String[] nomeStrings = new String []{"rayan", "Geovane"};

	private List<HashMap<String, Object>> listarEstabelecimentos() {
		estabelecimentos = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> item;
		for (Estabelecimento e : estabelecimentoRN.obterTodos()) {
			distancia=(estabelecimentoRN.distancia(latUsuario,lonUsuario, e.getLatitude(), e.getLongitude()))/1000;
			distanciaFormatada = df.format(distancia); 
			
			item = new HashMap<String, Object>();
			item.put("nome", e.getNome());
			System.out.println(e.getNome());
			item.put("distancia", distanciaFormatada+" KM");
			System.out.println(distancia);
			// item.put("ligar", R.drawable.ligar);
			estabelecimentos.add(item);
			
		}
		System.out.println("Quantidade: "+estabelecimentos.size());
		return estabelecimentos;
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

//		List<Estabelecimento> estabelecimentoOrdenado= new ArrayList<Estabelecimento>();
//		estabelecimentoOrdenado=ordenarEstabelecimentos(estabelecimentos,
//	             new BigDecimal(latUsuario),
//	            new BigDecimal(lonUsuario));
		String[] de = { "nome", "distancia" };
		int[] para = { R.id.lista_model_nome, R.id.distancia  };
		//
		SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(),
				listarEstabelecimentos(), R.layout.model_list_estabelecimento,
				de, para);
		//
		listaestab = (ListView) rootView.findViewById(R.id.lista_estabelecimento);
		listaestab.setAdapter(adapter);
		return rootView;

	}

//	public void ordenarEstabelecimentos(List<Estabelecimento> estabelecimentos,
//            BigDecimal latitude,
//            BigDecimal longitude) {
//        Distancia ed = null;
//        List<Distancia<Estabelecimento>> listaED = new ArrayList<Distancia<Estabelecimento>>();
//        //Converte de decimal para double
//        BigDecimal bdLat = new BigDecimal(latitude.doubleValue());  
//        double latDouble = bdLat.doubleValue();  
//        
//        BigDecimal bdLon = new BigDecimal(longitude.doubleValue());  
//        double lonDouble = bdLon.doubleValue(); 
//        
//        if (estabelecimentos != null
//                && latitude != null
//                && longitude != null) {
//            for (Estabelecimento estabelecimento : estabelecimentos) {
//                int distancia = (int) estabelecimentoRN.distancia(latDouble, lonDouble, estabelecimento.getLatitude(), estabelecimento.getLongitude());
//                ed = new Distancia(estabelecimento, distancia);
//                listaED.add(ed);
//            }
//            Collections.sort(listaED);
//            estabelecimentos.clear();
////            for (int i = 0; i < listaTD.size(); i++) {
////                taxistas.add(listaTD.get(i).getObjeto());
////            }
//        }
//    }
	

	private void realizarChamada() {

	}

}
