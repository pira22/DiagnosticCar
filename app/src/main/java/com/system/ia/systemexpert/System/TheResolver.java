package com.system.ia.systemexpert.System;

import android.util.Log;

import com.system.ia.systemexpert.Entity.ReponseSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by pira on 14/11/15.
 */
public class TheResolver {

    HashMap<String,String> propositionMap = new HashMap<String,String>();
    String[] propositions ;
    public TheResolver(String[] propostions) {
        propositionMap.put("P1","Injecteur défectueux");
        propositionMap.put("P2","Pompe d’injection dérèglé");
        propositionMap.put("P3","Calage de pompe d’injection");
        propositionMap.put("P4","Chapelles d’admission encrassées");
        propositionMap.put("P5","Moteur trop froid");
        propositionMap.put("P6"," Problème de culasse");
        propositionMap.put("P7","Moteur consomme d’huile");
        propositionMap.put("P8"," Calage de pompe");
        propositionMap.put("P9","Usure");
        propositionMap.put("P10","Moteur consomme d’huile");
        propositionMap.put("P11","Joint de culasse");
        propositionMap.put("P12","Usure de disque");
        propositionMap.put("P13","Usure de volant moteur");
        propositionMap.put("P14","Changer filtre à air");
        propositionMap.put("P15","Vérifier les bougies sont encrassés ou non");
        propositionMap.put("P16","Moteur est fatigue");
        propositionMap.put("P17","Admission d’air farinée");
        propositionMap.put("P18","Développement insuffisant de  l’avance");
        propositionMap.put("P19","Jeu de segments de pistons");
        propositionMap.put("P20","Chemise piston");
        propositionMap.put("P21","Changer eau de radiateur");
        propositionMap.put("P22","Changer le radiateur");
        propositionMap.put("P23"," Vérifier le thermostat fonctionne ou non");
        propositionMap.put("P24"," Vérifier le ventilateur de radiateur");
        propositionMap.put("P25","Vérifier la batterie");
        propositionMap.put("C1","Vérifier l’état des charnières de porte");
        propositionMap.put("C2","Réparation et peinture la voiture");
        propositionMap.put("C3","Rotule de direction fatiguée");
        propositionMap.put("C4","les soufflets des rotules ouverts");
        propositionMap.put("C5","Contrôler le restant des rotules et bras sur les 2 trains");
        propositionMap.put("C6"," Vérifier les 2 bras de suspension");
        propositionMap.put("C7","faire un parallélisme");
        propositionMap.put("C8"," changer ses amortisseurs de suspension");
        propositionMap.put("C9","Vérifier le  fonctionnement des systèmes de suspension");
        propositionMap.put("C10","Consultez votre mécanicien ");
        propositionMap.put("C11","Problème de pompe de direction assistée ");
        propositionMap.put("C12","Courroie de direction assistée usée ou cassée");
        propositionMap.put("C13","Baisse du niveau de liquide de direction assistée ");
        propositionMap.put("C14","Vérifier l’air des pneus ");
        this.propositions = propostions;
    }

    public List<String> trieTab(){
        final Map<String, Integer> map = new HashMap<String, Integer>();

        for (String s : propositions) {
            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }

        List<String> list = new ArrayList<String>(map.keySet());
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String x, String y) {
                return map.get(y) - map.get(x);
            }
        });


        return list;
    }

    public String getProp(){
        Log.d("results",propositionMap.get(trieTab().get(0).toString()).toString());
          return  propositionMap.get(trieTab().get(0).toString()).toString();
    }

}
