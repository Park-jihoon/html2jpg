package com.ponin.hwp.utils;

import com.ponin.hwp.domain.ImageBean;

import java.util.Comparator;

/**
 * Created by Administrator on 2015-06-12.
 */
public class EtcUtil {

    // 1HU = 25.4mm / 7200
    // 1mm = 1HU * 7200 / 25.4


    public static int toPx(long x) {
        return (int) Math.ceil((x * 96) / 7200);
//        return (int)x;
    }

    public static double hu2mm(long x) {
        return (x * 25.4) / 7200 ;
    }

    /**
     * 이름 오름차순
     * @author falbb
     *
     */
    public static class NameAscCompare implements Comparator<ImageBean> {

        /**
         * 오름차순(ASC)
         */
        @Override
        public int compare(ImageBean arg0, ImageBean arg1) {
            // TODO Auto-generated method stub
            return arg0.getPath().compareTo(arg1.getPath());
        }

    }
    /**
     * retieves integer from partial bits indicated by [from, from + length] inclusive
     * <pre>
     *    from = 3, length = 6
     *
     *            L     F
     *     01000111 01000111
     *            1 01000    => 40
     * </pre>
     * @param val
     * @param from
     * @param length
     * @return
     */
    public static int getBits(long val, int from, int length){
        long v = val << (32-from-length);
        v = v >>> (32-length);
        return (int) v ;
    }

    public static String make4Chid(long ctrlID) {
        return String.format("%s%s%s%s",
                (char) ((ctrlID & 0xFF000000L) >> 24),
                (char) ((ctrlID & 0x00FF0000L) >> 16),
                (char) ((ctrlID & 0x0000FF00L) >> 8),
                (char) (ctrlID & 0x000000FFL)
        );
    }
}
