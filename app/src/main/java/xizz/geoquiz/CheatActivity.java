package xizz.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class CheatActivity extends Activity {

	public static final String EXTRA_ANSWER_IS_TURE = "xizz.geoquiz.ANSWER_IS_TRUE";
	public static final String EXTRA_ANSWER_SHOWN = "xizz.geoquiz.ANSWER_SHOWN";

	private boolean mAnswer;
	private TextView mAnswerTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);

		mAnswer = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TURE, false);
		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);

		setAnswerShownresult(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_cheat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings)
			return true;

		return super.onOptionsItemSelected(item);
	}

	public void showAnswer(View v) {
		if (mAnswer)
			mAnswerTextView.setText(R.string.true_button);
		else
			mAnswerTextView.setText(R.string.false_button);
		setAnswerShownresult(true);
	}

	private void setAnswerShownresult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}
}
