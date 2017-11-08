package dang.daniel.project.ames.ac.nz.car_landscape;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Declare variables and objects
    //horseman
    private ImageView horseman1, horseman2, horseman3;
    private ImageView backgroundImage;
    private boolean isShowingBackground1;

    //Animation objects: ObjectAnimator and AnimationDrawable
    //Animator objects for horseman1/2/3
    private ObjectAnimator animatedBackgroundImage, horseman1Run, horseman2Run, horseman3Run;
    private AnimationDrawable horseman1Animation, horseman2Animation, horseman3Animation;

    //Set the move in X-axis
    final String PROPERTY_NAME = "x";

    //Random variable used to determine the horseman speed
    private Random randomGenerator = new Random(System.currentTimeMillis());

    //Start, Reset button
    private Button startBtn, resetBtn;
    private boolean readyPlay = true;

    //TextView to display the final places
    private TextView redLane, greenLane, blueLane;
    int horseman1Speed, horseman2Speed, horseman3Speed;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Reset the horse race
        resetLayout();

        //Set up the race tracks ready to start playing
        setTracks();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Reset
    public void resetLayout() {
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Call again the initial layout: activity_main
        setContentView(R.layout.activity_main);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Find references and do casting
        //background
        backgroundImage = (ImageView) findViewById(R.id.backGround);
        //horsemen
        horseman1 = (ImageView) findViewById(R.id.horseman1);
        horseman2 = (ImageView) findViewById(R.id.horseman2);
        horseman3 = (ImageView) findViewById(R.id.horseman3);
        //result TextView
        redLane = (TextView) findViewById(R.id.redLaneResult);
        greenLane = (TextView) findViewById(R.id.greenLaneResult);
        blueLane = (TextView) findViewById(R.id.blueLaneResult);

        //Start & Reset button
        startBtn = (Button) findViewById(R.id.startBtn);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        //Set listener for Start & Reset button
        startBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Set the result TextView INVISIBLE
        redLane.setVisibility(View.INVISIBLE);
        greenLane.setVisibility(View.INVISIBLE);
        blueLane.setVisibility(View.INVISIBLE);

        //Anytime reset the layout, the game is ready to play
        readyPlay = true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //setTracks()
    public void setTracks() {
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Setup background animation
        //Set the image source
        backgroundImage.setImageResource(R.drawable.background1);
        //Set the background image showing is true
        isShowingBackground1 = true;
        //Set animated background image in X-axis from coordinates from 2500 to -1000
        animatedBackgroundImage = ObjectAnimator.ofFloat(backgroundImage, PROPERTY_NAME, 2500, -1000);
        //Set other ObjectAnimator properties
        animatedBackgroundImage.setDuration(4000);//4 seconds
        animatedBackgroundImage.setRepeatMode(ValueAnimator.RESTART);
        animatedBackgroundImage.setRepeatCount(ValueAnimator.INFINITE);
        animatedBackgroundImage.setInterpolator(new LinearInterpolator());

        ///////////////////////////////////////////////////
        //Set listener for ObjectAnimator: AnimatorListener
        animatedBackgroundImage.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //Not used
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Not used
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                //Not used
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                //Set the background image to use
                if (isShowingBackground1) {
                    backgroundImage.setImageResource(R.drawable.background2);
                    isShowingBackground1 = false;
                } else {
                    backgroundImage.setImageResource(R.drawable.background1);
                    isShowingBackground1 = true;
                }
            }
        });


        ///////////////////////////////////////////////////////////////////////////////////////////
        //Set the animation properties for 3 horsemen
        //horseman1
        //Remove the initial image display on ImageView
        horseman1.setImageResource(0);
        //Set the AnimationDrawable file as the background resource for the View
        horseman1.setBackgroundResource(R.drawable.horseman_animation);
        //Create an AnimationDrawable object based on this background
        horseman1Animation = (AnimationDrawable) horseman1.getBackground();
        //Check whether the frames are running, if yes, stop running
        if (horseman1Animation.isRunning()) {
            //Start the animation
            horseman1Animation.stop();
        }

        //horseman2
        //Remove the initial image display on ImageView
        horseman2.setImageResource(0);
        //Set the AnimationDrawable file as the background resource for the View
        horseman2.setBackgroundResource(R.drawable.horseman_animation);
        //Create an AnimationDrawable object based on this background
        horseman2Animation = (AnimationDrawable) horseman2.getBackground();
        //Check whether the frames are running, if yes, stop running
        if (horseman2Animation.isRunning()) {
            //Start the animation
            horseman2Animation.stop();
        }

        //horseman3
        //Remove the initial image display on ImageView
        horseman3.setImageResource(0);
        //Set the AnimationDrawable file as the background resource for the View
        horseman3.setBackgroundResource(R.drawable.horseman_animation);
        //Create an AnimationDrawable object based on this background
        horseman3Animation = (AnimationDrawable) horseman3.getBackground();
        //Check whether the frames are running, if yes, stop running
        if (horseman3Animation.isRunning()) {
            //Start the animation
            horseman3Animation.stop();
        }

        ////////////////////////////////////////////////////////////////////////////////////
        //Make horsemen move forwards in different speed
        //Decide  speeds of horsemen by using random variable
        horseman1Speed = randomGenerator.nextInt(5) + 3;
        horseman2Speed = randomGenerator.nextInt(5) + 3;
        horseman3Speed = randomGenerator.nextInt(5) + 3;

        //Determine the places (results)
        int fastestHorse = Math.min(horseman1Speed, Math.min(horseman2Speed, horseman3Speed));
        if (horseman1Speed == fastestHorse) {
            //Horseman 1 (Red Lane) is placed first
            redLane.setText("Red lane (horse 1): " + "First");
            if (horseman2Speed <= horseman3Speed) {
                //Horseman 2 is placed second & Horseman 3 is placed third
                greenLane.setText("Green lane (horse 2): " + "Second");
                blueLane.setText("Blue lane (horse 3): " + "Third");
            } else {
                //Horseman 3 is placed second & Horseman 2 is placed third
                greenLane.setText("Green lane (horse 2): " + "Third");
                blueLane.setText("Blue lane (horse 3): " + "Second");
            }
        } else if (horseman2Speed == fastestHorse) {
            //Horseman 2 (Green Lane) is placed first
            greenLane.setText("Green lane (horse 2): " + "First");
            if (horseman1Speed <= horseman3Speed) {
                //Horseman 1 is placed second & Horseman 3 is placed third
                redLane.setText("Red lane (horse 1): " + "Second");
                blueLane.setText("Blue lane (horse 3): " + "Third");
            } else {
                //Horseman 3 is placed second & Horseman 1 is placed third
                redLane.setText("Red lane (horse 1): " + "Third");
                blueLane.setText("Blue lane (horse 3): " + "Second");
            }
        } else {
            //Horseman 3 (Blue Lane) is placed first
            blueLane.setText("Blue lane (horse 3): " + "First");
            if (horseman1Speed <= horseman2Speed) {
                //Horseman 1 is placed second & Horseman 2 is placed third
                redLane.setText("Red lane (horse 1): " + "Second");
                greenLane.setText("Green lane (horse 2): " + "Third");
            } else {
                //Horseman 1 is placed third & Horseman 2 is placed second
                redLane.setText("Red lane (horse 1): " + "Third");
                greenLane.setText("Green lane (horse 2): " + "Second");
            }
        }


        ////////////////////////////////////////////////////////////////////////////////////
        //Implement three horsemen moving animation
        //horseman1
        horseman1Run = ObjectAnimator.ofFloat(horseman1, "x", 1500);
        horseman1Run.setDuration(10000 + horseman1Speed * 1000);
        horseman1Run.setRepeatCount(ValueAnimator.INFINITE);
        horseman1Run.setRepeatMode(ValueAnimator.RESTART);
        horseman1Run.setInterpolator(new AccelerateInterpolator());

        //horseman2
        horseman2Run = ObjectAnimator.ofFloat(horseman2, "x", 1500);
        horseman2Run.setDuration(10000 + horseman2Speed * 1000);
        horseman2Run.setRepeatCount(ValueAnimator.INFINITE);
        horseman2Run.setRepeatMode(ValueAnimator.RESTART);
        horseman2Run.setInterpolator(new AccelerateInterpolator());

        //horseman3
        horseman3Run = ObjectAnimator.ofFloat(horseman3, "x", 1500);
        horseman3Run.setDuration(10000 + horseman3Speed * 1000);
        horseman3Run.setRepeatCount(ValueAnimator.INFINITE);
        horseman3Run.setRepeatMode(ValueAnimator.RESTART);
        horseman3Run.setInterpolator(new AccelerateInterpolator());

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Set listener for 3 horsemen: AnimatorListener
        //horseman 1
        horseman1Run.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //No use
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                //Not used
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                //Stop running
                horseman1Run.cancel();
                //Stop background animating
                animatedBackgroundImage.cancel();
                //Check whether the frames are running, if yes, stop running
                if (horseman1Animation.isRunning()) {
                    //Start the animation
                    horseman1Animation.stop();
                }

                //Display the place result
                //Set the result TextView of horseman 1 VISIBLE
                redLane.setVisibility(View.VISIBLE);

                //Keep animated View at the End Position
                //Define the type of the LayoutParams depending on the parent type
                RelativeLayout.LayoutParams horseman1EndPosition = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                //Add other rules to the layoutparams
                horseman1EndPosition.setMargins(0, 0, 0, 25);//Left-Top-Right-Bottom
                horseman1EndPosition.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                horseman1EndPosition.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                //Set the end position of horseman
                horseman1.setLayoutParams(horseman1EndPosition);
            }
        });

        //horseman 2
        horseman2Run.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //Not use
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                //Not used
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                //Stop running
                horseman2Run.cancel();
                //Stop background animating
                animatedBackgroundImage.cancel();

                //Display the place result
                //Set the result TextView of horseman 2 VISIBLE
                greenLane.setVisibility(View.VISIBLE);

                //Keep animated View at the End Position
                //Define the type of the LayoutParams depending on the parent type
                RelativeLayout.LayoutParams horseman2EndPosition = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                //Add other rules to the layoutparams
                horseman2EndPosition.setMargins(0, 0, 0, 75);//Left-Top-Right-Bottom
                horseman2EndPosition.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                horseman2EndPosition.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                //Set the end position of horseman
                horseman2.setLayoutParams(horseman2EndPosition);

                //Check whether the frames are running, if yes, stop running
                horseman2Animation.stop();

            }
        });

        //horseman 3
        horseman3Run.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //Not use
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                //Not used
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                //Stop running
                horseman3Run.cancel();
                //Stop background animating
                animatedBackgroundImage.cancel();
                //Check whether the frames are running, if yes, stop running
                if (horseman3Animation.isRunning()) {
                    //Start the animation
                    horseman3Animation.stop();
                }

                //Display the place result
                //Set the result TextView of horseman 3 VISIBLE
                blueLane.setVisibility(View.VISIBLE);

                //Keep animated View at the End Position
                //Define the type of the LayoutParams depending on the parent type
                RelativeLayout.LayoutParams horseman3EndPosition = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                //Add other rules to the layoutparams
                horseman3EndPosition.setMargins(0, 0, 0, 125);//Left-Top-Right-Bottom
                horseman3EndPosition.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                horseman3EndPosition.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                //Set the end position of horseman
                horseman3.setLayoutParams(horseman3EndPosition);
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Set the result TextView INVISIBLE
        redLane.setVisibility(View.INVISIBLE);
        greenLane.setVisibility(View.INVISIBLE);
        blueLane.setVisibility(View.INVISIBLE);

        //Whenever setting tracks, the game is ready to play
        readyPlay = true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //onClick()
    @Override
    public void onClick(View myView) {
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Check which Views has been clicked and perform responses
        switch (myView.getId()) {

            case R.id.resetBtn:
                resetLayout();
                break;

            case R.id.startBtn:
                //check whether the game is ready to play, if not, do nothing
                if (!readyPlay) {
                    break;
                }

                ////////////////////////////////////////////////////////////////////////////////////
                //Set up the race tracks ready to start playing
                setTracks();
                //Start the horse race
                ////////////////////////////////////////////////////////////////////////////////////
                //Apply the animation for background: start to move the background leftward
                animatedBackgroundImage.start();

                //////////////////////////////////////////////////////////////
                //Check whether the frames are running, if yes, do nothing. If not, start running
                //horseman1
                if (!horseman1Animation.isRunning()) {
                    //Start the animation by using FrameAnimation
                    horseman1Animation.start();
                }
                //horseman2
                if (!horseman2Animation.isRunning()) {
                    //Start the animation by using FrameAnimation
                    horseman2Animation.start();
                }
                //horseman3
                if (!horseman3Animation.isRunning()) {
                    //Start the animation by using FrameAnimation
                    horseman3Animation.start();
                }

                //////////////////////////////////////////////////////////////
                //Start three horsemen running
                horseman1Run.start();
                horseman2Run.start();
                horseman3Run.start();

                //Set the game is not ready to play
                readyPlay = false;
                break;

            default:
                //Errors or Exceptions
                //
                break;
        }
    }
}
