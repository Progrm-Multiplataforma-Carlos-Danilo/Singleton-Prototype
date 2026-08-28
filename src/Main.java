/**
 * Roteiro da Secretaria Academica.
 *
 * Demonstra o Singleton (FilaDeImpressao) e o Prototype (Certificado).
 */
public class Main {
    public static void main(String[] args) {

        // 1) PREPARACAO: solicita a instancia unica da fila de impressao.
        System.out.println("=== 1. PREPARACAO ===");
        FilaDeImpressao fila = FilaDeImpressao.getInstancia();

        // 2) O MOLDE: unico "new". Carrega a arte pesada uma so vez,
        //    informando apenas o nome do curso (aluno em branco).
        System.out.println("\n=== 2. O MOLDE (unico new) ===");
        Certificado original = new Certificado("Analise e Desenvolvimento de Sistemas");
        System.out.println("Molde criado. Nome do aluno: \"" + original.getNomeAluno() + "\"");

        // 3) A CLONAGEM: duas copias do molde, sem novo acesso ao banco.
        System.out.println("\n=== 3. A CLONAGEM ===");
        Certificado clone1 = original.clonar();
        Certificado clone2 = original.clonar();
        System.out.println("Dois clones criados sem recarregar a arte do banco.");

        // 4) PERSONALIZACAO: cada clone recebe um aluno diferente.
        System.out.println("\n=== 4. PERSONALIZACAO ===");
        clone1.setNomeAluno("Ana Souza");
        clone2.setNomeAluno("Bruno Lima");
        System.out.println("Clone 1 -> " + clone1.getNomeAluno());
        System.out.println("Clone 2 -> " + clone2.getNomeAluno());

        // 5) IMPRESSAO: os dados dos clones vao para a fila unica.
        System.out.println("\n=== 5. IMPRESSAO ===");
        fila.imprimir(clone1.getDados());
        fila.imprimir(clone2.getDados());

        // 6) VALIDACAO OBRIGATORIA: teste de memoria do Prototype.
        System.out.println("\n=== 6. VALIDACAO (teste de memoria) ===");
        System.out.println("clone1 == clone2 ? " + (clone1 == clone2));
        System.out.println("clone1 == original ? " + (clone1 == original));

        // Prova extra: a fila continua sendo o mesmo objeto (Singleton).
        FilaDeImpressao outraFila = FilaDeImpressao.getInstancia();
        System.out.println("fila == outraFila ? " + (fila == outraFila));
    }
}
