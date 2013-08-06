package fr.castorflex.android.flipimageview.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import fr.castorflex.android.flipimageview.R;
import fr.castorflex.android.flipimageview.library.FlipImageView;

public class SampleActivity extends Activity implements FlipImageView.OnFlipListener,
        SeekBar.OnSeekBarChangeListener {

    private static final String[] fData = new String[]{
            "Decelerate",
            "Accelerate",
            "AccelerateDecelerate",
            "Bounce",
            "Overshoot",
            "AnticipateOvershoot"

    };

    private static final Interpolator[] fInterpolators = new Interpolator[]{
            new DecelerateInterpolator(),
            new AccelerateInterpolator(),
            new AccelerateDecelerateInterpolator(),
            new BounceInterpolator(),
            new OvershootInterpolator(),
            new AnticipateOvershootInterpolator()
    };

    private SeekBar mSeekBar;

    private Spinner mSpinner;

    private TextView mTextViewDuration;

    private FlipImageView mFlipImageView;

    private CheckBox mCheckBoxX;

    private CheckBox mCheckBoxY;

    private CheckBox mCheckBoxZ;
    
    private CheckBox mCheckBoxReverse;

    private TextView mTextViewAnimationListener;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTextViewAnimationListener = (TextView) findViewById(R.id.textview);
        mFlipImageView = (FlipImageView) findViewById(R.id.imageview);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mTextViewDuration = (TextView) findViewById(R.id.textview_duration);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mCheckBoxX = (CheckBox) findViewById(R.id.checkedtextview_x);
        mCheckBoxY = (CheckBox) findViewById(R.id.checkedtextview_y);
        mCheckBoxZ = (CheckBox) findViewById(R.id.checkedtextview_z);
        mCheckBoxReverse = (CheckBox) findViewById(R.id.checkedtextview_reverse);

        mSpinner.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, fData));

        mSeekBar.setOnSeekBarChangeListener(this);

        mFlipImageView.setOnFlipListener(this);
    }

    /////////////////////FLIP IMAGE VIEW///////////////////

    @Override
    public void onClick(FlipImageView view) {
        mFlipImageView.setInterpolator(fInterpolators[mSpinner.getSelectedItemPosition()]);
        mFlipImageView.setDuration(mSeekBar.getProgress());
        mFlipImageView.setRotationXEnabled(mCheckBoxX.isChecked());
        mFlipImageView.setRotationYEnabled(mCheckBoxY.isChecked());
        mFlipImageView.setRotationZEnabled(mCheckBoxZ.isChecked());
        mFlipImageView.setRotationReversed(mCheckBoxReverse.isChecked());
    }

    @Override
    public void onFlipStart(FlipImageView view) {
        mTextViewAnimationListener.setText("OnFlipStart");
    }

    @Override
    public void onFlipEnd(FlipImageView view) {
        mTextViewAnimationListener.setText("OnFlipEnd");
    }

    ////////////////////////SEEK BAR/////////////////////////

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mTextViewDuration.setText("" + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
