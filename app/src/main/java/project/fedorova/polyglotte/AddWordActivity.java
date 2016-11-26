package project.fedorova.polyglotte;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileOutputStream;

public class AddWordActivity extends Activity implements View.OnClickListener {
    Button okBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addword);

        okBtn = (Button) findViewById(R.id.okButton);
        okBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
    }
}
