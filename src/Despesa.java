// Nota: Ainda não é abstrata, é a classe principal de despesa
// Vamos manter simples para o MVP.
public class Despesa {
    private int id;
    private String descricao;
    private double valor;
    private String dataVencimento;
    private TipoDespesa tipo; // Armazena o OBJETO TipoDespesa

    public Despesa(int id, String descricao, double valor, String dataVencimento, TipoDespesa tipo) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
    }

    // Usado para salvar no arquivo de texto no formato "id;descricao;valor;dataVenc;id_do_tipo"
    public String paraStringCsv() {
        return id + ";" + descricao + ";" + valor + ";" + dataVencimento + ";" + tipo.getId();
    }

    // Usado para exibir na tela para o usuário
    @Override
    public String toString() {
        return "ID: " + id + 
               " | Descrição: " + descricao + 
               " | Valor: R$ " + valor + 
               " | Vencimento: " + dataVencimento + 
               " | Tipo: " + tipo.getNome();
    }
}