public class Main {
    static Chamado[] criarChamados() {
        int[] prioridades = {3, 3, 5, 4, 5, 1, 4, 2};
        Chamado[] chamados = new Chamado[prioridades.length];
        for (int i = 0; i < prioridades.length; i++) {
            chamados[i] = new Chamado(i + 1, prioridades[i]);
        }
        return chamados;
    }

    // Item 2.1 - Insertion Sort decrescente por prioridade (estavel)
    static void insertionSort(Chamado[] v) {
        int deslocamentos = 0;
        for (int i = 1; i < v.length; i++) {
            Chamado chave = v[i];
            int j = i - 1;
            // desloca so quem tem prioridade MENOR que a chave (iguais nao sao ultrapassados)
            while (j >= 0 && v[j].prioridade < chave.prioridade) {
                v[j + 1] = v[j];   // desloca
                deslocamentos++;
                j--;
            }
            v[j + 1] = chave;
        }
        System.out.println("Insertion Sort - deslocamentos: " + deslocamentos);
    }

    // Item 2.2 - Selection Sort decrescente por prioridade (instavel)
    static void selectionSort(Chamado[] v) {
        int trocas = 0;
        for (int i = 0; i < v.length - 1; i++) {
            int maior = i;
            for (int j = i + 1; j < v.length; j++) {
                if (v[j].prioridade > v[maior].prioridade) {   // comparacao estrita
                    maior = j;
                }
            }
            if (maior != i) {   // troca so quando necessario
                Chamado aux = v[i];
                v[i] = v[maior];
                v[maior] = aux;
                trocas++;
            }
        }
        System.out.println("Selection Sort - trocas: " + trocas);
    }

    static void imprimirIds(String titulo, Chamado[] v) {
        System.out.print(titulo);
        for (int i = 0; i < v.length; i++) {
            System.out.print(v[i].id + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Chamado[] chamados = criarChamados();

        // ---------- Parte 1 ----------
        System.out.println("=== Parte 1: Fila de prioridade (heap maximo) ===");
        FilaPrioridade fila = new FilaPrioridade(chamados.length);
        for (int i = 0; i < chamados.length; i++) {
            fila.inserir(chamados[i]);   // ordem de chegada
        }
        System.out.print("Vetor do heap: ");
        fila.imprimirVetor();

        Chamado[] ordemFila = new Chamado[chamados.length];
        int k = 0;
        System.out.print("Ordem de atendimento: ");
        Chamado atual = fila.removerMaior();
        while (atual != null) {
            System.out.print(atual + " ");
            ordemFila[k++] = atual;
            atual = fila.removerMaior();
        }
        System.out.println();

        // ---------- Parte 2 ----------
        System.out.println();
        System.out.println("=== Parte 2: Estabilidade ===");
        Chamado[] copiaInsertion = chamados.clone();
        insertionSort(copiaInsertion);

        Chamado[] copiaSelection = chamados.clone();
        selectionSort(copiaSelection);

        // Item 2.3
        System.out.println();
        imprimirIds("Ids - Fila (heap):     ", ordemFila);
        imprimirIds("Ids - Insertion Sort:  ", copiaInsertion);
        imprimirIds("Ids - Selection Sort:  ", copiaSelection);

        /*
         * ANALISE DA ESTABILIDADE (item 2.3)
         *
         * Fila (heap):     3 5 4 7 1 2 8 6  -> respeita a ordem de chegada em todos os empates,
         *                  porque temPrioridadeSobre desempata pelo menor id.
         * Insertion Sort:  3 5 4 7 1 2 8 6  -> estavel: nenhum empate saiu da ordem de chegada
         *                  (so desloca quem tem prioridade menor que a chave, nunca os iguais).
         * Selection Sort:  3 5 4 7 2 1 8 6  -> instavel: os chamados #1 e #2 (ambos P3)
         *                  ficaram fora da ordem de chegada (#2 foi atendido antes do #1).
         *                  Isso aconteceu porque, em i = 2, a troca v[2] <-> v[3] mandou o
         *                  #1 para a posicao do #4, passando-o a frente do #2.
         *                  Os empates de P5 (#3, #5) e P4 (#4, #7) ficaram na ordem certa.
         */
    }
}
