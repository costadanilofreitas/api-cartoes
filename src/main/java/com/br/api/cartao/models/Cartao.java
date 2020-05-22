package com.br.api.cartao.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long numeroCartao;
    @NotNull
    private double limiteTotal;
    @DateTimeFormat
    private Date validade;
    @NotNull
    @Size(max = 3, message = "Cvv é obrigatorio informar três digitos")
    private int cvv;
    @NotNull
    private double limiteAtual;
    @NotNull
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
