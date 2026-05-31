package com.sistema3d;

import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController {

    @FXML private ComboBox<Impressora3D> comboImpressora;
    @FXML private ComboBox<MaterialImpressao> comboMaterial;
    @FXML private TextField txtNomeArquivo;
    @FXML private TextField txtMaterialGramas;
    @FXML private TextField txtTempoHoras;
    @FXML private CheckBox chkFalha;
    @FXML private CheckBox chkMargem;
    
    @FXML private ImageView imgImpressora;
    @FXML private Label lblDescricao;
    
    @FXML private Label lblCustoMaterial;
    @FXML private Label lblCustoMaquina;
    @FXML private Label lblCustoEnergia;
    @FXML private Label lblCustoMaoObra;
    @FXML private Label lblCustoManutencao;
    @FXML private Label lblCustoTotal;
    @FXML private Label lblPrecoVenda;

    @FXML
    public void initialize() {
        // Impressoras Hardcoded do enunciado (Corrigido de ComboBoxImpressora3D para Impressora3D)
        comboImpressora.getItems().addAll(
            new Impressora3D("Ender 3", 1500.00, 350, "ender3.png", "Impressora de entrada, ótima para filamentos básicos."),
            new Impressora3D("Creality K1", 3500.00, 500, "k1.png", "Alta velocidade de impressão com estrutura fechada CoreXY."),
            new Impressora3D("Bambu Lab A1", 4200.00, 400, "bambu_a1.png", "Excelente qualidade de impressão e calibração automática robusta.")
        );

        // Materiais Hardcoded do enunciado
        comboMaterial.getItems().addAll(
            new MaterialImpressao("PLA baixa densidade", "Baixa", 0.08),
            new MaterialImpressao("PLA média densidade", "Média", 0.12),
            new MaterialImpressao("PLA alta densidade", "Alta", 0.18)
        );

        // Seleções iniciais padrão
        comboImpressora.getSelectionModel().selectFirst();
        comboMaterial.getSelectionModel().selectFirst();
        handleImpressoraSelecionada();
    }

    @FXML
    private void handleImpressoraSelecionada() {
        Impressora3D selecionada = comboImpressora.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            lblDescricao.setText(selecionada.getDescricao() + " (" + selecionada.getPotencia() + "W)");
            try {
                String caminhoImg = "/com/sistema3d/images/" + selecionada.getNomeImagem();
                InputStream is = getClass().getResourceAsStream(caminhoImg);
                if (is != null) {
                    imgImpressora.setImage(new Image(is));
                } else {
                    imgImpressora.setImage(null);
                }
            } catch (Exception e) {
                imgImpressora.setImage(null);
            }
        }
    }

    @FXML
    private void handleCalcular() {
        try {
            Impressora3D imp = comboImpressora.getSelectionModel().getSelectedItem();
            MaterialImpressao mat = comboMaterial.getSelectionModel().getSelectedItem();
            
            double gramas = Double.parseDouble(txtMaterialGramas.getText());
            double horas = Double.parseDouble(txtTempoHoras.getText());
            String nome = txtNomeArquivo.getText();

            ProjetoImpressao projeto = new ProjetoImpressao(
                nome, imp, mat, gramas, horas, chkFalha.isSelected(), chkMargem.isSelected()
            );

            ResumoCusto resumo = CalculadoraCusto.calcular(projeto);

            // Atualiza os valores na tela formatados como moeda
            lblCustoMaterial.setText(String.format("R$ %.2f", resumo.material));
            lblCustoMaquina.setText(String.format("R$ %.2f", resumo.maquina));
            lblCustoEnergia.setText(String.format("R$ %.2f", resumo.energia));
            lblCustoMaoObra.setText(String.format("R$ %.2f", resumo.maoObra));
            lblCustoManutencao.setText(String.format("R$ %.2f", resumo.manutencao));
            lblCustoTotal.setText(String.format("R$ %.2f", resumo.total));
            lblPrecoVenda.setText(String.format("R$ %.2f", resumo.venda));

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, insira valores numéricos válidos para peso e tempo.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}