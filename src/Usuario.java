public class Usuario {
    private int id;
    private String login;
    private String senhaCriptografada; // Vamos guardar a senha já em Base64

    public Usuario(int id, String login, String senhaCriptografada) {
        this.id = id;
        this.login = login;
        this.senhaCriptografada = senhaCriptografada;
    }

    // Getters são necessários para o repositório
    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenhaCriptografada() {
        return senhaCriptografada;
    }

    // --- MÉTODOS NOVOS (SETTERS) ---
    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenhaCriptografada(String senhaCriptografada) {
        this.senhaCriptografada = senhaCriptografada;
    }
    // --- FIM DOS MÉTODOS NOVOS ---

    // Usado para salvar no arquivo no formato "id;login;senha"
    public String paraStringCsv() {
        return this.id + ";" + this.login + ";" + this.senhaCriptografada;
    }

    // Usado para listar na tela para o usuário (Opção 6)
    @Override
    public String toString() {
        return "ID: " + id + " | Login: " + login;
    }
}