/**
 * Padrao Prototype.
 *
 * A arte e os dados do curso sao "pesados" para carregar do banco.
 * Por isso carregamos um certificado padrao uma unica vez com "new"
 * e clonamos esse molde para cada aluno, trocando apenas o nome.
 */
public class Certificado implements Cloneable {

    private String nomeAluno;
    private String nomeCurso;
    private String artePesada;

    /**
     * Construtor "caro": simula a carga da arte do certificado no banco de dados.
     * Deve ser chamado uma unica vez, para montar o molde.
     */
    public Certificado(String nomeCurso) {
        this.nomeCurso = nomeCurso;
        this.nomeAluno = ""; // o molde nasce sem aluno
        System.out.println("[Certificado] Carregando arte pesada do banco de dados...");
        this.artePesada = "Arte oficial (brasao, moldura e assinaturas) do curso de " + nomeCurso;
    }

    /**
     * Cria uma copia independente deste certificado, sem repetir
     * o carregamento da arte no banco.
     */
    public Certificado clonar() {
        try {
            return (Certificado) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Falha ao clonar o certificado", e);
        }
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    /** Texto final do certificado, pronto para ser enviado a fila de impressao. */
    public String getDados() {
        return "Certificado de conclusao | Aluno(a): " + nomeAluno
                + " | Curso: " + nomeCurso
                + " | " + artePesada;
    }
}
