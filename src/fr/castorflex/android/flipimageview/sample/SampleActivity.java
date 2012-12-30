package fr.castorflex.android.flipimageview.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import fr.castorflex.android.flipimageview.R;
import fr.castorflex.android.flipimageview.library.FlipImageView;

public class SampleActivity extends Activity implements FlipImageView.OnFlipListener {

    TextView mTextView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTextView = (TextView) findViewById(R.id.textview);
        FlipImageView imageView = (FlipImageView) findViewById(R.id.imageview);

        imageView.setOnFlipListener(this);
    }

    @Override
    public void onClick(boolean flipped) {
        Toast.makeText(this, "OnClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFlipStart() {
        mTextView.setText("OnFlipStart");
    }

    @Override
    public void onFlipEnd() {
        mTextView.setText("OnFlipEnd");
    }
}
