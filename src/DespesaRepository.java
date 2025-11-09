import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DespesaRepository {

    private static final String ARQUIVO = "despesas.txt";
    private List<Despesa> despesas;
    private int proximoId;
    
    // Dependência: Precisa do repositório de tipos para "conectar" a despesa ao seu tipo
    private TipoDespesaRepository tipoRepository;

    public DespesaRepository(TipoDespesaRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
        this.despesas = new ArrayList<>();
        this.proximoId = 1;
        carregarDoArquivo();
    }

    private void carregarDoArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String desc = partes[1];
                double valor = Double.parseDouble(partes[2]);
                String dataVenc = partes[3];
                int idTipo = Integer.parseInt(partes[4]);

                // Busca o objeto TipoDespesa correspondente ao ID
                TipoDespesa tipo = tipoRepository.buscarPorId(idTipo);
                
                if (tipo != null) {
                    Despesa despesa = new Despesa(id, desc, valor, dataVenc, tipo);
                    this.despesas.add(despesa);
                    if (id >= proximoId) {
                        proximoId = id + 1;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo não existe ainda, normal.
        } catch (IOException e) {
            System.out.println("Erro ao carregar despesas: " + e.getMessage());
        }
    }

    private void salvarNoArquivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO, false))) {
            for (Despesa despesa : despesas) {
                pw.println(despesa.paraStringCsv());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar despesas: " + e.getMessage());
        }
    }

    public void criar(String desc, double valor, String dataVenc, TipoDespesa tipo) {
        Despesa novaDespesa = new Despesa(proximoId, desc, valor, dataVenc, tipo);
        this.despesas.add(novaDespesa);
        this.proximoId++;
        salvarNoArquivo();
        System.out.println("Despesa '" + desc + "' criada com sucesso!");
    }

    // No MVP, "em aberto" são todas as despesas, pois não implementamos o pagamento.
    public List<Despesa> listarEmAberto() {
        return this.despesas;
    }
}