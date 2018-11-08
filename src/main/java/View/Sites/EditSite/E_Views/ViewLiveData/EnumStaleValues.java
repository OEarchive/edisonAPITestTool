package View.Sites.EditSite.E_Views.ViewLiveData;

import java.awt.Color;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Seconds;

public enum EnumStaleValues {
    Green,
    Yellow,
    Red,
    Blue;

    EnumStaleValues() {

    }

    public static EnumStaleValues getEnumValueFromThreshAndDate(int yellowThresh, int redThresh, DateTime pointTimestamp) {

        if (pointTimestamp == null) {
            return Green;
        }

        DateTime rightNow = DateTime.now();
        Seconds secs = Seconds.secondsBetween(pointTimestamp, rightNow );
        int numSecs = secs.getSeconds();
        
        if( numSecs <= 0 ){
            return Blue;
        }

        if (numSecs < yellowThresh) {
            return Green;
        }

        if (numSecs < redThresh) {
            return Yellow;
        }

        return Red;
    }

    public Color getColor() {

        switch (this) {
            case Green:
                return new Color(204, 255, 204);
            case Yellow:
                return new Color(255, 255, 153);
            case Blue:
                return new Color(180, 247, 247);
            default:
                return new Color(255, 204, 204);
        }

    }

}
