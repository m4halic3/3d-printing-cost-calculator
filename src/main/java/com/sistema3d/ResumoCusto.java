package com.sistema3d;

public class ResumoCusto {
    public final double material;
    public final double maquina;
    public final double energia;
    public final double maoObra;
    public final double manutencao;
    public final double total;
    public final double venda;

    public ResumoCusto(double material, double maquina, double energia, double maoObra, double manutencao, double total, double venda) {
        this.material = material;
        this.maquina = maquina;
        this.energia = energia;
        this.maoObra = maoObra;
        this.manutencao = manutencao;
        this.total = total;
        this.venda = venda;
    }
}