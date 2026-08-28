/**
 * Padrao Singleton.
 *
 * Garante que exista uma unica conexao com a impressora central da secretaria.
 * Se o sistema criasse varias filas, os documentos sairiam misturados
 * ou a impressora travaria.
 */
public class FilaDeImpressao {

    // Unica instancia da classe. Fica em atributo estatico para ser
    // compartilhada por todo o sistema.
    private static FilaDeImpressao instancia;

    // Construtor privado: ninguem de fora consegue dar "new FilaDeImpressao()".
    private FilaDeImpressao() {
        System.out.println("[FilaDeImpressao] Conexao com a impressora central estabelecida.");
    }

    /**
     * Unico ponto de acesso a fila. Cria a instancia na primeira chamada
     * (lazy loading) e devolve sempre o mesmo objeto nas chamadas seguintes.
     */
    public static FilaDeImpressao getInstancia() {
        if (instancia == null) {
            instancia = new FilaDeImpressao();
        }
        return instancia;
    }

    /** Envia um documento para a impressora central. */
    public void imprimir(String documento) {
        System.out.println("[IMPRIMINDO] " + documento);
    }
}
