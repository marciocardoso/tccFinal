/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytcc;

import java.util.ArrayList;

/**
 *
 * @author Labin-L1
 * @author Marcio Cardoso
 */
public class WSN {

    private static ArrayList sensorList = new ArrayList<Sensor>();

    public static Sensor getSensor(int index) {
        return (Sensor) sensorList.get(index);
    }

    public static ArrayList getSensorList() {
        return sensorList;
    }

    public static void addSensor(Sensor sensor) {
        sensorList.add(sensor);
    }

    public static int size() {
        return sensorList.size();
    }
    
    // retorna o sensor com o maior valor de coornada X
    // para que sejam gerador Relay Nodes dentro deste intervalo
    public static int getHighestX() {
        int highestX = getSensor(0).getX();
        for (int i = 1; i < size(); i++) {
            if(getSensor(i).getX() >= highestX) {
                highestX = getSensor(i).getX();
            }
        }
        return highestX;
    }
    
    // retorna o sensor com o maior valor de coordenada Y
    // para que sejam gerador Relay Nodes dentro deste intervalo
    public static int getHighestY() {
        int highestY = getSensor(0).getY();
        for (int i = 1; i < size(); i++) {
            if(getSensor(i).getY() >= highestY) {
                highestY = getSensor(i).getY();
            }
        }
        return highestY;
    }
    
    public static double area() {
        return getHighestX() * getHighestY();
    }
}
