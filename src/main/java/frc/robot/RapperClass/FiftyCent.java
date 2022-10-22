package frc.robot.RapperClass;

import java.util.ArrayList;
import java.util.Map;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj2.command.button.NetworkButton;

public class FiftyCent extends CANSparkMax {

    private boolean onDashboard = true;
    private String name;
    private boolean enabled = true;

    private NetworkTableEntry enabledButton;
    
    private static ArrayList<FiftyCent> motors = new ArrayList<>();

    public FiftyCent(int id, MotorType type) {
        super(id, type);
        //motors.add(this);
        this.putOnDashboard();
    }

    public FiftyCent(int id, MotorType type, String name) {
        super(id, type);
        this.name = name;
        //motors.add(this);
        this.putOnDashboard();
    }

    public FiftyCent(int id, MotorType type, boolean dashboard) {
        super(id, type);
        //motors.add(this);
        if (dashboard) this.putOnDashboard();
    }

    public FiftyCent(int id, MotorType type, String name, boolean dashboard) {
        super(id, type);
        this.name=name;
        motors.add(this);
        if (dashboard) this.putOnDashboard();
        this.onDashboard = dashboard;
    }

    public static void putShuffleboard() {
        motors.forEach((m) -> {
            if (m.onDashboard()) {
                m.putData();
            }
        });
    }

    public void putOnDashboard() {
        putData();
    }

    public boolean onDashboard() {
        return onDashboard;
    }

    public String getName() {
        return name;
    }

    @Override
    public void set(double speed) {
        if (enabledButton.getBoolean(true)) {
            super.set(speed);
        } else super.set(0.0);
    }

    @Override
    public void setVoltage(double voltage) {
        if (enabledButton.getBoolean(true)) {
            super.setVoltage(voltage);
        } else super.setVoltage(0.0);
    }

    public void follow(FiftyCent motor) {
        super.follow(motor);
    }

    public ShuffleboardLayout getLayout() {
        return Shuffleboard.getTab("Motors").getLayout(name == null ? String.valueOf(this.getDeviceId()) : name, BuiltInLayouts.kList);
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void toggleEnabled() {
        enabled = !enabled;
    }

    public void putData() {
        ShuffleboardLayout layout = this.getLayout().withSize(2, 4);
        layout.addNumber(layout.getTitle() + " Temperature", this::getMotorTemperature)
            .withSize(2, 1)
            .withPosition(0, 0)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withProperties(Map.of("min", 22, "max", 125));
        layout.addNumber(layout.getTitle() + " Amperage", this::getOutputCurrent)
            .withSize(2, 1)
            .withPosition(0, 1)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withProperties(Map.of("min", 0, "max", 100));
        layout.addNumber(layout.getTitle() + " EncoderPos", this.getEncoder()::getPosition)
            .withSize(2, 1)
            .withPosition(0, 2)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withProperties(Map.of("min", 22, "max", 125));
        enabledButton = layout.add(layout.getTitle() + " Enabled", this.getEnabled())
            .withSize(2, 1)
            .withPosition(0, 3)
            .withWidget(BuiltInWidgets.kToggleButton).getEntry();
        new NetworkButton(enabledButton).whenPressed(() -> toggleEnabled());
    }

}
