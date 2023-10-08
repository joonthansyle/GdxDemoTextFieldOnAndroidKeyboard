package com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard.onscreenkeyboard.OnScreenKeyboardApplicationBundle;
import com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard.onscreenkeyboard.OnScreenKeyboardView;
import com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard.onscreenkeyboard.SizeChangeListener;

// Reference:
// https://stackoverflow.com/questions/9604963/how-do-libgdx-detect-keyboard-presence/33188659#33188659
// https://github.com/libgdx/libgdx/issues/3892
public class AppAndroidScreen implements Screen {
    private static final String TAG = AppAndroidScreen.class.getCanonicalName();
    public static final String NAME = TAG;
    private static Stage stage, stage2;
    private Skin skin;
    private ScreenViewport viewport, viewport2;
    private static OnScreenKeyboardView view;
    private Label lblViewHeight, lblStageHeight;
    private TextField tf1, tf2;
    private TextField[] tfs;
    private ScrollPane sp;
    private float yOffset = 500f;
    private static Actor hitActor = null;

    public AppAndroidScreen(OnScreenKeyboardApplicationBundle applicationBundle) {
        view = applicationBundle.getView();
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "APP ANDROID SCREEN");
        viewport = new ScreenViewport();
        viewport2 = new ScreenViewport();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(viewport);
        stage2 = new Stage(viewport2);
        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        root.setFillParent(true);
        root.align(Align.topLeft);
        lblViewHeight = new Label("", skin);
        lblStageHeight = new Label("", skin);

        tf1 = new TextField("", skin);
        tf2 = new TextField("", skin);
        tf1.setName("TF1");
        tf2.setName("TF2");
        tfs = new TextField[2];
        tfs[0] = tf1;
        tfs[1] = tf2;

        int originalHeight = Gdx.graphics.getHeight();
        tf1.setWidth((float) view.getWidth() * 0.6f);
        tf2.setWidth((float) view.getWidth() * 0.6f);
        tf1.setHeight((float) view.getHeight() * 0.05f);
        tf2.setHeight((float) view.getHeight() * 0.05f);

//        stage.addListener(new ChangeListener() {
//            private float x1, y1;
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                Gdx.app.log(TAG, "STAGE CHANGED : "+actor);
//                if(!isViewChanged) return;
//                if (actor instanceof TextField) {
//                    hitActor = actor;
//                }
//                if(hitActor!=null && isViewChanged){
//                    if(stage.getHeight() > view.getHeight()){
//                        float tfLocationY = 0;
//                        tfLocationY = hitActor.localToStageCoordinates(new Vector2(0, 0)).y;
//                        yOffset = getKeyboardHeight()-tfLocationY+50;
//                        StageHelper.getCountActorBy(stage, TextField.class);
//                        stage.getViewport().getCamera().translate(0, -(yOffset), 0);
//                        stage.getViewport().getCamera().update();
//                    }
//                    hitActor = null;
//                }
//                Gdx.app.log(TAG, "STAGE: HAS ACTOR : "+hitActor);
//                hitActor = null;
//                isViewChanged = false;
//            }
//        });
//
//        stage.addListener(new ClickListener(){
//            private float x1, y1;
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if(!isViewChanged) return;
//                Gdx.app.log(TAG,"INSIDE CLICK : "+Thread.currentThread().getName());
//                Gdx.app.log(TAG, "STAGE: CLICKED : ");
//                super.clicked(event, x, y);
//                Actor hitUnkActor = stage.hit(x, y, false);
//                if (hitUnkActor instanceof TextField) {
//                    hitActor = hitUnkActor;
//                } else {
//                    for (TextField t: tfs){
//                        stage.unfocus(t);
//                    }
//                }
//                if(hitActor==null && x!=x1 && y!=y1){
//                    x1 = x;
//                    y1 = y;
//                    clicked(event, x, y);
//                }
//                if(hitActor!=null && isViewChanged){
//                    if(stage.getHeight() > view.getHeight()){
//                        float tfLocationY = 0;
//                        tfLocationY = hitActor.localToStageCoordinates(new Vector2(0, 0)).y;
//                        yOffset = getKeyboardHeight()-tfLocationY+50;
//                        StageHelper.getCountActorBy(stage, TextField.class);
//                        stage.getViewport().getCamera().translate(0, -(yOffset), 0);
//                        stage.getViewport().getCamera().update();
//                    }
//                    hitActor = null;
//                }
//                Gdx.app.log(TAG, "STAGE: HAS ACTOR : "+hitActor);
//                hitActor = null;
//                isViewChanged = false;
//            }
//        });

