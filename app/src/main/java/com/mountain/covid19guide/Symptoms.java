package com.mountain.covid19guide;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

public class Symptoms extends AppCompatActivity {

    TextView difficulty;
    TextView chest;
    TextView loss;
    TextView tiredness;
    TextView dry;
    TextView fever;
    TextView aches;
    TextView sore;
    TextView diarrhoea;
    TextView conjunct;
    TextView headache;
    TextView taste;
    TextView rush;

    TextView topic;
    TextView body;

    Button backButton;

    ImageView image;

    VideoView videoView;

    LinearLayout layout;

    ScrollView pointsScrollView;
    ScrollView detailsScrollView;

    MediaController controller;



    @SuppressLint("SetTextI18n")
    public void details(View view){

        pointsScrollView.setVisibility(View.GONE);
        backButton.setVisibility(View.VISIBLE);
        detailsScrollView.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);

        if (view.getId() == R.id.difficulty){

            topic.setText("Difficulty in Breathing");
            image.setImageResource(R.drawable.difficulty_in_breathing);
            body.setText("One of the severe symptoms\n" +
                    "A person who is having difficulty breathing feels short of breath, has trouble inhaling or exhaling, or feels as though they cannot get enough oxygen.");

        }

        if (view.getId() == R.id.chest){

            topic.setText("Chest Pains");
            image.setImageResource(R.drawable.chest);
            body.setText("One of the severe symptoms\n" +
                    "Usually causes a sudden, sharp, stabbing pain that gets worse when you breathe deeply or lie down.");


        }

        if (view.getId() == R.id.loss){

            topic.setText("Loss of Speech/Movement");
            image.setImageResource(R.drawable.loss);
            body.setText("One of the severe symptoms\n" +
                    "Inability to speak, also finding it difficult to move your body.");


        }

        if (view.getId() == R.id.tiredness){

            topic.setText("Tiredness");
            image.setImageResource(R.drawable.tiredness);
            body.setText("One of the most common symptoms\n" +
                    " A feeling of a lessened capacity for work and reduced efficiency of accomplishment, usually accompanied by a sense of weariness and fatigue. (Needing to rest or sleep)");


        }

        if (view.getId() == R.id.dry){

            topic.setText("Dry Cough");
            image.setImageResource(R.drawable.dry);
            body.setText("One of the most common symptoms\n" +
                    "A sudden, forceful hacking sound to release air and clear an irritation in the throat or airway.");


        }

        if (view.getId() == R.id.fever){

            topic.setText("Fever");
            image.setImageResource(R.drawable.fever);
            body.setText("One of the most common symptoms\n" +
                    "");


        }

        if (view.getId() == R.id.aches ||
                view.getId() == R.id.sore ||
                view.getId() == R.id.diarrhoea ||
                view.getId() == R.id.conjunct ||
                view.getId() == R.id.headache ||
                view.getId() == R.id.taste ||
                view.getId() == R.id.rash){

            topic.setText("Less or Mild Symptoms");
            image.setImageResource(R.drawable.less);
            body.setText("People with mild symptoms who are otherwise healthy should manage their symptoms at home.\n" +
                    "On average it takes 5–6 days from when someone is infected with the virus for symptoms to show, however it can take up to 14 days. \n\n" +
                    "Aches and Pain: You can use aches and pains to refer in a general way to any minor pains that you feel in your body. It seems to ease all the aches and pains of a hectic and tiring day.\n\n" +
                    "Sore Throat: A sore throat is pain, scratchiness or irritation of the throat that often worsens when you swallow. The most common cause of a sore throat (pharyngitis) is a viral infection, such as a cold or the flu. A sore throat caused by a virus resolves on its own.\n\n" +
                    "Diarrhoea: Diarrhoea is when your bowel movements become loose or watery. The definition of diarrhoea is passing loose or watery bowel movements 3 or more times in a day (or more frequently than usual). \n\n" +
                    "Conjunctivitis: Conjunctivitis is an inflammation or infection of the transparent membrane (conjunctiva) that lines your eyelid and covers the white part of your eyeball." +
                    "Headache: A continuous pain in the head. \n\n" +
                    "Loss of taste or smell: For taste, the inability to detect sweetness, sourness, bitterness, saltiness, and pleasant/savory taste. For smell, is the loss of the ability to detect one or more smells. \n\n" +
                    "A rash on skin or discolouration of fingers or toes: A rash is a change of the human skin which affects its color, appearance, or texture. \n\n" +
                    "Do not forget these are all less or mild symptoms.");


        }


    }


    public void setBackButton(View view){

        pointsScrollView.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.INVISIBLE);
        detailsScrollView.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);

        videoView.pause();


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        topic = findViewById(R.id.topic);
        body = findViewById(R.id.body);

       difficulty = findViewById(R.id.difficulty);
       chest = findViewById(R.id.chest);
       loss = findViewById(R.id.loss);
       tiredness = findViewById(R.id.tiredness);
       dry = findViewById(R.id.dry);
       fever = findViewById(R.id.fever);
       aches = findViewById(R.id.aches);
       sore = findViewById(R.id.sore);
       diarrhoea = findViewById(R.id.diarrhoea);
       conjunct = findViewById(R.id.conjunct);
       headache = findViewById(R.id.headache);
       taste = findViewById(R.id.taste);
       rush = findViewById(R.id.rash);


       pointsScrollView = findViewById(R.id.pointsScrollView);
       detailsScrollView = findViewById(R.id.detailsScrollView);

       backButton = findViewById(R.id.backButton);

       image = findViewById(R.id.image);

       videoView = findViewById(R.id.videoView);

       controller = new MediaController(this);

       layout = findViewById(R.id.layout);

       videoView.setVideoPath("android.resource://" + getOpPackageName() + "/" + R.raw.symptoms);

       controller.setAnchorView(videoView);
       videoView.setMediaController(controller);



    }
}
