package com.argo.hwp.domain;

import com.argo.hwp.utils.HwpStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Administrator on 2015-06-13.
 */
public class BinData {
    protected static Logger log = LoggerFactory
            .getLogger(BinData.class);
    int attribute;
    int link1Len;
    int[] link1;
    int link2len;
    int[] link2;
    int binDataId;
    int binNameLen;
    int[] binDataType;

    public BinData(HwpStreamReader sectionStream, long length) throws IOException {
        attribute = sectionStream.uint16();

        switch ((int) (attribute & 0x000FL)) {
            case 0x0000 :
                //log.warn("외부그림참조");
                link1Len = sectionStream.uint16();
                if(link1Len > 0) link1 = sectionStream.uint16(link1Len);
                link2len = sectionStream.uint16();
                if(link2len > 0) link2 = sectionStream.uint16(link2len);
                break;
            case 0x0001 : //log.warn("그림파일포함");
                binDataId = sectionStream.uint16();
                binNameLen = sectionStream.uint16();
                if(binNameLen > 0) binDataType = sectionStream.uint16(binNameLen);
                break;
            case 0x0002 : //log.warn("OLE포함");
                binDataId = sectionStream.uint16();
                break;
        }

        switch ((int) (attribute & 0x00F0L)) {
            case 0x0000 : //log.warn("스토리지 디폴트모드");
                break;
            case 0x0010 : //log.warn("무조건압축");
                break;
            case 0x0020 : //log.warn("압축하지않음");
                break;
        }

        switch ((int) (attribute & 0x0F00L)) {
            case 0x0000 : //log.warn("access되지않은상태");
                break;
            case 0x0100 : //log.warn("access 성공함");
                break;
            case 0x0200 : //log.warn("access실패");
                break;
            case 0x0300 : //log.warn("access실패했지만 무시");
                break;
        }





    }

    private String getBinType2String(int[] binDataType) {
        String result = "";
        for (int i = 0; i < binDataType.length; i++) {
            result += (char) binDataType[i];
        }
        return result;
    }

    @Override
    public String toString() {
        return "BinData{" +
                "attribute=" + attribute +
                ", link1Len=" + link1Len +
                ", link1=" + Arrays.toString(link1) +
                ", link2len=" + link2len +
                ", link2=" + Arrays.toString(link2) +
                ", binDataId=" + binDataId +
                ", binNameLen=" + binNameLen +
                ", binDataType=" + Arrays.toString(binDataType) +
                ", binDataTypeString=" + getBinType2String(binDataType) +
                '}';
    }
}
