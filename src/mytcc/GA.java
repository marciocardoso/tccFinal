/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytcc;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Labin-L1
 * @author Marcio Cardoso
 */
public class GA {

    /*PARAMETROS*/
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);

        //mantem nosso melhor individuo na populacao
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.addRnNetwork(0, pop.getFittest());
            elitismOffset = 1;
        }

        //proceso de crossover
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            // seleciona os pais
            RelayNodeNetwork parent1 = tournamentSelection(pop);
            RelayNodeNetwork parent2 = tournamentSelection(pop);

            RelayNodeNetwork child = crossover(parent1, parent2);
            newPopulation.addRnNetwork(i, child);
        }

        /*MUTACAO*/
        for (int i = elitismOffset = 0; i < newPopulation.size(); i++) {
            mutate(newPopulation.getRnNetwork(i));
        }
        return newPopulation;
    }

    /*CROSSOVER*/
    public static RelayNodeNetwork crossover(RelayNodeNetwork parent1, RelayNodeNetwork parent2) {
        //filho que será retornado    
        RelayNodeNetwork child;
        int childSize;

        int startPos;
        int endPos;

        //tamanhos do maior e menor pais
        RelayNodeNetwork biggestParent;
        RelayNodeNetwork smallerParent;

        //o tamanho do filho terá o tamanho do maior pai
        if (parent1.size() >= parent2.size()) {
            childSize = parent1.size();
            biggestParent = parent1;
            smallerParent = parent2;
            //seleciona o inicio e o final do intervalo que será adicionado ao filho
            int temp1 = 1, temp2 = 1;
            while (temp1 == temp2) {
                temp1 = (int) (Math.random() * (parent2.size() - 1));
                temp2 = (int) (Math.random() * (parent2.size() - 1));
            }
            if (temp1 > temp2) {
                startPos = temp2;
                endPos = temp1;
            } else {
                startPos = temp1;
                endPos = temp2;
            }
        } else {
            childSize = parent2.size();
            biggestParent = parent2;
            smallerParent = parent1;
            //seleciona o inicio e o final do intervalo que será adicionado ao filho
            int temp1 = 1, temp2 = 1;
            while (temp1 == temp2) {
                temp1 = (int) (Math.random() * (parent1.size() - 1));
                temp2 = (int) (Math.random() * (parent1.size() - 1));
            }
            if (temp1 > temp2) {
                startPos = temp2;
                endPos = temp1;
            } else {
                startPos = temp1;
                endPos = temp2;
            }
        }
        //faz um loop colocando os sensores dos pais nos filhos
        child = new RelayNodeNetwork(childSize);
        for (int i = 0; i < childSize; i++) {
            //coloca os primeiros elementos do maior pai
            if (i < startPos) {
                int X = biggestParent.getRelayNode(i).getX();
                int Y = biggestParent.getRelayNode(i).getY();
                Sensor newRelayNode = new Sensor(X, Y);
                child.replaceRelayNode(i, newRelayNode);
            } //coloca os elementos intermediários sorteados do menor pai
            else if (i <= endPos) {
                int X = smallerParent.getRelayNode(i).getX();
                int Y = smallerParent.getRelayNode(i).getY();
                Sensor newRelayNode = new Sensor(X, Y);
                child.replaceRelayNode(i, newRelayNode);
            } //completa o filho com os elementos restantes do maior pai
            else {
                int X = biggestParent.getRelayNode(i).getX();
                int Y = biggestParent.getRelayNode(i).getY();
                Sensor newRelayNode = new Sensor(X, Y);
                child.replaceRelayNode(i, newRelayNode);
            }
        }

        // Remove o relay node que não tocar em nenhum ponto da WSN
        for (int i = 0; i < child.size(); i++) {
            if (child.getRelayNode(i).reachWSN() == false) {
                child.removeRelayNode(i);
                break;
            }
        }
        return child;
    }

    /*MUTATE ------ CRITICAL METHOD*/
    public static void mutate(RelayNodeNetwork rnNetwork) {
        //percorre cada relay node
        //mudando ou não de posição
        for (int i = 0; i < rnNetwork.size(); i++) {
            if (Math.random() < mutationRate) {
                int newX = (int) (Math.random() * WSN.getHighestX());
                int newY = (int) (Math.random() * WSN.getHighestY());

                rnNetwork.changeRNposition(i, newX, newY);
            }
        }
    }

    //seleciona indivíduos para crossover
    private static RelayNodeNetwork tournamentSelection(Population pop) {
        // Cria uma população para torneio
        Population tournament = new Population(tournamentSize, false);
        // para cada indivíduo da população de torneio
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.addRnNetwork(i, pop.getRnNetwork(randomId));
        }
        // pega o de maior fitness e retona
        RelayNodeNetwork fittest = tournament.getFittest();
        return fittest;
    }
}
