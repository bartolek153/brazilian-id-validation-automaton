package br.org.faculdadesalvadorarena.analisador_sintatico;

import br.org.faculdadesalvadorarena.analisador_sintatico.ast.No;
import br.org.faculdadesalvadorarena.analisador_sintatico.lexer.AnalisadorLexico;
import br.org.faculdadesalvadorarena.analisador_sintatico.parser.AnalisadorSintatico;
import br.org.faculdadesalvadorarena.analisador_sintatico.parser.ErroSintatico;

import java.util.Scanner;

/**
 * Aplicação principal para testar o analisador sintático.
 */
public class AnalisadorSintaticoApp {
    
    public static void main(String[] args) {
        System.out.println("Analisador Sintático Recursivo Descendente");
        System.out.println("Gramática:");
        System.out.println("E → E + T | E - T | T");
        System.out.println("T → T * F | T / F | F");
        System.out.println("F → F++ | F-- | ( E ) | id");
        
        // Verifica se deve executar os testes automatizados
        if (args.length > 0 && args[0].equals("--testes")) {
            executarTestes();
            return;
        }
        
        System.out.println("\nDigite 'sair' para encerrar o programa.");
        System.out.println("Digite 'testes' para executar os testes automatizados.");
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("\nDigite uma expressão: ");
            String entrada = scanner.nextLine().trim();
            
            if (entrada.equalsIgnoreCase("sair")) {
                break;
            }
            
            if (entrada.equalsIgnoreCase("testes")) {
                executarTestes();
                continue;
            }
            
            if (entrada.isEmpty()) {
                continue;
            }
            
            try {
                // Cria o analisador léxico
                AnalisadorLexico lexer = new AnalisadorLexico(entrada);
                
                // Cria o analisador sintático
                AnalisadorSintatico parser = new AnalisadorSintatico(lexer);
                
                // Realiza a análise sintática
                No raiz = parser.analisar();
                
                // Imprime a árvore sintática
                System.out.println("\nÁrvore Sintática:");
                System.out.println(raiz.imprimirArvore());
                
                System.out.println("Análise sintática concluída com sucesso!");
            } catch (ErroSintatico e) {
                System.out.println("Erro: " + e.getMessage());
                
                // Imprime uma indicação visual do erro
                System.out.println(entrada);
                for (int i = 0; i < e.getPosicao(); i++) {
                    System.out.print(" ");
                }
                System.out.println("^");
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        scanner.close();
        System.out.println("Programa encerrado.");
    }
    
    /**
     * Executa testes automatizados com casos de aceitação e rejeição.
     */
    public static void executarTestes() {
        System.out.println("Executando testes automatizados...");
        
        // Casos de aceitação
        String[] casosAceitacao = {
            "x",
            "x + y",
            "x * y + z",
            "(x + y) * z",
            "x++ * y--"
        };
        
        // Casos de rejeição
        String[] casosRejeicao = {
            "",
            "+",
            "x +",
            "x + + y",
            "x ) + y"
        };
        
        System.out.println("\n=== Casos de Aceitação ===");
        for (int i = 0; i < casosAceitacao.length; i++) {
            String caso = casosAceitacao[i];
            System.out.println("\nCaso " + (i + 1) + ": " + caso);
            
            try {
                AnalisadorLexico lexer = new AnalisadorLexico(caso);
                AnalisadorSintatico parser = new AnalisadorSintatico(lexer);
                No raiz = parser.analisar();
                
                System.out.println("Resultado: ACEITO");
                System.out.println("Árvore Sintática:");
                System.out.println(raiz.imprimirArvore());
            } catch (Exception e) {
                System.out.println("Resultado: ERRO (deveria ser aceito)");
                System.out.println("Erro: " + e.getMessage());
            }
        }
        
        System.out.println("\n=== Casos de Rejeição ===");
        for (int i = 0; i < casosRejeicao.length; i++) {
            String caso = casosRejeicao[i];
            System.out.println("\nCaso " + (i + 1) + ": " + caso);
            
            try {
                AnalisadorLexico lexer = new AnalisadorLexico(caso);
                AnalisadorSintatico parser = new AnalisadorSintatico(lexer);
                parser.analisar();
                
                System.out.println("Resultado: ACEITO (deveria ser rejeitado)");
            } catch (Exception e) {
                System.out.println("Resultado: REJEITADO (como esperado)");
                System.out.println("Erro: " + e.getMessage());
            }
        }
        
        System.out.println("\nTestes concluídos.");
    }
}
