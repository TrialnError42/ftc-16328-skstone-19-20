package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class BluePlatformFar extends AutoCommon {

    @Override
    public void runOpMode() {
        super.runOpMode();

        //drive out to the foundation
        driveOnHeading(16, 0.3, 0);
        turnToHeading(90, 0.3);
        driveOnHeading(12, 0.3, 90);
        //set the flaps up to grab the platform
        robot.setFlaps(false);
        turnToHeading(0, 0.3);
        driveOnHeading(15, 0.3, 0);
        // approach the platform slowly so it does not bounce away
        driveOnHeading(3, 0.1, 0);

        // lower the flaps and wait for them to be down
        robot.setFlaps(true);
        sleep(500);
        // arc backwards to reposition the platform
        driveOnHeadingFlipped(-20, 0.3, 20);
        driveOnHeadingFlipped(-5, 0.3, 10);
        turnToHeading(85, 0.3);
        //wall push the platform into the
        driveOnHeading(6, 0.3, 90);

        // raise the flaps and wait for them to go up
        robot.setFlaps(false);
        sleep(500);
        driveOnHeading(-4, 0.3, 90);
        // put the flaps back down
        robot.setFlaps(true);

        // turn around and move the arm/claw to teleop position
        turnToHeading(-90, 0.3);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_STRAIGHT_POS);
        moveArmDown(robot.ARM_AUTO_TO_TELEOP_ENC_TICKS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);

        // drive to the sky-bridge
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS);
        strafeOnHeading(-15, 0.3, -90);
        driveOnHeading(39, 0.3, -90);
        sleep(1000);
    }
}
