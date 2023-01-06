package frc.robot.util;

// Currently writing this to directly work with shooter
// TODO: make this more universal later - we've got a pep rally to do
public class LookupTable {
    
    private double[][] points;
    // {Slope, Y-Intercept}
    private double[] distanceFormula = {0.0, 0.0};
    private double[] hoodFormula = {0.0, 0.0};

    /**
     * @param points Pass points in the format of {Distance, RPM, Hood Angle}
     */
    public LookupTable(double[][] points) {
        this.points = points;

        distanceFit();
        hoodFit();

        System.out.println("y = " + distanceFormula[0] + " + " + distanceFormula[1]);

    }

    /**
     * Returns shooter rpm and hood angle for a distance
     * @param distance distance from goal with units used in this objects initialization
     * @return {Shooter RPM, Hood Angle}
     */
    public double[] calculate(double distance) {
        return new double[]{distanceFormula[0]*distance+distanceFormula[1], hoodFormula[0]*distance+hoodFormula[1]};
    }

    /**
     * Calculates the line of best fit for the distance equation
     */
    private void distanceFit() {
        double count = points.length;
        double sumX = 0.0;
        double sumY = 0.0;
        double sumX2 = 0.0;
        double sumXY = 0.0;
        for (double[] x : points) {
            sumX += x[0];
            sumY += x[1];
            sumX2 += x[0]*x[0];
            sumXY += x[0]*x[1];
        }

        //https://www.programsbuzz.com/article/best-fit-line
        double slope = ((count*sumXY) - (sumX*sumY))/((count*sumX2)-(sumX*sumX));
        double yInt = ((sumX2*sumY)-(sumX*sumXY))/((count*sumX2)-(sumX*sumX));

        distanceFormula[0] = slope;
        distanceFormula[1] = yInt;
    }

    /**
     * Calculates the line of best fit for the hood angle equation
     */
    private void hoodFit() {
        double count = points.length;
        double sumX = 0.0;
        double sumY = 0.0;
        double sumX2 = 0.0;
        double sumXY = 0.0;
        for (double[] x : points) {
            sumX += x[0];
            sumY += x[2];
            sumX2 += x[0]*x[0];
            sumXY = x[0]*x[2];
        }
        double xMean = sumX/count;
        double yMean = sumY/count;

        double slope = ((count*sumXY) - (sumX*sumY))/((count*sumX2)-(sumX*sumX));
        double yInt = ((sumX2*sumY)-(sumX*sumXY))/((count*sumX2)-(sumX*sumX));

        hoodFormula[0] = slope;
        hoodFormula[1] = yInt;
    }

    

}
