package GeneticAlgorithm;

public class Main {


	public static final int TARGET_VALUE = 40  ;

	public static final int GENE_MIN = -200;

	public static final int GENE_MAX = 200;

	public static final int POPULATION_COUNT = 20;

	public static final int GENES_COUNT = 5;

	public static final float P= 5.0F;

	public static final int MAX_ITERATIONS = 100000;

	public static final int TARGET_IS_REACHED_FLAG = -1  ;

	private static final int TARGET_NOT_REACHED_FLAG = -2  ;

	public static float Fittest = 0 ;

	public static float LeastFittest = 0 ;


	private Chromosome population[]=new Chromosome[Main.POPULATION_COUNT];


	private int fillChromosomesWithFitnesses(int target_value){
		for ( int i=0; i<POPULATION_COUNT;++i ){
			float currentFitness = population[i].calculateFitness(target_value);
			 population[i].setFitness(  currentFitness  );
			 

			 if ( currentFitness == TARGET_IS_REACHED_FLAG  )
				  return i;

		}

		return TARGET_NOT_REACHED_FLAG;
	}

	public float findFittest(int target_value){
		Fittest = population[0].calculateFitness(target_value);
		for ( int i=1; i<POPULATION_COUNT;++i ){
			if (Fittest < population[i].calculateFitness(target_value)){
				Fittest = population[i].calculateFitness(target_value);
			}
		}
		return Fittest;
	}

	public float findLeastFittest(int target_value){
		LeastFittest = population[0].calculateFitness(target_value);
		for ( int i=1; i<POPULATION_COUNT;++i ){
			if (LeastFittest > population[i].calculateFitness(target_value)){
				LeastFittest = population[i].calculateFitness(target_value);
			}
		}
		return LeastFittest;
	}
	

	public static int function( int u, int w, int x, int y, int z ){
		//уравнение u^2 * w * x^2 * y^2 + u^2 * x^2 * z^2 + w^2 * x * y^2 * z + y * z + w * x * z^2 = 40
		return  u * u * w * x * x * y * y + u * u * x * x * z * z + w * w * x * y * y * z + y * z + w * x * z * z ;
	}


	public float getAllFitnessesSum(){
		float allFitnessesSum = .0F; 
		for ( int i=0; i<POPULATION_COUNT;++i ){
			allFitnessesSum+=population[i].getFitness();
		}
		return allFitnessesSum;
	}

	private void fillChromosomeWithLikelihoods(){
		float allFitnessesSum = getAllFitnessesSum();
		float last=.0F;
		
		int i;
		for ( i=0; i<POPULATION_COUNT;++i ){
		   
		   float likelihood = last + (100* population[i].getFitness()/allFitnessesSum );
		   last=likelihood;
		   population[i].setLikelihood( likelihood  );
		}

		population[i-1].setLikelihood( 100  );
	} 

	private void printAllChromosomes(){
		System.out.println("Here is the current state of all chromosomes:");
		for ( int i=0; i<POPULATION_COUNT;++i ){
			System.out.println("**********  Chromosome "+i+"  ********");
			System.out.println( population[i].toString() );
		}
	}

	public static int getRandomInt( int min, int max ){
		return  (int)(Math.random() * ((max+1) - (min+1)) + (min+1));
	}

	public static float getRandomFloat( float min, float max ){
		return  (float) (Math.random()*max + min) ;
	}
	

	public static int getRandomGene(){
		return getRandomInt( GENE_MIN , GENE_MAX);
	}
	

	private void fillChromosomeWithRandomGenes( Chromosome chromosome ){
		for (int i=0;i<GENES_COUNT;++i){
			chromosome.getGenes()[i]=getRandomGene();
				
		}
		
	}
	

	private void createInitialPopulation(){
		for (int i = 0; i<POPULATION_COUNT;++i){
			population[i]=new Chromosome();
			fillChromosomeWithRandomGenes(population[i]);
		}
		
	}
	

	private int[][] getPairsForCrossover(){

		int[][] pairs = new int[POPULATION_COUNT][2];
		
		for (int i = 0; i<POPULATION_COUNT;++i){
			float rand=getRandomFloat(0, 100);
			int firstChromosome = getChromosomeNumberForThisRand(rand);
			
			int secondChromosome;
			do{
				rand=getRandomFloat(0, 100);
				secondChromosome = getChromosomeNumberForThisRand(rand);
				
			}while (firstChromosome==secondChromosome) ;
			
			pairs[i][0] = firstChromosome;
			pairs[i][1] = secondChromosome;
	
		}
		return pairs;
	}


/*	private void analizePairs(int[][] pairs){

		int[] totals = new int[POPULATION_COUNT];

		for (int i = 0; i<POPULATION_COUNT;++i){
			totals[i] = 0;
		}

		for (int i = 0; i<POPULATION_COUNT;++i){
			for (int j = 0; j<2;++j){
				totals [	 pairs[i][j]  ] ++;
			}
		}
	}
*/


	private int getChromosomeNumberForThisRand(float rand){
		
		int i;
		for ( i = 0; i<POPULATION_COUNT;++i){
			
			if (  rand<=population[i].getLikelihood() ){
				 return i;
			}
		}
		return i-1;
		
	}
	
	private Chromosome[] performCrossoverAndMutation(  int[][] pairs ){
		
		Chromosome nextGeneration[]=new Chromosome[Main.POPULATION_COUNT];

		for (int i = 0; i<POPULATION_COUNT;++i){
			Chromosome firstParent = population[  pairs[i][0]  ];
			Chromosome secondParent = population[  pairs[i][1]  ];

 			Chromosome result = firstParent.singleCrossover( secondParent );

 			nextGeneration[i] = result;
 			nextGeneration[i] = nextGeneration[i].mutation();
		}

		return nextGeneration;
	}


	public Chromosome[] getPopulation() {
		return population;
	}


	public void setPopulation(Chromosome[] population) {
		this.population = population;
	}

	public static void main(String[] args){
		Main main = new Main();
		main.createInitialPopulation();

	   long iterationsNumber = 0;
 	  do {	

		int fillingWithFitnessesResult =  main.fillChromosomesWithFitnesses(TARGET_VALUE);

		if ( fillingWithFitnessesResult != TARGET_NOT_REACHED_FLAG ){
			System.out.println ("Solution is found: "+ main.getPopulation()[fillingWithFitnessesResult]  );
			return;
		}
		
		main.fillChromosomeWithLikelihoods();
		main.printAllChromosomes();
		int[][] pairs = main.getPairsForCrossover();
		
		
		Chromosome nextGeneration[]=new Chromosome[Main.POPULATION_COUNT];
		
		nextGeneration = main.performCrossoverAndMutation(pairs);

		main.setPopulation(nextGeneration);

		System.out.println( "-=-=========== Finished iteration #"+iterationsNumber  );
	} while ( iterationsNumber++<MAX_ITERATIONS );

		System.out.println("SOLUTION NOT FOUND. Sad but true...");

		
		
	}


	
}
