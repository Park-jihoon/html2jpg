package com.argo.hwp.application;


import com.argo.hwp.domain.ImageBean;
import com.argo.hwp.utils.EtcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;


public class ImageProcessing {
    protected static Logger log = LoggerFactory
            .getLogger(ImageProcessing.class);
    public ImageProcessing()  { }



    public BufferedImage getImage(File file) {
        BufferedImage img = null;
        try
        {
            InputStream in = new FileInputStream(file);
            img = ImageIO.read(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("한글이 왜째서 ");
            img = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, 500, 500);
        }
        return img;
    }

    public BufferedImage getImage(String path) {
        return getImage(new File(path));
    }

    public boolean run(ArrayList<ImageBean> imageBeans) throws IOException {
        boolean result = false;
        ImageProcessing x = new ImageProcessing();
        // PhoneImageBean ( url, x, y, width, height )



        // getProperties
        String propFile = this.getClass().getClassLoader().getResource("application.properties").getFile();
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(propFile);
        props.load(new java.io.BufferedInputStream(fis));

        BufferedImage bgimg = x.getImage(props.getProperty("tmplate.00"));

        int width = bgimg.getWidth();
        int height = bgimg.getHeight();
        BufferedImage tmp = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );

        Graphics2D g = tmp.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g.drawImage( bgimg, null, 0, 0 );
        String title = "한글 타이틀 12312412";

        Collections.sort(imageBeans, new EtcUtil.NameAscCompare());

        for (int i = 0; i < imageBeans.size(); i++)
        {
            title += " " + i;
            ImageBean bean = (ImageBean)imageBeans.get(i);
            log.warn(bean.toString());
            g.drawImage( x.getImage(bean.getPath()), bean.getX(), bean.getY(), bean.getWidth(), bean.getHeight(), null );

//            g.setColor(Color.yellow);
//            g.setFont(new Font("돋움", Font.PLAIN, 10));
//            g.drawString(title, bean.getX() + 5, bean.getY() + bean.getHeight() - 5);
        }
//        g.setColor(Color.white);
//        g.fillRect(8, 7, 991, 70);
//        g.setColor(Color.black);
//        g.drawRect(8, 7, 991, 70);




        File file = new File( "C:\\Users\\Administrator\\Downloads\\임수정2.jpg" );

        try
        {
            ImageIO.write(tmp, "jpeg", file);
            result = true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

}