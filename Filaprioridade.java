public class FilaPrioridade {
    private Chamado[] heap;
    private int tamanho;
 
    public FilaPrioridade(int capacidade) {
        heap = new Chamado[capacidade];
        tamanho = 0;
    }
 
    // Item 1.1
    // true se a tem prioridade maior que b; em empate, quem chegou antes (menor id)
    private boolean temPrioridadeSobre(Chamado a, Chamado b) {
        if (a.prioridade != b.prioridade) {
            return a.prioridade > b.prioridade;
        }
        return a.id < b.id;
    }
 
    // Item 1.2
    public void inserir(Chamado c) {
        if (tamanho == heap.length) {
            System.out.println("Fila cheia! Chamado " + c + " nao foi inserido.");
            return;
        }
        heap[tamanho] = c;   // proximo espaco livre
        subir(tamanho);      // restaura a propriedade do heap
        tamanho++;
    }
 
    // Troca o chamado com o pai enquanto ele tiver prioridade sobre o pai
    private void subir(int i) {
        while (i > 0) {
            int pai = (i - 1) / 2;
            if (temPrioridadeSobre(heap[i], heap[pai])) {
                trocar(i, pai);
                i = pai;
            } else {
                break;
            }
        }
    }
 
    // Item 1.3
    public Chamado removerMaior() {
        if (tamanho == 0) {
            return null;
        }
        Chamado raiz = heap[0];
        heap[0] = heap[tamanho - 1];   // ultimo vai para a raiz
        heap[tamanho - 1] = null;
        tamanho--;
        if (tamanho > 0) {
            descer(0);
        }
        return raiz;
    }
 
    // Troca sempre com o filho de maior prioridade (apenas filhos dentro do heap)
    private void descer(int i) {
        while (true) {
            int esq = 2 * i + 1;
            int dir = 2 * i + 2;
            int maior = i;
 
            if (esq < tamanho && temPrioridadeSobre(heap[esq], heap[maior])) {
                maior = esq;
            }
            if (dir < tamanho && temPrioridadeSobre(heap[dir], heap[maior])) {
                maior = dir;
            }
            if (maior == i) {
                break;
            }
            trocar(i, maior);
            i = maior;
        }
    }
 
    private void trocar(int i, int j) {
        Chamado aux = heap[i];
        heap[i] = heap[j];
        heap[j] = aux;
    }
 
    public void imprimirVetor() {
        for (int i = 0; i < tamanho; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
}
