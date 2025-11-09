import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        // Loop principal do menu
        while (opcao != 7) {
            System.out.println("\n--- Sistema de Controle de Despesas ---");
            System.out.println("1. Entrar Despesa");
            System.out.println("2. Anotar Pagamento");
            System.out.println("3. Listar Despesas em Aberto");
            System.out.println("4. Listar Despesas Pagas");
            System.out.println("5. Gerenciar Tipos de Despesa");
            System.out.println("6. Gerenciar Usuários");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");

            // Tenta ler um inteiro. Se falhar, avisa o usuário.
            try {
                opcao = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Erro: Por favor, digite um número válido.");
                scanner.next(); // Limpa o buffer do scanner
                continue; // Volta ao início do loop
            }

            // Trata a escolha do usuário
            switch (opcao) {
                case 1:
                    System.out.println("\n>> Funcionalidade 'Entrar Despesa' em construção...");
                    break;
                case 2:
                    System.out.println("\n>> Funcionalidade 'Anotar Pagamento' em construção...");
                    break;
                case 3:
                    System.out.println("\n>> Funcionalidade 'Listar Despesas em Aberto' em construção...");
                    break;
                case 4:
                    System.out.println("\n>> Funcionalidade 'Listar Despesas Pagas' em construção...");
                    break;
                case 5:
                    System.out.println("\n>> Funcionalidade 'Gerenciar Tipos de Despesa' em construção...");
                    break;
                case 6:
                    System.out.println("\n>> Funcionalidade 'Gerenciar Usuários' em construção...");
                    break;
                case 7:
                    System.out.println("\nSaindo do sistema... Até logo!");
                    break;
                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
            }
        }
        
        // Fecha o scanner ao sair do loop
        scanner.close();
    }
}