# CHANGELOG - Sistema de Controle de Despesas

## v2.0.0-FINAL - (09/11/2025) (Entrega B4_A_TRABALHO)

-   **PROJETO 100% CONCLUÍDO.**
-   Implementada a refatoração para POO avançado:
    -   Interface `Pagavel` e classe `Pagamento`.
    -   Classe `Despesa` transformada em `abstract`.
    -   Classe `DespesaPadrao` criada (Herança) com Construtor Sobrecarga.
-   Funcionalidade **"Anotar Pagamento" (Opção 2)** implementada.
-   Funcionalidade **"Listar Despesas Pagas" (Opção 4)** implementada.
-   Funcionalidade **"Gerenciar Tipos de Despesa" (Opção 5)** agora inclui "Editar" e "Excluir" (com trava de segurança).
-   Funcionalidade **"Listar Despesas" (Opções 3 e 4)** agora inclui um submenu para "Editar" e "Excluir" despesas.
-   Funcionalidade **"Gerenciar Usuários" (Opção 6)** transformada em um submenu completo (Cadastrar, Listar, Editar, Excluir) com trava de segurança.
-   Implementada a tela de Login inicial.

## v1.0.0-mvp - (09/11/2025) (Entrega B4T01.3)

-   **MVP PRONTO**: O Produto Mínimo Viável foi concluído.
-   Implementadas as classes de Modelo: `TipoDespesa` e `Despesa`.
-   Implementadas as classes de Repositório: `TipoDespesaRepository` e `DespesaRepository`, responsáveis pela leitura e escrita nos arquivos `tipos_despesa.txt` e `despesas.txt`.
-   `Main.java` atualizado:
    -   Funcionalidade 5 ("Gerenciar Tipos") agora permite CRIAR e LISTAR tipos.
    -   Funcionalidade 1 ("Entrar Despesa") agora salva uma despesa associada a um tipo.
    -   Funcionalidade 3 ("Listar Despesas em Aberto") agora lê do arquivo e exibe as despesas salvas.
-   As funcionalidades (2, 4, 6) permanecem como *stubs* para a V2.
-   Adicionada tag `v1.0.0-mvp` ao repositório Git.

## v0.0.2 - (08/11/2025) (Entrega B4T01.2)

-   Definição da Separação de Prioridades (MoSCoW) no `README.md`.
-   Planejamento e projeto do MVP (Minimum Viable Product) no `README.md`.
-   Criação da Prova de Conceito (POC) para **Gerenciamento de Usuários**, **Criptografia de Senha (Base64)** e **Persistência em Arquivo de Texto** (`src/PocUsuarios.java`).

## v0.0.1 - 02/11/2025 (Entrega B4T01.1)

-   Repositório Git criado e compartilhado com `prof.roldjunior@uninga.edu.br`.
-   Repositório clonado localmente.
-   Criação da classe `Main.java` com o menu principal interativo.
-   Adicionado `println` de "em construção" para cada funcionalidade do menu, conforme solicitado.
-   Criação do `README.md` principal (raiz) com a estratégia de construção e documentação/abstração das classes, atributos e métodos.
-   Criação deste arquivo de CHANGELOG (`docs/README.md`).