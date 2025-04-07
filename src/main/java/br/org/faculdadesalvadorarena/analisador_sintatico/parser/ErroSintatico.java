package br.org.faculdadesalvadorarena.analisador_sintatico.parser;

/**
 * Exceção lançada quando ocorre um erro sintático durante a análise.
 */
public class ErroSintatico extends RuntimeException {
    private final int posicao;
    
    public ErroSintatico(String mensagem, int posicao) {
        super(mensagem);
        this.posicao = posicao;
    }
    
    public int getPosicao() {
        return posicao;
    }
    
    @Override
    public String toString() {
        return "Erro sintático na posição " + posicao + ": " + getMessage();
    }
}
