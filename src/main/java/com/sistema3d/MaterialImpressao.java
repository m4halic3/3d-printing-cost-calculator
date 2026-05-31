package com.sistema3d;

public class MaterialImpressao {
    private String tipo;
    private String densidade;
    private double custoPorGrama;

    public MaterialImpressao(String tipo, String densidade, double custoPorGrama) {
        this.tipo = tipo;
        this.densidade = densidade;
        this.custoPorGrama = custoPorGrama;
    }

    public String getTipo() { return tipo; }
    public String getDensidade() { return densidade; }
    public double getCustoPorGrama() { return custoPorGrama; }

    @Override
    public String toString() { 
        return tipo; // Exibe o tipo correto no ComboBox do JavaFX
    }
}