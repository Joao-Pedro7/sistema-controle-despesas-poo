public class TipoDespesa {
    private int id;
    private String nome;

    public TipoDespesa(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // Usado para salvar no arquivo de texto no formato "id;nome"
    public String paraStringCsv() {
        return this.id + ";" + this.nome;
    }

    // Usado para exibir na tela para o usuário
    @Override
    public String toString() {
        return this.id + " - " + this.nome;
    }
}