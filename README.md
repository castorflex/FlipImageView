FlipImageView
==================

Description
-----------
Small android lib allowing you to flip an imageview easily, by extending FlipImageView.

This lib is based on the FlipAnimator by coomar. All the credits goes to him.

Check the sample app here https://play.google.com/store/apps/details?id=fr.castorflex.android.flipimageview

XML Usage
---------
```xml
<fr.castorflex.android.flipimageview.library.FlipImageView
       xmlns:fiv="http://schemas.android.com/apk/res-auto"
       android:id="@+id/imageview"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:src="@drawable/YOUR_DEFAULT_DRAWABLE"
       fiv:flipDrawable="@drawable/YOUR_FLIPPED_DRAWABLE"
       fiv:flipDuration="YOUR_DURATION_IN_MS"
       fiv:flipInterpolator="@android:anim/YOUR_INTERPOLATOR"
       fiv:flipRotations="none|x|y|z"
       fiv:isAnimated="true|false"
       fiv:isFlipped="true|false"/>
```

License
-------

```
"THE BEER-WARE LICENSE" (Revision 42):
You can do whatever you want with this stuff.
If we meet some day, and you think this stuff is worth it, you can buy me a beer in return.
```
