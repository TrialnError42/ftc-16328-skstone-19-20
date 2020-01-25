package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous
@Disabled
public class RedBlockParkTest extends AutoCommon {

    @Override
    public void runOpMode() {
        super.runOpMode();

        driveOnHeading(1, 0.3, 0);
        turnToHeading(90, 0.3);

        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_STRAIGHT_POS);
        moveArmDown(4900);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);

        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_STRAIGHT_POS);
        robot.servoClaw.setPosition(robot.CLAW_CLOSE_POS);

        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS);


        driveOnHeading(-22, 0.3, 90);
        strafeOnHeading(-2, 0.3, 90);

    }


}
