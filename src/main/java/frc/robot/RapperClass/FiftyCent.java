package frc.robot.RapperClass;

import java.util.ArrayList;
import java.util.Map;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;

public class FiftyCent extends CANSparkMax {

    private boolean onDashboard = true;
    private String name;
    
    private static ArrayList<FiftyCent> motors = new ArrayList<>();

    public FiftyCent(int id, MotorType type) {
        super(id, type);
        motors.add(this);
    }

    public FiftyCent(int id, MotorType type, String name) {
        super(id, type);
        this.name = name;
        motors.add(this);
    }

    public static void putShuffleboard() {
        motors.forEach((m) -> {
            if (m.onDashboard()) {
                m.putData();
            }
        });
    }

    public void putOnDashboard(boolean b) {
        onDashboard = b;
    }

    public boolean onDashboard() {
        return onDashboard;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public ShuffleboardLayout getLayout() {
        return Shuffleboard.getTab("Motors").getLayout(name == null ? String.valueOf(this.getDeviceId()) : name, BuiltInLayouts.kList);
    }

    public void putData() {
        ShuffleboardLayout layout = this.getLayout().withSize(1, 3);
        layout.add("Temperature" + layout.getTitle(), this.getMotorTemperature())
            .withSize(1, 1)
            .withPosition(0, 0)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withProperties(Map.of("min", 22, "max", 125));
        layout.add("Amperage" + layout.getTitle(), this.getOutputCurrent())
            .withSize(1, 1)
            .withPosition(0, 1)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withProperties(Map.of("min", 0, "max", 100));
        layout.add("EncoderPos" + layout.getTitle(), this.getEncoder().getPosition())
            .withSize(1, 1)
            .withPosition(0, 2)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withProperties(Map.of("min", 22, "max", 125));
    }

}
