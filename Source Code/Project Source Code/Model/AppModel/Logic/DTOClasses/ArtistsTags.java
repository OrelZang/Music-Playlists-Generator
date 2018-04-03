package Model.AppModel.Logic.DTOClasses;

import java.util.ArrayList;
import java.util.List;

public class ArtistsTags {
    private List<String> artistTagsList;

    public ArtistsTags() {
        artistTagsList=new ArrayList<>();
    }

    public void addArtistTag(String tag){
        artistTagsList.add(tag);
    }

    public List<String> getArtistTagsList() {
        return artistTagsList;
    }

    @Override
    public String toString() {
        return "ArtistsTags{" +
                "artistTagsList=" + artistTagsList +
                '}';
    }
}
