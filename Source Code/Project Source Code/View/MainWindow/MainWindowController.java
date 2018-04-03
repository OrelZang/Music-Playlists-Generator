package View.MainWindow;

import Controller.IController;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.DMLStatementExecutor;
import Model.AppModel.Logic.DTOClasses.*;
import View.LoginWindow.LoginWindowCreator;
import View.MainWindow.PlaylistCell.PlaylistCell;
import View.MainWindow.ToolPanel.ToolPanelController;
import View.SettingsWindow.SettingsWindowCreator;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;

import static javafx.scene.paint.Color.WHITE;

public class MainWindowController implements Initializable {



    private IController controller;
    private User user;
    private List<Song> currentTableSong;


    @FXML
    private ImageView userIconImage;
    @FXML
    private Label userNameTextField;
    @FXML
    private ImageView appLogoImage;
    @FXML
    private JFXHamburger drawerButton;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXTabPane playlistsTabPane;
    @FXML
    private JFXTabPane generatorTabPane;
    @FXML
    private StackPane mainStackPane;
    @FXML
    private JFXSpinner loadingAnimation;
    @FXML
    private Pane centerPane;
    @FXML
    private JFXTabPane statisticsTabPane;
    @FXML
    private Tab artistsStatisticsTab;
    @FXML
    private Tab decadesStatisticsTab;
    @FXML
    private Tab genresStatisticsTab;
    @FXML
    private Tab loudnessStatisticsTab;
    @FXML
    private Tab tempoStatisticsTab;
    @FXML
    private Tab familiarityStatisticsTab;
    @FXML
    private PieChart artistsPieChart;
    @FXML
    private PieChart decadesPieChart;
    @FXML
    private PieChart genresPieChart;
    @FXML
    private AnchorPane tempoAnchorPane;
    @FXML
    private AnchorPane loudnessAnchorPane;
    @FXML
    private AnchorPane familiarityAnchorPane;
    @FXML
    private JFXSlider happinessSlider;
    @FXML
    private JFXComboBox genresComboBox;
    @FXML
    private JFXButton generatePreferencesPlaylistButton;
    @FXML
    private AnchorPane preferencesGeneratorAnchorPane;
    @FXML
    private Tab preferencesGeneratorTab;
    @FXML
    private JFXSlider numberOfSongsSlider2;
    @FXML
    private JFXButton signOutButton;
    @FXML
    private JFXTreeTableView songsTable;
    @FXML
    private JFXTextField songsFilterTextField;
    @FXML
    private JFXTextField artistNameTextField;
    @FXML
    private JFXButton searchArtistButton;
    @FXML
    private JFXButton generateSongPlaylistButton;
    @FXML
    private AnchorPane bySongGeneratorAnchorPane;
    @FXML
    private JFXSlider numberOfSongsSlider1;
    @FXML
    private Tab songBasedGeneratorTab;
    @FXML
    private AnchorPane artistsFrequencyAnchorPane;
    @FXML
    public ImageView fullLogoImage;
    @FXML
    private Label emptyText;
    @FXML
    private MaterialDesignIconView emptyIcon;

    private JFXButton generationMenuButton;
    private JFXButton playlistsMenuButton;
    private JFXButton statisticsMenuButton;
    private JFXButton settingsMenuButton;
    private JFXDialog sqlServerDialog;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appLogoImage.setImage(new Image(getClass().getResourceAsStream("logo2.png")));

        fullLogoImage.setImage(new Image(getClass().getResourceAsStream("welcome.png")));
        initDrawer();

        JFXDialogLayout sqlErrorDialogLayout = new JFXDialogLayout();
        sqlErrorDialogLayout.setHeading(new Text("Error"));
        sqlErrorDialogLayout.setBody(new Text("An error occurred with the database server\nPlease try again"));
        sqlServerDialog = new JFXDialog(mainStackPane,sqlErrorDialogLayout,JFXDialog.DialogTransition.CENTER);
        JFXButton closeButton = new JFXButton("Close");
        closeButton.setOnAction(event -> {
            sqlServerDialog.close();
        });
        closeButton.setButtonType(JFXButton.ButtonType.RAISED);

        sqlErrorDialogLayout.setActions(closeButton);

        closeButton.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");

