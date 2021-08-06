package raj.com.memorygame;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    Animation shakeAnimation;
    String folderName;
    List<String> fileNameList = new ArrayList<>();
    String[] pictures;
    LinearLayout[] linearLayouts;
    int counter = 0;
    int clicks = 0;
    ImageButton img1, img2;
    String image1, image2;
    List<String> isolate = new ArrayList<>();
    Handler handler;
    String isolated1, isolated2;
    SoundPool soundPool;
    int match = -1;
    int win = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(10).build();
        } else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }
        try {
            AssetManager assetManager = getBaseContext().getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("win.wav");
            win = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("match.wav");
            match = soundPool.load(descriptor, 0);


        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        int intNumber = intent.getIntExtra("itemNumber", 0);
        //Toast.makeText(getApplicationContext(), "item Number: " + intNumber, Toast.LENGTH_SHORT).show();


        handler = new Handler();

        switch (intNumber) {

            case 0:
                folderName = "amaken";
                break;
            case 1:
                folderName = "parcham";
                break;
            case 2:
                folderName = "manzare";
                break;
            case 3:
                folderName = "khodro";
                break;
            case 4:
                folderName = "motor";
                break;
            case 5:
                folderName = "gol";
                break;
            case 6:
                folderName = "cartoon";
                break;
            case 7:
                folderName = "heivanat";
                break;
            case 8:
                folderName = "argham";
                break;
            case 9:
                folderName = "horuf";
                break;
            case 10:
                folderName = "lavazem_khanegi";
                break;
            case 11:
                folderName = "havapeima";
                break;

        }
        AssetManager assetManager = getBaseContext().getAssets();
        fileNameList.clear();
        try {
            pictures = assetManager.list(folderName);
            for (String picture : pictures) {
                fileNameList.add(picture);
                fileNameList.add(picture);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(fileNameList);
        linearLayouts = new LinearLayout[4];
        linearLayouts[0] = findViewById(R.id.linear1);
        linearLayouts[1] = findViewById(R.id.linear2);
        linearLayouts[2] = findViewById(R.id.linear3);
        linearLayouts[3] = findViewById(R.id.linear4);
        for (LinearLayout ll : linearLayouts) {
            for (int i = 0; i < ll.getChildCount(); i++) {
                ImageButton imageButton = (ImageButton) ll.getChildAt(i);
                imageButton.setOnClickListener(imageButtonListener);
                imageButton.setContentDescription(fileNameList.get(counter));
                counter++;


            }
        }


    }//oncreate

    private View.OnClickListener imageButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clicks++;
            if (clicks == 1) {
                img1 = (ImageButton) v;
                img1.setEnabled(false);
                ShowImage(img1);


            } else if (clicks == 2) {
                img2 = (ImageButton) v;
                ShowImage(img2);
                if (image1.equals(image2)) {
                    img2.setEnabled(false);
                    soundPool.play(match, 1, 1, 0, 0, 1);
                    shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    img1.setAnimation(shakeAnimation);
                    img2.setAnimation(shakeAnimation);
                    isolated1 = (String) img1.getContentDescription();
                    isolated2 = (String) img2.getContentDescription();
                    isolate.add(isolated1);
                    isolate.add(isolated2);
                    if (isolate.size() == 16) {
                        soundPool.play(win, 1, 1, 0, 0, 1);
                        Toast.makeText(GameActivity.this, "تبریک شما برنده شدید ", Toast.LENGTH_LONG).show();

                    } else {

                    }
                } else {
                    disableButtons();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            img1.setImageResource(R.drawable.pattern);
                            img2.setImageResource(R.drawable.pattern);
                            enabeButtons();

                        }
                    }, 1000);
                }
                clicks = 0;

            }//end clicks==2

        }
    };

    private void enabeButtons() {
        for (LinearLayout ll : linearLayouts)
            for (int i = 0; i < ll.getChildCount(); i++) {
                ImageButton imageButton = (ImageButton) ll.getChildAt(i);
                boolean found = false;
                for (int j = 0; j < isolate.size(); j++) {
                    if (imageButton.getContentDescription() == isolate.get(j))
                        found = true;

                }
                if (found == false) {
                    imageButton.setEnabled(true);
                }

            }


    }


    private void disableButtons() {
        for (LinearLayout ll : linearLayouts)
            for (int i = 0; i < ll.getChildCount(); i++) {
                ImageButton imageButton = (ImageButton) ll.getChildAt(i);
                imageButton.setEnabled(false);

            }
    }

    private void ShowImage(ImageButton view) {
        String filename = (String) view.getContentDescription();
        if (clicks == 1)
            image1 = filename;
        else if (clicks == 2)
            image2 = filename;

        try {
            InputStream inputStream = getAssets().open(folderName + "/" + filename);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            view.setImageDrawable(drawable);
            inputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
