package oop.pcg2d.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

import oop.pcg2d.App;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.Random;

public class SelectMap extends AbstractScreen {

    private String[] algorithms = { "Cellular Automata", "Rooms and Mazes" };
    private SelectBox<String> algorithmSelectBox;

    // 공통 파라미터
    private TextField widthTextField;
    private TextField heightTextField;
    private TextField seedTextField;
    private Label widthLabel; // 클래스 멤버 변수로 변경
    private Label heightLabel; // 클래스 멤버 변수로 변경

    // Cellular Automata 파라미터
    private TextField fillProbTextField;
    private CheckBox isConnectedCheckBox;

    // Rooms and Mazes 파라미터
    private TextField roomMinLenTextField;
    private TextField roomMaxLenTextField;
    private TextField roomGenAttemptTextField;
    private CheckBox removeDeadendCheckBox;

    // 알고리즘별 파라미터를 담는 테이블
    private Table algorithmParamsTable;

    // 배경 이미지 텍스처
    private Texture backgroundTexture;
    private Image background;

    public SelectMap(App game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        backgroundTexture = new Texture("ui/mine.png");
        background = new Image(backgroundTexture);
        background.setFillParent(true);
        stage.addActor(background);

        // 메인 테이블 생성 및 설정
        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.center);
        stage.addActor(table);

        // 맵 생성 알고리즘 선택
        Label algorithmLabel = new Label("Select a Map Algorithm:", skin);
        algorithmSelectBox = new SelectBox<>(skin);
        algorithmSelectBox.setItems(algorithms);

        // 공통 파라미터 입력 필드
        widthLabel = new Label("Map Width Size:", skin); // 라벨을 클래스 멤버 변수로 선언
        widthTextField = new TextField("", skin);

        heightLabel = new Label("Map Height Size:", skin); // 라벨을 클래스 멤버 변수로 선언
        heightTextField = new TextField("", skin);

        Label seedLabel = new Label("Enter a Random Seed:", skin);
        seedTextField = new TextField("", skin);

        // 알고리즘별 파라미터 테이블
        algorithmParamsTable = new Table();
        updateAlgorithmParamsTable("Cellular Automata"); // 초기 알고리즘에 맞게 파라미터 테이블 생성

        // 확인 및 취소 버튼 생성
        TextButton confirmButton = new TextButton("Create", skin);
        TextButton backButton = new TextButton("Back", skin);

        // 테이블에 요소 배치
        table.add(algorithmLabel).pad(5);
        table.add(algorithmSelectBox).width(200).pad(5);
        table.row();

        table.add(widthLabel).pad(5);
        table.add(widthTextField).width(200).pad(5);
        table.row();

        table.add(heightLabel).pad(5);
        table.add(heightTextField).width(200).pad(5);
        table.row();

        table.add(seedLabel).pad(5);
        table.add(seedTextField).width(200).pad(5);
        table.row();

        table.add(algorithmParamsTable).colspan(2);
        table.row();

        table.add(confirmButton).width(150).height(50).pad(10);
        table.add(backButton).width(150).height(50).pad(10);
        table.row();

