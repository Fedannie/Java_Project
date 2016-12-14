package project.fedorova.polyglotte;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class RepeatWordActivity extends Activity implements View.OnClickListener {
    private ImageButton left;
    private ImageButton right;
    private Button stop;
    TextView wordTV;
    TextView mainTrans;
    TextView extraTrans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeat_word);

        init();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.leftBtnRepeat):
                //TODO next word
                break;
            case (R.id.rightBtnRepeat):
                //TODO previous word
                break;
            case (R.id.stopBtn):
                //TODO exit activity
                break;
            default:
                //TODO show translation
                break;
        }
    }

    private void init() {
        left = (ImageButton) findViewById(R.id.leftBtnRepeat);
        left.setOnClickListener(this);

        right = (ImageButton) findViewById(R.id.rightBtnRepeat);
        right.setOnClickListener(this);

        stop = (Button) findViewById(R.id.stopBtn);
        stop.setOnClickListener(this);

        wordTV = (TextView) findViewById(R.id.wordTVRepeat);

        mainTrans = (TextView) findViewById(R.id.mainTranslationTVRepeat);

        extraTrans = (TextView) findViewById(R.id.extraTranslationsTVRepeat);
    }
}
