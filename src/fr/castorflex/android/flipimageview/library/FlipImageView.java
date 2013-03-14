package fr.castorflex.android.flipimageview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

import fr.castorflex.android.flipimageview.R;

/**
 * Created with IntelliJ IDEA. User: castorflex Date: 30/12/12 Time: 16:25
 */
public class FlipImageView extends ImageView implements View.OnClickListener,
        Animation.AnimationListener {

    private static final int FLAG_ROTATION_X = 1 << 0;

    private static final int FLAG_ROTATION_Y = 1 << 1;

    private static final int FLAG_ROTATION_Z = 1 << 2;

    private static final Interpolator fDefaultInterpolator = new DecelerateInterpolator();

    private static int sDefaultDuration;

    private static int sDefaultRotations;

    private static boolean sDefaultAnimated;

    private static boolean sDefaultFlipped;

    private static boolean sDefaultIsRotationReversed;


    public interface OnFlipListener {

        public void onClick(boolean flipped);

        public void onFlipStart();

        public void onFlipEnd();
    }

    private OnFlipListener mListener;

    private boolean mIsFlipped;

    private boolean mIsDefaultAnimated;

    private Drawable mDrawable;

    private Drawable mFlippedDrawable;

    private FlipAnimator mAnimation;

    private boolean mIsRotationXEnabled;

    private boolean mIsRotationYEnabled;

    private boolean mIsRotationZEnabled;
	
	private boolean mIsFlipping;

    private boolean mIsRotationReversed;

    public FlipImageView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public FlipImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public FlipImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        sDefaultDuration = context.getResources().getInteger(R.integer.default_fiv_duration);
        sDefaultRotations = context.getResources().getInteger(R.integer.default_fiv_rotations);
        sDefaultAnimated = context.getResources().getBoolean(R.bool.default_fiv_isAnimated);
        sDefaultFlipped = context.getResources().getBoolean(R.bool.default_fiv_isFlipped);
        sDefaultIsRotationReversed = context.getResources().getBoolean(R.bool.default_fiv_isRotationReversed);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlipImageView, defStyle, 0);
        mIsDefaultAnimated = a.getBoolean(R.styleable.FlipImageView_isAnimated, sDefaultAnimated);
        mIsFlipped = a.getBoolean(R.styleable.FlipImageView_isFlipped, sDefaultFlipped);
        mFlippedDrawable = a.getDrawable(R.styleable.FlipImageView_flipDrawable);
        int duration = a.getInt(R.styleable.FlipImageView_flipDuration, sDefaultDuration);
        int interpolatorResId = a.getResourceId(R.styleable.FlipImageView_flipInterpolator, 0);
        Interpolator interpolator = interpolatorResId > 0 ? AnimationUtils
                .loadInterpolator(context, interpolatorResId) : fDefaultInterpolator;
        int rotations = a.getInteger(R.styleable.FlipImageView_flipRotations, sDefaultRotations);
        mIsRotationXEnabled = (rotations & FLAG_ROTATION_X) != 0;
        mIsRotationYEnabled = (rotations & FLAG_ROTATION_Y) != 0;
        mIsRotationZEnabled = (rotations & FLAG_ROTATION_Z) != 0;

        mDrawable = getDrawable();
        mIsRotationReversed = a.getBoolean(R.styleable.FlipImageView_reverseRotation, sDefaultIsRotationReversed);

        mAnimation = new FlipAnimator();
        mAnimation.setAnimationListener(this);
        mAnimation.setInterpolator(interpolator);
        mAnimation.setDuration(duration);

        setOnClickListener(this);

        setImageDrawable(mIsFlipped ? mFlippedDrawable : mDrawable);
        mIsFlipping = false;

        a.recycle();
    }

    public void setFlippedDrawable(Drawable flippedDrawable){
        mFlippedDrawable = flippedDrawable;
        if(mIsFlipped) setImageDrawable(mFlippedDrawable);
    }

    public void setDrawable(Drawable drawable){
        mDrawable = drawable;
        if(!mIsFlipped) setImageDrawable(mDrawable);
    }

    public boolean isRotationXEnabled() {
        return mIsRotationXEnabled;
    }

    public void setRotationXEnabled(boolean enabled) {
        mIsRotationXEnabled = enabled;
    }

    public boolean isRotationYEnabled() {
        return mIsRotationYEnabled;
    }

    public void setRotationYEnabled(boolean enabled) {
        mIsRotationYEnabled = enabled;
    }

    public boolean isRotationZEnabled() {
        return mIsRotationZEnabled;
    }

    public void setRotationZEnabled(boolean enabled) {
        mIsRotationZEnabled = enabled;
    }

    public FlipAnimator getFlipAnimation() {
        return mAnimation;
    }

    public void setInterpolator(Interpolator interpolator) {
        mAnimation.setInterpolator(interpolator);
    }

    public void setDuration(int duration) {
        mAnimation.setDuration(duration);
    }

    public boolean isFlipped() {
        return mIsFlipped;
    }

    public boolean isFlipping() {
        return mIsFlipping;
    }

    public boolean isRotationReversed(){
        return mIsRotationReversed;
    }

    public void setRotationReversed(boolean rotationReversed){
        mIsRotationReversed = rotationReversed;
    }

    public boolean isAnimated() {
        return mIsDefaultAnimated;
    }

    public void setAnimated(boolean animated) {
        mIsDefaultAnimated = animated;
    }

    public void setFlipped(boolean flipped) {
        setFlipped(flipped, mIsDefaultAnimated);
    }

    public void setFlipped(boolean flipped, boolean animated) {
        if (flipped != mIsFlipped) {
            toggleFlip(animated);
        }
    }

    public void toggleFlip() {
        toggleFlip(mIsDefaultAnimated);
    }

    public void toggleFlip(boolean animated) {
        if (animated) {
            mAnimation.setToDrawable(mIsFlipped ? mDrawable : mFlippedDrawable);
            startAnimation(mAnimation);
        } else {
            setImageDrawable(mIsFlipped ? mDrawable : mFlippedDrawable);
        }
        mIsFlipped = !mIsFlipped;
    }


    public void setOnFlipListener(OnFlipListener listener) {
        mListener = listener;
    }


    @Override
    public void onClick(View v) {
        toggleFlip();
        if (mListener != null) {
            mListener.onClick(mIsFlipped);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (mListener != null) {
            mListener.onFlipStart();
        }
		mIsFlipping = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (mListener != null) {
            mListener.onFlipEnd();
        }
		mIsFlipping = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    /**
     * Animation part All credits goes to coomar
     */
    public class FlipAnimator extends Animation {

        private Camera camera;

        private Drawable toDrawable;

        private float centerX;

        private float centerY;

        private boolean visibilitySwapped;

        public void setToDrawable(Drawable to) {
            toDrawable = to;
            visibilitySwapped = false;
        }

        public FlipAnimator() {
            setFillAfter(true);
        }


        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            camera = new Camera();
            this.centerX = width / 2;
            this.centerY = height / 2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            // Angle around the y-axis of the rotation at the given time. It is
            // calculated both in radians and in the equivalent degrees.
            final double radians = Math.PI * interpolatedTime;
            float degrees = (float) (180.0 * radians / Math.PI);
            
            if(mIsRotationReversed){ 
            	degrees = -degrees;
            }

            // Once we reach the midpoint in the animation, we need to hide the
            // source view and show the destination view. We also need to change
            // the angle by 180 degrees so that the destination does not come in
            // flipped around. This is the main problem with SDK sample, it does not
            // do this.
            if (interpolatedTime >= 0.5f) {
            	if(mIsRotationReversed){ degrees += 180.f; } else{ degrees -= 180.f; }

                if (!visibilitySwapped) {
                    setImageDrawable(toDrawable);
                    visibilitySwapped = true;
                }
            }

            final Matrix matrix = t.getMatrix();

            camera.save();
            camera.translate(0.0f, 0.0f, (float) (150.0 * Math.sin(radians)));
            camera.rotateX(mIsRotationXEnabled ? degrees : 0);
            camera.rotateY(mIsRotationYEnabled ? degrees : 0);
            camera.rotateZ(mIsRotationZEnabled ? degrees : 0);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }
}

