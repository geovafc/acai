package br.com.bpmlab.acaipaidegua.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.bpmlab.acaipaidegua.entidade.Estabelecimento;
import br.com.bpmlab.acaipaidegua.rn.EstabelecimentoRN;

public class GlobalUtil {

	
	public static List<Distancia<Estabelecimento>> ordenarEstabelecimentos(List<Estabelecimento> estabelecimentos,
            BigDecimal latitude,
            BigDecimal longitude) {
        Distancia ed = null;
        List<Distancia<Estabelecimento>> listaED = new ArrayList<Distancia<Estabelecimento>>();
        //Converte de decimal para double
//        BigDecimal bdLat = new BigDecimal(latitude.doubleValue());  
//        double latDouble = bdLat.doubleValue();  
//        
//        BigDecimal bdLon = new BigDecimal(longitude.doubleValue());  
//        double lonDouble = bdLon.doubleValue(); 
        
        if (estabelecimentos != null
                && latitude != null
                && longitude != null) {
            for (Estabelecimento estabelecimento : estabelecimentos) {
            	double distancia = EstabelecimentoRN.distancia(latitude, longitude, estabelecimento.getLatitude(), estabelecimento.getLongitude());
                ed = new Distancia(estabelecimento, distancia);
                listaED.add(ed);
                
            }
            Collections.sort(listaED);
            estabelecimentos.clear();
            
            for (int i = 0; i < listaED.size(); i++) {
                estabelecimentos.add(listaED.get(i).getObjeto());
            }
        }
        return listaED;
    }
}
