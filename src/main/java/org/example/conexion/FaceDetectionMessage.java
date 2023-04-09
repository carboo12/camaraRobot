package org.example.conexion;
import java.io.Serializable;

import org.opencv.core.Mat;

public class FaceDetectionMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private Mat frame;

    public FaceDetectionMessage(Mat frame) {
        this.frame = frame;
    }

    public Mat getFrame() {
        return frame;
    }

    public void setFrame(Mat frame) {
        this.frame = frame;
    }

}
