import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.Map;
import java.util.Timer;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getMasterTimer;
import static javafx.scene.text.Font.*;

public class Main extends GameApplication {

    private Entity background, leftPlayer, rightPlayer, power, power2, StartWindow;
    private boolean isleft = true;
    private boolean isright = false;
    private boolean ispower = false;
    private boolean ispower2 = false;
    private boolean cheack_attack = false;
    private boolean entry = false;


    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("CK-fighters");
        settings.setVersion("1.0");
        settings.setHeight(1000);
        settings.setWidth(1900);
    }

    protected void initGame() {
        background = FXGL.entityBuilder()
                .at(0, 0)
                .view("BackgroundFinal.png")
                .buildAndAttach();
        leftPlayer = FXGL.entityBuilder()
                .at(100, 500)
                .view("static1.gif")
                .buildAndAttach();
        rightPlayer = FXGL.entityBuilder()
                .at(1400, 500)
                .view("static2.gif")
                .buildAndAttach();
        power = FXGL.entityBuilder()
                .at(-1000, -1000)
                .view("power1.gif")
                .buildAndAttach();
        power2 = FXGL.entityBuilder()
                .at(-1000, -1000)
                .view("Power2.gif")
                .buildAndAttach();
       StartWindow = FXGL.entityBuilder()
                .at(0,0)
                .view("EntryPage.gif")
                .buildAndAttach();
    }

    protected void initInput() {
        Input input = FXGL.getInput();
        input.addAction(new UserAction("entry") {
            @Override
            protected void onAction() {
                // super.onAction();

                if ((input.getMousePositionUI().getX() > 1000 && input.getMousePositionUI().getX() < 1800) && (input.getMousePositionUI().getY() > 0 && input.getMousePositionUI().getY() < 1000)) {
                    entry = true;
//                    initInput();

                    StartWindow.removeFromWorld();
                    Text textPixels = new Text();
                    textPixels.setTranslateX(230); // x = 50
                    textPixels.setTranslateY(160); // y = 100
                    textPixels.setFont(font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
                    textPixels.textProperty().bind(FXGL.getGameState().intProperty("left").asString());
                    //textPixels.setText("Score : ");
                    textPixels.setFill(Color.WHITE);
                    FXGL.getGameScene().addUINode(textPixels); // add to the scene graph

                    Text textPixels1 = new Text();
                    textPixels1.setTranslateX(1600); // x = 50
                    textPixels1.setTranslateY(160); // y = 100
                    // textPixels1.setText("Score : ");
                    textPixels1.setFont(font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
                    textPixels1.textProperty().bind(FXGL.getGameState().intProperty("right").asString());
                    //textPixels1.setText("Score : ");
                    textPixels1.setFill(Color.WHITE);
                    FXGL.getGameScene().addUINode(textPixels1);

                } else {
                    System.exit(0);
                }
            }
        }, MouseButton.PRIMARY);
        if (true) {
           /* Text textPixels = new Text();
            textPixels.setTranslateX(230); // x = 50
            textPixels.setTranslateY(160); // y = 100
            textPixels.setFont(font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
            textPixels.textProperty().bind(FXGL.getGameState().intProperty("left").asString());
            //textPixels.setText("Score : ");
            textPixels.setFill(Color.WHITE);
            FXGL.getGameScene().addUINode(textPixels); // add to the scene graph

            Text textPixels1 = new Text();
            textPixels1.setTranslateX(1600); // x = 50
            textPixels1.setTranslateY(160); // y = 100
            // textPixels1.setText("Score : ");
            textPixels1.setFont(font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
            textPixels1.textProperty().bind(FXGL.getGameState().intProperty("right").asString());
            //textPixels1.setText("Score : ");
            textPixels1.setFill(Color.WHITE);
            FXGL.getGameScene().addUINode(textPixels1); // add to the scene graph*/
            if (isleft) {
                isleft = false;
                isright = true;

                //count=count-1;
                // getMasterTimer().newLocalTimer().elapsed(10);
                //Input input = FXGL.getInput();
                //leftplayer move forword R
                input.addAction(new UserAction("move forword") {
                    @Override
                    protected void onAction() {
                        leftPlayer.setView(FXGL.getAssetLoader().loadTexture("static1.gif"));
                        if (rightPlayer.getX() - leftPlayer.getX() > 350) {
                            leftPlayer.translateX(3);
                        }
                    }

                    protected void onActionEnd() {
                    }
                }, KeyCode.R);
//Attack forword W fire

                input.addAction(new UserAction("forword W") {
                    //cheack_attack = true;
                    @Override
                    protected void onAction() {
                        leftPlayer.setView(FXGL.getAssetLoader().loadTexture("left_power_attack.gif"));
                        power.setView(FXGL.getAssetLoader().loadTexture("power1.gif"));
                        if (!ispower) {
                            ispower = true;
                            power.setX(leftPlayer.getX() + 300);
                            power.setY(leftPlayer.getY());
                            if (rightPlayer.getX() - leftPlayer.getX() <= 600) {
                                FXGL.getGameState().increment("left", 10);
                            }
                        }
                        if (leftPlayer.getX() < rightPlayer.getX() - 220) {
                            leftPlayer.translateX(7);
                            if (rightPlayer.getX() - leftPlayer.getX() <= 280) {
                                FXGL.getGameState().increment("left", 1);
                            }
                        }
                        if (FXGL.getGameState().getInt("left") >= 50) {
                            rightPlayer.setView(FXGL.getAssetLoader().loadTexture("gof.png"));
                            rightPlayer.setX(1400);
                            rightPlayer.setY(500);

                            getMasterTimer().runOnceAfter(() -> {
                                System.exit(0);
                            }, Duration.seconds(3));
                        }
                    }

                    @Override
                    protected void onActionEnd() {
                        power.removeFromWorld();
                        getMasterTimer().runOnceAfter(() -> {
                            //power.removeFromWorld();
                            leftPlayer.setView(FXGL.getAssetLoader().loadTexture("static1.gif"));
                            if (leftPlayer.getX() > 100) {
                                leftPlayer.translateX(-2);
                            }
                        }, Duration.millis(500));
                    }
                }, KeyCode.W);
                //flying attack Q
                input.addAction(new UserAction("flying attack by right player Q") {

                    @Override
                    protected void onAction() {
                        getMasterTimer().runOnceAfter(() -> {

                            if (rightPlayer.getX() - leftPlayer.getX() >= 1200) {
                                leftPlayer.setView(FXGL.getAssetLoader().loadTexture("attack2 Fly.gif"));
                                leftPlayer.setX(20);
                                leftPlayer.setY(100);
                                FXGL.getGameState().increment("left", 2);
                            }
                        }, Duration.millis(800));
                        if (FXGL.getGameState().getInt("left") >= 200) {
                            rightPlayer.setView(FXGL.getAssetLoader().loadTexture("gof.png"));

                            getMasterTimer().runOnceAfter(() -> {
                                System.exit(0);
                            }, Duration.seconds(3));
                        }
                    }

                    // @override
                    protected void onActionEnd() {
                        getMasterTimer().runOnceAfter(() -> {
                            leftPlayer.setView(FXGL.getAssetLoader().loadTexture("static1.gif"));
                            if (leftPlayer.getX() > 100) {
                                leftPlayer.translateX(-2);
                            }
                        }, Duration.millis(500));

                    }
                }, KeyCode.Q);
                //attack3 left ghusi E
                input.addAction(new UserAction("attack3 left ghusi E") {
                    @Override
                    protected void onAction() {
                        leftPlayer.setView(FXGL.getAssetLoader().loadTexture("attack3 left.gif"));
                        if (rightPlayer.getX() - leftPlayer.getX() > 400) {
                            leftPlayer.translateX(5);

                        }
                        if (rightPlayer.getX() - leftPlayer.getX() <= 400) {
                            FXGL.getGameState().increment("left", 1);
                        }
                        if (FXGL.getGameState().getInt("left") >= 200) {
                            rightPlayer.setView(FXGL.getAssetLoader().loadTexture("gof.png"));

                            getMasterTimer().runOnceAfter(() -> {
                                System.exit(0);
                            }, Duration.seconds(4));
                        }
                    }

                    @Override
                    protected void onActionEnd() {
                        getMasterTimer().runOnceAfter(() -> {
                            leftPlayer.setView(FXGL.getAssetLoader().loadTexture("static1.gif"));

                        }, Duration.millis(500));
                    }

                }, KeyCode.E);
                //defend forword k
                input.addAction(new UserAction("Defend backword K") {
                    //defend_cheack='v';
                    @Override
                    protected void onAction() {
                        rightPlayer.setView(FXGL.getAssetLoader().loadTexture("Defend1.gif"));
                        if (rightPlayer.getX() < 1650) {
                            rightPlayer.translateX(2);
                        }
                    }

                    @Override
                    protected void onActionEnd() {
                        getMasterTimer().runOnceAfter(() -> {
                            rightPlayer.setView(FXGL.getAssetLoader().loadTexture("static2.gif"));
                            if (rightPlayer.getX() < 1400) {
                                rightPlayer.translateX(2);
                            }
                        }, Duration.millis(60));
                    }

                }, KeyCode.K);
                //defend3 right L
                input.addAction(new UserAction("defend3 right L") {
                    @Override
                    protected void onAction() {
                        rightPlayer.setView(FXGL.getAssetLoader().loadTexture("defend3 right.gif"));
                        if (rightPlayer.getX() < 1550) {
                            rightPlayer.translateX(2);
                        }
                    }

                    @Override
                    protected void onActionEnd() {

                        getMasterTimer().runOnceAfter(() -> {
                            rightPlayer.setView(FXGL.getAssetLoader().loadTexture("static2.gif"));
                            if (rightPlayer.getX() < 1400) {
                                rightPlayer.translateX(2);
                            }
                        }, Duration.millis(60));
                    }
                }, KeyCode.L);
                //degending flying attack J
                input.addAction(new UserAction("degending flying attack") {
                    @Override
                    protected void onAction() {
                        rightPlayer.setView(FXGL.getAssetLoader().loadTexture("DefendingFlyAttack (1).gif"));
                        if (rightPlayer.getX() < 1650) {
                            rightPlayer.translateX(2);
                            // rightPlayer.translateY(-1);
                        }
                    }

                    @Override
                    protected void onActionEnd() {
                        getMasterTimer().runOnceAfter(() -> {
                            rightPlayer.setView(FXGL.getAssetLoader().loadTexture("static2.gif"));
                            if (rightPlayer.getX() < 1400) {
                                rightPlayer.translateX(2);
                            }
                        }, Duration.millis(60));
                    }
                }, KeyCode.J);
            }
            if (isright) {
                //Input input = FXGL.getInput();
//move forword right player backspace
                input.addAction(new UserAction("move forword right player backspace") {
                    protected void onAction() {
                        rightPlayer.setView(FXGL.getAssetLoader().loadTexture("static2.gif"));
                        if (rightPlayer.getX() - leftPlayer.getX() >= 350) {
                            rightPlayer.translateX(-7);
                        }
                    }

                    protected void onActionEnd() {
                        if (rightPlayer.getX() < 1400) {
                            rightPlayer.translateX(2);
                        }
                    }
                }, KeyCode.BACK_SPACE);
                //attack straight O slide lang
                input.addAction(new UserAction("Attack left") {
                    @Override
                    protected void onAction() {
                        rightPlayer.setView(FXGL.getAssetLoader().loadTexture("attack2right.gif"));
                        /*if (!ispower2) {
                            ispower = true;
                            power2.setX(rightPlayer.getX() - 300);
                            power2.setY(rightPlayer.getY());
                            if (rightPlayer.getX() - leftPlayer.getX() <= 600) {
                                FXGL.getGameState().increment("right", 10);
                            }
                        }*/
                        getMasterTimer().runOnceAfter(() -> {
                            if (rightPlayer.getX() - leftPlayer.getX() > 300) {
                                rightPlayer.translateX(-10);
                                if (rightPlayer.getX() - leftPlayer.getY() < 400) {
                                    FXGL.getGameState().increment("right", 10);
                                }
                            }
                        }, Duration.millis(300));

                        if (FXGL.getGameState().getInt("right") >= 200) {
                            leftPlayer.setView(FXGL.getAssetLoader().loadTexture("gof.png"));

                            getMasterTimer().runOnceAfter(() -> {
                                System.exit(0);
                            }, Duration.seconds(5));
                        }
                    }
                    @Override
                    protected void onActionEnd() {
                       // power2.removeFromWorld();
                        if (rightPlayer.getX() < 1400) {
                            getMasterTimer().runOnceAfter(() -> {
                                rightPlayer.setView(FXGL.getAssetLoader().loadTexture("static2.gif"));
                                //rightPlayer.translateX(2);

                            }, Duration.millis(500));
                        }
                    }
                }, KeyCode.O);
//attack3 right player I
                input.addAction(new UserAction("Attack3 I") {
                    @Override
                    protected void onAction() {
                        rightPlayer.setView(FXGL.getAssetLoader().loadTexture("attack3.gif"));
                        power2.setView(FXGL.getAssetLoader().loadTexture("Power2.gif"));
                        /*if (!ispower2) {
                            ispower2 = true;
                            getMasterTimer().runOnceAfter(() -> {
                                power2.setX(rightPlayer.getX() - 450);
                                power2.setY(rightPlayer.getY());
                                if (rightPlayer.getX() - leftPlayer.getX() <= 600) {
                                    FXGL.getGameState().increment("right", 10);
                                }
                                //power2.removeFromWorld();
                            }, Duration.seconds(1));


                        }*/
                        if (!ispower2) {
                            ispower = true;
                            getMasterTimer().runOnceAfter(()->{
                                power2.setX(rightPlayer.getX() - 300);
                                power2.setY(rightPlayer.getY());
                            },Duration.millis(50));

                            if (rightPlayer.getX() - leftPlayer.getX() <= 600) {
                                FXGL.getGameState().increment("right", 10);
                            }
                        }

                        if (rightPlayer.getX() - leftPlayer.getX() > 350) {
                            //leftPlayer.translateX(100);
                            rightPlayer.translateX(-1);
                            //rightPlayer.translateY(5);
                            if (rightPlayer.getX() - leftPlayer.getY() < 150) {
                                FXGL.getGameState().increment("right", 1);
                            }
                        }

                        if (FXGL.getGameState().getInt("right") >= 200) {
                            leftPlayer.setView(FXGL.getAssetLoader().loadTexture("gof.png"));
                            // power2.removeFromWorld();
                            getMasterTimer().runOnceAfter(() -> {
                                System.exit(0);
                            }, Duration.seconds(4));
                        }
                    }


                    @Override
                    protected void onActionEnd() {

                            power2.removeFromWorld();

                        if (rightPlayer.getX() < 1400) {
                            getMasterTimer().runOnceAfter(() -> {
                                //power2.removeFromWorld();
                                rightPlayer.setView(FXGL.getAssetLoader().loadTexture("static2.gif"));
                                rightPlayer.translateX(2);

                            }, Duration.millis(500));
                        }

                    }
                }, KeyCode.I);
                //attack1 rightplayer U
                input.addAction(new UserAction("attack1 rightplayer U") {
                    protected void onAction() {
                        rightPlayer.setView(FXGL.getAssetLoader().loadTexture("attack1 right.gif"));
                        /*if (!ispower2){
                            ispower2=true;
                            power2.setX(rightPlayer.getX()-100);
                            power2.setY(rightPlayer.getY());
                        }*/
                        if (rightPlayer.getX() - leftPlayer.getX() >= 300) {
                            rightPlayer.translateX(-5);
                        }
                        if (rightPlayer.getX() - leftPlayer.getX() <= 305) {
                            FXGL.getGameState().increment("right", 10);
                        }
                        if (FXGL.getGameState().getInt("right") >= 200) {
                            leftPlayer.setView(FXGL.getAssetLoader().loadTexture("gof.png"));
                            System.exit(0);
                        }
                    }

                    protected void inActoinEnd() {
                        if (rightPlayer.getX() < 1400) {
                            getMasterTimer().runOnceAfter(() -> {
                                rightPlayer.setView(FXGL.getAssetLoader().loadTexture("static2.gif"));
                                rightPlayer.translateX(2);

                            }, Duration.millis(10));

                        }
                    }
                }, KeyCode.U);
                //defend straight s
                input.addAction(new UserAction("Defend straight S") {
                    @Override
                    protected void onAction() {
                        leftPlayer.setView(FXGL.getAssetLoader().loadTexture("defendingSecondAttack.gif"));
                        leftPlayer.setX(100);
                        leftPlayer.setY(500);
                        if (leftPlayer.getX() >= 100) {
                            leftPlayer.translateX(-2);
                            //leftPlayer.translateY(-2);
                        }
                    }

                    @Override
                    protected void onActionEnd() {

                        leftPlayer.setView(FXGL.getAssetLoader().loadTexture("static1.gif"));
                    }
                }, KeyCode.S);
                //defending first attack A
                input.addAction(new UserAction("Defending fisrt attack A") {
                    @Override
                    protected void onAction() {
                        leftPlayer.setView(FXGL.getAssetLoader().loadTexture("DefendingFirstAttack.gif"));
                        if (leftPlayer.getX() > 100) {
                            leftPlayer.translateX(-2);
                        }
                    }

                    @Override
                    protected void onActionEnd() {

                        leftPlayer.setView(FXGL.getAssetLoader().loadTexture("static1.gif"));
                        if (leftPlayer.getX() > 100) {
                            leftPlayer.setX(100);
                            leftPlayer.setY(500);
                        }
                    }
                }, KeyCode.A);
                //defending third attack D
                input.addAction(new UserAction("Defending third attack D") {
                    @Override
                    protected void onAction() {
                        leftPlayer.setView(FXGL.getAssetLoader().loadTexture("defendingThirdAttack.gif"));
                        if (leftPlayer.getX() > 100) {
                            leftPlayer.translateX(-2);
                        }
                    }

                    @Override
                    protected void onActionEnd() {

                        leftPlayer.setView(FXGL.getAssetLoader().loadTexture("static1.gif"));
                        getMasterTimer().runOnceAfter(() -> {
                            if (leftPlayer.getX() > 100) {
                                leftPlayer.translateX(-1);
                            }
                        }, Duration.millis(1));
                    }
                }, KeyCode.D);

            }
        }

    }
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("left", 0);
        vars.put("right", 0);
    }

    @Override
    protected void initUI() {
       /* Text textPixels = new Text();
        textPixels.setTranslateX(230); // x = 50
        textPixels.setTranslateY(160); // y = 100
        textPixels.setFont(font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        textPixels.textProperty().bind(FXGL.getGameState().intProperty("left").asString());
        //textPixels.setText("Score : ");
        textPixels.setFill(Color.WHITE);
        FXGL.getGameScene().addUINode(textPixels); // add to the scene graph

        Text textPixels1 = new Text();
        textPixels1.setTranslateX(1600); // x = 50
        textPixels1.setTranslateY(160); // y = 100
        // textPixels1.setText("Score : ");
        textPixels1.setFont(font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        textPixels1.textProperty().bind(FXGL.getGameState().intProperty("right").asString());
        //textPixels1.setText("Score : ");
        textPixels1.setFill(Color.WHITE);
        FXGL.getGameScene().addUINode(textPixels1); // add to the scene graph*/

    }

    public static void main(String[] args) {
        launch(args);
    }
}