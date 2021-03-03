package frc.robot.vission;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class Camera {
    public void robotInit() {
        new Thread(() -> {
          UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
          camera.setResolution(640, 480);

          CvSink cvSink = CameraServer.getInstance().getVideo();
          CvSource outputStream = CameraServer.getInstance().putVideo("camera Test", 640, 480);

          Mat source = new Mat();
          Mat output = new Mat();

        while(!Thread.interrupted()) {
        if (cvSink.grabFrame(source) == 0) {
          continue;
        }
        //Changing each frame to a different color to test if it works
        //Imgproc.cvtColor(source, output, Imgproc.COLOCOLOR_RGB2BGRA);
        outputStream.putFrame(output);
      }

        }).start();
      }
}