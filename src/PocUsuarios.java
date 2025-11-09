import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Scanner;

public class PocUsuarios {

    // O nome do arquivo que vamos usar
    private static final String ARQUIVO_USUARIOS = "usuarios.txt";

    public static void main(String[] args) {
        System.out.println("--- Prova de Conceito (POC) - Gerenciar Usuários ---");

        try {
            Scanner scanner = new Scanner(System.in);
            
            // 1. CADASTRAR USUÁRIO
            System.out.print("Digite um login para cadastrar: ");
            String login = scanner.nextLine();
            
            System.out.print("Digite uma senha para cadastrar: ");
            String senha = scanner.nextLine();

            // 2. CRIPTOGRAFAR A SENHA
            // Usamos o Base64, que já vem no Java, para "embaralhar" a senha.
            String senhaCriptografada = Base64.getEncoder().encodeToString(senha.getBytes());

            System.out.println("\nSalvando usuário...");
            System.out.println("Login: " + login);
            System.out.println("Senha Original: " + senha);
            System.out.println("Senha Criptografada: " + senhaCriptografada);

            // 3. SALVAR EM ARQUIVO DE TEXTO
            // Usamos "true" no FileWriter para "append" (adicionar ao final), 
            // sem apagar o que já existe.
            FileWriter fileWriter = new FileWriter(ARQUIVO_USUARIOS, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            
            // Salvamos no formato "login;senha"
            printWriter.println(login + ";" + senhaCriptografada);
            
            printWriter.close();
            fileWriter.close();

            System.out.println("Usuário salvo com sucesso em " + ARQUIVO_USUARIOS);
            
            // 4. LER DO ARQUIVO DE TEXTO
            System.out.println("\n--- Lendo todos os usuários salvos ---");
            FileReader fileReader = new FileReader(ARQUIVO_USUARIOS);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                // "Quebramos" a linha no caractere ";"
                String[] partes = linha.split(";");
                String loginSalvo = partes[0];
                String senhaSalva = partes[1];
                
                // 5. DESCRIPTOGRAFAR A SENHA
                // Para ler a senha, fazemos o processo inverso (decode)
                byte[] bytesSenha = Base64.getDecoder().decode(senhaSalva);
                String senhaOriginal = new String(bytesSenha);

                System.out.println("Usuário Lido: " + loginSalvo + " | Senha Original: " + senhaOriginal);
            }
            
            bufferedReader.close();
            fileReader.close();
            scanner.close();

            System.out.println("\n--- POC CONCLUÍDA COM SUCESSO! ---");

        } catch (Exception e) {
            System.out.println("Ocorreu um erro na POC: " + e.getMessage());
            e.printStackTrace();
        }
    }
}