        view.addListener(new SizeChangeListener() {
            @Override
            public void onSizeChange(float width, float height) {
                Gdx.app.postRunnable(() -> {
                    if(stage.getKeyboardFocus()!=null && stage.getKeyboardFocus().hasKeyboardFocus()){
                        Gdx.app.log(TAG, "VIEW: KEYBOARD FOCUS : "+stage.getKeyboardFocus());
                        if(stage.getKeyboardFocus() instanceof TextField){
                            hitActor = stage.getKeyboardFocus();
                            if(stage.getHeight() > view.getHeight()){
                                float tfLocationY = 0;
                                tfLocationY = hitActor.localToStageCoordinates(new Vector2(0, 0)).y;
                                Gdx.app.log(TAG, "VIEW: TRANSLATING TEXTFIELD Y LOCATION: "+tfLocationY);
                                yOffset = getKeyboardHeight()-tfLocationY+50;
                                stage.getViewport().getCamera().translate(0, -(yOffset), 0);
                                stage.getViewport().getCamera().update();
                            }
                        }
                    }

                    boolean isCameraAtOriginalPosition = stage.getHeight()/2 == stage.getViewport().getCamera().position.y;
                    float camCenter = 0;
                    if(!isCameraAtOriginalPosition)
                        camCenter = stage.getHeight()/2 - stage.getViewport().getCamera().position.y;

                    lblViewHeight.setText("Visible area: " + width + "   " + height);
                    lblStageHeight.setText("Stage area: " + stage.getWidth() + "   " + stage.getHeight());

                    if(stage.getHeight() == view.getHeight()){
                        stage.getViewport().getCamera().translate(0, camCenter, 0);
                        stage.getViewport().getCamera().update();
                    }
                });
            }
        });
        lblViewHeight.getStyle().font.getData().scale(.5f);
        lblStageHeight.getStyle().font.getData().scale(.5f);
        lblViewHeight.setText(Gdx.graphics.getHeight());
        createScrollPane();

        root.add(sp).top().left().padTop(100f).padLeft(30f);

        stage.addActor(root);
        Table root2 = new Table();
        root2.setFillParent(true);
        root2.align(Align.topLeft);
        root2.add(getTableTitle()).growX();

        stage2.addActor(root2);

    }
    private static float getKeyboardHeight() {
        return stage.getHeight() - view.getHeight();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(0));
        stage.act();
        stage.draw();
        stage2.act();
        stage2.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.F5)) {
            dispose();
            show();
        }

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
        stage2.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        stage2.dispose();
        skin.dispose();
    }
    private Table getTableTitle(){
        Table tableTitle = new Table();
        Label titleLabel = new Label("TITLE TABLE", skin);
        tableTitle.add(titleLabel).height(100f);
        tableTitle.setBackground(skin.getDrawable("button-normal"));
        return tableTitle;
    }

    private void createScrollPane(){
        Table lbTable = new Table();
        Table tfTable = new Table();
        Table contentTable = new Table();

        lbTable.add(lblViewHeight);
        lbTable.row();
        lbTable.add(lblStageHeight);

        tfTable.add(tf1).width(view.getWidth() * 0.6f).height(view.getHeight() * 0.05f);
        tfTable.row();
        tfTable.add(tf2).width(view.getWidth() * 0.6f).height(view.getHeight() * 0.05f);

        contentTable.add(lbTable);
        for (int i = 0; i < 25; i++) {
            contentTable.row();
            contentTable.add(new Label("LABEL TOP "+i, skin)).pad(10f);
        }
        contentTable.row();
        contentTable.add(tfTable);
        for (int i = 0; i < 4; i++) {
            contentTable.row();
            contentTable.add(new Label("LABEL BOTTOM "+i, skin)).pad(10f);
        }
        sp = new ScrollPane(contentTable, skin);
    }

}
