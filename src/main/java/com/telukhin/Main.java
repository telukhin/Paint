package com.telukhin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.telukhin.paint.DisplayDriver;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import com.telukhin.paint.Board;
import com.telukhin.paint.Const;
import com.telukhin.paint.DisplayDriver;
import com.telukhin.paint.model.Shape;
import com.telukhin.paint.save.Save;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static final int CANVAS_X = 1200;
    public static final int CANVAS_Y = 700;

    private Board board;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Paint");
        final Canvas canvas = new Canvas(CANVAS_X, CANVAS_Y);
        final BorderPane group = new BorderPane();
        group.setCenter(canvas);
        final Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.show();

        initBoard(canvas, primaryStage);
        registerOnKeyPressListener(primaryStage.getScene());
        registerOnMousePressListener(primaryStage.getScene());
    }

    private void initBoard(Canvas canvas, final Stage primaryStage) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        DisplayDriver driver = new DisplayDriver(gc);
        board = new Board(driver, new Board.ShapeSelectedListener() {
            @Override
            public void onShapeSelected(int index) {
                primaryStage.setTitle(String.valueOf(index));
            }
        });
        File f = new File(Const.SAVE_FILE_NAME);
        if (f.exists()){
            loadScene();
        }

        gc.fillText("Controls: move: arrows; create: 1-3; inc/dec: w/q; select: page up/down; Mouse click: Ctrl-merge, Shift-clone", 100, 50);

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void registerOnKeyPressListener(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        board.move(Board.Direction.UP);
                        break;
                    case RIGHT:
                        board.move(Board.Direction.RIGHT);
                        break;
                    case DOWN:
                        board.move(Board.Direction.DOWN);
                        break;
                    case LEFT:
                        board.move(Board.Direction.LEFT);
                        break;
                    case DIGIT1:
                        board.add(Shape.ShapeType.OVAL);
                        break;
                    case DIGIT2:
                        board.add(Shape.ShapeType.SQUARE);
                        break;
                    case DIGIT3:
                        board.add(Shape.ShapeType.TRIANGLE);
                        break;
                    case PAGE_DOWN:
                        board.previous();
                        break;
                    case PAGE_UP:
                        board.next();
                        break;
                    case Q:
                        board.dec();
                        break;
                    case W:
                        board.inc();
                        break;
                    case DELETE:
                        board.remove();
                        break;
                    case S:
                        saveScene();
                        break;
                    case L:
                        loadScene();
                        break;
                }
            }
        });
    }

    private void loadScene() {
        try {
            File f = new File(Const.SAVE_FILE_NAME);
            String jsonString = FileUtils.readFileToString(f, "windows-1251");

            Gson gson = new GsonBuilder().create();
            Save save = gson.fromJson(jsonString, Save.class);
            board.loadSave(save);
        } catch (Exception e) {
            System.out.println("Can't load from file");
        }
    }

    private void saveScene() {
        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(board.makeSave());
        File f = new File(Const.SAVE_FILE_NAME);
        try {
            FileUtils.writeStringToFile(f, jsonString, "windows-1251", false);
        } catch (IOException e) {
            System.out.println("Can't save to file");
        }
    }

    public void registerOnMousePressListener(Scene scene) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isControlDown()) {
                    board.merge((int) event.getSceneX(), (int) event.getSceneY(), true);
                }
                if (event.isShiftDown()) {
                    board.merge((int) event.getSceneX(), (int) event.getSceneY(), false);
                }
            }
        });
    }


}