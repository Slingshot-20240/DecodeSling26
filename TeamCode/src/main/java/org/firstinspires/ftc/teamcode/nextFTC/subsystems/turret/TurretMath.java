package org.firstinspires.ftc.teamcode.nextFTC.subsystems.turret;

public class TurretMath {

    // Normalize any angle to (-pi, pi] /
    public static double wrapToPi(double angle) {
        while (angle <= -Math.PI) angle += 2 * Math.PI;
        while (angle > Math.PI) angle -= 2 * Math.PI;
        return angle;
    }

    /** Compute the absolute angle from turret to target (radians) */
    public static double absoluteBearing(double xt, double yt,
                                         double xT, double yT) {
        // vector to target
        return Math.atan2(yT - yt, xT - xt);
    }

    /** Compute how much to rotate turret (radians) */
     public static double requiredRotation(double xt, double yt,
                                           double xT, double yT,
                                           double headingRad) {
         double dx = xT - xt;   // X distance to target
         double dy = yT - yt;   // Y distance to target

         if (dx == 0 && dy == 0) {
             // turret and target in same spot, direction undefined
             return 0.0;
         }

         double theta = Math.atan2(dy, dx);    // angle to target
         double dtheta = theta - headingRad;   // how far off we are
         return wrapToPi(dtheta);              // shortest rotation
     }

     /** Same thing but in degrees */
    public static double requiredRotationDeg(double xt, double yt,
                                             double xT, double yT,
                                             double headingDeg) {
        double headingRad = Math.toRadians(headingDeg);
        double dthetaRad = requiredRotation(xt, yt, xT, yT, headingRad);
        return Math.toDegrees(dthetaRad);
    }
}