package org.firstinspires.ftc.teamcode;

import java.lang.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class TapeTest extends AutoCommon{

    @Override
    public void runOpMode() {
        super.runOpMode();

        driveOnHeading(50, 0.3, 0);

        tapeExtension(1000);

}


}
