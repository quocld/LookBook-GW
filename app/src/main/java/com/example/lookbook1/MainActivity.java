package com.example.lookbook1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals, buttonPercent;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;
    String expressionToCalculate = "";
    boolean shouldCalculate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);
        buttonC = findViewById(R.id.button_c);
        buttonDivide = findViewById(R.id.button_divide);
        buttonMultiply = findViewById(R.id.button_multiply);
        buttonPlus = findViewById(R.id.button_plus);
        buttonMinus = findViewById(R.id.button_minus);
        buttonEquals = findViewById(R.id.button_equals);
        buttonPercent = findViewById(R.id.button_percent);
        button0 = findViewById(R.id.button_0);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        buttonAC = findViewById(R.id.button_ac);
        buttonDot = findViewById(R.id.button_dot);
        setClickListeners();
    }

    void setClickListeners() {
        buttonC.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        buttonPercent.setOnClickListener(this);
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonAC.setOnClickListener(this);
        buttonDot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            expressionToCalculate = "";
            shouldCalculate = false;
            return;
        }

        if (buttonText.equals("=")) {
            if (shouldCalculate) {
                // Tính toán và hiển thị kết quả ở đây
                String finalResult = getResult(expressionToCalculate);
                if (!finalResult.equals("Err")) {
                    DecimalFormat decimalFormat = new DecimalFormat("#.####");
                    finalResult = decimalFormat.format(Double.parseDouble(finalResult));
                    resultTv.setText(finalResult);
                    shouldCalculate = false; // Đặt lại để không tính toán lại khi bấm nút "=" một lần nữa
                }
            }
            return;
        }

        if (buttonText.equals("C")) {
            // Xóa một ký tự khỏi biểu thức tính toán
            if (!expressionToCalculate.isEmpty()) {
                expressionToCalculate = expressionToCalculate.substring(0, expressionToCalculate.length() - 1);
                solutionTv.setText(expressionToCalculate);
                shouldCalculate = true; // Đặt lại để tính toán lại nếu bạn nhấn nút "=" sau đó
            }
            return;
        }

        if (buttonText.equals("%")) {
            // Chia biểu thức cho 100 và hiển thị kết quả
            expressionToCalculate = String.valueOf(Double.parseDouble(expressionToCalculate) / 100);
            solutionTv.setText(expressionToCalculate);
            shouldCalculate = true;
            return;
        }

        // Xử lý các nút số và phép toán
        if (buttonText.equals("x")) {
            expressionToCalculate += "*"; // Sử dụng "*" thay vì "x" cho phép nhân
        } else {
            expressionToCalculate += buttonText;
        }
        solutionTv.setText(expressionToCalculate);
        shouldCalculate = true;
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}
