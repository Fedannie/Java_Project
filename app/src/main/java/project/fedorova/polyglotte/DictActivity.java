package project.fedorova.polyglotte;

import android.app.Activity;
import android.view.ViewGroup.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class DictActivity extends Activity implements View.OnClickListener {
    private Button filterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary);

        FloatingActionButton addWordBtn = (FloatingActionButton) findViewById(R.id.addWordFAB);
        addWordBtn.setOnClickListener(this);
        filterBtn = (Button) findViewById(R.id.filterButton);
        filterBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.addWordFAB):
                Intent intentAWA = new Intent(this, AddWordActivity.class);
                startActivity(intentAWA);
                break;
            case (R.id.filterButton):
                showPopup();
                break;
            default:
                break;
        }
    }

    private PopupWindow pw;
    private void showPopup() {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popupfilterthemes, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                false);
        popupWindow.showAsDropDown(filterBtn, 50, -30);
    }
}
