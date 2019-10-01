package cis350.upenn.edu.easyfooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by haile on 3/24/2017.
 */

public class MotivationActivity extends AppCompatActivity {

    protected MotivationView motivationView;
    protected String quote;
    protected TextView textView_quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation);
        motivationView = new MotivationView(this);

        textView_quote = (TextView) findViewById(R.id.quote);
        String[] quotes = new String[22];
        quotes[0] = "\"If you don’t do what’s best for your body, you’re the one who comes up on the short end.\" - Julius Erving";
        quotes[1] = "\"A man’s health can be judged by which he takes two at a time – pills or stairs.\" - Joan Welsh";
        quotes[2] = "\"Those who think they have not time for bodily exercise will sooner or later have to find time for illness.\" - Edward Stanley";
        quotes[3] = "\"A man too busy to take care of his health is like a mechanic too busy to take care of his tools.\" - Unknown";
        quotes[4] = "\"Take care of your body. It’s the only place you have to live.\" - Jim Rohn";
        quotes[5] = "\"The difference between someone who is in shape, and someone who is not in shape, is the individual who is in shape works out even when they do not want to.\" - Unknown";
        quotes[6] = "\"A healthy happy life is a sacred bliss!\" ― Lailah Gifty Akita";
        quotes[7] = "\"A healthy body is a platform for flourishing a healthy mind.\" ― Pawan Mishra";
        quotes[8] = "\"A fit, healthy body—that is the best fashion statement\" ― Jess C. Scott";
        quotes[9] = "\"Getting fit is all about mind over matter. I don't mind, so it doesn't matter.\" - Adam Hargreaves";
        quotes[10] = "\"Will is a skill.\" - Jillian Michaels";
        quotes[11] = "\"Weight loss doesn't begin in the gym with a dumb bell; it starts in your head with a decision.\" - Toni Sorenson";
        quotes[12] = "\"There’s no secret formula. I lift heavy, work hard, and aim to be the best.\"- Ronnie Coleman";
        quotes[13] = "\"You’re going to have to let it hurt. Let it suck. The harder you work, the better you will look. Your appearance isn’t parallel to how heavy you lift, it’s parallel to how hard you work.\" - Joe Manganiello";
        quotes[14] = "\"You have to push past your perceived limits, push past that point you thought was as far as you can go.\" - Drew Brees";
        quotes[15] = "\"I know that if I set my mind to something, even if people are saying I can’t do it, I will achieve it.\" - David Beckham";
        quotes[16] = "\"The greatest wealth is health.\" - Virgil";
        quotes[17] = "\"Either you run the day or the day runs you.\" –Jim Rohn";
        quotes[18] = "\"Take care of your body. It’s the only place you have to live.\"  - Jim Rohn";
        quotes[19] = "\"An apple a day keeps the doctor away.\"  - Proverb";
        quotes[20] = "\"Don’t wish for it. Work for it.\" – Unknown";
        quotes[21] = "\"He who enjoys good health is rich, though he knows it not.\" - Italian Proverb";

        int rand = (int) Math.floor(Math.random() * 22);
        textView_quote.setText(quotes[rand]);
    }
    public void onClick(View view) {
        /*if (view.getId() == R.id.main) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }*/
    }
}
