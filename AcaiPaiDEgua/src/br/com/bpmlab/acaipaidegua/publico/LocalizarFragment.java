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
import android.app.Fragment;
import android.app.ProgressDialog;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LocalizarFragment extends Fragment {

	
	private EstabelecimentoRN estabelecimentoRN;
	ListView listV;
	private List<HashMap<String, Object>> estabelecimentos;
	private double distancia;
	private ListView listaestab;
	DecimalFormat df = new DecimalFormat("0.0");  
	String distanciaFormatada ;
	private ProgressDialog progresD;
	LocationManager lm = null;
	ProgressDialog pd = null;
	double latUsuario ;
	double lonUsuario;
	List<Distancia<Estabelecimento>> estabelecimentoOrdenadoDistancia;

	public LocalizarFragment() {	
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		MainActivity ma = (MainActivity) getActivity();
		latUsuario = ma.lat;
		lonUsuario = ma.lon;
		
	pd=ProgressDialog.show(getActivity(), "Aguarde", "...");

		estabelecimentoRN = new EstabelecimentoRN(Estabelecimento.class,
				getActivity());

		View rootView = inflater.inflate(R.layout.fragment_localizar,
				container, false);


		
//		List<Distancia<Estabelecimento>> estabelecimentoOrdenado= new ArrayList<Distancia<Estabelecimento>>();
//		estabelecimentoOrdenado=ordenarEstabelecimentos(estabDesord,
//	             new BigDecimal(latUsuario),
//	            new BigDecimal(lonUsuario));
//		for (Distancia<Estabelecimento> ed: estabelecimentoOrdenado){
//			System.out.println("estabelecimento " +ed.getObjeto().getNome()+ "distancia "+ed.getDistancia());
//		}
		
		
		String[] de = { "nome", "distancia", "endereco", "telefone" };
		int[] para = { R.id.lista_model_nome, R.id.lista_model_distancia, R.id.lista_model_endereco, R.id.lista_model_telefone };
		
		SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(),
				listarEstabelecimentos(), R.layout.model_list_estabelecimento,
				de, para);
		
		//
		listaestab = (ListView) rootView.findViewById(R.id.lista_estabelecimento);
		listaestab.setAdapter(adapter);
		pd.dismiss();
		
		return rootView;

	}

	public List<Distancia<Estabelecimento>> ordenarEstabelecimentos(List<Estabelecimento> estabelecimentos,
            BigDecimal latitude,
            BigDecimal longitude) {
        Distancia ed = null;
        List<Distancia<Estabelecimento>> listaED = new ArrayList<Distancia<Estabelecimento>>();
        //Converte de decimal para double
        BigDecimal bdLat = new BigDecimal(latitude.doubleValue());  
        double latDouble = bdLat.doubleValue();  
        
        BigDecimal bdLon = new BigDecimal(longitude.doubleValue());  
        double lonDouble = bdLon.doubleValue(); 
        
        if (estabelecimentos != null
                && latitude != null
                && longitude != null) {
            for (Estabelecimento estabelecimento : estabelecimentos) {
                int distancia = (int) estabelecimentoRN.distancia(latDouble, lonDouble, estabelecimento.getLatitude(), estabelecimento.getLongitude());
                ed = new Distancia(estabelecimento, distancia);
                listaED.add(ed);
                
            }
            Collections.sort(listaED);
            estabelecimentos.clear();
            
            for (int i = 0; i < listaED.size(); i++) {
                estabelecimentos.add(listaED.get(i).getObjeto());
            }
        }
        return listaED;
    }
	
	private List<HashMap<String, Object>> listarEstabelecimentos() {
		estabelecimentos = new ArrayList<HashMap<String, Object>>();
		estabelecimentoOrdenadoDistancia=ordenarEstabelecimentos(estabelecimentoRN.obterTodos(),
	             new BigDecimal(latUsuario),
	            new BigDecimal(lonUsuario));
		HashMap<String, Object> item;
		for (Distancia<Estabelecimento> e : estabelecimentoOrdenadoDistancia) {
			//distancia=(estabelecimentoRN.distancia(latUsuario,lonUsuario, e.getLatitude(), e.getLongitude()));
			distanciaFormatada = df.format(e.getDistancia()); 			
			item = new HashMap<String, Object>();
			item.put("nome", e.getObjeto().getNome());
			item.put("distancia", distanciaFormatada+" KM");
			item.put("endereco", e.getObjeto().getEndereco()+", "+e.getObjeto().getBairro());
			item.put("telefone", e.getObjeto().getTelefone());			
			// item.put("ligar", R.drawable.ligar);
			estabelecimentos.add(item);
			
		}
		System.out.println("lat: "+latUsuario);
		return estabelecimentos;
	}
		
	private void realizarChamada() {

	}

	
}
