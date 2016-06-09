package akirakozov.ru.smartjumper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new DrawingView(this));
        setContentView(R.layout.activity_main);
    }

    public void sendStartMessage(View view) {
        Intent intent = new Intent(MainActivity.this, PlaySceneActivity.class);
        startActivity(intent);
    }
}
