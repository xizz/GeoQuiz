package xizz.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends Activity {
	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";

	private Button mPreviousButton;
	private Button mNextButton;

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
		mNextButton = (Button) findViewById(R.id.next_button);
		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
		mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { click(v); }
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSavedInstanceState");
		outState.putInt(KEY_INDEX, mCurrentIndex);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_quiz, menu);
		return true;
	}

	private void updateUI() {
		int question = mAnswerKey[mCurrentIndex].question;
		mQuestionTextView.setText(question);

		mPreviousButton.setEnabled(mCurrentIndex > 0);
		mNextButton.setEnabled(mCurrentIndex < mAnswerKey.length - 1);
	}

	private void checkAnswer(boolean userAnswer) {
		boolean answer = mAnswerKey[mCurrentIndex].answer;

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
				--mCurrentIndex;
				updateUI();
				break;
			case R.id.question_text_view:
			case R.id.next_button:
				++mCurrentIndex;
				updateUI();
				break;
		}
	}
}
