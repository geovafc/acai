package br.com.bpmlab.acaipaidegua.adapter;
import java.util.HashMap;
import java.util.List;

import br.com.bpmlab.acaipaidegua.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EstabelecimentoAdapter extends BaseAdapter{
	private Context context;
	private List<HashMap<String, Object>> listarEstabelecimentos;
	private HashMap<String, Object> estabelecimento;
	
	public EstabelecimentoAdapter (Context context, List<HashMap<String, Object>> listarEstabelecimentos){
		this.context=context;
		this.listarEstabelecimentos=listarEstabelecimentos;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listarEstabelecimentos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listarEstabelecimentos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		 estabelecimento=(HashMap<String, Object>) getItem(position);
		String nome = (String) estabelecimento.get("nome");
		String endereco = (String) estabelecimento.get("endereco");
		String distancia = (String) estabelecimento.get("distancia");
		String bairro = (String) estabelecimento.get("bairro");
		if (convertView == null){
			LayoutInflater layoutI = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutI.inflate(R.layout.model_list_estabelecimento, null);
		}
		
		TextView txtNome= (TextView) convertView.findViewById(R.id.lista_model_nome);
		TextView txtEndereco= (TextView) convertView.findViewById(R.id.lista_model_endereco);
		TextView txtDistancia = (TextView) convertView.findViewById(R.id.lista_model_distancia);
		TextView txtBairro = (TextView) convertView.findViewById(R.id.lista_model_bairro);		
		
		txtNome.setText(nome);
		txtEndereco.setText(endereco);
		txtDistancia.setText(distancia);
		txtBairro.setText(bairro);
		
		 ImageButton b = (ImageButton)convertView.findViewById(R.id.imageButton);
		 b.setClickable(true);
		 b.setFocusable(true);
		 b.setBackgroundResource(android.R.drawable.menuitem_background);
		 b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("clicou");			 
					
				 realizarChamada(estabelecimento.get("telefone").toString());
							
				
			}
		});
		
		return convertView;
	}
	
private void realizarChamada(final String telefone) {
		
		if (!telefone.equals("")) {
			
			AlertDialog.Builder alerta = new AlertDialog.Builder(context);
			System.out.println("num "+ telefone);
			alerta.setTitle("Ligação");
			alerta.setMessage("Deseja realizar uma ligação para o ponto de venda de açaí ?");
			alerta.setPositiveButton("Sim",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Uri uri = Uri.parse("tel:" + telefone);

							Intent it = new Intent(Intent.ACTION_CALL, uri);
							context.startActivity(it);

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
			Toast.makeText(context,"Número de telefone não informado", Toast.LENGTH_SHORT).show();
		}
}


}
