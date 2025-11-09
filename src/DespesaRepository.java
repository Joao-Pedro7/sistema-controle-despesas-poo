import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DespesaRepository {

    private static final String ARQUIVO = "despesas.txt";
    // Agora a lista armazena o TIPO ABSTRATO 'Despesa'
    // Mas na prática, vamos colocar 'DespesaPadrao' nela (Polimorfismo)
    private List<Despesa> despesas; 
    private int proximoId;
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
                
                // Pega os dados básicos
                int id = Integer.parseInt(partes[0]);
                String desc = partes[1];
                double valor = Double.parseDouble(partes[2]);
                String dataVenc = partes[3];
                int idTipo = Integer.parseInt(partes[4]);
                boolean estaPaga = Boolean.parseBoolean(partes[5]);
                
                // Busca o objeto TipoDespesa
                TipoDespesa tipo = tipoRepository.buscarPorId(idTipo);
                
                if (tipo != null) {
                    // Cria o objeto concreto DespesaPadrao
                    Despesa despesa = new DespesaPadrao(id, desc, valor, dataVenc, tipo);
                    
                    // Se ela estava paga no arquivo, recria o objeto Pagamento
                    if (estaPaga) {
                        String dataPag = partes[6];
                        double valorPag = Double.parseDouble(partes[7]);
                        Pagamento pagamento = new Pagamento(dataPag, valorPag);
                        despesa.pagar(pagamento); // Usa o método da interface para marcar como paga
                    }
                    
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
        } catch (Exception e) {
            System.out.println("Erro grave ao ler arquivo de despesas (pode estar corrompido): " + e.getMessage());
        }
    }

    // Este método não muda, pois a despesa sabe como se formatar (Polimorfismo)
    public void salvarNoArquivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO, false))) {
            for (Despesa despesa : despesas) {
                // Chama o método paraStringCsv() da classe filha (DespesaPadrao)
                pw.println(despesa.paraStringCsv()); 
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar despesas: " + e.getMessage());
        }
    }

    public void criar(String desc, double valor, String dataVenc, TipoDespesa tipo) {
        // Cria a instância CONCRETA
        Despesa novaDespesa = new DespesaPadrao(proximoId, desc, valor, dataVenc, tipo);
        this.despesas.add(novaDespesa);
        this.proximoId++;
        salvarNoArquivo();
        System.out.println("Despesa '" + desc + "' criada com sucesso!");
    }
    
    public Despesa buscarPorId(int id) {
        for (Despesa d : despesas) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    // Método de listar (agora com filtros)
    public List<Despesa> listar(boolean filtrarPagas, boolean filtrarPendentes) {
        List<Despesa> resultado = new ArrayList<>();
        for (Despesa d : despesas) {
            if (filtrarPagas && d.estaPaga()) {
                resultado.add(d);
            }
            if (filtrarPendentes && !d.estaPaga()) {
                resultado.add(d);
            }
        }
        return resultado;
    }

    // --- MÉTODOS NOVOS ADICIONADOS ---

    public boolean editar(int id, String novaDesc, double novoValor, String novaData) {
        Despesa despesaParaEditar = buscarPorId(id);
        
        if (despesaParaEditar != null) {
            despesaParaEditar.setDescricao(novaDesc);
            despesaParaEditar.setValor(novoValor);
            despesaParaEditar.setDataVencimento(novaData);
            
            salvarNoArquivo(); // Salva a mudança no arquivo
            System.out.println("Despesa ID " + id + " editada com sucesso!");
            return true;
        }
        
        System.out.println("Erro: Despesa não encontrada.");
        return false;
    }

    public boolean excluir(int id) {
        Despesa despesaParaExcluir = buscarPorId(id);
        
        if (despesaParaExcluir != null) {
            this.despesas.remove(despesaParaExcluir); // Remove da lista
            salvarNoArquivo(); // Salva a lista atualizada
            System.out.println("Despesa ID " + id + " excluída com sucesso!");
            return true;
        }
        
        System.out.println("Erro: Despesa não encontrada.");
        return false;
    }
}