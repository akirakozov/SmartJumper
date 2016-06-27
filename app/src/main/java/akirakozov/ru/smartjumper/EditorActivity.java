package akirakozov.ru.smartjumper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import akirakozov.ru.smartjumper.drawing.SwampEditorView;
import akirakozov.ru.smartjumper.level.LevelType;
import akirakozov.ru.smartjumper.level.Levels;
import akirakozov.ru.smartjumper.level.LevelsLoader;

public class EditorActivity extends AppCompatActivity {
    private final Levels levels = new Levels();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        addSaveButtonListener();
    }

    private void addSaveButtonListener() {
        final Button saveButton = (Button) findViewById(R.id.saveSwampButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwampEditorView swampView = (SwampEditorView) findViewById(R.id.swampEditorView);
                Levels.Level newLevel = new Levels.Level(swampView.getStates());
                levels.addLevel(LevelType.EASY, newLevel);
                String workingDir = getApplicationContext().getFilesDir().getAbsolutePath();
                LevelsLoader.storeLevels(levels, workingDir + "/result.json");
            }
        });
    }

}
