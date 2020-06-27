package GeneticAlgorithm;

import java.util.Random;

/*  first equation: u * u * w * w * x * y * z + z + x * y * y + w * x * y * z * z + w * w * x * y = 13
 *   second equation: u * u * w * y * z * z + x + w * w * y * y * z + u * y * z = 49
 *   third equation: u * w * x * y + u * u * w * x * y * z + y + x + u * x * y * z * z = -50
 */

public class Main {


	public static final int TARGET_VALUE = 13  ;

	public static final int TARGET_IS_REACHED_FLAG = -1  ; 

	private static final int TARGET_NOT_REACHED_FLAG = -2  ;

	public static final int POPULATION_COUNT = 5;

	public static final int GENES_COUNT = 5;

	public static final int GENE_MIN = -300;

	public static final int GENE_MAX = 300;

	public static final float MUTATION_LIKELIHOOD= 5.0F;

	public static final int MAX_ITERATIONS = 30000;

	

	private Chromosome population[]=new Chromosome[Main.POPULATION_COUNT];
	

	private int fillChromosomesWithFitnesses(int target_value){
		log( "***Started to create FITNESSES for all chromosomes. " );
		for ( int i=0; i<POPULATION_COUNT;++i ){
			log("Filling with fitness population number "+i);
			float currentFitness = population[i].calculateFitness(target_value);
			 population[i].setFitness(  currentFitness  );  
			 log("Fitness: "+population[i].getFitness());
			 

			 if ( currentFitness == TARGET_IS_REACHED_FLAG  )
				  return i;
			 

		}
		
		log( "***Finished to create FITNESSES for all chromosomes. " );
		return TARGET_NOT_REACHED_FLAG;
	}
	

	public static int function( int u, int w, int x, int y, int z ){
		return  u * u * w * w * x * y * z + z + x * y * y + w * x * y * z * z + w * w * x * y ;
	}


	private float getAllFitnessesSum(){
		float allFitnessesSum = .0F; 
		for ( int i=0; i<POPULATION_COUNT;++i ){
			allFitnessesSum+=population[i].getFitness();
		}
		return allFitnessesSum;
	}

	private void fillChromosomeWithLikelihoods(){
		float allFitnessesSum = getAllFitnessesSum();
		log( "***Started to create LIKELIHOODS for all chromosomes. allFitnessesSum="+allFitnessesSum );
		float last=.0F;
		
		int i;
		for ( i=0; i<POPULATION_COUNT;++i ){
		   
		   float likelihood = last + (100* population[i].getFitness()/allFitnessesSum );
		   last=likelihood;
		   population[i].setLikelihood( likelihood  );	
		   log( "Created likelihood for chromosome number "+i+". Likelihood  value:  "+likelihood );
		}

		population[i-1].setLikelihood( 100  );
		
		log( "***Finished to create LIKELIHOODS for all chromosomes. " );
	} 

	private void printAllChromosomes(){
		System.out.println("Here is the current state of all chromosomes:");
		for ( int i=0; i<POPULATION_COUNT;++i ){
			System.out.println("**********  Chromosome "+i+"  ********");
			System.out.println( population[i].toString() );
		}
	}

	public static void log(String message){
		//System.out.println( message );
	}

	public static int getRandomInt( int min, int max ){
		Random randomGenerator;
		randomGenerator = new Random();
		return  (int)(Math.random() * ((max+1) - (min+1)) + (min+1));
	}

	public static float getRandomFloat( float min, float max ){
		return  (float) (Math.random()*max + min) ;
	}
	

	public static int getRandomGene(){
		return getRandomInt( GENE_MIN , GENE_MAX);
	}
	

	private void fillChromosomeWithRandomGenes( Chromosome chromosome ){
		log("Filling chromosome with random genes....");
		for (int i=0;i<GENES_COUNT;++i){
			chromosome.getGenes()[i]=getRandomGene();
			log("Gene:"+i+"; value:"+chromosome.getGenes()[i]);
				
		}
		log("I'm done filling chromosome with random genes..");
		
	}
	

	private void createInitialPopulation(){
		log("*** Started creating initial population...");
		for (int i = 0; i<POPULATION_COUNT;++i){
			log("Creating chromosome number "+i);
			population[i]=new Chromosome();
			fillChromosomeWithRandomGenes(population[i]);
		}
		log("*** FINISHED creating initial population...");	
		
	}
	

