package xizz.geoquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
	private static final String KEY_CHEATED = "cheated";

	private Button mPreviousButton;
	private Button mNextButton;

	private TextView mQuestionTextView;

	private Question[] mAnswerKeys = new Question[]{
			new Question(R.string.question_oceans, true),
			new Question(R.string.question_mideast, false),
			new Question(R.string.question_africa, false),
			new Question(R.string.question_americas, true),
			new Question(R.string.question_asia, true)
	};

	private boolean[] mQuestionsCheated;

	private int mCurrentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(R.string.subtitle);

		mQuestionsCheated = new boolean[mAnswerKeys.length];
		Log.d(TAG, "onCreate, index: " + mCurrentIndex + " cheated: " +
				mQuestionsCheated[mCurrentIndex]);

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
		Log.d(TAG, "onSavedInstanceState, index: " + mCurrentIndex + " cheated: " +
				mQuestionsCheated[mCurrentIndex]);
		outState.putInt(KEY_INDEX, mCurrentIndex);
		outState.putBooleanArray(KEY_CHEATED, mQuestionsCheated);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
		mQuestionsCheated = savedInstanceState.getBooleanArray(KEY_CHEATED);
		Log.d(TAG, "onRestoreInstanceState, index: " + mCurrentIndex + " cheated: " +
				mQuestionsCheated[mCurrentIndex]);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mQuestionsCheated[mCurrentIndex] |=
				data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
		Log.d(TAG, "onActivityResult, index: " + mCurrentIndex + " cheated: " +
				mQuestionsCheated[mCurrentIndex]);
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

	public void startCheatActivity(View v) {
		Intent i = new Intent(this, CheatActivity.class);
		boolean answer = mAnswerKeys[mCurrentIndex].answer;
		i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TURE, answer);
		startActivityForResult(i, 0);
	}

	private void updateUI() {
		int question = mAnswerKeys[mCurrentIndex].question;
		mQuestionTextView.setText(question);

		mPreviousButton.setEnabled(mCurrentIndex > 0);
		mNextButton.setEnabled(mCurrentIndex < mAnswerKeys.length - 1);
	}

	private void checkAnswer(boolean userAnswer) {
		boolean answer = mAnswerKeys[mCurrentIndex].answer;

		int messageResId;

		if (mQuestionsCheated[mCurrentIndex]) {
			if (userAnswer == answer) {
				messageResId = R.string.judgment_toast;
			} else {
				messageResId = R.string.incorrect_judgement_toast;
			}
		} else {
			if (userAnswer == answer) {
				messageResId = R.string.correct_toast;
			} else {
				messageResId = R.string.incorrect_toast;
			}
		}

		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}
}
