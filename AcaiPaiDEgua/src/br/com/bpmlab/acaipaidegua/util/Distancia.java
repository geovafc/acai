package br.com.bpmlab.acaipaidegua.util;

public class Distancia<T> implements Comparable<Distancia<T>> {

    private final T t;
    private  Integer distancia;

    public Distancia(T t,
            Integer distancia) {
        this.t = t;
        this.distancia = distancia;
    }
    
    

    public Integer getDistancia() {
		return distancia;
	}




	@Override
    public int compareTo(Distancia<T> o) {
        if (o == null) {
            return -1;
        } else {
            return distancia.compareTo(o.distancia);
        }
    }

    public T getObjeto() {
        return t;
    }

    @Override
    public String toString() {
        return t + " - " + distancia;
    }
    
    

}
