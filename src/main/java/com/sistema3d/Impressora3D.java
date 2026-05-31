package com.sistema3d;

public class Impressora3D {
    private String modelo;
    private double preco;
    private int potencia;
    private String nomeImagem;
    private String descricao;

    public Impressora3D(String modelo, double preco, int potencia, String nomeImagem, String descricao) {
        this.modelo = modelo;
        this.preco = preco;
        this.potencia = potencia;
        this.nomeImagem = nomeImagem;
        this.descricao = descricao;
    }

    public String getModelo() { return modelo; }
    public double getPreco() { return preco; }
    public int getPotencia() { return potencia; }
    public String getNomeImagem() { return nomeImagem; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() { 
        return modelo; // Exibe o nome correto no ComboBox do JavaFX
    }
}