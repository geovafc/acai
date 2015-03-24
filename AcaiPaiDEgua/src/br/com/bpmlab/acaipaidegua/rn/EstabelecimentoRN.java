package br.com.bpmlab.acaipaidegua.rn;

import android.content.Context;
import br.com.bpmlab.acaipaidegua.dao.EstabelecimentoDAO;
import br.com.bpmlab.acaipaidegua.entidade.Estabelecimento;

public class EstabelecimentoRN extends GenericRN<Estabelecimento>{
	EstabelecimentoDAO dao;

	public EstabelecimentoRN(Class<Estabelecimento> tipo, Context context) {
		super(tipo, context);
		dao = new EstabelecimentoDAO(context);
	}

}
