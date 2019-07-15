package com.ads.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;

	int flapState = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		background= new Texture("bg.png");

		birds = new Texture[2];
		birds [0] = new Texture("bird.png");
		birds [1] = new Texture ("bird2.png");
		
	}

	@Override
	public void render () {

		if(flapState==1){

			flapState=0;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		else{

			flapState=1;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		batch.begin();
		batch.draw(background, 0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(birds[flapState], Gdx.graphics.getWidth()/2-birds[flapState].getWidth()/2,Gdx.graphics.getHeight()/2-birds[flapState].getHeight()/2);

		batch.end();


	}
	

}
