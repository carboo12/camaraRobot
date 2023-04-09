package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.example.conexion.MyIoHandler;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class cama2 {

    private CascadeClassifier faceDetector;
    private MatOfRect faceDetections;
    private VideoCapture capture;
    private Mat frame;
    private boolean running;
    private JFrame window;
    private JLabel label;

    public cama2() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String ruta = "C:\\opencv\\cara.xml"; // Ruta de lbpcascade_frontalface.xml
        faceDetector = new CascadeClassifier(ruta);
        faceDetections = new MatOfRect();
        capture = new VideoCapture(0);
        frame = new Mat();
        running = true;
        window = new JFrame();
        label = new JLabel();
        window.getContentPane().add(label);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);

    }

    public void run() {
        System.out.println("Deteccion de rostros con OpenCV y Webcam en java by andres2288");

        while (running) {
            try {
                Thread.sleep(100);

                if (capture.read(frame)) {
                    faceDetector.detectMultiScale(frame, faceDetections);

                    for (Rect rect : faceDetections.toArray()) {
                        Imgproc.rectangle(frame, new Point(rect.x, rect.y),
                                new Point(rect.x + rect.width, rect.y + rect.height),
                                new Scalar(0, 255, 0));
                    }
                    displayFrame();

                } else {
                    System.out.println("No se pudo obtener un frame de video");
                    break;
                }
            } catch (InterruptedException e) {
                System.out.println("La detección de rostros se interrumpió");
                break;
            } catch (Exception e) {
                System.out.println("Ocurrió un error durante la detección de rostros: " + e.getMessage());
                break;
            }
        }

        capture.release();
        System.out.println("La detección de rostros se detuvo");
    }
    private void displayFrame() {
        Image image = convertMatToImage(frame);
        label.setIcon(new ImageIcon(image));
        label.repaint();
        window.pack();
    }
    private Image convertMatToImage(Mat mat) {
        BufferedImage image = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);
        mat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
        return image;
    }


    public static void main(String[] args){
     /*   System.loadLibrary(Core.NATIVE_LIBRARY_NAME);*/
        cama2 main = new cama2();
        main.run();

    }

}
