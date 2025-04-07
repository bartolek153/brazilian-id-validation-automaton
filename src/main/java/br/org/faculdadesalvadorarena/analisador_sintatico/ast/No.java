package br.org.faculdadesalvadorarena.analisador_sintatico.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe base para todos os nós da árvore sintática.
 */
public abstract class No {
    private final List<No> filhos;
    private final String valor;
    
    public No(String valor) {
        this.valor = valor;
        this.filhos = new ArrayList<>();
    }
    
    public void adicionarFilho(No filho) {
        filhos.add(filho);
    }
    
    public List<No> getFilhos() {
        return filhos;
    }
    
    public String getValor() {
        return valor;
    }
    
    /**
     * Retorna uma representação em string da árvore a partir deste nó.
     */
    public String imprimirArvore() {
        return imprimirArvore(0);
    }
    
    /**
     * Método auxiliar para imprimir a árvore com indentação.
     */
    private String imprimirArvore(int nivel) {
        StringBuilder sb = new StringBuilder();
        
        // Indentação
        String indentacao = "  ".repeat(nivel);
        
        // Imprime o nó atual
        sb.append(indentacao).append(this).append("\n");
        
        // Imprime os filhos
        for (No filho : filhos) {
            sb.append(filho.imprimirArvore(nivel + 1));
        }
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + valor;
    }
}
