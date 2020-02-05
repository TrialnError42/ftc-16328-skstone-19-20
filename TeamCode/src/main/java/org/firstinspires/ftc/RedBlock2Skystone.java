package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.teamcode.AutoCommon;

@Autonomous
public class RedBlock2Skystone extends AutoCommon {

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
        moveArmDown(robot.ARM_AUTO_TO_TELEOP_ENC_TICKS);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);

        if (skystonePos == 1) {
            driveOnHeading(4, 0.5, 0);
        } else if (skystonePos == 2) {
            driveOnHeading(-1, 0.3, 0);
        } else {
            driveOnHeading(-10, 0.5, 0);
        }

        // TODO: Research SWITCH function to execute 1 vs 2 vs 3
        // switch (skystonePOS){
        //  case 1: { write code and break; }
        //  case 2: {write code and break; }
        //  case 3: {write code and break; }
        // default: go and do the platform;
        //}
        // TODO: For now duplicate the work

        turnToHeading(-90, 0.3);
        driveOnHeading(17, 0.5, -90);
        driveOnHeading(4, 0.1, -90);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYSTONE_GRAB_POS - 0.1);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        sleep(500);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS + 0.2);
        sleep(500);
        driveOnHeading(-3, 0.3, -90);

        //Need to take right turn and move forward
        // Need to use the relative position to drive towards trey

        turnToHeading(-178, 0.3);
        // the following ststement needs t adjusted based on the skystone position

        if (skystonePos == 1) {
            driveOnHeading(87, 0.8, -180);
        } else if (skystonePos == 2) {
            driveOnHeading(81, 0.8, -180);
        } else {
            driveOnHeading(70, 0.8, -180);
        }





        driveOnHeading(3,0.3,-180);
        robot.setFlaps(false);
        turnToHeading(-90, 0.3);
        driveOnHeading(7, 0.5, -90);
        driveOnHeading(5, 0.3, -90);
        robot.setFlaps(true);
        sleep(1000);
        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
        //added program down

        /***********COPIED FROM ***********/

        // arc backwards to reposition the platform
        driveOnHeading(-5, 0.5, 260);

        driveOnHeading(-20, 0.5, 250);


        turnToHeading(180, 0.5);
        //wall push the platform into the
        driveOnHeading(-5, 0.6, -90);

        // raise the flaps and wait for them to go up
        robot.setFlaps(false);
        sleep(500);
        // back up
        driveOnHeading(-10, 0.8, -90);
        // put the flaps back down
        robot.setFlaps(true);


        // turn around and move the arm/claw to teleop position
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_STRAIGHT_POS);

        //lift arm by inch
        moveArmUp(300);

        // drive to the sky-bridge
        strafeOnHeading(-13, 0.8, 0);

        if (skystonePos == 1) {
            driveOnHeading(-90, 0.8, -180);
        } else if (skystonePos == 2) {
            driveOnHeading(-85, 0.8, -180);
        } else {
            driveOnHeading(-80, 0.8, -180);
        }

        driveOnHeading(-3,0.3,180);
        turnToHeading(-90,0.5);
        driveOnHeading(8,0.4,-90);
        driveOnHeading(1,0.1,-90);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS + 0.2);
        driveOnHeading(-4,0.8,-90);
        turnToHeading(180,0.5);

        if (skystonePos == 1) {
            driveOnHeading(105, 0.8, -180);
        } else if (skystonePos == 2) {
            driveOnHeading(100, 0.8, -180);
        } else {
            driveOnHeading(95, 0.8, -180);
        }

        robot.servoClaw.setPosition(robot.CLAW_OPEN_POS);
        sleep(300);
        driveOnHeading(-35,0.8,-180);


        /*driveOnHeading(35, 0.8, 0);
        sleep(1000);
        //    robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS);
*/
    }
}