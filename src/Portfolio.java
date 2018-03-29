import java.util.Arrays;
import java.util.function.IntToDoubleFunction;

public final class Portfolio {
    public static final Portfolio AGGRESSIVE = new Portfolio(9.4324, 15.675);
    public static final Portfolio VERY_CONSERVATIVE = new Portfolio(6.189, 6.3438);

    private static final double PERCENT_INTEREST = 3.5;

    private final double avgReturn;
    private final double risk;

    private double [] simulations;

    public void simulate(final double amount, final int years, final int iterations) {
        this.simulations = new double [iterations];
        Arrays.parallelSetAll(this.simulations, new IntToDoubleFunction() {
            @Override
            public double applyAsDouble(int value) {
                double total = amount;

                for (int i = 0; i < years; i++) {
                    total = MonteCarloSimulation.run(total, avgReturn, risk);
                    total = adjustForInflation(total);
                }

                return total;
            }
        });
        Arrays.parallelSort(this.simulations);
    }

    private double adjustForInflation(final double value) {
        return value / (1 + (PERCENT_INTEREST / 100.0));
    }

    private void checkIsSimulated() {
        if (this.simulations == null) {
            throw new IllegalAccessError("Cannot get returns without simulation");
        }
    }

    public double getMedianReturn() {
        this.checkIsSimulated();
        return this.getPercentile(50.0);
    }

    public double getBestCase10() {
        this.checkIsSimulated();
        return this.getPercentile(90.0);
    }

    public double getWorstCase10() {
        this.checkIsSimulated();
        return this.getPercentile(10.0);
    }

    private double getPercentile(double percentile) {
        percentile = percentile / 100.0;
        return simulations[(int) Math.round(simulations.length * percentile)];
    }

    private Portfolio(double avgReturn, double risk) {
        this.avgReturn = avgReturn;
        this.risk = risk;
    }
}
