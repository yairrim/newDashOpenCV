package dashboard.tcp;


import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;


import static java.lang.Double.parseDouble;

public class MessageProcessing {

    public static double[] driveData = new double[4];
    public static double[] horizontalData = new double[3];
    public static double[] verticalData = new double[3];
    public static double[] pos = {0,0,0};
    public static BufferedImage frame;

    public static void processMessage(String receivedMessage) {
        String receivedMessageString = receivedMessage;
      //  System.out.println("RECEIVED: " + receivedMessageString);


        String[] splitMessages = receivedMessageString.split("%");


        for(String message : splitMessages){
            String[] splitString = message.split(",");
            String id = splitString[0];

            if(id.equals("DRIVE")){
                processDrive(splitString);
            }else{
                if(id.equals("HORIZONTAL")){
                    processHorizontal(splitString);
                }else{
                    if(id.equals("VERTICAL")){
                        processVertical(splitString);
                    }else{
                        if (id.equals("POS")){
                            processPos(splitString);
                        }else {
                            if (id.equals("FRAME")){
                                processFrame(splitString);
                            }else {
                            }
                        }
                    }
                }
            }
        }
    }


    private static void processDrive(String[] splitString){
        driveData[0] = parseDouble(splitString[1]);
        driveData[1] = parseDouble(splitString[2]);
        driveData[2] = parseDouble(splitString[3]);
        driveData[3] = parseDouble(splitString[4]);
    }

    private static void processHorizontal(String[] splitString){
        horizontalData[0] = parseDouble(splitString[1]);
        horizontalData[1] = parseDouble(splitString[2]);
        horizontalData[2] = parseDouble(splitString[3]);
    }

    private static void processVertical(String[] splitString) {
        verticalData[0] = parseDouble(splitString[1]);
        verticalData[1] = parseDouble(splitString[2]);
        verticalData[2] = parseDouble(splitString[3]);
    }

    private static void processPos(String[] splitString){
        pos[0] = Double.parseDouble(splitString[1]);
        pos[1] = Double.parseDouble(splitString[2]);
        pos[2] = Double.parseDouble(splitString[3]);
    }
    private static void processFrame(String[] splitString){
        frame = decodeToImage(splitString[1]);


    }
    public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}