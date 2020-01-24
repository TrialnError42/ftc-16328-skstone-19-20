package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous
public class RedPlatformSlowArm extends AutoCommon {

    @Override
    public void runOpMode() {
        super.runOpMode();

        driveOnHeading(16, 0.3, 0);
        turnToHeading(-90, 0.3);
        driveOnHeading(12, 0.3, -90);
        robot.setFlaps(false);
        turnToHeading(0, 0.3);
        driveOnHeading(15, 0.3, 0);
        driveOnHeading(3, 0.1, 0);
        robot.setFlaps(true);
        sleep(500);
        driveOnHeading(-5, 0.3, -10);
        driveOnHeading(-20, 0.3, -20);
        turnToHeading(-85, 0.3);
        driveOnHeading(6, 0.3, -90);
        robot.setFlaps(false);
        sleep(500);
        driveOnHeading(-4, 0.3, -90);
        robot.setFlaps(true);

        //1.21.2020 New code to move the arm forward to save sometime
        turnToHeading(90, 0.3);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_STRAIGHT_POS);
        moveArmDown(robot.ARM_AUTO_TO_TELEOP_ENC_TICKS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        //1.21.2020 New code ends

        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS);
        strafeOnHeading(-10, 0.4, 90);
        driveOnHeading(38, 0.3, 90);
        sleep(1000);

    }


}
