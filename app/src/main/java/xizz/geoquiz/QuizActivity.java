package xizz.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends Activity {

	private Button mPreviousButton;

	private TextView mQuestionTextView;

	private Question[] mAnswerKey = new Question[]{
			new Question(R.string.question_oceans, true),
			new Question(R.string.question_mideast, false),
			new Question(R.string.question_africa, false),
			new Question(R.string.question_americas, true),
			new Question(R.string.question_asia, true)
	};

	private int mCurrentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		mPreviousButton = (Button) findViewById(R.id.previous_button);
		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
		mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				click(v);
			}
		});

		updateUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_quiz, menu);
		return true;
	}

	private void updateUI() {
		int question = mAnswerKey[mCurrentIndex % mAnswerKey.length].question;
		mQuestionTextView.setText(question);

		if (mCurrentIndex > 0)
			mPreviousButton.setEnabled(true);
		else
			mPreviousButton.setEnabled(false);
	}

	private void checkAnswer(boolean userAnswer) {
		boolean answer = mAnswerKey[mCurrentIndex % mAnswerKey.length].answer;

		if (userAnswer == answer)
			Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
	}

	public void click(View v) {
		switch (v.getId()) {
			case R.id.true_button:
				checkAnswer(true);
				break;
			case R.id.false_button:
				checkAnswer(false);
				break;
			case R.id.previous_button:
				if (mCurrentIndex > 0) {
					--mCurrentIndex;
					updateUI();
				}
				break;
			case R.id.question_text_view:
			case R.id.next_button:
				++mCurrentIndex;
				updateUI();
				break;
		}
	}
}
