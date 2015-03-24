package br.com.bpmlab.acaipaidegua.rn;

import android.content.Context;

import java.util.List;

import br.com.bpmlab.acaipaidegua.dao.GenericDAO;

/**
 * Created by geovane on 20/03/15.
 */
public class GenericRN<T> {

    private Context context;
    private Class<T> tipo;

    GenericDAO<T> dao ;

    public GenericRN(Class<T> tipo, Context context){
        this.tipo=tipo;
        this.context=context;
        dao = new GenericDAO(context,tipo);
    }

    public boolean salvar(T e){
        return dao.insert(e);
    }

    public boolean remover (T e){
        return dao.delete(e);
    }

    public boolean atualizar(T e){return dao.update(e); }

    public T obterId(int id){
        return dao.obterId(id);
    }

    public List<T> obterTodos(){
        return dao.obterTodos();
    }
}
