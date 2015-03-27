package br.com.bpmlab.acaipaidegua.rn;

import java.math.BigDecimal;

import android.content.Context;
import br.com.bpmlab.acaipaidegua.dao.EstabelecimentoDAO;
import br.com.bpmlab.acaipaidegua.entidade.Estabelecimento;

public class EstabelecimentoRN extends GenericRN<Estabelecimento>{
	EstabelecimentoDAO dao;

	public EstabelecimentoRN(Class<Estabelecimento> tipo, Context context) {
		super(tipo, context);
		dao = new EstabelecimentoDAO(context);
	}
	
	private static final int RAIO =6371; //Raio da terra

    public static double distancia(BigDecimal latitudeP1, BigDecimal longitudeP1, Double latitudeP2,
    		Double longitudeP2 ){
        if (latitudeP1 == null || latitudeP2 == null || longitudeP1 == null || longitudeP1 == null){
            return -1;
        } else {
            // Latitude e Longitude do Ponto 1
            double latP1 = latitudeP1.doubleValue() * Math.PI / 180;
            double lonP1 = longitudeP1.doubleValue() * Math.PI / 180;
           // Latitude e Longitude de Ponto 2
            double latP2 = latitudeP2.doubleValue() * Math.PI / 180;
            double lonP2 = longitudeP2.doubleValue() * Math.PI/ 180;
            //Diferen�as
            double difLat = latP1 - latP2;
            double difLon = lonP1 - lonP2;

            //Senos das diferen�as das latitudes
            double sinDifLat = Math.sin( difLat / 2);
            double sinDifLon = Math.sin( difLon / 2);

            double valorA = sinDifLat * sinDifLat + Math.cos(latP1) * Math.cos(latP2) * sinDifLon * sinDifLon;
            double valorC = 2 * Math.atan2(Math.sqrt(valorA), Math.sqrt(1 - valorA));

            return valorC * RAIO ;
        }

    }

    public static double distancia(double latitudeP1, double longitudeP1, double latitudeP2, double longitudeP2) {
        // Latitude e Longitude de P1
        double latP1 = latitudeP1 * Math.PI / 180;
        double lonP1 = longitudeP1 * Math.PI / 180;
        // Latitude e Longitude de P2
        double latP2 = latitudeP2 * Math.PI / 180;
        double lonP2 = longitudeP2 * Math.PI / 180;
        // Diferen�as
        double dLat = latP1 - latP2;
        double dLon = lonP1 - lonP2;

        double sinDLat = Math.sin(dLat / 2);
        double sinDLon = Math.sin(dLon / 2);

        double valorA = sinDLat * sinDLat + Math.cos(latP1) * Math.cos(latP2) * sinDLon * sinDLon;
        double valorC = 2 * Math.atan2(Math.sqrt(valorA), Math.sqrt(1 - valorA));

        return valorC * RAIO;

    }

}
