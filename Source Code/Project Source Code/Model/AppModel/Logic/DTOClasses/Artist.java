package Model.AppModel.Logic.DTOClasses;

public class Artist {
    private String artistID;
    private String name;
    private float familiarity;

    public Artist(String artistID, String name, float familiarity) {
        this.artistID = artistID;
        this.name = name;
        this.familiarity = familiarity;
    }

    public Artist(String name) {
        this.name = name;
    }

    public String getArtistID() {
        return artistID;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(float familiarity) {
        this.familiarity = familiarity;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistID='" + artistID + '\'' +
                ", name='" + name + '\'' +
                ", familiarity=" + familiarity +
                '}';
    }
}
