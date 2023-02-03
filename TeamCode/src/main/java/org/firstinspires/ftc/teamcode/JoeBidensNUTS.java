
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@TeleOp

public class JoeBidensNUTS extends LinearOpMode {
    private Blinker expansion_Hub_2;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private Gyroscope imu;
    private Servo clawServo1;
    private Servo clawServo2;
    //private DcMotor duckMotor;
    private DcMotor clawMotor;

    double position1;
    double position2;

    ElapsedTime clawTimer = new ElapsedTime();

    @Override
    public void runOpMode() {
        //expansion_Hub_2 = hardwareMap.get(Blinker.class, "Expansion Hub 2");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        imu = hardwareMap.get(Gyroscope.class, "imu");
        clawServo1 = hardwareMap.get(Servo.class, "clawServo1");
        clawServo2 = hardwareMap.get(Servo.class, "clawServo2");
        //duckMotor = hardwareMap.get(DcMotor.class, "duckMotor");
        clawMotor = hardwareMap.get(DcMotor.class, "clawMotor");

        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //duckMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        clawTimer.startTime();

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            //telemetry.update();


            frontLeft.setPower(0.6 * (-gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.right_trigger - gamepad1.left_trigger));
            frontRight.setPower(0.6 * (-gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.right_trigger + gamepad1.left_trigger));
            backLeft.setPower(0.6 * (-gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.right_trigger + gamepad1.left_trigger));
            backRight.setPower(0.6 * (-gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.right_trigger - gamepad1.left_trigger));
            if (gamepad2.right_bumper) {
                position1=.02;
                position2=0;

            }
            if (gamepad2.left_bumper) {
                position1=0;
                position2=.2;
            }

//            if(gamepad2.left_trigger > 0.5 && clawTimer.seconds() > 2){
//                position1 -= 0.001;
//                position2 += 0.001;
//            }
//            if (gamepad2.right_trigger > 0.5 && clawTimer.seconds() > 2){
//                position1 += 0.001;
//                position2 -= 0.001;
//            }

            clawMotor.setPower(-gamepad2.right_stick_y);


            position1 = Range.clip(position1, 0, 1);
            position2 = Range.clip(position2, 0, 1);
            clawServo1.setPosition(position1);
            clawServo2.setPosition(position2);

            telemetry.addData("left trigger position", gamepad2.left_trigger);
            telemetry.addData("clawServo1 position", position1);
            telemetry.addData("clawServo2 position", position2);
            telemetry.update();


        }
    }
}
