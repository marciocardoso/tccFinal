/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytcc;

/**
 *
 * @author Labin-L1
 * @author Marcio Cardoso
 */
public class Sensor {

    //coordenadas do sensor no Plano
    int x;
    int y;

    //guarda todos os componentes conexos deste sensor
    //private ArrayList connected_sensors = new ArrayList<Sensor>();
    //atribui uma posição aleatória ao sensor;
    public Sensor() {
        this.x = (int) (Math.random() * WSN.getHighestX());
        this.y = (int) (Math.random() * WSN.getHighestY());
    }

    public Sensor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //gets para as coordenadas
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    //calcula e retorna a distance deste sensor até qualquer outro        
    public double distanceTo(Sensor sensor) {
        int xDistance = Math.abs(getX() - sensor.getX());
        int yDistance = Math.abs(getY() - sensor.getY());

        double distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

        return distance;
    }

    //retorna a area de cobertura do sensor
    public static double area() {
        return (Math.PI * ConstantNumber.SENSOR_RANGE * ConstantNumber.SENSOR_RANGE);
    }

    //checa se um RN toca em alguem
    public boolean reachWSN() {
        //assume-se que o nó não toca 
        boolean reach = false;
        // para cada RN
        for (int i = 0; i < WSN.size(); i++) {
            if (this.distanceTo(WSN.getSensor(i)) <= ConstantNumber.SENSOR_RANGE) {
                reach = true;
            }            
        }
        return reach;
    }
}
