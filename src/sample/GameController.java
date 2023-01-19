package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GameController implements Initializable {


    private int wordCounter = 0;
    private boolean first = true;

    private File saveData;

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);


    @FXML
    public Text seconds;
    @FXML
    private Text wordsPerMin;
    @FXML
    private Text accuracy;
    @FXML
    private Text programWord;
    @FXML
    private Text secondProgramWord;

    @FXML
    private TextField userWord;

    @FXML
    private ImageView correct;
    @FXML
    private ImageView wrong;

    @FXML
    private Button playAgain;

    ArrayList<String> words = new ArrayList<>();

    // add words to array list
    public void addToList() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("wordsList"));
            String line = reader.readLine();
            while (line != null) {
                words.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toMainMenu(ActionEvent ae) throws IOException {
        Main m = new Main();
        m.changeScene("sample.fxml");
    }
    private int Saving = 1;
    private boolean NewLife = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        playAgain.setVisible(false);
        playAgain.setDisable(true);
        seconds.setText("60");
        addToList();
        Collections.shuffle(words);
        programWord.setText(words.get(wordCounter));


        secondProgramWord.setText("Level : "+level);
        wordCounter++;


        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        saveData = new File("src/data/"+formatter.format(date).strip()+".txt");

        try {
            if (saveData.createNewFile()) {
                System.out.println("File created: " + saveData.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int clock = 60;
    private int level=1;
    private int life = 3;
    private int max = 1000;
    private double FinalTime=3.9*Math.pow(0.9, 1);

    private double startTime = 0;
    private double speed = 3.9*Math.pow(0.9, max);

    Runnable r = new Runnable() {
        @Override
        public void run() {
            startTime=startTime+speed;
            System.out.println(startTime);
            if(startTime==FinalTime)
            {
                System.out.println("5 SEC passed");
                wordCounter++;
                programWord.setText(words.get(wordCounter));

            }


            if (clock > -1 && life!=-1) {

                seconds.setText(String.valueOf(clock));
                wordsPerMin.setText(String.valueOf(life));
                clock -= 1;
            }

            else {
                if (life<=-1) {
                    userWord.setDisable(true);
                    userWord.setText("Game over");
                    playAgain.setVisible(true);
                    playAgain.setDisable(false);


                    try {
                        FileWriter myWriter = new FileWriter(saveData);
                        myWriter.write(countAll +";");
                        myWriter.write(counter +";");
                        myWriter.write(String.valueOf(countAll-counter));
                        myWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    executor.shutdown();
                }

                if (clock == -1) {
                    if(level>=13){
                        clock = 3;
                    }else{
                        clock = 65-5*level;
                    }

                    life = life -1;
                    if(life!=-1)
                    {Thread t = new Thread(fadeWrong);
                        t.start();}

                    userWord.setText("");
                    accuracy.setText(String.valueOf(Math.round((counter*1.0/countAll)*100)));
                    programWord.setText(words.get(wordCounter));
                    wordCounter++;
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 5);

                    if(randomNum==Saving) {
                        programWord.setFill(Color.BLUE);
                        NewLife=true;

                    }
                    else
                    {
                        programWord.setFill(Color.BLACK);
                        NewLife=false;

                    }

                    System.out.println(randomNum);
                }

                clock -= 1;
            }
        }
    };

    Runnable fadeCorrect = new Runnable() {
        @Override
        public void run() {
            correct.setOpacity(0);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            correct.setOpacity(50);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            correct.setOpacity(100);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            correct.setOpacity(0);

        }
    };

    Runnable fadeWrong = new Runnable() {
        @Override
        public void run() {
            wrong.setOpacity(0);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wrong.setOpacity(50);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wrong.setOpacity(100);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wrong.setOpacity(0);
        }
    };


    private int countAll = 0;
    private int counter = 0;

    public void startGame(KeyEvent ke) {

        // only gets called once
        if (first ) {
            first = false;
            executor.scheduleAtFixedRate(r, 0, 1, TimeUnit.SECONDS);
        }

        if (ke.getCode()==KeyCode.ENTER) {

            String s = userWord.getText();
            String real = programWord.getText();
            countAll++;

            // if correct
            if (s.equals(real)) {
                if(NewLife)
                    life=life+1;

                counter++;
                if(counter%3 == 0 && counter!=level-1*5)
                    level=level+1;
                //speed=3.9*Math.pow(0.9, level);
                secondProgramWord.setText("Level : "+level);
                if(level>=13){
                    clock = 3;
                }else{
                    clock = 65-5*level;
                }
                //setText(String.valueOf(counter));
                wordsPerMin.setText(String.valueOf(life));
                wordCounter++;
                Thread t = new Thread(fadeCorrect);
                t.start();

            }
            else {
                life=life-1;
                if(life>=0)
                {Thread t = new Thread(fadeWrong);
                t.start();}

                if(level>=13){
                    clock = 3;
                }else{
                    clock = 65-5*level;
                }
            }
            userWord.setText("");
            accuracy.setText(String.valueOf(Math.round((counter*1.0/countAll)*100)));
            programWord.setText(words.get(wordCounter));
            wordCounter++;
            int randomNum = ThreadLocalRandom.current().nextInt(0, 5);

            if(randomNum == Saving) {
                programWord.setFill(Color.BLUE);
                NewLife=true;
            }
            else
            {
                programWord.setFill(Color.BLACK);
                NewLife=false;

            }
            System.out.println(randomNum);
        }
    }
}