        // 알고리즘 선택 변경 시 파라미터 테이블 및 맵 사이즈 라벨 업데이트
        algorithmSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selectedAlgorithm = algorithmSelectBox.getSelected();
                updateAlgorithmParamsTable(selectedAlgorithm);
                updateMapSizeLabels(selectedAlgorithm); // 맵 사이즈 라벨 업데이트
            }
        });

        // 확인 버튼 클릭 시 동작 정의
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    // 공통 파라미터 가져오기
                    int mapWidth = Integer.parseInt(widthTextField.getText());
                    int mapHeight = Integer.parseInt(heightTextField.getText());
                    long seed = seedTextField.getText().isEmpty() ? new Random().nextLong()
                            : Long.parseLong(seedTextField.getText());
                    String selectedAlgorithm = algorithmSelectBox.getSelected();

                    // Rooms and Mazes 알고리즘의 경우 맵 크기가 홀수여야 함
                    if (selectedAlgorithm.equals("Rooms and Mazes")) {
                        if (mapWidth % 2 == 0 || mapHeight % 2 == 0) {
                            // 오류 메시지 표시
                            showErrorDialog("Map size must be odd numbers for Rooms and Mazes algorithm.");
                            return;
                        }
                    }

                    // 알고리즘별 파라미터 가져오기
                    if (selectedAlgorithm.equals("Cellular Automata")) {
                        double fillProb = Double.parseDouble(fillProbTextField.getText());
                        boolean isConnected = isConnectedCheckBox.isChecked();

                        // 맵 생성 화면으로 이동
                        game.setScreen(new MapGenerationScreen(game, mapWidth, mapHeight, seed, fillProb, isConnected));
                    } else if (selectedAlgorithm.equals("Rooms and Mazes")) {
                        int roomMinLen = Integer.parseInt(roomMinLenTextField.getText());
                        int roomMaxLen = Integer.parseInt(roomMaxLenTextField.getText());
                        int roomGenAttempt = Integer.parseInt(roomGenAttemptTextField.getText());
                        boolean removeDeadend = removeDeadendCheckBox.isChecked();

                        // 방 최소/최대 길이가 홀수여야 함
                        if (roomMinLen % 2 == 0 || roomMaxLen % 2 == 0) {
                            // 오류 메시지 표시
                            showErrorDialog("Room min and max lengths must be odd numbers.");
                            return;
                        }

                        // 맵 생성 화면으로 이동
                        game.setScreen(new MapGenerationScreen(game, mapWidth, mapHeight, seed,
                                roomMinLen, roomMaxLen, roomGenAttempt, removeDeadend));
                    }
                } catch (NumberFormatException e) {
                    // 숫자 형식이 잘못된 경우 오류 메시지 표시
                    showErrorDialog("Please enter valid numeric values.");
                }
            }
        });
        // 뒤로가기 버튼 클릭 시 메인 메뉴로 돌아감
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }

    private void updateAlgorithmParamsTable(String algorithm) {
        algorithmParamsTable.clear(); // 기존의 파라미터 필드 제거

        if (algorithm.equals("Cellular Automata")) {
            // Cellular Automata 파라미터 생성
            Label fillProbLabel = new Label("Fill Probability (0~1):", skin);
            fillProbTextField = new TextField("0.45", skin);

            isConnectedCheckBox = new CheckBox("Connect Rooms", skin);
            isConnectedCheckBox.setChecked(true);

            // 테이블에 추가
            algorithmParamsTable.add(fillProbLabel).pad(5);
            algorithmParamsTable.add(fillProbTextField).width(200).pad(5);
            algorithmParamsTable.row();

            algorithmParamsTable.add(isConnectedCheckBox).colspan(2).pad(5);
            algorithmParamsTable.row();

        } else if (algorithm.equals("Rooms and Mazes")) {
            // Rooms and Mazes 파라미터 생성
            Label roomMinLenLabel = new Label("Room Min Length (Odd Number):", skin); // 홀수임을 표시
            roomMinLenTextField = new TextField("5", skin);

            Label roomMaxLenLabel = new Label("Room Max Length (Odd Number):", skin); // 홀수임을 표시
            roomMaxLenTextField = new TextField("11", skin);

            Label roomGenAttemptLabel = new Label("Room Generation Attempts:", skin);
            roomGenAttemptTextField = new TextField("50", skin);

            removeDeadendCheckBox = new CheckBox("Remove Dead Ends", skin);
            removeDeadendCheckBox.setChecked(false);

            // 테이블에 추가
            algorithmParamsTable.add(roomMinLenLabel).pad(5);
            algorithmParamsTable.add(roomMinLenTextField).width(200).pad(5);
            algorithmParamsTable.row();

            algorithmParamsTable.add(roomMaxLenLabel).pad(5);
            algorithmParamsTable.add(roomMaxLenTextField).width(200).pad(5);
            algorithmParamsTable.row();

            algorithmParamsTable.add(roomGenAttemptLabel).pad(5);
            algorithmParamsTable.add(roomGenAttemptTextField).width(200).pad(5);
            algorithmParamsTable.row();

            algorithmParamsTable.add(removeDeadendCheckBox).colspan(2).pad(5);
            algorithmParamsTable.row();
        }
    }

    // 맵 사이즈 라벨을 업데이트하는 메서드 추가
    private void updateMapSizeLabels(String algorithm) {
        if (algorithm.equals("Rooms and Mazes")) {
            // Rooms and Mazes 선택 시 라벨에 "(Odd Number)" 추가
            widthLabel.setText("Map Width Size (Odd Number):");
            heightLabel.setText("Map Height Size (Odd Number):");
        } else {
            // 다른 알고리즘 선택 시 원래 라벨로 복원
            widthLabel.setText("Map Width Size:");
            heightLabel.setText("Map Height Size:");
        }
    }

    // 오류 메시지를 표시하는 메서드
    private void showErrorDialog(String message) {
        Dialog dialog = new Dialog("Error", skin);
        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
    }

    @Override
    public void dispose() {
        super.dispose();
        // 필요한 자원 해제 작업 수행
    }
}