        artistsPieChart.setStyle("-fx-font-size: " + 15 + "px;");
        artistsPieChart.setStyle("-fx-font-weight: bold");
        decadesPieChart.setStyle("-fx-font-size: " + 15 + "px;");
        decadesPieChart.setStyle("-fx-font-weight: bold");
        genresPieChart.setStyle("-fx-font-size: " + 15 + "px;");
        genresPieChart.setStyle("-fx-font-weight: bold");





    }

    public void setTable(){
        JFXTreeTableColumn<SongTableItem,String> songCol = new JFXTreeTableColumn<>("Song");
        JFXTreeTableColumn<SongTableItem,String> artistCol = new JFXTreeTableColumn<>("Artist");
        JFXTreeTableColumn<SongTableItem,String> albumCol = new JFXTreeTableColumn<>("Album");
        JFXTreeTableColumn<SongTableItem,String> yearCol = new JFXTreeTableColumn<>("Year");
        songCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SongTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SongTableItem, String> param) {
                return param.getValue().getValue().nameProperty();
            }
        });
        artistCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SongTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SongTableItem, String> param) {
                return param.getValue().getValue().artistProperty();
            }
        });
        albumCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SongTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SongTableItem, String> param) {
                return param.getValue().getValue().albumProperty();
            }
        });
        yearCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SongTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SongTableItem, String> param) {
                return param.getValue().getValue().yearProperty();
            }
        });

        songCol.prefWidthProperty().bind(songsTable.widthProperty().divide(3.5)); // w * 1/4
        artistCol.prefWidthProperty().bind(songsTable.widthProperty().divide(3.4)); // w * 1/2
        albumCol.prefWidthProperty().bind(songsTable.widthProperty().divide(3.4)); // w * 1/4
        yearCol.prefWidthProperty().bind(songsTable.widthProperty().divide(10)); // w * 1/4

        songsTable.getColumns().addAll(songCol, artistCol, albumCol,yearCol);



        songsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                generateSongPlaylistButton.setDisable(false);
            }
        });


        songsFilterTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                songsTable.setPredicate(new Predicate<TreeItem>() {
                    @Override
                    public boolean test(TreeItem treeItem) {
                        Boolean flag = ((SongTableItem)treeItem.getValue()).nameProperty().getValue().toLowerCase().contains(newValue.toLowerCase()) ||
                                ((SongTableItem)treeItem.getValue()).albumProperty().getValue().toLowerCase().contains(newValue.toLowerCase()) ||
                                ((SongTableItem)treeItem.getValue()).yearProperty().getValue().toLowerCase().contains(newValue.toLowerCase());
                        return  flag;
                    }
                });
            }
        });



    }

    public void setGenresComboBox() throws SQLException {
        ObservableList genresComboboxOptions = FXCollections.observableArrayList();
        ArtistsTags artistsTags=null;
        artistsTags = controller.getAllArtistsTagsInDB();

        List<String> genresList = artistsTags.getArtistTagsList();

        for (String genre : genresList){
            genresComboboxOptions.add(new Label(genre));
        }

        genresComboBox.setItems(genresComboboxOptions);
    }



    public void setPlaylistsView(){
        List<Playlist> playlistsList = user.getPlaylistsList();

        for (Playlist playlist: playlistsList){
            addPlaylist(playlist);

        }


    }

    private void searchSongs(String artistName){
        List<Song> songsList=null;
        try {
            songsList = controller.getSongsByArtistNameFromDB(new Artist(artistName));
        } catch (SQLException e) {
            Platform.runLater(() -> {
                songsTable.setVisible(true);
                centerPane.setVisible(false);
                loadingAnimation.setVisible(false);
                sqlServerDialog.show();

            });
            return;
        }

        currentTableSong=songsList;


        ObservableList<SongTableItem> data = FXCollections.observableArrayList();

        for (Song song :songsList){
            data.add(song.getSongTableItem());
        }

        TreeItem<SongTableItem> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        Platform.runLater(() -> {
            songsTable.setRoot(root);
            songsTable.setShowRoot(false);
            songsTable.setVisible(true);
            centerPane.setVisible(false);
            loadingAnimation.setVisible(false);

        });

    }

    public void onKeyReleasedEventSongFilterTextField(KeyEvent keyEvent) {
        generateSongPlaylistButton.setDisable(true);
    }

    public void onClickEventSearchSongs(ActionEvent actionEvent) {
        songsTable.setVisible(false);
        centerPane.setVisible(true);
        loadingAnimation.setVisible(true);
        songsFilterTextField.clear();
        generateSongPlaylistButton.setDisable(true);
        Thread searchSongsThread = new Thread(() -> searchSongs(artistNameTextField.getText()));
        searchSongsThread.start();
    }


    private void deletePlaylist(List<Playlist> playlistsList, Playlist playlistToDelete,Tab playlistTab){
        try {
            controller.deletePlaylist(playlistToDelete);
            playlistsList.remove(playlistToDelete);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    playlistsTabPane.getTabs().remove(playlistTab);
                    centerPane.setVisible(false);
                    loadingAnimation.setVisible(false);
                }
            });

        } catch (SQLException e) {
            Platform.runLater(() -> {
                centerPane.setVisible(false);
                loadingAnimation.setVisible(false);
                sqlServerDialog.show();
            });
        }
    }

    private void initStatisticsFX(PlaylistsStatistics playlistsStatistics){


        artistsStatisticsTab.setOnSelectionChanged(t -> {

            if (artistsStatisticsTab.isSelected()) {
                ObservableList<PieChart.Data> artistsFrequencyObservableList = playlistsStatistics.getArtistsFrequencyObservableList();

                artistsPieChart.setData(artistsFrequencyObservableList);

            }
        });

        decadesStatisticsTab.setOnSelectionChanged(event -> {
            if(decadesStatisticsTab.isSelected()){
                ObservableList<PieChart.Data> decadesFrequencyObservableList = playlistsStatistics.getDecadesFrequencyObservableList();
                decadesPieChart.setData(decadesFrequencyObservableList);
            }
        });

        genresStatisticsTab.setOnSelectionChanged(event -> {
            if(genresStatisticsTab.isSelected()){
                ObservableList<PieChart.Data> genresFrequencyObservableList = playlistsStatistics.getGenresFrequencyObservableList();
                genresPieChart.setData(genresFrequencyObservableList);



            }
        });

        tempoStatisticsTab.setOnSelectionChanged(event -> {
            if(tempoStatisticsTab.isSelected()){

                ObservableList<XYChart.Series<Number,Number>> tempoChartObservableList=playlistsStatistics.getTempoChartObservableList();

                NumberAxis xAxis = new NumberAxis();
                NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel("Song Number");
                yAxis.setLabel("Song Tempo (BPM)");

                xAxis.setAutoRanging(false);
                xAxis.setLowerBound(0);
                xAxis.setUpperBound(21);
                xAxis.setTickUnit(1);

                yAxis.setAutoRanging(false);
                yAxis.setLowerBound(0);
                yAxis.setUpperBound(275);
                yAxis.setTickUnit(25);

                //creating the chart
                LineChart<Number,Number> tempoLineChart = new LineChart<>(xAxis, yAxis);
                tempoLineChart.setTranslateX(154);
                tempoLineChart.setTranslateY(20);
                tempoLineChart.setPrefWidth(675);
                tempoLineChart.setPrefHeight(530);
                tempoAnchorPane.getChildren().clear();
                tempoAnchorPane.getChildren().add(tempoLineChart);

                tempoLineChart.setStyle("-fx-font-size: " + 15 + "px;");
                tempoLineChart.setStyle("-fx-font-weight: bold");
                tempoLineChart.setTitle("           Playlists Tempo Progression");
                tempoLineChart.setData(tempoChartObservableList);

                MaterialDesignIconView tempoIcon = new MaterialDesignIconView(MaterialDesignIcon.CHART_LINE);
                tempoIcon.setSize("22");
                tempoIcon.setTranslateX(375);
                tempoIcon.setTranslateY(45);
                tempoAnchorPane.getChildren().add(tempoIcon);

            }
        });

        loudnessStatisticsTab.setOnSelectionChanged(event -> {
            if(loudnessStatisticsTab.isSelected()){

                ObservableList<XYChart.Series<Number,Number>> loudnessChartObservableList=playlistsStatistics.getLoudnessChartObservableList();
                NumberAxis xAxis = new NumberAxis();
                NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel("Song Number");
                yAxis.setLabel("Song Loudness (dB)");

                xAxis.setAutoRanging(false);
                xAxis.setLowerBound(0);
                xAxis.setUpperBound(21);
                xAxis.setTickUnit(1);

                yAxis.setAutoRanging(false);
                yAxis.setLowerBound(-56);
                yAxis.setUpperBound(4);
                yAxis.setTickUnit(5);


                //creating the chart
                LineChart<Number,Number> loudnessLineChart = new LineChart<>(xAxis, yAxis);
                loudnessLineChart.setTranslateX(154);
                loudnessLineChart.setTranslateY(20);
                loudnessLineChart.setPrefWidth(675);
                loudnessLineChart.setPrefHeight(530);
                loudnessAnchorPane.getChildren().clear();
                loudnessAnchorPane.getChildren().add(loudnessLineChart);

                loudnessLineChart.setStyle("-fx-font-size: " + 15 + "px;");
                loudnessLineChart.setStyle("-fx-font-weight: bold");
                loudnessLineChart.setTitle("            Playlists Loudness Progression");
                loudnessLineChart.setData(loudnessChartObservableList);

                MaterialDesignIconView loudnessIcon = new MaterialDesignIconView(MaterialDesignIcon.VOLUME_HIGH);
                loudnessIcon.setSize("22");
                loudnessIcon.setTranslateX(373);
                loudnessIcon.setTranslateY(46);
                loudnessAnchorPane.getChildren().add(loudnessIcon);

            }
        });

        familiarityStatisticsTab.setOnSelectionChanged(event -> {
            if(familiarityStatisticsTab.isSelected()){

                ObservableList<XYChart.Series<String,Number>> familiarityChartObservableList=playlistsStatistics.getFamiliarityChartObservableList();
                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();




                xAxis.setLabel("Playlist");
                yAxis.setLabel("Average Artist Familiarity Value");

                yAxis.setAutoRanging(false);
                yAxis.setLowerBound(0);
                yAxis.setUpperBound(1);
                yAxis.setTickUnit(0.05);

                //creating the chart
                BarChart<String,Number> familiarityBarChart = new BarChart<String,Number>(xAxis,yAxis);
                familiarityBarChart.setTranslateX(154);
                familiarityBarChart.setTranslateY(20);
                familiarityBarChart.setPrefWidth(675);
                familiarityBarChart.setPrefHeight(530);
                familiarityAnchorPane.getChildren().clear();
                familiarityAnchorPane.getChildren().add(familiarityBarChart);

                familiarityBarChart.setStyle("-fx-font-size: " + 15 + "px;");
                familiarityBarChart.setStyle("-fx-font-weight: bold");
                familiarityBarChart.setTitle("          Playlists Average Artist Familiarity");
                familiarityBarChart.setData(familiarityChartObservableList);

                MaterialDesignIconView familiarityIcon = new MaterialDesignIconView(MaterialDesignIcon.CHART_BAR);
                familiarityIcon.setSize("22");
                familiarityIcon.setTranslateX(350);
                familiarityIcon.setTranslateY(45);
                familiarityAnchorPane.getChildren().add(familiarityIcon);


            }
        });
        centerPane.setVisible(false);
        loadingAnimation.setVisible(false);
        generatorTabPane.setVisible(false);
        statisticsTabPane.setVisible(true);
        playlistsTabPane.setVisible(false);
        statisticsTabPane.getSelectionModel().select(artistsStatisticsTab);
        ObservableList<PieChart.Data> artistsFrequencyObservableList1 = playlistsStatistics.getArtistsFrequencyObservableList();
        artistsPieChart.setData(artistsFrequencyObservableList1);
    }

    private void initStatistics(){
        PlaylistsStatistics playlistsStatistics=null;
        try {
            playlistsStatistics = controller.getPlaylistsStatistics(user,user.getPlaylistsList());
            PlaylistsStatistics finalPlaylistsStatistics = playlistsStatistics;
            Platform.runLater(() -> {
                initStatisticsFX(finalPlaylistsStatistics);
            });
        } catch (SQLException e) {
            Platform.runLater(() -> {
                centerPane.setVisible(false);
                loadingAnimation.setVisible(false);
                sqlServerDialog.show();
            });
        }

    }

    private void initDrawer(){

        try {
            FXMLLoader fxmlLoader = null;
            fxmlLoader = new FXMLLoader(getClass().getResource("ToolPanel/toolPanel.fxml"));
            VBox toolbar = fxmlLoader.load();
            ToolPanelController toolPanelController = fxmlLoader.getController();
            generationMenuButton=toolPanelController.getGenerationMenuButton();
            playlistsMenuButton=toolPanelController.getPlaylistsMenuButton();
            statisticsMenuButton=toolPanelController.getStatisticsMenuButton();
            settingsMenuButton=toolPanelController.getSettingsMenuButton();
            drawer.setSidePane(toolbar);
            drawer.setPrefHeight(375);
            drawer.setMaxHeight(375);
            drawer.setMinHeight(375);
            drawer.setPrefWidth(143);
            drawer.setMaxWidth(143);
            drawer.setMinWidth(143);
            drawer.setTranslateX(-13);
            drawer.setTranslateY(-95);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(drawerButton);
        task.setRate(-1);
        drawerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
            task.setRate(task.getRate()*-1);
            task.play();
            if (drawer.isHidden()){
                drawer.open();
            }else{
                drawer.close();
            }
        });

        generationMenuButton.setOnAction(event -> {
            task.setRate(task.getRate()*-1);
            task.play();
            drawer.close();
            emptyIcon.setVisible(false);
            emptyText.setVisible(false);
            centerPane.setVisible(false);
            fullLogoImage.setVisible(false);

            statisticsTabPane.setVisible(false);
            playlistsTabPane.setVisible(false);
            generatorTabPane.getSelectionModel().select(songBasedGeneratorTab);
            genresComboBox.setValue(null);
            happinessSlider.setValue(0);
            numberOfSongsSlider2.setValue(10);
            generatePreferencesPlaylistButton.setDisable(true);
            preferencesGeneratorTab.setContent(preferencesGeneratorAnchorPane);
            preferencesGeneratorAnchorPane.setVisible(true);
            artistNameTextField.clear();
            songsFilterTextField.clear();
            songsTable.setRoot(null);
            numberOfSongsSlider1.setValue(10);
            generateSongPlaylistButton.setDisable(true);
            songBasedGeneratorTab.setContent(bySongGeneratorAnchorPane);
            bySongGeneratorAnchorPane.setVisible(true);
            generatorTabPane.setVisible(true);


        });

        playlistsMenuButton.setOnAction(event -> {
            task.setRate(task.getRate()*-1);
            task.play();
            drawer.close();
            centerPane.setVisible(false);
            fullLogoImage.setVisible(false);
            generatorTabPane.setVisible(false);
            statisticsTabPane.setVisible(false);
            if(user.getPlaylistsList().size()==0){
                centerPane.setVisible(true);
                emptyIcon.setVisible(true);
                emptyText.setVisible(true);
            }
            try{
                playlistsTabPane.getSelectionModel().select(playlistsTabPane.getTabs().get(0));
            }catch (Exception e){

            }
            playlistsTabPane.setVisible(true);

        });

        statisticsMenuButton.setOnAction(event -> {
            task.setRate(task.getRate()*-1);
            task.play();
            drawer.close();
            if(playlistsTabPane.getTabs().size()==0){

                centerPane.setVisible(false);
                fullLogoImage.setVisible(false);
                generatorTabPane.setVisible(false);
                statisticsTabPane.setVisible(false);
                playlistsTabPane.setVisible(false);
                centerPane.setVisible(true);
                emptyIcon.setVisible(true);
                emptyText.setVisible(true);

                return;
            }

            Thread initStatisticsThread = new Thread(this::initStatistics);
            initStatisticsThread.start();
            centerPane.setVisible(false);
            fullLogoImage.setVisible(false);
            generatorTabPane.setVisible(false);
            statisticsTabPane.setVisible(false);
            playlistsTabPane.setVisible(false);
            centerPane.setVisible(true);
            loadingAnimation.setVisible(true);
        });

        settingsMenuButton.setOnAction(event -> {
            task.setRate(task.getRate()*-1);
            task.play();
            drawer.close();
            SettingsWindowCreator settingsWindowCreator = new SettingsWindowCreator();
            settingsWindowCreator.launchWindow();
        });
    }

    void prepareUserInfoAppBar(){
        userNameTextField.setText(user.getName());
        userIconImage.setImage(new Image(getClass().getResourceAsStream(user.getIcon().getIconFileName())));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public void onSelectedEventGenreComboBox(ActionEvent actionEvent) {
        generatePreferencesPlaylistButton.setDisable(false);
    }

    public void addPlaylist(Playlist playlist){
        Tab playlistTab = new Tab(playlist.getPlaylistName());

        Platform.runLater(() -> {
            playlistsTabPane.getTabs().add(playlistTab);
        });
        AnchorPane anchorPane = new AnchorPane();
        playlistTab.setContent(anchorPane);
        MaterialIconView tabIcon = new MaterialIconView(MaterialIcon.QUEUE_MUSIC);
        tabIcon.setFill(WHITE);
        tabIcon.setSize("20");
        List<PlaylistSong> playlistSongList = playlist.getPlaylistSongsList();

        for( PlaylistSong playlistSong : playlistSongList){
            playlistSong.setTempoSimilarity(-1);
            playlistSong.setEstimatedHappiness(-2);
        }



        Platform.runLater(() -> {
            playlistTab.setGraphic(tabIcon);
        });


        ObservableList observableList = FXCollections.observableArrayList();

        observableList.setAll(playlist.getPlaylistSongsList());
        JFXListView<PlaylistSong> playlistSongsListview = new JFXListView<>();
        playlistSongsListview.setCellFactory(new Callback<ListView<PlaylistSong>, javafx.scene.control.ListCell<PlaylistSong>>()
        {
            @Override
            public ListCell<PlaylistSong> call(ListView<PlaylistSong> listView)
            {
                return new PlaylistCell();
            }
        });
        playlistSongsListview.setItems(observableList);
        playlistSongsListview.depthProperty().set(1);
        playlistSongsListview.setPrefHeight(375);

        playlistSongsListview.setPrefWidth(600);

        playlistSongsListview.setTranslateX(200);
        playlistSongsListview.setTranslateY(30);



        MaterialDesignIconView clockIcon = new MaterialDesignIconView(MaterialDesignIcon.CLOCK);
        clockIcon.setSize("40");
        clockIcon.setTranslateX(320+25);
        clockIcon.setTranslateY(460);

        Label totalDurationLabel = new Label("Total Playlist Duration: "+playlist.getPlaylistDurationStringFormat());
        totalDurationLabel.setTranslateX(368+25);
        totalDurationLabel.setTranslateY(431);
        totalDurationLabel.setStyle("-fx-font-weight: bold");
        totalDurationLabel.setFont(Font.font ("Ariel", 20));

        JFXDialogLayout deletePlaylistDialogLayout = new JFXDialogLayout();
        deletePlaylistDialogLayout.setHeading(new Text("Delete Playlist: \""+playlist.getPlaylistName()+"\""));
        deletePlaylistDialogLayout.setBody(new Text("Are you sure you want to delete this playlist?"));
        final JFXDialog[] deletePlaylisDialog = {null};
        Platform.runLater(() -> {
            deletePlaylisDialog[0] = new JFXDialog(mainStackPane,deletePlaylistDialogLayout,JFXDialog.DialogTransition.CENTER);
        });



        JFXButton yesButton = new JFXButton("Delete");
        yesButton.setOnAction(event -> {
            deletePlaylisDialog[0].close();
            centerPane.setVisible(true);
            loadingAnimation.setVisible(true);

            Thread deletePlaylistThread = new Thread(() -> deletePlaylist(user.getPlaylistsList(),playlist,playlistTab));
            deletePlaylistThread.start();
        });
        yesButton.setButtonType(JFXButton.ButtonType.RAISED);



        yesButton.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");

        JFXButton noButton = new JFXButton("Cancel");
        noButton.setOnAction(event -> {
            deletePlaylisDialog[0].close();
        });
        noButton.setButtonType(JFXButton.ButtonType.RAISED);

        deletePlaylistDialogLayout.setActions(Arrays.asList(noButton,yesButton));



        noButton.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");



        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("deletePlaylistButton.fxml"));
        JFXButton deletePlaylistButton = null;
        try {
            deletePlaylistButton = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        deletePlaylistButton.setTranslateX(420+25);
        deletePlaylistButton.setTranslateY(485);
        deletePlaylistButton.setOnAction(event -> {
            deletePlaylisDialog[0].show();

        });


        JFXButton finalDeletePlaylistButton = deletePlaylistButton;

        Platform.runLater(() -> {
            anchorPane.getChildren().add(playlistSongsListview);
            anchorPane.getChildren().add(clockIcon);
            anchorPane.getChildren().add(totalDurationLabel);
            anchorPane.getChildren().add(finalDeletePlaylistButton);
        });

    }

    private void addPlaylist(Playlist newPlaylist,JFXDialog finalPlaylistSavedDialog,AnchorPane generatedPlaylistAnchorPane){
        Platform.runLater(() -> {
            centerPane.setVisible(true);
            loadingAnimation.setVisible(true);
            generatedPlaylistAnchorPane.setVisible(false);
        });

        try {
            controller.addPlaylistToDB(newPlaylist);
        } catch (SQLException e) {
            Platform.runLater(() -> {
                centerPane.setVisible(false);
                loadingAnimation.setVisible(false);
                sqlServerDialog.show();
                generatedPlaylistAnchorPane.setVisible(true);
            });
            return;

        }
        Platform.runLater(() -> {
            centerPane.setVisible(false);
            loadingAnimation.setVisible(false);
            generatedPlaylistAnchorPane.setVisible(true);
        });



        user.getPlaylistsList().add(newPlaylist);
        addPlaylist(newPlaylist);


        Platform.runLater(finalPlaylistSavedDialog::show);

    }

    private void generatePlaylistByPreferences(PlaylistPreferences playlistPreferences){
        List<PlaylistSong> playlistSongList=null;
        try {
            playlistSongList = controller.generatePlaylistByPreferences(playlistPreferences);
        } catch (SQLException e) {
            Platform.runLater(() -> {
                centerPane.setVisible(false);
                loadingAnimation.setVisible(false);
                sqlServerDialog.show();
                preferencesGeneratorAnchorPane.setVisible(true);

            });
            return;
        }

        List<PlaylistSong> finalPlaylistSongList1 = playlistSongList;
        Platform.runLater(() -> {

            AnchorPane anchorPane = new AnchorPane();
            preferencesGeneratorTab.setContent(anchorPane);

            ObservableList observableList = FXCollections.observableArrayList();


            observableList.setAll(finalPlaylistSongList1);
            JFXListView<PlaylistSong> playlistSongsListview = new JFXListView<>();
            playlistSongsListview.setCellFactory(new Callback<ListView<PlaylistSong>, javafx.scene.control.ListCell<PlaylistSong>>()
            {
                @Override
                public ListCell<PlaylistSong> call(ListView<PlaylistSong> listView)
                {
                    return new PlaylistCell();
                }
            });
            playlistSongsListview.setItems(observableList);
            playlistSongsListview.depthProperty().set(1);
            playlistSongsListview.setPrefHeight(300);

            playlistSongsListview.setPrefWidth(700);

            playlistSongsListview.setTranslateX(175);
            playlistSongsListview.setTranslateY(100);

            Label title = new Label("Generated Playlist For: ");
            title.setTranslateX(325+35);
            title.setTranslateY(25-10);
            title.setStyle("-fx-font-weight: bold");
            title.setFont(Font.font ("Ariel", 20));
            Label title2 = new Label("Genre: "+playlistPreferences.getGenre());
            title2.setTranslateX(325+35);
            title2.setTranslateY(50-10);
            title2.setStyle("-fx-font-weight: bold");
            title2.setFont(Font.font ("Ariel", 20));
            Label title3 = new Label("Happiness: "+playlistPreferences.getHappines());
            title3.setTranslateX(325+35);
            title3.setTranslateY(75-10);
            title3.setStyle("-fx-font-weight: bold");
            title3.setFont(Font.font ("Ariel", 20));
            MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.BOOKMARK_MUSIC);
            icon.setSize("40");
            icon.setTranslateX(280+35);
            icon.setTranslateY(69);



            MaterialDesignIconView clockIcon = new MaterialDesignIconView(MaterialDesignIcon.CLOCK);
            clockIcon.setSize("40");
            clockIcon.setTranslateX(320+25);
            clockIcon.setTranslateY(455);

            Label totalDurationLabel = new Label("Total Playlist Duration: "+Playlist.getPlaylistDurationStringFormat(finalPlaylistSongList1));
            totalDurationLabel.setTranslateX(368+25);
            totalDurationLabel.setTranslateY(426);
            totalDurationLabel.setStyle("-fx-font-weight: bold");
            totalDurationLabel.setFont(Font.font ("Ariel", 20));


            JFXDialogLayout playlistSavedDialogLayout = new JFXDialogLayout();
            playlistSavedDialogLayout.setHeading(new Text("Playlist Saved"));
            playlistSavedDialogLayout.setBody(new Text("You can find your new playlist in the \"My Playlists\" section"));
            JFXDialog playlistSavedDialog = null;
            playlistSavedDialog = new JFXDialog(mainStackPane,playlistSavedDialogLayout,JFXDialog.DialogTransition.CENTER);

            JFXButton closeButton = new JFXButton("Close");
            JFXDialog finalPlaylistSavedDialog1 = playlistSavedDialog;
            closeButton.setOnAction(event -> {
                finalPlaylistSavedDialog1.close();
            });
            closeButton.setButtonType(JFXButton.ButtonType.RAISED);

            playlistSavedDialogLayout.setActions(closeButton);

            closeButton.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");




            JFXDialogLayout savePlaylistDialogLayout = new JFXDialogLayout();
            savePlaylistDialogLayout.setHeading(new Text("Save Playlist"));
            JFXTextField playlistNameTextField= new JFXTextField();
            playlistNameTextField.setPromptText("Enter Playlist Name");

            savePlaylistDialogLayout.setBody(playlistNameTextField);
            JFXDialog savePlaylisDialog = null;
            savePlaylisDialog = new JFXDialog(mainStackPane,savePlaylistDialogLayout,JFXDialog.DialogTransition.CENTER);



            JFXButton saveButton = new JFXButton("Save");
            saveButton.setDisable(true);
            JFXDialog finalPlaylistSavedDialog = playlistSavedDialog;
            JFXDialog finalSavePlaylisDialog2 = savePlaylisDialog;
            List<PlaylistSong> finalPlaylistSongList = finalPlaylistSongList1;
            saveButton.setOnAction(event -> {
                finalSavePlaylisDialog2.close();
                Playlist newPlaylist = new Playlist(user.getUserID(),playlistNameTextField.getText(), finalPlaylistSongList);
                Thread addPlaylistThread = new Thread(() -> addPlaylist(newPlaylist,finalPlaylistSavedDialog,anchorPane));
                addPlaylistThread.start();


            });
            saveButton.setButtonType(JFXButton.ButtonType.RAISED);

            playlistNameTextField.setOnKeyReleased(event -> {
                if(playlistNameTextField.getText().length()>0){
                    saveButton.setDisable(false);
                }else{
                    saveButton.setDisable(true);
                }
            });

            saveButton.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");

            JFXButton cancelButton = new JFXButton("Cancel");
            JFXDialog finalSavePlaylisDialog = savePlaylisDialog;
            cancelButton.setOnAction(event -> {
                finalSavePlaylisDialog.close();
            });
            cancelButton.setButtonType(JFXButton.ButtonType.RAISED);

            savePlaylistDialogLayout.setActions(Arrays.asList(cancelButton,saveButton));



            cancelButton.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");







            FXMLLoader fxmlLoader;
            fxmlLoader = new FXMLLoader(getClass().getResource("restartGenerationButton.fxml"));
            JFXButton restartButton = null;
            try {
                restartButton = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            restartButton.setTranslateX(420+25);
            restartButton.setTranslateY(517);
            restartButton.setOnAction(event -> {
                genresComboBox.setValue(null);
                happinessSlider.setValue(0);
                numberOfSongsSlider2.setValue(10);
                generatePreferencesPlaylistButton.setDisable(true);
                preferencesGeneratorTab.setContent(preferencesGeneratorAnchorPane);
                preferencesGeneratorAnchorPane.setVisible(true);

            });

            fxmlLoader = new FXMLLoader(getClass().getResource("savePlaylistButton.fxml"));
            JFXButton savePlaylistButton = null;
            try {
                savePlaylistButton = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            savePlaylistButton.setTranslateX(420+25);
            savePlaylistButton.setTranslateY(472);
            JFXDialog finalSavePlaylisDialog1 = savePlaylisDialog;
            savePlaylistButton.setOnAction(event -> {
                saveButton.setDisable(true);
                playlistNameTextField.setText("");
                finalSavePlaylisDialog1.show();

            });






            JFXButton finalRestartButton = restartButton;
            JFXButton finalSavePlaylistButton = savePlaylistButton;
            anchorPane.getChildren().add(title);
            anchorPane.getChildren().add(title2);
            anchorPane.getChildren().add(title3);
            anchorPane.getChildren().add(icon);

            anchorPane.getChildren().add(playlistSongsListview);
            anchorPane.getChildren().add(clockIcon);
            anchorPane.getChildren().add(totalDurationLabel);
            anchorPane.getChildren().add(finalRestartButton);
            anchorPane.getChildren().add(finalSavePlaylistButton);

            centerPane.setVisible(false);
            loadingAnimation.setVisible(false);
        });




    }


    public void onClickEventGeneratePlaylistByPreferences(ActionEvent actionEvent) {
        preferencesGeneratorAnchorPane.setVisible(false);
        centerPane.setVisible(true);
        loadingAnimation.setVisible(true);

        PlaylistPreferences playlistPreferences = new PlaylistPreferences(  ((Label)genresComboBox.getValue()).getText(), (int) happinessSlider.getValue(), (int) numberOfSongsSlider2.getValue());

        Thread generatePlaylistThread = new Thread(() -> generatePlaylistByPreferences(playlistPreferences));
        generatePlaylistThread.start();


    }


    public void onClickEventSignOut(ActionEvent actionEvent) {
        LoginWindowCreator mainWindowCreator = new LoginWindowCreator();
        try {
            mainWindowCreator.launchWindow(actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void generatePlaylistBySong(PlaylistPreferences playlistPreferences){
        List<PlaylistSong> playlistSongList=null;
        try {
            playlistSongList = controller.generatePlaylistBySong(playlistPreferences);
        } catch (SQLException e) {
            Platform.runLater(() -> {
                centerPane.setVisible(false);
                loadingAnimation.setVisible(false);
                sqlServerDialog.show();
                bySongGeneratorAnchorPane.setVisible(true);

            });
            return;
        }

        List<PlaylistSong> finalPlaylistSongList1 = playlistSongList;
        Platform.runLater(() -> {

            AnchorPane anchorPane = new AnchorPane();
            songBasedGeneratorTab.setContent(anchorPane);

            ObservableList observableList = FXCollections.observableArrayList();


            observableList.setAll(finalPlaylistSongList1);
            JFXListView<PlaylistSong> playlistSongsListview = new JFXListView<>();
            playlistSongsListview.setCellFactory(new Callback<ListView<PlaylistSong>, javafx.scene.control.ListCell<PlaylistSong>>()
            {
                @Override
                public ListCell<PlaylistSong> call(ListView<PlaylistSong> listView)
                {
                    return new PlaylistCell();
                }
            });
            playlistSongsListview.setItems(observableList);
            playlistSongsListview.depthProperty().set(1);
            playlistSongsListview.setPrefHeight(300);

            playlistSongsListview.setPrefWidth(700);

            playlistSongsListview.setTranslateX(175);
            playlistSongsListview.setTranslateY(100);

            String songName=playlistPreferences.getReferenceSong().getName();
            if(songName.endsWith(")")){
                int i=songName.length()-1;
                while(i>=0&&songName.charAt(i)!='('){
                    i--;
                }
                songName=songName.substring(0,i);

            }
            if(songName.endsWith("]")){
                int i=songName.length()-1;
                while(i>=0&&songName.charAt(i)!='['){
                    i--;
                }
                songName=songName.substring(0,i);

            }


            Label title = new Label("Generated Playlist For: ");
            title.setTranslateX(325+35);
            title.setTranslateY(25);
            title.setStyle("-fx-font-weight: bold");
            title.setFont(Font.font ("Ariel", 20));
            Label title2 = new Label(songName+" - "+playlistPreferences.getReferenceSong().getArtist().getName());
            title2.setTranslateX(325+35);
            title2.setTranslateY(50);
            title2.setStyle("-fx-font-weight: bold");
            title2.setFont(Font.font ("Ariel", 20));
            MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.BOOKMARK_MUSIC);
            icon.setSize("40");
            icon.setTranslateX(280+35);
            icon.setTranslateY(69);


            MaterialDesignIconView clockIcon = new MaterialDesignIconView(MaterialDesignIcon.CLOCK);
            clockIcon.setSize("40");
            clockIcon.setTranslateX(320+25);
            clockIcon.setTranslateY(455);

            Label totalDurationLabel = new Label("Total Playlist Duration: "+Playlist.getPlaylistDurationStringFormat(finalPlaylistSongList1));
            totalDurationLabel.setTranslateX(368+25);
            totalDurationLabel.setTranslateY(426);
            totalDurationLabel.setStyle("-fx-font-weight: bold");
            totalDurationLabel.setFont(Font.font ("Ariel", 20));


            JFXDialogLayout playlistSavedDialogLayout = new JFXDialogLayout();
            playlistSavedDialogLayout.setHeading(new Text("Playlist Saved"));
            playlistSavedDialogLayout.setBody(new Text("You can find your new playlist in the \"My Playlists\" section"));
            JFXDialog playlistSavedDialog = null;
            playlistSavedDialog = new JFXDialog(mainStackPane,playlistSavedDialogLayout,JFXDialog.DialogTransition.CENTER);

            JFXButton closeButton = new JFXButton("Close");
            JFXDialog finalPlaylistSavedDialog1 = playlistSavedDialog;
            closeButton.setOnAction(event -> {
                finalPlaylistSavedDialog1.close();
            });
            closeButton.setButtonType(JFXButton.ButtonType.RAISED);

            playlistSavedDialogLayout.setActions(closeButton);

            closeButton.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");




            JFXDialogLayout savePlaylistDialogLayout = new JFXDialogLayout();
            savePlaylistDialogLayout.setHeading(new Text("Save Playlist"));
            JFXTextField playlistNameTextField= new JFXTextField();
            playlistNameTextField.setPromptText("Enter Playlist Name");

            savePlaylistDialogLayout.setBody(playlistNameTextField);
            JFXDialog savePlaylisDialog = null;
            savePlaylisDialog = new JFXDialog(mainStackPane,savePlaylistDialogLayout,JFXDialog.DialogTransition.CENTER);



            JFXButton saveButton = new JFXButton("Save");
            saveButton.setDisable(true);
            JFXDialog finalPlaylistSavedDialog = playlistSavedDialog;
            JFXDialog finalSavePlaylisDialog2 = savePlaylisDialog;
            List<PlaylistSong> finalPlaylistSongList = finalPlaylistSongList1;
            saveButton.setOnAction(event -> {
                finalSavePlaylisDialog2.close();
                Playlist newPlaylist = new Playlist(user.getUserID(),playlistNameTextField.getText(), finalPlaylistSongList);
                Thread addPlaylistThread = new Thread(() -> addPlaylist(newPlaylist,finalPlaylistSavedDialog,anchorPane));
                addPlaylistThread.start();


            });
            saveButton.setButtonType(JFXButton.ButtonType.RAISED);

            playlistNameTextField.setOnKeyReleased(event -> {
                if(playlistNameTextField.getText().length()>0){
                    saveButton.setDisable(false);
                }else{
                    saveButton.setDisable(true);
                }
            });

            saveButton.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");

            JFXButton cancelButton = new JFXButton("Cancel");
            JFXDialog finalSavePlaylisDialog = savePlaylisDialog;
            cancelButton.setOnAction(event -> {
                finalSavePlaylisDialog.close();
            });
            cancelButton.setButtonType(JFXButton.ButtonType.RAISED);

            savePlaylistDialogLayout.setActions(Arrays.asList(cancelButton,saveButton));



            cancelButton.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");


            FXMLLoader fxmlLoader;
            fxmlLoader = new FXMLLoader(getClass().getResource("restartGenerationButton.fxml"));
            JFXButton restartButton = null;
            try {
                restartButton = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            restartButton.setTranslateX(420+25);
            restartButton.setTranslateY(517);
            restartButton.setOnAction(event -> {
                artistNameTextField.clear();
                songsFilterTextField.clear();
                songsTable.setRoot(null);
                numberOfSongsSlider1.setValue(10);
                generateSongPlaylistButton.setDisable(true);
                songBasedGeneratorTab.setContent(bySongGeneratorAnchorPane);
                bySongGeneratorAnchorPane.setVisible(true);

            });

            fxmlLoader = new FXMLLoader(getClass().getResource("savePlaylistButton.fxml"));
            JFXButton savePlaylistButton = null;
            try {
                savePlaylistButton = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            savePlaylistButton.setTranslateX(420+25);
            savePlaylistButton.setTranslateY(472);
            JFXDialog finalSavePlaylisDialog1 = savePlaylisDialog;
            savePlaylistButton.setOnAction(event -> {
                saveButton.setDisable(true);
                playlistNameTextField.setText("");
                finalSavePlaylisDialog1.show();

            });


            JFXButton finalRestartButton = restartButton;
            JFXButton finalSavePlaylistButton = savePlaylistButton;
            anchorPane.getChildren().add(title);
            anchorPane.getChildren().add(title2);
            anchorPane.getChildren().add(icon);
            anchorPane.getChildren().add(playlistSongsListview);
            anchorPane.getChildren().add(clockIcon);
            anchorPane.getChildren().add(totalDurationLabel);
            anchorPane.getChildren().add(finalRestartButton);
            anchorPane.getChildren().add(finalSavePlaylistButton);

            centerPane.setVisible(false);
            loadingAnimation.setVisible(false);
        });




    }


    public void onClickEventGeneratePlaylistBySong(ActionEvent actionEvent) {


        RecursiveTreeItem recursiveTreeItem = (RecursiveTreeItem) songsTable.getSelectionModel().getSelectedItem();
        SongTableItem selectedItem = (SongTableItem) recursiveTreeItem.getValue();
        for(Song song: currentTableSong){
            if (selectedItem.getName().equals(song.getName())  &  selectedItem.getArtist().equals(song.getArtist().getName()) &&
                    selectedItem.getAlbum().equals(song.getAlbumName()) && selectedItem.getYear().equals(String.valueOf(song.getYear()))  ){

                PlaylistPreferences playlistPreferences = new PlaylistPreferences(  (int) numberOfSongsSlider1.getValue(),song);

                bySongGeneratorAnchorPane.setVisible(false);
                centerPane.setVisible(true);
                loadingAnimation.setVisible(true);

                Thread generatePlaylistThread = new Thread(() -> generatePlaylistBySong(playlistPreferences));
                generatePlaylistThread.start();
                return;

            }
        }






    }
}
