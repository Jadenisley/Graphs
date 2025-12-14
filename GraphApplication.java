package UI;

import Algorythm.CycleFinder;
import Algorythm.CycleFinder.Result;
import Algorythm.GraphGenerator;
import Algorythm.Mason;
import Algorythm.Mason.MasonResult;
import Graphs.Graph;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

public class GraphApplication extends Application {

    public Graph CurrentGraph;

    @Override
    public void start(Stage Stage) {
        TextField VertexField = new TextField("5");
        TextField AbilityField = new TextField("0.4");

        Button GenerateButton = new Button("Сгенерировать граф");
        Button CalculateButton = new Button("Рассчитать");

        TextArea OutPutArea = new TextArea();
        OutPutArea.setEditable(false);
        OutPutArea.setPrefRowCount(10);

        CalculateButton.setOnAction( e -> {
            if (CurrentGraph == null) {
                OutPutArea.setText("Сначала нужно сгенерировать граф.\n");
                return;
            }

            long start = System.nanoTime();

            CycleFinder CycleFinder = new CycleFinder();
            Result CycleResult = CycleFinder.FindCycles(CurrentGraph);

            int n = CurrentGraph.getCountVertex();
            int from = 0;
            int to = n - 1;

            Mason Mason = new Mason();
            MasonResult MasonResult = Mason.Calculate(CurrentGraph, from, to);

            long end = System.nanoTime();
            double m = (end-start)/1000000.0;

            StringBuilder Str = new StringBuilder();
            Str.append("Количество циклов: ").append(CycleResult.getCountCycles()).append("\n");
            Str.append("T= ").append(String.format("%.3f", MasonResult.getTransfer())).append("\n");
            Str.append("Delta= ").append(String.format("%.3f", MasonResult.getDelta())).append("\n");
            Str.append("Количество путей: ").append(MasonResult.getPath().size()).append("\n");
            Str.append("Время расчета: ").append(m).append(" ms\n");
            Str.append("Время расчета формулы Мейсона (нс): ").append(MasonResult.getNanoTime()).append("\n");

            OutPutArea.setText(Str.toString());
        });

        GenerateButton.setOnAction(e -> {
            try {
                int n = Integer.parseInt(VertexField.getText().trim());
                double p = Double.parseDouble(AbilityField.getText().trim());

                GraphGenerator GraphGenerator = new GraphGenerator();
                CurrentGraph = GraphGenerator.Generate(n, p);

                OutPutArea.setText("Граф сгенерирован. Вершин: " + n + ", вероятность ребра: " + p + "\n");
            } catch (NumberFormatException ex) {
                OutPutArea.setText("Ошибка ввода параметров. \n");
            }
        });

        VBox Root = new VBox(8,
                new Label("Количество вершин: "), VertexField,
                new Label("Вероятность ребра: "), AbilityField,
                GenerateButton, CalculateButton,
                new Label("Результаты: "), OutPutArea
        );
        Root.setPadding(new Insets(10));

        Scene Scene = new Scene(Root, 500, 400);
        Stage.setTitle("Вычисление количество петель в графе");
        Stage.setScene(Scene);
        Stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
