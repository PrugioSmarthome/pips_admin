package com.daewooenc.pips.admin.web.util.geo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 기상청 데이터 조회를 위한 기상청 격자정보 - 위경도 변환 Util
 * https://gist.github.com/fronteer-kr/14d7f779d52a21ac2f16 by fronteer.kr
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description     <br/>
 * ------------------------------------------------------------    <br/>
 *    2019-07-24       :      dokim         :                      <br/>
 *
 * </pre>
 * @since : 2019-07-24
 **/
public class GeoGridTrans {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static int TO_GRID = 0;
    public static int TO_GPS = 1;

    /**
     * 기상청 격자정보 변환
     * @param mode
     * @param latX
     * @param lngY
     * @return LatXLngY
     * @throws
     */
    public LatXLngY convertToGridOrGps (int mode, double latX, double lngY ) {
        logger.debug("convertToGridOrGps start");
        logger.debug("convertToGridOrGps is TO_GRID? " + (TO_GRID == mode));

        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)

        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1)  / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        GeoGridTrans.LatXLngY rs = new GeoGridTrans.LatXLngY();

        if (mode == TO_GRID) {
            rs.lat = latX;
            rs.lng = lngY;
            double ra = Math.tan(Math.PI * 0.25 + (latX) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lngY * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else if (mode == TO_GPS) {
            rs.x = latX;
            rs.y = lngY;
            double xn = latX - XO;
            double yn = ro - lngY + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            rs.lat = alat * RADDEG;
            rs.lng = alon * RADDEG;
        }
        return rs;
    }

    public class LatXLngY
    {
        public double lat;
        public double lng;

        public double x;
        public double y;
    }
}