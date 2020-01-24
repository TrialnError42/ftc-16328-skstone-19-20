package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class BluePlatformPark extends AutoCommon {

    @Override
    public void runOpMode() {
        super.runOpMode();

        driveOnHeading(1, 0.3, 0);
        turnToHeading(-90, 0.3);
        robot.servoClawPivot.setPosition(robot.CLAW_PIVOT_SKYBRIDGE_POS);
        driveOnHeading(22, 0.3, -90);
        strafeOnHeading(2, 0.3, -90);

    }


}
