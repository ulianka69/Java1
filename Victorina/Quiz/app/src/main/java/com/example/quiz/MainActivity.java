package com.example.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btn1, btn2, btn3, btn4, btnRestart;
    private TextView resultText;

    private QuizQuestion[] questions = {
            new QuizQuestion(R.drawable.image1, "Илья Репин", new String[]{"Илья Репин", "Виктор Васнецов", "Клод Моне", "Пикассо"}),
            new QuizQuestion(R.drawable.image2, "Клод Моне", new String[]{"Иван Шишкин", "Ренуар", "Бэнкси", "Клод Моне"}),
            new QuizQuestion(R.drawable.image3, "Леонардо да Винче", new String[]{"Леонардо да Винче", "Сальводор Дали", "Валентин Серов", "Боттичелли"}),
            new QuizQuestion(R.drawable.image4, "Рафаэль", new String[]{"Джексон Поллок", "Рафаэль", "Фрида Кало", "Рене Магритт"}),
            new QuizQuestion(R.drawable.image5, "Энди Уорхол", new String[]{"Энди Уорхол", "Архип Куинджи", "Микеланджело", "Рембрандт"}),
            new QuizQuestion(R.drawable.image6, "Веласкес", new String[]{"Я", "Орест Кипренский", "Исаак Левитан", "Веласкес"})
    };

    private int currentQuestionIndex = 0;
    private int correctAnswers = 0; // Счётчик правильных ответов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btn1 = findViewById(R.id.btnOption1);
        btn2 = findViewById(R.id.btnOption2);
        btn3 = findViewById(R.id.btnOption3);
        btn4 = findViewById(R.id.btnOption4);
        resultText = findViewById(R.id.resultText);
        btnRestart = findViewById(R.id.btnRestart);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartQuiz();
            }
        });

        loadQuestion(currentQuestionIndex);

        View.OnClickListener optionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                String selectedAnswer = clickedButton.getText().toString();
                String correctAnswer = questions[currentQuestionIndex].correctAnswer;

                // Проверяем и увеличиваем счётчик при правильном ответе
                if (selectedAnswer.equals(correctAnswer)) {
                    correctAnswers++;
                    resultText.setText("Правильно!");
                    resultText.setTextColor(0xFF00FF00);
                } else {
                    resultText.setText("Неправильно! Правильный ответ: " + correctAnswer);
                    resultText.setTextColor(0xFFFF0000);
                }

                setAnswerButtonsEnabled(false);

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentQuestionIndex++;
                        if (currentQuestionIndex < questions.length) {
                            loadQuestion(currentQuestionIndex);
                            resultText.setText("");
                            setAnswerButtonsEnabled(true);
                        } else {
                            // ВИКТОРИНА ЗАВЕРШЕНА — показываем итог
                            String finalResult = String.format("✅ Вы ответили правильно на %d из %d вопросов!",
                                    correctAnswers, questions.length);
                            resultText.setText(finalResult);
                            resultText.setTextColor(0xFF0000FF); // Синий цвет для итога

                            btnRestart.setVisibility(View.VISIBLE);
                        }
                    }
                }, 2000);
            }
        };

        btn1.setOnClickListener(optionClickListener);
        btn2.setOnClickListener(optionClickListener);
        btn3.setOnClickListener(optionClickListener);
        btn4.setOnClickListener(optionClickListener);
    }private void loadQuestion(int index) {
        QuizQuestion q = questions[index];
        imageView.setImageResource(q.imageResId);
        btn1.setText(q.options[0]);
        btn2.setText(q.options[1]);
        btn3.setText(q.options[2]);
        btn4.setText(q.options[3]);
    }

    private void setAnswerButtonsEnabled(boolean enabled) {
        btn1.setEnabled(enabled);
        btn2.setEnabled(enabled);
        btn3.setEnabled(enabled);
        btn4.setEnabled(enabled);
    }

    private void restartQuiz() {
        currentQuestionIndex = 0;
        correctAnswers = 0; // Сбрасываем счётчик
        resultText.setText("");
        btnRestart.setVisibility(View.GONE);
        setAnswerButtonsEnabled(true);
        loadQuestion(currentQuestionIndex);
    }

    private static class QuizQuestion {
        int imageResId;
        String correctAnswer;
        String[] options;

        QuizQuestion(int imageResId, String correctAnswer, String[] options) {
            this.imageResId = imageResId;
            this.correctAnswer = correctAnswer;
            this.options = options;
        }
    }
}