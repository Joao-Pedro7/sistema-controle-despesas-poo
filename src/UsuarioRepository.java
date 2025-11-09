import java.io.*;
import java.util.ArrayList;
import java.util.Base64; // Importamos o Base64 da POC
import java.util.List;

public class UsuarioRepository {

    private static final String ARQUIVO = "usuarios.txt";
    private List<Usuario> usuarios;
    private int proximoId;

    public UsuarioRepository() {
        this.usuarios = new ArrayList<>();
        this.proximoId = 1;
        carregarDoArquivo();
    }

    // --- Métodos de Criptografia (da POC) ---
    public String criptografar(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    // --- Métodos de Arquivo ---
    private void carregarDoArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String login = partes[1];
                String senhaCriptografada = partes[2];
                
                this.usuarios.add(new Usuario(id, login, senhaCriptografada));
                
                if (id >= proximoId) {
                    proximoId = id + 1;
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo não existe ainda, tudo bem.
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    private void salvarNoArquivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO, false))) {
            for (Usuario usuario : usuarios) {
                pw.println(usuario.paraStringCsv());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    // --- Métodos de Negócio ---

    public boolean criar(String login, String senha) {
        if (buscarPorLogin(login) != null) {
            System.out.println("Erro: Login '" + login + "' já existe.");
            return false;
        }
        
        String senhaCriptografada = criptografar(senha);
        Usuario novoUsuario = new Usuario(proximoId, login, senhaCriptografada);
        this.usuarios.add(novoUsuario);
        this.proximoId++;
        salvarNoArquivo();
        System.out.println("Usuário '" + login + "' cadastrado com sucesso!");
        return true;
    }

    public List<Usuario> listarTodos() {
        return this.usuarios;
    }

    public Usuario buscarPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public Usuario buscarPorLogin(String login) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equalsIgnoreCase(login)) {
                return usuario;
            }
        }
        return null; // Não encontrado
    }

    // Retorna o OBJETO Usuario se for válido, ou null se for inválido
    public Usuario validarLogin(String login, String senha) {
        Usuario usuario = buscarPorLogin(login);
        
        if (usuario != null) {
            String senhaDigitadaCriptografada = criptografar(senha);
            if (senhaDigitadaCriptografada.equals(usuario.getSenhaCriptografada())) {
                return usuario; // Sucesso! Retorna o usuário.
            }
        }
        
        return null; // Usuário não encontrado ou senha errada
    }

    // --- MÉTODOS NOVOS (EDITAR E EXCLUIR) ---

    public boolean editar(int id, String novoLogin, String novaSenha) {
        Usuario usuarioParaEditar = buscarPorId(id);
        
        if (usuarioParaEditar == null) {
            System.out.println("Erro: Usuário não encontrado.");
            return false;
        }

        // Verifica se o NOVO login já está em uso por OUTRO usuário
        Usuario loginExistente = buscarPorLogin(novoLogin);
        if (loginExistente != null && loginExistente.getId() != id) {
            System.out.println("Erro: O login '" + novoLogin + "' já está em uso por outro usuário.");
            return false;
        }
        
        usuarioParaEditar.setLogin(novoLogin);
        
        // Só atualiza a senha se o usuário digitar uma nova
        if (novaSenha != null && !novaSenha.isEmpty()) {
            usuarioParaEditar.setSenhaCriptografada(criptografar(novaSenha));
        }
        
        salvarNoArquivo();
        System.out.println("Usuário editado com sucesso!");
        return true;
    }

    public boolean excluir(int id) {
        Usuario usuarioParaExcluir = buscarPorId(id);
        
        if (usuarioParaExcluir == null) {
            System.out.println("Erro: Usuário não encontrado.");
            return false;
        }
        
        // TODO: Adicionar lógica de segurança (não excluir usuário logado)
        
        this.usuarios.remove(usuarioParaExcluir);
        salvarNoArquivo();
        System.out.println("Usuário excluído com sucesso!");
        return true;
    }
}