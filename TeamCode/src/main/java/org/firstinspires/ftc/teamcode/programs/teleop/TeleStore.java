package org.firstinspires.ftc.teamcode.programs.teleop;

import com.acmerobotics.roadrunner.Pose2d;

// This is a data store between Auton and TeleOP programs.
// This is a singleton class that can be used to save data between states.
public class TeleStore {
    public Pose2d beginPose;
    private static TeleStore instance;

    private TeleStore() {}

    public static TeleStore getInstance () {
        if (instance != null) {
            return instance;
        } else {
            instance = new TeleStore();
            return instance;
        }
    }
}
