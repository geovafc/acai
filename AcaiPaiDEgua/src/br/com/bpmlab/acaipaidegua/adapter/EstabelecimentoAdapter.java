package br.com.bpmlab.acaipaidegua.adapter;

import java.util.List;

import br.com.bpmlab.acaipaidegua.entidade.Estabelecimento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EstabelecimentoAdapter extends BaseAdapter{
	private Context context;
	private List<Estabelecimento> estabelecimentos;
	
	public EstabelecimentoAdapter (Context context, List<Estabelecimento> estabelecimentos){
		this.context=context;
		this.estabelecimentos=estabelecimentos;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return estabelecimentos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return estabelecimentos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Estabelecimento e=(Estabelecimento) getItem(position);
		LayoutInflater layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		View v= layout.inflate(br.com.bpmlab.acaipaidegua.R.layout.fragment_localizar, null);
		TextView txtNome= (TextView) v.findViewById(br.com.bpmlab.acaipaidegua.R.id.list);
		txtNome.setText(e.getNome());
		return v;
	}

}
