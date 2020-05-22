package com.br.api.cartao.erros;

import java.util.List;

public class RespostaDeErro {

    private String tipoDeErro;
    private int codigo;
    private String status;
    private String nomeDoObjeto;
    private List<ObjetoDeErro> objetoDeErros;

    public RespostaDeErro(String tipoDeErro, int codigo, String status, String nomeDoObjeto, List<ObjetoDeErro> objetoDeErros) {
        this.tipoDeErro = tipoDeErro;
        this.codigo = codigo;
        this.status = status;
        this.nomeDoObjeto = nomeDoObjeto;
        this.objetoDeErros = objetoDeErros;
    }

    public String getTipoDeErro() {
        return tipoDeErro;
    }

    public void setTipoDeErro(String tipoDeErro) {
        this.tipoDeErro = tipoDeErro;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomeDoObjeto() {
        return nomeDoObjeto;
    }

    public void setNomeDoObjeto(String nomeDoObjeto) {
        this.nomeDoObjeto = nomeDoObjeto;
    }

    public List<ObjetoDeErro> getObjetoDeErros() {
        return objetoDeErros;
    }

    public void setObjetoDeErros(List<ObjetoDeErro> objetoDeErros) {
        this.objetoDeErros = objetoDeErros;
    }
}
