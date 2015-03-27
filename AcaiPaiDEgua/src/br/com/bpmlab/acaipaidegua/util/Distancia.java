package br.com.bpmlab.acaipaidegua.util;

import java.math.BigDecimal;

public class Distancia<T> implements Comparable<Distancia<T>> {

    private final T t;
    private  Double distancia;

    public Distancia(T t,
    		Double distancia) {
        this.t = t;
        this.distancia = distancia;
    }
    
    

    public Double getDistancia() {
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
