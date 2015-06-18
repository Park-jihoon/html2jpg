package com.argo.hwp.domain;

import com.argo.hwp.utils.HwpStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Administrator on 2015-06-12.
 */
public class ComponentPicture {
    protected static Logger log = LoggerFactory
            .getLogger(ComponentPicture.class);

    int binNo;

    public ComponentPicture(HwpStreamReader sectionStream, long length) throws IOException {

//        log.debug("테두리색 = {}", sectionStream.uint32());
//        log.debug("테두리 두께 = {}", sectionStream.uint32());
//        log.debug("테두리 속성 = {}", sectionStream.uint32());
//        log.debug("테두리 이미지 테두리 사각형의 x좌표 = {}", sectionStream.uint32(4));
//        log.debug("테두리 이미지 테두리 사각형의 y좌표 = {}", sectionStream.uint32(4));
//        log.debug("자르기 한 후 사각형의 left = {}", sectionStream.uint32());
//        log.debug("자르기 한 후 사각형의 top = {}", sectionStream.uint32());
//        log.debug("자르기 한 후 사각형의 right = {}", sectionStream.uint32());
//        log.debug("자르기 한 후 사각형의 bottom = {}", sectionStream.uint32());
//
//        // 그림정보 시작
//        log.debug("안쪽 여백정보 시작");
//        log.debug("왼쪽여백={}", sectionStream.uint16());
//        log.debug("오른쪽여백={}", sectionStream.uint16());
//        log.debug("위쪽여백={}", sectionStream.uint16());
//        log.debug("아래쪽여백 ={}", sectionStream.uint16());
//        log.debug("안쪽 여백정보 끝");
//        // 그림정보 끝
//
//
//        // 그림정보 시작
//        log.debug("그림정보 시작");
//        log.debug("밝기={}", sectionStream.uint8());
//        log.debug("명암={}", sectionStream.uint8());
//        log.debug("그림효과={}", sectionStream.uint8());

        //앞부분 주석처리시 스킵구간
        sectionStream.ensureSkip(71);

        binNo = sectionStream.uint16();

        //뒷부분 주석처리시 스킵구간
        sectionStream.ensureSkip(5);

        log.debug("binItem 참조값 ={}", binNo);
//        log.debug("그림정보 끝");
//        // 그림정보 끝
//
//        log.debug("테두리 투명도={}", sectionStream.uint8());
//        log.debug("문서 내 각 개체에 대한 고유 아이디(instance ID)={}", sectionStream.uint32());

        sectionStream.ensureSkip(length - 78);
    }

    public int getBinNo() {
        return binNo;
    }
}
