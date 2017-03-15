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
public class Population {
    
    private RelayNodeNetwork[] RelayNodeNetworks;
    
    public Population(int size, boolean fillPopulation) {
        RelayNodeNetworks = new RelayNodeNetwork[size];
        
        //inializa a população com indivíduos, se tiver que ser feito
        for (int i = 0; i < size; i++) {
            RelayNodeNetwork newRnNetwork = new RelayNodeNetwork();
            addRnNetwork(i, newRnNetwork);
        }
    }
    
    // guarda um individuo na populacao
    public void addRnNetwork(int index, RelayNodeNetwork newRnNetwork) {
        RelayNodeNetworks[index] = newRnNetwork;
    }
    
    //retorna um individuo especifico
    public RelayNodeNetwork getRnNetwork(int index) {
        return this.RelayNodeNetworks[index];
    }
    
    //retorna o individuo mais apto da população
    public RelayNodeNetwork getFittest() {
        RelayNodeNetwork fittest = this.RelayNodeNetworks[0];
        //percorre todos os inivíduos e encontra o mais apto
        for (int i = 1; i < size(); i++) {
            if (fittest.getFitness() <= getRnNetwork(i).getFitness()) {
                fittest = getRnNetwork(i);
            }
        }
        return fittest;
    }
    
    public int size() {
        return this.RelayNodeNetworks.length;
    }

}
