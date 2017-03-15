/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package training;

import co.expertic.training.service.test.IntensityZoneSesionDist;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Edwin G
 */
public class GeneratePlan {

    public static void main(String[] args) {

        Integer horas_entrenamiento = 5;
        Integer min_semanales = horas_entrenamiento * 60;
        Integer num_sesiones = 4;
        Map<Integer, Integer> carga_semana = new HashMap<>();
        carga_semana.put(1, 95);
        carga_semana.put(2, 100);
        carga_semana.put(3, 105);
        carga_semana.put(4, 60);

        Map<Integer, Integer> carga_intensidad_sesion = new HashMap<>();
        carga_intensidad_sesion.put(1, 110);
        carga_intensidad_sesion.put(2, 100);
        carga_intensidad_sesion.put(3, 100);
        carga_intensidad_sesion.put(4, 90);

        Map<Integer, Integer> dist_int_zona = new HashMap<>();
        dist_int_zona.put(1, 0);
        dist_int_zona.put(2, 40);
        dist_int_zona.put(3, 28);
        dist_int_zona.put(4, 22);
        dist_int_zona.put(5, 8);
        dist_int_zona.put(6, 2);
        
        Map<Integer, Double> tiempo_zona_serie_dist = new HashMap<>();
        tiempo_zona_serie_dist.put(1, 15.0);
        tiempo_zona_serie_dist.put(1, 30.0);
        tiempo_zona_serie_dist.put(1, 45.0);
        tiempo_zona_serie_dist.put(1, 60.0);
        tiempo_zona_serie_dist.put(1, 75.0);
        tiempo_zona_serie_dist.put(1, 90.0);
        
        tiempo_zona_serie_dist.put(2, 15.0);
        tiempo_zona_serie_dist.put(2, 30.0);
        tiempo_zona_serie_dist.put(2, 45.0);
        tiempo_zona_serie_dist.put(2, 60.0);
        tiempo_zona_serie_dist.put(2, 75.0);
        tiempo_zona_serie_dist.put(2, 90.0);
        tiempo_zona_serie_dist.put(2, 105.0);
        tiempo_zona_serie_dist.put(2, 120.0);
        tiempo_zona_serie_dist.put(2, 135.0);
        tiempo_zona_serie_dist.put(2, 150.0);
        tiempo_zona_serie_dist.put(2, 165.0);
        tiempo_zona_serie_dist.put(2, 180.0);
        
        tiempo_zona_serie_dist.put(3, 20.0);
        tiempo_zona_serie_dist.put(3, 25.0);
        tiempo_zona_serie_dist.put(3, 30.0);
        tiempo_zona_serie_dist.put(3, 35.0);
        tiempo_zona_serie_dist.put(3, 40.0);
        tiempo_zona_serie_dist.put(3, 45.0);
        tiempo_zona_serie_dist.put(3, 50.0);
        tiempo_zona_serie_dist.put(3, 55.0);
        tiempo_zona_serie_dist.put(3, 60.0);
        tiempo_zona_serie_dist.put(3, 65.0);
        tiempo_zona_serie_dist.put(3, 70.0);
        tiempo_zona_serie_dist.put(3, 75.0);
        
        tiempo_zona_serie_dist.put(4, 10.0);
        tiempo_zona_serie_dist.put(4, 15.0);
        tiempo_zona_serie_dist.put(4, 20.0);
        tiempo_zona_serie_dist.put(4, 25.0);
        tiempo_zona_serie_dist.put(4, 30.0);
        tiempo_zona_serie_dist.put(4, 35.0);
        tiempo_zona_serie_dist.put(4, 40.0);
        tiempo_zona_serie_dist.put(4, 45.0);
        tiempo_zona_serie_dist.put(4, 50.0);
        tiempo_zona_serie_dist.put(4, 55.0);
        tiempo_zona_serie_dist.put(4, 60.0);
        tiempo_zona_serie_dist.put(4, 65.0);
        
        tiempo_zona_serie_dist.put(5, 10.0);
        tiempo_zona_serie_dist.put(5, 12.0);
        tiempo_zona_serie_dist.put(5, 14.0);
        tiempo_zona_serie_dist.put(5, 16.0);
        tiempo_zona_serie_dist.put(5, 18.0);
        tiempo_zona_serie_dist.put(5, 20.0);
        tiempo_zona_serie_dist.put(5, 22.0);
        tiempo_zona_serie_dist.put(5, 24.0);
        tiempo_zona_serie_dist.put(5, 26.0);
        tiempo_zona_serie_dist.put(5, 28.0);
        tiempo_zona_serie_dist.put(5, 30.0);
        tiempo_zona_serie_dist.put(5, 32.0);
        
        tiempo_zona_serie_dist.put(6, 2.0);
        tiempo_zona_serie_dist.put(6, 2.5);
        tiempo_zona_serie_dist.put(6, 3.0);
        tiempo_zona_serie_dist.put(6, 3.5);
        tiempo_zona_serie_dist.put(6, 4.0);
        tiempo_zona_serie_dist.put(6, 4.5);
        tiempo_zona_serie_dist.put(6, 5.0);
        tiempo_zona_serie_dist.put(6, 5.5);
        tiempo_zona_serie_dist.put(6, 6.0);
        tiempo_zona_serie_dist.put(6, 6.5);
        tiempo_zona_serie_dist.put(6, 7.0);
        tiempo_zona_serie_dist.put(6, 7.5);
        

        Map<Integer, Double> min_x_semana = new HashMap<>();
        Map<Integer, Double> dist_int_tiempo_sesion = new HashMap<>();
        Map<Integer, Double> dist_int_tiempo_zona = new HashMap<>();

        List<IntensityZoneSesionDist> dist = new ArrayList<>();
        dist.add(new IntensityZoneSesionDist(1, 1, 0));
        dist.add(new IntensityZoneSesionDist(1, 2, 0));
        dist.add(new IntensityZoneSesionDist(1, 3, 0));
        dist.add(new IntensityZoneSesionDist(1, 4, 0));
        dist.add(new IntensityZoneSesionDist(1, 5, 0));
        dist.add(new IntensityZoneSesionDist(1, 6, 0));
        dist.add(new IntensityZoneSesionDist(2, 1, 0));
        dist.add(new IntensityZoneSesionDist(2, 2, 0));
        dist.add(new IntensityZoneSesionDist(2, 3, 70));
        dist.add(new IntensityZoneSesionDist(2, 4, 35));
        dist.add(new IntensityZoneSesionDist(2, 5, 0));
        dist.add(new IntensityZoneSesionDist(2, 6, 0));
        dist.add(new IntensityZoneSesionDist(3, 1, 0));
        dist.add(new IntensityZoneSesionDist(3, 2, 0));
        dist.add(new IntensityZoneSesionDist(3, 3, 30));
        dist.add(new IntensityZoneSesionDist(3, 4, 65));
        dist.add(new IntensityZoneSesionDist(3, 5, 20));
        dist.add(new IntensityZoneSesionDist(3, 6, 0));
        dist.add(new IntensityZoneSesionDist(4, 1, 0));
        dist.add(new IntensityZoneSesionDist(4, 2, 0));
        dist.add(new IntensityZoneSesionDist(4, 3, 0));
        dist.add(new IntensityZoneSesionDist(4, 4, 0));
        dist.add(new IntensityZoneSesionDist(4, 5, 80));
        dist.add(new IntensityZoneSesionDist(4, 6, 100));

        carga_semana.keySet().stream().forEach((Integer key) -> {
            System.out.println("Semana #" + key);
            double base = (min_semanales * carga_semana.get(key)) / 100;
            min_x_semana.put(key, base);
            double min_x_dia = base / num_sesiones;

            System.out.println("base:" + base + " minutos/dia:" + min_x_dia);
            //System.exit(0);

            carga_intensidad_sesion.keySet().stream().forEach((Integer k) -> {
                double min_x_sesion = (min_x_dia * carga_intensidad_sesion.get(k)) / 100;
                dist_int_tiempo_sesion.put(k, min_x_sesion);

                System.out.println("sesion:" + k + " tiempo:" + min_x_sesion);

            });

            //dist_int_zona.keySet().stream().forEach((Integer k) -> {
            Iterator it = dist_int_zona.keySet().iterator();
            while (it.hasNext()) {
                Integer k = (Integer) it.next();
                double tiempo_x_zona = (base * dist_int_zona.get(k)) / 100;
                dist_int_tiempo_zona.put(k, tiempo_x_zona);

                System.out.println("zona:" + k + " tiempo x zona:" + tiempo_x_zona);

                for (IntensityZoneSesionDist intensityZoneSesionDist : dist) {
                    if (Objects.equals(k, intensityZoneSesionDist.getZone()) && intensityZoneSesionDist.getZone() != 2) {
                        double min_zona_sesion = (intensityZoneSesionDist.getPercentaje() * tiempo_x_zona) / 100;
                        intensityZoneSesionDist.setTime(min_zona_sesion);

                        //System.out.println("zone:"+ intensityZoneSesionDist.getZone()+" sesion:"+intensityZoneSesionDist.getSesion()+" time:"+intensityZoneSesionDist.getTime() );
                    }

                }

                /*  Map<Integer, Double> sum_tiempo_zona = dist.stream().collect(
                Collectors.groupingBy(IntensityZoneSesionDist::getSesion, Collectors.summingDouble(IntensityZoneSesionDist::getTime)));*/
                //relleno zona 2 con el faltante del tiempo_x_zona 
            };

            Map<Integer, Double> sum_tiempo_sesion = new HashMap<>();

            for (int i = 1; i <= num_sesiones; i++) {
                Double sum_min_zona_sesion = 0.0;
                for (IntensityZoneSesionDist item : dist) {
                    if (item.getSesion() == i && item.getTime() != null) {
                        sum_min_zona_sesion += item.getTime();
                    }
                }
                sum_tiempo_sesion.put(i, sum_min_zona_sesion);
            }
            
            dist_int_tiempo_sesion.keySet().stream().forEach((Integer s) -> {
                for (IntensityZoneSesionDist intensityZoneSesionDist : dist) {
                    if(Objects.equals(intensityZoneSesionDist.getSesion(), s) && intensityZoneSesionDist.getZone() == 2){
                        intensityZoneSesionDist.setTime(dist_int_tiempo_sesion.get(s)- sum_tiempo_sesion.get(s));
                    }
                }
                
            });

            /*sum_tiempo_sesion.keySet().stream().forEach((Integer s) -> {
                System.out.println("map sesion:" + s + " tiempo total:" + sum_tiempo_sesion.get(s));
            });*/

            dist.stream().forEach((d)->{
                  System.out.println("zone: "+d.getZone()+" sesion:"+d.getSesion()+" time:"+d.getTime());
        });
            
            
            System.exit(0);

        });

    }

}
