package com.example.myapp.uiview;

public class MathUtil {
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
    private static double EARTH_RADIUS = 6378137;


    public static boolean checkInRound(float sx, float sy, float r, float x, float y) {
//        System.out.println("check-> math = " + Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)));
        return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) <= r;

//        return Math.sqrt(Math.pow(sx, x) + Math.pow(sy, y)) <= r;

    }


    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
//double len=s/EARTH_SEA;
        //s = s / 1000;
        return s;
    }
    public static boolean isInCircle(double lng1, double lat1, double lng2, double lat2, double radius) {
        double distance = getDistance(lat1, lng1, lat2, lng2);
        if (distance > radius) {
            return false;
        } else {
            return true;
        }
    }
    public static int distans(float toFloat, float toFloat1, float toFloat2, float toFloat3) {
        return 0;
    }
}
