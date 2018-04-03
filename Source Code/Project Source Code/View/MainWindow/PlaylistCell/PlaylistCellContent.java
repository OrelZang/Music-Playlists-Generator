package View.MainWindow.PlaylistCell;

import Model.AppModel.Logic.DTOClasses.PlaylistSong;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.math.BigDecimal;

public class PlaylistCellContent {
    @FXML
    private HBox cell;
    @FXML
    private Text songName;
    @FXML
    private Text songDuration;
    @FXML
    private Text songArtistAndAlbum;
    @FXML
    private Text songYear;
    @FXML
    private Text tempoSimilarity;
    @FXML
    private MaterialDesignIconView tempoSimilarityIcon;
    @FXML
    private Text tempoSimilarityText;
    @FXML
    private Text loudnessSimilarity;
    @FXML
    private MaterialDesignIconView loudnessSimilarityIcon;
    @FXML
    private Text loudnessSimilarityText;
    @FXML
    private Text estimatedHappiness;
    @FXML
    private MaterialDesignIconView estimatedHappinessHappyIcon;
    @FXML
    private MaterialDesignIconView estimatedHappinessSadIcon;
    @FXML
    private Text estimatedHappinessText;


    public PlaylistCellContent()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("playlistCell.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(PlaylistSong playlistSong)
    {
        String songNameValue = playlistSong.getSongName();
        String songArtistAndAlbumValue= playlistSong.getArtistName()+" - "+playlistSong.getAlbumName();
        songNameValue=getTextWithoutParenthesis(songNameValue);
        songArtistAndAlbumValue=getTextWithoutParenthesis(songArtistAndAlbumValue);


        songName.setText(songNameValue);
        songDuration.setText("  ("+playlistSong.getSongDurationStringFormat()+")");
        songArtistAndAlbum.setText(songArtistAndAlbumValue);
        songYear.setText("  ("+String.valueOf(playlistSong.getYear())+")");
        if(playlistSong.getTempoSimilarity()>=0){
            float tempoSimilarityValue=BigDecimal.valueOf(playlistSong.getTempoSimilarity()*100).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
            float loudnessSimilarityValue=BigDecimal.valueOf(playlistSong.getLoudnessSimilarity()*100).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();

            tempoSimilarity.setText(String.valueOf(tempoSimilarityValue)+"%");
            loudnessSimilarity.setText(String.valueOf(loudnessSimilarityValue)+"%");

            tempoSimilarity.setVisible(true);
            tempoSimilarityIcon.setVisible(true);
            tempoSimilarityText.setVisible(true);
            loudnessSimilarity.setVisible(true);
            loudnessSimilarityIcon.setVisible(true);
            loudnessSimilarityText.setVisible(true);
        }else if(playlistSong.getEstimatedHappiness()!=-2){
            Boolean isSad=false;
            float estimatedHappinessInitValue=playlistSong.getEstimatedHappiness();
            if(estimatedHappinessInitValue<0){
                isSad=true;
                estimatedHappinessInitValue=-estimatedHappinessInitValue;
            }
            float estimatedHappinessValue=BigDecimal.valueOf(estimatedHappinessInitValue*100).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();

            estimatedHappiness.setText(String.valueOf(estimatedHappinessValue)+"%");
            estimatedHappiness.setVisible(true);
            estimatedHappinessText.setVisible(true);
            if(!isSad){
                estimatedHappinessHappyIcon.setVisible(true);
            }else{
                estimatedHappinessSadIcon.setVisible(true);
            }

        }


    }

    private String getTextWithoutParenthesis(String text){
        if(text.endsWith(")")){
            int i=text.length()-1;
            while(i>=0&&text.charAt(i)!='('){
                i--;
            }
            text=text.substring(0,i);

        }
        if(text.endsWith("]")){
            int i=text.length()-1;
            while(i>=0&&text.charAt(i)!='['){
                i--;
            }
            text=text.substring(0,i);

        }

        if(text.length()>50){
            text=text.substring(0,50);
        }

        return text;
    }

    public HBox getCell() {
        return cell;
    }
}
