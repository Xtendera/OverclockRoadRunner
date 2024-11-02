package org.firstinspires.ftc.paths;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(8, 61, Math.toRadians(270)))
                .strafeTo(new Vector2d(8, 46))
                .waitSeconds(.5)
                //Score
                .strafeTo(new Vector2d(8, 29))
                .waitSeconds(.5)
                //Intake
                .strafeTo(new Vector2d(8, 48))
                .waitSeconds(.5)
                //Score
                .strafeTo(new Vector2d(48, 46))
                .waitSeconds(.5)
                .turnTo(Math.toRadians(45))
                //Intake
                .strafeTo(new Vector2d(57, 57))
                .waitSeconds(.5)
                //Score

                .waitSeconds(.5)
                .turnTo(Math.toRadians(90))
                .strafeTo(new Vector2d(-45, 60))
                .waitSeconds(.5)
                //Intake

                //Score
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }


}

