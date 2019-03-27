package com.example.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText visor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* instanciando atributos com os respectivos ids */

        visor = findViewById(R.id.visor);

        Button[] bt_numerico = new Button[10];
        Button[] bt_operacao = new Button[9];

        bt_numerico[0] = findViewById(R.id.number0);
        bt_numerico[1] = findViewById(R.id.number1);
        bt_numerico[2] = findViewById(R.id.number2);
        bt_numerico[3] = findViewById(R.id.number3);
        bt_numerico[4] = findViewById(R.id.number4);
        bt_numerico[5] = findViewById(R.id.number5);
        bt_numerico[6] = findViewById(R.id.number6);
        bt_numerico[7] = findViewById(R.id.number7);
        bt_numerico[8] = findViewById(R.id.number8);
        bt_numerico[9] = findViewById(R.id.number9);

        bt_operacao[0] = findViewById(R.id.mais);
        bt_operacao[1] = findViewById(R.id.menos);
        bt_operacao[2] = findViewById(R.id.multiplicar);
        bt_operacao[3] = findViewById(R.id.barra);
        bt_operacao[4] = findViewById(R.id.ponto);
        bt_operacao[5] = findViewById(R.id.igual);
        bt_operacao[6] = findViewById(R.id.clear);
        bt_operacao[7] = findViewById(R.id.porcento);
        bt_operacao[8] = findViewById(R.id.maisOuMenos);

        for (int i = 0; i < 10; i++) {
            bt_numerico[i].setOnClickListener(this);
        }

        for (int i = 0; i < 9; i++) {
            bt_operacao[i].setOnClickListener(this);
        }

        bt_operacao[6].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                visor.setText("0");
                return true;
            }
        });

    }

    /*Exibir numeros na tela */
    private void tecladoNumero(String lerNumero){
        String preencheVisor;
        if(visor.getText().toString().trim().equals("0")){
            limparVisor();
            String textoVisor = visor.getText().toString();
            preencheVisor = textoVisor + lerNumero;
            visor.setText(preencheVisor);
        }else {
            String textoVisor = visor.getText().toString();
            preencheVisor = textoVisor + lerNumero;
            visor.setText(preencheVisor);
        }
    }

    /*Limpar tela*/
    private void limparVisor() {

        int pegarTamanho = visor.getText().toString().length();
        String tudoNovoDeNovo = visor.getText().toString().substring(0, pegarTamanho - 1);
        visor.setText(tudoNovoDeNovo);
    }

    /*Colocar ponto na tela*/
    private void ponto(){
        String textvisor = visor.getText().toString();
        visor.setText(String.format("%s", textvisor + "."));
    }

    /*faz porcentagem*/
    private void porcentagem(){
        double porcent =  Double.parseDouble(visor.getText().toString().trim()) / 100;

        if(porcent == Math.rint(porcent)){
            int porcentagem = (int) porcent;
            visor.setText(String.format("%s", porcentagem));
        }else{
            visor.setText(String.format("%s", porcent));
        }
    }

    private void maisOumenos(String equacao){

        double var = Double.parseDouble(equacao) * (-1);
        visor.setText(String.valueOf(var));


    }

    /*botao de igual*/
    private void igual(String equacao){

        operacao(equacao);
    }

    /*faz a operacao*/
    private void operacao(String equacao){

        int posicao;
        int tamanho = equacao.length() - 1;
        String sinal;


        for(int i = 0; i <= tamanho; i++){

            char b = equacao.charAt(i);

            if(b == '+') {
                posicao = i;
                sinal = "+";
                pegarPosicoes(equacao, sinal, posicao);
                break;
            }

            if (b == '-') {
                posicao = i;
                sinal = "-";
                pegarPosicoes(equacao, sinal, posicao);
                break;
            }
        }

        for(int i = 0; i <= tamanho; i++){

            char a = equacao.charAt(i);

            if(a == '/') {
                posicao = i;
                sinal = "/";
                pegarPosicoes(equacao, sinal, posicao);
                break;
            }

            if(a == '*') {
                posicao = i;
                sinal = "*";
                pegarPosicoes(equacao, sinal, posicao);
                break;
            }
        }
    }

    /*pega as posicoes da equacao*/
    private void pegarPosicoes(String equacao, String sinal, int posicao){
        int tamanho = equacao.length() - 1;
        int direita = posicao;
        int esquerda = posicao;

        for(int teste = posicao + 1; teste <= tamanho; teste++) {
            char a = equacao.charAt(teste);

            if ((a == '+') || (a == '-')  || (a == '*') || (a == '/')) {
                break;
            }else{
                direita++;
            }
        }
        for(int teste = posicao - 1; teste >= 0; teste--) {
            char b = equacao.charAt(teste);
            if ((b == '+') || (b == '-')  || (b == '*') || (b == '/')) {
                break;
            }else {
                esquerda--;
            }
        }

        String valorDireito = equacao.substring(posicao + 1,direita + 1);
        String valorEsquerdo = equacao.substring(esquerda, posicao);
        String valorForaDireito = equacao.substring((direita + 1), tamanho + 1);
        String valorForaEsquerdo = equacao.substring(0, esquerda);

        double operador1 = Double.parseDouble(valorDireito.trim());
        double operador2 = Double.parseDouble(valorEsquerdo.trim());

        resultado(sinal, valorForaEsquerdo, valorForaDireito, operador1, operador2);

    }

    /*pega resultado*/
    private void resultado(String sinal, String valorForaEsquerdo, String valorForaDireito, double operador1, double operador2){
        String equacaoTratada;
        String equacao;

        switch (sinal) {
            case "/": {
                double resultado = operador2 / operador1;
                equacaoTratada = valorForaEsquerdo + String.valueOf(resultado) + valorForaDireito;
                equacao = equacaoTratada;
                if (valorForaDireito.equals("") && valorForaEsquerdo.equals("")){
                    exibir(equacao);
                }else{
                    igual(equacao);
                }
                break;
            }
            case "*": {
                double resultado = operador2 * operador1;
                equacaoTratada = valorForaEsquerdo + String.valueOf(resultado) + valorForaDireito;
                equacao = equacaoTratada;
                if (valorForaDireito.equals("") && valorForaEsquerdo.equals("")){
                    exibir(equacao);
                }else{
                    igual(equacao);
                }
                break;
            }
            case "+": {
                double resultado = operador2 + operador1;
                equacaoTratada = valorForaEsquerdo + String.valueOf(resultado) + valorForaDireito;
                equacao = equacaoTratada;
                visor.setText(equacaoTratada);
                if (valorForaDireito.equals("") && valorForaEsquerdo.equals("")){
                    exibir(equacao);
                }else{
                    igual(equacao);
                }
                break;
            }
            case "-": {
                double resultado = operador2 - operador1;
                equacaoTratada = valorForaEsquerdo + String.valueOf(resultado) + valorForaDireito;
                equacao = equacaoTratada;
                if (valorForaDireito.equals("") && valorForaEsquerdo.equals("")){
                    exibir(equacao);
                }else{
                    igual(equacao);
                }
                break;
            }
        }
    }

    /*exibir resultado*/
    private void exibir(String equacao){
        double result = Double.parseDouble(equacao);
        if(result == Math.rint(result)){
            int resultado = (int) result;
            visor.setText(String.format("%s", resultado));
        }else{
            visor.setText(String.format("%s", result));
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.number0){
            tecladoNumero("0");
        }

        if(id == R.id.number1){
            tecladoNumero("1");
        }

        if(id == R.id.number2){
            tecladoNumero("2");
        }

        if(id == R.id.number3){
            tecladoNumero("3");
        }

        if(id == R.id.number4){
            tecladoNumero("4");
        }

        if(id == R.id.number5){
            tecladoNumero("5");
        }

        if(id == R.id.number6){
            tecladoNumero("6");
        }

        if(id == R.id.number7){
            tecladoNumero("7");
        }

        if(id == R.id.number8){
            tecladoNumero("8");
        }

        if(id == R.id.number9){
            tecladoNumero("9");
        }

        if(id == R.id.clear){
            limparVisor();
        }

        if(id == R.id.igual){
            String equacao = visor.getText().toString();
            igual(equacao);
        }

        if(id == R.id.ponto){
            ponto();
        }

        if(id == R.id.porcento){
            porcentagem();
        }

        if(id == R.id.mais){
            tecladoNumero("+");
        }

        if(id == R.id.menos){
            tecladoNumero("-");
        }

        if(id == R.id.multiplicar){
            tecladoNumero("*");
        }

        if(id == R.id.barra){
            tecladoNumero("/");
        }

        if(id == R.id.maisOuMenos){
            String equacao = visor.getText().toString();
            maisOumenos(equacao);
        }
    }
}
