package com.example.calculator;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private Switch radSwitch;
    private String currentInput = "";
    private String operator = "";
    private double firstNum = 0;
    private boolean isDegree = true;
    private Button btn_clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicialización de los componentes de la interfaz
        display = findViewById(R.id.display);
        radSwitch = findViewById(R.id.switch1);

        // Inicialmente se muestra "0" en el display
        display.setText("0");

        // Listener para el radSwitch para alternar entre grados y radianes
        radSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> isDegree = !isChecked);
        // Boton clear
        btn_clear = findViewById(R.id.buttonclear);
        btn_clear.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
                clearCalculator();
            }

        });
        // Configuración de listeners para los botones
        setupNumberButtonListeners();
        setupOperatorButtonListeners();


    }

    private void setupNumberButtonListeners() {
        int[] buttonsN = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9
        };

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            currentInput += b.getText().toString();
            display.setText(currentInput);
        };

        for (int id : buttonsN) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setupOperatorButtonListeners() {
        int[] operatorB = {
                R.id.bottonsinus, R.id.buttoncosinus, R.id.buttondivision,
                R.id.buttonmultiplicacion, R.id.buttonresta, R.id.buttonsuma,
                R.id.buttontangent, R.id.button_equal
        };

        for (int id : operatorB) {
            findViewById(id).setOnClickListener(v -> {
                Button b = (Button) v;
                OperationManagement(b.getText().toString());
            });
        }
    }

    private void OperationManagement(String operator) {
        switch (operator) {
            case "+":
            case "-":
            case "x":
            case "%":
                if (!currentInput.isEmpty()) {
                    firstNum = Double.parseDouble(currentInput);
                    this.operator = operator;
                    currentInput = "";
                }
                break;
            case "sin":
            case "cos":
            case "tan":
                if (!currentInput.isEmpty()) {
                    double angle = Double.parseDouble(currentInput);
                    double result = trigonometricOperation(angle, operator);
                    display.setText(String.valueOf(result));
                    currentInput = String.valueOf(result);
                    this.operator = "";
                }
                break;
            case "=":
                if (!currentInput.isEmpty() && !this.operator.isEmpty()) {
                    double secondNum = Double.parseDouble(currentInput);
                    double result = Operation(firstNum, this.operator, secondNum);
                    display.setText(String.valueOf(result));
                    currentInput = String.valueOf(result);
                    this.operator = "";
                }
                break;
        }
    }

    public void clearCalculator() {
        // Limpia todos los valores de la calculadora
        currentInput = "";
        operator = "";
        firstNum = 0;
        display.setText("0"); // Asegura que la pantalla muestra "0"
    }

    private double trigonometricOperation(double angle, String operator) {
        // Convierte a radianes si el switch está en grados
        if (isDegree) {
            angle = Math.toRadians(angle);
        }

        switch (operator) {
            case "sin":
                return Math.sin(angle);
            case "cos":
                return Math.cos(angle);
            case "tan":
                return Math.tan(angle);
            default:
                return 0;
        }
    }

    private double Operation(double num1, String operator, double num2) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "x": // Multiplicación
                return num1 * num2;
            case "%": // División
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    display.setText("Error: División por cero");
                    return 0;
                }
            default:
                return 0;
        }
    }
}


