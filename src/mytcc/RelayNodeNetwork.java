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
public class RelayNodeNetwork {

    private ArrayList<Sensor> relayNodeList = new ArrayList<Sensor>();

    public RelayNodeNetwork() {

        int defaultSize = (int) (WSN.area() / Sensor.area());

        for (int i = 0; i < defaultSize; i++) {
            int x = (int) (Math.random() * WSN.getHighestX());
            int y = (int) (Math.random() * WSN.getHighestY());

            Sensor relayNode = new Sensor(x, y);

            addRelayNode(relayNode);
        }
    }

    //cria uma RN vazia
    public RelayNodeNetwork(int size) {
        for (int i = 0; i < size; i++) {
            addRelayNode(null);
        }

    }

    public void addRelayNode(Sensor newRelayNode) {
        this.relayNodeList.add(newRelayNode);
    }

    // subsitui um espaço vazio por um RN de fato
    public void replaceRelayNode(int index, Sensor newRelayNode) {
        this.relayNodeList.remove(index);
        this.relayNodeList.add(index, newRelayNode);
    }

    public void removeRelayNode(int index) {
        this.relayNodeList.remove(index);
    }

    //muda as coordenadas de um RN específico
    public void changeRNposition(int index, int newX, int newY) {
        getRelayNode(index).setX(newX);
        getRelayNode(index).setY(newY);
    }

    public Sensor getRelayNode(int index) {
        return (Sensor) (this.relayNodeList.get(index));
    }
    
    public ArrayList<Sensor> getRelayNodeList() {
        return this.relayNodeList;
    }

    // tamanho deste RN
    public int size() {
        return this.relayNodeList.size();
    }

    //calcula a quantidade de sensores não alcançados por esta RN
    public int numberOfUncoveredSensors() {
        int numberOfUncovered = 0;
        // para cada sensor da WSN
        for (int i = 0; i < WSN.size(); i++) {
            boolean reached = false;
            //para cada relay node
            for (int j = 0; j < size(); j++) {
                if (getRelayNode(j) != null) {
                    if (WSN.getSensor(i).distanceTo(getRelayNode(j)) <= ConstantNumber.SENSOR_RANGE) {
                        reached = true;
                        break;
                    }
                }
            }
            if (reached == false) {
                numberOfUncovered++;
            }
        }
        return numberOfUncovered;
    }
    
    //checa se um sensor desta rede alcanca outro sensor
    public boolean reachAnyOtherRelayNode(int relayNodeIndex) {        
        for (int i = 0; i < size(); i++) {
            if (i != relayNodeIndex) {
                if (getRelayNode(relayNodeIndex).distanceTo(getRelayNode(i)) <= ConstantNumber.SENSOR_RANGE) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public double getFitness() {
        double weight = (double)(WsnGraph.numberOfGraphs(getRelayNodeList()));
        double fitness = 1.0/(Math.pow(weight, 3.0) + (double)numberOfUncoveredSensors());
        return fitness;
    }    
    
}
