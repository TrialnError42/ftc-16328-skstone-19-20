package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class BluePlatformFast extends AutoCommon {

    @Override
    public void runOpMode() {
        super.runOpMode();

        driveOnHeading(16, 0.5, 0);
        robot.setFlaps(false);
        strafeOnHeading(-14, 0.5, 0);
        driveOnHeading(13, 0.5, 0);
        driveOnHeading(3, 0.2, 0);
        robot.setFlaps(true);
        sleep(500);
        driveOnHeadingFlipped(-5, 0.3, 10);
        driveOnHeadingFlipped(-20, 0.3, 20);
        turnToHeading(85, 0.3);
        driveOnHeading(6, 0.3, 90);
        robot.setFlaps(false);
        sleep(500);
        driveOnHeading(-8, 0.5, 90);
        robot.setFlaps(true);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS);
        strafeOnHeading(-13.5, 0.5, 90);
        driveOnHeading(-33, 0.5, 90);
        sleep(1000);

    }


}
