package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class PowerPlayDetection extends OpenCvPipeline {
    final Scalar RED = new Scalar(255, 0, 0);
    final Scalar BLACK = new Scalar(0, 0, 0);
    final Scalar WHITE = new Scalar(255, 255, 255);

    int position;

    final Point CONE_TOP_LEFT_ANCHOR_POINT = new Point(130,200);

    Point cone_pointA = new Point(CONE_TOP_LEFT_ANCHOR_POINT.x, CONE_TOP_LEFT_ANCHOR_POINT.y);
    Point cone_pointB = new Point(
            CONE_TOP_LEFT_ANCHOR_POINT.x + 20, // adding region width
            CONE_TOP_LEFT_ANCHOR_POINT.y + 20); // adding region height

    // Create points for left and right pole alignment autos to create submats for detection

    Mat region1_Cb;
    Mat YCrCb = new Mat();
    Mat Cb = new Mat();
    int avgConeCb;

    void inputToCb(Mat input) {
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(YCrCb, Cb, 0);
    }

    @Override
    public void init(Mat firstFrame) {
        inputToCb(firstFrame);
        region1_Cb = Cb.submat(new Rect(cone_pointA, cone_pointB));
        // create submats for a pole alignment on both the right and left

        /*
            Properly implement YCrCb for a more accurate alignment
            channel 0 => Y
            channel 1 => Cr
            channel 2 => Cb

            Telemetry all 3 channels and find out where yellow can be tracked the best and maybe even fix our regular autonomous to be more accurate.

            processFrame for all these submats and create Core.mean int values for YCrCb for each input
            use ImgProcs to highlight the pole yellow if detected and green if not based on YCrCb
         */
    }

    @Override
    public Mat processFrame(Mat input) {
        inputToCb(input);
        avgConeCb = (int) Core.mean(region1_Cb).val[0];

        if (avgConeCb < 50) {
            position = 3;
            Imgproc.rectangle(input, cone_pointA, cone_pointB, BLACK, -1);
        } else if (avgConeCb < 150) {
            position = 1;
            Imgproc.rectangle(input, cone_pointA, cone_pointB, RED, -1);
        } else {
            position = 2;
            Imgproc.rectangle(input, cone_pointA, cone_pointB, WHITE, -1);
        }
        return input;
    }
    public int getAvg() {
        return avgConeCb;
    }
    public int getPosition() {
        return position;
    }
}
