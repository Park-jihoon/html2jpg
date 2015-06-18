package com.argo.hwp.domain;

import com.argo.hwp.utils.EtcUtil;
import com.argo.hwp.utils.HwpStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Administrator on 2015-06-05.
 */
public class CtrlHeader {
    protected static Logger log = LoggerFactory
            .getLogger(CtrlHeader.class);
    String ctrlID;       //ctrl ID   4byte UINT32
    long binAttrybute; //속성표     4byte UINT32
    long yOffset;      //세로 오프셋 값 4byte UINT32
    long xOffset;      //가로 오프셋 값 4byte UINT32
    long width;        //width 오브젝트의 폭 4byte UINT32
    long height;       //height 오브젝트의 높이 4byte UINT32
    long zOrder;       //z-order   UINT32
    int[] outLine;     //오브젝트의 바깥4방향 여백 UINT16[4]
    long instanceID;   //문서 내 각 개체에 대한 고유 아이디
    long zzock;        // 쪽나눔 방지
    int len;           //개체 설명문 글자 길이(len) WORD
    String objectComennt;   // 개체 설명문

    private static final int[] HWP_CONTROL_CHARS = new int[] { 0, 10, 13, 24,
            25, 26, 27, 28, 29, 30, 31 };
    private static final int[] HWP_INLINE_CHARS = new int[] { 4, 5, 6, 7, 8, 9,
            19, 20 };
    private static final int[] HWP_EXTENDED_CHARS = new int[] { 1, 2, 3, 11,
            12, 14, 15, 16, 17, 18, 21, 22, 23 };

    public CtrlHeader(HwpStreamReader sectionStream, long length, String ctrlID) throws IOException {
//        log.debug("ctrlID = {}", ctrlID);
        if(ctrlID.equals("gso ") || ctrlID.equals("$con")) {          // 그림개체 컨트롤ID
            this.setCtrlID(ctrlID);
            this.setBinAttrybute(sectionStream.uint32());
            if(EtcUtil.make4Chid(this.getBinAttrybute()).equals(ctrlID)) {
                this.setBinAttrybute(sectionStream.uint32());
            }
            this.setyOffset(sectionStream.uint32());
            this.setxOffset(sectionStream.uint32());
            this.setWidth(sectionStream.uint32());
            this.setHeight(sectionStream.uint32());
            this.setzOrder(sectionStream.uint32());
            this.setOutLine(sectionStream.uint16(4));
            this.setInstanceID(sectionStream.uint32());
            this.setZzock(sectionStream.uint32());
            this.setLen(sectionStream.uint16());
            this.setObjectComennt(sectionStream, this.getLen());
        } else {
//                    log.debug("스킵 컨트롤 아이디 = {}", ctrlID);
            sectionStream.ensureSkip(length - 4);
        }
    }

    public CtrlHeader(HwpStreamReader sectionStream, long length) throws IOException {
        new CtrlHeader(sectionStream, length, EtcUtil.make4Chid(sectionStream.uint32()));
    }

    public String getCtrlID() {
        return ctrlID;
    }

    public void setCtrlID(String ctrlID) {
        this.ctrlID = ctrlID;
    }

    public long getBinAttrybute() {
        return binAttrybute;
    }

    public void setBinAttrybute(long binAttrybute) {
        this.binAttrybute = binAttrybute;
    }

    public int getyOffset() {
        return EtcUtil.toPx(yOffset);
    }

    public void setyOffset(long yOffset) {
        this.yOffset = yOffset;
    }

    public int getxOffset() {
        return EtcUtil.toPx(xOffset);
    }

    public void setxOffset(long xOffset) {
        this.xOffset = xOffset;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getzOrder() {
        return zOrder;
    }

    public void setzOrder(long zOrder) {
        this.zOrder = zOrder;
    }

    public int[] getOutLine() {
        return outLine;
    }

    public void setOutLine(int[] outLine) {
        this.outLine = outLine;
    }

    public long getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(long instanceID) {
        this.instanceID = instanceID;
    }

    public long getZzock() {
        return zzock;
    }

    public void setZzock(long zzock) {
        this.zzock = zzock;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getObjectComennt() {
        return objectComennt;
    }

    public void setObjectComennt(HwpStreamReader sectionStream, int datasize) throws IOException {
        if (datasize == 0) this.objectComennt = "";
        else {
            int[] chars = sectionStream.uint16((int) (datasize));
            StringBuffer buf = new StringBuffer();
            for (int index = 0; index < chars.length; index++) {
                int ch = chars[index];
                if (Arrays.binarySearch(HWP_INLINE_CHARS, ch) >= 0) {
                    if (ch == 9) {
                        buf.append('\t');
                    }
                    index += 7;
                } else if (Arrays.binarySearch(HWP_EXTENDED_CHARS, ch) >= 0) {
                    index += 7;
                } else if (Arrays.binarySearch(HWP_CONTROL_CHARS, ch) >= 0) {
                    buf.append(' ');
                } else {
                    buf.append((char) ch);
                }
            }
            this.objectComennt = buf.toString();
        }
    }



    @Override
    public String toString() {
        return "CtrlHeader{" +
                "ctrlID=" + ctrlID +
                ", 속성표=" + binAttrybute +
                ", 세로 오프셋 값=" + yOffset +
                ", 가로 오프셋 값=" + xOffset +
                ", width 오브젝트의 폭=" + width +
                ", height 오브젝트의 높이=" + height +
                ", zOrder=" + zOrder +
                ", 오브젝트의 바깥4방향 여백=" + Arrays.toString(outLine) +
                ", 문서 내 각 개체에 대한 고유 아이디=" + instanceID +
                ", 쪽나눔 방지=" + zzock +
                ", 개체 설명문 글자 길이(len)=" + len +
                ", 개체 설명문='" + objectComennt + '\'' +
                '}';
    }
    //                    long binAttrybute = sectionStream.uint32(); //속성표     4byte UINT32
//                    long yOffset = sectionStream.uint32();      //세로 오프셋 값 4byte UINT32
//                    long xOffset = sectionStream.uint32();      //가로 오프셋 값 4byte UINT32
//                    long width = sectionStream.uint32();        //width 오브젝트의 폭 4byte UINT32
//                    long height = sectionStream.uint32();       //height 오브젝트의 높이 4byte UINT32
//                    long zOrder = sectionStream.uint32();       //z-order   UINT32
//                    int[] outLine = sectionStream.uint16(4);    //오브젝트의 바깥4방향 여백 UINT16[4]
//                    long instanceID = sectionStream.uint32();   //문서 내 각 개체에 대한 고유 아이디
//                    long zzock = sectionStream.uint32();         // 쪽나눔 방지
//                    int len = sectionStream.uint16();           //개체 설명문 글자 길이(len) WORD
//                    log.debug("개체설명문 길이?? = {}", len);
}
