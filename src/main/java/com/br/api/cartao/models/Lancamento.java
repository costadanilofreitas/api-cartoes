package com.br.api.cartao.models;

import com.br.api.cartao.enums.TipoDeLancamento;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message="Informar o tipo de lancamento")
    private TipoDeLancamento tipoDeLancamento;
    @DecimalMin("0.01")
    private double valor;
    @DateTimeFormat
    private Date data;
    @ManyToOne
    private Cartao cartao;

    public Lancamento() {
    }

    public Lancamento(int id, @NotNull(message = "Informar o tipo de lancamento")
            TipoDeLancamento tipoDeLancamento, @DecimalMin("0.01") double valor,
                      Date data, Cartao cartao) {
        this.id = id;
        this.tipoDeLancamento = tipoDeLancamento;
        this.valor = valor;
        this.data = data;
        this.cartao = cartao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoDeLancamento getTipoDeLancamento() {
        return tipoDeLancamento;
    }

    public void setTipoDeLancamento(TipoDeLancamento tipoDeLancamento) {
        this.tipoDeLancamento = tipoDeLancamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
