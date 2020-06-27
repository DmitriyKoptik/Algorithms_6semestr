package GeneticAlgorithm;

public interface FitnessFunction {
    int getArity();
    long run(long[] genom);
}
