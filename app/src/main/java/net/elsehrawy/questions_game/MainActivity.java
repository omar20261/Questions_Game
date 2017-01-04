package net.elsehrawy.questions_game;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

        TextView TxT1,TxT2,TxT3,TxT4,TxT5,TxT6;
        JSONArray jArray;
        Boolean firstAnswer = true;
        String UserChooses,theRightAnswer=null;
        int postion=0,max_num=15;
        public  int RightAnswers=0;
        private Handler mHandler;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            TxT1 = (TextView)findViewById(R.id.Txt1);
            TxT2 = (TextView)findViewById(R.id.Txt2);
            TxT3 = (TextView)findViewById(R.id.Txt3);
            TxT4 = (TextView)findViewById(R.id.Txt4);
            TxT5 = (TextView)findViewById(R.id.Txt5);
            TxT6 = (TextView)findViewById(R.id.Txt6);
            ReadData();

            mHandler = new Handler();}


        public void ReadData()
        {
            try {
                InputStream InSt = getAssets().open("quiz.json");
                BufferedReader in= new BufferedReader(new InputStreamReader(InSt, "UTF-8"));
                String str ;
                StringBuffer buffer = new StringBuffer();
                while ((str=in.readLine()) != null)
                { buffer.append(str);}
                JSONObject myJ = null;
                try {myJ = new JSONObject(buffer.toString());
                    jArray = myJ.getJSONArray("questions");
                    gettargetdata();}
                catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public void gettargetdata()
        { firstAnswer = true;
            try {
                Random rand2 = new Random();
                int n2 = rand2.nextInt((jArray.length()-1));
                JSONObject item = jArray.getJSONObject(n2);
                TxT1.setText(item.getString("question"));
                String [] chooses={"rightAnswer","falseAnswer1","falseAnswer2","falseAnswer3"};
                Random rand = new Random();
                int n1 = rand.nextInt(4);
                TxT2.setText(item.getString(chooses[(n1 + 1) % 4]));
                TxT3.setText(item.getString(chooses[(n1 + 2) % 4]));
                TxT4.setText(item.getString(chooses[(n1 + 3) % 4]));
                TxT5.setText(item.getString(chooses[(n1 + 4) % 4]));
                TxT6.setText(getResources().getString(R.string.NumofQues)+" "+(postion+1) + " / "+max_num/* +" موقع السؤال "+n2+ " الطول"+jArray.length()*/);
                theRightAnswer = item.getString("rightAnswer");
                refrshbtnBG();
                jArray.remove(n2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void ChooseisThis(View view) {
            UserChooses = ((TextView)view).getText().toString();
            if(theRightAnswer != null || UserChooses!= null){
                if(UserChooses == theRightAnswer)
                {AnswerIsRight(view);}else{AnswerIsWrong(view);}
            }
        }

        public void AnswerIsRight(final View v)
        {v.setBackgroundResource( R.drawable.raghit_answer);
            TxT2.setEnabled(false);
            TxT3.setEnabled(false);
            TxT4.setEnabled(false);
            TxT5.setEnabled(false);
            makeSound(R.raw.correct);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (firstAnswer == true){RightAnswers++;}
                    if(postion < (max_num-1)){postion++;} else{questionIsfinished();}
                    gettargetdata();
                    v.setEnabled(true);
                }
            }, 2000);
        }

        public void AnswerIsWrong(View v)
        { firstAnswer = false;
            v.setBackgroundResource( R.drawable.wrong_answer);
            v.setEnabled(false);
            makeSound(R.raw.wronganswer);}

        public void questionIsfinished()
        {   Intent intent = new Intent(this,Questions_Result.class);
            Bundle extras = new Bundle();
            String strKey = "resultQuiz";
            String strKey2 = "Que_Length";
            extras.putString(strKey, RightAnswers+"");
            extras.putString(strKey2, max_num+"");
            intent.putExtras(extras);
            startActivity(intent);
            finish();
        }

        public void refrshbtnBG(){
            TxT2.setBackgroundResource(R.drawable.btn_choose_anim);
            TxT3.setBackgroundResource(R.drawable.btn_choose_anim);
            TxT4.setBackgroundResource(R.drawable.btn_choose_anim);
            TxT5.setBackgroundResource(R.drawable.btn_choose_anim);
            TxT2.setEnabled(true);
            TxT3.setEnabled(true);
            TxT4.setEnabled(true);
            TxT5.setEnabled(true);
        }
        public void makeSound(int sound){
            MediaPlayer sijecanj = MediaPlayer.create(MainActivity.this,sound);
            sijecanj.start();
            sijecanj.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                };
            });    }


}
