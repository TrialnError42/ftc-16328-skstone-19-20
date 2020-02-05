package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

@Autonomous
public class RedBlockSkystone extends AutoCommon {

    @Override
    public void runOpMode() {
        super.runOpMode();

        // get the skystone pos
        CameraDevice.getInstance().setFlashTorchMode(true);
        sleep(100);
        int skystonePos = getSkystonePos();
        CameraDevice.getInstance().setFlashTorchMode(false);

        // drive away from wall
        strafeOnHeading(8, 0.3, 0);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_APPROACH_POS);
        moveArmDown(robot.ARM_AUTO_TO_SKYSTONE_ENC_TICKS);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);

        if (skystonePos == 1) {
            driveOnHeading(5, 0.3, 0);
        } else if (skystonePos == 2) {
            driveOnHeading(-1, 0.3, 0);
        } else {
            driveOnHeading(-9, 0.3, 0);
        }

        turnToHeading(-90, 0.3);
        driveOnHeading(18, 0.3, -90);
        driveOnHeading(4, 0.1, -90);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        sleep(500);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS + 0.2);
        //lift arm by inch
        moveArmUp(150);
        driveOnHeading(-3, 0.3, -90);

        turnToHeading(-178, 0.3);
        // Move forward based on where we picked up the skystone position

        if (skystonePos == 1) {
            driveOnHeadingRamp(91, 0.3, 1.0, 15,-180);
        } else if (skystonePos == 2) {
            driveOnHeadingRamp(83, 0.3, 1.0, 15,-180);
        } else {
            driveOnHeadingRamp(73, 0.3, 1.0, 15,-180);
        }

        robot.setFlaps(false);
        turnToHeading(-90, 0.3);
        driveOnHeading(2, 0.3, -90);
        sleep(200);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
        //lift arm by inch
        moveArmUp(300);
        driveOnHeading(6, 0.4, -90);
        driveOnHeading(4, 0.2, -90);
        robot.setFlaps(true);
        sleep(500);
        //added program down

        // arc backwards to reposition the platform
        driveOnHeading(-5, 0.3, 260);

        driveOnHeading(-20, 0.3, 250);

        turnToHeading(180, 0.3);
        //wall push the platform into the
        driveOnHeading(5, 0.5, 180);

        // raise the flaps and wait for them to go up
        robot.setFlaps(false);
        sleep(500);
        // back up
        driveOnHeading(-10, 0.3, 180);
        // put the flaps back down
        robot.setFlaps(true);


        // turn around and move the arm/claw to teleop position
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_STRAIGHT_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);

        // drive to the sky-bridge
        turnToHeading(-90, 0.5);
        driveOnHeading(9, 0.5, -90);
        turnToHeading(0, 0.5);
        driveOnHeading(35, 0.3, 0);
        sleep(1000);

    }
}