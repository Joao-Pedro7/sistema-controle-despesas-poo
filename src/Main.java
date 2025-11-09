import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Scanner global
    private static Scanner scanner = new Scanner(System.in);
    
    // Repositórios globais
    private static TipoDespesaRepository tipoRepo;
    private static DespesaRepository despesaRepo;
    private static UsuarioRepository usuarioRepo; 

    public static void main(String[] args) {
        // Inicializa os repositórios
        tipoRepo = new TipoDespesaRepository();
        usuarioRepo = new UsuarioRepository();
        despesaRepo = new DespesaRepository(tipoRepo); // Depende do tipoRepo

        iniciarSistema();
        
        scanner.close();
    }

    // --- Métodos de Login/Usuário (Atualizados) ---
    
    private static void iniciarSistema() {
        while (true) {
            System.out.println("\n--- Bem-vindo ao Sistema de Despesas ---");
            System.out.println("1. Fazer Login");
            System.out.println("2. Cadastrar Novo Usuário");
            System.out.println("3. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                switch (opcao) {
                    case 1:
                        // O login agora retorna o Usuário logado (ou null)
                        Usuario usuarioLogado = fazerLogin();
                        if (usuarioLogado != null) {
                            System.out.println("Login bem-sucedido! Bem-vindo, " + usuarioLogado.getLogin());
                            executarMenuPrincipal(usuarioLogado); // Passa o usuário para o menu
                        } else {
                            System.out.println("\nLogin ou senha inválidos.");
                        }
                        break;
                    case 2:
                        cadastrarNovoUsuario();
                        break;
                    case 3:
                        System.out.println("Saindo do sistema... Até logo!");
                        return; 
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nErro: Por favor, digite um número.");
                scanner.nextLine(); 
            }
        }
    }

    // Agora retorna o OBJETO Usuario em vez de um boolean
    private static Usuario fazerLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        
        return usuarioRepo.validarLogin(login, senha); // Retorna o usuário ou null
    }

    private static void cadastrarNovoUsuario() {
        System.out.println("\n--- Novo Cadastro ---");
        System.out.print("Digite o novo login: ");
        String login = scanner.nextLine();
        System.out.print("Digite a nova senha: ");
        String senha = scanner.nextLine();
        
        usuarioRepo.criar(login, senha);
    }
    
    // *** ESTE ERA O MÉTODO QUE FALTAVA ***
    private static void listarUsuarios() {
        System.out.println("\n--- Usuários Cadastrados ---");
        List<Usuario> usuarios = usuarioRepo.listarTodos();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println(usuario.toString());
            }
        }
    }
    
    // --- Fim dos Métodos de Login/Usuário ---


    // --- Menu Principal da Aplicação ---

    // Agora recebe o usuário que está logado
    private static void executarMenuPrincipal(Usuario usuarioLogado) {
        int opcao = 0;
        while (opcao != 7) {
            exibirMenuApp();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcao) {
                    case 1:
                        entrarDespesa();
                        break;
                    case 2:
                        anotarPagamento();
                        break;
                    case 3:
                        listarDespesasEmAberto();
                        break;
                    case 4:
                        listarDespesasPagas();
                        break;
                    case 5:
                        gerenciarTiposDespesa();
                        break;
                    case 6:
                        // Chama o novo menu completo, passando o usuário logado
                        gerenciarUsuarios(usuarioLogado);
                        break;
                    case 7:
                        System.out.println("\nFazendo logout...");
                        break; 
                    default:
                        System.out.println("\nOpção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nErro: Por favor, digite um número válido.");
                scanner.nextLine(); 
            }
        }
    }
    
    private static void exibirMenuApp() {
        System.out.println("\n--- Sistema de Controle de Despesas (Logado) ---");
        System.out.println("1. Entrar Despesa");
        System.out.println("2. Anotar Pagamento");
        System.out.println("3. Listar Despesas em Aberto (com Submenu)");
        System.out.println("4. Listar Despesas Pagas (com Submenu)");
        System.out.println("5. Gerenciar Tipos de Despesa (COMPLETO)");
        System.out.println("6. Gerenciar Usuários (COMPLETO)");
        System.out.println("7. Fazer Logout (Voltar ao menu inicial)");
        System.out.print("Escolha uma opção: ");
    }


    // --- Métodos de Despesa (Não mudam) ---

    private static void entrarDespesa() {
        System.out.println("\n--- Entrar Nova Despesa ---");
        System.out.print("Descrição: ");
        String desc = scanner.nextLine();
        System.out.print("Valor (ex: 50.75): ");
        double valor = 0;
        try {
            valor = scanner.nextDouble();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Valor inválido. Usando 0.0");
            scanner.nextLine(); 
        }
        System.out.print("Data de Vencimento (ex: 15/12/2025): ");
        String dataVenc = scanner.nextLine();
        System.out.println("Selecione o Tipo de Despesa:");
        List<TipoDespesa> tipos = tipoRepo.listarTodos();
        if (tipos.isEmpty()) {
            System.out.println("Erro: Nenhum tipo de despesa cadastrado.");
            return;
        }
        for (TipoDespesa tipo : tipos) {
            System.out.println(tipo.toString());
        }
        System.out.print("Digite o ID do tipo: ");
        int idTipo = -1;
        try {
            idTipo = scanner.nextInt();
            scanner.nextLine(); 
        } catch (InputMismatchException e) {
            System.out.println("ID de tipo inválido.");
            scanner.nextLine();
            return;
        }
        TipoDespesa tipoSelecionado = tipoRepo.buscarPorId(idTipo);
        if (tipoSelecionado == null) {
            System.out.println("Erro: ID do tipo não encontrado.");
            return;
        }
        despesaRepo.criar(desc, valor, dataVenc, tipoSelecionado);
    }
    
    private static void anotarPagamento() {
        System.out.println("\n--- Anotar Pagamento ---");
        System.out.println("Abaixo estão as despesas pendentes:");
        List<Despesa> pendentes = despesaRepo.listar(false, true); 
        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma despesa pendente encontrada.");
            return;
        }
        for (Despesa d : pendentes) {
            System.out.println(d.toString());
        }
        System.out.print("\nDigite o ID da despesa que deseja pagar: ");
        int idDespesa = -1;
        try {
            idDespesa = scanner.nextInt();
            scanner.nextLine(); 
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
            return;
        }
        Despesa despesaParaPagar = despesaRepo.buscarPorId(idDespesa);
        if (despesaParaPagar == null) {
            System.out.println("Erro: Nenhuma despesa encontrada com este ID.");
            return;
        }
        if (despesaParaPagar.estaPaga()) {
            System.out.println("Erro: Esta despesa já foi paga.");
            return;
        }
        System.out.print("Digite a data do pagamento (ex: 10/11/2025): ");
        String dataPag = scanner.nextLine();
        System.out.print("Digite o valor pago: ");
        double valorPag = scanner.nextDouble();
        scanner.nextLine();
        Pagamento novoPagamento = new Pagamento(dataPag, valorPag);
        despesaParaPagar.pagar(novoPagamento);
        despesaRepo.salvarNoArquivo();
        System.out.println("Pagamento da despesa " + idDespesa + " anotado com sucesso!");
    }

    private static void listarDespesasEmAberto() {
        System.out.println("\n--- Despesas Em Aberto ---");
        List<Despesa> despesas = despesaRepo.listar(false, true); 
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa em aberto encontrada.");
            return; 
        }
        for (Despesa despesa : despesas) {
            System.out.println(despesa.toString());
            System.out.println("----------------------------------------");
        }
        submenuDespesas();
    }
    
    private static void listarDespesasPagas() {
        System.out.println("\n--- Despesas Pagas ---");
        List<Despesa> despesas = despesaRepo.listar(true, false);
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa paga encontrada.");
            return; 
        }
        for (Despesa despesa : despesas) {
            System.out.println(despesa.toString());
            System.out.println("----------------------------------------");
        }
        submenuDespesas();
    }
    
    private static void submenuDespesas() {
        int opcao = 0;
        while (opcao != 3) {
            System.out.println("\n--- Submenu Despesas ---");
            System.out.println("1. Editar Despesa");
            System.out.println("2. Excluir Despesa");
            System.out.println("3. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); 
                switch (opcao) {
                    case 1:
                        editarDespesa();
                        break;
                    case 2:
                        excluirDespesa();
                        break;
                    case 3:
                        System.out.println("Voltando ao menu principal...");
                        return; 
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nErro: Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    private static void editarDespesa() {
        System.out.println("\n--- Editar Despesa (Modo Simples) ---");
        System.out.print("Digite o ID da despesa que deseja editar (veja lista acima): ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        Despesa despesa = despesaRepo.buscarPorId(id);
        if (despesa == null) {
            System.out.println("Erro: Despesa não encontrada.");
            return;
        }
        System.out.println("AVISO: Você deve preencher todos os campos.");
        System.out.print("Digite a NOVA Descrição: ");
        String novaDesc = scanner.nextLine();
        System.out.print("Digite o NOVO Valor: ");
        double novoValor = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Digite a NOVA Data de Vencimento: ");
        String novaData = scanner.nextLine();
        despesaRepo.editar(id, novaDesc, novoValor, novaData);
    }

    private static void excluirDespesa() {
        System.out.println("\n--- Excluir Despesa ---");
        System.out.print("Digite o ID da despesa que deseja excluir (veja lista acima): ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        despesaRepo.excluir(id);
    }
    

    // --- Métodos de Tipo de Despesa (Completos) ---
    private static void gerenciarTiposDespesa() {
        int opcao = 0;
        while (opcao != 5) { 
            System.out.println("\n--- Gerenciar Tipos de Despesa ---");
            System.out.println("1. Criar novo tipo");
            System.out.println("2. Listar tipos existentes");
            System.out.println("3. Editar tipo");
            System.out.println("4. Excluir tipo");
            System.out.println("5. Voltar ao Menu Principal"); 
            System.out.print("Escolha uma opção: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); 
                switch (opcao) {
                    case 1:
                        System.out.print("Digite o nome do novo tipo (ex: Alimentação): ");
                        String nome = scanner.nextLine();
                        tipoRepo.criar(nome);
                        break;
                    case 2:
                        listarTipos();
                        break;
                    case 3:
                        editarTipoDespesa();
                        break;
                    case 4:
                        excluirTipoDespesa();
                        break;
                    case 5:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nErro: Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }
    
    private static void listarTipos() {
        System.out.println("\n--- Tipos de Despesa Cadastrados ---");
        List<TipoDespesa> tipos = tipoRepo.listarTodos();
        if (tipos.isEmpty()) {
            System.out.println("Nenhum tipo cadastrado.");
        } else {
            for (TipoDespesa tipo : tipos) {
                System.out.println(tipo.toString());
            }
        }
    }

    private static void editarTipoDespesa() {
        System.out.println("\n--- Editar Tipo de Despesa ---");
        listarTipos(); // Mostra a lista
        System.out.print("Digite o ID do tipo que deseja editar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Digite o NOVO nome para este tipo: ");
        String novoNome = scanner.nextLine();
        tipoRepo.editar(id, novoNome);
    }

    private static void excluirTipoDespesa() {
        System.out.println("\n--- Excluir Tipo de Despesa ---");
        listarTipos(); // Mostra a lista
        System.out.print("Digite o ID do tipo que deseja excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        
        boolean tipoEmUso = false;
        List<Despesa> todasDespesas = despesaRepo.listar(true, true);
        for (Despesa d : todasDespesas) {
            if (d.getTipo().getId() == id) {
                tipoEmUso = true;
                break; 
            }
        }
        if (tipoEmUso) {
            System.out.println("Erro: Este tipo de despesa está sendo usado por uma despesa e não pode ser excluído.");
        } else {
            tipoRepo.excluir(id);
        }
    }


    // --- MÉTODO NOVO: GERENCIAR USUÁRIOS (COMPLETO) ---
    private static void gerenciarUsuarios(Usuario usuarioLogado) {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("\n--- Gerenciar Usuários ---");
            System.out.println("1. Cadastrar novo usuário");
            System.out.println("2. Listar usuários");
            System.out.println("3. Editar usuário");
            System.out.println("4. Excluir usuário");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa buffer

                switch (opcao) {
                    case 1:
                        cadastrarNovoUsuario(); // Reusa o método do menu de login
                        break;
                    case 2:
                        listarUsuarios(); // Reusa o método que já tínhamos
                        break;
                    case 3:
                        editarUsuario();
                        break;
                    case 4:
                        // Passa o ID do usuário logado para a lógica de exclusão
                        excluirUsuario(usuarioLogado.getId()); 
                        break;
                    case 5:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nErro: Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    private static void editarUsuario() {
        System.out.println("\n--- Editar Usuário ---");
        listarUsuarios();
        System.out.print("Digite o ID do usuário que deseja editar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        
        Usuario usuario = usuarioRepo.buscarPorId(id);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.print("Digite o NOVO login (Atual: " + usuario.getLogin() + "): ");
        String novoLogin = scanner.nextLine();
        
        System.out.print("Digite a NOVA senha (Deixe em branco para não alterar): ");
        String novaSenha = scanner.nextLine();
        
        usuarioRepo.editar(id, novoLogin, novaSenha);
    }

    private static void excluirUsuario(int idUsuarioLogado) {
        System.out.println("\n--- Excluir Usuário ---");
        listarUsuarios();
        System.out.print("Digite o ID do usuário que deseja excluir: ");
        int idParaExcluir = scanner.nextInt();
        scanner.nextLine(); 
        
        // LÓGICA DE SEGURANÇA
        if (idParaExcluir == idUsuarioLogado) {
            System.out.println("Erro: Você não pode excluir a si mesmo!");
            return;
        }
        
        // (Opcional: verificar se é o único usuário, etc)
        
        usuarioRepo.excluir(idParaExcluir);
    }
}