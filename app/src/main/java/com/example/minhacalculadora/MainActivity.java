package com.example.minhacalculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declaração dos campos de entrada
    private EditText campoValor1, campoOperacao, campoValor2;
    private TextView textoResultado;

    // Campo em foco no momento (para saber onde escrever os números)
    private EditText campoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Conecta com o layout XML

        // Ligando os componentes visuais às variáveis
        campoValor1 = findViewById(R.id.campo_valor1);
        campoOperacao = findViewById(R.id.campo_operacao);
        campoValor2 = findViewById(R.id.campo_valor2);
        textoResultado = findViewById(R.id.resultado_texto);

        // Começa com foco no primeiro valor
        campoSelecionado = campoValor1;

        // Muda o foco conforme o usuário toca no campo
        campoValor1.setOnClickListener(v -> campoSelecionado = campoValor1);
        campoOperacao.setOnClickListener(v -> campoSelecionado = campoOperacao);
        campoValor2.setOnClickListener(v -> campoSelecionado = campoValor2);

        // IDs dos botões de números e ponto
        int[] botoesNumericos = {
                R.id.numero_zero, R.id.numero_um, R.id.numero_dois,
                R.id.numero_tres, R.id.numero_quatro, R.id.numero_cinco,
                R.id.numero_seis, R.id.numero_sete, R.id.numero_oito,
                R.id.numero_nove, R.id.numero_ponto
        };

        // Quando o botão é pressionado, insere o número no campo em foco
        View.OnClickListener numeroClique = v -> {
            Button botao = (Button) v;
            campoSelecionado.append(botao.getText().toString());
        };

        // Aplica a lógica para cada botão numérico
        for (int id : botoesNumericos) {
            findViewById(id).setOnClickListener(numeroClique);
        }

        // Operações matemáticas
        int[] botoesOperadores = {
                R.id.botao_somar, R.id.botao_subtrair, R.id.botao_multiplicar, R.id.botao_dividir
        };

        // Quando o operador é clicado, ele aparece no campo e muda o foco pro 2º número
        View.OnClickListener operadorClique = v -> {
            Button b = (Button) v;
            String simbolo = b.getText().toString();
            if (simbolo.equals("×")) simbolo = "*";
            if (simbolo.equals("÷")) simbolo = "/";
            campoOperacao.setText(simbolo);
            campoSelecionado = campoValor2;
        };

        for (int id : botoesOperadores) {
            findViewById(id).setOnClickListener(operadorClique);
        }

        // Botão de limpar (AC)
        findViewById(R.id.botao_limpar).setOnClickListener(v -> {
            campoValor1.setText("");
            campoOperacao.setText("");
            campoValor2.setText("");
            textoResultado.setText("");
            campoSelecionado = campoValor1;
        });

        // Botão igual (=), faz o cálculo
        findViewById(R.id.botao_igual).setOnClickListener(v -> {
            try {
                String str1 = campoValor1.getText().toString().trim();
                String op = campoOperacao.getText().toString().trim();
                String str2 = campoValor2.getText().toString().trim();

                if (str1.isEmpty() || str2.isEmpty() || op.isEmpty()) {
                    textoResultado.setText("Preencha tudo");
                    return;
                }

                double n1 = Double.parseDouble(str1);
                double n2 = Double.parseDouble(str2);
                double res;

                switch (op) {
                    case "+": res = n1 + n2; break;
                    case "-": res = n1 - n2; break;
                    case "*": res = n1 * n2; break;
                    case "/":
                        if (n2 == 0) {
                            textoResultado.setText("Erro: Divisão por 0");
                            return;
                        }
                        res = n1 / n2;
                        break;
                    default:
                        textoResultado.setText("Operador inválido");
                        return;
                }

                textoResultado.setText(String.valueOf(res));

            } catch (NumberFormatException e) {
                textoResultado.setText("Erro nos valores");
            }
        });
    }
}
