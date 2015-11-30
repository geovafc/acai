package br.com.bpmlab.acaipaidegua.publico;

import br.com.bpmlab.acaipaidegua.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NovidadeFragment extends Fragment{

	View rootView;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_novidade, container, false);
			

		return rootView;
	}
}
