package project.sarah.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by sarah on 11/10/17.
 */

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Double final_p=intent.getDoubleExtra("final",0.0);
        TextView tv_final=(TextView)findViewById(R.id.textView4);
        tv_final.setText(final_p+"");
    }
}
