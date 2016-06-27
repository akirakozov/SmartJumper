package akirakozov.ru.smartjumper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import akirakozov.ru.smartjumper.drawing.DrawingView;

public class PlaySceneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawingView(this));
    }
}
