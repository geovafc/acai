package br.com.bpmlab.acaipaidegua.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.util.List;
import br.com.bpmlab.acaipaidegua.conexao.DataBaseHelper;

public class GenericDAO<E> extends DataBaseHelper<E> {
    protected Dao<E, Integer> dao;
    private Class<E> type;

    public GenericDAO(Context context, Class<E> type) {
        // Super permite acessar o construtor da classe pai(DataBaseHelper) para
        // passar os
        // parametros
        super(context);
        this.type = type;
        setDao();
    }

    protected void setDao() {
        try {
            dao = DaoManager.createDao(getConnectionSource(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<E> obterTodos() {
        try {
            List<E> result = dao.queryForAll();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public E obterId(int id) {
        try {
            E obj = dao.queryForId(id);
            
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao obter:" + e.getMessage());
            return null;
        }
    }

    public boolean insert(E obj) {
        try {
            System.out.println("obg"+obj.getClass());
            dao.create(obj);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir:" + e.getMessage());
            return false;
        }

    }

    public boolean delete(E obj) {
        try {
            dao.delete(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(E obj) {
        try {
            dao.update(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
