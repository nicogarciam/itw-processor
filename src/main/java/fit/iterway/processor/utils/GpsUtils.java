package fit.iterway.processor.utils;

public class GpsUtils {
//
//    North	0°	                South	180°
//    North-northeast	22.5°	South-southwest	202.5°
//    Northeast	45°	            Southwest	225°
//    East-northeast	67.5°	West-southwest	247.5°
//    East	90°	                West	270°
//    East-southeast	112.5°	West-northwest	292.5°
//    Southeast	135°	        Northwest	315°
//    South-southeast	157.5°	North-northwest	337.5°


    public static String getDirecctionFromAzimuth(Float azimuth) {
        String direction = "";
        if (azimuth != null) {
            if (azimuth >= 337.5 && azimuth < 22.5)
                direction = "N";
            else if (azimuth >= 22.5 && azimuth < 67.5)
                direction = "NE";
            else if (azimuth >= 67.5 && azimuth < 112.5)
                direction = "E";
            else if (azimuth >= 112.5 && azimuth < 157.5)
                direction = "SE";
            else if (azimuth >= 157.5 && azimuth < 202.5)
                direction = "S";
            else if (azimuth >= 202.5 && azimuth < 247.5)
                direction = "SW";
            else if (azimuth >= 247.5 && azimuth < 292.5)
                direction = "W";
            else if (azimuth >= 292.5 && azimuth < 337.5)
                direction = "NW";
        }
        return direction;
    }

    //      "M"  Miles
//		"K"  Kilometers
//		"N"  Nautical Miles
    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
}
