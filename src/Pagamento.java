public class Pagamento {
    private String dataPagamento;
    private double valorPago;

    public Pagamento(String dataPagamento, double valorPago) {
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public double getValorPago() {
        return valorPago;
    }

    // Usado para salvar no arquivo
    public String paraStringCsv() {
        return dataPagamento + ";" + valorPago;
    }

    // Usado para exibir na tela
    @Override
    public String toString() {
        return "Pago em: " + dataPagamento + " | Valor: R$ " + valorPago;
    }
}
