package com.br.api.cartao.models;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Cartao {
    
    private long numeroCartao;
    private double limiteTotal;
    private Date validade;
    private int cvv;
    private double limiteAtual;
    private int idCliente;

    public Cartao() {
    }

    public Cartao(long numeroCartao, double limiteTotal, Date validade, int cvv, double limiteAtual, int idCliente) {
        this.numeroCartao = numeroCartao;
        this.limiteTotal = limiteTotal;
        this.validade = validade;
        this.cvv = cvv;
        this.limiteAtual = limiteAtual;
        this.idCliente = idCliente;
    }

    public long getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(long numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public double getLimiteTotal() {
        return limiteTotal;
    }

    public void setLimiteTotal(double limiteTotal) {
        this.limiteTotal = limiteTotal;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public double getLimiteAtual() {
        return limiteAtual;
    }

    public void setLimiteAtual(double limiteAtual) {
        this.limiteAtual = limiteAtual;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
