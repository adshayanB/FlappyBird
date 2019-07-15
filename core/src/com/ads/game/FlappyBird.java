package com.ads.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;

	float birdY =0;
	float speed = 0;
	float gravity =8;

	int gameState;

	int flapState = 0;

	Texture topTube;
	Texture bottomTube;
	float gap = 400;
	float maxTubespacing;

	float tubespeed=16;

	int  numberOfTubes =4;
	float[] tubeX =new float [numberOfTubes];
	float []tubespace =new float [numberOfTubes];
	float distanceInbetween;

	Random randValue;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		background= new Texture("bg.png");

		birds = new Texture[2];
		birds [0] = new Texture("bird.png");
		birds [1] = new Texture ("bird2.png");

		birdY=Gdx.graphics.getHeight()/2-birds[0].getHeight()/2;

		topTube= new Texture("toptube.png");
		bottomTube= new Texture("bottomtube.png");
		maxTubespacing =Gdx.graphics.getHeight()/2-gap/2-100;
		randValue = new Random();

		distanceInbetween = Gdx.graphics.getWidth()*3/4;

		for (int i=0; i<numberOfTubes; i++){

			tubespace[i]= (randValue.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-gap-200);
			tubeX[i]=Gdx.graphics.getWidth()/2-topTube.getWidth()/2+i*distanceInbetween;
		}


	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState != 0) {



			if (Gdx.input.justTouched()) {
				speed = -45;


			}
			if (birdY > 0 || speed < 0) {
				speed = speed + gravity;
				birdY -= speed;

			}
			for (int i=0; i<numberOfTubes; i++) {

                   if(tubeX[i]<-topTube.getWidth()){

                   	tubeX[i]+=numberOfTubes*distanceInbetween;

				   }

                   else {
					   tubeX[i] = tubeX[i] - tubespeed;

				   }
				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubespace[i]);

				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubespace[i]);

			}

		}

	else{

			if (Gdx.input.justTouched()){


				gameState=1;

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

        batch.end();
	}
	

}
