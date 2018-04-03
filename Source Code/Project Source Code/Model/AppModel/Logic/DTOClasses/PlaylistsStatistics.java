package Model.AppModel.Logic.DTOClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.Hashtable;

public class PlaylistsStatistics {
    private ObservableList<PieChart.Data> artistsFrequencyObservableList;
    private ObservableList<PieChart.Data> decadesFrequencyObservableList;
    private ObservableList<PieChart.Data> genresFrequencyObservableList;
    private ObservableList<XYChart.Series<Number,Number>> tempoChartObservableList;
    private ObservableList<XYChart.Series<Number,Number>> loudnessChartObservableList;
    private ObservableList<XYChart.Series<String,Number>> familiarityChartObservableList;


    public PlaylistsStatistics(ObservableList<PieChart.Data> artistsFrequencyObservableList, ObservableList<PieChart.Data> decadesFrequencyObservableList, ObservableList<PieChart.Data> genresFrequencyObservableList, ObservableList<XYChart.Series<Number, Number>> tempoChartObservableList, ObservableList<XYChart.Series<Number, Number>> loudnessChartObservableList, ObservableList<XYChart.Series<String, Number>> familiarityChartObservableList) {
        this.artistsFrequencyObservableList = artistsFrequencyObservableList;
        this.decadesFrequencyObservableList = decadesFrequencyObservableList;
        this.genresFrequencyObservableList = genresFrequencyObservableList;
        this.tempoChartObservableList = tempoChartObservableList;
        this.loudnessChartObservableList = loudnessChartObservableList;
        this.familiarityChartObservableList = familiarityChartObservableList;
    }

    public ObservableList<PieChart.Data> getArtistsFrequencyObservableList() {
        return artistsFrequencyObservableList;
    }

    public void setArtistsFrequencyObservableList(ObservableList<PieChart.Data> artistsFrequencyObservableList) {
        this.artistsFrequencyObservableList = artistsFrequencyObservableList;
    }

    public ObservableList<PieChart.Data> getDecadesFrequencyObservableList() {
        return decadesFrequencyObservableList;
    }

    public void setDecadesFrequencyObservableList(ObservableList<PieChart.Data> decadesFrequencyObservableList) {
        this.decadesFrequencyObservableList = decadesFrequencyObservableList;
    }

    public ObservableList<XYChart.Series<Number, Number>> getTempoChartObservableList() {
        return tempoChartObservableList;
    }

    public void setTempoChartObservableList(ObservableList<XYChart.Series<Number, Number>> tempoChartObservableList) {
        this.tempoChartObservableList = tempoChartObservableList;
    }

    public ObservableList<XYChart.Series<Number, Number>> getLoudnessChartObservableList() {
        return loudnessChartObservableList;
    }

    public void setLoudnessChartObservableList(ObservableList<XYChart.Series<Number, Number>> loudnessChartObservableList) {
        this.loudnessChartObservableList = loudnessChartObservableList;
    }

    public ObservableList<XYChart.Series<String, Number>> getFamiliarityChartObservableList() {
        return familiarityChartObservableList;
    }

    public void setFamiliarityChartObservableList(ObservableList<XYChart.Series<String, Number>> familiarityChartObservableList) {
        this.familiarityChartObservableList = familiarityChartObservableList;
    }

    public ObservableList<PieChart.Data> getGenresFrequencyObservableList() {
        return genresFrequencyObservableList;
    }

    public void setGenresFrequencyObservableList(ObservableList<PieChart.Data> genresFrequencyObservableList) {
        this.genresFrequencyObservableList = genresFrequencyObservableList;
    }
}
