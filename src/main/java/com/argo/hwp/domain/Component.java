package com.argo.hwp.domain;

import com.argo.hwp.utils.EtcUtil;
import com.argo.hwp.utils.HwpStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Administrator on 2015-06-12.
 */
public class Component {
    protected static Logger log = LoggerFactory
            .getLogger(Component.class);
    public String ctrlId;
    public long offsetX;
    public long offsetY;
    public int  group;
    public int  localFileVersion;
    public long width;
    public long height;
    public long nowWidth;
    public long nowHeight;
    public long attribute;
    public int rotationAngle;
    public long rotationX;
    public long rotationY;

    public int matrixCnt;
    public double[] transMatrix;
    public double[][][] matrixArrays;
    public double[] scaleMatrix;
    public double[] rotationMatrix;

    public long xPos = 0;
    public long yPos = 0;

    public Component(HwpStreamReader sectionStream, long length) throws IOException {
        ctrlId = EtcUtil.make4Chid(sectionStream.uint32());       //ctrl ID   4byte UINT32
//        log.debug("length = {}", length);
        log.debug("ctrl ID = {}",ctrlId);
        if(ctrlId.equals("$pic")) {       // 그림 개체일 경우에만 실행
            offsetX = sectionStream.uint32();
            if(EtcUtil.make4Chid(offsetX).equals(ctrlId)) {
                offsetX = sectionStream.uint32();
                log.debug("offsetx 한번더!!");
                length = length - 4;
            }
            offsetY = sectionStream.uint32();
            group = sectionStream.uint16();
            localFileVersion = sectionStream.uint16();
            width = sectionStream.uint32();
            height = sectionStream.uint32();
            nowWidth = sectionStream.uint32();
            nowHeight = sectionStream.uint32();
            attribute = sectionStream.uint32();
            rotationAngle = sectionStream.uint16();
            rotationX = sectionStream.uint32();
            rotationY = sectionStream.uint32();

            matrixCnt = sectionStream.uint16();
            transMatrix = sectionStream.doubles(6);

            xPos = (long) transMatrix[2];
            yPos = (long) transMatrix[5];

            if(matrixCnt > 0) {
                matrixArrays = new double[matrixCnt][2][6];
                for(int i = 0; i < matrixCnt; i++) {
                    scaleMatrix = sectionStream.doubles(6);
                    rotationMatrix = sectionStream.doubles(6);
                    matrixArrays[i][0] = scaleMatrix;
                    matrixArrays[i][1] = rotationMatrix;
                    log.debug("scaleMatrix = {}", Arrays.toString(scaleMatrix));
                    log.debug("rotationMatrix = {}", Arrays.toString(rotationMatrix));
                    xPos += scaleMatrix[2] + rotationMatrix[2];
                    yPos += scaleMatrix[5] + rotationMatrix[5];

                }
                log.debug("matrixCnt = {}", matrixCnt);
                log.debug("transMatrix = {}", Arrays.toString(transMatrix));
            }
            length = length - 46;
            log.debug("length = {}" , length);
            log.debug("xPos = {} [{}]" , xPos, EtcUtil.toPx(xPos));
            log.debug("yPos = {} [{}]" , yPos, EtcUtil.toPx(yPos));


            log.debug("XOffset = {} [{}] {}", EtcUtil.toPx(offsetX), EtcUtil.hu2mm(offsetX), offsetX);
            log.debug("YOffset = {} [{}] {}", EtcUtil.toPx(offsetY), EtcUtil.hu2mm(offsetY), offsetY);
            log.debug("group = {}", group);
            log.debug("local file version = {}", localFileVersion);
            log.debug("width = {}", EtcUtil.toPx(width));
            log.debug("height = {}", EtcUtil.toPx(height));
            log.debug("nowWidth = {} [{}] {}", EtcUtil.toPx(nowWidth), EtcUtil.hu2mm(nowWidth), nowWidth);
            log.debug("nowHeight = {} [{}] {}", EtcUtil.toPx(nowHeight), EtcUtil.hu2mm(nowHeight), nowHeight);
            log.debug("attribute = {}",attribute);
            log.debug("회전각 = {}", rotationAngle);
            log.debug("회전중심x좌표 = {}", rotationX);
            log.debug("회전중심x좌표 = {}", rotationY);



//            sectionStream.ensureSkip(length);
//        } else if (ctrlId.equals("$con")) {

        } else {
            sectionStream.ensureSkip(length - 4);
        }

    }
    public String getCtrlId() {
        return ctrlId;
    }

    public int getOffsetX() {
        return EtcUtil.toPx(offsetX);
    }

    public int getOffsetY() {
        return EtcUtil.toPx(offsetY);
    }

    public int getGroup() {
        return group;
    }

    public int getLocalFileVersion() {
        return localFileVersion;
    }

    public int getWidth() {
        return EtcUtil.toPx(width);
    }

    public int getHeight() {
        return EtcUtil.toPx(height);
    }

    public int getNowWidth() {
        return EtcUtil.toPx(nowWidth);
    }

    public int getNowHeight() {
        return EtcUtil.toPx(nowHeight);
    }

    public long getAttribute() {
        return attribute;
    }

    public long getRotationAngle() {
        return rotationAngle;
    }

    public long getRotationX() {
        return rotationX;
    }

    public long getRotationY() {
        return rotationY;
    }

    public int getxPos() {
        return EtcUtil.toPx(xPos);
    }

    public int getyPos() {
        return EtcUtil.toPx(yPos);
    }
}
