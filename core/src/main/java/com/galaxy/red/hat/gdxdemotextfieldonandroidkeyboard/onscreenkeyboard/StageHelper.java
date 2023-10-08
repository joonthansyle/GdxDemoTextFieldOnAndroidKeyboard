package com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard.onscreenkeyboard;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StageHelper {
    private static final String TAG = StageHelper.class.getCanonicalName();
    public static final String NAME = TAG;


    public static int getCountActorBy(Stage stage, Class<?> clazz){
        if(clazz.isInstance(stage)){

        }
        int count = 0;
        for (int i = 0; i < stage.getActors().size ; i++) {
            Actor actor = stage.getActors().get(i);
            if(clazz.isInstance(actor)){
                count++;
            }
            if(stage.getActors().get(i) instanceof Group){
                count += getCountActorFromGroup(stage.getActors().get(i), clazz);
            }
        }
        return count;
    }

    private static int getCountActorFromGroup(Object actor, Class<?> clazz) {
        int count = 0;
        if(actor instanceof Group){
            Group gr = (Group)actor;
            for (int i = 0; i < gr.getChildren().size; i++) {
                if(clazz.isInstance(gr.getChildren().get(i))){
                    count++;
                }
                if(gr.getChildren().get(i) instanceof Group){
                    count += getCountActorFromGroup(gr.getChildren().get(i), clazz);
                }
            }
        }
        return count;
    }
}
