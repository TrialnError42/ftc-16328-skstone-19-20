package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp

public class ServoSetup extends LinearOpMode {

    private RobotHardware robot;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        robot = new RobotHardware(hardwareMap, false);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();


        Servo[] servos = new Servo[] { robot.servoRClamp,           robot.servoLClamp,          robot.servoClaw,        robot.servoClawPivot };
        double[] pos = new double[] { robot.RIGHT_FLAP_DOWN_POS,    robot.LEFT_FLAP_DOWN_POS,   robot.CLAW_INIT_POS,    robot.CLAW_PIVOT_INIT_POS };
        String[] names = new String[] { "Right Clamp",              "Left Clamp",               "Claw",                 "Claw Pivot" };

        int index = 0;
        boolean prevBumperPress = true;
        boolean prevButtonPress = true;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.right_bumper && !prevBumperPress) {
                index++;
                if (index >= servos.length) {
                    index = 0;
                }
            } else if (gamepad1.left_bumper && !prevBumperPress) {
                index--;
                if (index < 0) {
                    index = servos.length - 1;
                }
            }

            if (!prevButtonPress) {
                if (gamepad1.a) {
                    pos[index] = Range.clip(pos[index] - 0.05, 0, 1);
                    servos[index].setPosition(pos[index]);
                } else if (gamepad1.b) {
                    pos[index] = Range.clip(pos[index] + 0.05, 0, 1);
                    servos[index].setPosition(pos[index]);
                } else if (gamepad1.x) {
                    pos[index] = Range.clip(pos[index] - 0.01, 0, 1);
                    servos[index].setPosition(pos[index]);
                } else if (gamepad1.y) {
                    pos[index] = Range.clip(pos[index] + 0.01, 0, 1);
                    servos[index].setPosition(pos[index]);
                }
            }

            prevBumperPress = gamepad1.left_bumper || gamepad1.right_bumper;
            prevButtonPress = gamepad1.a || gamepad1.b || gamepad1.x || gamepad1.y;

            telemetry.addData("Servo", names[index]);
            telemetry.addData("Pos", pos[index]);
            telemetry.update();
        }
    }

}
