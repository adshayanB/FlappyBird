package com.ads.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	BitmapFont font;

	Texture gameOver;

	float birdY =0;
	float speed = 0;
	float gravity =8;

	int gameState;
	int score=0;
	int scoringTube=0;

	int flapState = 0;

	Texture topTube;
	Texture bottomTube;
	float gap = 400;
	float maxTubespacing;

	float tubespeed=25;

	int  numberOfTubes =4;
	float[] tubeX =new float [numberOfTubes];
	float []tubespace =new float [numberOfTubes];
	float distanceInbetween;
 private Sound flap;
 private Sound point;
 private Sound hit;

 int counter;
	Circle bird;

	Random randValue;

	Rectangle [] topTubes;
	Rectangle[] bottomTubes;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
		gameOver = new Texture("gameover.png");


		background= new Texture("bg.png");
        bird = new Circle();
        topTubes = new Rectangle[numberOfTubes];
        bottomTubes = new Rectangle[numberOfTubes];

		birds = new Texture[2];
		birds [0] = new Texture("bird.png");
		birds [1] = new Texture ("bird2.png");


		topTube= new Texture("toptube.png");
		bottomTube= new Texture("bottomtube.png");
		maxTubespacing =Gdx.graphics.getHeight()/2-gap/2-100;
		randValue = new Random();

		distanceInbetween = Gdx.graphics.getWidth()*3/4;

		flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        point = Gdx.audio.newSound(Gdx.files.internal("sfx_point.ogg"));
        hit = Gdx.audio.newSound(Gdx.files.internal("sfx_hit.ogg"));

		counter=0;

startGame();


	}
public void startGame(){
	birdY=Gdx.graphics.getHeight()/2-birds[0].getHeight()/2;

	for (int i=0; i<numberOfTubes; i++){

		tubespace[i]= (randValue.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-gap-200);
		tubeX[i]=Gdx.graphics.getWidth()/2-topTube.getWidth()/2+Gdx.graphics.getWidth()+i*distanceInbetween;

		topTubes [i] = new Rectangle();
		bottomTubes[i] = new Rectangle();
	}



}
	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState ==1) {
            if(tubeX[scoringTube]<Gdx.graphics.getWidth()/2){

                    score++;
                    point.play();

                    Gdx.app.log("Score",String.valueOf(score));
                    if(scoringTube<numberOfTubes-1){
                        scoringTube++;

                    }
                    else{

                        scoringTube =0;
                    }

            }



			if (Gdx.input.justTouched()) {
				speed = -45;
                 flap.play();



			}
			if (birdY > 0 ) {
				speed = speed + gravity;
				birdY -= speed;

			}else {

				if (counter == 0) {
					hit.play();
					counter++;
				}
                gameState = 2;
			}

			for (int i=0; i<numberOfTubes; i++) {

                   if(tubeX[i]<-topTube.getWidth()){

                   	tubeX[i]+=numberOfTubes*distanceInbetween;

                   	tubespace[i]= (randValue.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-gap-200);

				   }

                   else {
					   tubeX[i] = tubeX[i] - tubespeed;


				   }
				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubespace[i]);

				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubespace[i]);

				topTubes [i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubespace[i],topTube.getWidth(),topTube.getHeight());

				bottomTubes [i] = new Rectangle(tubeX[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubespace[i],bottomTube.getWidth(),bottomTube.getHeight());

			}

		}

	else if (gameState==0){

			if (Gdx.input.justTouched()){


				gameState=1;

			}
		}
	else if (gameState==2)
		{
			batch.draw(gameOver, Gdx.graphics.getWidth()/2-gameOver.getWidth()/2,Gdx.graphics.getHeight()/2+gameOver.getHeight()/2);




			if (Gdx.input.justTouched()){


				gameState=1;
				startGame();
				score = 0;
				scoringTube=0;
				speed=0;
				counter=0;


			}



		}
		if(flapState==1){

			flapState=0;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		else {

			flapState = 1;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}



        batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
      font.draw (batch, String.valueOf(score), 100,200);

		bird.set(Gdx.graphics.getWidth()/2, birdY + birds[flapState].getHeight()/2, birds[flapState].getWidth()/2 );





		for (int i=0; i<numberOfTubes; i++) {



			   if(Intersector.overlaps(bird,topTubes[i])|| Intersector.overlaps(bird,bottomTubes[i])){

			       if (counter == 0) {
                       hit.play();
                       counter++;
                   }

                   gameState=2;


			   }
		}

        batch.end();
	}
	

}
