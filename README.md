# Sistema Secretaria Acadêmica — Singleton + Prototype

Projeto em Java que implementa dois padrões de projeto (GoF) no contexto da emissão
de certificados de conclusão de uma secretaria acadêmica:

- **Singleton** — `FilaDeImpressao`: garante uma única conexão com a impressora central.
- **Prototype** — `Certificado`: carrega a arte/dados do curso uma única vez e clona
  esse molde para cada aluno, alterando apenas o nome.

## Estrutura do projeto

```
src/
├── FilaDeImpressao.java   # Singleton  (fila única de impressão)
├── Certificado.java       # Prototype  (molde clonável do certificado)
└── Main.java              # Roteiro de execução
```

## Como executar

Requer JDK 17 ou superior (desenvolvido e testado no JDK 21).

```bash
javac -d bin src/*.java
java -cp bin Main
```

No VS Code, basta abrir `src/Main.java` e clicar em **Run**.

## Como a atividade foi realizada

### 1. Singleton — `FilaDeImpressao`

O problema: se o sistema criar várias filas de impressão, os documentos saem
misturados ou a impressora trava. A solução foi restringir a criação de objetos:

- **Construtor privado** — impede `new FilaDeImpressao()` fora da própria classe.
- **Atributo estático `instancia`** — guarda a única instância existente.
- **Método estático `getInstancia()`** — único ponto de acesso. Na primeira chamada
  cria o objeto (*lazy loading*); nas seguintes devolve sempre o mesmo.
- **Método `imprimir(String documento)`** — recebe o texto do certificado e o envia
  para a impressora central.

### 2. Prototype — `Certificado`

O problema: carregar a arte e os dados do curso do banco de dados a cada aluno é
caro. A solução foi criar um molde uma única vez e cloná-lo:

- A classe implementa `Cloneable`.
- O **construtor** recebe apenas o nome do curso, inicializa `nomeAluno` em branco
  e simula a carga pesada da arte no banco (imprime a mensagem de carregamento).
  Ele é chamado **uma única vez** em todo o programa.
- O método **`clonar()`** usa `super.clone()` para produzir uma cópia rasa do objeto,
  devolvendo um `Certificado` novo em memória — sem repetir o acesso ao banco.
  A `CloneNotSupportedException` é tratada internamente, então quem chama `clonar()`
  não precisa lidar com exceção verificada.
- **`setNomeAluno(String)`** personaliza cada cópia.
- **`getDados()`** monta o texto final do certificado (aluno + curso + arte) que é
  entregue à fila de impressão.

Como todos os atributos são `String` (imutáveis em Java), a cópia rasa do
`super.clone()` já é suficiente: alterar o nome do aluno de um clone não afeta o
molde nem o outro clone.

### 3. Roteiro da `Main`

1. **Preparação** — solicita a instância única com `FilaDeImpressao.getInstancia()`.
2. **O molde** — cria o `Certificado` original com `new`, informando só o nome do
   curso; o nome do aluno fica em branco.
3. **A clonagem** — chama `clonar()` duas vezes, gerando `clone1` e `clone2`.
4. **Personalização** — `setNomeAluno()` com dois alunos diferentes, um em cada clone.
5. **Impressão** — envia `clone1.getDados()` e `clone2.getDados()` para `imprimir()`.
6. **Validação** — imprime o teste de memória `clone1 == clone2`.

### 4. Validação obrigatória

O teste `clone1 == clone2` compara **referências de memória**, não conteúdo. O
resultado `false` prova que a clonagem gerou dois objetos independentes na *heap*.
Foram incluídas duas checagens extras: `clone1 == original` (também `false`,
confirmando que o clone não é o molde) e `fila == outraFila`, que resulta `true` e
comprova que o Singleton devolve sempre a mesma instância.

## Saída esperada

```
=== 1. PREPARACAO ===
[FilaDeImpressao] Conexao com a impressora central estabelecida.

=== 2. O MOLDE (unico new) ===
[Certificado] Carregando arte pesada do banco de dados...
Molde criado. Nome do aluno: ""

=== 3. A CLONAGEM ===
Dois clones criados sem recarregar a arte do banco.

=== 4. PERSONALIZACAO ===
Clone 1 -> Ana Souza
Clone 2 -> Bruno Lima

=== 5. IMPRESSAO ===
[IMPRIMINDO] Certificado de conclusao | Aluno(a): Ana Souza | Curso: Analise e Desenvolvimento de Sistemas | Arte oficial (brasao, moldura e assinaturas) do curso de Analise e Desenvolvimento de Sistemas
[IMPRIMINDO] Certificado de conclusao | Aluno(a): Bruno Lima | Curso: Analise e Desenvolvimento de Sistemas | Arte oficial (brasao, moldura e assinaturas) do curso de Analise e Desenvolvimento de Sistemas

=== 6. VALIDACAO (teste de memoria) ===
clone1 == clone2 ? false
clone1 == original ? false
fila == outraFila ? true
```

Repare que a mensagem de carregamento do banco aparece **uma única vez**, mesmo
tendo três certificados em memória (o molde e os dois clones) — é exatamente o
ganho que o Prototype entrega.
