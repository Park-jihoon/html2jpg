package com.ponin.hwp.domain;

import com.ponin.hwp.utils.EtcUtil;
import com.ponin.hwp.utils.HwpStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Administrator on 2015-06-17.
 */
public class ComponentContainer {
    protected static Logger log = LoggerFactory
            .getLogger(ComponentContainer.class);
    int objectSize;
    long[] ctrlIds;

    public ComponentContainer(HwpStreamReader sectionStream, long length) throws IOException {
        objectSize = sectionStream.uint16();
        log.debug("objectSize = {}", objectSize);
        if (objectSize > 0) {
            ctrlIds = sectionStream.uint32(objectSize);
            log.debug("ctrlIds = {}", Arrays.toString(ctrlIds));

            for(int i = 0; i < ctrlIds.length; i++) {
                log.debug("make4Chids = ",EtcUtil.make4Chid(ctrlIds[i]));
            }
        }
    }

}
