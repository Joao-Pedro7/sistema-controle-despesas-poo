# Projeto: Sistema de Controle de Despesas

Este projeto é um sistema de console para gerenciamento de despesas, desenvolvido como projeto final da disciplina de Programação Orientada a Objetos.

## Estratégia de Construção (PARTE 01)

O sistema será desenvolvido em Java, seguindo os princípios de POO. A estratégia de construção focará em separar as responsabilidades do sistema:

1.  **Modelos (Models):** Classes que representam os dados (ex: `Usuario`, `Despesa`, `TipoDespesa`).
2.  **Repositórios (Repositories):** Classes responsáveis pela persistência (leitura e escrita) em arquivos de texto.
3.  **Serviços (Services):** Classes que conterão as regras de negócio (validações, criptografia, cálculos).
4.  **Interface (UI):** A classe `Main` será responsável pelo menu e interação com o usuário.

## Documentação Inicial de Classes e Métodos

Abaixo está a abstração inicial das classes que representarão as regras de negócio:

---

### Classe: `Usuario`
-   **Atributos:**
    -   `private int id`
    -   `private String login`
    -   `private String senhaCriptografada`
-   **Métodos:**
    -   `public Usuario(String login, String senhaPlana)`: Construtor que recebe a senha, criptografa e armazena.
    -   `public boolean verificarSenha(String senhaPlana)`: Verifica se a senha fornecida bate com a senha armazenada.
    -   `private String criptografar(String senhaPlana)`: Método privado para criptografar a senha (usará o método estático de uma classe utilitária).

---

### Classe Abstrata: `Despesa`
-   **Atributos:**
    -   `protected int id`
    -   `protected String descricao`
    -   `protected double valor`
    -   `protected Date dataVencimento`
    -   `protected TipoDespesa tipoDespesa`
    -   `protected boolean estaPaga`
    -   `protected Pagamento pagamento`
-   **Métodos:**
    -   `public Despesa(String desc, double val, Date venc)`: Construtor básico.
    -   `public void anotarPagamento(Pagamento p)`: Marca a despesa como paga e armazena os dados do pagamento.
    -   `public abstract String getCategoriaDetalhada()`: Método abstrato para ser implementado pelas subclasses.

---

### Classe: `DespesaTransporte` (Herda de `Despesa`)
-   **Atributos:**
    -   `private String tipo` (ex: "App", "Combustível", "Público")
-   **Métodos:**
    -   `public DespesaTransporte(...)`: Construtor sobrecarregado.
    -   `@Override public String getCategoriaDetalhada()`: Sobrescreve o método para retornar "Transporte - [tipo]".

---

### Classe: `DespesaAlimentacao` (Herda de `Despesa`)
-   **Atributos:**
    -   `private String local` (ex: "Supermercado", "Restaurante")
-   **Métodos:**
    -   `public DespesaAlimentacao(...)`: Construtor sobrecarregado.
    -   `@Override public String getCategoriaDetalhada()`: Sobrescreve o método para retornar "Alimentação - [local]".

---

### Interface: `Pagavel`
-   **Métodos:**
    -   `void pagar(double valor, Date data)`
    -   `double getValorPendente()`

*(Nota: A classe Despesa implementaria esta interface)*

---

### Classe: `TipoDespesaRepository` (Gerenciador de Arquivo)
-   **Atributos:**
    -   `private static final String ARQUIVO_TIPOS = "tipos_despesa.txt"`
-   **Métodos:**
    -   `public void salvar(TipoDespesa tipo)`
    -   `public List<TipoDespesa> listarTodos()`
    -   `public void editar(TipoDespesa tipoAntigo, TipoDespesa tipoNovo)`
    -   `public void excluir(TipoDespesa tipo)`

*(...e assim por diante para `UsuarioRepository` e `DespesaRepository`)*


---

## B4T01.2: Separação de Prioridades (MoSCoW)

Para definir a ordem de construção, as funcionalidades do sistema foram priorizadas usando o método MoSCoW:

### M - MUST HAVE (Obrigatório)
*São as funções centrais. Sem elas, o sistema não tem valor.*
-   **Gerenciar Tipos de Despesa:** Criar e Listar (uma despesa precisa de um tipo).
-   **Entrar Despesa:** Inserir uma nova despesa.
-   **Listar Despesas em Aberto:** Ver o que foi inserido.
-   **Persistência em Arquivo:** Salvar e ler os dados em arquivos de texto (o núcleo do projeto).

### S - SHOULD HAVE (Deveria Ter)
*São importantes, mas não vitais para o primeiro funcionamento.*
-   **Anotar Pagamento:** Marcar uma despesa como paga (Conciliação).
-   **Listar Despesas Pagas:** Filtro de despesas pagas.
-   **Gerenciar Usuários:** Cadastro com login e senha.
-   **Criptografia de Senhas:** Implementar a segurança de senha.

### C - COULD HAVE (Poderia Ter)
*São "nice-to-have", melhorias que podem ficar para o final.*
-   **Editar Despesa:** Alterar dados de uma despesa.
-   **Excluir Despesa:** Remover uma despesa.
-   **Editar/Excluir Tipos de Despesa:** Gerenciamento completo.
-   **Editar/Listar Usuários:** Gerenciamento completo de usuários.

### W - WON'T HAVE (Não Terá... por agora)
-   Interface Gráfica (GUI).
-   Relatórios complexos ou gráficos.
-   Integração com bancos de dados (ex: SQL).

---

## B4T01.2: Projeto do MVP (Minimum Viable Product)

Com base nas prioridades, o **MVP (Produto Mínimo Viável)** é a primeira versão funcional que será construída.

O objetivo do MVP será:
1.  Permitir que o usuário **crie um Tipo de Despesa** (ex: "Alimentação").
2.  Permitir que o usuário **insira uma Despesa** (ex: "Jantar", 50.00) usando o tipo criado.
3.  Permitir que o usuário **liste as Despesas em Aberto** e veja a despesa que acabou de criar.
4.  **Tudo isso deve ser salvo e lido de arquivos de texto.**

*Obs: O MVP inicial não terá o sistema de login (Gerenciar Usuários) ou a função de Anotar Pagamento. Essas são as funcionalidades da V2.*