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
import com.google.android.gms.maps.model.CameraPosition;
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

		// Habilitar botão de zoom no mapa
		map.getUiSettings().setZoomControlsEnabled(true);
		// habilitar compasso de búlsola
		map.getUiSettings().setCompassEnabled(true);

		// map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 20));
		// map.animateCamera(CameraUpdateFactory.zoomTo(12), 200, null);
		// habiliza o zoom por gestos, dois cliques
		// map.getUiSettings().setZoomGesturesEnabled(true);
		/** mostra o mata com imagem de satï¿½lite */
		// map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

		LatLng casa = new LatLng(-1.4562033, -48.477089715);
		// Construct a CameraPosition focusing on Mountain View and animate the
		// camera to that position.
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(casa) // Define o centro do mapa de
				.zoom(13) // Define o zoom
				.bearing(350) // Define a orientação da câmara para leste
				.tilt(0) // Define a inclinação da câmara a 30 graus
				.build(); // Cria um CameraPosition do construtor

		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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
			marcador.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.ic_marker));
			map.addMarker(marcador);

		}

		return rootView;
	}

	// @Override
	// public void onDetach() {
	// super.onDetach();
	//
	// try {
	// Field childFragmentManager = Fragment.class
	// .getDeclaredField("mChildFragmentManager");
	// childFragmentManager.setAccessible(true);
	// childFragmentManager.set(this, null);
	//
	// } catch (NoSuchFieldException e) {
	// throw new RuntimeException(e);
	// } catch (IllegalAccessException e) {
	// throw new RuntimeException(e);
	// }
	// }

	// @Override
	// public void onDestroyView() {
	// super.onDestroyView();
	//
	// if (mapfragment != null)
	// getFragmentManager().beginTransaction().remove(mapfragment)
	// .commit();
	// }

	@Override
	public void onDestroy() {

		if (mapfragment.isResumed()) {
			getFragmentManager().beginTransaction().remove(mapfragment)
					.commit();
		}
		super.onDestroy();
	}

}
