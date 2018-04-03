package Model.AppModel.Logic.DTOClasses;

public class Icon {
    private int iconID;
    private String iconFileName;
    private String iconName;


    public Icon(int iconID, String iconFileName, String iconName) {
        this.iconID = iconID;
        this.iconFileName = iconFileName;
        this.iconName = iconName;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getIconFileName() {
        return iconFileName;
    }

    public void setIconFileName(String iconFileName) {
        this.iconFileName = iconFileName;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    @Override
    public String toString() {
        return "Icon{" +
                "iconID=" + iconID +
                ", iconFileName='" + iconFileName + '\'' +
                '}';
    }
}
