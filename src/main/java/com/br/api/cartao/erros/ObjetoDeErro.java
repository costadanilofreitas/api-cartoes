package com.br.api.cartao.erros;

public class ObjetoDeErro {

    private String mensagem;
    private String campo;
    private Object parametro;

    public ObjetoDeErro(String mensagem, String campo, Object parametro) {
        this.mensagem = mensagem;
        this.campo = campo;
        this.parametro = parametro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public Object getParametro() {
        return parametro;
    }

    public void setParametro(Object parametro) {
        this.parametro = parametro;
    }
}
