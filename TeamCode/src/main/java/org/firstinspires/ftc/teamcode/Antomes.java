/*
 * Copyright (c) 2020 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

/*
 * This sample demonstrates a basic (but battle-tested and essentiallypi
 * 100% accurate) method of detecting the skystone when lined up with
 * the sample regions over the first 3 stones.
 */
@Autonomous
public class Antomes extends LinearOpMode
{
    OpenCvInternalCamera phoneCam;
    SkystoneDeterminationPipeline pipeline;



    // This is how the robot drives. Most likely you will not need to change it, but if it seems like the robot is not moving forward
    // or backward properly, change it
    public void drive (double distance, double speed){

        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Each motor is setup here. The first line initializes the encoder, the second takes in the speed value, and the third
        // initializes the motor. Dont change anything here unless Carlos tells you to
        backLeft.setTargetPosition((int) (distance * (537.7/12.5663706144)));
        backLeft.setPower(speed);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition((int) (distance * (537.7/12.5663706144)));
        frontLeft.setPower(speed);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backRight.setTargetPosition((int) (distance * (537.7/12.5663706144)));
        backRight.setPower(speed);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setTargetPosition((int) (distance * (537.7/12.5663706144)));
        frontRight.setPower(speed);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (frontRight.isBusy()){
            telemetry.addData("frontRight",frontRight.getCurrentPosition());
            telemetry.addData("frontLeft",frontLeft.getCurrentPosition());
            telemetry.addData("backRight",backRight.getCurrentPosition());
            telemetry.addData("backLeft",backLeft.getCurrentPosition());

            telemetry.update();

        }
    }
    //if this doesnt lift the arm try to replace clawMotor.setPower(speed); with clawMotor.setPower(-speed);
    public void liftArm (double distance, double speed){
        clawMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Each motor is setup here. The first line initializes the encoder, the second takes in the speed value, and the third
        // initializes the motor. Dont change anything here unless Carlos tells you to
        clawMotor.setTargetPosition((int) (distance * (537.7/12.5663706144)));
        clawMotor.setPower(speed);
        clawMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (clawMotor.isBusy()){

        }
    }
    // This is how the robot strafes to the right. Probably wont need to change anything unless strafing goes wrong
    public void strafeRight (double distance, double speed){

        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        backLeft.setTargetPosition((int) (-distance * (537.7/12.5663706144)));
        backLeft.setPower(speed);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition((int) (distance * (537.7/12.5663706144)));
        frontLeft.setPower(speed);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backRight.setTargetPosition((int) (distance * (537.7/12.5663706144)));
        backRight.setPower(speed);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setTargetPosition((int) (-distance * (537.7/12.5663706144)));
        frontRight.setPower(speed);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (frontRight.isBusy()){
            telemetry.addData("frontRight",frontRight.getCurrentPosition());
            telemetry.addData("frontLeft",frontLeft.getCurrentPosition());
            telemetry.addData("backRight",backRight.getCurrentPosition());
            telemetry.addData("backLeft",backLeft.getCurrentPosition());

            telemetry.update();

        }





    }
    // Same as strafeRight except left version
    public void strafeLeft (double distance, double speed){

        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backLeft.setTargetPosition((int) (distance * (537.7/12.5663706144)));
        backLeft.setPower(speed);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition((int) (-distance * (537.7/12.5663706144)));
        frontLeft.setPower(speed);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backRight.setTargetPosition((int) (-distance * (537.7/12.5663706144)));
        backRight.setPower(speed);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setTargetPosition((int) (distance * (537.7/12.5663706144)));
        frontRight.setPower(speed);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (frontRight.isBusy()){

        }
    }

    //Dont change anything below here until you see the next comment
    private Blinker expansion_Hub_2;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private Gyroscope imu;
    private Servo clawServo1;
    private Servo clawServo2;
    private DcMotor duckMotor;
    private DcMotor clawMotor;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode()
    {
        /**
         * NOTE: Many comments have been omitted from this sample for the
         * sake of conciseness. If you're just starting out with EasyOpenCv,
         * you should take a look at {@link InternalCamera1Example} or its
         * webcam counterpart, {@link WebcamExample} first.
         */
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        imu = hardwareMap.get(Gyroscope.class, "imu");
        clawServo1 = hardwareMap.get(Servo.class, "clawServo1");
        clawServo2 = hardwareMap.get(Servo.class, "clawServo2");
        duckMotor = hardwareMap.get(DcMotor.class, "duckMotor");
        clawMotor = hardwareMap.get(DcMotor.class, "clawMotor");

        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        duckMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        clawMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        pipeline = new SkystoneDeterminationPipeline();
        phoneCam.setPipeline(pipeline);

        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                phoneCam.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        int pos = pipeline.getPosition();
        telemetry.addData("avg", pipeline.getAvg());
        telemetry.addData("position", pos);
        telemetry.update();

        waitForStart();

        while (opModeIsActive())
        {
            telemetry.addData("avg", pipeline.getAvg());
            telemetry.addData("position", pipeline.getPosition());

            telemetry.addData("fR", frontRight.getCurrentPosition());
            telemetry.addData("fL", frontLeft.getCurrentPosition());
            telemetry.addData("bR", backRight.getCurrentPosition());
            telemetry.addData("bL", backLeft.getCurrentPosition());
            telemetry.update();
        }
    }

    public static class SkystoneDeterminationPipeline extends OpenCvPipeline
    {
        static final Scalar BLUE = new Scalar(0, 0, 255);

        static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(230,98);

        static final int REGION_WIDTH = 20;
        static final int REGION_HEIGHT = 20;

        /*
         * Points which actually define the sample region rectangles, derived from above values
         *
         * Example of how points A and B work to define a rectangle
         *
         *   ------------------------------------
         *   | (0,0) Point A                    |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                  Point B (70,50) |
         *   ------------------------------------
         *
         */
        Point region1_pointA = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x,
                REGION1_TOPLEFT_ANCHOR_POINT.y);
        Point region1_pointB = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

        Mat region1_Cb;
        Mat YCrCb = new Mat();
        Mat Cb = new Mat();
        int avg1;

        void inputToCb(Mat input)
        {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 0);
        }

        @Override
        public void init(Mat firstFrame)
        {
            inputToCb(firstFrame);

            region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
        }

        @Override
        public Mat processFrame(Mat input)
        {

            inputToCb(input);

            avg1 = (int) Core.mean(region1_Cb).val[0];

            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines

            if (avg1 < 50) {
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region1_pointA, // First point which defines the rectangle
                        region1_pointB, // Second point which defines the rectangle
                        new Scalar(0,0,0), // The color the rectangle is drawn in
                        -1);
            } else if (avg1 < 150) {
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region1_pointA, // First point which defines the rectangle
                        region1_pointB, // Second point which defines the rectangle
                        new Scalar(255,0,0), // The color the rectangle is drawn in
                        -1);
            } else {
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region1_pointA, // First point which defines the rectangle
                        region1_pointB, // Second point which defines the rectangle
                        new Scalar(255,255,255), // The color the rectangle is drawn in
                        -1);
            }

            return input;
        }

        public int getAvg() {
            return avg1;
        }
        public int getPosition() {
            if (avg1 < 160) {
                return 3; // black
            } else if (avg1 < 220) {
                return 1; // purple
            } else {
                return 2; // white
            }
        }
    }
}
