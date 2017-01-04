package net.elsehrawy.questions_game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Questions_Result extends AppCompatActivity {
        TextView TxT17,TxT18,TxT19;
        String theresult1,theresult2;
        float percentage,i,i2;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_questions__result);
            TxT17=(TextView)findViewById(R.id.Txt17);
            TxT18=(TextView)findViewById(R.id.Txt18);
            TxT19=(TextView)findViewById(R.id.Txt19);
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            String result = extras.getString("resultQuiz");
            String Length = extras.getString("Que_Length");
            i = Integer.parseInt(result);
            i2 = Integer.parseInt(Length);
            percentage = (i/i2)*100;
            preintResult();}

        public void preintResult(){
            if(percentage >= 90 ){theresult1 = "ممتاز ";theresult2 ="انت استاذ";makeSound(R.raw.finishsounds);}
            else if(percentage >= 60 && percentage < 90){    makeSound(R.raw.finishsounds);
                theresult1 = "جيد ";theresult2 ="انت مثقف";}
            else if(percentage >= 40 && percentage < 60){    makeSound(R.raw.finishsounds);
                theresult1 = "مقبول ";theresult2 ="تحتاج لتتعلم اكثر";}
            else if(percentage >= 20 && percentage < 40){theresult1 = "سيئ ";theresult2 ="تحتاج لتتعلم اكثر";}
            else if(percentage >= 0 && percentage < 20){theresult1 = "سيئ جدا";theresult2 ="اتحتاج لتتعلم اكثر";}

            TxT17.setText(((int) percentage)+" %");
            TxT18.setText(theresult1);
            TxT19.setText(theresult2);
        }

        public void GoHome(View view) {
            Intent i =new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }
        public void makeSound(int sound){
            MediaPlayer sijecanj = MediaPlayer.create(Questions_Result.this,sound);
            sijecanj.start();
        }

}
