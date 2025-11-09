public interface Pagavel {
    
    // Método para marcar a despesa como paga
    void pagar(Pagamento pagamento);
    
    // Método para verificar se está paga
    boolean estaPaga();
}
