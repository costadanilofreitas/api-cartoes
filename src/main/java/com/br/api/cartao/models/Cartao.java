package com.br.api.cartao.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long numeroCartao;

    private double limiteTotal;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date validade;

    @Min(100)
    @Max(999)
    private int cvv;

    private double limiteAtual;

    @ManyToOne
    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cartao() {
    }

    public Cartao(long numeroCartao, double limiteTotal, Date validade, int cvv, double limiteAtual, Cliente cliente) {
        this.numeroCartao = numeroCartao;
        this.limiteTotal = limiteTotal;
        this.validade = validade;
        this.cvv = cvv;
        this.limiteAtual = limiteAtual;
        this.cliente = cliente;
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
}
