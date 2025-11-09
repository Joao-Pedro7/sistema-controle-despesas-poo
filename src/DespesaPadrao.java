// 1. HERDA (extends) da classe abstrata Despesa
public class DespesaPadrao extends Despesa {

    // --- SOBRECARGA DE CONSTRUTOR ---
    // O professor pediu "Sobrecarga de Construtor"

    // Construtor 1 (Completo, usado pelo Repositório)
    public DespesaPadrao(int id, String descricao, double valor, String dataVencimento, TipoDespesa tipo) {
        // 'super()' chama o construtor da classe PAI (Despesa)
        super(id, descricao, valor, dataVencimento, tipo);
    }
    
    // Construtor 2 (Sobrecarga)
    // Um construtor mais simples, talvez para criar uma despesa sem data
    public DespesaPadrao(int id, String descricao, double valor, TipoDespesa tipo) {
        // Chama o construtor principal, passando "null" para a data
        super(id, descricao, valor, null, tipo);
    }
    
    // --- FIM DA SOBRECARGA ---


    // --- IMPLEMENTAÇÃO DO MÉTODO ABSTRATO ---
    // A classe PAI (Despesa) nos obrigou a implementar este método
    @Override
    public String paraStringCsv() {
        // Formato para salvar no arquivo:
        // id;descricao;valor;dataVenc;id_do_tipo;estaPaga;dataPagamento;valorPago

        String csv = id + ";" +
                     descricao + ";" +
                     valor + ";" +
                     dataVencimento + ";" +
                     tipo.getId() + ";" +
                     estaPaga; // Salva "true" ou "false"

        if (estaPaga && pagamento != null) {
            // Se está paga, adiciona os dados do pagamento
            csv += ";" + pagamento.paraStringCsv(); // "data;valor"
        } else {
            // Adiciona campos vazios para manter a estrutura do CSV
            csv += ";null;0.0";
        }
        
        return csv;
    }
    
    // (Não precisamos do método toString() aqui, 
    // pois ele vai usar o toString() da classe PAI - Herança!)
}