import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDespesaRepository {

    private static final String ARQUIVO = "tipos_despesa.txt";
    private List<TipoDespesa> tipos; // Um "cache" em memória
    private int proximoId;

    public TipoDespesaRepository() {
        this.tipos = new ArrayList<>();
        this.proximoId = 1;
        carregarDoArquivo(); // Carrega os dados assim que o repositório é criado
    }

    private void carregarDoArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                this.tipos.add(new TipoDespesa(id, nome));
                
                // Atualiza o proximoId para ser sempre 1 a mais que o maior ID encontrado
                if (id >= proximoId) {
                    proximoId = id + 1;
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo não existe ainda, o que é normal na primeira vez.
        } catch (IOException e) {
            System.out.println("Erro ao carregar tipos de despesa: " + e.getMessage());
        }
    }

    private void salvarNoArquivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO, false))) {
            for (TipoDespesa tipo : tipos) {
                pw.println(tipo.paraStringCsv());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar tipos de despesa: " + e.getMessage());
        }
    }

    public void criar(String nome) {
        TipoDespesa novoTipo = new TipoDespesa(proximoId, nome);
        this.tipos.add(novoTipo);
        this.proximoId++;
        salvarNoArquivo(); // Salva a mudança no arquivo
        System.out.println("Tipo '" + nome + "' criado com sucesso!");
    }

    public List<TipoDespesa> listarTodos() {
        return this.tipos;
    }

    public TipoDespesa buscarPorId(int id) {
        for (TipoDespesa tipo : tipos) {
            if (tipo.getId() == id) {
                return tipo;
            }
        }
        return null; // Não encontrado
    }

    // --- MÉTODOS NOVOS ADICIONADOS ---

    public boolean editar(int id, String novoNome) {
        TipoDespesa tipoParaEditar = buscarPorId(id);
        
        if (tipoParaEditar != null) {
            tipoParaEditar.setNome(novoNome); // Usa o novo método setNome
            salvarNoArquivo(); // Salva a mudança no arquivo
            System.out.println("Tipo editado com sucesso!");
            return true;
        }
        
        System.out.println("Erro: Tipo não encontrado.");
        return false;
    }

    public boolean excluir(int id) {
        TipoDespesa tipoParaExcluir = buscarPorId(id);
        
        if (tipoParaExcluir != null) {
            this.tipos.remove(tipoParaExcluir); // Remove da lista em memória
            salvarNoArquivo(); // Salva a mudança (agora sem o item)
            System.out.println("Tipo excluído com sucesso!");
            return true;
        }
        
        System.out.println("Erro: Tipo não encontrado.");
        return false;
    }
}