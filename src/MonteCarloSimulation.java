import java.util.concurrent.ThreadLocalRandom;

public final class MonteCarloSimulation {
    public static double run(final double value, final double mean, final double sd) {
        double gaussian = ThreadLocalRandom.current().nextGaussian();
        gaussian = gaussian * sd + mean;

        return value * (1 + (gaussian / 100));
    }

    public static void main(String [] args) {
        Portfolio.AGGRESSIVE.simulate(100000.0, 20, 10000);
        Portfolio.VERY_CONSERVATIVE.simulate(100000.0, 20, 10000);

        System.out.println("Aggressive Portfolio: Median: "
                + Portfolio.AGGRESSIVE.getMedianReturn());
        System.out.println("Aggressive Portfolio: 90: "
                + Portfolio.AGGRESSIVE.getBestCase10());
        System.out.println("Aggressive Portfolio: 10: "
                + Portfolio.AGGRESSIVE.getWorstCase10());

        System.out.println("Conservative Portfolio: Median: "
                + Portfolio.VERY_CONSERVATIVE.getMedianReturn());
        System.out.println("Conservative Portfolio: 90: "
                + Portfolio.VERY_CONSERVATIVE.getBestCase10());
        System.out.println("Conservative Portfolio: 10: "
                + Portfolio.VERY_CONSERVATIVE.getWorstCase10());
    }
}

