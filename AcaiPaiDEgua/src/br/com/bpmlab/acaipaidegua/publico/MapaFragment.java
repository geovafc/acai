package br.com.bpmlab.acaipaidegua.publico;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.bpmlab.acaipaidegua.R;
import br.com.bpmlab.acaipaidegua.entidade.Estabelecimento;
import br.com.bpmlab.acaipaidegua.rn.EstabelecimentoRN;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapaFragment extends Fragment {

	private LatLng location;
	private GoogleMap map;
	private MapFragment mapfragment;
	View rootView;
	private EstabelecimentoRN estabelecimentoRN;
	private List<Estabelecimento> estabelecimentos;
	private Double lat;
	private Double lon;
	private String nome;

	public MapaFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView != null) {
			ViewGroup parent = (ViewGroup) rootView.getParent();

			if (parent != null)
				parent.removeView(rootView);
		}
		try {
			rootView = inflater.inflate(R.layout.fragment_mapa, container,
					false);
		} catch (InflateException e) {

		}

		estabelecimentoRN = new EstabelecimentoRN(Estabelecimento.class,
				getActivity());
		estabelecimentos = new ArrayList<Estabelecimento>();
		estabelecimentos = estabelecimentoRN.obterTodos();

		mapfragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);
		map = mapfragment.getMap();

		map.setMyLocationEnabled(true);

		/** mostra o mata com imagem de sat�lite */
		// map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

		/** *Adcionar marcadores */
		for (Estabelecimento estabelecimento : estabelecimentos) {
			lat = estabelecimento.getLatitude();
			lon = estabelecimento.getLongitude();
			nome = estabelecimento.getNome();
			String endereco = estabelecimento.getEndereco();
			location = new LatLng(lat, lon);

			MarkerOptions marcador = new MarkerOptions();
			marcador.position(location);
			marcador.title(nome).snippet(endereco);
			marcador.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
			map.addMarker(marcador);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 20));
			map.animateCamera(CameraUpdateFactory.zoomTo(12), 200, null);

			}

		return rootView;
	}



	
	@Override
	public void onDestroyView() {
		super.onDestroyView();

		if (mapfragment != null)
			getFragmentManager().beginTransaction().remove(mapfragment)
					.commit();
	}
}
