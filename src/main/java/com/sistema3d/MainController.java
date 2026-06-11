package com.sistema3d;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MainController {

    // Componentes injetados do FXML
    @FXML private HBox raizJanela; 
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

    // Componentes adicionais mapeados para estilização dinâmica
    @FXML private Button btnCalcular; 
    @FXML private GridPane painelResultados;

    @FXML
    public void initialize() {
        // Impressoras Hardcoded com a extensão corrigida para .jpeg
        comboImpressora.getItems().addAll(
            new Impressora3D("Ender 3", 1500.00, 350, "ender3.jpeg", "Impressora de entrada, ótima para filamentos básicos."),
            new Impressora3D("Creality K1", 3500.00, 500, "creality_k1.jpeg", "Alta velocidade de impressão com estrutura fechada CoreXY."),
            new Impressora3D("Bambu Lab A1", 4200.00, 400, "bambu_a1.jpeg", "Excelente qualidade de impressão e calibração automática robusta.")
        );

        // Materiais Hardcoded do enunciado
        comboMaterial.getItems().addAll(
            new MaterialImpressao("PLA baixa densidade", "Baixa", 0.08),
            new MaterialImpressao("PLA média densidade", "Média", 0.12),
            new MaterialImpressao("PLA alta densidade", "Alta", 0.18)
        );

        // Aplica o visual moderno escuro, plano e sem sombras de foco
        aplicarEstiloVisual();

        // Seleções iniciais padrão
        comboImpressora.getSelectionModel().selectFirst();
        comboMaterial.getSelectionModel().selectFirst();
        handleImpressoraSelecionada();
    }

    private void aplicarEstiloVisual() {
        // Estilo Dark unificado para Inputs - Remove insets (o quadrado fantasma) e efeitos nativos
        String estiloInputsEscuros = "-fx-background-color: #1E293B; -fx-background-radius: 8; -fx-border-color: #334155; -fx-border-radius: 8; -fx-padding: 8; -fx-text-fill: #F1F5F9; -fx-background-insets: 0; -fx-effect: null;";
        
        comboImpressora.setStyle(estiloInputsEscuros);
        comboMaterial.setStyle(estiloInputsEscuros);
        txtNomeArquivo.setStyle(estiloInputsEscuros);
        txtMaterialGramas.setStyle(estiloInputsEscuros);
        txtTempoHoras.setStyle(estiloInputsEscuros);

        // Remove os anéis de foco azul/cinza padrão do sistema operacional ao clicar nos componentes
        comboImpressora.setFocusTraversable(false);
        comboMaterial.setFocusTraversable(false);
        txtNomeArquivo.setFocusTraversable(false);
        txtMaterialGramas.setFocusTraversable(false);
        txtTempoHoras.setFocusTraversable(false);
        chkFalha.setFocusTraversable(false);
        chkMargem.setFocusTraversable(false);

        // Garante que a lista de opções interna do ComboBox acompanhe a paleta escura limpa
        estilizarListaInternaComboBox(comboImpressora);
        estilizarListaInternaComboBox(comboMaterial);

        // Estilização do Botão de Calcular totalmente FLAT (Sem sombras nem fundos cinzas tridimensionais)
        if (btnCalcular != null) {
            btnCalcular.setFocusTraversable(false);
            String estiloBotaoNormal = "-fx-background-color: #0EA5E9; -fx-background-radius: 8; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 12; -fx-effect: null; -fx-background-insets: 0;";
            String estiloBotaoHover = "-fx-background-color: #0284C7; -fx-background-radius: 8; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 12; -fx-effect: null; -fx-background-insets: 0;";
            
            btnCalcular.setStyle(estiloBotaoNormal);
            btnCalcular.setOnMouseEntered(e -> btnCalcular.setStyle(estiloBotaoHover));
            btnCalcular.setOnMouseExited(e -> btnCalcular.setStyle(estiloBotaoNormal));
        }
    }

    // Método auxiliar para formatar as caixas de opções internas de forma limpa e escura
    private <T> void abrirEstiloListaInterna(ComboBox<T> combo) {
        // Mantido apenas para compatibilidade caso use em outra assinatura
    }

    private <T> void estilizarListaInternaComboBox(ComboBox<T> combo) {
        combo.setCellFactory(lv -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
                setStyle("-fx-background-color: #1E293B; -fx-text-fill: #F1F5F9; -fx-padding: 8; -fx-effect: null; -fx-background-insets: 0;");
                setOnMouseEntered(e -> setStyle("-fx-background-color: #334155; -fx-text-fill: #FFFFFF; -fx-padding: 8; -fx-effect: null; -fx-background-insets: 0;"));
                setOnMouseExited(e -> setStyle("-fx-background-color: #1E293B; -fx-text-fill: #F1F5F9; -fx-padding: 8; -fx-effect: null; -fx-background-insets: 0;"));
            }
        });
        combo.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
                setStyle("-fx-text-fill: #F1F5F9;");
            }
        });
    }

    @FXML
    private void handleImpressoraSelecionada() {
        Impressora3D selecionada = comboImpressora.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            lblDescricao.setText(selecionada.getDescricao() + " (" + selecionada.getPotencia() + "W)");
            try {
                String caminhoImg = "/com/sistema3d/images/" + selecionada.getNomeImagem();
                URL recurso = getClass().getResource(caminhoImg);
                
                if (recurso != null) {
                    imgImpressora.setImage(new Image(recurso.toExternalForm()));
                } else {
                    System.out.println("Aviso: Imagem não encontrada no caminho: " + caminhoImg);
                    imgImpressora.setImage(null);
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar a imagem: " + e.getMessage());
                imgImpressora.setImage(null);
            }
        }
    }

    @FXML
    private void handleCalcular() {
        try {
            Impressora3D imp = comboImpressora.getSelectionModel().getSelectedItem();
            MaterialImpressao mat = comboMaterial.getSelectionModel().getSelectedItem();
            
            if (txtMaterialGramas.getText().isBlank() || txtTempoHoras.getText().isBlank()) {
                mostrarAlertaErro("Campos vazios", "Por favor, preencha os campos de peso e tempo.");
                return;
            }

            double gramas = Double.parseDouble(txtMaterialGramas.getText().trim());
            double horas = Double.parseDouble(txtTempoHoras.getText().trim());
            String nome = txtNomeArquivo.getText();

            ProjetoImpressao projeto = new ProjetoImpressao(
                nome, imp, mat, gramas, horas, chkFalha.isSelected(), chkMargem.isSelected()
            );

            ResumoCusto resumo = CalculadoraCusto.calcular(projeto);

            lblCustoMaterial.setText(String.format("R$ %.2f", resumo.material));
            lblCustoMaquina.setText(String.format("R$ %.2f", resumo.maquina));
            lblCustoEnergia.setText(String.format("R$ %.2f", resumo.energia));
            lblCustoMaoObra.setText(String.format("R$ %.2f", resumo.maoObra));
            lblCustoManutencao.setText(String.format("R$ %.2f", resumo.manutencao));
            lblCustoTotal.setText(String.format("R$ %.2f", resumo.total));
            lblPrecoVenda.setText(String.format("R$ %.2f", resumo.venda));

        } catch (NumberFormatException e) {
            mostrarAlertaErro("Entrada Inválida", "Por favor, insira valores numéricos válidos (use ponto para decimais).");
        }
    }

    private void mostrarAlertaErro(String titulo, String mensagem) {
        // Corrigido o erro de compilação da variável 'message'
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.OK);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}