import org.apache.commons.math3.distribution.TDistribution;

/**
 * The tool accepts double numbers as input and outputs the mean and distribution.
 * It uses JMH's math (Student's t-distribution with 0.999 confidence) and is essentially a simplified version of JMH's code.
 */
class DistribErrorPrinter {

    // Result.java
    public void iterationResult() {
        System.out.println(String.format("   %.2f ± %.2f", getScore(), getScoreError()));
    }
    public double getScoreError() {
        return getMeanErrorAt(0.999);
    }
    public double getScore() {
        return getMean();
    }

    // AbstractStatistics.java
    public double getMeanErrorAt(double confidence) {
        if (getN() <= 2) return Double.NaN;
        TDistribution tDist = new TDistribution(getN() - 1);
        double a = tDist.inverseCumulativeProbability(1 - (1 - confidence) / 2);
        return a * getStandardDeviation() / Math.sqrt(getN());
    }
    public double getStandardDeviation() {
        return Math.sqrt(getVariance());
    }

    // ListStatistics.java
    public double getVariance() {
        if (count > 1) {
            double v = 0;
            double m = getMean();
            for (int i = 0; i < count; i++) {
                v += Math.pow(values[i] - m, 2);
            }
            return v / (count - 1);
        } else {
            return Double.NaN;
        }
    }
    public double getMean() {
        if (getN() > 0) {
            return getSum() / getN();
        } else {
            return Double.NaN;
        }
    }
    public double getSum() {
        if (count > 0) {
            double s = 0;
            for (int i = 0; i < count; i++) {
                s += values[i];
            }
            return s;
        } else {
            return Double.NaN;
        }
    }

    double values[];
    int count;
    int getN() { return count; }

    DistribErrorPrinter (double[] data) {
        values = data;
        count = data.length;
    }

    // wget https://repo.maven.apache.org/maven2/org/apache/commons/commons-math3/3.0/commons-math3-3.0.jar
    // javac -cp commons-math3-3.0.jar DistribErrorPrinter.java ; ~/jdk-21.0.4/bin/java -cp .:commons-math3-3.0.jar DistribErrorPrinter 1.2 1.3 1.1
    public static void main(String[] a) {
        double[] varr = new double[a.length];
        for (int i=0; i<a.length; i++) {
            varr[i] = Double.parseDouble(a[i]);
        }
        new DistribErrorPrinter(varr).iterationResult();
    }
}