	private int[][] getPairsForCrossover(){
		log("*** Started looking for pairs for crossover");
		
		int[][] pairs = new int[POPULATION_COUNT][2];
		
		for (int i = 0; i<POPULATION_COUNT;++i){
			log("Looking for pair number "+i+"...");
			float rand=getRandomFloat(0, 100);
			int firstChromosome = getChromosomeNumberForThisRand(rand);
			log("First individual... Random number: "+rand+"; corresponding chromosome:"+firstChromosome+
				"; chromosome's fitness*100: "+population[firstChromosome].getFitness()*100);
			
			int secondChromosome;
			do{
				rand=getRandomFloat(0, 100);
				secondChromosome = getChromosomeNumberForThisRand(rand);
				
			}while (firstChromosome==secondChromosome) ;  //prevent individual's crossover with itself :)

			
			log("Second individual... Random number: "+rand+"; corresponding chromosome:"+secondChromosome+
					"; chromosome's fitness*100: "+population[secondChromosome].getFitness()*100);
			
			pairs[i][0] = firstChromosome;
			pairs[i][1] = secondChromosome;
	
		}
		
		log("*** Finished looking for pairs for crossover");
		
		return pairs;
	}
	

	private void analizePairs(int[][] pairs){
		log( "*** Started analyzing totals (for testing only)" );
		
		int[] totals = new int[POPULATION_COUNT];

		for (int i = 0; i<POPULATION_COUNT;++i){
			totals[i] = 0;
		}

		for (int i = 0; i<POPULATION_COUNT;++i){
			for (int j = 0; j<2;++j){
				totals [	 pairs[i][j]  ] ++;
			}
			
		}

		//printing totals
		for (int i = 0; i<POPULATION_COUNT;++i){
			log( "Individual #"+i+"; fitness:"+population[i].getFitness()+"; number of crossovers:"+totals[i] );
		}		
		
		log( "*** Finished analyzing totals (for testing only)" );
		
	}
	
	

	private int getChromosomeNumberForThisRand(float rand){
		
		int i;
		for ( i = 0; i<POPULATION_COUNT;++i){
			
			if (  rand<=population[i].getLikelihood() ){
				 return i;
			}
		}
		return i-1;
		
	}
	
	private  Chromosome[] performCrossoverAndMutationForThePopulationAndGetNextGeneration(  int[][] pairs ){
		
		Chromosome nextGeneration[]=new Chromosome[Main.POPULATION_COUNT];
		
		log("*******************************");
		log("Starting performing Crossover operation For The Population...");

		for (int i = 0; i<POPULATION_COUNT;++i){
			log("** Starting crossover #"+i);
			Chromosome firstParent = population[  pairs[i][0]  ];
			Chromosome secondParent = population[  pairs[i][1]  ];
			log("First parent (#"+pairs[i][0]+")\n" + firstParent );
			log("Second parent (#"+pairs[i][1]+")\n" + secondParent );

 			Chromosome result = firstParent.singleCrossover( secondParent );
			nextGeneration[i]=result;
			log( "Resulting (child) chromosome BEFORE the mutation:\n"+ nextGeneration[i]);

			nextGeneration[i]=nextGeneration[i].mutateWithGivenLikelihood();					

			log( "Resulting (child) chromosome AFTER the mutation:\n"+ nextGeneration[i]);
			log("** Finished crossover #"+i);
		}

		log("Finished performing Crossover operation For The Population...");
		return nextGeneration;
	}


	public Chromosome[] getPopulation() {
		return population;
	}


	public void setPopulation(Chromosome[] population) {
		this.population = population;
	}

	/*
	 * main method
	 * */		
	public static void main(String[] args){
		log("main() is started");	
		log("POPULATION_COUNT="+POPULATION_COUNT);
		log("GENES_COUNT="+GENES_COUNT);
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
		main.analizePairs(pairs);
		
		
		Chromosome nextGeneration[]=new Chromosome[Main.POPULATION_COUNT];
		
		nextGeneration = main.performCrossoverAndMutationForThePopulationAndGetNextGeneration(  pairs );

		main.setPopulation(nextGeneration);

		System.out.println( "-=-=========== Finished iteration #"+iterationsNumber  );
	} while ( iterationsNumber++<MAX_ITERATIONS );

		System.out.println("SOLUTION NOT FOUND. Sad but true...");

		
		
	}


	
}
