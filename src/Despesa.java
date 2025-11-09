// 1. Agora é ABSTRACT e 2. IMPLEMENTS Pagavel
public abstract class Despesa implements Pagavel {
    
    // 'protected' permite que as classes filhas (DespesaPadrao) acessem
    protected int id;
    protected String descricao;
    protected double valor;
    protected String dataVencimento;
    protected TipoDespesa tipo;
    
    protected Pagamento pagamento; // Novo! Guarda os dados do pagamento
    protected boolean estaPaga;    // Novo! Controla o status

    // Construtor principal
    public Despesa(int id, String descricao, double valor, String dataVencimento, TipoDespesa tipo) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.estaPaga = false; // Começa como não paga
        this.pagamento = null;
    }

    // --- Métodos da Interface Pagavel ---
    
    @Override // Sobrescrita
    public void pagar(Pagamento pagamento) {
        this.pagamento = pagamento;
        this.estaPaga = true;
    }

    @Override // Sobrescrita
    public boolean estaPaga() {
        return this.estaPaga;
    }

    // --- Fim da Interface ---

    // Getters públicos (para o Repositório usar)
    public int getId() { return id; }
    public TipoDespesa getTipo() { return tipo; }
    public Pagamento getPagamento() { return pagamento; }
    
    // --- MÉTODOS NOVOS (SETTERS) ---
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    // --- FIM DOS MÉTODOS NOVOS ---

    // --- Método Abstrato ---
    // Uma classe abstrata precisa de (pelo menos) um método abstrato.
    // Este método será implementado pela classe filha (DespesaPadrao).
    // Ele vai formatar a string para salvar no arquivo .txt
    public abstract String paraStringCsv();


    // Método de exibição (Polimorfismo)
    // A classe filha pode sobrescrever (override) isso se quiser
    @Override
    public String toString() {
        String status = estaPaga ? "PAGO" : "PENDENTE";
        String infoPagamento = estaPaga ? " | " + pagamento.toString() : "";
        
        return "ID: " + id + " | Status: " + status +
               " | Descrição: " + descricao + 
               " | Valor: R$ " + valor + 
               " | Vencimento: " + dataVencimento + 
               " | Tipo: " + tipo.getNome() +
               infoPagamento;
    }
}