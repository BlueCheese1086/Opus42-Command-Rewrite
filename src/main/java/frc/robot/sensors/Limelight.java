package frc.robot.sensors;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
    //limelight returns in inches
    NetworkTable limelight;

    public Limelight(){
        limelight = NetworkTableInstance.getDefault().getTable("limelight");
    }
 
     /**
      * @return horizontal angle of the current target in degrees
      */
     public double getXAngle() {
         return limelight.getEntry("tx").getDouble(0);
     }
 
     /**
      * @return vertical angle of the current target in degrees (i hope)
      */
     public double getYAngle() {
         return limelight.getEntry("ty").getDouble(0);
     }

     /**
      * Sets light settings
      * ledMode settings:
      *    0 - use the LED Mode set in current pipeline
      *    1 - force off
      *    2 - force blink
      *    3 - force on
      * @param val
      */
     public void setLights(int val) {
         limelight.getEntry("ledMode").setNumber(val);
     }
 
     /**
      * Sets camMode:
      *    0 - vision processor
      *    1 - Driver Camera (Increases exposure, disables vision processing)
      * @param val
      */
     public void setCameraMode(int val) {
         limelight.getEntry("camMode").setNumber(val);
     }
}
