package com.example.thuytrangnguyen.jalearning.gui;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuytrangnguyen.jalearning.R;
import com.example.thuytrangnguyen.jalearning.helper.DatabaseHelper;
import com.example.thuytrangnguyen.jalearning.object.Word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

/**
 * Created by Thuy Trang Nguyen on 8/7/2016.
 */
public class Answer extends AppCompatActivity {

    TextView tvQues;
    TextToSpeech t1;
    Context context;
    ImageButton ibLoa;
    private List<Word> wordList;
    private DatabaseHelper dbHelper;
    TextView tvA, tvB, tvC, tvD;
    TextView tickA, tickB, tickC, tickD;
    private int isSoundon = 1 ;
    private String chooseanswer="";
    private int numberquestion=1;
    private static final int MY_DATA_CHECK_CODE = 1234;
    String toSpeak = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer);
        dbHelper = new DatabaseHelper(this);
        File database = getApplicationContext().getDatabasePath(dbHelper.DB_NAME);
        if (false == database.exists()) {
            dbHelper.getReadableDatabase();
            if (copyDataBase(this)) {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        wordList = dbHelper.getListWord();
        Log.d("Size", "" + wordList.size());
        Word w = wordList.get(1);

        connectView();
        showAnswers(w);

//        Nếu có 3 dòng này thì tiếng sẽ phát trước khi activity này đc hiện ra.
//        Intent checkIntent = new Intent();
//        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

        toSpeak = tvQues.getText().toString();
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                t1.setLanguage(Locale.JAPANESE);
                // dòng t1. speak trong hàm onInit để phát âm luôn ko phải đợi click
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        ibLoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundEvent();
            }
        });
        tvA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aEvent();
            }
        });
    }

    public void onPause() {
        if (t1 != null) {
            t1.stop();
//            t1.shutdown();
        }
        super.onPause();
    }


    public void connectView() {
        tvQues = (TextView) findViewById(R.id.tvQues);
        ibLoa = (ImageButton) findViewById(R.id.ibLoa);
        tvA = (TextView) findViewById(R.id.tvA);
        tvB = (TextView) findViewById(R.id.tvB);
        tvC = (TextView) findViewById(R.id.tvC);
        tvD = (TextView) findViewById(R.id.tvD);
        tickA = (TextView) findViewById(R.id.textView_tickA);
        tickB = (TextView) findViewById(R.id.textView_tickB);
        tickC = (TextView) findViewById(R.id.textView_tickC);
        tickD = (TextView) findViewById(R.id.textView_tickD);
    }

    public void showAnswers(Word word){
        tvQues.setText(word.getWord().toString());
        tvA.setText(word.getA().toString());
        tvB.setText(word.getB().toString());
        tvC.setText(word.getC().toString());
        tvD.setText(word.getMean().toString());
    }

    public void soundEvent(){
        if(isSoundon==1){
            isSoundon=0;
            ibLoa.setImageResource(R.drawable.ic_volume_off);
            onPause();
        } else{
            isSoundon=1;
            ibLoa.setImageResource(R.drawable.ic_volume_down_black_24dp);
            sound();
        }
    }

    public void sound(){
        String toSpeak = tvQues.getText().toString();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void setStatusOnClickABCD(boolean status){
        //status=false: remove onClick from button
        //status=true:
        tvA.setClickable(status);
        tvB.setClickable(status);
        tvC.setClickable(status);
        tvD.setClickable(status);
    }

    public void aEvent(){
        //setStatusOnClickABCD(false);
        chooseanswer="A";
        tickA.setBackgroundResource(R.drawable.check);
        tvA.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview_true));
//        getAnswer(chooseanswer, tvA,tickA);
    }

    public void bEvent(){
        //setStatusOnClickABCD(false);
        chooseanswer="B";
        tickB.setBackgroundResource(R.drawable.check);
        tvB.setBackgroundResource(R.drawable.border_textview);
    }

    public void cEvent(){
        //setStatusOnClickABCD(false);
        chooseanswer="C";
        //getAnswer(chooseanswer, tvC, tickC);
    }

    public void dEvent(){
        //setStatusOnClickABCD(false);
        chooseanswer="D";
        //getAnswer(chooseanswer, tvD, tickD);
    }

    public void getAnswer(final String chooseanswer, TextView backgroundanswer, TextView tick){
//        countDownTimer.cancel();
//
//        if(trueAnswer(chooseanswer)) {
//            //change layout button
//            setCurrentScreen(backgroundanswer,tick,true);
//
//            if(numberquestion<10) {
//                //time wait before next question
//                timeHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                        switch (chooseanswer) {
                            case "A":
                                tickA.setBackgroundResource(0);
                                tvA.setBackgroundResource(R.drawable.border_textview);
                                break;
                            case "B":
                                tickB.setBackgroundResource(0);
                                tvB.setBackgroundResource(R.drawable.border_textview);
                                break;
                            case "C":
                                tickC.setBackgroundResource(0);
                                tvC.setBackgroundResource(R.drawable.border_textview);
                                break;
                            case "D":
                                tickD.setBackgroundResource(0);
                                tvD.setBackgroundResource(R.drawable.border_textview);
                                break;
                        }
//                        toNextQuestion();
//                    }
//                }, 1000);
//            }else{
//                stopPlaying();
//            }

//        }else{
//            setCurrentScreen(backgroundanswer,tick,false);
//            countDownTimer.cancel();
//            toNextQuestion();
//        }
    }
    private boolean copyDataBase(Context context) {

        try {
            //Open your local db as the input stream
            InputStream myInput = context.getAssets().open(dbHelper.DB_NAME);

            // Path to the just created empty db
            String outFileName = dbHelper.DB_PATH + dbHelper.DB_NAME;

            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}