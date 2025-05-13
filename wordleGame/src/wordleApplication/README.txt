link to demo: https://youtu.be/lW4JXTZ84Ys
compile and run: 
cd wordleGame && 
javac -d bin --module-path /usr/share/openjfx/lib --add-modules javafx.controls,javafx.fxml src/wordleApplication/*.java &&
java --module-path /usr/share/openjfx/lib --add-modules javafx.controls,javafx.fxml -cp bin wordleApplication.Main