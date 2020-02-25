package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp

public class RgSample extends LinearOpMode {
//    private DcMotor motorTest;
//    private DigitalChannel digitalTouch;
//    private DistanceSensor sensorColorRange;
//    private Servo servoTest;

    private RobotHardware robot;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        robot = new RobotHardware(hardwareMap, false);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Running");
        telemetry.update();

        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_STRAIGHT_POS);
        motorPosOffset = robot.motorArm.getCurrentPosition();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            driveControl();
            flapControl();
            armControl();
            tapeControl();
        }
    }

    private void tapeControl() {

        boolean dTapeExtend = gamepad2.dpad_right;
        boolean dTapeReverse = gamepad2.dpad_left;
        double speed = 0.0;

        if (dTapeExtend) {
            speed = 1.0;
        } else if (dTapeReverse) {
            speed = -1.0;
        }
        robot.motorTape.setPower(speed);
      //  sleep(300);
        robot.motorTape.setPower(0.0);

    }

    private void driveControl() {
        double scale = 0.3;
        if (gamepad1.left_bumper) {
            scale = 1.0;
        } else if (gamepad1.left_trigger > 0.5) {
            scale = 0.1;
        }

        double drive = -gamepad1.left_stick_y; // ?? why is this getting negated
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        robot.startMove(drive, strafe, turn, scale);
    }

    private boolean prevAPress = true;
    private boolean isFlapDown = true;
    private void flapControl() {
        if (gamepad1.a && !prevAPress) {
            isFlapDown = !isFlapDown;
            robot.setFlaps(isFlapDown);
        }
        prevAPress = gamepad1.a;
    }

    private int motorPosOffset = 0;
    private boolean prevBumperPress = true;
    private boolean isClawOpen = false;
    private void armControl() {
        double armPower = gamepad1.right_trigger > 0.5 ? robot.ARM_POWER_FAST : robot.ARM_POWER_SLOW;
        if (gamepad1.dpad_down) {
            robot.motorArm.setPower(-armPower);
        } else if (gamepad1.dpad_up) {
            robot.motorArm.setPower(armPower);
        } else {
            robot.motorArm.setPower(0);
        }

        if (gamepad1.b) {
            motorPosOffset = robot.motorArm.getCurrentPosition();
        }

        if (gamepad1.right_bumper && !prevBumperPress) {
            isClawOpen = !isClawOpen;
            robot.servoClaw.setPosition(isClawOpen ? robot.CLAW_OPEN_POS : robot.CLAW_CLOSE_POS);
        }
        prevBumperPress = gamepad1.right_bumper;

        double clawPivotPos = robot.CLAW_PIVOT_STRAIGHT_POS + ((robot.motorArm.getCurrentPosition() - motorPosOffset) / robot.CLAW_PIVOT_MOTOR_MOD);
        robot.servoClawPivot.setPosition(Range.clip(clawPivotPos, robot.CLAW_PIVOT_MIN_POS, robot.CLAW_PIVOT_MAX_POS));

        telemetry.addData("Motor Pos", robot.motorArm.getCurrentPosition());
        telemetry.update();



    }
}
